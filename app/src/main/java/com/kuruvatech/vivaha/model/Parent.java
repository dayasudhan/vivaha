package com.kuruvatech.vivaha.model;

/**
 * Created by gagan on 3/3/2017.
 */
public class Parent {
    String phone;
    String name;
    String occupation;

    public Parent() {
        phone = new String("");
        name = new String("");
        occupation = new String("");
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }
}
