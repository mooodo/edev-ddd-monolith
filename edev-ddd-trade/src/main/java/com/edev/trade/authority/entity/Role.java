package com.edev.trade.authority.entity;

import com.edev.support.entity.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class Role extends Entity<Long> {
    private Long id;
    private String name;
    private String comment;
    private List<Authority> authorities = new ArrayList<>();
    private List<User> users = new ArrayList<>();

    public void addAuthority(Authority authority) {
        this.authorities.add(authority);
    }

    public void addUser(User user) {
        this.users.add(user);
    }
}
