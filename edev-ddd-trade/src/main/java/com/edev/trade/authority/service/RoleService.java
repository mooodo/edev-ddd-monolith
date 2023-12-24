package com.edev.trade.authority.service;

import com.edev.trade.authority.entity.Role;

public interface RoleService {
    Long create(Role role);
    void modify(Role role);
    void remove(Long roleId);
    Role load(Long roleId);
    Role loadByName(String roleName);
}
