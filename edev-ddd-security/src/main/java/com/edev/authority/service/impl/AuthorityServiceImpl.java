package com.edev.authority.service.impl;

import com.edev.authority.entity.Authority;
import com.edev.authority.service.AuthorityService;
import com.edev.support.dao.BasicDao;

public class AuthorityServiceImpl implements AuthorityService {
    private final BasicDao dao;

    public AuthorityServiceImpl(BasicDao dao) {
        this.dao = dao;
    }
    @Override
    public Long createAuthority(Authority authority) {
        return dao.insert(authority);
    }

    @Override
    public void modifyAuthority(Authority authority) {
        dao.update(authority);
    }

    @Override
    public void deleteAuthority(Long id) {
        dao.delete(id, Authority.class);
    }

    @Override
    public Authority getAuthority(Long id) {
        return dao.load(id, Authority.class);
    }
}
