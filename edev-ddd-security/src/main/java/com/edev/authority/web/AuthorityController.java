package com.edev.authority.web;

import com.edev.authority.entity.Authority;
import com.edev.authority.service.AuthorityService;
import com.edev.support.entity.ResultSet;
import com.edev.support.query.QueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("authority")
public class AuthorityController {
    @Autowired
    private AuthorityService service;
    @PostMapping("create")
    @PreAuthorize("hasAuthority('administrator')")
    public Long create(@RequestBody Authority authority) {
        return service.create(authority);
    }
    @PostMapping("modify")
    @PreAuthorize("hasAuthority('administrator')")
    public void modify(@RequestBody Authority authority) {
        service.modify(authority);
    }
    @GetMapping("remove")
    @PreAuthorize("hasAuthority('administrator')")
    public void remove(Long authorityId) {
        service.remove(authorityId);
    }
    @GetMapping("load")
    @PreAuthorize("hasAuthority('administrator')")
    public Authority load(Long authorityId) {
        return service.load(authorityId);
    }
    @Autowired @Qualifier("authorityQry")
    private QueryService queryService;
    @PostMapping("query")
    @PreAuthorize("hasAuthority('administrator')")
    public ResultSet query(Map<String, Object> params) {
        return queryService.query(params);
    }
}
