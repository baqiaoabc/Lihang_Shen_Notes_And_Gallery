import java.io.Console;
import java.sql.SQLOutput;
import java.util.List;
import java.util.Scanner;


/**
 * The View class that interacts with the user.
 */
public class ViewModel implements ViewActions {
    /**
     * @description: Prints greeting statement
     * @param: []
     * @return: void
     * @date: 2020-11-14
     */

    public void SignUpSuccess(){
        System.out.println("Hey! You have created an account!");
    }
    @Override
    public void Introduction() {

        System.out.println("Welcome to the 0173 Conference!");
    }
    /**
     * @description: Asks user if he/she/they have registered or not
     * @param: []
     * @return: returns 1 if user has already registered
     * returns 0 if not
     * @date: 2020-11-14
     */

    @Override
    public int AreURegistered() {
        Scanner scanner = new Scanner (System.in);
        System.out.println("Do you have an account? Type: Yes/No");
        String n = scanner.nextLine();
        if (n.equals("Yes")) {
            return 1;
        } else if (n.equals("No")) {
            return 0;
        }else{
            System.out.println("Invalid input! Please Type: Yes/No");
            return -1;
        }
    }
    /**
     * @description: Interacts with the user and takes its username from the console
     * @param: []
     * @return: returns user's username
     * @date: 2020-11-14
     */

    @Override
    public String username() {
        Scanner scanner = new Scanner (System.in);
        System.out.println("Enter your username here:");
        String username = scanner.nextLine();
        return username;
    }
    /**
     * @description: Interacts with the user and takes its password from the console
     * @param: []
     * @return: returns user's password
     * @date: 2020-11-14
     */


    @Override
    public String Pass() {
        Scanner scanner = new Scanner (System.in);
        System.out.println("Enter your password here(at least 6 characters): ");
        String pass = scanner.nextLine();//converting char array into string
        return pass;
    }
    /**
     * @description: Interacts with the user to sign in
     * @param: []
     * @return: returns the String[username, password]
     * @date: 2020-11-14
     */

    @Override
    public String[] SignIn(){
        Scanner scanner = new Scanner (System.in);
        System.out.println("To sign in enter your username here:");
        String n = scanner.nextLine();
        System.out.println("To sign in enter your password here:");
        String k =  scanner.nextLine();
        return new String[]{n, k};
    }

    /**
     * @description: Greets the user if user successfully log in
     * @param username  User name of the user attempting to log in
     * @return: void
     * @date: 2020-11-14
     */
    @Override
    public void Credentials(String username) {
        System.out.println("Welcome " + username + "!");
    }

    /**
     * @description: Generates the message if user failed to log in
     * @param: []
     * @return: void
     * @date: 2020-11-14
     */
    @Override
    public void CredentialsFail(){
        System.out.println("Oops, something went wrong. Please try again");
    }
    /**
     * @description: Interacts with the user to open a messenger
     * @param: []
     * @return returns 1 if user wants to send a message
     * returns 2 if user want to view messages
     * returns 3 if user want to reply to a message
     * @date: 2020-11-14
     */

    public int Messenger(){
        System.out.println("Welcome to Messenger!");
        System.out.println("Type 1 If you want to send a message: ");
        System.out.println("Type 2 If you want to view the messages you received : ");
        System.out.println("Type 3 If you want to view the messages you have sent'");
        System.out.println("Type 4 If you want to reply to a message");
        exit();
        return answer();
    }

    private int answer(){
        int i = 0;
        Scanner scanner = new Scanner (System.in);
        String answer = scanner.nextLine();
        try{
            i = Integer.parseInt(answer);
        }catch (Exception e){
            System.out.println("Remember! WE NEED A NUMBER!!!!");
        }
        return i;
    }


    /**
     * @description: Interacts with the Attendee to open a messenger
     * @param: []
     * @return returns 1 if user wants to send a message to attendee
     * returns 2 if user wants to send a message to a speaker
     * @date: 2020-11-14
     */


    @Override
    public int MessengerAttendee() {
        System.out.println("Type 1 If you want to send a message to an attendee: ");
        System.out.println("TYpe 2 If you want to send a message to a speaker: ");
        exit();
        return answer();

    }
    /**
     * @description: Interacts with the Organizer to open a messenger
     * @param: []
     * @return returns 1 if user wants to send a message to attendee
     * returns 2 if user wants to send a message to a speaker
     * returns 3 if user wants to send a message to all attendees
     * returns 4 if user want to send a message to all speakers
     * @date: 2020-11-14
     */

