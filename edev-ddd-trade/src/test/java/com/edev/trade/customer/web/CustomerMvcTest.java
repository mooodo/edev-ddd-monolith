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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CustomerMvcTest {
    @Autowired
    private MockMvc mvc;
    /**
     * 业务需求：
     * 1）对客户可以进行增删改查的操作
     * 2）添加客户时，出生日期是根据身份证按照规则获取
     */
    @Test
    public void testSaveAndDelete() throws Exception {
        String id = "1";
        String json = JsonFile.read("json/customer/customer0.json");
        String excepted = JsonFile.read("json/customer/excepted0.json");
        mvc.perform(get("/orm/customer/delete")
                .param("customerId", id)
        ).andExpect(status().isOk());
        mvc.perform(post("/orm/customer/register")
                .content(json).contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andExpect(content().string(id));
        mvc.perform(get("/orm/customer/load")
                .param("customerId", id)
        ).andExpect(status().isOk()).andExpect(content().json(excepted));

        String json1 = JsonFile.read("json/customer/customer1.json");
        String excepted1 = JsonFile.read("json/customer/excepted1.json");
        mvc.perform(post("/orm/customer/modify")
                .content(json1).contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
        mvc.perform(get("/orm/customer/load")
                .param("customerId", id)
        ).andExpect(status().isOk()).andExpect(content().json(excepted1));

        mvc.perform(get("/orm/customer/delete")
                .param("customerId", id)
        ).andExpect(status().isOk());
        mvc.perform(get("/orm/customer/load")
                .param("customerId", id)
        ).andExpect(status().isOk()).andExpect(content().string(""));
    }

    /**
     * 业务需求：
     * 1）客户地址是作为聚合由客户来进行管理
     * 2）添加客户的同时，应当添加客户地址，并在同一事务中
     * 3）更改客户时，应当对比当前客户地址，确定对客户地址的增删改
     * 4）删除客户的同时，应当删除该客户所有的地址，并在同一事务中
     * 5）查询客户时，应当显示该客户相关的所有地址
     */
    @Test
    public void testSaveAndDeleteWithAddress() throws Exception {
        String id = "2";
        String json = JsonFile.read("json/customer/customer2.json");
        String excepted = JsonFile.read("json/customer/excepted2.json");

        mvc.perform(get("/orm/customer/delete")
                .param("customerId", id)
        ).andExpect(status().isOk());
        mvc.perform(post("/orm/customer/register")
                .content(json).contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andExpect(content().string(id));
        mvc.perform(get("/orm/customer/load")
                .param("customerId", id)
        ).andExpect(status().isOk()).andExpect(content().json(excepted));

        String json1 = JsonFile.read("json/customer/customer3.json");
        String excepted1 = JsonFile.read("json/customer/excepted3.json");
        mvc.perform(post("/orm/customer/modify")
                .content(json1).contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
        mvc.perform(get("/orm/customer/load")
                .param("customerId", id)
        ).andExpect(status().isOk()).andExpect(content().json(excepted1));

        mvc.perform(get("/orm/customer/delete")
                .param("customerId", id)
        ).andExpect(status().isOk());
        mvc.perform(get("/orm/customer/load")
                .param("customerId", id)
        ).andExpect(status().isOk()).andExpect(content().string(""));
    }

    /**
     * 业务需求：
     * 以列表的形式对客户进行批量地增删改查操作：
     * 1）添加、修改时这样提交：[{id:1,name:"Johnwood"...},{id:2,name:"Mary"...}]
     * 2）删除等操作时这样提交：[1,2]
     */
    @Test
    public void testSaveAndDeleteForList() throws Exception {
        String json = JsonFile.read("json/customer/customers0.json");
        String excepted = JsonFile.read("json/customer/exceptedList.json");
        mvc.perform(post("/list/customer/deleteAll")
                .content("[1,2]").contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
        mvc.perform(post("/list/customer/saveAll")
                .content(json).contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
        mvc.perform(post("/list/customer/loadAll")
                .content("[1,2]").contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andExpect(content().json(excepted));

        String query = JsonFile.read("json/customer/query.json");
        String resultSet = JsonFile.read("json/customer/resultSet.json");
        mvc.perform(post("/query/customerQry")
                .content(query).contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andExpect(content().json(resultSet));

        mvc.perform(post("/list/customer/deleteAll")
                .content("[1,2]").contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
        mvc.perform(post("/list/customer/loadAll")
                .content("[1,2]").contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andExpect(content().json("[]"));
    }

    /**
     * 业务需求：
     * 以Json的形式对客户进行批量地增删改查操作：
     * 1）添加、修改时这样提交：{customers: [{id:1,name:"Johnwood"...},{id:2,name:"Mary"...}]}
     * 2）删除等操作时这样提交：customerIds=1,2
     */
    @Test
    public void testSaveAndDeleteForJsonList() throws Exception {
        String json = JsonFile.read("json/customer/customers1.json");
        String excepted = JsonFile.read("json/customer/exceptedList.json");
        mvc.perform(get("/orm/customer/deleteAll")
                .param("customerIds", "1,2")
        ).andExpect(status().isOk());
        mvc.perform(post("/orm/customer/saveAll")
                .content(json).contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
        mvc.perform(get("/orm/customer/loadAll")
                .param("customerIds", "1,2")
        ).andExpect(status().isOk()).andExpect(content().json(excepted));

        String resultSet = JsonFile.read("json/customer/resultSet.json");
        mvc.perform(get("/query/customerQry")
                .param("id","1,2")
                .param("page","0").param("size","10")
        ).andExpect(status().isOk()).andExpect(content().json(resultSet));

        mvc.perform(get("/orm/customer/deleteAll")
                .param("customerIds", "1,2")
        ).andExpect(status().isOk());
        mvc.perform(get("/orm/customer/loadAll")
                .param("customerIds", "1,2")
        ).andExpect(status().isOk()).andExpect(content().json("[]"));
    }
}
