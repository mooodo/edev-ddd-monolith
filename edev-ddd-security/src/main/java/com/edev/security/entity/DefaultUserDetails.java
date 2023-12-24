package com.edev.security.entity;

import com.edev.authority.entity.Authority;
import com.edev.authority.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Stream;

public class DefaultUserDetails implements UserDetails {
    private final User user;
    private final Collection<GrantedAuthority> grantedAuthorities;

    public DefaultUserDetails(User user) {
        this.user = user;
        this.grantedAuthorities = new HashSet<>();
        List<Authority> authorities = new ArrayList<>();
        this.user.getRoles().forEach(role -> authorities.addAll(role.getAuthorities()));
        authorities.addAll(this.user.getAuthorities());
        authorities.stream().distinct().forEach(authority -> {
            GrantedAuthority grantedAuthority = new DefaultGrantedAuthority(authority);
            grantedAuthorities.add(grantedAuthority);
        });
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
        return user.getName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return !user.isAccountExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return !user.isAccountLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return !user.isCredentialsExpired();
    }

    @Override
    public boolean isEnabled() {
        return !user.isDisabled();
    }
}
