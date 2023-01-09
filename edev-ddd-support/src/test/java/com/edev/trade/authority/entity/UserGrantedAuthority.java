package com.edev.trade.authority.entity;

import com.edev.support.entity.Entity;

public class UserGrantedAuthority extends Entity<Long> {
    private Long id;
    private String available;
    private Long userId;
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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getAuthorityId() {
        return authorityId;
    }

    public void setAuthorityId(Long authorityId) {
        this.authorityId = authorityId;
    }
}
