package gateway;

import entity.*;
import entity.event.*;
import redis.clients.jedis.Jedis;
import usecase.EventManager;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

/**
 * @program: group_0173
 * @description: GatewayCli is an external Command line interface of Gateway in the purpose of debugging, displaying database and operating database.
 * @create: 2020-12-03 22:18
 **/
public class GatewayCli extends Gateway{

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_CYAN = "\u001B[36m";

    public static void main(String[] args) {
        GatewayFacade gatewayFacade = new GatewayFacade();
        Scanner scan = new Scanner(System.in);
        String input = "";
        GatewayCli gcli = new GatewayCli();
        while (!input.equals("0")) {
            System.out.println(ANSI_CYAN + "---------- ---------- ---------- ---------- ---------- ----------");
            System.out.println("Gateway: Welcome to Gateway CLI" + ANSI_GREEN );
            System.out.println("1. Display all data");
            System.out.println("2. Check gateway errors");
            System.out.println("3. Format Database");
            System.out.println("4. Create new Organizer");
            System.out.println("0. Exit");
            input = scan.nextLine();
            switch (input) {
                case "1":
                    gcli.printDataBase();
                    break;
                case "2":
                    gcli.testCases();
                    break;
                case "3":
                    gcli.rmrf();
                    break;
                case "4":
                    gcli.createOrganizer(gatewayFacade);
                case "0":
                    System.out.println("Gateway: CLI exit");
                    break;
                default:
                    System.out.println("Gateway: Please type correct operation number");
            }
        }
    }

    /** Create a new organizer*/
    private void createOrganizer(GatewayFacade gatewayFacade) {
        Scanner scan = new Scanner(System.in);
        System.out.println("User Name:");
        String username = scan.nextLine();
        System.out.println("Password:");
        String password = scan.nextLine();
        Organizer o = new Organizer(gatewayFacade.getNextUserId(), password, username);
        gatewayFacade.addUser(o);
        System.out.println(ANSI_RED + "Organizer" + o.getUserName() + "has been created" + ANSI_RESET);
    }

    /** Format Database*/
    private void rmrf() {
        Jedis jedis = getJedis();
        Scanner scan = new Scanner(System.in);
        System.out.println(ANSI_RED + "Gateway: Warning! Are you sure want to FORMAT Database! (N/Y)" + ANSI_RESET);
        String input = scan.nextLine();
        if (input.equals("Y")){
            System.out.println("Gateway: FORMATTING Database ...");
            System.out.print("Process:");
            jedis.del(Config.NEXT_USER_ID);
            System.out.print("**");
            jedis.del(Config.NEXT_EVENT_ID);
            System.out.print("**");
            jedis.del(Config.NEXT_ROOM_ID);
            System.out.print("**");
            jedis.del(Config.USER_HASH);
            System.out.print("**");
            jedis.del(Config.EVENT_HASH);
            System.out.print("**");
            jedis.del(Config.ROOM_HASH);
            System.out.print("**");
            jedis.del(Config.MESSAGE_LIST);
            System.out.print("**");
            System.out.println("\nGateway: Database has been formatted");
        } else {
            System.out.println("Gateway: Format operation cancelled");
        }
        closeJedis(jedis);
    }

    /** This method is used for test    */
    public void printDataBase () {
        GatewayFacade gf = new GatewayFacade();
        Jedis jedis = getJedis();
        System.out.println(ANSI_CYAN + "DataBase: Displaying DATA:");
        System.out.println("---------- ---------- ---------- ---------- ---------- ----------" + ANSI_GREEN);
        System.out.println("+ UserNextId: " + jedis.get(Config.NEXT_USER_ID));
        System.out.println("+ EventNextId: " + jedis.get(Config.NEXT_EVENT_ID));
        System.out.println("+ RoomNextId: " + jedis.get(Config.NEXT_ROOM_ID));
        System.out.println("+ User List");
        gf.getUserList().forEach((u) -> System.out.println("   - " + u.toString()));
        System.out.println("+ Event List");
        gf.getEventList().forEach((e) -> System.out.println("  - " + e.toString()));
        System.out.println("+ Room List");
        gf.getRoomList().forEach((r) -> System.out.println("   - " + r.toString()));
        System.out.println("+ Message List");
        gf.getMessageList().forEach((m) -> System.out.println("    - " + m.toString()));

        jedis.close();
    }

    /** Enable '-ea' in VM option in config before testing */
    private void testCases () {
        System.out.println(ANSI_RED + "Please Enable '-ea' in VM option in configuration before testing" + ANSI_RESET);
        System.out.println("Gateway: Testing...");
        GatewayFacade gf = new GatewayFacade();
        testUser (gf);
        testEvent (gf);
        testRoom (gf);
        testMessage(gf);
        System.out.println("\nGateway: All tests passed");
    }

    /** All test case */
    private void testUser (GatewayFacade gf) {
        User u1 = new Attendee(998, "114514", "testJIMA");
        User u2 = new Organizer(999, "234234", "testJim2");
        gf.addUser(u1);
        gf.addUser(u2);
        assert (gf.getUserById(998).getUserName().equals("testJIMA"));
        assert (gf.getOrganizerById(998) == null);
        assert (gf.getOrganizerById(999).getUserName().equals("testJim2"));
        assert (gf.getUserList().get(999).getClass().equals(Organizer.class));
        gf.deleteUser(u1);
        gf.deleteUser(u2);
        System.out.print("**");
    }

    /**
     * @Description: test event
     */
    private void testEvent (GatewayFacade gf) {
        EventManager em = new EventManager();
        LocalDateTime sTime = LocalDateTime.parse("2020-11-14 18:00", em.getTimeFormatter());
        LocalDateTime eTime = LocalDateTime.parse("2020-11-14 20:00", em.getTimeFormatter());

        NonSpeakerEvent e1 = new Party(sTime, eTime, 998, "test event", 250, 200);
        OneSpeakerEvent e2 = new Talk(sTime, eTime, 999, "test event222", 255, 400);
        e2.setSpeaker(14);
        gf.addEvent(e1);
        gf.addEvent(e2);
        assert (gf.getEventById(998).getTitle().equals("test event"));
        assert (gf.getEventList().get(999).getCapacity() == 400);
        assert (gf.getEventById(10000) == null);
        gf.deleteEvent(e1);
        gf.deleteEvent(e2);
        System.out.print("**");
    }

    /**
     * @Description: test room
     */
    private void testRoom (GatewayFacade gf) {
        Room r1 = new Room("123313", 999, 10);
        gf.addRoom(r1);
        assert (gf.getRoomById(999).getRoomNum().equals("123313"));
        assert (gf.getRoomByRoomNum("123313").getRid() == 999);
        gf.deleteRoom(r1);
        System.out.print("**");
    }


    /**
     * @Description: test message
     */
    private void testMessage (GatewayFacade gf) {
        boolean check = false;
        List<Message> messageList = gf.getSentMessageListByUserId(999);
        for (Message message : messageList) {
            check = (message.getInfo().equals("Hello Message") || check);
        }
        if (!check) {
            Message m = new Message("Hello Message", "info", 999, 998);
            gf.addMessage(m);
        }
        messageList = gf.getSentMessageListByUserId(999);
        check = false;
        for (Message message : messageList) {
            check = (message.getInfo().equals("Hello Message") || check);
        }
        System.out.print("**");
    }
}