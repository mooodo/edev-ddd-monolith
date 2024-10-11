package com.edev.security.service;

import com.edev.security.utils.SecurityHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class DefaultAuthenticationProvider implements AuthenticationProvider, AuthenticationManager {
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private SecurityHelper securityHelper;
    @Override
    public Authentication authenticate(Authentication authentication) {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        if(userDetails==null) throw new BadCredentialsException("The user not exists!");
        if(securityHelper.passwordIsMatch(password, userDetails.getPassword()))
            return new UsernamePasswordAuthenticationToken(username, password,
                    userDetails.getAuthorities());
        throw new BadCredentialsException("The username or password is wrong!");
    }

    @Override
    public boolean supports(Class<?> authenticationType) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authenticationType);
    }
}
