package com.edev.security.service;

import com.edev.authority.entity.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface UserDetailsManager {
    Long createUser(User user);

    void modifyUser(User user);

    void removeUser(String username);

    void changePassword(String oldPwd, String newPwd);

    User loadUserByUsername(String username) throws UsernameNotFoundException;
}
