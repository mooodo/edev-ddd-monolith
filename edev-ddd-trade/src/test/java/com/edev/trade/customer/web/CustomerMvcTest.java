package com.edev.trade.customer.web;

import com.alibaba.fastjson.JSONObject;
import com.edev.trade.customer.entity.Address;
import com.edev.trade.customer.entity.Customer;
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
        Long id = 1L;
        Customer customer = new Customer(id, "John", "male",
                "510212199901012211", "13677778888");
        String json = JSONObject.toJSONStringWithDateFormat(customer, "yyyy-MM-dd HH:mm:ss");

        mvc.perform(get("/orm/customer/delete")
                .param("customerId", id.toString())
        ).andExpect(status().isOk());
        mvc.perform(post("/orm/customer/register")
                .content(json).contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andExpect(content().string(id.toString()));
        mvc.perform(get("/orm/customer/load")
                .param("customerId", id.toString())
        ).andExpect(status().isOk()).andExpect(content().json(json));

        customer.setName("Jone");
        customer.setGender("female");
        customer.setIdentification("100101200003052314");
        json = JSONObject.toJSONStringWithDateFormat(customer, "yyyy-MM-dd HH:mm:ss");
        mvc.perform(post("/orm/customer/modify")
                .content(json).contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
        mvc.perform(get("/orm/customer/load")
                .param("customerId", id.toString())
        ).andExpect(status().isOk()).andExpect(content().json(json));

        mvc.perform(get("/orm/customer/delete")
                .param("customerId", id.toString())
        ).andExpect(status().isOk());
        mvc.perform(get("/orm/customer/load")
                .param("customerId", id.toString())
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
        Long id = 2L;
        Customer customer = new Customer(id, "Mary", "female",
                "510212200012137812", "13456567878");
        Address address0 = new Address(10L,null,"China","Shandong","Jinan",
                "Lixiaqu","Happy street No.12","0531-88896666");
        Address address1 = new Address(20L,null,"China","Zhejiang","Hangzhou",
                "Xihuqu","The park of Gushan","0571-64128989");
        List<Address> addressList = new ArrayList<>();
        addressList.add(address0);
        addressList.add(address1);
        customer.setAddresses(addressList);
        String json = JSONObject.toJSONStringWithDateFormat(customer, "yyyy-MM-dd HH:mm:ss");

        mvc.perform(get("/orm/customer/delete")
                .param("customerId", id.toString())
        ).andExpect(status().isOk());
        mvc.perform(post("/orm/customer/register")
                .content(json).contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andExpect(content().string(id.toString()));
        mvc.perform(get("/orm/customer/load")
                .param("customerId", id.toString())
        ).andExpect(status().isOk()).andExpect(content().json(json));

        customer.setName("Mike");
        customer.setGender("male");
        customer.setIdentification("100101200003052314");
        customer.getAddresses().remove(0);
        Address address2 = new Address(30L,null,"China","Chongqing","Chongqing",
                "ShaPingBaQu","The ChongQing University","023-62325544");
        customer.getAddresses().add(address2);
        json = JSONObject.toJSONStringWithDateFormat(customer, "yyyy-MM-dd HH:mm:ss");
        mvc.perform(post("/orm/customer/modify")
                .content(json).contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
        mvc.perform(get("/orm/customer/load")
                .param("customerId", id.toString())
        ).andExpect(status().isOk()).andExpect(content().json(json));

        mvc.perform(get("/orm/customer/delete")
                .param("customerId", id.toString())
        ).andExpect(status().isOk());
        mvc.perform(get("/orm/customer/load")
                .param("customerId", id.toString())
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
        Long id0 = 1L;
        Customer customer0 = new Customer(id0, "Johnwood", "male",
                "510212199901012211", "13677778888");
        Long id1 = 2L;
        Customer customer1 = new Customer(id1, "Mary", "female",
                "510212200012137812", "13456567878");
        Address address0 = new Address(10L,null,"China","Shandong","Jinan",
                "Lixiaqu","Happy street No.12","0531-88896666");
        Address address1 = new Address(20L,null,"China","Zhejiang","Hangzhou",
                "Xihuqu","The park of Gushan","0571-64128989");
        List<Address> addressList = new ArrayList<>();
        addressList.add(address0);
        addressList.add(address1);
        customer1.setAddresses(addressList);

        List<Customer> customers = new ArrayList<>();
        customers.add(customer0);
        customers.add(customer1);
        String jsonArray = JSONObject.toJSONStringWithDateFormat(customers, "yyyy-MM-dd HH:mm:ss");

        mvc.perform(post("/list/customer/deleteAll")
                .content("[1,2]").contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
        mvc.perform(post("/list/customer/saveAll")
                .content(jsonArray).contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
        mvc.perform(post("/list/customer/loadAll")
                .content("[1,2]").contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andExpect(content().json(jsonArray));

        mvc.perform(post("/query/customerQry")
                .content("{\"page\":0,\"size\":10,\"aggregation\":{\"name\":\"count\"}}")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());

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
        Long id0 = 1L;
        Customer customer0 = new Customer(id0, "Johnwood", "male",
                "510212199901012211", "13677778888");

        Long id1 = 2L;
        Customer customer1 = new Customer(id1, "Mary", "female",
                "510212200012137812", "13456567878");
        Address address0 = new Address(10L,null,"China","Shandong","Jinan",
                "Lixiaqu","Happy street No.12","0531-88896666");
        Address address1 = new Address(20L,null,"China","Zhejiang","Hangzhou",
                "Xihuqu","The park of Gushan","0571-64128989");
        List<Address> addressList = new ArrayList<>();
        addressList.add(address0);
        addressList.add(address1);
        customer1.setAddresses(addressList);

        List<Customer> customers = new ArrayList<>();
        customers.add(customer0);
        customers.add(customer1);
        String jsonArray = JSONObject.toJSONStringWithDateFormat(customers, "yyyy-MM-dd HH:mm:ss");
        Map<String, Object> map = new HashMap<>();
        map.put("customers", customers);
        String json = JSONObject.toJSONStringWithDateFormat(map, "yyyy-MM-dd HH:mm:ss");

        mvc.perform(get("/orm/customer/deleteAll")
                .param("customerIds", "1,2")
        ).andExpect(status().isOk());
        mvc.perform(post("/orm/customer/saveAll")
                .content(json).contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
        mvc.perform(get("/orm/customer/loadAll")
                .param("customerIds", "1,2")
        ).andExpect(status().isOk()).andExpect(content().json(jsonArray));

        mvc.perform(get("/query/customerQry")
                .param("page","0").param("size","10")
        ).andExpect(status().isOk());

        mvc.perform(get("/orm/customer/deleteAll")
                .param("customerIds", "1,2")
        ).andExpect(status().isOk());
        mvc.perform(get("/orm/customer/loadAll")
                .param("customerIds", "1,2")
        ).andExpect(status().isOk()).andExpect(content().json("[]"));
    }
}
