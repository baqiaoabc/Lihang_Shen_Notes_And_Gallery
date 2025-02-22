package controller;

import usecase.EventManager;
import usecase.MessageManager;
import usecase.RoomManager;
import usecase.UserManager;

abstract class subSystem {

    EventManager em = new EventManager();
    MessageManager mm = new MessageManager();
    RoomManager rm = new RoomManager();
    UserManager um = new UserManager();
    int user;

    /**
     * set the user
     */
    void setUser(int userID){
        this.user = userID;
    }
}
