package presenter;

import controller.ConferenceSystem;

/**
 * Presenter is the initial presenter before user opens their corresponding menu
 */
public class Presenter {
    public int type;
    ConferenceSystem cs;


    /**
     * construct the Presenter
     */
    public Presenter(){
        this.cs = new ConferenceSystem();
    }

    /**
     * sign in base on user input
     * @param username the username entered by the user
     * @param password the password made by the user
     * @param window the IUpdate Window whose update method we need to use
     */
    public void signIn( String username, String password, IUpdate window){
        type = cs.login(username, password);
        if(type == 0){
            window.update("Speaker");
        }else if(type == 1){
            window.update("Organizer");
        }else if(type == 2){
            window.update("Attendee");
        }else{
            window.update("error");
        }
    }

    /**
     * sign up an account based on the user input
     * @param username the username entered by the user
     * @param password the password made by the user
     * @param window the IUpdate Window whose update method we need to use
     */
    public void signup(String username, String password, IUpdate window){
        Boolean bool = cs.signup(username, password);
        System.out.println(bool);
        if (bool){
            window.update("succeedCreateAccount");
        }else{
            window.update("failCreateAccount");
        }
    }

    /**
     * Open messenger
     * @param menu  the IUpdate menu whose update method we need to use
     */
    public void openMessenger(IUpdate menu){
        if (type == 2){
            menu.update("Attendee");
        }
        if (type == 1){
            menu.update("Organizer");
        }
        if (type == 0){
            menu.update("Speaker");
        }
    }






}
