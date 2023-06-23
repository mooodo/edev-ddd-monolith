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
public class OrderDiscountMvcTest {
    @Autowired
    private MockMvc mvc;
    /**
     * 业务需求：
     * 如果订单明细中的商品存在产品折扣，则订单金额按照产品折扣进行打折
     */
    @Test
    public void testProductDiscount() throws Exception {
        String id = "3";
        String json = JsonFile.read("json/discount/orderProductDiscount.json");
        String excepted = JsonFile.read("json/discount/orderProductDiscountExcepted.json");
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
     * 如果订购订单的用户是VIP会员，则所有的商品按照VIP会员进行折扣
     */
    @Test
    public void testVipDiscount() throws Exception {
        String id = "4";
        String json = JsonFile.read("json/discount/orderVipDiscount.json");
        String excepted = JsonFile.read("json/discount/orderVipDiscountExcepted.json");
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
}
