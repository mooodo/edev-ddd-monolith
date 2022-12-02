package com.edev.trade.product.web;

import com.alibaba.fastjson.JSONObject;
import com.edev.trade.product.entity.Distributor;
import com.edev.trade.product.entity.Vendor;
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
public class SupplierMvcTest {
    @Autowired
    private MockMvc mvc;
    /**
     * 业务需求：
     * 1）供应商按照继承分为分销商和零售商，按照供应商类型来划分
     * 2）可以通过前端读取的数据，创建不同类型的供应商对象
     * 3）通过修改供应商类型，可以切换不同类型的供应商对象
     * 4）将不同子类的数据分别写入到数据库的不同表中
     */
    @Test
    public void testSaveAndDelete() throws Exception {
        Long id = 1L;
        Distributor distributor = new Distributor(id, "eDev");
        String json = JSONObject.toJSONString(distributor);

        mvc.perform(get("/orm/supplier/removeSupplier")
                .content(json).contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
        mvc.perform(post("/orm/supplier/addSupplier")
                .content(json).contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andExpect(content().string(id.toString()));
        mvc.perform(get("/orm/supplier/getDistributor")
                .param("id",id.toString())
        ).andExpect(status().isOk()).andExpect(content().json(json));

        distributor.setName("eGenius");
        String json1 = JSONObject.toJSONString(distributor);
        mvc.perform(post("/orm/supplier/modifySupplier")
                .content(json1).contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
        mvc.perform(get("/orm/supplier/getDistributor")
                .param("id",id.toString())
        ).andExpect(status().isOk()).andExpect(content().json(json1));

        mvc.perform(get("/orm/supplier/removeDistributor")
                .param("id", id.toString())
        ).andExpect(status().isOk());
        mvc.perform(get("/orm/supplier/getDistributor")
                .param("id",id.toString())
        ).andExpect(status().isOk()).andExpect(content().string(""));
    }

    @Test
    public void testSaveAndDeleteWithJoin() throws Exception {
        Long id = 1L;
        Vendor vendor = new Vendor(id, "The Great Chemist's shop", 10001L);
        Distributor distributor = new Distributor(10001L, "桐君阁");
        vendor.setDistributor(distributor);
        String json = JSONObject.toJSONString(vendor);

        mvc.perform(get("/orm/supplier/removeSupplier")
                .content(json).contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
        mvc.perform(post("/orm/supplier/addSupplier")
                .content(json).contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andExpect(content().string(id.toString()));
        mvc.perform(get("/orm/supplier/getVendor")
                .param("id",id.toString())
        ).andExpect(status().isOk()).andExpect(content().json(json));

        Distributor distributor1 = new Distributor(10002L, "晨光百货");
        vendor.setDistributorId(distributor1.getId());
        vendor.setDistributor(distributor1);
        String json1 = JSONObject.toJSONString(vendor);
        mvc.perform(post("/orm/supplier/modifySupplier")
                .content(json1).contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
        mvc.perform(get("/orm/supplier/getVendor")
                .param("id",id.toString())
        ).andExpect(status().isOk()).andExpect(content().json(json1));

        mvc.perform(get("/orm/supplier/removeVendor")
                .param("id", id.toString())
        ).andExpect(status().isOk());
        mvc.perform(get("/orm/supplier/getVendor")
                .param("id",id.toString())
        ).andExpect(status().isOk()).andExpect(content().string(""));
    }
}
