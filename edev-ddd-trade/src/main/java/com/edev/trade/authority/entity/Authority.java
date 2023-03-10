package com.edev.trade.authority.entity;

import com.edev.support.entity.Entity;

import java.util.ArrayList;
import java.util.List;

public class Authority extends Entity<Long> {
    private Long id;
    private String name;
    private String url;
    private String available;
    private List<User> users = new ArrayList<>();
    private List<Role> roles = new ArrayList<>();

    public Authority() {
    }

    public Authority(Long id, String name, String url, Boolean available) {
        this.id = id;
        this.name = name;
        this.url = url;
        setAvailable(available);
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isAvailable() {
        return "T".equals(available);
    }

    public void setAvailable(boolean available) {
        this.available = available?"T":"F";
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }
}
