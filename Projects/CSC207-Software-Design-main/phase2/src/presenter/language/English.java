package presenter.language;

public class English implements Language {
    @Override
    public String username(){
        return ("Username:");
    }

    @Override
    public String password(){
        return ("Password: ");
    }

    @Override
    public String signUpAccount(){
        return ("Sign up for free now!");
    }

    @Override
    public String ok(){
        return ("OK");
    }

    @Override
    public String exit(){
        return ("Exit");
    }

    @Override
    public String titleLogin(){
        return ("Welcome to the Best Conference in the World! ");
    }

    @Override
    public String back() {
        return ("Back");
    }

    @Override
    public String titleSignUp() {
        return ("You are signing up!");
    }

    @Override
    public String language(){
        return ("English");
    }

    @Override
    public String selectLanguage(){
        return("Select your language! ");
    }

    @Override
    public String fail() {
        return ("Oops. It looks like something went wrong! Try again!");
    }

    @Override
    public String messageTitle() {
        return ("Message");
    }

    @Override
    public String createAccountMessage() {
        return ("Hey! You have created an account!");
    }

    @Override
    public String succeedAddRoomMessage() {
        return "New Room has been created successfully!";
    }

    @Override
    public String successful() {
        return "Successful";
    }

    @Override
    public String failed() {
        return "Failed";
    }

    @Override
    public String failAddRoomMessage() {
        return "The room already exists or your input is valid";
    }

    @Override
    public String succeedCancelEventMessage() {
        return "Whoops, the event has been canceled successfully";
    }

    @Override
    public String failCancelEventMessage() {
        return "It seems that you cannot cancel the event";
    }

    @Override
    public String succeedSignUpEventMessage() {
        return "You have signed up for this event!";
    }

    @Override
    public String failSignUpEvent() {
        return "It seems that you cannot sign up for this event";
    }

    @Override
    public String succeedWaitEvent() {
        return "You have signed up for the waiting";
    }

    @Override
    public String failWaitEventMessage() {
        return "You have signed up for the waiting list of this event!";
    }

    @Override
    public String succeedChangeEventMessage() {
        return "You have changed the setting of this event!";
    }

    @Override
    public String failChangeEventMessage() {
        return "It seems that you cannot change the setting of this event";
    }

    @Override
    public String succeedCreateUserMessage() {
        return "You have created a user!";
    }

    @Override
    public String failCreateUserMessage() {
        return "You fail to create a user!";
    }

    @Override
    public String succeedCreateEventMessage() {
        return "You have created an event!";
    }

    @Override
    public String failCreateEventMessage() {
        return "You fail to create an event!";
    }

    @Override
    public String succeedCancelEnrollmentMessage() {
        return "Whoops! You have cancel the enrollment from the event successfully!";
    }

    @Override
    public String failCancelEnrollmentMessage() {
        return "You fail to cancel the enrollment!";
    }

    @Override
    public String succeedRemoveWaitMessage() {
        return "Whoops! You have given up the waiting list spot for the event successfully";
    }

    @Override
    public String failRemoveWaitMessage() {
        return "It seems that we cannot remove you from the waiting list of the event!";
    }

    @Override
    public String succeedResetPasswordMessage() {
        return "You have a new password!";
    }

    @Override
    public String failResetPasswordMessage() {
        return "You cannot reset your new password to this password!";
    }

    @Override
    public String succeedCreateAccountMessage() {
        return "You have created an account!";
    }

    @Override
    public String failCreateAccount() {
        return "It seems that you cannot create an account";
    }

    @Override
    public String welcome(){
        return ("Welcome");
    }
    @Override
    public String myProfile(){
        return ("My Profile");
    }
    @Override
    public String logOut(){
        return ("Log Out");
    }
    @Override
    public String resetPass(){
        return ("Reset Password");
    }
    @Override
    public String schedule(){
        return ("Schedule");
    }
    @Override
    public String signUp(){
        return ("Sign Up for An Event");
    }

    @Override
    public String signUpWait(){
        return ("Get a spot in the waiting list");
    }
    @Override
    public String signUpNowait(){
        return ("Don't have to wait!");
    }
    @Override
    public String viewMyEvent(){
        return ("View my Event");
    }
    @Override
    public String  viewMyWait(){
        return ("Events in waiting list");
    }
    @Override
    public String  viewMyNoWait(){
        return ("Events not in waiting list");
    }
    @Override
    public String  viewAllEvent(){
        return ("View All event");
    }
    @Override
    public String  messenger(){
        return ("Messenger");
    }
    @Override
    public String  newPass(){
        return ("New Password");
    }

    @Override
    public String  withID(){
        return (" with ID ");
    }
    @Override
    public String  startAt(){
        return (" starts at ");
    }
    @Override
    public String  endAt(){
        return (" ends at ");
    }
    @Override
    public String  takePlace(){
        return (" takes place at ");
    }
    @Override
    public String  whichIs(){
        return (" which is ");
    }

