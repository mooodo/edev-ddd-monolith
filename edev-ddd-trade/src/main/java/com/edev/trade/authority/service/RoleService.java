package com.edev.trade.authority.service;

import com.edev.trade.authority.entity.Role;

public interface RoleService {
    Long createRole(Role role);
    void modifyRole(Role role);
    void removeRole(Long id);
    Role loadRoleById(Long id);
}
