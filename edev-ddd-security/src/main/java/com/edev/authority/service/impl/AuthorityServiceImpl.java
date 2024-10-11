package com.edev.authority.service.impl;

import com.edev.authority.entity.Authority;
import com.edev.authority.service.AuthorityService;
import com.edev.support.dao.BasicDao;

import static com.edev.utils.ValidUtils.isNull;

public class AuthorityServiceImpl implements AuthorityService {
    private final BasicDao dao;
    public AuthorityServiceImpl(BasicDao dao) {
        this.dao = dao;
    }
    private void valid(Authority authority) {
        isNull(authority, "authority");
        isNull(authority.getId(), "id");
        isNull(authority.getName(), "name");
    }
    @Override
    public Long create(Authority authority) {
        valid(authority);
        return dao.insert(authority);
    }

    @Override
    public void modify(Authority authority) {
        valid(authority);
        dao.update(authority);
    }

    @Override
    public void remove(Long authorityId) {
        dao.delete(authorityId, Authority.class);
    }

    @Override
    public Authority load(Long authorityId) {
        return dao.load(authorityId, Authority.class);
    }
}
