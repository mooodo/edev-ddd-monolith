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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class OrderMvcTest {
    @Autowired
    private MockMvc mvc;
    /**
     * 业务需求：
     * 1）增删改订单信息
     * 2）添加订单时，下单时间默认为当前时间，状态为create
     * 3）查询订单时，能够关联订单的客户和客户地址信息
     */
    @Test
    public void testSaveAndDelete() throws Exception {
        String id = "1";
        String json = JsonFile.read("json/order/order0.json");
        String excepted = JsonFile.read("json/order/excepted0.json");
        mvc.perform(get("/orm/order/delete")
                .param("orderId",id)
        ).andExpect(status().isOk());
        mvc.perform(post("/orm/order/create")
                .content(json).contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andExpect(content().string(id));
        mvc.perform(get("/orm/order/load")
                .param("orderId", id)
        ).andExpect(status().isOk()).andExpect(content().json(excepted));

        mvc.perform(get("/orm/order/delete")
                .param("orderId",id)
        ).andExpect(status().isOk());
        mvc.perform(get("/orm/order/load")
                .param("orderId", id)
        ).andExpect(status().isOk()).andExpect(content().string(""));
    }
    /**
     * 业务需求：
     * 1）添加订单时，由于订单与订单明细是聚合关系，应当同时保存订单与订单明细，并在同一事务中
     * 2）更新订单时，要将订单明细与原数据进行比对，确定对订单明细的增删改
     * 3）删除订单时，要同时删除该订单的订单明细，并在同一事务中
     * 4）查询订单时，要关联该订单的客户、客户地址与订单明细
     * 5）如果客户是VIP会员，要根据会员类型进行VIP折扣
     * 6）订单金额应当是所有订单明细的金额之和
     */
    @Test
    public void testSaveAndDeleteWithItem() throws Exception {
        String id = "1";
        String json = JsonFile.read("json/order/order1.json");
        String excepted = JsonFile.read("json/order/excepted1.json");
        mvc.perform(get("/orm/order/delete")
                .param("orderId",id)
        ).andExpect(status().isOk());
        mvc.perform(post("/orm/order/create")
                .content(json).contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andExpect(content().string(id));
        mvc.perform(get("/orm/order/load")
                .param("orderId", id)
        ).andExpect(status().isOk()).andExpect(content().json(excepted));

        mvc.perform(get("/orm/order/delete")
                .param("orderId",id)
        ).andExpect(status().isOk());
        mvc.perform(get("/orm/order/load")
                .param("orderId", id)
        ).andExpect(status().isOk()).andExpect(content().string(""));
    }
    @Test
    public void testSaveAndDeleteWithItemAndPayment() throws Exception {
        String id = "1";
        String json = JsonFile.read("json/order/order2.json");
        String excepted = JsonFile.read("json/order/excepted2.json");
        mvc.perform(get("/orm/order/delete")
                .param("orderId",id)
        ).andExpect(status().isOk());
        mvc.perform(post("/orm/order/create")
                .content(json).contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andExpect(content().string(id));
        mvc.perform(get("/orm/order/load")
                .param("orderId", id)
        ).andExpect(status().isOk()).andExpect(content().json(excepted));

        mvc.perform(get("/orm/order/delete")
                .param("orderId",id)
        ).andExpect(status().isOk());
        mvc.perform(get("/orm/order/load")
                .param("orderId", id)
        ).andExpect(status().isOk()).andExpect(content().string(""));
    }
    @Test
    public void testSaveAndDeleteForList() throws Exception {
        String json = JsonFile.read("json/order/orders0.json");
        String excepted = JsonFile.read("json/order/exceptedList.json");
        mvc.perform(post("/list/order/deleteAll")
                .content("[1,2]").contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
        mvc.perform(post("/list/order/saveAll")
                .content(json).contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
        mvc.perform(post("/list/order/loadAll")
                .content("[1,2]").contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andExpect(content().json(excepted));

        String query = JsonFile.read("json/order/query0.json");
        String resultSet = JsonFile.read("json/order/resultSet0.json");
        mvc.perform(post("/query/orderQry")
                .content(query).contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andExpect(content().json(resultSet));

        mvc.perform(post("/list/order/deleteAll")
                .content("[1,2]").contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
        mvc.perform(post("/list/order/loadAll")
                .content("[1,2]").contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andExpect(content().json("[]"));
    }
    @Test
    public void testSaveAndDeleteForJsonList() throws Exception {
        String json = JsonFile.read("json/order/orders1.json");
        String excepted = JsonFile.read("json/order/exceptedList.json");
        mvc.perform(get("/orm/order/deleteAll")
                .param("orderIds", "1,2")
        ).andExpect(status().isOk());
        mvc.perform(post("/orm/order/saveAll")
                .content(json).contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
        mvc.perform(get("/orm/order/loadAll")
                .param("orderIds", "1,2")
        ).andExpect(status().isOk()).andExpect(content().json(excepted));

        String resultSet = JsonFile.read("json/order/resultSet1.json");
        mvc.perform(get("/query/orderQry").param("id","1,2")
                .param("page","0").param("size","5")
        ).andExpect(status().isOk()).andExpect(content().json(resultSet));

        mvc.perform(get("/orm/order/deleteAll")
                .param("orderIds", "1,2")
        ).andExpect(status().isOk());
        mvc.perform(get("/orm/order/loadAll")
                .param("orderIds", "1,2")
        ).andExpect(status().isOk()).andExpect(content().json("[]"));
    }
}
