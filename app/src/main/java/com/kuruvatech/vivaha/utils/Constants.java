package com.kuruvatech.vivaha.utils;

/**
 * Created by gagan on 3/3/2017.
 */
public class Constants {
    public static final String LOCALHOST_URL = "http://192.168.1.104:3000";
    public static final String RELEASE_URL = "https://madhuve.herokuapp.com";
    public static final String DEBUG_URL = "https://kuruva.herokuapp.com";
    public static final String MAIN_URL =RELEASE_URL;
    public static final String ORDER_URL = MAIN_URL + "/v1/vendor/order/";
    public static final String LOGIN_URL =  MAIN_URL +"/v1/m/login";
    public static final String GET_MENU = MAIN_URL +"/v1/vendor/menu/";
    public static final String UPDATE_MENU= MAIN_URL + "/v1/vendor/menu/item/";
    public static final String GET_STATUS_URL = MAIN_URL +"/v1/vendor/order/status/";
    public static final String POST_ISOPEN = MAIN_URL +"/v1/vendor/isopen/";
    public static final String GET_PROFILE_INFO  = MAIN_URL +"/v1/profile/info/";
    public static final String GET_ORDER_BY_ID = MAIN_URL + "/v1/vendor/order_by_id/";
    public static final String FIREBASE_APP = "https://project-8598805513533999178.firebaseio.com";
    //To store the firebase id in shared preferences
    public static final String UNIQUE_ID = "uniqueid";
    public static final String NOTIFICATION_ID = "NOTIFICATION_ID";

    public static final String SECUREKEY_KEY = "securekey";
    public static final String VERSION_KEY = "version";
    public static final String CLIENT_KEY = "client";

    public static final String SECUREKEY_VALUE = "ORql2BHQq9ku8eUX2bGHjFmurqG84x2rkDQUNq9Peelw";
    public static final String VERSION_VALUE = "1";
    public static final String CLIENT_VALUE = "tunga";
}