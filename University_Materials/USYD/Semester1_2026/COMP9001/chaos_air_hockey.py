"""Chaos Air Hockey - local two-player Pygame game."""

import math
import random
import pygame

# -----------------------------
# Constants
# -----------------------------
SCREEN_WIDTH = 1024
SCREEN_HEIGHT = 600
TOP_INFO_HEIGHT = 96
HUD_PANEL_HEIGHT = 130
WINDOW_HEIGHT = TOP_INFO_HEIGHT + SCREEN_HEIGHT + HUD_PANEL_HEIGHT
FPS = 60

LINE_COLOR = (200, 210, 225)
LEFT_PADDLE_COLOR = (67, 170, 255)
RIGHT_PADDLE_COLOR = (255, 117, 117)
PUCK_COLOR = (245, 245, 245)
OBSTACLE_COLOR = (110, 128, 150)
GOAL_COLOR = (60, 78, 104)
TEXT_COLOR = (240, 240, 240)
SUBTEXT_COLOR = (180, 190, 205)
MENU_HIGHLIGHT_COLOR = (255, 225, 120)
HUD_PANEL_BG = (10, 14, 22)
HUD_PANEL_BORDER = (132, 150, 176)

DEFAULT_PADDLE_SPEED = 420
MIN_PADDLE_SPEED = 260
MAX_PADDLE_SPEED = 560

DEFAULT_PADDLE_RADIUS = 26
MIN_PADDLE_RADIUS = 18
MAX_PADDLE_RADIUS = 38

PUCK_RADIUS = 12
PUCK_SPEED = 420
SERVE_GAP = 10

REGULATION_TIME = 90

GOAL_HEIGHT = 220
GOAL_BORDER_THICKNESS = 8
GOAL_PAUSE_DURATION = 3.0
ZONE_WIDTH = 270
ZONE_TOP_MARGIN = 55
ZONE_BOTTOM_MARGIN = 55

BUFF_SIZE = 22
BUFF_BORDER = 2

STATE_START = "start"
STATE_TUTORIAL = "tutorial"
STATE_PLAYING = "playing"
STATE_OVERTIME = "overtime"
STATE_GOAL_PAUSE = "goal_pause"
STATE_GAME_OVER = "game_over"

BUFF_TYPES = ["Big Paddle", "Small Paddle", "Speed Boost", "Slow Paddle"]
BUFF_COLORS = {
    "Big Paddle": (74, 222, 128),
    "Small Paddle": (255, 165, 0),
    "Speed Boost": (59, 130, 246),
    "Slow Paddle": (239, 68, 68),
}

REGULATION_TIME_OPTIONS = [10, 60, 90, 120, 180]
PUCK_SPEED_OPTIONS = [360, 420, 500]
MAP_MODE_OPTIONS = ["Random", "Normal Map", "Center Block Map", "Double Block Map"]
BUFF_MODE_OPTIONS = [
    ("Standard", 10, 5),
    ("Frequent", 7, 4),
    ("Chaos", 5, 3),
]


class Obstacle:
    """Static rectangular obstacle placed on the map."""

    def __init__(self, rect: pygame.Rect):
        """Store obstacle geometry as a pygame Rect."""
        self.rect = rect

    def draw(self, surface: pygame.Surface, y_offset: int = 0):
        """Render the obstacle."""
        draw_rect = self.rect.move(0, y_offset)
        pygame.draw.rect(surface, OBSTACLE_COLOR, draw_rect, border_radius=5)
        pygame.draw.rect(surface, (180, 196, 214), draw_rect, 2, border_radius=5)


class Paddle:
    """Player-controlled paddle constrained to a defensive zone."""

    def __init__(self, player_id: str, x: int, y: int, color, control_keys, zone_rect: pygame.Rect):
        """Create a paddle with control keys and movement bounds."""
        self.player_id = player_id
        self.x = float(x)
        self.y = float(y)
        self.color = color
        self.control_keys = control_keys
        self.zone_rect = zone_rect

        self.default_radius = DEFAULT_PADDLE_RADIUS
        self.default_speed = DEFAULT_PADDLE_SPEED
        self.radius = self.default_radius
        self.speed = self.default_speed
        self.move_dir = pygame.Vector2(0, 0)

    def reset_position(self, x: int, y: int, reset_stats: bool = True):
        """Move paddle back to a spawn point and optionally reset stats."""
        self.x = float(x)
        self.y = float(y)
        if reset_stats:
            self.reset_stats()

    def reset_stats(self):
        """Restore default radius and movement speed."""
        self.radius = self.default_radius
        self.speed = self.default_speed

    def apply_big_paddle(self):
        """Increase paddle size, clamped to the global max."""
        self.radius = min(MAX_PADDLE_RADIUS, self.radius + 7)

    def apply_small_paddle(self):
        """Decrease paddle size, clamped to the global min."""
        self.radius = max(MIN_PADDLE_RADIUS, self.radius - 7)

    def apply_speed_boost(self):
        """Increase paddle speed, clamped to the global max."""
        self.speed = min(MAX_PADDLE_SPEED, self.speed + 90)

    def apply_slow(self):
        """Decrease paddle speed, clamped to the global min."""
        self.speed = max(MIN_PADDLE_SPEED, self.speed - 90)

    def update(self, key_state, dt: float):
        """Update paddle movement from input and keep it inside its zone."""
        dx = float(key_state[self.control_keys[0]]) - float(key_state[self.control_keys[1]])
        dy = float(key_state[self.control_keys[2]]) - float(key_state[self.control_keys[3]])
        direction = pygame.Vector2(dx, dy)
        self.move_dir = pygame.Vector2(0, 0)

        if direction.length_squared() > 0:
            direction = direction.normalize()
            self.move_dir = direction
            self.x += direction.x * self.speed * dt
            self.y += direction.y * self.speed * dt

        min_x = self.zone_rect.left + self.radius
        max_x = self.zone_rect.right - self.radius
        min_y = self.zone_rect.top + self.radius
        max_y = self.zone_rect.bottom - self.radius

        self.x = max(min_x, min(max_x, self.x))
        self.y = max(min_y, min(max_y, self.y))

    def draw(self, surface: pygame.Surface, y_offset: int = 0):
        """Render the paddle as a circle."""
        pygame.draw.circle(surface, self.color, (int(self.x), int(self.y + y_offset)), int(self.radius))


