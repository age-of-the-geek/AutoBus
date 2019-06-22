package com.autobus.Passenger;

public class bus_data {


    private String day;
    private String image;
    private String bus_number;
    private String bus_total_seats;
    private String bus_available_seats;
    private String bus_from;
    private String bus_to;
    private String bus_leaving_time;
    private String bus_reaching_time;
    private String bus_driver_name;
    private String bus_ticketchecker_name;
    private String bus_break_time;
    private String bus_company;
    private String ticket_price;


    public bus_data(String bus_number, String bus_total_seats, String bus_available_seats,
                    String bus_from, String bus_to, String bus_leaving_time, String bus_reaching_time,
                    String bus_driver_name, String bus_ticketchecker_name, String
                            bus_break_time, String bus_company, String bus_image, String day, String ticket_price) {

        this.day = day;
        this.image = bus_image;
        this.bus_number = bus_number;
        this.bus_total_seats = bus_total_seats;
        this.bus_available_seats = bus_available_seats;
        this.bus_from = bus_from;
        this.bus_to = bus_to;
        this.bus_leaving_time = bus_leaving_time;
        this.bus_reaching_time = bus_reaching_time;
        this.bus_driver_name = bus_driver_name;
        this.bus_ticketchecker_name = bus_ticketchecker_name;
        this.bus_break_time = bus_break_time;
        this.bus_company = bus_company;
        this.ticket_price = ticket_price;
    }

    public String getDay() {
        return day;
    }

    public String getImage() {
        return image;
    }

    public String getbus_number() {
        return bus_number;
    }

    public String getbus_total_seats() {
        return bus_total_seats;
    }

    public String getbus_available_seats() {
        return bus_available_seats;
    }

    public String getBus_from() {
        return bus_from;
    }

    public String getBus_to() {
        return bus_to;
    }

    public String getbus_leaving_time() {
        return bus_leaving_time;
    }

    public String getbus_reaching_time() {
        return bus_reaching_time;
    }

    public String getbus_driver_name() {
        return bus_driver_name;
    }

    public String getbus_ticketchecker_name() {
        return bus_ticketchecker_name;
    }

    public String getbus_break_time() {
        return bus_break_time;
    }

    public String getbus_company() {
        return bus_company;
    }

    public String getTicket_price() {
        return ticket_price;
    }

}

