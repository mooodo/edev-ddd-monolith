package com.edev.authority;

import com.edev.authority.service.AuthorityService;
import com.edev.authority.service.RoleService;
import com.edev.authority.service.UserService;
import com.edev.authority.service.impl.AuthorityServiceImpl;
import com.edev.authority.service.impl.RoleServiceImpl;
import com.edev.authority.service.impl.UserServiceImpl;
import com.edev.support.dao.BasicDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OrmConfig {
    @Autowired @Qualifier("basicDao")
    private BasicDao basicDao;
    @Autowired @Qualifier("repository")
    private BasicDao repository;
    @Bean
    public UserService user() {
        return new UserServiceImpl(repository);
    }
    @Bean
    public RoleService role() {
        return new RoleServiceImpl(repository);
    }
    @Bean
    public AuthorityService authority() {
        return new AuthorityServiceImpl(repository);
    }
}
