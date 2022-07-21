package com.eapp.sirjefffharthart;

public class Users {
    String wiw1;
    String wiw2;
    String hob;

    public Users(){

    }

    public Users(String hob) {
        this.hob = hob;
    }

    public String getHob() {
        return hob;
    }

    public void setHob(String hob) {
        this.hob = hob;
    }

    @Override
    public String toString() {
        return "My Hobby :  " + hob;
    }
}
