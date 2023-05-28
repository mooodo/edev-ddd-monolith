package com.edev.trade.authority.service.impl;

import com.edev.support.dao.BasicDao;
import com.edev.trade.authority.entity.Role;
import com.edev.trade.authority.service.RoleService;

public class RoleServiceImpl implements RoleService {
    private final BasicDao dao;

    public RoleServiceImpl(BasicDao dao) {
        this.dao = dao;
    }

    @Override
    public Long createRole(Role role) {
        return dao.insert(role);
    }

    @Override
    public void modifyRole(Role role) {
        dao.update(role);
    }

    @Override
    public void removeRole(Long id) {
        dao.delete(id, Role.class);
    }

    @Override
    public Role loadRoleById(Long id) {
        return dao.load(id, Role.class);
    }
}