class Puck:
    """Puck physics, collisions, and goal detection."""

    def __init__(self):
        """Initialize puck state at center ice."""
        self.x = SCREEN_WIDTH / 2
        self.y = SCREEN_HEIGHT / 2
        self.vx = 0.0
        self.vy = 0.0
        self.radius = PUCK_RADIUS
        self.last_hit_player = None
        self.base_speed = PUCK_SPEED

    def set_base_speed(self, new_speed: int):
        """Set the target speed used for velocity normalization."""
        self.base_speed = new_speed

    def reset(self, serving_player: str, serving_paddle: Paddle):
        """Place puck near the serving paddle and launch toward opponent."""
        direction = 1 if serving_player == "left" else -1
        self.x = serving_paddle.x + direction * (serving_paddle.radius + self.radius + SERVE_GAP)
        self.y = serving_paddle.y
        self.x = max(self.radius, min(SCREEN_WIDTH - self.radius, self.x))
        self.vy = random.uniform(-120, 120)
        self.vx = direction * self.base_speed
        self.last_hit_player = None
        self._normalize_speed()

    def prepare_for_serve(self, serving_player: str, serving_paddle: Paddle):
        """Place puck for serve without launching (used during goal pause)."""
        direction = 1 if serving_player == "left" else -1
        self.x = serving_paddle.x + direction * (serving_paddle.radius + self.radius + SERVE_GAP)
        self.y = serving_paddle.y
        self.x = max(self.radius, min(SCREEN_WIDTH - self.radius, self.x))
        self.vx = 0.0
        self.vy = 0.0
        self.last_hit_player = None

    def _normalize_speed(self):
        """Keep puck movement at a constant base speed."""
        mag = math.hypot(self.vx, self.vy)
        if mag == 0:
            self.vx = self.base_speed
            self.vy = 0
            return
        scale = self.base_speed / mag
        self.vx *= scale
        self.vy *= scale

    def circle_rect_collision(self, rect: pygame.Rect):
        """Return True if puck circle overlaps the given rectangle."""
        closest_x = max(rect.left, min(self.x, rect.right))
        closest_y = max(rect.top, min(self.y, rect.bottom))
        dx = self.x - closest_x
        dy = self.y - closest_y
        return dx * dx + dy * dy <= self.radius * self.radius

    def handle_paddle_collision(self, paddle: Paddle):
        """Resolve collision against one paddle and apply rebound force."""
        dx = self.x - paddle.x
        dy = self.y - paddle.y
        dist_sq = dx * dx + dy * dy
        min_dist = self.radius + paddle.radius

        if dist_sq >= min_dist * min_dist:
            return False

        dist = math.sqrt(dist_sq) if dist_sq > 0 else 0.0001
        nx = dx / dist
        ny = dy / dist

        overlap = min_dist - dist
        self.x += nx * overlap
        self.y += ny * overlap

        paddle_influence = paddle.move_dir * paddle.speed * 0.4
        self.vx = nx * self.base_speed + paddle_influence.x
        self.vy = ny * self.base_speed + paddle_influence.y
        self._normalize_speed()

        self.last_hit_player = paddle.player_id
        return True

    def handle_obstacle_collision(self, obstacle: Obstacle):
        """Resolve puck collision against a rectangular obstacle."""
        rect = obstacle.rect
        closest_x = max(rect.left, min(self.x, rect.right))
        closest_y = max(rect.top, min(self.y, rect.bottom))
        dx = self.x - closest_x
        dy = self.y - closest_y
        dist_sq = dx * dx + dy * dy

        if dist_sq > self.radius * self.radius:
            return

        if dist_sq == 0:
            # Rare case: puck center exactly on nearest point (inside rect); choose shortest axis push.
            left_pen = abs(self.x - rect.left)
            right_pen = abs(rect.right - self.x)
            top_pen = abs(self.y - rect.top)
            bot_pen = abs(rect.bottom - self.y)
            min_pen = min(left_pen, right_pen, top_pen, bot_pen)
            if min_pen == left_pen:
                self.x = rect.left - self.radius
                self.vx = -abs(self.vx)
            elif min_pen == right_pen:
                self.x = rect.right + self.radius
                self.vx = abs(self.vx)
            elif min_pen == top_pen:
                self.y = rect.top - self.radius
                self.vy = -abs(self.vy)
            else:
                self.y = rect.bottom + self.radius
                self.vy = abs(self.vy)
            self._normalize_speed()
            return

        dist = math.sqrt(dist_sq)
        nx = dx / dist
        ny = dy / dist
        overlap = self.radius - dist

        self.x += nx * overlap
        self.y += ny * overlap

        # Reflect puck velocity along collision normal.
        dot = self.vx * nx + self.vy * ny
        self.vx -= 2 * dot * nx
        self.vy -= 2 * dot * ny
        self._normalize_speed()

    def update(self, dt: float, goal_top: int, goal_bottom: int, obstacles):
        """Advance puck and return goal result when a side concedes."""
        self.x += self.vx * dt
        self.y += self.vy * dt

        # Top/bottom wall bounce
        if self.y - self.radius <= 0:
            self.y = self.radius
            self.vy *= -1
        elif self.y + self.radius >= SCREEN_HEIGHT:
            self.y = SCREEN_HEIGHT - self.radius
            self.vy *= -1

        # Left/right walls except goal opening.
        if self.x - self.radius <= 0:
            if goal_top <= self.y <= goal_bottom:
                return "left_conceded"
            self.x = self.radius
            self.vx = abs(self.vx)

        if self.x + self.radius >= SCREEN_WIDTH:
            if goal_top <= self.y <= goal_bottom:
                return "right_conceded"
            self.x = SCREEN_WIDTH - self.radius
            self.vx = -abs(self.vx)

        for obstacle in obstacles:
            self.handle_obstacle_collision(obstacle)

        return None

    def draw(self, surface: pygame.Surface, y_offset: int = 0):
        """Render the puck as a circle."""
        pygame.draw.circle(surface, PUCK_COLOR, (int(self.x), int(self.y + y_offset)), self.radius)


