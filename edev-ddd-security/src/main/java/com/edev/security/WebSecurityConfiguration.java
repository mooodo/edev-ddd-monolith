package com.edev.security;

import com.edev.security.csrf.DbCsrfTokenRepository;
import com.edev.support.dao.BasicDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRepository;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Bean
    public CsrfTokenRepository csrfTokenRepository() {
        return new CookieCsrfTokenRepository();
    }

    @Autowired @Qualifier("basicDao")
    private BasicDao basicDao;

    @Bean
    public DbCsrfTokenRepository dbCsrfTokenRepository() {
        return new DbCsrfTokenRepository(basicDao);
    }
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //http.csrf().csrfTokenRepository(dbCsrfTokenRepository());
        http.csrf().ignoringAntMatchers("/orm/userDetailsManager/*");
        http.authorizeRequests()
                .anyRequest().authenticated().and()
                .formLogin().and()
                .httpBasic();
    }

    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private AuthenticationProvider authenticationProvider;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).and()
                .authenticationProvider(authenticationProvider);
    }
}
