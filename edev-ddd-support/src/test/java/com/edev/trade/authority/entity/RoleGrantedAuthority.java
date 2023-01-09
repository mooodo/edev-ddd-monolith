package com.edev.trade.authority.entity;

import com.edev.support.entity.Entity;

public class RoleGrantedAuthority extends Entity<Long> {
    private Long id;
    private String available;
    private Long roleId;
    private Long authorityId;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public boolean isAvailable() {
        return "T".equals(available);
    }

    public void setAvailable(boolean available) {
        this.available = available?"T":"F";
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Long getAuthorityId() {
        return authorityId;
    }

    public void setAuthorityId(Long authorityId) {
        this.authorityId = authorityId;
    }
}
