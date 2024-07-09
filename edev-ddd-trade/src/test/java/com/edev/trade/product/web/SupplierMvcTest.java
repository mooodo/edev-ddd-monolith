package com.edev.trade.product.web;


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
public class SupplierMvcTest {
    @Autowired
    private MockMvc mvc;
    @Test
    public void testSaveAndDelete() throws Exception {
        String id = "1";
        String json = JsonFile.read("json/supplier/supplier0.json");
        String excepted = JsonFile.read("json/supplier/excepted0.json");
        mvc.perform(get("/orm/supplier/removeById")
                .param("id",id)
        ).andExpect(status().isOk());
        mvc.perform(post("/orm/supplier/register")
                .content(json).contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andExpect(content().string(id));
        mvc.perform(get("/orm/supplier/loadById")
                .param("id",id)
        ).andExpect(status().isOk()).andExpect(content().json(excepted));

        String json1 = JsonFile.read("json/supplier/supplier1.json");
        String excepted1 = JsonFile.read("json/supplier/excepted1.json");
        mvc.perform(post("/orm/supplier/modify")
                .content(json1).contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
        mvc.perform(get("/orm/supplier/loadById")
                .param("id",id)
        ).andExpect(status().isOk()).andExpect(content().json(excepted1));

        String json2 = JsonFile.read("json/supplier/supplier2.json");
        String excepted2 = JsonFile.read("json/supplier/excepted2.json");
        mvc.perform(post("/orm/supplier/modify")
                .content(json2).contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
        mvc.perform(get("/orm/supplier/loadById")
                .param("id",id)
        ).andExpect(status().isOk()).andExpect(content().json(excepted2));

        mvc.perform(post("/orm/supplier/remove")
                .content(json2).contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
        mvc.perform(get("/orm/supplier/loadById")
                .param("id",id)
        ).andExpect(status().isOk()).andExpect(content().string(""));
    }
}
