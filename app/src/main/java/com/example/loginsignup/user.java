package com.example.loginsignup;

import com.google.firebase.firestore.PropertyName;

public class user {

    public String Name,email, password, confirmpassword;

    private boolean isWorker;

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public boolean isWorker() {
        return isWorker;
    }

    public void setWorker(boolean worker) {
        isWorker = worker;
    }

    public user(){

    }
    public user(String email, String password, String confirmpassword){
        this.email = email;
        this.password = password;
        this.confirmpassword =  confirmpassword;
    }
}
