package presenter.language;
/**
 * This is the interface used to implement the multi-language version used in our system
 */
public interface Language {
    /** @return the String meaning Username:  */
    String username();

    /** @return the String meaning Password: */
    String password();

    /** @return the String meaning sign up an account */
    String signUpAccount();

    /** @return  the String meaning OK*/
    String ok();

    /** @return the String meaning Exit */
    String exit();

    /** @return the String meaning Back */
    String back();

    /** @return the String meaning Welcome to the Best Conference in the World!  */
    String titleLogin();

    /** @return the String meaning You are signing up */
    String titleSignUp();

    /** @return the String meaning what is the language called in this language*/
    String language();

    /** @return the String meaning select language */
    String selectLanguage();

    /** @return the String meaning It looks like something went wrong! Try again!" */
    String fail();

    /** @return the String meaning the message */
    String messageTitle();

    /** @return the String meaning Hey! You have created an account! */
    String createAccountMessage();

    /** @return the String meaning a room has been successfully added */
    String succeedAddRoomMessage();

    /** @return the String meaning successful */
    String successful();

    /** @return the String meaning failed */
    String failed();

    /** @return the String meaning The room already exists or your input is valid */
    String failAddRoomMessage();

    /** @return the String meaning the user has successfully canceled event */
    String succeedCancelEventMessage();

    /** @return the String meaning the user has failed to cancel event*/
    String failCancelEventMessage();

    /** @return the String meaning the user has successfully sign up an event */
    String succeedSignUpEventMessage();

    /** @return the String meaning the user has failed to sign up an event */
    String failSignUpEvent();

    /** @return the String meaning the user has successfully get a spot in an event in the waiting list */
    String succeedWaitEvent();

    /** @return the String meaning the user has failed to get a spot in an event in the waiting list */
    String failWaitEventMessage();

    /** @return the String meaning the user has successfully changed an event's setting */
    String succeedChangeEventMessage();

    /** @return the String meaning the user has failed to change an event's setting  */
    String failChangeEventMessage();

    /** @return the String meaning the User has successfully created an user */
    String succeedCreateUserMessage();

    /** @return the String meaning the User has failed to create an user */
    String failCreateUserMessage();

    /** @return the String meaning the user has successfully created an  event */
    String succeedCreateEventMessage();

    /** @return the String meaning the user has failed to create an event */
    String failCreateEventMessage();

    /** @return the String meaning the user has successfully canceled an enrollment in an event*/
    String succeedCancelEnrollmentMessage();

    /** @return the String meaning the user has failed to cancel an enrollment in an event */
    String failCancelEnrollmentMessage();

    /** @return the String meaning the user has successfully removed the spot in the waiting list */
    String succeedRemoveWaitMessage();

    /** @return the String meaning the user has failed to remove the spot in the waiting list */
    String failRemoveWaitMessage();

    /** @return the String meaning the user has successfully reset the password */
    String succeedResetPasswordMessage();

    /** @return the String meaning the user has failed to reset the password */
    String failResetPasswordMessage();

    /** @return the String meaning the user has successfully created an account*/
    String succeedCreateAccountMessage();

    /** @return the String meaning the user has  failed to create an account*/
    String failCreateAccount();

    /** @return the String meaning welcome */
    String welcome();

    /** @return the String meaning my profile */
    String myProfile();

    /** @return the String meaning logout  */
    String logOut();

    /** @return the String meaning resetPass */
    String resetPass();

    /** @return the String meaning schedule */
    String schedule();

    /** @return the String meaning sign up  */
    String signUp();

    /** @return  the String meaning get a spot in the waiting list */
    String signUpWait();

    /**  @return the String meaning sign Up an event that is not in the waiting list  */
    String signUpNowait();

    /**  @return the String meaning view my events  */
    String viewMyEvent();

    /** @return the String meaning view my events that are in the waiting list  */
    String viewMyWait();

    /** @return the String meaning view my events that are not in the waiting list */
    String viewMyNoWait();

    /** @return the String meaning view all events */
    String viewAllEvent();

    /** @return the String meaning messenger */
    String messenger();

    /** @return the String meaning new password */
    String newPass();

    /** @return the String meaning With id */
    String withID();

    /** @return the String meaning Start at */
    String startAt();

    /** @return the String meaning End at */
    String endAt();

    /** @return the String meaning the place that is going take */
    String takePlace();

    /** @return the String meaning which is */
    String whichIs();

    /** @return the String meaning title */
    String title();

    /** @return the String meaning duration */
    String duration();

    /** @return the String meaning starting time */
    String startTime();

    /** @return the String meaning end time */
    String endTime();

    /** @return the String meaning VIP Status */
    String vIPStatus();

    /** @return the String meaning waiting list */
    String waitinglist();

    /** @return the String meaning sign up? */
    String signUpQuestion();

    /** @return the String meaning yes */
    String yes();

    /** @return the String meaning enter the room number */
    String enterRoomNum();

    /** @return the String meaning enter the room capacity */
    String enterRoomCapacity();