    @Override
    public int MessengerOrganizer() {
        System.out.println("Type 1 If you want to send a message to an attendee: ");
        System.out.println("TYpe 2 If you want to send a message to a speaker: ");
        System.out.println("Type 3 If you want to send a message to all attendees: ");
        System.out.println("Type 4 If you want to send a message to all speakers: ");
        exit();
        return answer();
    }
    /**
     * @description: Interacts with the Speaker to open a messenger
     * @param: []
     * @return returns 1 if user wants to send a message to all attendees of one event
     * returns 2 if user wants to send a message to all attendees of all speaker's events
     * @date: 2020-11-14
     */

    @Override
    public int MessengerSpeaker() {
        System.out.println("Type 1 If you want to send a message to all attendees in one event: '");
        System.out.println("Type 2 If you want to send a message to all attendees in all your events: ");
        System.out.println("Type 3 If you want to send a specific user in your events: ");
        exit();
        return answer();
    }

    private void exit(){
        System.out.println("Type other numbers if you want to return to the previous page: ");
    }
    /**
     * @description: Interacts with the user to go back to a previous page
     * @param: []
     * @return returns answer from the console
     * @date: 2020-11-14
     */

    public String back(){
        System.out.println("Type any thing to go back to previous page");
        Scanner scanner = new Scanner (System.in);
        String answer =scanner.nextLine();
        return answer;
    }

    /**
     * @description: Interacts with the user to open a menu
     * @param: []
     * @return returns 1 if user wants to open the Messenger
     * returns 2 if user wants to to browse all the events in the conference
     * return 3 if user wants to see the schedule
     * returns 4 if user wants to see what events are available to sign up
     * returns 5 if user wants to sign up for a new event
     * returns 6 if user wants to cancel an event
     * returns 7 if user wants to change password
     * returns 8 if user wants to sign out
     * @date: 2020-11-14
     */



    @Override
    public int AttendeeMenu() {
        System.out.println("Welcome to the menu!");
        System.out.println("Type 1 if you want to open the Messenger: ");
        System.out.println("Type 2 if you want to browse all the events in the conference: ");
        System.out.println("Type 3 if you want to see your schedule: ");
        System.out.println("Type 4 if you want to see what events you can sign up now: ");
        System.out.println("Type 5 if you want to sign up for a new event: ");
        System.out.println("Type 6 if you want to cancel an event: ");
        System.out.println("Type 7 if you want to change password");
        System.out.println("Type 8 if you want to sign out");
        return answer();
    }

    /**
     * @description: Interacts with the Organizer to open an Organizer menu
     * @param: []
     * @return returns 1 if user wants to open the Messenger
     * returns 2 if user wants to create a new room
     * return 3 if user want to create a new speaker
     * returns 4 if user want to assign a speaker to an event
     * returns 5 if user want to create a new event
     * return 6 if the user wants to view all events the user signs up
     * returns 7 if the user wants to sign up to a new event
     * returns 8 if the user wants to view all the events the user has organized
     * returns 9 if the user wants to change password
     * returns 10 if the user wants to sign out
     * @date: 2020-11-14
     */

    @Override
    public int OrganizerMenu() {
        System.out.println("Welcome to the menu!");
        System.out.println("Type 1 if you want to open the Messenger: ");
        System.out.println("Type 2 if you want to create a new room: ");
        System.out.println("Type 3 if you want to create a new speaker: ");
        System.out.println("Type 4 if you want to assign a speaker to an event: ");
        System.out.println("Type 5 if you want to create a new event: ");
        System.out.println("Type 6 if you want to view all events you sign up; ");
        System.out.println("Type 7 if you want to sign up to a new event");
        System.out.println("Type 8 if you want to view all the events you have organized; ");
        System.out.println("Type 9 if you want to change password");
        System.out.println("Type 10 if you want to sign out");
        return answer();
    }

    /**
     * @description: Interacts with the Speaker to open a Speaker menu
     * @param: []
     * @return returns 1 if user wants to open the Messenger
     * returns 2 if user wants to see a list of talks Speaker gives
     * returns 3 if user wants to change password
     * returns 4 if user wants to sign out
     * @date: 2020-11-14
     */

    @Override
    public int SpeakerMenu() {
        System.out.println("Welcome to the menu!");
        System.out.println("Type 1 if you want to open the Messenger: ");
        System.out.println("Type 2 if you want to see a list of talks you are given: ");
        System.out.println("Type 3 if you want to change password");
        System.out.println("Type 4 if you want to sign out");
        return answer();
    }

    /**
     * @description: Generates a message if user can't reply to a message
     * @param: []
     * @return void
     * @date: 2020-11-14
     */

