package com.edev.authority;

import com.edev.support.dao.BasicDao;
import com.edev.support.dao.QueryDao;
import com.edev.support.ddd.AutofillQueryServiceImpl;
import com.edev.support.ddd.QueryDaoMybastisImplForDdd;
import com.edev.support.query.QueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QryConfig {
    @Autowired @Qualifier("repositoryWithCache")
    private BasicDao repositoryWithCache;
    @Bean
    public QueryDao userQryDao() {
        return new QueryDaoMybastisImplForDdd(
                "com.edev.authority.entity.User",
                "com.edev.query.dao.UserMapper");
    }
    @Bean
    public QueryService userQry() {
        return new AutofillQueryServiceImpl(
                userQryDao(), repositoryWithCache);
    }
    @Bean
    public QueryDao roleDao() {
        return new QueryDaoMybastisImplForDdd(
                "com.edev.authority.entity.Role",
                "com.edev.query.dao.RoleMapper");
    }
    @Bean
    public QueryService roleQry() {
        return new AutofillQueryServiceImpl(
                roleDao(), repositoryWithCache);
    }
    @Bean
    public QueryDao authorityDao() {
        return new QueryDaoMybastisImplForDdd(
                "com.edev.authority.entity.Authority",
                "com.edev.query.dao.AuthorityMapper");
    }
    @Bean
    public QueryService authorityQry() {
        return new AutofillQueryServiceImpl(
                authorityDao(), repositoryWithCache);
    }
}