    /** @return the String meaning create */
    String create();

    /** @return the String meaning Event type*/
    String eventType();

    /** @return the String meaning Party */
    String party();

    /** @return the String meaning Talk */
    String talk();

    /**  @return the String meaning Discussion */
    String discussion();

    /**  @return the String meaning Topic */
    String topic();

    /**  @return the String meaning Room Number */
    String roomNum();

    /** @return the String meaning capacity */
    String capacity();

    /** @return the String meaning No */
    String no();

    /** @return the String meaning select a speaker */
    String selectSpeaker();

    /** @return the String meaning empty */
    String empty();

    /** @return the String meaning User type */
    String userType();

    /** @return the String meaning Speaker */
    String speaker();

    /** @return the String meaning Organizer */
    String organizer();

    /** @return the String meaning Attendee */
    String attendee();

    /** @return the String meaning cancel */
    String cancel();

    /** @return the String meaning the rank */
    String rank();

    /** @return the String meaning questions for confirmation*/
    String reallyQuestion();

    /** @return the String meaningchange */
    String change();

    /** @return the String meaning view talks */
    String viewTalks();

    /** @return the String meaning New User */
    String newUser();

    /** @return the String meaning New Room */
    String newRoom();

    /** @return the String meaning New Event */
    String newEvent();

    /** @return the String meaning Organized event */
    String organizedEvent();

    /** @return the String meaning change event setting */
    String changeEventSetting();

    /** @return the String meaning view all the organized event */
    String viewOrganizedEvent();

    /** @return the String meaning Event ID */
    String eventId();

    /**
     * @return the string meaning cancel
     */
    String cancelled();
    /**
     * @return the String meaning signed up
     */
    String signedUp();
    /**
     * @return the String meaning Add/Change Speaker
     */
    String addSpeaker();
    /**
     * @return the String meaning New Capacity
     */
    String newCapacity();
    /**
     * @return the String meaning New Status
     */
    String newStatus();

    /**
     * @return the String meaning Send
     */
    String send();
    /**
     * @return the String meaning Received Messages
     */
    /**
     *
     * @return the String meaning Sent Messages
     */
    String sentMessage();

    /**
     *
     * @return the String meaning Received Message
     */
    String receivedMessages();
    /**
     * @return the String meaning Close
     */
    String close();
    /**
     * @return the String Meaning Messenger Options;
     */
    String messengerOptions();
    /**
     * @return the String meaning View my messages
     */
    String viewMyMessages();
    /**
     * @return the String meaning message
     */
    String message();
    /**
     * @return the String meaning Write your message here
     */
    String writeMessage();
    /**
     * @return the String meaning Enter the title of your message
     */
    String writeTitle();
    /**
     * @return the String meaning All attendees
     */
    String allAttendees();
    /**
     * @return the String meaning one attendess
     */
    String oneAttendee();

    /**
     *
     * @return the String meaning Who do you want to send this message to
     */
    String sendTo();

    /**the
     * @return the String meaning Message Information
     */
    String messageInformation();

    /**
     *
     * @return the String meaning Enter the ID of the Event
     */
    String enterIdEvent();

    /**
     *
     * @return the String meaning Enter the ID of the speaker
     */

    String enterIdSpeaker();

    /**
     *
     * @return the String meaning Enter the username of the attendee
     */
    String enterNameAttendee();
    /**
     *
     * @return the String meaning Write new message
     */
    String writeNewMes();
    /**
     *
     * @return the String meaning Reply
     */
    String reply();
    /**
     *
     * @return the String meaning write your message
     */
    String writeMesHere();
    /**
     *
     * @return the String meaning Choose your title
     */
    String chooseTitle();
    /**
     *
     * @return the String meaning message is sent successfully
     */
    String succeedSendMes();
    /**
     *
     * @return the String meaning Enter the title of your message
     */
    String enterTitleMes();
    /**
     *
     * @return the String meaning message all speakers
     */
    String mesAllSpeakers();
    /**
     *
     * @return the String meaning message one speaker
     */
    String mesOneSpeaker();
    /**
     *
     * @return the String meaning message all attendees
     */
    String mesAllAttendees();
    /**
     *
     * @return the String meaning message one attendee
     */
    String mesOneAttendees();
    /**
     *
     * @return the String meaning Enter the username of the Speaker
     */
    String enterNameSpeaker();
    /**
     *
     * @return the String meaning message all attendee of my event
     */
    String mesAllAttendeesEvent();
    /**
     *
     * @return the String meaning message one Attendee of my event
     */
    String mesOneAttendeeEvent();
    /**
     *
     * @return the String meaning Received Email
     */
    String receivedEmail();

    /**
     *
     * @return the String meaning Message all attendees of one of my event
     */
    String mesAllAttendeesInOneEvent();

    /**
     * @return the String " with capacity " (with space for language that use space in the grammar!!!)
     */
    String withCapacity();

    /**
     *
     * @return the String meaning here
     */
    String here();

    /**
     *
     * @return the String meaning event
     */
    String event();

}
