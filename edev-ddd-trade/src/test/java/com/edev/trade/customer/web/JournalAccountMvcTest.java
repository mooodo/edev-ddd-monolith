package com.edev.trade.customer.web;

import com.alibaba.fastjson.JSONObject;
import com.edev.trade.customer.entity.JournalAccount;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class JournalAccountMvcTest {
    @Autowired
    private MockMvc mvc;

    /**
     * 业务需求：
     * 1）添加账户流水时，不输入主键，实现自增主键，返回自增的主键值
     * 2）根据主键值删除账户流水
     * 说明：在数据库中该表应该选择自增主键，并且主键名必须为id
     */
    @Test
    public void test() throws Exception {
        JournalAccount journalAccount =
                new JournalAccount(10001L,10D,"TopUp");
        String json = JSONObject.toJSONStringWithDateFormat(journalAccount, "yyyy-MM-dd HH:mm:ss");
        MvcResult result = mvc.perform(post("/orm/journalAccount/addJournalAccount")
                .content(json).contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andReturn();
        String id = result.getResponse().getContentAsString();
        mvc.perform(get("/orm/journalAccount/removeJournalAccount")
                .param("id", id)
        ).andExpect(status().isOk());
    }
}