    @Override
    public void SorryMessenger() {
        System.out.println("Sorry! You can't reply to a message, since no one can send a message to you!");
    }

    /**
     * @description: Generates a message if user signed up for a new event
     * @param: []
     * @return void
     * @date: 2020-11-14
     */

    @Override
    public void SignUpEventMessage() {
        System.out.println("Hey! You just sign up a new event! Arrive on time! ");
    }

    /**
     * @description: Print all the messages a user received;
     * @param: []
     * @return void
     * @date: 2020-11-14
     */

    @Override
    public void readReceivedMessages(){
        System.out.println("Here are the messages you received!");
    }

    /**
     * @description: Print all the messages a user has sent;
     * @param: []
     * @return void
     * @date: 2020-11-15
     */
    @Override
    public void  readSentMessages(){
        System.out.println("Here are the messages you sent");
    }

    /**
     * @description: Generates a message if user failed to sign up for an event
     * @param: []
     * @return void
     * @date: 2020-11-14
     */

    @Override
    public void SignUpEventErr() {
        System.out.println("Sorry! You can't sign up for the event. Please check again!");
    }

    /**
     * @description: Generates a message if user made a new room
     * @param: []
     * @return void
     * @date: 2020-11-14
     */

    @Override
    public void AddNewRoom() {
        System.out.println("Hey! You just make a new room");
    }

    /**
     * @description: Generates a message if user failed to add a new room
     * @param: []
     * @return void
     * @date: 2020-11-14
     */

    @Override
    public void AddNewRoomErr() {
        System.out.println("Sorry! We can not add the room. Please check again!");
    }

    /**
     * @description: Generates a message if user created a new speaker
     * @param: []
     * @return void
     * @date: 2020-11-14
     */

    @Override
    public void SpeakerAccCreated() {
        System.out.println("Hey! You just make a new speaker account!");

    }
    /**
     * @description: Generates a message if user fails to create a new speaker
     * @param: []
     * @return void
     * @date: 2020-11-14
     */


    @Override
    public void SpeakerAccCreatedErr() {
        System.out.println("Sorry! We can not add the speaker. Please check again!");
    }

    /**
     * @description: Generates a message if user set a new speaker
     * @param: []
     * @return void
     * @date: 2020-11-14
     */

    @Override
    public void SpeakerSet() {
        System.out.println("Hey! You just make set a speaker for event!");

    }
    /**
     * @description: Generates a message if user fails to set a new speaker
     * @param: []
     * @return void
     * @date: 2020-11-14
     */

    @Override
    public void SpeakerSetErr() {
        System.out.println("Sorry! We can not set the speaker for the event. Please check again!");
    }

    /**
     * @description: Generates a message if user canceled an event
     * @param: []
     * @return void
     * @date: 2020-11-14
     */

    @Override
    public void CancelEventMessage() {
        System.out.println("You have just canceled an amazing event!");

    }

    /**
     * @description: Generates an error message
     * @param: []
     * @return void
     * @date: 2020-11-14
     */

    @Override
    public void MessageErr() {
        System.out.println("Oops, something went wrong! Please check again!");

    }
    /**
     * @description: Generates a message if user's message was sent successfully
     * @param: []
     * @return void
     * @date: 2020-11-14
     */

    @Override
    public void MessageSuccess() {
        System.out.println("The operation has done successfully! You got it!");

    }


    /**
     * @description: Generates a message if the organizer creates an event successfully.
     * @param: []
     * @return void
     * @date: 2020-11-14
     */
    @Override
    public void eventSuccess(){
        System.out.println("Hey! You just create a new event!");
    }

    /**
     * @description: Generates a message if user put an invalid input
     * @param: []
     * @return void
     * @date: 2020-11-14
     */
    @Override
    public void invalidInput(){
        System.out.println("Hey! That's an invalid input! please try again!");
    }

    /**
     * @description: Interacts with the user who wants to sign up for a new event
     * @param: []
     * @return returns the event's id
     * @date: 2020-11-14
     */

    @Override
    public String SignUpEvent(){
        Scanner scanner = new Scanner (System.in);
        System.out.println("Enter the event id you would like to attend");
        String eventID = scanner.nextLine();
        return eventID;
    }

    /**
     * @description: Interacts with the user who wants to add a new room
     * @param: []
     * @return returns the room's name
     * @date: 2020-11-14
     */

    @Override
    public String addRoom(){
        Scanner scanner = new Scanner (System.in);
        System.out.println("Enter the room name you want to add");
        String room = scanner.nextLine();
        return room;
    }

    /**
     * @description: Interacts with the user who wants to add a new speaker
     * @param: []
     * @return returns the String[speaker's name, speaker's password] from the console
     * @date: 2020-11-14
     */

