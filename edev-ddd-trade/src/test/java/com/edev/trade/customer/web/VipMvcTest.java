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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class VipMvcTest {
    @Autowired
    private MockMvc mvc;

    /**
     * 业务需求：
     * 1）会员按照继承分为金卡会员和银卡会员，按照会员类型来划分
     * 2）可以通过前端读取的数据，创建不同类型的会员对象
     * 3）通过修改会员类型，可以切换不同类型的会员对象
     * 4）将父类与子类的数据全部都写入数据库的一张表中
     * 5）插入会员时，自动生成创建时间
     */
    @Test
    public void testSaveAndDelete() throws Exception {
        String id = "1";
        String silver = JsonFile.read("json/vip/vip0.json");
        String excepted = JsonFile.read("json/vip/excepted0.json");
        mvc.perform(get("/orm/vip/delete")
                .param("vipId", id)
        ).andExpect(status().isOk());
        mvc.perform(post("/orm/vip/register")
                .content(silver).contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andExpect(content().string(id));
        mvc.perform(get("/orm/vip/load")
                .param("vipId", id)
        ).andExpect(status().isOk()).andExpect(content().json(excepted));

        String golden = JsonFile.read("json/vip/vip1.json");
        String excepted1 = JsonFile.read("json/vip/excepted1.json");
        mvc.perform(post("/orm/vip/modify")
                .content(golden).contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
        mvc.perform(get("/orm/vip/load")
                .param("vipId", id)
        ).andExpect(status().isOk()).andExpect(content().json(excepted1));

        mvc.perform(post("/orm/vip/deleteVip")
                        .content(golden).contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
        mvc.perform(get("/orm/vip/load")
                .param("vipId", id)
        ).andExpect(status().isOk()).andExpect(content().string(""));
    }

    /**
     * 业务需求：
     * 1）会员通过一对一关系会关联一个客户对象
     * 2）当查询会员时，会关联该客户
     * 3）当增删改会员时，由于不是聚合关系，不会更改客户
     */
    @Test
    public void testSaveAndDeleteWithCustomer() throws Exception {
        String id = "10001";
        String silver = JsonFile.read("json/vip/vip2.json");
        String excepted = JsonFile.read("json/vip/excepted2.json");
        mvc.perform(get("/orm/vip/delete")
                .param("vipId", id)
        ).andExpect(status().isOk());
        mvc.perform(post("/orm/vip/register")
                .content(silver).contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andExpect(content().string(id));
        mvc.perform(get("/orm/vip/load")
                .param("vipId", id)
        ).andExpect(status().isOk()).andExpect(content().json(excepted));

        String golden = JsonFile.read("json/vip/vip3.json");
        String excepted1 = JsonFile.read("json/vip/excepted3.json");
        mvc.perform(post("/orm/vip/modify")
                .content(golden).contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
        mvc.perform(get("/orm/vip/load")
                .param("vipId", id)
        ).andExpect(status().isOk()).andExpect(content().json(excepted1));

        mvc.perform(post("/orm/vip/deleteVip")
                .content(golden).contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
        mvc.perform(get("/orm/vip/load")
                .param("vipId", id)
        ).andExpect(status().isOk()).andExpect(content().string(""));
    }

    /**
     * 业务需求：
     * 以列表的形式对会员进行批量地增删改查操作：
     * 1）添加、修改时这样提交：[{id:1,available:true...},{id:10001,available:true...}]
     * 2）删除等操作时这样提交：[1,10001]
     */
    @Test
    public void testSaveAndDeleteForList() throws Exception {
        String json = JsonFile.read("json/vip/vips0.json");
        String excepted = JsonFile.read("json/vip/exceptedList.json");
        mvc.perform(post("/list/vip/deleteAll")
                .content("[1,10001]").contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
        mvc.perform(post("/list/vip/saveAll")
                .content(json).contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
        mvc.perform(post("/list/vip/loadAll")
                .content("[1,10001]").contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andExpect(content().json(excepted));

        String query = JsonFile.read("json/vip/query.json");
        String resultSet = JsonFile.read("json/vip/resultSet.json");
        mvc.perform(post("/query/vipQry")
                .content(query).contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andExpect(content().json(resultSet));

        mvc.perform(post("/list/vip/deleteAll")
                .content("[1,10001]").contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
        mvc.perform(post("/list/vip/loadAll")
                .content("[1,10001]").contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andExpect(content().json("[]"));
    }

    /**
     * 业务需求：
     * 以Json的形式对客户进行批量地增删改查操作：
     * 1）添加、修改时这样提交：{customers: [{id:1,available:true...},{id:10001,available:true...}]}
     * 2）删除等操作时这样提交：customerIds=1,10001
     */
    @Test
    public void testSaveAndDeleteForJsonList() throws Exception {
        String json = JsonFile.read("json/vip/vips1.json");
        String excepted = JsonFile.read("json/vip/exceptedList.json");

        mvc.perform(get("/orm/vip/deleteAll")
                .param("vipIds", "1,10001")
        ).andExpect(status().isOk());
        mvc.perform(post("/orm/vip/saveAll")
                .content(json).contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
        mvc.perform(get("/orm/vip/loadAll")
                .param("vipIds", "1,10001")
        ).andExpect(status().isOk()).andExpect(content().json(excepted));

        String resultSet = JsonFile.read("json/vip/resultSet.json");
        mvc.perform(get("/query/vipQry").param("id","1,10001")
                .param("page","0").param("size","10")
        ).andExpect(status().isOk()).andExpect(content().json(resultSet));

        mvc.perform(get("/orm/vip/deleteAll")
                .param("vipIds", "1,10001")
        ).andExpect(status().isOk());
        mvc.perform(get("/orm/vip/loadAll")
                .param("vipIds", "1,10001")
        ).andExpect(status().isOk()).andExpect(content().json("[]"));
    }
}
