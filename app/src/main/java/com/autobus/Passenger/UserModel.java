package com.autobus.Passenger;

import java.io.Serializable;

public class UserModel implements Serializable {
    public String tmpName;

    public UserModel() {
    }

    public UserModel(String tmpName) {
        this.tmpName = tmpName;
    }
}
