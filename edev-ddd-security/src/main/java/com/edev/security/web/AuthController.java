package com.edev.security.web;

import com.edev.security.entity.Credentials;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@RestController
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @GetMapping(value = "/login", produces = "application/json")
    public void login(@RequestBody Credentials userAndPassword) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        userAndPassword.getUsername(),
                        userAndPassword.getPassword()
                )
        );
    }

    @RequestMapping(value = "/userinfo", produces = "application/json")
    public Map<String, Object> userinfo(UsernamePasswordAuthenticationToken token) {
        Map<String, Object> userInfo = new HashMap<>();
        userInfo.put("user", token.getPrincipal());
        userInfo.put("authorities", AuthorityUtils.authorityListToSet(token.getAuthorities()));
        return userInfo;
    }
    @RequestMapping(value = "authenticationInfo", produces = "application/json")
    public Map<String, Object> authenticationInfo(Authentication authentication) {
        String username = authentication.getName();
        Collection<? extends GrantedAuthority> grantedAuthorities = authentication.getAuthorities();
        Map<String, Object> authenticationInfo = new HashMap<>();
        authenticationInfo.put("username", username);
        authenticationInfo.put("authorities", AuthorityUtils.authorityListToSet(grantedAuthorities));
        return authenticationInfo;
    }
}
