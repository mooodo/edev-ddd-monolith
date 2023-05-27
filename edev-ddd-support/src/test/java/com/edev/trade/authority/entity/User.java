package com.edev.trade.authority.entity;

import com.edev.support.entity.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class User extends Entity<Long> {
    private Long id;
    private String name;
    private String password;
    private List<Authority> authorities = new ArrayList<>();

    public static User build() {
        return new User();
    }

    public User setValues(Long id, String name, String password) {
        this.id = id;
        this.name = name;
        this.password = password;
        return this;
    }

    public void addAuthority(Authority authority) {
        this.authorities.add(authority);
    }
}
