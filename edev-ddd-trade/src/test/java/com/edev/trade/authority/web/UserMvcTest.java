package com.edev.trade.authority.web;

import com.edev.support.utils.JsonFile;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UserMvcTest {
    @Autowired
    private MockMvc mvc;
    @Test
    public void testSaveAndDelete() throws Exception {
        String id = "1";
        String json0 = JsonFile.read("json/user/user0.json");
        String excepted0 = JsonFile.read("json/user/excepted0.json");
        mvc.perform(get("/orm/user/deleteById")
                .param("userId", id)
        ).andExpect(status().isOk());
        mvc.perform(post("/orm/user/register")
                .content(json0).contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andExpect(content().string(id));
        mvc.perform(get("/orm/user/load")
                .param("userId", id)
        ).andExpect(status().isOk()).andExpect(content().json(excepted0));
        mvc.perform(get("/orm/user/loadByName")
                .param("userName", "Mary")
        ).andExpect(status().isOk()).andExpect(content().json(excepted0));

        String json1 = JsonFile.read("json/user/user1.json");
        String excepted1 = JsonFile.read("json/user/excepted1.json");
        mvc.perform(post("/orm/user/modify")
                .content(json1).contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
        mvc.perform(get("/orm/user/load")
                .param("userId", id)
        ).andExpect(status().isOk()).andExpect(content().json(excepted1));
        mvc.perform(get("/orm/user/loadByName")
                .param("userName", "Mary Ann")
        ).andExpect(status().isOk()).andExpect(content().json(excepted1));

        String template = JsonFile.read("json/user/template.json");
        mvc.perform(post("/orm/user/delete")
                .content(template).contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());mvc.perform(get("/orm/user/load")
                .param("userId", id)
        ).andExpect(status().isOk()).andExpect(content().string(""));
    }
    @Test
    public void testSaveAndDeleteWithRole() throws Exception {
        String id = "1";
        String json0 = JsonFile.read("json/user/user2.json");
        String excepted0 = JsonFile.read("json/user/excepted2.json");
        mvc.perform(get("/orm/user/deleteById")
                .param("userId", id)
        ).andExpect(status().isOk());
        mvc.perform(post("/orm/user/register")
                .content(json0).contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andExpect(content().string(id));
        mvc.perform(get("/orm/user/load")
                .param("userId", id)
        ).andExpect(status().isOk()).andExpect(content().json(excepted0));
        mvc.perform(get("/orm/user/loadByName")
                .param("userName", "Mary")
        ).andExpect(status().isOk()).andExpect(content().json(excepted0));

        String json1 = JsonFile.read("json/user/user1.json");
        String excepted1 = JsonFile.read("json/user/excepted1.json");
        mvc.perform(post("/orm/user/modify")
                .content(json1).contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
        mvc.perform(get("/orm/user/load")
                .param("userId", id)
        ).andExpect(status().isOk()).andExpect(content().json(excepted1));
        mvc.perform(get("/orm/user/loadByName")
                .param("userName", "Mary Ann")
        ).andExpect(status().isOk()).andExpect(content().json(excepted1));

        String template = JsonFile.read("json/user/template.json");
        mvc.perform(post("/orm/user/delete")
                .content(template).contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());mvc.perform(get("/orm/user/load")
                .param("userId", id)
        ).andExpect(status().isOk()).andExpect(content().string(""));
    }
    @Test
    public void testSaveAndDeleteForList() throws Exception {
        String json0 = JsonFile.read("json/user/users0.json");
        String excepted0 = JsonFile.read("json/user/exceptedList.json");
        mvc.perform(post("/list/user/deleteAll")
                .content("[1,2]").contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
        mvc.perform(post("/list/user/saveAll")
                .content(json0).contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
        mvc.perform(post("/list/user/loadAll")
                .content("[1,2]").contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andExpect(content().json(excepted0));
        mvc.perform(post("/list/user/deleteAll")
                .content("[1,2]").contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
        mvc.perform(post("/list/user/loadAll")
                .content("[1,2]").contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andExpect(content().json("[]"));
    }
    @Test
    public void testSaveAndDeleteForJsonList() throws Exception {
        String json0 = JsonFile.read("json/user/users1.json");
        String excepted0 = JsonFile.read("json/user/exceptedList.json");
        mvc.perform(get("/orm/user/deleteAll")
                .param("userIds", "1,2")
        ).andExpect(status().isOk());
        mvc.perform(post("/orm/user/saveAll")
                .content(json0).contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
        mvc.perform(get("/orm/user/loadAll")
                .param("userIds", "1,2")
        ).andExpect(status().isOk()).andExpect(content().json(excepted0));
        mvc.perform(get("/orm/user/deleteAll")
                .param("userIds", "1,2")
        ).andExpect(status().isOk());
        mvc.perform(get("/orm/user/loadAll")
                .param("userIds", "1,2")
        ).andExpect(status().isOk()).andExpect(content().json("[]"));
    }
}
