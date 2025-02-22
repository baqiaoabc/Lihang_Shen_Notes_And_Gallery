package presenter.factory;

import presenter.language.Language;

import javax.swing.*;
/**
 *JOptionPaneFactory, as the name suggested, is used to make JOptionPane used in the program
 */
public class JOptionPaneFactory {
    Language language;
    /**
     * @param language the language used in the JOptionPane
     */
    public JOptionPaneFactory(Language language){
        this.language = language;
    }

    /**
     * get the JOptionPane wanted
     * @param instruction to tell the JOptionFactory which JOptionPane should it make* Input "error" to tell there is an error
     *Input "createAccount" to tell the user is making an account
     *Input "succeedAddRoom" to tell a room has been added successfully
     *Input "failAddRoom" failed to add a room
     *Input "succeedCancelEvent" successfully cancel the event
     *Input "failCancelEvent" failed to cancel the event
     *Input "succeedSignUpEvent" successfully signup the event
     *Input "failSignUpEvent" failed to signup the event
     *Input "succeedWaitEvent" successfully get a spot on the waiting list for the event
     *Input "failWaitEvent" failed to get a spot on the waiting list for the event
     *Input "succeedChangeEvent" successfully changed the event
     *Input "failChangeEvent" failed to change the event
     *Input "succeedCreateUser" successfully created an user
     *Input "failCreateUser" failed to create an user
     *Input "succeedCancelEnrollment" successfully cancel the enrollment
     *Input "failCancelEnrollment" failed to cancel the enrollment
     *Input "succeedRemoveWait" successfully cancel the spot for an event in the waiting list
     *Input "failRemoveWait" failed to cancel the spot for an event in the waiting list
     *Input "succeedResetPass" successfully reset the password
     *Input "failResetPass" failed to reset the password
     *Input "succeedCreateAccount" successfully create an account
     *Input "failCreateAccount" failed to create an account
     *Input "succeedCreateEvent" successfully create an event
     *Input "failCreateEvent" failed to create an event
     *
     */
    public void get(String instruction){
        if(instruction == "error"){
            error();
        }else if(instruction == "createAccount"){
            createAccount();
        }else if(instruction == "succeedAddRoom"){
            succeedAddRoom();
        }else if(instruction == "failAddRoom"){
            failAddRoom();
        }else if(instruction == "succeedCancelEvent"){
            succeedCancelEvent();
        }else if(instruction == "failCancelEvent"){
            failCancelEvent();
        }else if(instruction == "succeedSignUpEvent"){
            succeedSignUpEvent();
        }else if(instruction == "failSignUpEvent"){
            failSignUpEvent();
        }else if(instruction == "succeedWaitEvent"){
            succeedWaitEvent();
        }else if(instruction == "failWaitEvent"){
            failWaitEvent();
        }else if(instruction == "succeedChangeEvent"){
            succeedChangeEvent();
        }else if(instruction == "failChangeEvent"){
            failChangeEvent();
        }else if(instruction == "succeedCreateUser"){
            succeedCreateUser();
        }else if (instruction == "failCreateUser"){
            failCreateUser();
        }else if(instruction == "succeedCancelEnrollment"){
            succeedCancelEnrollment();
        }else if(instruction == "failCancelEnrollment"){
            failCancelEnrollment();
        }else if(instruction == "succeedRemoveWait"){
            succeedRemoveWait();
        }else if(instruction == "failRemoveWait"){
            failRemoveWait();
        }else if(instruction == "succeedResetPass"){
            succeedResetPass();
        }else if(instruction == "failResetPass"){
            failResetPass();
        }else if(instruction == "succeedCreateAccount"){
            succeedCreateAccount();
        }else if(instruction == "failCreateAccount"){
            failCreateAccount();
        }else if(instruction == "succeedCreateEvent"){
            succeedCreateEvent();
        }else if(instruction == "failCreateEvent"){
            failCreateEvent();
        }
    }

    private void error(){
        JOptionPane.showMessageDialog(null, language.fail(), language.messageTitle(),
                JOptionPane.ERROR_MESSAGE);
    }

    private void createAccount(){
        JOptionPane.showMessageDialog(null, language.createAccountMessage(), language.messageTitle(),
                JOptionPane.INFORMATION_MESSAGE);
    }

