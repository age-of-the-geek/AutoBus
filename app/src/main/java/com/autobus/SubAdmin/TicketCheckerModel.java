package com.autobus.SubAdmin;

public class TicketCheckerModel  {

    private String tkCheckerName;
    private String tkCheckerPassword;
    private String tkCheckerID;
    private String tkCheckerPhone;
    private String busNumber;

    public TicketCheckerModel(String tkCheckerName, String tkCheckerPassword, String tkCheckerID, String tkCheckerPhone, String busNumber) {
        this.tkCheckerName = tkCheckerName;
        this.tkCheckerPassword = tkCheckerPassword;
        this.tkCheckerID = tkCheckerID;
        this.tkCheckerPhone = tkCheckerPhone;
        this.busNumber = busNumber;
    }

    public String getTkCheckerName() {
        return tkCheckerName;
    }

    public String getTkCheckerPassword() {
        return tkCheckerPassword;
    }

    public String getTkCheckerID() {
        return tkCheckerID;
    }

    public String getTkCheckerPhone() {
        return tkCheckerPhone;
    }

    public String getBusNumber() {
        return busNumber;
    }
}
