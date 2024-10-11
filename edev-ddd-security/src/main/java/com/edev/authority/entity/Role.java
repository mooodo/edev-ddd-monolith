package com.edev.authority.entity;

import com.edev.support.entity.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.Collection;

@Data
@EqualsAndHashCode(callSuper = true)
public class Role extends Entity<Long> {
    private Long id;
    private String name;
    private String description;
    private Collection<Authority> authorities = new ArrayList<>();
    private Collection<User> users = new ArrayList<>();

    public void addAuthority(Authority authority) {
        this.authorities.add(authority);
    }

    public void addUser(User user) {
        this.users.add(user);
    }
}
