package com.edev.trade.order.web;

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
public class DiscountMvcTest {
    @Autowired
    private MockMvc mvc;
    @Test
    public void testSaveAndDelete() throws Exception {
        String id = "1";
        String json = JsonFile.read("json/discount/discount0.json");
        String excepted = JsonFile.read("json/discount/excepted0.json");
        mvc.perform(get("/orm/discount/deleteById")
                .param("discountId", id)
        ).andExpect(status().isOk());
        mvc.perform(post("/orm/discount/create")
                .content(json).contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andExpect(content().string(id));
        mvc.perform(get("/orm/discount/loadById")
                .param("discountId", id)
        ).andExpect(status().isOk()).andExpect(content().json(excepted));


        String json1 = JsonFile.read("json/discount/discount0.json");
        String excepted1 = JsonFile.read("json/discount/excepted0.json");
        mvc.perform(post("/orm/discount/modify")
                .content(json1).contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
        mvc.perform(get("/orm/discount/loadById")
                .param("discountId", id)
        ).andExpect(status().isOk()).andExpect(content().json(excepted1));

        mvc.perform(post("/orm/discount/delete")
                .content(json1).contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
        mvc.perform(get("/orm/discount/loadById")
                .param("discountId", id)
        ).andExpect(status().isOk()).andExpect(content().string(""));
    }
    @Test
    public void testSaveAndDeleteWithChild() throws Exception {
        String id = "1";
        String json = JsonFile.read("json/discount/discount0.json");
        String excepted = JsonFile.read("json/discount/excepted0.json");
        mvc.perform(get("/orm/discount/delete")
                .param("discountId", id).param("discountType", "productDiscount")
        ).andExpect(status().isOk());
        mvc.perform(post("/orm/discount/create")
                .content(json).contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andExpect(content().string(id));
        mvc.perform(get("/orm/discount/loadById")
                .param("discountId", id)
        ).andExpect(status().isOk()).andExpect(content().json(excepted));

        mvc.perform(post("/orm/discount/delete")
                .content(json).contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
        mvc.perform(get("/orm/discount/load")
                .param("discountId", id).param("discountType", "productDiscount")
        ).andExpect(status().isOk()).andExpect(content().string(""));
    }
}
