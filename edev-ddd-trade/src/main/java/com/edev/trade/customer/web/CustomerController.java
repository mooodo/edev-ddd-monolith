package com.edev.trade.customer.web;

import com.edev.support.web.OrmController;
import com.edev.support.web.QueryController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("customer")
public class CustomerController {
    @Autowired
    private OrmController ormController;
    private static final String BEAN = "customer";
    @PostMapping("register")
    public Object register(@RequestBody Map<String, Object> json) {
        return ormController.doPost(BEAN,"register",json);
    }
    @PostMapping("modify")
    public void modify(@RequestBody Map<String, Object> json) {
        ormController.doPost(BEAN,"modify",json);
    }
    @GetMapping("delete")
    public void delete(HttpServletRequest request) {
        ormController.doGet(BEAN,"delete", request);
    }
    @GetMapping("load")
    public Object load(HttpServletRequest request) {
        return ormController.doGet(BEAN,"load",request);
    }

    @Autowired
    private QueryController queryController;
    private static final String QRY_BEAN = "customerQry";
    @PostMapping("query")
    public Object query(@RequestBody Map<String, Object> json) {
        return queryController.queryByPost(QRY_BEAN, json);
    }
    @GetMapping("query")
    public Object query(HttpServletRequest request) {
        return queryController.queryByGet(QRY_BEAN, request);
    }
}
