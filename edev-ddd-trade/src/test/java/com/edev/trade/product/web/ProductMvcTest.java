package com.edev.trade.product.web;

import com.alibaba.fastjson.JSONObject;
import com.edev.trade.product.entity.Product;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        Long id = 1L;
        Product product = new Product(id,"BookNode",8000D,"unit",null,"electric");
        String json = JSONObject.toJSONString(product);

        mvc.perform(get("/orm/product/deleteProduct")
                .param("id", id.toString())
        ).andExpect(status().isOk());
        mvc.perform(post("/orm/product/saveProduct")
                .content(json).contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andExpect(content().string(id.toString()));
        mvc.perform(get("/orm/product/getProduct")
                .param("id",id.toString())
        ).andExpect(status().isOk()).andExpect(content().json(json));

        product.setImage("/img/NodeBook");
        product.setOriginalPrice(8000D);
        product.setPrice(7500D);
        product.setTip("自营");
        String json1 = JSONObject.toJSONString(product);
        mvc.perform(post("/orm/product/saveProduct")
                .content(json1).contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andExpect(content().string(id.toString()));
        mvc.perform(get("/orm/product/getProduct")
                .param("id",id.toString())
        ).andExpect(status().isOk()).andExpect(content().json(json1));

        mvc.perform(get("/orm/product/deleteProduct")
                .param("id",id.toString())
        ).andExpect(status().isOk());
        mvc.perform(get("/orm/product/getProduct")
                .param("id",id.toString())
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
        Long id0 = 1L;
        Product product0 = new Product(id0,"BookNode",8000D,"unit",null,"electric");
        Long id1 = 2L;
        Product product1 = new Product(id1,"SmartPhone",2000D,"unit",null,"electric");
        List<Product> list = new ArrayList<>();
        list.add(product0);
        list.add(product1);
        String json = JSONObject.toJSONString(list);

        mvc.perform(post("/list/product/deleteProducts")
                .content("[1,2]").contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
        mvc.perform(post("/list/product/saveProducts")
                .content(json).contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
        mvc.perform(post("/list/product/listProducts")
                .content("[1,2]").contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andExpect(content().json(json));

        product0.setImage("/img/NodeBook");
        product0.setOriginalPrice(8000D);
        product0.setPrice(7500D);
        product0.setTip("自营");
        list.remove(0);
        list.add(product0);
        String json1 = JSONObject.toJSONString(list);
        mvc.perform(post("/list/product/saveProducts")
                .content(json1).contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
        mvc.perform(post("/list/product/listProducts")
                .content("[1,2]").contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andExpect(content().json(json1));

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
        Long id0 = 1L;
        Product product0 = new Product(id0,"BookNode",8000D,"unit",null,"electric");
        Long id1 = 2L;
        Product product1 = new Product(id1,"SmartPhone",2000D,"unit",null,"electric");
        List<Product> list = new ArrayList<>();
        list.add(product0);
        list.add(product1);
        String jsonArray = JSONObject.toJSONString(list);
        Map<String, Object> map = new HashMap<>();
        map.put("products", list);
        String json = JSONObject.toJSONString(map);

        mvc.perform(get("/orm/product/deleteProducts")
                .param("ids","1,2")
        ).andExpect(status().isOk());
        mvc.perform(post("/orm/product/saveProducts")
                .content(json).contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
        mvc.perform(get("/orm/product/listProducts")
                .param("ids","1,2")
        ).andExpect(status().isOk()).andExpect(content().json(jsonArray));

        product0.setImage("/img/NodeBook");
        product0.setOriginalPrice(8000D);
        product0.setPrice(7500D);
        product0.setTip("自营");
        list.remove(0);
        list.add(product0);
        String jsonArray1 = JSONObject.toJSONString(list);
        map.put("products", list);
        String json1 = JSONObject.toJSONString(map);
        mvc.perform(post("/orm/product/saveProducts")
                .content(json1).contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
        mvc.perform(get("/orm/product/listProducts")
                .param("ids","1,2")
        ).andExpect(status().isOk()).andExpect(content().json(jsonArray1));

        mvc.perform(get("/orm/product/deleteProducts")
                .param("ids","1,2")
        ).andExpect(status().isOk());
        mvc.perform(get("/orm/product/listProducts")
                .param("ids","1,2")
        ).andExpect(status().isOk()).andExpect(content().json("[]"));
    }
}
