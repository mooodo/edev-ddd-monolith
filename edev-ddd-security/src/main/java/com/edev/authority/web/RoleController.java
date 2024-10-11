package com.edev.authority.web;

import com.edev.authority.entity.Role;
import com.edev.authority.service.RoleService;
import com.edev.support.entity.ResultSet;
import com.edev.support.query.QueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("role")
public class RoleController {
    @Autowired
    private RoleService service;
    @PostMapping("create")
    @PreAuthorize("hasAuthority('administrator')")
    public Long create(@RequestBody Role role) {
        return service.create(role);
    }
    @PostMapping("modify")
    @PreAuthorize("hasAuthority('administrator')")
    public void modify(@RequestBody Role role) {
        service.modify(role);
    }
    @GetMapping("remove")
    @PreAuthorize("hasAuthority('administrator')")
    public void remove(Long roleId) {
        service.remove(roleId);
    }
    @GetMapping("load")
    @PreAuthorize("hasAuthority('administrator')")
    public Role load(Long roleId) {
        return service.load(roleId);
    }
    @Autowired @Qualifier("roleQry")
    private QueryService queryService;
    @PostMapping("query")
    @PreAuthorize("hasAuthority('administrator')")
    public ResultSet query(Map<String,Object> params) {
        return queryService.query(params);
    }
}