class Buff:
    """A buff pickup that activates when touched by the puck."""

    def __init__(self, buff_type: str, x: int, y: int, spawned_at: float):
        """Create a buff icon at a map position."""
        self.buff_type = buff_type
        self.rect = pygame.Rect(x, y, BUFF_SIZE, BUFF_SIZE)
        self.spawned_at = spawned_at

    def draw(self, surface: pygame.Surface, y_offset: int = 0):
        """Render buff icon with color and shape cues."""
        color = BUFF_COLORS[self.buff_type]
        draw_rect = self.rect.move(0, y_offset)
        pygame.draw.rect(surface, color, draw_rect, border_radius=3)
        pygame.draw.rect(surface, (20, 20, 20), draw_rect, BUFF_BORDER, border_radius=3)

        # Shape cue: size buffs use circles, speed buffs use arrows.
        cx, cy = draw_rect.centerx, draw_rect.centery - 4
        if self.buff_type in ("Big Paddle", "Small Paddle"):
            radius = 5 if self.buff_type == "Big Paddle" else 3
            pygame.draw.circle(surface, (20, 20, 20), (cx, cy), radius, 1)
            if self.buff_type == "Big Paddle":
                pygame.draw.circle(surface, (20, 20, 20), (cx, cy), 2)
        else:
            direction = 1 if self.buff_type == "Speed Boost" else -1
            pygame.draw.line(surface, (20, 20, 20), (cx - 6 * direction, cy), (cx + 5 * direction, cy), 2)
            pygame.draw.line(surface, (20, 20, 20), (cx + 5 * direction, cy), (cx + 1 * direction, cy - 3), 2)
            pygame.draw.line(surface, (20, 20, 20), (cx + 5 * direction, cy), (cx + 1 * direction, cy + 3), 2)


