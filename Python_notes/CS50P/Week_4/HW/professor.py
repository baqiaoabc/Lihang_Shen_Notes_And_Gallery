import random


def main():
    n = get_level()
    count = 10
    while count > 0:
        x = generate_integer(n)
        y = generate_integer(n)
        for i in range(3):
            try:
                ans = int(input(f"{x} + {y} = "))
                if x + y != ans:
                    raise ValueError
                count -= 1
                break
            except ValueError:
                if i < 2:
                    print("EEE")
                else:
                    print("EEE")
                    print(f"{x} + {y} = {x+y}")


def get_level():
    while True:
        try:
            level = int(input("Level: "))
            if level in [1, 2, 3]:
                return level
        except ValueError:
            pass


def generate_integer(n):
    if n == 1:
        return random.randint(0, 9)
    elif n == 2:
        return random.randint(10, 99)
    else:
        return random.randint(100, 999)


if __name__ == "__main__":
    main()
