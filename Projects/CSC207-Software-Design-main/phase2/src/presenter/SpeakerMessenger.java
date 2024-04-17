package presenter;

import presenter.language.Language;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.List;

/**
 * the speaker messenger window
 */

public class SpeakerMessenger extends MessengerWindow {
    /**
     * Construct the Speaker messenger window
     *
     */
    SpeakerMessenger(Presenter presenter,Language language){
        super(presenter, language);
    }
    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();

        if (src == send) {
            String title = JOptionPane.showInputDialog(language.enterTitleMes());
            String message = msgSend.getText();
            String[] buttons = {language.mesAllAttendeesEvent(), language.mesAllAttendeesInOneEvent(), language.mesOneAttendeeEvent()};
            int i = JOptionPane.showOptionDialog(null, language.sendTo(),
                    language.messageInformation(),
                    JOptionPane.PLAIN_MESSAGE, 0, null, buttons, buttons[1]);
            if (i == 0) {
                super._msgPresenter.messageAllAttendeesOfSpeakerEvent(title, message);
            }
            if (i == 1) {
                JFrame frame = new JFrame();
                DefaultListModel listModel = new DefaultListModel();
                JList Jlist = new JList(listModel);
                List<List<String>> allSpeakingEvents = _msgPresenter.viewSpeakingEvent();
                for (List lst : allSpeakingEvents) {
                    String element = language.event() + " " + lst.get(1) + language.withID() + lst.get(2);
                    listModel.addElement(element);
                }
                Jlist.addListSelectionListener(e1 -> {
                    int ind = Jlist.getSelectedIndex();
                    String eventId = allSpeakingEvents.get(ind).get(2);
                    _msgPresenter.messageAllAttendeesOfOneSpeakerEvent(title, message, eventId);
                    frame.dispose();
                });
                frame.add(Jlist);
                frame.pack();
                frame.setSize(300, 300);
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
            if (i == 2) {
                JFrame frame = new JFrame();
                DefaultListModel listModel = new DefaultListModel();
                JList Jlist = new JList(listModel);
                List<List<String>> allSpeakingEvents = _msgPresenter.viewSpeakingEvent();
                for (List lst : allSpeakingEvents) {
                    String element = language.event() + " " + lst.get(1) + language.withID()+ lst.get(2);
                    listModel.addElement(element);
                }
                Jlist.addListSelectionListener(e1 -> {
                    int ind = Jlist.getSelectedIndex();
                    String eventId = allSpeakingEvents.get(ind).get(2);
                    JFrame frame2 = new JFrame();
                    DefaultListModel listModel2 = new DefaultListModel();
                    JList subList = new JList(listModel2);
                    List<List<String>> allAttendeeInEvents = _msgPresenter.viewAllAttendeesInEvent(eventId);
                    for (List lst : allAttendeeInEvents) {
                        String element = language.attendee() + " " + lst.get(1) + language.withID() + lst.get(0);
                        listModel2.addElement(element);
                    }
                    subList.addListSelectionListener(e2 -> {
                        int secondInd = subList.getSelectedIndex();
                        String userId = allAttendeeInEvents.get(secondInd).get(0);
                        _msgPresenter.messageOneSpecificAttendee(title, message, eventId, userId);
                        frame2.dispose();
                    });
                    frame2.add(subList);
                    frame2.pack();
                    frame2.setSize(300, 300);
                    frame2.setLocationRelativeTo(null);
                    frame2.setVisible(true);
                });
                frame.add(Jlist);
                frame.pack();
                frame.setSize(300, 300);
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
            msgSend.setText(language.writeNewMes());
        } else {
            super.actionPerformed(e);
        }
    }
}
