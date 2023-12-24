package com.edev.authority.entity;

import com.edev.support.entity.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class UserGrantedRole extends Entity<Long> {
    private Long id;
    private String available;
    private Long userId;
    private Long roleId;

    public boolean isAvailable() {
        return "T".equals(available);
    }

    public void setAvailable(boolean available) {
        this.available = available?"T":"F";
    }
}
