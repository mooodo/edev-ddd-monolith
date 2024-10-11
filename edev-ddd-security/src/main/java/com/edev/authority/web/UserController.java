package com.edev.authority.web;

import com.edev.authority.entity.User;
import com.edev.authority.service.UserService;
import com.edev.security.utils.SecurityHelper;
import com.edev.support.entity.ResultSet;
import com.edev.support.query.QueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("user")
public class UserController {
    @Autowired
    private UserService service;
    @Autowired
    private SecurityHelper securityHelper;
    @Value("${security.passwordEncoder}")
    private String passwordEncoder;
    private void encodePassword(User user) {
        String password = String.format("{%s}%s",passwordEncoder,user.getPassword());
        user.setPassword(password);
    }
    @PostMapping("register")
    public Long register(@RequestBody User user) {
        encodePassword(user);
        return service.register(user);
    }
    @PostMapping("modify")
    @PreAuthorize("(#user.username == authentication.principal) or (hasAuthority('administrator'))")
    public void modify(@RequestBody User user) {
        encodePassword(user);
        service.modify(user);
    }
    @GetMapping("remove")
    @PreAuthorize("hasAuthority('administrator')")
    public void remove(Long userId) {
        service.remove(userId);
    }
    @GetMapping("load")
    @PreAuthorize("hasAuthority('administrator')")
    public User load(Long userId) {
        return service.load(userId);
    }
    @GetMapping("showMe")
    public User showMe() {
        String username = securityHelper.getMyName();
        return service.loadByName(username);
    }
    @GetMapping("changePassword")
    public void changePassword(String oldPwd, String newPwd) {
        User me = showMe();
        if(me==null) throw new BadCredentialsException("No Authentication for the current user!");
        if(!securityHelper.passwordIsMatch(oldPwd, me.getPassword()))
            throw new BadCredentialsException("Wrong Password!");
        me.setPassword(newPwd);
        encodePassword(me);
        service.modify(me);
    }
    @Autowired @Qualifier("userQry")
    private QueryService queryService;
    @PostMapping("query")
    @PreAuthorize("hasAuthority('administrator')")
    public ResultSet query(@RequestBody Map<String, Object> params) {
        return queryService.query(params);
    }
}
