package com.autobus.SubAdmin;

public class DriverModel {

    private String driverName;
    private String driverPassword;
    private String driverID;
    private String driverPhone;
    private String busNumber;

    public DriverModel(String driverName, String driverPassword, String driverID, String driverPhone, String busNumber) {
        this.driverName = driverName;
        this.driverPassword = driverPassword;
        this.driverID = driverID;
        this.driverPhone = driverPhone;
        this.busNumber = busNumber;
    }

    public String getDriverName() {
        return driverName;
    }

    public String getDriverPassword() {
        return driverPassword;
    }

    public String getDriverID() {
        return driverID;
    }

    public String getDriverPhone() {
        return driverPhone;
    }

    public String getBusNumber() {
        return busNumber;
    }
}
