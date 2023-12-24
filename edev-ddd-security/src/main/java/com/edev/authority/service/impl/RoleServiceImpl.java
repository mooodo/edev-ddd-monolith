package com.edev.authority.service.impl;

import com.edev.authority.entity.Role;
import com.edev.authority.service.RoleService;
import com.edev.support.dao.BasicDao;

import java.util.Collection;

public class RoleServiceImpl implements RoleService {
    private final BasicDao dao;

    public RoleServiceImpl(BasicDao dao) {
        this.dao = dao;
    }

    @Override
    public Long create(Role role) {
        return dao.insert(role);
    }

    @Override
    public void modify(Role role) {
        dao.update(role);
    }

    @Override
    public void remove(Long id) {
        dao.delete(id, Role.class);
    }

    @Override
    public Role load(Long id) {
        return dao.load(id, Role.class);
    }

    @Override
    public Role loadByName(String roleName) {
        Role template = Role.build();
        template.setName(roleName);
        Collection<Role> roles = dao.loadAll(template);
        if(roles==null||roles.isEmpty()) return null;
        return roles.iterator().next();
    }
}
