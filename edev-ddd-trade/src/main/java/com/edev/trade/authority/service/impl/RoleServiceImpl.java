package com.edev.trade.authority.service.impl;

import com.edev.support.dao.BasicDao;
import com.edev.trade.authority.entity.Role;
import com.edev.trade.authority.service.RoleService;
import lombok.NonNull;

import java.util.Collection;

public class RoleServiceImpl implements RoleService {
    private final BasicDao dao;

    public RoleServiceImpl(BasicDao dao) {
        this.dao = dao;
    }

    @Override
    public Long create(@NonNull Role role) {
        return dao.insert(role);
    }

    @Override
    public void modify(@NonNull Role role) {
        dao.update(role);
    }

    @Override
    public void remove(@NonNull Long id) {
        dao.delete(id, Role.class);
    }

    @Override
    public Role load(@NonNull Long id) {
        return dao.load(id, Role.class);
    }

    @Override
    public Role loadByName(@NonNull String roleName) {
        Role template = new Role();
        template.setName(roleName);
        Collection<Role> roles = dao.loadAll(template);
        if(roles==null||roles.isEmpty()) return null;
        return roles.iterator().next();
    }
}
