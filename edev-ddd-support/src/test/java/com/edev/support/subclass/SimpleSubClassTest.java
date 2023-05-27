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
        Vip vip = Vip.build().setValues(id,true,200L,"silver");
        dao.delete(id, Vip.class);
        dao.insert(vip);
        assertThat(dao.load(id, Vip.class), equalTo(SilverVip.build().setValues(id,true,200L)));

        vip.setCoin(300L);
        dao.update(vip);
        assertThat(dao.load(id, Vip.class), equalTo(SilverVip.build().setValues(id,true,300L)));

        vip.setVipType("golden");
        dao.update(vip);
        assertThat(dao.load(id, Vip.class), equalTo(GoldenVip.build().setValues(id,true,300L,(Double) null)));

        dao.delete(id, Vip.class);
        assertNull(dao.load(id, Vip.class));
    }
    @Test
    public void testSaveAndDeleteForChild() {
        Long id = 1L;
        Vip vip = SilverVip.build().setValues(id, true, 200L);
        dao.delete(id, Vip.class);
        dao.insert(vip);
        assertThat(dao.load(id, SilverVip.class), equalTo(SilverVip.build().setValues(id,true,200L)));

        vip.setCoin(300L);
        dao.update(vip);
        assertThat(dao.load(id, SilverVip.class), equalTo(SilverVip.build().setValues(id,true,300L)));

        vip = GoldenVip.build().setValues(id, true, 300L, 100D);
        dao.update(vip);
        assertThat(dao.load(id, GoldenVip.class), equalTo(GoldenVip.build().setValues(id,true,300L,100D)));

        dao.delete(id, GoldenVip.class);
        assertNull(dao.load(id, GoldenVip.class));
    }
    @Test
    public void testSaveAndDeleteForListParent() {
        Long id0 = 1L;
        Vip vip0 = Vip.build().setValues(id0,true,200L,"silver");
        Long id1 = 2L;
        Vip vip1 = Vip.build().setValues(id1,true,600L,"golden");
        Long id2 = 3L;
        Vip vip2 = Vip.build().setValues(id2,false,700L,"golden");
        List<Long> ids = Arrays.asList(id0, id1, id2);
        List<Vip> vips = Arrays.asList(vip0, vip1, vip2);

        dao.deleteForList(ids, Vip.class);
        dao.insertOrUpdateForList(vips);
        assertThat(dao.loadForList(ids, Vip.class), hasItems(
                SilverVip.build().setValues(id0,true,200L),
                GoldenVip.build().setValues(id1,true,600L,(Double) null),
                GoldenVip.build().setValues(id2,false,700L,(Double) null)
        ));

        vip0.setVipType("golden");
        vip1.setCoin(800L);
        Long id3 = 4L;
        Vip vip3 = Vip.build().setValues(id3,true,200L,"silver");
        ids = Arrays.asList(id0, id1, id3);
        vips = Arrays.asList(vip0, vip1, vip3);
        dao.insertOrUpdateForList(vips);
        assertThat(dao.loadForList(ids, Vip.class), hasItems(
                GoldenVip.build().setValues(id0,true,200L,(Double) null),
                GoldenVip.build().setValues(id1,true,800L,(Double) null),
                SilverVip.build().setValues(id3,true,200L)
        ));

        ids = Arrays.asList(id0, id1, id2, id3);
        dao.deleteForList(ids, Vip.class);
        assertTrue(dao.loadForList(ids, Vip.class).isEmpty());
    }
    @Test
    public void testSaveAndDeleteForListChild() {
        Long id0 = 1L;
        Vip vip0 = SilverVip.build().setValues(id0,true,200L);
        Long id1 = 2L;
        Vip vip1 = GoldenVip.build().setValues(id1,true,600L, 200D);
        Long id2 = 3L;
        Vip vip2 = GoldenVip.build().setValues(id2,false,700L,250D);
        List<Long> ids = Arrays.asList(id0, id1, id2);
        List<Vip> vips = Arrays.asList(vip0, vip1, vip2);

        dao.deleteForList(ids, Vip.class);
        dao.insertOrUpdateForList(vips);
        assertThat(dao.loadForList(ids, Vip.class), hasItems(
                SilverVip.build().setValues(id0,true,200L),
                GoldenVip.build().setValues(id1,true,600L,200D),
                GoldenVip.build().setValues(id2,false,700L,250D)
        ));

        vip0 = GoldenVip.build().setValues(id0,true,200L,0D);
        vip1.setCoin(800L);
        Long id3 = 4L;
        Vip vip3 = SilverVip.build().setValues(id3,true,200L);
        ids = Arrays.asList(id0, id1, id3);
        vips = Arrays.asList(vip0, vip1, vip3);
        dao.insertOrUpdateForList(vips);
        assertThat(dao.loadForList(ids, Vip.class), hasItems(
                GoldenVip.build().setValues(id0,true,200L,0D),
                GoldenVip.build().setValues(id1,true,800L,200D),
                SilverVip.build().setValues(id3,true,200L)
        ));

        ids = Arrays.asList(id0, id1, id2, id3);
        dao.deleteForList(ids, Vip.class);
        assertTrue(dao.loadForList(ids, Vip.class).isEmpty());
    }
}
