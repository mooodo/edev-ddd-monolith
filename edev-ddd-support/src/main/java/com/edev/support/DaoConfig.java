package com.edev.support;

import com.edev.support.cache.BasicCache;
import com.edev.support.cache.CacheEntityDao;
import com.edev.support.dao.BasicDao;
import com.edev.support.dao.impl.BasicDaoMybatisImpl;
import com.edev.support.ddd.Repository;
import com.edev.support.subclass.SubClassPlusDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DaoConfig {
    @Bean
    public BasicDao basicDaoMybatis() {
        return new BasicDaoMybatisImpl();
    }
    @Bean
    public BasicDao basicDao() {
        return new SubClassPlusDao(basicDaoMybatis());
    }
    @Bean
    public Repository repository() {
        return new Repository(basicDao());
    }
    @Autowired @Qualifier("redisCache")
    private BasicCache basicCache;
    @Bean
    public CacheEntityDao basicDaoWithCache() {
        return new CacheEntityDao(basicDao(), basicCache);
    }
    @Bean
    public CacheEntityDao repositoryWithCache() {
        return new CacheEntityDao(repository(), basicCache);
    }
}
