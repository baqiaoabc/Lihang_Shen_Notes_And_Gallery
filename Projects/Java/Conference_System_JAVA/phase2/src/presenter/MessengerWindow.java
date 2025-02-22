package presenter;

import presenter.factory.JOptionPaneFactory;
import presenter.language.Language;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

import static javax.swing.JOptionPane.showMessageDialog;

/**
 * the messenger window
 */

class MessengerWindow extends JFrame implements ActionListener, IMessage {
    static JTextArea msgRec = new JTextArea(100, 50);
    static JTextArea msgSend = new JTextArea(100, 50);
    private static Language language1;
    JButton send;
    JButton rcv;
    JButton snd;
    JScrollPane pane2, pane1;
    JMenu messenger;
    JMenuItem logOut;
    JMenuBar bar;
    JMenu messageOptions;
    MessengerPresenter _msgPresenter;
    Language language;

    /**
     * Construct the messenger Window
     *
     */

    public MessengerWindow(Presenter presenter, Language language){
        super(language.message());
        this.language = language;
        this.language1 = language;
        send = new JButton(language.send());
        rcv = new JButton(language.receivedMessages());
        snd = new JButton(language.sentMessage());
        messenger = new JMenu(language.messenger());
        logOut = new JMenuItem(language.close());
        bar = new JMenuBar();
        messageOptions = new JMenu(language.messengerOptions());

        _msgPresenter = new MessengerPresenter(this, presenter);
        setBounds(0, 0, 407, 495);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
        setLayout(null);

        msgRec.setEditable(false);
        msgRec.setBackground(Color.WHITE);
        msgRec.setForeground(Color.DARK_GRAY);
        msgRec.setText("");

        msgRec.setWrapStyleWord(true);
        msgRec.setLineWrap(true);

        pane2 = new JScrollPane(msgRec);
        pane2.setBounds(0, 0, 400, 200);
        pane2.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        add(pane2);

        msgSend.setBackground(Color.WHITE);
        msgSend.setForeground(Color.DARK_GRAY);
        msgSend.setLineWrap(true);
        msgSend.setWrapStyleWord(true);

        msgSend.setText(language.writeMesHere());

        pane1 = new JScrollPane(msgSend);
        pane1.setBounds(0, 200, 400, 200);
        pane1.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        add(pane1);

        send.setBounds(0, 400, 400, 40);
        add(send);
        send.addActionListener(this);

        bar.add(messenger);
        messenger.add(logOut);
        logOut.addActionListener(this);

        setJMenuBar(bar);
        bar.add(messageOptions);
        rcv.setSize(200, 200);
        msgRec.setLayout(new FlowLayout());
        msgRec.add(rcv);
        msgRec.add(snd);
        rcv.addActionListener(this);
        snd.addActionListener(this);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    @Override
    /**
     * Perform the action
     * @param e action that we need to follow
     */
    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();
        if (src == send){
            String title = JOptionPane.showInputDialog(language.enterTitleMes());

            String message = msgSend.getText();
            Object[] options = {language.mesOneSpeaker(),
                    language.oneAttendee()};
            int optionPane = JOptionPane.showOptionDialog(this,
                    language.sendTo(),
                    language.messageInformation(),
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    options,
                    options[1]);
            if (optionPane == JOptionPane.YES_OPTION){
                JFrame frame = new JFrame();
                DefaultListModel listModel = new DefaultListModel();
                JList Jlist = new JList(listModel);
                List<List<String>> allSpeaker = _msgPresenter.viewAllSpeakers();
                for (List lst : allSpeaker) {
                    String element = language.speaker() + " " + lst.get(1) + language.withID() + lst.get(0);
                    listModel.addElement(element);
                }
                Jlist.addListSelectionListener(e1 -> {
                    int ind = Jlist.getSelectedIndex();
                    String userName = allSpeaker.get(ind).get(1);
                    _msgPresenter.messageOneSpeaker(title, message, userName);
                    frame.dispose();
                });
                frame.add(Jlist);
                frame.pack();
                frame.setSize(300, 300);
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
            if (optionPane == JOptionPane.NO_OPTION){
                System.out.println(language.no());
                JFrame frame = new JFrame();
                DefaultListModel listModel = new DefaultListModel();
                JList list = new JList(listModel);
                List<List<String>> allMessageableAttendee = _msgPresenter.allMessageableAttendee();
                helper(title, message, frame, listModel, list, allMessageableAttendee, _msgPresenter, true);
            }
            msgSend.setText(language.writeNewMes());}
        if (src == logOut){
            dispose();
        }

        if (src == rcv) {
            JFrame frame = new JFrame();
            List<List<String>> allReceivedMessage = _msgPresenter.readReceivedMessages();
            DefaultListModel listModel = new DefaultListModel();
            for (List lst : allReceivedMessage) {
                String element = lst.get(0) + ": " + lst.get(1);
                listModel.addElement(element);
            }

            JList list = new JList(listModel);
            list.addListSelectionListener(e1 -> {
                int ind = list.getSelectedIndex();
                System.out.println(ind);
                Object[] replyOrClose = {language.reply(),
                        language.close()};
                List t = allReceivedMessage.get(ind);
                int optionPane = JOptionPane.showOptionDialog(new JFrame(),
                        t.get(2),
                        language.receivedEmail(),
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        replyOrClose,
                        replyOrClose[1]);
                if (optionPane == JOptionPane.YES_OPTION){
                    String content = JOptionPane.showInputDialog(language.writeMesHere());
                    String title = JOptionPane.showInputDialog(language.chooseTitle());
                    String messageID = Integer.toString(ind+1);
                    _msgPresenter.replyTo(content, title, messageID);
                }
            });
            frame.add(list);
            frame.pack();
            frame.setSize(300, 300);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        }
        if (src == snd){
            JFrame frame = new JFrame();
            List<List<String>> listOfLists = _msgPresenter.readSentMessages();
            DefaultListModel listModel = new DefaultListModel();
            for (List lst : listOfLists) {
                String element = lst.get(0) + ": " + lst.get(1);
                listModel.addElement(element);
            }
            JList list = new JList(listModel);
            list.addListSelectionListener(new ListSelectionListener() {
                @Override
                public void valueChanged(ListSelectionEvent e) {
                    int ind = list.getSelectedIndex();
                    List message = listOfLists.get(ind);
                    JOptionPane.showMessageDialog(null, message.get(2), language.messageTitle(),
                            JOptionPane.INFORMATION_MESSAGE);
                }
            });
            frame.add(list);
            frame.pack();
            frame.setSize(300, 300);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        }
    }

    static void helper(String title, String message, JFrame frame, DefaultListModel listModel, JList list, List<List<String>> allMessageableAttendee, MessengerPresenter _msgPresenter, boolean messageToAttendee) {
        for (List lst : allMessageableAttendee) {
            String element = language1.attendee() + " "+ lst.get(1) + language1.withID() + lst.get(0);
            listModel.addElement(element);
        }
        list.addListSelectionListener(e1 -> {
            int ind = list.getSelectedIndex();
            String userName = allMessageableAttendee.get(ind).get(1);
            if (messageToAttendee){
                _msgPresenter.messageOneAttendee(title, message, userName);}
            else{
                _msgPresenter.messageOneSpeaker(title, message, userName);
            }
            frame.dispose();
                });
        frame.add(list);
        frame.pack();
        frame.setSize(300, 300);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }


    /**
     * notifies the user of his/her/they message status
     * @param success the success of user's action
     */
    public void messageSuccess(boolean success){
        if (success){
            showMessageDialog(null, language.succeedSendMes());

        }
        else{
            showMessageDialog(null, language.fail());
        }
    }

    @Override
    public void setVisible() {
        setVisible(true);
    }

}