package com.edev.security.entity;

import com.edev.authority.entity.Authority;
import com.edev.authority.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

public class DefaultUserDetails implements UserDetails {
    private final User user;
    private final Collection<GrantedAuthority> grantedAuthorities;
    public DefaultUserDetails(User user) {
        this.user = user;
        // add all the authorities of user and role together.
        List<Authority> authorities = new ArrayList<>(user.getAuthorities());
        user.getRoles().forEach(role -> authorities.addAll(role.getAuthorities()));

        // convert authority to GrantedAuthority
        Collection<GrantedAuthority> grantedAuthorities = new HashSet<>();
        authorities.forEach(authority -> grantedAuthorities.add((GrantedAuthority) authority::getName));
        this.grantedAuthorities = grantedAuthorities;
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.grantedAuthorities;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return !user.getAccountExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return !user.getAccountLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return !user.getCredentialsExpired();
    }

    @Override
    public boolean isEnabled() {
        return !user.getDisabled();
    }
}
