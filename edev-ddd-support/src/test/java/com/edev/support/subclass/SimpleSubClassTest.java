package com.edev.support.subclass;

import com.edev.support.entity.Entity;
import com.edev.trade.TradeApplication;
import com.edev.trade.customer.entity.GoldenVip;
import com.edev.trade.customer.entity.SilverVip;
import com.edev.trade.customer.entity.Vip;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TradeApplication.class)
public class SimpleSubClassTest {
    @Autowired
    private SimpleSubClass dao;
    @Test
    public void testCreateSimpleSubClass() {
        Map<String, Object> json = new HashMap<>();
        json.put("id", 1L);
        json.put("vipType", "golden");
        Entity<Long> entity = dao.createEntityByJson(Vip.class, json);
        assertThat(entity.getClass(), equalTo(GoldenVip.class));
    }
    @Test
    public void testSaveAndDeleteForParent() {
        Long id = 1L;
        Vip vip = new Vip(id,true,200L,"silver");
        dao.delete(id, Vip.class);
        dao.insert(vip);
        assertThat(dao.load(id, Vip.class), equalTo(new SilverVip(id,true,200L)));

        vip.setCoin(300L);
        dao.update(vip);
        assertThat(dao.load(id, Vip.class), equalTo(new SilverVip(id,true,300L)));

        vip.setVipType("golden");
        dao.update(vip);
        assertThat(dao.load(id, Vip.class), equalTo(new GoldenVip(id,true,300L,null)));

        dao.delete(id, Vip.class);
        assertNull(dao.load(id, Vip.class));
    }
    @Test
    public void testSaveAndDeleteForChild() {
        Long id = 1L;
        Vip vip = new SilverVip(id, true, 200L);
        dao.delete(id, Vip.class);
        dao.insert(vip);
        assertThat(dao.load(id, SilverVip.class), equalTo(new SilverVip(id,true,200L)));

        vip.setCoin(300L);
        dao.update(vip);
        assertThat(dao.load(id, SilverVip.class), equalTo(new SilverVip(id,true,300L)));

        vip = new GoldenVip(id, true, 300L, null);
        dao.update(vip);
        assertThat(dao.load(id, GoldenVip.class), equalTo(new GoldenVip(id,true,300L,null)));

        dao.delete(id, GoldenVip.class);
        assertNull(dao.load(id, GoldenVip.class));
    }
    @Test
    public void testSaveAndDeleteForListParent() {
        Long id0 = 1L;
        Vip vip0 = new Vip(id0,true,200L,"silver");
        Long id1 = 2L;
        Vip vip1 = new Vip(id1,true,600L,"golden");
        Long id2 = 3L;
        Vip vip2 = new Vip(id2,false,700L,"golden");
        List<Long> ids = Arrays.asList(id0, id1, id2);
        List<Vip> vips = Arrays.asList(vip0, vip1, vip2);

        dao.deleteForList(ids, Vip.class);
        dao.insertOrUpdateForList(vips);
        assertThat(dao.loadForList(ids, Vip.class), hasItems(
                new SilverVip(id0,true,200L),
                new GoldenVip(id1,true,600L,null),
                new GoldenVip(id2,false,700L,null)
        ));

        vip0.setVipType("golden");
        vip1.setCoin(800L);
        Long id3 = 4L;
        Vip vip3 = new Vip(id3,true,200L,"silver");
        ids = Arrays.asList(id0, id1, id3);
        vips = Arrays.asList(vip0, vip1, vip3);
        dao.insertOrUpdateForList(vips);
        assertThat(dao.loadForList(ids, Vip.class), hasItems(
                new GoldenVip(id0,true,200L,null),
                new GoldenVip(id1,true,800L,null),
                new SilverVip(id3,true,200L)
        ));

        ids = Arrays.asList(id0, id1, id2, id3);
        dao.deleteForList(ids, Vip.class);
        assertTrue(dao.loadForList(ids, Vip.class).isEmpty());
    }
    @Test
    public void testSaveAndDeleteForListChild() {
        Long id0 = 1L;
        Vip vip0 = new SilverVip(id0,true,200L);
        Long id1 = 2L;
        Vip vip1 = new GoldenVip(id1,true,600L, 200D);
        Long id2 = 3L;
        Vip vip2 = new GoldenVip(id2,false,700L,250D);
        List<Long> ids = Arrays.asList(id0, id1, id2);
        List<Vip> vips = Arrays.asList(vip0, vip1, vip2);

        dao.deleteForList(ids, Vip.class);
        dao.insertOrUpdateForList(vips);
        assertThat(dao.loadForList(ids, Vip.class), hasItems(
                new SilverVip(id0,true,200L),
                new GoldenVip(id1,true,600L,200D),
                new GoldenVip(id2,false,700L,250D)
        ));

        vip0 = new GoldenVip(id0,true,200L,0D);
        vip1.setCoin(800L);
        Long id3 = 4L;
        Vip vip3 = new SilverVip(id3,true,200L);
        ids = Arrays.asList(id0, id1, id3);
        vips = Arrays.asList(vip0, vip1, vip3);
        dao.insertOrUpdateForList(vips);
        assertThat(dao.loadForList(ids, Vip.class), hasItems(
                new GoldenVip(id0,true,200L,0D),
                new GoldenVip(id1,true,800L,200D),
                new SilverVip(id3,true,200L)
        ));

        ids = Arrays.asList(id0, id1, id2, id3);
        dao.deleteForList(ids, Vip.class);
        assertTrue(dao.loadForList(ids, Vip.class).isEmpty());
    }
}
