package com.edev.trade.customer.web;

import com.edev.support.web.OrmController;
import com.edev.trade.customer.service.AccountAggService;
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

    @Autowired
    private AccountAggService service;
    @GetMapping("topUp")
    public Double topUp(Long id, Double amount) {
        return service.topUp(id, amount);
    }
    @GetMapping("payoff")
    public Double payoff(Long id, Double amount) {
        return service.payoff(id, amount);
    }
    @GetMapping("refund")
    public Double refund(Long id, Double amount) {
        return service.refund(id, amount);
    }
}