class Game:
    """Top-level game controller for state, logic, and rendering."""

    def __init__(self):
        """Create window, entities, menu state, and runtime flags."""
        pygame.init()
        pygame.display.set_caption("Chaos Air Hockey")
        self.screen = pygame.display.set_mode((SCREEN_WIDTH, WINDOW_HEIGHT))
        self.clock = pygame.time.Clock()

        self.font = pygame.font.SysFont("consolas", 28)
        self.small_font = pygame.font.SysFont("consolas", 20)
        self.big_font = pygame.font.SysFont("consolas", 46)

        self.goal_top = (SCREEN_HEIGHT - GOAL_HEIGHT) // 2
        self.goal_bottom = self.goal_top + GOAL_HEIGHT

        self.left_zone = pygame.Rect(0, ZONE_TOP_MARGIN, ZONE_WIDTH, SCREEN_HEIGHT - ZONE_TOP_MARGIN - ZONE_BOTTOM_MARGIN)
        self.right_zone = pygame.Rect(SCREEN_WIDTH - ZONE_WIDTH, ZONE_TOP_MARGIN, ZONE_WIDTH, SCREEN_HEIGHT - ZONE_TOP_MARGIN - ZONE_BOTTOM_MARGIN)

        self.left_paddle = Paddle(
            "left",
            self.left_zone.centerx,
            self.left_zone.centery,
            LEFT_PADDLE_COLOR,
            (pygame.K_d, pygame.K_a, pygame.K_s, pygame.K_w),
            self.left_zone,
        )
        self.right_paddle = Paddle(
            "right",
            self.right_zone.centerx,
            self.right_zone.centery,
            RIGHT_PADDLE_COLOR,
            (pygame.K_RIGHT, pygame.K_LEFT, pygame.K_DOWN, pygame.K_UP),
            self.right_zone,
        )

        self.puck = Puck()
        self.puck.set_base_speed(PUCK_SPEED)

        self.state = STATE_TUTORIAL
        self.left_score = 0
        self.right_score = 0
        self.regulation_time_left = REGULATION_TIME

        self.map_name = "Normal Map"
        self.obstacles = []

        self.active_buffs = []
        self.next_buff_spawn_time = 0.0
        self.buff_message = ""
        self.buff_message_until = 0.0
        self.controls_hint_until = 0.0
        self.last_frame_time = pygame.time.get_ticks() / 1000.0

        self.winner = ""
        self.running = True
        self.pause_end_time = 0.0
        self.pause_server = "left"
        self.pause_message = ""
        self.lock_map_for_overtime = False

        self.menu_index = 0
        self.time_option_index = REGULATION_TIME_OPTIONS.index(REGULATION_TIME)
        self.puck_speed_option_index = PUCK_SPEED_OPTIONS.index(PUCK_SPEED)
        self.map_mode_option_index = 0
        self.buff_mode_option_index = 0
        self.menu_actions = [
            "Start Match",
            "Regulation Time",
            "Map Mode",
            "Puck Speed",
            "Buff Spawn Mode",
            "Quit Game",
        ]

    def choose_match_map(self):
        """Build obstacle layout based on current map mode option."""
        maps = ["Normal Map", "Center Block Map", "Double Block Map"]
        selected_mode = MAP_MODE_OPTIONS[self.map_mode_option_index]
        self.map_name = random.choice(maps) if selected_mode == "Random" else selected_mode
        self.obstacles = []

        if self.map_name == "Center Block Map":
            rect = pygame.Rect(0, 0, 120, 180)
            rect.center = (SCREEN_WIDTH // 2, SCREEN_HEIGHT // 2)
            self.obstacles.append(Obstacle(rect))
        elif self.map_name == "Double Block Map":
            w, h = 120, 140
            gap = 140
            top_rect = pygame.Rect(0, 0, w, h)
            bot_rect = pygame.Rect(0, 0, w, h)
            top_rect.center = (SCREEN_WIDTH // 2, SCREEN_HEIGHT // 2 - (gap // 2 + h // 2))
            bot_rect.center = (SCREEN_WIDTH // 2, SCREEN_HEIGHT // 2 + (gap // 2 + h // 2))
            self.obstacles.extend([Obstacle(top_rect), Obstacle(bot_rect)])

    def start_new_match(self):
        """Reset match data and switch state to active play."""
        self.left_score = 0
        self.right_score = 0
        self.regulation_time_left = REGULATION_TIME_OPTIONS[self.time_option_index]
        self.winner = ""
        self.active_buffs = []
        self.buff_message = ""
        self.pause_message = ""
        self.lock_map_for_overtime = False

        self.left_paddle.reset_position(self.left_zone.centerx, self.left_zone.centery, reset_stats=True)
        self.right_paddle.reset_position(self.right_zone.centerx, self.right_zone.centery, reset_stats=True)

        self.choose_match_map()
        self.puck.set_base_speed(PUCK_SPEED_OPTIONS[self.puck_speed_option_index])

        first_server = random.choice(["left", "right"])
        serving_paddle = self.left_paddle if first_server == "left" else self.right_paddle
        self.puck.reset(first_server, serving_paddle)

        now = pygame.time.get_ticks() / 1000.0
        _, regulation_interval, _ = BUFF_MODE_OPTIONS[self.buff_mode_option_index]
        self.next_buff_spawn_time = now + regulation_interval
        self.controls_hint_until = now + 10.0
        self.state = STATE_PLAYING

    def interval_for_state(self):
        """Get buff spawn interval for regulation or overtime."""
        _, regulation_interval, overtime_interval = BUFF_MODE_OPTIONS[self.buff_mode_option_index]
        return regulation_interval if self.state == STATE_PLAYING else overtime_interval

    def cycle_option(self, action_name: str):
        """Cycle a configurable start-menu option to its next value."""
        if action_name == "Regulation Time":
            self.time_option_index = (self.time_option_index + 1) % len(REGULATION_TIME_OPTIONS)
        elif action_name == "Map Mode":
            self.map_mode_option_index = (self.map_mode_option_index + 1) % len(MAP_MODE_OPTIONS)
        elif action_name == "Puck Speed":
            self.puck_speed_option_index = (self.puck_speed_option_index + 1) % len(PUCK_SPEED_OPTIONS)
        elif action_name == "Buff Spawn Mode":
            self.buff_mode_option_index = (self.buff_mode_option_index + 1) % len(BUFF_MODE_OPTIONS)

    def activate_start_menu_option(self):
        """Run the action currently highlighted in the start menu."""
        action = self.menu_actions[self.menu_index]
        if action == "Start Match":
            self.start_new_match()
        elif action == "Quit Game":
            self.running = False
        else:
            self.cycle_option(action)

    def spawn_buff(self, now: float):
        """Spawn one buff in the contested center if a valid spot exists."""
        central_left = self.left_zone.right + 30
        central_right = self.right_zone.left - 30 - BUFF_SIZE
        if central_left >= central_right:
            return

        candidates = []
        for _ in range(80):
            x = random.randint(central_left, central_right)
            y = random.randint(50, SCREEN_HEIGHT - 50 - BUFF_SIZE)
            rect = pygame.Rect(x, y, BUFF_SIZE, BUFF_SIZE)

            if rect.colliderect(self.left_zone) or rect.colliderect(self.right_zone):
                continue
            if any(rect.colliderect(ob.rect) for ob in self.obstacles):
                continue
            if any(rect.colliderect(buff.rect) for buff in self.active_buffs):
                continue
            candidates.append((x, y))

        if not candidates:
            return

        x, y = random.choice(candidates)
        buff_type = random.choice(BUFF_TYPES)
        self.active_buffs.append(Buff(buff_type, x, y, now))

    def apply_buff_from_puck(self, now: float):
        """Apply the first buff hit by the puck, based on last hitter."""
        if not self.active_buffs:
            return

        for buff in list(self.active_buffs):
            if not self.puck.circle_rect_collision(buff.rect):
                continue

            hitter = self.puck.last_hit_player
            if hitter is None:
                continue

            actor = self.left_paddle if hitter == "left" else self.right_paddle
            opponent = self.right_paddle if hitter == "left" else self.left_paddle

            if buff.buff_type == "Big Paddle":
                actor.apply_big_paddle()
                self.buff_message = f"{hitter.upper()} got Big Paddle"
            elif buff.buff_type == "Small Paddle":
                opponent.apply_small_paddle()
                self.buff_message = f"{hitter.upper()} triggered Small Paddle"
            elif buff.buff_type == "Speed Boost":
                actor.apply_speed_boost()
                self.buff_message = f"{hitter.upper()} got Speed Boost"
            elif buff.buff_type == "Slow Paddle":
                opponent.apply_slow()
                self.buff_message = f"{hitter.upper()} triggered Slow Paddle"

            self.buff_message_until = now + 1.8
            self.active_buffs.remove(buff)
            break

    def format_countdown(self, seconds_left: float) -> str:
        """Format remaining seconds as M:SS."""
        total = max(0, int(math.ceil(seconds_left)))
        minutes = total // 60
        seconds = total % 60
        return f"{minutes}:{seconds:02d}"

    def handle_goal(self, goal_result: str):
        """Update score, reset field, and process overtime win condition."""
        # Use conceded-side naming to keep scoring direction unambiguous.
        if goal_result == "left_conceded":
            self.right_score += 1
            conceding = "left"
        else:
            self.left_score += 1
            conceding = "right"

        # Sudden-death overtime ends instantly on first goal.
        if self.state == STATE_OVERTIME:
            self.winner = "Left Player" if self.left_score > self.right_score else "Right Player"
            self.state = STATE_GAME_OVER
            return

        self.left_paddle.reset_position(self.left_zone.centerx, self.left_zone.centery, reset_stats=True)
        self.right_paddle.reset_position(self.right_zone.centerx, self.right_zone.centery, reset_stats=True)
        if not self.lock_map_for_overtime:
            self.choose_match_map()
        self.active_buffs = []
        self.pause_server = conceding
        serving_paddle = self.left_paddle if conceding == "left" else self.right_paddle
        self.puck.prepare_for_serve(conceding, serving_paddle)

        now = pygame.time.get_ticks() / 1000.0
        self.pause_end_time = now + GOAL_PAUSE_DURATION
        self.next_buff_spawn_time = self.pause_end_time + self.interval_for_state()
        self.pause_message = "Goal! Resetting arena..."
        self.state = STATE_GOAL_PAUSE

    def update_goal_pause(self):
        """Hold play for a short break, then resume with a stationary puck."""
        now = pygame.time.get_ticks() / 1000.0
        if now < self.pause_end_time:
            return
        # Keep puck stationary for a true serve; first paddle touch decides launch angle.
        self.pause_message = ""
        self.state = STATE_PLAYING

    def update_gameplay(self, dt: float):
        """Run one gameplay tick: input, physics, buffs, and timer."""
        now = pygame.time.get_ticks() / 1000.0
        key_state = pygame.key.get_pressed()

        self.left_paddle.update(key_state, dt)
        self.right_paddle.update(key_state, dt)

        self.puck.handle_paddle_collision(self.left_paddle)
        self.puck.handle_paddle_collision(self.right_paddle)

        goal = self.puck.update(dt, self.goal_top, self.goal_bottom, self.obstacles)
        if goal:
            self.handle_goal(goal)
            return

        if now >= self.next_buff_spawn_time:
            self.spawn_buff(now)
            self.next_buff_spawn_time = now + self.interval_for_state()

        self.apply_buff_from_puck(now)

        if self.state == STATE_PLAYING:
            self.regulation_time_left = max(0, self.regulation_time_left - dt)
            if self.regulation_time_left <= 0:
                if self.left_score == self.right_score:
                    # Keep the exact same map when entering sudden death.
                    self.lock_map_for_overtime = True
                    self.state = STATE_OVERTIME
                    # Keep existing buff(s) when overtime starts; do not reset field state.
                    self.next_buff_spawn_time = now + self.interval_for_state()
                else:
                    self.winner = "Left Player" if self.left_score > self.right_score else "Right Player"
                    self.state = STATE_GAME_OVER

    def draw_arena(self):
        """Draw rink, goals, obstacles, buffs, paddles, and puck."""
        self.screen.fill(HUD_PANEL_BG)
        field_top = TOP_INFO_HEIGHT

        # Subtle vertical gradient background.
        top = (10, 18, 30)
        bottom = (22, 34, 52)
        for y in range(SCREEN_HEIGHT):
            t = y / max(1, SCREEN_HEIGHT - 1)
            color = (
                int(top[0] * (1 - t) + bottom[0] * t),
                int(top[1] * (1 - t) + bottom[1] * t),
                int(top[2] * (1 - t) + bottom[2] * t),
            )
            pygame.draw.line(self.screen, color, (0, field_top + y), (SCREEN_WIDTH, field_top + y))

        zone_layer = pygame.Surface((SCREEN_WIDTH, SCREEN_HEIGHT), pygame.SRCALPHA)
        pygame.draw.rect(zone_layer, (80, 120, 180, 42), self.left_zone, border_radius=8)
        pygame.draw.rect(zone_layer, (180, 92, 92, 42), self.right_zone, border_radius=8)
        self.screen.blit(zone_layer, (0, field_top))

        pygame.draw.rect(self.screen, (95, 125, 165), self.left_zone.move(0, field_top), 2, border_radius=8)
        pygame.draw.rect(self.screen, (165, 105, 105), self.right_zone.move(0, field_top), 2, border_radius=8)

        center_x = SCREEN_WIDTH // 2
        pygame.draw.line(self.screen, LINE_COLOR, (center_x, field_top), (center_x, field_top + SCREEN_HEIGHT), 3)
        pygame.draw.circle(self.screen, (215, 225, 240), (center_x, field_top + SCREEN_HEIGHT // 2), 85, 2)

        # Goal posts (visual only; openings are in the wall between posts).
        pygame.draw.rect(self.screen, GOAL_COLOR, (0, field_top, GOAL_BORDER_THICKNESS, self.goal_top))
        pygame.draw.rect(
            self.screen,
            GOAL_COLOR,
            (0, field_top + self.goal_bottom, GOAL_BORDER_THICKNESS, SCREEN_HEIGHT - self.goal_bottom),
        )
        pygame.draw.rect(
            self.screen,
            GOAL_COLOR,
            (SCREEN_WIDTH - GOAL_BORDER_THICKNESS, field_top, GOAL_BORDER_THICKNESS, self.goal_top),
        )
        pygame.draw.rect(
            self.screen,
            GOAL_COLOR,
            (
                SCREEN_WIDTH - GOAL_BORDER_THICKNESS,
                field_top + self.goal_bottom,
                GOAL_BORDER_THICKNESS,
                SCREEN_HEIGHT - self.goal_bottom,
            ),
        )

        # Goal opening highlights.
        pygame.draw.rect(self.screen, (88, 140, 210), (0, field_top + self.goal_top, 6, GOAL_HEIGHT))
        pygame.draw.rect(self.screen, (210, 105, 105), (SCREEN_WIDTH - 6, field_top + self.goal_top, 6, GOAL_HEIGHT))

        # Timer inside field, near top of center line.
        if self.state == STATE_OVERTIME:
            timer_label = "OVERTIME"
        else:
            timer_label = self.format_countdown(self.regulation_time_left)
        timer_surf = self.small_font.render(timer_label, True, TEXT_COLOR)
        timer_bg = pygame.Rect(0, 0, timer_surf.get_width() + 20, timer_surf.get_height() + 8)
        timer_bg.center = (center_x, field_top + 24)
        pygame.draw.rect(self.screen, (8, 12, 18), timer_bg, border_radius=8)
        pygame.draw.rect(self.screen, (140, 158, 180), timer_bg, 1, border_radius=8)
        self.screen.blit(timer_surf, (timer_bg.x + 10, timer_bg.y + 4))

        for obstacle in self.obstacles:
            obstacle.draw(self.screen, y_offset=field_top)

        for buff in self.active_buffs:
            buff.draw(self.screen, y_offset=field_top)

        self.left_paddle.draw(self.screen, y_offset=field_top)
        self.right_paddle.draw(self.screen, y_offset=field_top)
        self.puck.draw(self.screen, y_offset=field_top)

    def draw_hud(self):
        """Draw bottom info panel: score, map, state, stats, and controls."""
        panel_y = TOP_INFO_HEIGHT + SCREEN_HEIGHT + 10
        panel_h = HUD_PANEL_HEIGHT - 20
        panel = pygame.Surface((SCREEN_WIDTH - 24, panel_h), pygame.SRCALPHA)
        panel.fill((8, 12, 18, 220))
        self.screen.blit(panel, (12, panel_y))
        pygame.draw.rect(self.screen, HUD_PANEL_BORDER, (12, panel_y, SCREEN_WIDTH - 24, panel_h), 1, border_radius=8)

        score_text = self.font.render(f"{self.left_score}  :  {self.right_score}", True, TEXT_COLOR)
        self.screen.blit(score_text, (SCREEN_WIDTH // 2 - score_text.get_width() // 2, panel_y + 8))

        map_text = self.small_font.render(f"Map: {self.map_name}", True, SUBTEXT_COLOR)
        self.screen.blit(map_text, (28, panel_y + 14))

        state_text = self.small_font.render(f"State: {self.state.upper()}", True, SUBTEXT_COLOR)
        self.screen.blit(state_text, (SCREEN_WIDTH - state_text.get_width() - 28, panel_y + 14))

        left_stats = f"L Size:{int(self.left_paddle.radius)} Speed:{int(self.left_paddle.speed)}"
        right_stats = f"R Size:{int(self.right_paddle.radius)} Speed:{int(self.right_paddle.speed)}"
        stat_line = self.small_font.render(f"{left_stats}   |   {right_stats}", True, SUBTEXT_COLOR)
        self.screen.blit(stat_line, (SCREEN_WIDTH // 2 - stat_line.get_width() // 2, panel_y + 46))

        if pygame.time.get_ticks() / 1000.0 <= self.controls_hint_until:
            controls = "Left: W A S D   Right: Arrow Keys   R: Restart   ESC: Quit"
            hint = self.small_font.render(controls, True, SUBTEXT_COLOR)
            self.screen.blit(hint, (SCREEN_WIDTH // 2 - hint.get_width() // 2, panel_y + 72))

    def draw_top_info(self):
        """Draw top panel: buff legend, active count, and recent buff trigger."""
        top_panel = pygame.Surface((SCREEN_WIDTH - 24, TOP_INFO_HEIGHT - 16), pygame.SRCALPHA)
        top_panel.fill((8, 12, 18, 210))
        self.screen.blit(top_panel, (12, 8))
        pygame.draw.rect(self.screen, HUD_PANEL_BORDER, (12, 8, SCREEN_WIDTH - 24, TOP_INFO_HEIGHT - 16), 1, border_radius=8)

        title = self.small_font.render("Buff Legend", True, TEXT_COLOR)
        self.screen.blit(title, (24, 12))

        buff_status = "Buff on field: None"
        if self.active_buffs:
            buff_status = f"Buff on field: {len(self.active_buffs)} active"
        status_text = self.small_font.render(buff_status, True, SUBTEXT_COLOR)
        self.screen.blit(status_text, (SCREEN_WIDTH - status_text.get_width() - 24, 12))

        legend_items = [
            ("Big Paddle", "Big: your size up"),
            ("Small Paddle", "Small: opponent size down"),
            ("Speed Boost", "Speed: your move speed up"),
            ("Slow Paddle", "Slow: opponent speed down"),
        ]
        x = 24
        y = 38
        col_gap = 560
        for idx, (buff_name, text_label) in enumerate(legend_items):
            item_x = x + (idx % 2) * col_gap
            item_y = y + (idx // 2) * 24
            swatch = pygame.Rect(item_x, item_y + 2, 14, 14)
            pygame.draw.rect(self.screen, BUFF_COLORS[buff_name], swatch, border_radius=3)
            pygame.draw.rect(self.screen, (20, 20, 20), swatch, 1, border_radius=3)
            text = self.small_font.render(text_label, True, SUBTEXT_COLOR)
            self.screen.blit(text, (item_x + 20, item_y - 1))

        show_buff_msg = pygame.time.get_ticks() / 1000.0 <= self.buff_message_until and bool(self.buff_message)
        if show_buff_msg:
            msg = self.small_font.render(self.buff_message, True, MENU_HIGHLIGHT_COLOR)
            self.screen.blit(msg, (SCREEN_WIDTH // 2 - msg.get_width() // 2, 82))

    def draw_start_screen(self):
        """Draw interactive start menu over a dimmed arena background."""
        self.draw_arena()
        dim = pygame.Surface((SCREEN_WIDTH, WINDOW_HEIGHT), pygame.SRCALPHA)
        dim.fill((0, 0, 0, 130))
        self.screen.blit(dim, (0, 0))

        title = self.big_font.render("Chaos Air Hockey", True, TEXT_COLOR)
        self.screen.blit(title, (SCREEN_WIDTH // 2 - title.get_width() // 2, 70))

        subtitle = self.small_font.render("Use Up/Down to select, Enter to confirm.", True, SUBTEXT_COLOR)
        self.screen.blit(subtitle, (SCREEN_WIDTH // 2 - subtitle.get_width() // 2, 130))
        helper = self.small_font.render("Press H for full tutorial", True, SUBTEXT_COLOR)
        self.screen.blit(helper, (SCREEN_WIDTH // 2 - helper.get_width() // 2, 156))

        menu_box = pygame.Rect(SCREEN_WIDTH // 2 - 260, 185, 520, 305)
        pygame.draw.rect(self.screen, (12, 18, 28), menu_box, border_radius=10)
        pygame.draw.rect(self.screen, (138, 154, 176), menu_box, 2, border_radius=10)

        option_labels = [
            "Start Match",
            f"Regulation Time: {self.format_countdown(REGULATION_TIME_OPTIONS[self.time_option_index])}",
            f"Map Mode: {MAP_MODE_OPTIONS[self.map_mode_option_index]}",
            f"Puck Speed: {PUCK_SPEED_OPTIONS[self.puck_speed_option_index]}",
            f"Buff Spawn Mode: {BUFF_MODE_OPTIONS[self.buff_mode_option_index][0]}",
            "Quit Game",
        ]

        y = 210
        for idx, line in enumerate(option_labels):
            color = MENU_HIGHLIGHT_COLOR if idx == self.menu_index else TEXT_COLOR
            prefix = "> " if idx == self.menu_index else "  "
            surf = self.font.render(prefix + line, True, color)
            self.screen.blit(surf, (SCREEN_WIDTH // 2 - surf.get_width() // 2, y))
            y += 48

        guide_lines = [
            "How to Play:",
            "Left Player: W A S D",
            "Right Player: Arrow Keys",
            "Hit the puck into the opponent goal to score.",
            "Tie after regulation enters sudden-death overtime.",
            "Buffs trigger only when puck collides with them.",
        ]

        y = 520
        help_box = pygame.Rect(SCREEN_WIDTH // 2 - 330, 500, 660, 190)
        pygame.draw.rect(self.screen, (10, 15, 24), help_box, border_radius=10)
        pygame.draw.rect(self.screen, (120, 138, 160), help_box, 1, border_radius=10)
        for i, line in enumerate(guide_lines):
            color = TEXT_COLOR if i == 0 else SUBTEXT_COLOR
            surf = self.small_font.render(line, True, color)
            self.screen.blit(surf, (SCREEN_WIDTH // 2 - surf.get_width() // 2, y))
            y += 28

    def draw_tutorial_screen(self):
        """Draw full-screen tutorial and controls reference."""
        self.screen.fill((8, 12, 20))

        title = self.big_font.render("Chaos Air Hockey - How To Play", True, TEXT_COLOR)
        self.screen.blit(title, (SCREEN_WIDTH // 2 - title.get_width() // 2, 36))

        lines = [
            "Goal:",
            "Score by hitting the puck into the opponent goal before regulation time ends.",
            "Tie at end of regulation enters sudden-death overtime.",
            "",
            "Controls:",
            "Left Player: W A S D",
            "Right Player: Arrow Keys",
            "R: Restart match   ESC: Back to start menu",
            "",
            "Buff Rules:",
            "Buffs activate only when the puck touches them (not paddles).",
            "The effect target depends on who touched the puck last.",
            "Big Paddle / Small Paddle change paddle size.",
            "Speed Boost / Slow Paddle change paddle movement speed.",
            "",
            "Buff Spawn Mode (Start Menu option):",
            "Standard: buffs every 10s in regulation, 5s in overtime.",
            "Frequent: buffs every 7s in regulation, 4s in overtime.",
            "Chaos: buffs every 5s in regulation, 3s in overtime.",
            "",
            "Map Mode:",
            "Random / Normal / Center Block / Double Block.",
            "",
            "After each goal:",
            "Map resets, game pauses 3 seconds, then serve resumes with a stationary puck.",
            "First paddle touch sets serve direction.",
        ]

        y = 110
        line_gap = 24
        section_gap = 6
        footer_top = WINDOW_HEIGHT - 62
        for line in lines:
            color = TEXT_COLOR if line.endswith(":") else SUBTEXT_COLOR
            if line == "":
                y += section_gap
                continue
            if y > footer_top - line_gap:
                break
            surf = self.small_font.render(line, True, color)
            self.screen.blit(surf, (60, y))
            y += line_gap

        footer_rect = pygame.Rect(0, footer_top, SCREEN_WIDTH, WINDOW_HEIGHT - footer_top)
        pygame.draw.rect(self.screen, (6, 10, 16), footer_rect)
        pygame.draw.line(self.screen, HUD_PANEL_BORDER, (0, footer_top), (SCREEN_WIDTH, footer_top), 1)
        hint = self.font.render("Press N to continue to Start Menu", True, MENU_HIGHLIGHT_COLOR)
        self.screen.blit(hint, (SCREEN_WIDTH // 2 - hint.get_width() // 2, footer_top + 12))

    def draw_goal_pause_overlay(self):
        """Draw 3-second pause overlay after each goal."""
        now = pygame.time.get_ticks() / 1000.0
        remain = max(0, int(math.ceil(self.pause_end_time - now)))
        overlay = pygame.Surface((SCREEN_WIDTH, WINDOW_HEIGHT), pygame.SRCALPHA)
        overlay.fill((0, 0, 0, 110))
        self.screen.blit(overlay, (0, 0))
        field_center_y = TOP_INFO_HEIGHT + SCREEN_HEIGHT // 2

        title = self.big_font.render(self.pause_message or "Goal!", True, TEXT_COLOR)
        self.screen.blit(title, (SCREEN_WIDTH // 2 - title.get_width() // 2, field_center_y - 80))

        countdown = self.font.render(f"Restarting in {remain}", True, MENU_HIGHLIGHT_COLOR)
        self.screen.blit(countdown, (SCREEN_WIDTH // 2 - countdown.get_width() // 2, field_center_y - 24))

        serve_side = "LEFT" if self.pause_server == "left" else "RIGHT"
        serve_text = self.small_font.render(f"{serve_side} side serves next", True, SUBTEXT_COLOR)
        self.screen.blit(serve_text, (SCREEN_WIDTH // 2 - serve_text.get_width() // 2, field_center_y + 22))

    def draw_game_over_screen(self):
        """Draw end-of-match overlay and restart hint."""
        self.draw_arena()
        overlay = pygame.Surface((SCREEN_WIDTH, WINDOW_HEIGHT), pygame.SRCALPHA)
        overlay.fill((0, 0, 0, 150))
        self.screen.blit(overlay, (0, 0))

        title = self.big_font.render("Game Over", True, TEXT_COLOR)
        self.screen.blit(title, (SCREEN_WIDTH // 2 - title.get_width() // 2, 180))

        winner = self.font.render(f"Winner: {self.winner}", True, TEXT_COLOR)
        self.screen.blit(winner, (SCREEN_WIDTH // 2 - winner.get_width() // 2, 260))

        final_score = self.font.render(f"Final Score {self.left_score} : {self.right_score}", True, SUBTEXT_COLOR)
        self.screen.blit(final_score, (SCREEN_WIDTH // 2 - final_score.get_width() // 2, 312))

        hint = self.small_font.render("Press R to restart or ESC to quit.", True, SUBTEXT_COLOR)
        self.screen.blit(hint, (SCREEN_WIDTH // 2 - hint.get_width() // 2, 364))

    def handle_events(self):
        """Handle quit, menu navigation, and in-game control shortcuts."""
        for event in pygame.event.get():
            if event.type == pygame.QUIT:
                self.running = False
            elif event.type == pygame.KEYDOWN:
                if event.key == pygame.K_ESCAPE:
                    if self.state in (STATE_START, STATE_TUTORIAL):
                        self.running = False
                    else:
                        self.state = STATE_START
                        self.pause_message = ""
                        self.active_buffs = []
                        self.buff_message = ""
                elif self.state == STATE_TUTORIAL:
                    if event.key == pygame.K_n:
                        self.state = STATE_START
                elif self.state == STATE_START:
                    if event.key in (pygame.K_UP, pygame.K_w):
                        self.menu_index = (self.menu_index - 1) % len(self.menu_actions)
                    elif event.key in (pygame.K_DOWN, pygame.K_s):
                        self.menu_index = (self.menu_index + 1) % len(self.menu_actions)
                    elif event.key in (pygame.K_RETURN, pygame.K_KP_ENTER):
                        self.activate_start_menu_option()
                    elif event.key == pygame.K_h:
                        self.state = STATE_TUTORIAL
                elif self.state == STATE_GAME_OVER and event.key in (pygame.K_r, pygame.K_RETURN, pygame.K_SPACE):
                    self.start_new_match()
                elif self.state in (STATE_PLAYING, STATE_OVERTIME, STATE_GOAL_PAUSE) and event.key == pygame.K_r:
                    self.start_new_match()

    def run(self):
        """Main loop: process events, update state, render frame."""
        while self.running:
            self.clock.tick(FPS)
            now = pygame.time.get_ticks() / 1000.0
            dt = min(0.05, max(0.0, now - self.last_frame_time))
            self.last_frame_time = now
            self.handle_events()

            if self.state in (STATE_PLAYING, STATE_OVERTIME):
                self.update_gameplay(dt)
                self.draw_arena()
                self.draw_top_info()
                self.draw_hud()
            elif self.state == STATE_GOAL_PAUSE:
                self.update_goal_pause()
                self.draw_arena()
                self.draw_top_info()
                self.draw_hud()
                self.draw_goal_pause_overlay()
            elif self.state == STATE_START:
                self.draw_start_screen()
            elif self.state == STATE_TUTORIAL:
                self.draw_tutorial_screen()
            elif self.state == STATE_GAME_OVER:
                self.draw_game_over_screen()

            pygame.display.flip()

        pygame.quit()



def main():
    """Program entry point."""
    game = Game()
    game.run()


if __name__ == "__main__":
    main()
