package com.maximilianomicciullo.androidcontactlistapp.Models;

import java.io.Serializable;

/**
 * Created by maximilianomicciullo on 06/09/14.
 */
public class Contact implements Serializable {

    private String name;
    private String phoneTest;
    private int iconID;

    private Long employeeId;
    private String company;
    private String detailsURL;
    private String smallImageURL;
    private String birthdate;
    private Phone phone = new Phone();
    private ContactDetails details = new ContactDetails();

    public Contact() {
    }

    public Contact(String name, String phone, int iconID) {
        this.name = name;
        this.phoneTest = phone;
        this.iconID = iconID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhoneTest(String phoneTest) {
        this.phoneTest = phoneTest;
    }

    public void setIconID(int iconID) {
        this.iconID = iconID;
    }

    public String getName() {
        return name;
    }

    public String getPhoneTest() {
        return phoneTest;
    }

    public int getIconID() {
        return iconID;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getDetailsURL() {
        return detailsURL;
    }

    public void setDetailsURL(String detailsURL) {
        this.detailsURL = detailsURL;
    }

    public String getSmallImageURL() {
        return smallImageURL;
    }

    public void setSmallImageURL(String smallImageURL) {
        this.smallImageURL = smallImageURL;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public Phone getPhone() {
        return phone;
    }

    public void setPhone(Phone phone) {
        this.phone = phone;
    }

    public ContactDetails getDetails() {
        return details;
    }

    public void setDetails(ContactDetails details) {
        this.details = details;
    }
}
