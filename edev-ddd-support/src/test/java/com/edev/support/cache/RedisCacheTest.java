package com.edev.support.cache;

import com.edev.support.entity.Entity;
import com.edev.trade.TradeApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TradeApplication.class)
public class RedisCacheTest {
    @Autowired
    private RedisCache cache;
    @Test
    public void testSetGetAndDelete() {
        Long key = 1L;
        Obj obj = new Obj(key, "John");
        cache.set(obj);
        Obj actual = cache.get(key,Obj.class);
        assertThat(actual, equalTo(obj));

        cache.delete(key, Obj.class);
        Obj deleted = cache.get(key,Obj.class);
        assertNull(deleted);
    }
    @Test
    public void testSetGetAndDeleteForList() {
        List<Obj> list = new ArrayList<>();
        List<Long> ids = new ArrayList<>();

        Long key0 = 1L;
        Obj obj0 = new Obj(key0, "John");
        Long key1 = 2L;
        Obj obj1 = new Obj(key1, "Mary");
        list.add(obj0);
        list.add(obj1);
        ids.add(key0);
        ids.add(key1);

        cache.setForList(list);
        List<Obj> actual = cache.getForList(ids, Obj.class);
        assertThat(actual, hasItems(obj0, obj1));
    }
}

class Obj extends Entity<Long> {
    private Long id;
    private String name;
    public Obj(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
