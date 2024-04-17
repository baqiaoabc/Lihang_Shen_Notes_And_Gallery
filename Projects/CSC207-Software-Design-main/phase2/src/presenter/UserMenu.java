package presenter;

import presenter.factory.PanelFactory;
import presenter.language.Language;
import presenter.windowBeforeLogin.LoginWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static javax.swing.JOptionPane.showMessageDialog;
/**
 * The menu all attendees see
 */
public class UserMenu extends JFrame implements IUpdate {
    protected JMenu myProfile;
    protected JMenu schedule;
    protected JMenu signUp;
    protected JMenu viewMyEvent;
    protected JMenuItem logOut;
    protected JMenuItem reSet;
    protected JMenuItem viewAllEvent;
    protected JMenuItem signUpNoWait;
    protected JMenuItem signUpWait;
    protected JMenuItem viewNoWait;
    protected JMenuItem viewWait;
    protected JMenuItem messenger;
    protected JMenuBar menuBar;
    protected PanelFactory panelFactory;
    protected JPanel contentPanel;
    protected Language language;
    protected IUpdate menu = this;
    protected Presenter presenter;
    protected IMessage mesWindow;

    /**
     * construct the UserMenu
     * @param language the language used in UserMenu
     * @param presenter the presenter whose presenter is used to initialize the PanelFactory
     */
    public UserMenu(Language language, Presenter presenter) {
        setLayout(new BorderLayout());
        this.language = language;
        this.presenter = presenter;
        panelFactory = new PanelFactory(language, presenter);
        contentPanel = new JPanel();
        contentPanel.add(new JLabel(language.welcome()));
        add(contentPanel, BorderLayout.CENTER);



        menuBar = new JMenuBar();
        myProfile = new JMenu(language.myProfile());
        logOut = new JMenuItem(language.logOut());
        logOut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LoginWindow window = new LoginWindow();
                System.exit(0);
            }
        });
        reSet = new JMenuItem(language.resetPass());
        reSet.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                update("reSet");
            }
        });
        myProfile.add(logOut);
        myProfile.add(reSet);
        menuBar.add(myProfile);

        schedule = new JMenu(language.schedule());
        signUp = new JMenu(language.signUp());
        signUpNoWait = new JMenuItem(language.signUpNowait());
        signUpNoWait.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                update("signUpNoWait");
            }
        });
        signUpWait = new JMenuItem(language.signUpWait());
        signUpWait.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                update("signUpWait");
            }
        });
        signUp.add(signUpNoWait);
        signUp.add(signUpWait);
        viewMyEvent = new JMenu(language.viewMyEvent());
        viewNoWait = new JMenuItem(language.viewMyNoWait());
        viewNoWait.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                update("viewNoWait");
            }
        });
        viewWait = new JMenuItem(language.viewMyWait());
        viewWait.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                update("viewWait");
            }
        });
        viewMyEvent.add(viewNoWait);
        viewMyEvent.add(viewWait);
        viewAllEvent = new JMenuItem(language.viewAllEvent());
        viewAllEvent.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                update("viewAllEvents");
            }
        });
        schedule.add(signUp);
        schedule.add(viewMyEvent);
        schedule.add(viewAllEvent);
        menuBar.add(schedule);

        messenger = new JMenuItem(language.messenger());
        messenger.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               presenter.openMessenger(menu);
            }
        });
        menuBar.add(messenger);

        setJMenuBar(menuBar);;

        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

    }

    /**
     * update the actions
     * @param action action that we need to follow
     */
    public void update(String action){
        if  (action == "Attendee"){
            if (mesWindow == null){
                IMessage mesWindow = new MessengerWindow(presenter, language);
                this.mesWindow = mesWindow;
            }
            else{
                mesWindow.setVisible();
            }

        }else{
            contentPanel.removeAll();
            contentPanel.add(panelFactory.getPanel(action));
            contentPanel.validate();
            contentPanel.repaint();
        }
    }




}
