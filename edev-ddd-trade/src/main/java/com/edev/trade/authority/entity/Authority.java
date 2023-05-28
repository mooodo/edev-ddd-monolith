package com.edev.trade.authority.entity;

import com.edev.support.entity.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class Authority extends Entity<Long> {
    private Long id;
    private String name;
    private String authenticated;
    private List<User> users = new ArrayList<>();
    private List<Role> roles = new ArrayList<>();

    public static Authority build() {
        return new Authority();
    }

    public Authority setValues(Long id, String name, Boolean authenticated) {
        this.setId(id);
        this.setName(name);
        this.setAuthenticated(authenticated);
        return this;
    }

    public boolean isAuthenticated() {
        return "T".equals(authenticated);
    }

    public void setAuthenticated(boolean authenticated) {
        this.authenticated = authenticated?"T":"F";
    }
}