    @Override
    public String[]  speakerAdder(){
        Scanner scanner = new Scanner (System.in);
        System.out.println("Enter the username of the speaker you want to add: ");
        String speaker_name = scanner.nextLine();
        System.out.println("Enter the password of the speaker you want to add: ");
        String speaker_pass = scanner.nextLine();
        String[] l = new String[]{speaker_name, speaker_pass};
        return l;
    }
    /**
     * @description: Interacts with the user who wants to set a new speaker
     * @param: []
     * @return returns the String[speaker's id, event's id] from the console
     * @date: 2020-11-14
     */


    @Override
    public String[] setSpeakerEvent(){
        Scanner scanner = new Scanner (System.in);
        System.out.println("Enter the ID of the speaker you want to set for an event: ");
        String speaker_id = scanner.nextLine();
        System.out.println("Enter the event ID you want to set the speaker for: ");
        String event_id = scanner.nextLine();
        String[] l = {speaker_id, event_id};
        return l;
    }
    /**
     * @description: Interacts with the user who wants to cancel an event
     * @param: []
     * @return returns the event's id from the console
     * @date: 2020-11-14
     */
    @Override
    public String cancelEnrollment(){
        Scanner scanner = new Scanner (System.in);
        System.out.println("Enter the ID of the event you want to cancel: ");
        String event_id = scanner.nextLine();
        return event_id;
    }

    /**
     * @description: Interacts with the user who wants to cancel an event
     * @param: []
     * @return returns the event's id from the console
     * @date: 2020-11-14
     */

    private String AskForUser(){
        Scanner scanner = new Scanner (System.in);
        System.out.println("What is the ID of the User you want to send message to?");
        String user_id = scanner.nextLine();
        return user_id;
    }
    /**
     * @description: Interacts with the user who wants to send a message to the attendee of the event
     * @param: []
     * @return returns the event's id from the console
     * @date: 2020-11-14
     */
    private String AskForEvent(){
        Scanner scanner = new Scanner (System.in);
        System.out.println("What is the ID of the event the user you want to message to is in?");
        String event_id = scanner.nextLine();
        return event_id;
    }

    /**
     * @description: Interacts with the user who wants to send a message
     * @param: []
     * @return returns the message that user typed
     * @date: 2020-11-14
     */

    private String AskForMessage(){
        Scanner scanner = new Scanner (System.in);
        System.out.println("Type what you want to send there");
        String message = scanner.nextLine();
        return message;
    }
    /**
     * @description: Interacts with the user who wants to reply to a message
     * @param: []
     * @return returns the String[the number of the message, reply]
     * @date: 2020-11-14
     */
    @Override
    public String[] replyMessage(List<String> receive_message){
        int i = 0;
        for (String m: receive_message){
            System.out.println("Message" + i + ": " + m);
            i = i + 1;
        }
        Scanner scanner = new Scanner (System.in);
        System.out.println("Enter the number of the message you want to reply here: ");
        String num = scanner.nextLine();
        System.out.println("Enter what you want to reply here: ");
        String reply = scanner.nextLine();
        String[] l = {num, reply};
        return l;
    }
    /**
     * @description: Interacts with the user who who wants to send a message to all attendee of the event
     * @param: []
     * @return returns the the String[event's id, message]
     * @date: 2020-11-14
     */
    @Override
    public String[] MessageAllAttendeeEvent(){
        String event_id = AskForEvent();
        String message = AskForMessage();
        String[] l = {event_id, message};
        return l;
    }
    /**
     * @description: Interacts with the user who who wants to send a message to all attendees
     * @param: []
     * @return returns the message
     * @date: 2020-11-14
     */

    @Override
    public String messageAllUsersInAllSpeakingEvents(){
        String message = AskForMessage();
        return message;
    }
    /**
     * @description: Interacts with the user who who wants to send a message to one attendee of the event
     * @param: []
     * @return returns String[event's id, user's id, message]
     * @date: 2020-11-14
     */
    @Override
    public String[] messageOneSpecificUserInEvent(){
        String event_id = AskForEvent();
        String user_id = AskForUser();
        String message = AskForMessage();
        String[] l = {event_id, user_id, message};
        return l;
    }
    /**
     * @description: Interacts with the user who wants to send a message to all speakers of the event
     * @param: []
     * @return returns String[event's id, user's id, message]
     * @date: 2020-11-14
     */
    @Override
    public String messageAllSpeakers(){
        String message = AskForMessage();
        return message;
    }
    /**
     * @description: Interacts with the user who wants to send a message to one speaker
     * @param: []
     * @return returns String[user's id, message]
     * @date: 2020-11-14
     */
    @Override
    public String[] messageSpeaker(){
        String user_id = AskForUser();
        String message = AskForMessage();
        String[] l = {user_id,message};
        return l;
    }
    /**
     * @description: Interacts with the user who wants to send a message to all attendees
     * @param: []
     * @return returns message
     * @date: 2020-11-14
     */
    @Override
    public String messageAllAttendee(){
        String message = AskForMessage();
        return message;
    }
    /**
     * @description: Interacts with the user who wants to send a message to one attendee
     * @param: []
     * @return returns String[user's id, message]
     * @date: 2020-11-14
     */

