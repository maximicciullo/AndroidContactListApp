package com.maximilianomicciullo.androidcontactlistapp;

/**
 * Created by maximilianomicciullo on 06/09/14.
 */
public class Contact {

    private String name;
    private String phone;
    private int iconID;

    public Contact(String name, String phone, int iconID) {
        this.name = name;
        this.phone = phone;
        this.iconID = iconID;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public int getIconID() {
        return iconID;
    }
}
