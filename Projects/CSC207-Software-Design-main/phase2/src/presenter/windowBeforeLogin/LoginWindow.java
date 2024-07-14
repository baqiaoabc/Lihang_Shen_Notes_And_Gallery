package presenter.windowBeforeLogin;

import controller.ConferenceSystem;
import entity.Organizer;
import entity.User;
import gateway.GatewayFacade;
import presenter.*;
import presenter.factory.JOptionPaneFactory;
import presenter.language.English;
import presenter.language.Language;
import usecase.UserManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * The first window users see; user interface
 */
public class LoginWindow extends JFrame implements IUpdate {
    JLabel usernameLabel;
    JLabel passwordLabel;
    JTextField usernameText;
    JTextField passwordText;
    JButton okButton;
    JButton exitButton;
    JButton signUpButton;
    JButton languageButton;
    Font defaultFont = new Font("Mononspace", 1, 25);
    Font specialFont = new Font("Mononspace", 1, 20);
    Language language;
    Presenter presenter = new Presenter();
    JOptionPaneFactory wf;
    IUpdate window = this;
    private GatewayFacade gw = new GatewayFacade();

    /**
     * construct the login window in English
     */
    public LoginWindow(){//The language is English By default
        language = new English();
        wf = new JOptionPaneFactory(language);
        init();
    }

    /**
     * construct the login window in a specific language
     * @param language the language used in the login window
     */
    public LoginWindow(Language language){
        this.language = language;
        wf = new JOptionPaneFactory(language);
        init();
    }

    private void init(){
        JPanel loginPanel= new JPanel();
        setContentPane(loginPanel);
        loginPanel.setLayout(new GridLayout(4,2,0,50));

        //set an organizer
        User initialed_organizer = new Organizer(999, "123456", "organizer");
        gw.addUser(initialed_organizer);

        usernameLabel = new JLabel(language.username());
        usernameLabel.setFont(defaultFont);
        loginPanel.add(usernameLabel);

        usernameText = new JTextField();
        usernameText.setFont(defaultFont);
        loginPanel.add(usernameText);

        passwordLabel = new JLabel(language.password());
        passwordLabel.setFont(defaultFont);
        loginPanel.add(passwordLabel);

        passwordText = new JTextField();
        passwordText.setFont(defaultFont);
        loginPanel.add(passwordText);

        signUpButton = new JButton(language.signUpAccount());
        signUpButton.setFont(specialFont);
        loginPanel.add(signUpButton);

        languageButton = new JButton(language.language());
        languageButton.setFont(specialFont);
        loginPanel.add(languageButton);

        okButton = new JButton(language.ok());
        okButton.setFont(defaultFont);
        loginPanel.add(okButton);

        exitButton = new JButton(language.exit());
        exitButton.setFont(defaultFont);
        loginPanel.add(exitButton);

        loginPanel.setVisible(true);

        // do sign up action after clicked ok button
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameText.getText();
                String password = passwordText.getText();
                presenter.signIn(username, password, window );
            }
        });

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                update("Exit");
            }
        });

        signUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                update("SignUp");
            }
        });

        languageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                update("Language");
            }
        });

        setTitle(language.titleLogin());
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(600, 500);
        setLocation(450,200);
        setVisible(true);
    }

    /**
     * @Description: update the action
     * @param action the action
     */
    public void update(String action) {
        if (action == "Exit"){
            System.exit(0);
        }else if (action == "Speaker"){
            SpeakerMenu speakerMenu = new SpeakerMenu(language, presenter);
        }else if (action == "Organizer"){
            OrganizerMenu organizerMenu = new OrganizerMenu(language, presenter);
        }else if (action == "Attendee"){
           UserMenu userMenu = new UserMenu(language, presenter);
        }else if (action == "SignUp"){
            SignUpWindow signUpWindow = new SignUpWindow(language);
        }else if(action == "Language"){
            LanguageWindow languageWindow = new LanguageWindow(language);
        }else{
            wf.get(action);
        }
        dispose();
    }


}