    private void succeedAddRoom(){
        JOptionPane.showMessageDialog(null, language.succeedAddRoomMessage(),
                language.successful(), JOptionPane.INFORMATION_MESSAGE);
    }

    private void failAddRoom(){
        JOptionPane.showMessageDialog(null, language.failAddRoomMessage(),
                language.failed(), JOptionPane.WARNING_MESSAGE);
    }

    private void succeedCancelEvent(){
        JOptionPane.showMessageDialog(null, language.succeedCancelEventMessage(),
                language.successful(), JOptionPane.INFORMATION_MESSAGE);
    }

    private void failCancelEvent(){
        JOptionPane.showMessageDialog(null, language.failCancelEventMessage(),
                language.failed(), JOptionPane.WARNING_MESSAGE);
    }


    private void succeedSignUpEvent(){
        JOptionPane.showMessageDialog(null, language.succeedSignUpEventMessage(),
                language.successful(), JOptionPane.INFORMATION_MESSAGE);
    }



    private void failSignUpEvent(){
        JOptionPane.showMessageDialog(null, language.failSignUpEvent(),
                language.failed(), JOptionPane.WARNING_MESSAGE);
    }


    private void succeedWaitEvent(){
        JOptionPane.showMessageDialog(null, language.succeedWaitEvent(),
                language.successful(), JOptionPane.INFORMATION_MESSAGE);
    }


    private void failWaitEvent(){
        JOptionPane.showMessageDialog(null, language.failWaitEventMessage(),
                language.failed(), JOptionPane.WARNING_MESSAGE);
    }

    private void succeedChangeEvent(){
        JOptionPane.showMessageDialog(null, language.succeedChangeEventMessage(),
                language.successful(), JOptionPane.INFORMATION_MESSAGE);
    }


    private void failChangeEvent(){
        JOptionPane.showMessageDialog(null, language.failChangeEventMessage(),
                language.successful(), JOptionPane.WARNING_MESSAGE);
    }

    private void succeedCreateUser(){
        JOptionPane.showMessageDialog(null, language.succeedCreateUserMessage(),
                language.successful(), JOptionPane.INFORMATION_MESSAGE);
    }


    private  void failCreateUser(){
        JOptionPane.showMessageDialog(null, language.failCreateUserMessage(),
                language.failed(), JOptionPane.WARNING_MESSAGE);
    }


    private void succeedCreateEvent(){
        JOptionPane.showMessageDialog(null, language.succeedCreateEventMessage(),
                language.successful(), JOptionPane.INFORMATION_MESSAGE);
    }


    private void failCreateEvent(){
        JOptionPane.showMessageDialog(null, language.failCreateEventMessage(),
                language.failed(), JOptionPane.WARNING_MESSAGE);
    }

    private void succeedCancelEnrollment(){
        JOptionPane.showMessageDialog(null, language.succeedCancelEnrollmentMessage(),
                language.successful(), JOptionPane.INFORMATION_MESSAGE);
    }

    private void failCancelEnrollment(){
        JOptionPane.showMessageDialog(null, language.failCancelEnrollmentMessage(),
                language.failed(), JOptionPane.WARNING_MESSAGE);
    }


    private void succeedRemoveWait(){
        JOptionPane.showMessageDialog(null, language.succeedRemoveWaitMessage(),
                language.successful(), JOptionPane.INFORMATION_MESSAGE);
    }


    private void failRemoveWait(){
        JOptionPane.showMessageDialog(null, language.failRemoveWaitMessage(),
                language.failed(), JOptionPane.WARNING_MESSAGE);
    }


    private void succeedResetPass(){
        JOptionPane.showMessageDialog(null, language.succeedResetPasswordMessage(),
                language.successful(), JOptionPane.INFORMATION_MESSAGE);
    }


    private void failResetPass(){
        JOptionPane.showMessageDialog(null, language.failResetPasswordMessage(),
                language.failed(), JOptionPane.WARNING_MESSAGE);
    }


    private void succeedCreateAccount(){
        JOptionPane.showMessageDialog(null, language.succeedCreateAccountMessage(),
                language.successful(), JOptionPane.INFORMATION_MESSAGE);
    }

    private void failCreateAccount(){
        JOptionPane.showMessageDialog(null, language.failCreateAccount(),
                language.failed(), JOptionPane.WARNING_MESSAGE);
    }



}
