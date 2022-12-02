package com.edev.trade.customer.web;

import com.alibaba.fastjson.JSONObject;
import com.edev.trade.customer.entity.Customer;
import com.edev.trade.customer.entity.GoldenVip;
import com.edev.trade.customer.entity.SilverVip;
import com.edev.trade.customer.entity.Vip;
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
        Long id = 1L;
        Vip vip = new SilverVip(id, true, 200L);
        String json = JSONObject.toJSONStringWithDateFormat(vip, "yyyy-MM-dd HH:mm:ss");

        mvc.perform(get("/orm/vip/delete")
                .param("vipId", id.toString())
        ).andExpect(status().isOk());
        mvc.perform(post("/orm/vip/register")
                .content(json).contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andExpect(content().string(id.toString()));
        mvc.perform(get("/orm/vip/load")
                .param("vipId", id.toString())
        ).andExpect(status().isOk()).andExpect(content().json(json));

        vip = new GoldenVip(id, true, 500L, 2000D);
        json = JSONObject.toJSONStringWithDateFormat(vip, "yyyy-MM-dd HH:mm:ss");
        mvc.perform(post("/orm/vip/modify")
                .content(json).contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
        mvc.perform(get("/orm/vip/load")
                .param("vipId", id.toString())
        ).andExpect(status().isOk()).andExpect(content().json(json));

        mvc.perform(post("/orm/vip/deleteVip")
                        .content(json).contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
        mvc.perform(get("/orm/vip/load")
                .param("vipId", id.toString())
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
        Long id = 10001L;
        Vip vip = new SilverVip(id, true, 200L);
        Customer customer = new Customer(10001L,"李秋水","女",
                "510110197910012312","13388990123");
        vip.setCustomer(customer);
        String json = JSONObject.toJSONStringWithDateFormat(vip, "yyyy-MM-dd HH:mm:ss");

        mvc.perform(get("/orm/vip/delete")
                .param("vipId", id.toString())
        ).andExpect(status().isOk());
        mvc.perform(post("/orm/vip/register")
                .content(json).contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andExpect(content().string(id.toString()));
        mvc.perform(get("/orm/vip/load")
                .param("vipId", id.toString())
        ).andExpect(status().isOk()).andExpect(content().json(json));

        vip = new GoldenVip(id, true, 500L, 2000D);
        json = JSONObject.toJSONStringWithDateFormat(vip, "yyyy-MM-dd HH:mm:ss");
        mvc.perform(post("/orm/vip/modify")
                .content(json).contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
        mvc.perform(get("/orm/vip/load")
                .param("vipId", id.toString())
        ).andExpect(status().isOk()).andExpect(content().json(json));

        mvc.perform(post("/orm/vip/deleteVip")
                .content(json).contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
        mvc.perform(get("/orm/vip/load")
                .param("vipId", id.toString())
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
        Long id0 = 1L;
        Vip vip0 = new SilverVip(id0, true, 200L);
        Long id1 = 10001L;
        Vip vip1 = new SilverVip(id1, true, 200L);
        Customer customer = new Customer(10001L,"李秋水","女",
                "510110197910012312","13388990123");
        vip1.setCustomer(customer);
        List<Vip> vipList = new ArrayList<>();
        vipList.add(vip0);
        vipList.add(vip1);
        String jsonArray = JSONObject.toJSONStringWithDateFormat(vipList, "yyyy-MM-dd HH:mm:ss");

        mvc.perform(post("/list/vip/deleteAll")
                .content("[1,10001]").contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
        mvc.perform(post("/list/vip/saveAll")
                .content(jsonArray).contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
        mvc.perform(post("/list/vip/loadAll")
                .content("[1,10001]").contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andExpect(content().json(jsonArray));

        mvc.perform(post("/query/vipQry")
                .content("{\"page\":0,\"size\":10,\"aggregation\":{\"id\":\"count\"}}")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());

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
        Long id0 = 1L;
        Vip vip0 = new SilverVip(id0, true, 200L);
        Long id1 = 10001L;
        Vip vip1 = new SilverVip(id1, true, 200L);
        Customer customer = new Customer(10001L,"李秋水","女",
                "510110197910012312","13388990123");
        vip1.setCustomer(customer);
        List<Vip> vipList = new ArrayList<>();
        vipList.add(vip0);
        vipList.add(vip1);
        String jsonArray = JSONObject.toJSONStringWithDateFormat(vipList, "yyyy-MM-dd HH:mm:ss");
        Map<String, Object> map = new HashMap<>();
        map.put("list", vipList);
        String json = JSONObject.toJSONStringWithDateFormat(map, "yyyy-MM-dd HH:mm:ss");

        mvc.perform(get("/orm/vip/deleteAll")
                .param("vipIds", "1,10001")
        ).andExpect(status().isOk());
        mvc.perform(post("/orm/vip/saveAll")
                .content(json).contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
        mvc.perform(get("/orm/vip/loadAll")
                .param("vipIds", "1,10001")
        ).andExpect(status().isOk()).andExpect(content().json(jsonArray));

        mvc.perform(get("/query/vipQry")
                .param("page","0").param("size","10")
        ).andExpect(status().isOk());

        mvc.perform(get("/orm/vip/deleteAll")
                .param("vipIds", "1,10001")
        ).andExpect(status().isOk());
        mvc.perform(get("/orm/vip/loadAll")
                .param("vipIds", "1,10001")
        ).andExpect(status().isOk()).andExpect(content().json("[]"));
    }
}
