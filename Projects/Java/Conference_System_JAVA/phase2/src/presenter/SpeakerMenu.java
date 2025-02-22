package presenter;

import presenter.language.Language;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
/**
 * The menu all speakers see
 */
public class SpeakerMenu extends UserMenu {
    JMenuItem viewMyTalks;
    JMenu myTalks;
    IMessage speakerMes;
    /**
     * construct the SpeakerMenu
     * @param language the language used in UserMenu
     * @param presenter the presenter whose presenter is used to initialize the PanelFactory
     */
    public SpeakerMenu(Language language, Presenter presenter) {
        super(language, presenter);
        this.myTalks = new JMenu(language.talk());
        myTalks.add(this.viewMyTalks = new JMenuItem(language.viewTalks()));
        menuBar.add(myTalks);
        viewMyTalks.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                update("viewTalks");
                ;
            }
        });
    }

    /**
     * update the actions
     * @param action action that we need to follow
     */
    public void update(String action){
        if  (action == "Speaker"){
            if (speakerMes == null){
                IMessage speakerMes = new SpeakerMessenger(super.presenter, super.language);
                this.speakerMes = speakerMes;
            }
            else {
                speakerMes.setVisible();
            }
        }else{
            contentPanel.removeAll();
            contentPanel.add(panelFactory.getPanel(action));
            contentPanel.validate();
            contentPanel.repaint();
        }
    }
}
