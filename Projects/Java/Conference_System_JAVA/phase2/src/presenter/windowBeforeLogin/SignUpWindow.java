package presenter.windowBeforeLogin;

import presenter.IUpdate;
import presenter.Presenter;
import presenter.windowBeforeLogin.LoginWindow;
import presenter.factory.JOptionPaneFactory;
import presenter.language.Language;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SignUpWindow extends JFrame implements IUpdate {
    JLabel usernameLabel;
    JLabel passwordLabel;
    JTextField usernameText;
    JTextField passwordText;
    JButton okButton;
    JButton backButton;
    Font defaultFont = new Font("Mononspace", 1, 25);
    Language language;
    JOptionPaneFactory windowFactory;
    Presenter presenter = new Presenter();
    IUpdate window = this;


    /**
     * @Description: the sign up window
     * @param language the language
     */
    public SignUpWindow(Language language) {
        this.language = language;
        windowFactory = new JOptionPaneFactory(language);
        init();
    }

    private void init() {
        JPanel signUpPanel= new JPanel();
        setContentPane(signUpPanel);
        signUpPanel.setLayout(new GridLayout(3,2,0,50));

        usernameLabel = new JLabel(language.username());
        usernameLabel.setFont(defaultFont);
        signUpPanel.add(usernameLabel);

        usernameText = new JTextField();
        usernameText.setFont(defaultFont);
        signUpPanel.add(usernameText);

        passwordLabel = new JLabel(language.password());
        passwordLabel.setFont(defaultFont);
        signUpPanel.add(passwordLabel);

        passwordText = new JTextField();
        passwordText.setFont(defaultFont);
        signUpPanel.add(passwordText);

        okButton = new JButton(language.ok());
        okButton.setFont(defaultFont);
        signUpPanel.add(okButton);

        backButton = new JButton(language.back());
        backButton.setFont(defaultFont);
        signUpPanel.add(backButton);

        setTitle(language.titleSignUp());
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(600, 500);
        setLocation(450,200);
        setVisible(true);

        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                presenter.signup(usernameText.getText(), passwordText.getText(), window);
            }
        });




        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               update("Back");
            }
        });
    }

    /**
     * @Description: a user sign up with his password
     */
    private void signUp(){
        String username = usernameText.getText();
        String password = passwordText.getText();
        presenter.signup(username, password, window);
    }

    /**
     * @Description: update the action
     * @param action the action
     */
    public void update(String action){
        if(action == "Back"){ //back to the login page
            LoginWindow loginWindow = new LoginWindow(language);
            dispose();
        }else {
            windowFactory.get(action);
        }
    }


}