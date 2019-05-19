package com.autobus.Driver;

import java.io.Serializable;

public class User implements Serializable {

    public String user_email;
    public String token;

    public User(){

    }

    public User(String user_email, String token) {
        this.user_email = user_email;
        this.token = token;
    }
}