    @Override
    public String  title(){
        return ("Title: ");
    }
    @Override
    public String  duration(){
        return ("Duration: ");
    }
    @Override
    public String  startTime(){
        return ("Start Time: ");
    }
    @Override
    public String  endTime(){
        return ("End Time: ");
    }
    @Override
    public String  vIPStatus(){
        return ("VIP Status: ");
    }
    @Override
    public String  waitinglist(){
        return ("Waiting list: ");
    }
    @Override
    public String  signUpQuestion(){
        return ("Sign Up?");
    }
    @Override
    public String  yes(){
        return ("Yes!");
    }
    @Override
    public String  enterRoomNum(){
        return ("Enter a new room number you want to add: ");
    }
    @Override
    public String  enterRoomCapacity(){
        return ("Enter a capacity for this room:");
    }
    @Override
    public String  create(){
        return ("Create");
    }
    @Override
    public String  eventType(){
        return ("Event Type: ");
    }
    @Override
    public String  party(){
        return ("Party");
    }
    @Override
    public String  talk(){
        return ("Talk");
    }
    @Override
    public String  discussion(){
        return ("Panel Discussion");
    }
    @Override
    public String  topic(){
        return ("Topic: ");
    }
    @Override
    public String  roomNum(){
        return ("Room Number: ");
    }
    @Override
    public String  capacity(){
        return ("Capacity: ");
    }
    @Override
    public String  no(){
        return ("No!");
    }
    @Override
    public String  selectSpeaker(){
        return ("Select Speaker: ");
    }
    @Override
    public String  empty(){
        return ("Empty");
    }

    @Override
    public String  userType(){
        return ("User Type: ");
    }
    @Override
    public String  speaker(){
        return ("Speaker");
    }
    @Override
    public String  organizer(){
        return ("Organizer");
    }
    @Override
    public String  attendee(){
        return ("Attendee");
    }
    @Override
    public String  cancel(){
        return ("Cancel");
    }
    @Override
    public String  rank(){
        return ("Rank: ");
    }
    @Override
    public String  reallyQuestion(){
        return ("Really??");
    }
    @Override
    public String  change(){
        return ("Change: ");
    }

    @Override
    public String  viewTalks(){
        return ("View my Talks: ");
    }
    @Override
    public String  newUser(){
        return ("New User");
    }
    @Override
    public String  newRoom(){
        return ("New Room");
    }
    @Override
    public String  newEvent(){
        return ("New Event");
    }
    @Override
    public String  organizedEvent(){
        return ("Organized Event");
    }
    @Override
    public String  changeEventSetting(){
        return ("Change Event Setting");
    }
    @Override
    public String viewOrganizedEvent(){
        return ("View Organized Event");
    }
    @Override
    public String eventId(){
        return ("Event ID: ");
    }
    @Override
    public String cancelled(){
        return ("Cancelled");
    }
    @Override
    public String signedUp(){
        return ("Signed Up");
    }

    @Override
    public String addSpeaker() {
        return ("Add/New Speaker");
    }

    @Override
    public String newCapacity() {
        return ("New Capacity");
    }

    @Override
    public String newStatus() {
        return ("New Status");
    }

    @Override
    public String send() {
        return ("Send");
    }

    @Override
    public String sentMessage(){
        return ("Sent Message");
    }

    @Override
    public String receivedMessages() {
        return ("Received Messages");
    }

    @Override
    public String close() {
        return ("Close");
    }

    @Override
    public String messengerOptions() {
        return ("Messenger Options");
    }

    @Override
    public String viewMyMessages() {
        return ("View my messages");
    }

    @Override
    public String message() {
        return ("Message");
    }

    @Override
    public String writeMessage() {
        return ("Write your message here");
    }

    @Override
    public String writeTitle() {
        return ("Enter the title of the message");
    }

    @Override
    public String allAttendees() {
        return ("All attendees");
    }

    @Override
    public String oneAttendee() {
        return ("One Attendee");
    }

    @Override
    public String sendTo() {
        return ("Who do you want to send this message to");
    }

    @Override
    public String messageInformation() {
        return ("Message Information");
    }

    @Override
    public String enterIdEvent() {
        return ("Enter the id of the event");
    }

    @Override
    public String enterIdSpeaker() {return "Enter the id of the event";}

    @Override
    public String enterNameAttendee() {
        return ("Enter the username of the attendee");
    }

    @Override
    public String writeNewMes() {
        return ("Write new message");
    }

    @Override
    public String reply() {
        return ("Reply");
    }

    @Override
    public String writeMesHere() {
        return ("Write your message here");
    }

    @Override
    public String chooseTitle() {
        return ("Choose your title");
    }

    @Override
    public String succeedSendMes() {
        return ("Your message was sent successfully");
    }

    @Override
    public String enterTitleMes() {
        return ("Enter the title of your message");
    }

    @Override
    public String mesAllSpeakers() {
        return ("Message all speakers");
    }

    @Override
    public String mesOneSpeaker() {
        return ("Message one speaker");
    }

    @Override
    public String mesAllAttendees() {
        return ("Message all attendees");
    }

    @Override
    public String mesOneAttendees() {
        return ("Message one attendee");
    }

    @Override
    public String enterNameSpeaker() {
        return ("Enter the username of the speaker");
    }

    @Override
    public String mesAllAttendeesEvent() {
        return ("Message all attendees of my events");
    }

    @Override
    public String mesOneAttendeeEvent() {
        return ("Message one attendee of my events");
    }

    @Override
    public String receivedEmail() {
        return ("Received email");
    }

    @Override
    public String mesAllAttendeesInOneEvent() {
        return "Message all attendees of one of my event";
    }

    @Override
    public String withCapacity() {
        return " with capacity ";
    }

    @Override
    public String event() {
        return "event";
    }

    @Override
    public String here() {
        return "here";
    }
}
