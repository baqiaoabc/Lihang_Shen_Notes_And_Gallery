package presenter;

import presenter.language.Language;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.List;

/**
 * the organizer messenger window
 */

public class OrganizerMessenger extends MessengerWindow {

    /**
     * Construct the Organizer messenger window
     *
     */
    OrganizerMessenger(Presenter presenter, Language language){
        super(presenter, language);

    }

    public void actionPerformed(ActionEvent e) {

        Object src = e.getSource();
        if (src == send){
            String title = JOptionPane.showInputDialog(language.enterTitleMes());
            String message = msgSend.getText();
            String[] buttons = {language.mesAllSpeakers(), language.mesOneSpeaker(), language.mesAllAttendees(),
                    language.mesOneAttendees()};
            int i = JOptionPane.showOptionDialog(null, language.sendTo(),
                    language.messengerOptions(),
                    JOptionPane.DEFAULT_OPTION, 0, null, buttons, buttons[3]);
            if (i == 0){
                super._msgPresenter.messageAllSpeakers(title,message);
            }
            if (i == 1){
                JFrame frame = new JFrame();
                DefaultListModel listModel = new DefaultListModel();
                JList list = new JList(listModel);
                List<List<String>> allSpeaker = _msgPresenter.viewAllSpeakers();
                System.out.println(language.here());
                helper(title, message, frame, listModel, list, allSpeaker, false);
            }
            if (i == 2){
                _msgPresenter.messageAllAttendeeOrganizer(title, message);
            }
            if (i == 3){
                JFrame frame = new JFrame();
                DefaultListModel listModel = new DefaultListModel();
                JList list = new JList(listModel);
                List<List<String>> allAttendee = _msgPresenter.viewAllAttendees();
                helper(title, message, frame, listModel, list, allAttendee, true);
            }
            msgSend.setText(language.writeNewMes());
        }
        if (src == rcv || src == snd || src == logOut){
            super.actionPerformed(e);
        }
    }

    /**
     * A helper function to send a message
     * @param title the title of message
     * @param message the message content
     * @param frame the fram
     * @param listModel the list model
     * @param list a Jlist
     * @param allAttendee the list of all attendees
     * @param messageToAttendee a boolean of whether it is sent to an attendee
     */
    private void helper(String title, String message, JFrame frame, DefaultListModel listModel, JList list, List<List<String>> allAttendee, boolean messageToAttendee) {
        MessengerWindow.helper(title, message, frame, listModel, list, allAttendee, _msgPresenter, messageToAttendee);
    }

}
