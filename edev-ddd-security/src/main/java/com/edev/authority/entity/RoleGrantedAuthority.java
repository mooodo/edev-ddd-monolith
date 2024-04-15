package com.edev.authority.entity;

import com.edev.support.entity.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class RoleGrantedAuthority extends Entity<Long> {
    private Long id;
    private String available;
    private Long roleId;
    private Long authorityId;

    public Boolean getAvailable() {
        return "T".equals(available);
    }

    public void setAvailable(Boolean available) {
        this.available = (available!=null&&available) ? "T" : "F";
    }
}
