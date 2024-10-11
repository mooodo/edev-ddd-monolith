package com.edev.authority.entity;

import com.edev.support.entity.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.Collection;

@Data
@EqualsAndHashCode(callSuper = true)
public class Authority extends Entity<Long> {
    private Long id;
    private String name;
    private String description;
    private Collection<User> users = new ArrayList<>();
    private Collection<Role> roles = new ArrayList<>();
}
