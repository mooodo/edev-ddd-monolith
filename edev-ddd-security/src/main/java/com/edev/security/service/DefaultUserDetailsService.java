package com.edev.security.service;

import com.edev.authority.entity.Role;
import com.edev.authority.entity.User;
import com.edev.authority.service.RoleService;
import com.edev.authority.service.UserService;
import com.edev.security.entity.DefaultUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service("userDetailsService")
public class DefaultUserDetailsService implements UserDetailsService {
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.loadByName(username);
        if(user==null) throw new UsernameNotFoundException(String.format("No found the user[%s]", username));
        user.getRoles().forEach(role -> role.setAuthorities(roleService.load(role.getId()).getAuthorities()));
        return new DefaultUserDetails(user);
    }
}
