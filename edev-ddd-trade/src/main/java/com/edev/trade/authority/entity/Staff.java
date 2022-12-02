package com.edev.trade.authority.entity;

public class Staff extends User {
    private String gender;
    private String position;

    public Staff() { super();
    }

    public Staff(Long id, String name, String password, String userType, String gender, String position) {
        super(id, name, password, userType);
        this.gender = gender;
        this.position = position;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }
}
