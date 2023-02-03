package com.edev.trade.customer.web;

import com.edev.support.utils.JsonFile;
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
        String id = "1";
        String json = JsonFile.read("json/account/account0.json");
        String excepted0 = JsonFile.read("json/account/excepted0.json");
        mvc.perform(get("/orm/account/remove")
                .param("id",id)
        ).andExpect(status().isOk());
        mvc.perform(post("/orm/account/create")
                .content(json).contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andExpect(content().string(id));
        mvc.perform(get("/orm/account/get")
                .param("id", id)
        ).andExpect(status().isOk()).andExpect(content().json(excepted0));

        mvc.perform(get("/account/topUp")
                .param("id", id).param("amount", "1000")
        ).andExpect(status().isOk()).andExpect(content().string("11000.0"));
        String excepted1 = JsonFile.read("json/account/excepted1.json");
        mvc.perform(get("/orm/account/get")
                .param("id", id)
        ).andExpect(status().isOk()).andExpect(content().json(excepted1));

        mvc.perform(get("/account/payoff")
                .param("id", id).param("amount", "1000")
        ).andExpect(status().isOk()).andExpect(content().string("10000.0"));
        mvc.perform(get("/orm/account/get")
                .param("id", id)
        ).andExpect(status().isOk()).andExpect(content().json(excepted0));

        mvc.perform(get("/orm/account/remove")
                .param("id",id)
        ).andExpect(status().isOk());
        mvc.perform(get("/orm/account/get")
                .param("id", id)
        ).andExpect(status().isOk()).andExpect(content().string(""));
    }
}
