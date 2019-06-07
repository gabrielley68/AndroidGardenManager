package com.mds.gab.gardenmanager.Manager;

import com.mds.gab.gardenmanager.Models.User;

public class UserManager {

    private static final UserManager instance = new UserManager();

    private User user;

    public static UserManager getInstance(){
        return instance;
    }

    public User getUser(){
        return user;
    }

    public void setUser(User user){
        this.user = user;
    }
}
