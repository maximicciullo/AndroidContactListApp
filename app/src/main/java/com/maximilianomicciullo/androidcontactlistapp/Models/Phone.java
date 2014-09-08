package com.maximilianomicciullo.androidcontactlistapp.Models;

import java.io.Serializable;

/**
 * Created by maximilianomicciullo on 07/09/14.
 */
public class Phone implements Serializable {

    private String work;
    private String home;
    private String mobile;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getHome() {
        return home;
    }

    public void setHome(String home) {
        this.home = home;
    }

    public String getWork() {
        return work;
    }

    public void setWork(String work) {
        this.work = work;
    }

}
