package com.edev.security.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class SecurityHelper {
    @Autowired
    private PasswordEncoder passwordEncoder;
    public boolean passwordIsMatch(String source, String target) {
        return passwordEncoder.matches(source, target);
    }
    private Authentication getCurrentUser() {
        Authentication currentUser = SecurityContextHolder.getContext().getAuthentication();
        if(currentUser==null)
            throw new BadCredentialsException("No Authentication for the current user!");
        return currentUser;
    }
    public String getMyName() {
        Authentication currentUser = getCurrentUser();
        return currentUser.getName();
    }
    public String getPassword() {
        Authentication currentUser = getCurrentUser();
        return currentUser.getCredentials().toString();
    }
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Authentication currentUser = getCurrentUser();
        return currentUser.getAuthorities();
    }
}
