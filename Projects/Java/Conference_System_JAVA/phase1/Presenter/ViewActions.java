import java.util.List;

public interface ViewActions {
    void SignUpSuccess();
    void Introduction();
    int AreURegistered();
    String username();
    String[] SignIn();
    String Pass();
    void Credentials(String username);
    void CredentialsFail();
    int MessengerAttendee();
    int MessengerOrganizer();
    int MessengerSpeaker();
    int AttendeeMenu();
    int OrganizerMenu();
    int SpeakerMenu();
    void SorryMessenger();
    void SignUpEventMessage();
    void SignUpEventErr();
    void AddNewRoom();
    void AddNewRoomErr();
    void SpeakerAccCreated();
    void SpeakerAccCreatedErr();
    void SpeakerSet();
    void SpeakerSetErr();
    void CancelEventMessage();
    void MessageErr();
    void MessageSuccess();
    void invalidInput();
    String SignUpEvent();
    String addRoom();
    String[]  speakerAdder();
    String[] setSpeakerEvent();
    String cancelEnrollment();
    String[] replyMessage(List<String> receive_message);
    String[] MessageAllAttendeeEvent();
    String messageAllUsersInAllSpeakingEvents();
    String[] messageSpeaker();
    String messageAllAttendee();
    String[] messageAttendee();
    String[] newEvent();
    String messageAllSpeakers();
    String[] messageOneSpecificUserInEvent();
    void viewAllAttendees();
    void  viewAllSpeakers();
    void viewAttendeesInSpeakingEvents();
    void viewAllRooms();
    String resetPassword();
    void passSuccess();
    void signOutMessage();
    void eventSuccess();
    void StringPrinter(List<String> events);
    void readReceivedMessages();
    void readSentMessages();




}
