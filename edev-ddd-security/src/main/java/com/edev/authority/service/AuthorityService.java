package com.edev.authority.service;

import com.edev.authority.entity.Authority;

public interface AuthorityService {
    Long createAuthority(Authority authority);
    void modifyAuthority(Authority authority);
    void deleteAuthority(Long id);
    Authority getAuthority(Long id);
}
