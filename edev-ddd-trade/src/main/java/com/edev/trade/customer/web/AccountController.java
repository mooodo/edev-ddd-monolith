package com.edev.trade.customer.web;

import com.edev.support.web.OrmController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("account")
public class AccountController {
    @Autowired
    private OrmController ormController;
    private static final String BEAN = "account";
    @PostMapping("create")
    public Object create(@RequestBody Map<String, Object> json) {
        return ormController.doPost(BEAN, "createAccount", json);
    }
    @PostMapping("modify")
    public void modify(@RequestBody Map<String, Object> json) {
        ormController.doPost(BEAN, "modifyAccount", json);
    }
    @GetMapping("remove")
    public void remove(HttpServletRequest request) {
        ormController.doGet(BEAN, "removeAccount", request);
    }
    @GetMapping("get")
    public Object get(HttpServletRequest request) {
        return ormController.doGet(BEAN, "getAccount", request);
    }
}
