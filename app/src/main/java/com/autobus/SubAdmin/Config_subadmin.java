package com.autobus.SubAdmin;

public class Config_subadmin {


        //URL to our tk_checker_login.php file
        public static final String LOGIN_URL = "http://192.168.43.197/AutoBus/subadmin_login.php";
        public static final String LOAD_LOGO = "http://192.168.43.197/AutoBus/show_company_name.php";

        //Keys for email and password as defined in our $_POST['key'] in tk_checker_login.php
        public static final String KEY_EMAIL = "email";
        public static final String KEY_PASSWORD = "password";

        //If server response is equal to this that means tk_checker_login is successful
        public static final String LOGIN_SUCCESS = "success";

        //Keys for Sharedpreferences
        //This would be the name of our shared preferences
        public static final String SHARED_PREF_NAME = "autobus";

        //This would be used to store the email of current logged in user
        public static final String EMAIL_SHARED_PREF = "email";

        //We will use this to store the boolean in sharedpreference to track user is loggedin or not
        public static final String LOGGEDIN_SHARED_PREF = "loggedin";
}
