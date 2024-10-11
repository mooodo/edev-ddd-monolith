package com.edev.security;

import com.edev.security.jwt.JwtAuthenticationEntryPoint;
import com.edev.security.jwt.JwtAuthenticationFilter;
import com.edev.security.jwt.LoginFailureHandler;
import com.edev.security.jwt.LoginSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Value("${security.loginUrl}")
    private String loginUrl;
    @Value("${security.whiteListUrls}")
    private String whiteListUrls;
    @Autowired
    private LoginFailureHandler loginFailureHandler;
    @Autowired
    private LoginSuccessHandler loginSuccessHandler;
    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() throws Exception {
        return new JwtAuthenticationFilter(authenticationManager());
    }
    // Define which requests need authenticate and authorise.
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().disable();
        http.csrf().disable();
        http.authorizeRequests()
                .antMatchers(whiteListUrls.split(",")).permitAll()
                .anyRequest().authenticated()

                .and()
                .formLogin()
//                .loginPage(loginUrl)
                .failureHandler(loginFailureHandler)
                .successHandler(loginSuccessHandler)
                .permitAll()
                .and().httpBasic();
        http.exceptionHandling()
                .authenticationEntryPoint(jwtAuthenticationEntryPoint);
        http.addFilter(jwtAuthenticationFilter());
    }
}
