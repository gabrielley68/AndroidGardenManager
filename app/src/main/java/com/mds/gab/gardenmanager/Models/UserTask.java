package com.mds.gab.gardenmanager.Models;

import io.realm.RealmObject;

public class UserTask extends RealmObject {

    private User user;
    private Task task;
    private boolean done;

    public UserTask(){

    }

    public UserTask(User user, Task task) {
        this.user = user;
        this.task = task;
        this.done = false;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }
}
