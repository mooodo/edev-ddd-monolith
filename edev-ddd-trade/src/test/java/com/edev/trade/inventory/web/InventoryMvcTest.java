package com.edev.trade.inventory.web;

import com.alibaba.fastjson.JSONObject;
import com.edev.support.utils.JsonFile;
import com.edev.trade.inventory.entity.Inventory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class InventoryMvcTest {
    @Autowired
    private MockMvc mvc;

    /**
     * 业务需求：
     * 库存的入库、出库、查库与删除库存
     */
    @Test
    public void testStockInAndOut() throws Exception {
        String id = "1";
        String json = JsonFile.read("json/inventory/inventory0.json");
        mvc.perform(get("/orm/inventory/remove")
                .param("id", id)
        ).andExpect(status().isOk());
        mvc.perform(get("/orm/inventory/stockIn")
                .param("id", id).param("quantity","50")
        ).andExpect(status().isOk());
        mvc.perform(get("/orm/inventory/checkInventory")
                .param("id", id)
        ).andExpect(status().isOk()).andExpect(content().json(json));

        String json1 = JsonFile.read("json/inventory/inventory1.json");
        mvc.perform(get("/orm/inventory/stockOut")
                .param("id",id).param("quantity","50")
        ).andExpect(status().isOk());
        mvc.perform(get("/orm/inventory/checkInventory")
                .param("id", id)
        ).andExpect(status().isOk()).andExpect(content().json(json1));

        mvc.perform(get("/orm/inventory/remove")
                .param("id", id)
        ).andExpect(status().isOk());
        mvc.perform(get("/orm/inventory/checkInventory")
                .param("id", id)
        ).andExpect(status().isOk()).andExpect(content().string(""));
    }
}
