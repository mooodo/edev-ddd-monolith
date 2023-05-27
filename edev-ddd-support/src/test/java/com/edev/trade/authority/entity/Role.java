package com.edev.trade.authority.entity;

import com.edev.support.entity.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class Role extends Entity<Long> {
    private Long id;
    private String name;
    private String comment;
    private List<Authority> authorities;

    public static Role build() {
        return new Role();
    }

    public Role setValues(Long id, String name, String comment) {
        this.id = id;
        this.name = name;
        this.comment = comment;
        return this;
    }
}
