package presenter;

import presenter.language.Language;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * The menu organizers see
 */
public class OrganizerMenu extends UserMenu {
    JMenu create;
    JMenuItem createUser;
    JMenuItem createRoom;
    JMenuItem createEvent;
    JMenuItem changeEventSetting;
    JMenuItem viewOrganizedEvent;
    JMenu organizedEvent;
    IMessage organizerMes;

    /**
     * construct the OrganizerMenu
     * @param language the language used in the organizer menu
     * @param presenter the presenter whose presenter is used to initialize the PanelFactory
     */
    public OrganizerMenu(Language language, Presenter presenter) {
        super(language, presenter);
     create = new JMenu(language.create());
     createUser = new JMenuItem(language.newUser());
     createUser.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
             update("createUser");
         }
     });
     createRoom = new JMenuItem(language.newRoom());
     createRoom.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
             update("addRoom");
         }
     });
     createEvent = new JMenuItem(language.newEvent());
     createEvent.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
             update("createEvent");
         }
     });
     create.add(createUser);
     create.add(createEvent);
     create.add(createRoom);
     menuBar.add(create);

     organizedEvent = new JMenu(language.organizedEvent());
     changeEventSetting = new JMenuItem(language.changeEventSetting());
        changeEventSetting.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                update("changeEvent");
            }
        });
     viewOrganizedEvent = new JMenuItem(language.viewOrganizedEvent());
     viewOrganizedEvent.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
             update("viewOrganizedEvent");
         }
     });
     organizedEvent.add(changeEventSetting);
     organizedEvent.add(viewOrganizedEvent);
     menuBar.add(organizedEvent);

    }

    /**
     * update the actions
     * @param action action that we need to follow
     */
    public void update(String action){
      if  (action == "Organizer"){
          if (organizerMes == null) {
              IMessage organizerMes = new OrganizerMessenger(super.presenter, super.language);
              this.organizerMes = organizerMes;
          }
          else {
              organizerMes.setVisible();
          }
        }
      else{
          contentPanel.removeAll();
          contentPanel.add(panelFactory.getPanel(action));
          contentPanel.validate();
          contentPanel.repaint();
        }
    }


}



