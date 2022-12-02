package com.edev.trade.authority.web;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import com.alibaba.fastjson.JSONObject;
import com.edev.trade.authority.entity.Customer;
import com.edev.trade.authority.entity.Staff;
import com.edev.trade.authority.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UserMvcTest {
    @Autowired
    private MockMvc mvc;
    @Test
    public void testSaveAndDelete() throws Exception {
        Long id = 1L;
        Customer customer = new Customer(id, "Johnwood",
                "123","customer","male",
                "510212199901012211", "13677778888");
        String json = JSONObject.toJSONStringWithDateFormat(customer, "yyyy-MM-dd HH:mm:ss");

        mvc.perform(get("/orm/user/deleteById")
                .param("userId", id.toString())
        ).andExpect(status().isOk());
        mvc.perform(post("/orm/user/register")
                .content(json).contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andExpect(content().string(id.toString()));
        mvc.perform(get("/orm/user/load")
                .param("userId", id.toString())
        ).andExpect(status().isOk()).andExpect(content().json(json));

        customer.setName("Jone");
        customer.setGender("female");
        customer.setPassword("321");
        String json1 = JSONObject.toJSONStringWithDateFormat(customer, "yyyy-MM-dd HH:mm:ss");

        mvc.perform(post("/orm/user/modify")
                .content(json1).contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
        mvc.perform(get("/orm/user/load")
                .param("userId", id.toString())
        ).andExpect(status().isOk()).andExpect(content().json(json1));

        Staff staff = new Staff(id, "Johnwood",
                "123","staff","male","hr");
        String json2 = JSONObject.toJSONStringWithDateFormat(staff, "yyyy-MM-dd HH:mm:ss");
        mvc.perform(post("/orm/user/modify")
                .content(json2).contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
        mvc.perform(get("/orm/user/load")
                .param("userId", id.toString())
        ).andExpect(status().isOk()).andExpect(content().json(json2));

        mvc.perform(post("/orm/user/delete")
                .content(json2).contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
        mvc.perform(get("/orm/user/load")
                .param("userId", id.toString())
        ).andExpect(status().isOk()).andExpect(content().string(""));
    }
    @Test
    public void testSaveAndDeleteForList() throws Exception {
        Long id0 = 1L;
        Customer customer = new Customer(id0, "Johnwood",
                "123","customer","male",
                "510212199901012211", "13677778888");
        Long id1 = 2L;
        Staff staff = new Staff(id1, "Jone",
                "123","staff","female","hr");
        List<User> users = new ArrayList<>();
        users.add(customer);
        users.add(staff);
        String json = JSONObject.toJSONStringWithDateFormat(users, "yyyy-MM-dd HH:mm:ss");

        mvc.perform(post("/list/user/deleteAll")
                .content("[1,2]").contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
        mvc.perform(post("/list/user/saveAll")
                .content(json).contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
        mvc.perform(post("/list/user/loadAll")
                .content("[1,2]").contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andExpect(content().json(json));

        mvc.perform(post("/list/user/deleteAll")
                .content("[1,2]").contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
        mvc.perform(post("/list/user/loadAll")
                .content("[1,2]").contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andExpect(content().json("[]"));
    }
}
