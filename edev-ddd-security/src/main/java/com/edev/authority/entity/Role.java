package com.edev.authority.entity;

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

    public static Role build() {
        return new Role();
    }

    public Role setValues(Long id, String name, String comment) {
        this.id = id;
        this.name = name;
        this.comment = comment;
        return this;
    }

    public void addAuthority(Authority authority) {
        this.authorities.add(authority);
    }

    public void addUser(User user) {
        this.users.add(user);
    }
}
