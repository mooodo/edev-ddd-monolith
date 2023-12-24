package com.edev.security.service;

import com.edev.authority.entity.User;
import com.edev.authority.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service("userDetailsManager")
public class DefaultUserDetailsManager implements UserDetailsManager {
    @Autowired
    private UserService userService;
    private final PasswordEncoder passwordEncoder = DefaultPasswordEncoder.build();

    private void encodePassword(User user) {
        String password = passwordEncoder.encode(user.getPassword());
        user.setPassword(password);
    }

    @Override
    @PreAuthorize("hasAuthority('registerUser')")
    public Long createUser(User user) {
        encodePassword(user);
        return userService.register(user);
    }

    @Override
    @PreAuthorize("hasAuthority('modifyUser')")
    public void modifyUser(User user) {
        encodePassword(user);
        userService.modify(user);
    }

    @Override
    @PreAuthorize("hasAuthority('removeUser')")
    public void removeUser(String username) {
        if(username==null) return;
        User template = User.build();
        template.setName(username);
        userService.delete(template);
    }

    @Override
    public void changePassword(String oldPwd, String newPwd) {
        Authentication currentUser = SecurityContextHolder.getContext().getAuthentication();
        if(currentUser==null)
            throw new BadCredentialsException("No Authentication for the current user!");
        String username = currentUser.getName();
        User user = loadUserByUsername(username);
        if(!passwordEncoder.matches(oldPwd, user.getPassword()))
            throw new BadCredentialsException("Wrong Password!");
        user.setPassword(newPwd);
        modifyUser(user);
    }

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.loadByName(username);
        if(user==null) throw new UsernameNotFoundException(String.format("No found the user[%s]", username));
        return user;
    }
}
