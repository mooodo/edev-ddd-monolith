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

import java.util.*;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ProductMvcTest {
    @Autowired
    private MockMvc mvc;

    /**
     * 业务需求：
     * 1）直接保存产品，系统自动去识别是添加还是更新
     * 2）如果没有该产品，执行插入操作
     * 3）如果已经有该产品，执行更新操作
     */
    @Test
    public void testSaveAndDelete() throws Exception {
        String id = "1";
        String json = JsonFile.read("json/product/product0.json");
        String excepted = JsonFile.read("json/product/excepted0.json");
        mvc.perform(get("/orm/product/deleteProduct")
                .param("id", id)
        ).andExpect(status().isOk());
        mvc.perform(post("/orm/product/saveProduct")
                .content(json).contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andExpect(content().string(id));
        mvc.perform(get("/orm/product/getProduct")
                .param("id",id)
        ).andExpect(status().isOk()).andExpect(content().json(excepted));

        String json1 = JsonFile.read("json/product/product1.json");
        String excepted1 = JsonFile.read("json/product/excepted1.json");
        mvc.perform(post("/orm/product/saveProduct")
                .content(json1).contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andExpect(content().string(id));
        mvc.perform(get("/orm/product/getProduct")
                .param("id",id)
        ).andExpect(status().isOk()).andExpect(content().json(excepted1));

        mvc.perform(get("/orm/product/deleteProduct")
                .param("id",id)
        ).andExpect(status().isOk());
        mvc.perform(get("/orm/product/getProduct")
                .param("id",id)
        ).andExpect(status().isOk()).andExpect(content().string(""));
    }

    /**
     * 业务需求：
     * 以列表的形式对产品进行批量地增删改查操作：
     * 1）添加、修改时这样提交：[{id:1,name:"BookNode"...},{id:2,name:"SmartPhone"...}]
     * 2）删除等操作时这样提交：[1,2]
     */
    @Test
    public void testSaveAndDeleteForList() throws Exception {
        String json = JsonFile.read("json/product/products0.json");
        String excepted = JsonFile.read("json/product/exceptedList.json");
        mvc.perform(post("/list/product/deleteProducts")
                .content("[1,2]").contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
        mvc.perform(post("/list/product/saveProducts")
                .content(json).contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
        mvc.perform(post("/list/product/listProducts")
                .content("[1,2]").contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andExpect(content().json(excepted));

        String json1 = JsonFile.read("json/product/products1.json");
        String excepted1 = JsonFile.read("json/product/exceptedList1.json");
        mvc.perform(post("/list/product/saveProducts")
                .content(json1).contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
        mvc.perform(post("/list/product/listProducts")
                .content("[1,2]").contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andExpect(content().json(excepted1));

        mvc.perform(post("/list/product/deleteProducts")
                .content("[1,2]").contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
        mvc.perform(post("/list/product/listProducts")
                .content("[1,2]").contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andExpect(content().json("[]"));
    }

    /**
     * 业务需求：
     * 以Json的形式对客户进行批量地增删改查操作：
     * 1）添加、修改时这样提交：{products: [{id:1,name:"BookNode"...},{id:2,name:"SmartPhone"...}]}
     * 2）删除等操作时这样提交：ids=1,2
     */
    @Test
    public void testSaveAndDeleteForJsonList() throws Exception {
        String json = JsonFile.read("json/product/products2.json");
        String excepted = JsonFile.read("json/product/exceptedList.json");
        mvc.perform(get("/orm/product/deleteProducts")
                .param("ids","1,2")
        ).andExpect(status().isOk());
        mvc.perform(post("/orm/product/saveProducts")
                .content(json).contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
        mvc.perform(get("/orm/product/listProducts")
                .param("ids","1,2")
        ).andExpect(status().isOk()).andExpect(content().json(excepted));

        String json1 = JsonFile.read("json/product/products3.json");
        String excepted1 = JsonFile.read("json/product/exceptedList1.json");
        mvc.perform(post("/orm/product/saveProducts")
                .content(json1).contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
        mvc.perform(get("/orm/product/listProducts")
                .param("ids","1,2")
        ).andExpect(status().isOk()).andExpect(content().json(excepted1));

        mvc.perform(get("/orm/product/deleteProducts")
                .param("ids","1,2")
        ).andExpect(status().isOk());
        mvc.perform(get("/orm/product/listProducts")
                .param("ids","1,2")
        ).andExpect(status().isOk()).andExpect(content().json("[]"));
    }
}
