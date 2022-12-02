package com.edev.trade.customer.web;

import com.alibaba.fastjson.JSONObject;
import com.edev.support.utils.DateUtils;
import com.edev.trade.customer.entity.Account;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class AccountMvcTest {
    @Autowired
    private MockMvc mvc;
    @Test
    public void testSaveAndDelete() throws Exception {
        Long id = 1L;
        Account account = new Account(id,10001L,10000D,
                DateUtils.getDate("2020-01-01","yyyy-MM-dd"),null);
        String json = JSONObject.toJSONStringWithDateFormat(account, "yyyy-MM-dd HH:mm:ss");

        mvc.perform(get("/orm/account/remove")
                .param("id",id.toString())
        ).andExpect(status().isOk());
        mvc.perform(post("/orm/account/create")
                .content(json).contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andExpect(content().string(id.toString()));
        mvc.perform(get("/orm/account/get")
                .param("id", id.toString())
        ).andExpect(status().isOk()).andExpect(content().json(json));

        mvc.perform(get("/payment/topUp")
                .param("id", id.toString()).param("amount", "1000")
        ).andExpect(status().isOk()).andExpect(content().string("11000.0"));
        account.setBalance(11000D);
        String json1 = JSONObject.toJSONStringWithDateFormat(account, "yyyy-MM-dd HH:mm:ss");
        mvc.perform(get("/orm/account/get")
                .param("id", id.toString())
        ).andExpect(status().isOk()).andExpect(content().json(json1));

        mvc.perform(get("/payment/payoff")
                .param("id", id.toString()).param("amount", "1000")
        ).andExpect(status().isOk()).andExpect(content().string("10000.0"));
        mvc.perform(get("/orm/account/get")
                .param("id", id.toString())
        ).andExpect(status().isOk()).andExpect(content().json(json));

        mvc.perform(get("/orm/account/remove")
                .param("id",id.toString())
        ).andExpect(status().isOk());
        mvc.perform(get("/orm/account/get")
                .param("id", id.toString())
        ).andExpect(status().isOk()).andExpect(content().string(""));
    }
}
