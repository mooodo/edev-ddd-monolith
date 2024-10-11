package com.edev.authority.service.impl;

import com.edev.authority.entity.Role;
import com.edev.authority.service.RoleService;
import com.edev.support.dao.BasicDao;

import java.util.Collection;
import java.util.List;

import static com.edev.utils.ValidUtils.isNull;

public class RoleServiceImpl implements RoleService {
    private final BasicDao dao;
    public RoleServiceImpl(BasicDao dao) {
        this.dao = dao;
    }
    private void valid(Role role) {
        isNull(role, "role");
        isNull(role.getId(), "id");
        isNull(role.getName(), "name");
    }
    @Override
    public Long create(Role role) {
        valid(role);
        return dao.insert(role);
    }

    @Override
    public void modify(Role role) {
        valid(role);
        dao.update(role);
    }

    @Override
    public void remove(Long roleId) {
        dao.delete(roleId, Role.class);
    }

    @Override
    public Role load(Long roleId) {
        return dao.load(roleId, Role.class);
    }

    @Override
    public Collection<Role> loadAll(List<Long> roleIds) {
        return dao.loadForList(roleIds, Role.class);
    }
}
