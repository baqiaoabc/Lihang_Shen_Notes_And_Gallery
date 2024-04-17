package presenter.windowBeforeLogin;

import presenter.IUpdate;
import presenter.language.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * the window used to choose language
 */
public class LanguageWindow extends JFrame implements IUpdate {
    ButtonGroup buttonGroup;
    JRadioButton simplifiedChineseButton;
    JRadioButton englishButton;
    JRadioButton russiaButton;
    JRadioButton traditionalChineseButton;
    JRadioButton japaneseButton;
    JButton okButton;
    JButton backButton;
    Language language;

    Font defaultFont = new Font("Mononspace", 1, 25);

    /**
     * Construct the language Window
     * @param language the language used in this window
     */
    public LanguageWindow(Language language) {
        this.language = language;
        init();


    }

    private void init() {
        JPanel languagePanel = new JPanel();
        setContentPane(languagePanel);
        languagePanel.setLayout(new GridLayout(4,2,0,50));

        buttonGroup = new ButtonGroup();

        simplifiedChineseButton = new JRadioButton(new SimplifiedChinese().language());
        simplifiedChineseButton.setFont(defaultFont);
        simplifiedChineseButton.setFont(defaultFont);
        simplifiedChineseButton.setAlignmentY(Component.CENTER_ALIGNMENT);

        englishButton = new JRadioButton(new English().language());
        englishButton.setSelected(true);
        englishButton.setFont(defaultFont);

        russiaButton = new JRadioButton(new Russian().language());
        russiaButton.setFont(defaultFont);

        traditionalChineseButton = new JRadioButton(new TraditionalChinese().language());
        traditionalChineseButton.setFont(defaultFont);

        japaneseButton = new JRadioButton(new Japanese().language());
        japaneseButton.setFont(defaultFont);

        buttonGroup.add(englishButton);
        buttonGroup.add(simplifiedChineseButton);
        buttonGroup.add(russiaButton);
        buttonGroup.add(traditionalChineseButton);
        buttonGroup.add(japaneseButton);

        languagePanel.add(englishButton);
        languagePanel.add(simplifiedChineseButton);
        languagePanel.add(russiaButton);
        languagePanel.add(traditionalChineseButton);
        languagePanel.add(japaneseButton);

        JLabel empty = new JLabel("More is coming!");
        empty.setFont(defaultFont);
        languagePanel.add(empty);


        okButton = new JButton(language.ok());
        okButton.setFont(defaultFont);
        languagePanel.add(okButton);

        backButton = new JButton(language.back());
        backButton.setFont(defaultFont);
        languagePanel.add(backButton);


        setTitle(language.selectLanguage());
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(600, 500);
        setLocation(450, 200);
        setVisible(true);

        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                update("OK");
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
     * @Description: set new languages
     */
    private void setNewLanguage(){
        Language newLanguage;
        if (simplifiedChineseButton.isSelected()){
            newLanguage = new SimplifiedChinese();
        }else if (englishButton.isSelected()){
            newLanguage = new English();
        }else if(traditionalChineseButton.isSelected()){
            newLanguage = new TraditionalChinese();
        }else if(japaneseButton.isSelected()){
            newLanguage = new Japanese();
        }else {
            newLanguage = new Russian();
        }
        LoginWindow loginWindow = new LoginWindow(newLanguage);
        dispose();
    }

    /**
     * @Description: update the action
     * @param action the action
     */
    public void update(String action){
        if (action == "Back"){
            LoginWindow loginWindow = new LoginWindow(language);
            dispose();
        }else if (action == "OK"){
            setNewLanguage();
        }
    }
}