    @Override
    public String[] messageAttendee() {
        String n = AskForUser();
        String k = AskForMessage();
        String[] l = new String[]{n, k};
        return l;
    }
    /**
     * @description: Interacts with the user who wants to set a time for the event
     * @param: []
     * @return returns time in format YYYY--MM--DD HH:MM
     * @date: 2020-11-14
     */
    private String AskStartTime(){
        Scanner scanner = new Scanner (System.in);
        System.out.println("When does the event start at? Format: YYYY-MM-DD HH");
        String start = scanner.nextLine();
        return start;
    }
    /**
     * @description: Interacts with the user who wants to set a title of the event
     * @param: []
     * @return returns title
     * @date: 2020-11-14
     */

    private String AskTopic(){
        Scanner scanner = new Scanner (System.in);
        System.out.println("What is the topic of the event");
        String topic = scanner.nextLine();
        return topic;
    }
    /**
     * @description: Interacts with the user who wants to create a new event
     * @param: []
     * @return returns String[start time, user's id, title, room number]
     * @date: 2020-11-14
     */

    private String AskForSpeaker() {
        Scanner scanner = new Scanner (System.in);
        System.out.println("What's the Speaker id you want to set to this event?");
        String user_id = scanner.nextLine();
        return user_id;
    }



    /**
     * @description: ask the organizer to create a new event
     * @param: String[]
     * @return void
     * @date: 2020-11-15
     */
    @Override
    public String[] newEvent(){
        String start = this.AskStartTime();
        String user_id = this.AskForSpeaker();
        String topic = this.AskTopic();
        Scanner scanner = new Scanner (System.in);
        System.out.println("What is the room number where the event takes place?");
        String room = scanner.nextLine();
        String[] l = {start, user_id, topic, room};
        return l;
    }


    /**
     * @description: Prints a statement to show all elements in the given list.
     * @param: []
     * @return void
     * @date: 2020-11-15
     */
    @Override
    public void StringPrinter(List<String> events){
        for (String e: events){
            System.out.println(e);
        }
    }

    /**
     * @description: Prints a statement to show all attendees.
     * @param: []
     * @return void
     * @date: 2020-11-14
     */
    @Override
    public void viewAllAttendees(){
        System.out.println("Here is all the attendees in the conference! Format: in UserName (userID)");
    }




    /**
     * @description: Prints a statement to show all rooms.
     * @param: []
     * @return void
     * @date: 2020-11-15
     */
    @Override
    public void viewAllRooms() {
        System.out.println("Here are all rooms (with format room number):");
    }

    /**
     * @description: Prints a statement to show all speakers.
     * @param: []
     * @return void
     * @date: 2020-11-14
     */
    @Override
    public void  viewAllSpeakers(){
        System.out.println("Here is all the speakers in the conference! Format: in UserName (userID)");
    }

    /**
     * @description: Prints a statement to the attendees who signed up for at one talks of a speaker .
     * @param: []
     * @return void
     * @date: 2020-11-14
     */
    @Override
    public void viewAttendeesInSpeakingEvents(){
        System.out.println("Here is all the attendees who signed up for your talks! Format: in UserName (userID)\"");
    }

    /**
     * @description: reset password for a user.
     * @param: []
     * @return void
     * @date: 2020-11-14
     */
    @Override
    public String resetPassword(){
        Scanner scanner = new Scanner (System.in);
        System.out.println("What's the new password (at least 6 characters)");
        String pass = scanner.nextLine();
        return pass;
    }


    /**
     * @description: tell user you make a new password.
     * @param: []
     * @return void
     * @date: 2020-11-14
     */
    @Override
    public void passSuccess(){
        System.out.println("Look! You have a new password.");
    }


    /**
     * @description: Tell user that she/he signs out.
     * @param: []
     * @return void
     * @date: 2020-11-14
     */
    @Override
    public void signOutMessage(){
        System.out.println("Hope to se you again!");
    }


}
