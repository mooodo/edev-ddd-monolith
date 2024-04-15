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

    public Boolean getAuthenticated() {
        return "T".equals(authenticated);
    }

    public void setAuthenticated(Boolean authenticated) {
        this.authenticated = (authenticated!=null&&authenticated)?"T":"F";
    }
}
