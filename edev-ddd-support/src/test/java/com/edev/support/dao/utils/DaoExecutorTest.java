package com.edev.support.dao.utils;

import com.edev.support.dao.impl.utils.DaoEntity;
import com.edev.support.dao.impl.utils.DaoEntityBuilder;
import com.edev.support.dao.impl.utils.DaoExecutor;
import com.edev.trade.TradeApplication;
import com.edev.trade.authority.entity.User;
import com.edev.trade.customer.entity.JournalAccount;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TradeApplication.class)
public class DaoExecutorTest {
    @Autowired
    private DaoExecutor daoExecutor;

    private Map<Object, Object> buildColMap(Object key, Object value) {
        Map<Object, Object> colMap = new HashMap<>();
        colMap.put(DaoEntityBuilder.KEY, key);
        colMap.put(DaoEntityBuilder.VALUE, value);
        return colMap;
    }

    @Test
    public void testSaveAndDelete() {
        Long id = 1L;
        User user = User.build().setValues(id,"John","123");
        DaoEntity daoEntity = new DaoEntity();
        daoEntity.setTableName("t_user");
        daoEntity.addColMap(buildColMap("id", id));
        daoExecutor.delete(daoEntity);

        daoEntity = new DaoEntity();
        daoEntity.setTableName("t_user");
        daoEntity.addColMap(buildColMap("id",id));
        daoEntity.addColMap(buildColMap("name","John"));
        daoEntity.addColMap(buildColMap("password","123"));
        Long rtn = daoExecutor.insert(daoEntity, user);
        assertThat("different id when insert",rtn, equalTo(id));

        daoEntity = new DaoEntity();
        daoEntity.setTableName("t_user");
        daoEntity.addColMap(buildColMap("id", id));
        assertThat("fail insert",daoExecutor.find(daoEntity, User.class), hasItem(user));

        daoEntity = new DaoEntity();
        daoEntity.setTableName("t_user");
        daoEntity.addColMap(buildColMap("name","Mary"));
        daoEntity.addPkMap(buildColMap("id",id));
        daoExecutor.update(daoEntity);

        user.setName("Mary");
        daoEntity = new DaoEntity();
        daoEntity.setTableName("t_user");
        daoEntity.addColMap(buildColMap("id", id));
        assertThat("fail update",daoExecutor.find(daoEntity, User.class), hasItem(user));

        daoEntity = new DaoEntity();
        daoEntity.setTableName("t_user");
        daoEntity.addColMap(buildColMap("id", id));
        daoExecutor.delete(daoEntity);

        daoEntity = new DaoEntity();
        daoEntity.setTableName("t_user");
        daoEntity.addColMap(buildColMap("id", id));
        assertTrue("fail delete",daoExecutor.find(daoEntity, User.class).isEmpty());
    }
    @Test
    public void testSaveAndDeleteForList() {
        JournalAccount journalAccount0 = JournalAccount.build().setValues(null,5000D,"test");
        JournalAccount journalAccount1 = JournalAccount.build().setValues(null,3000D,"test");
        DaoEntity daoEntity = new DaoEntity();
        daoEntity.setTableName("t_journal_account");
        daoEntity.addColMap(buildColMap("amount",5000D));
        daoEntity.addColMap(buildColMap("operation","test"));
        Long id0 = daoExecutor.insert(daoEntity, journalAccount0);

        daoEntity = new DaoEntity();
        daoEntity.setTableName("t_journal_account");
        daoEntity.addColMap(buildColMap("amount",3000D));
        daoEntity.addColMap(buildColMap("operation","test"));
        Long id1 = daoExecutor.insert(daoEntity, journalAccount1);

        journalAccount0.setId(id0);
        journalAccount1.setId(id1);
        daoEntity = new DaoEntity();
        daoEntity.setTableName("t_journal_account");
        daoEntity.addColMap(buildColMap("operation","test"));
        assertThat("fail find",daoExecutor.find(daoEntity, JournalAccount.class), hasItems(
                journalAccount0, journalAccount1
        ));

        daoEntity = new DaoEntity();
        daoEntity.setTableName("t_journal_account");
        daoEntity.addColMap(buildColMap("id",Arrays.asList(id0,id1)));
        assertThat("fail load",daoExecutor.load(daoEntity, JournalAccount.class), hasItems(
                journalAccount0, journalAccount1
        ));

        daoEntity = new DaoEntity();
        daoEntity.setTableName("t_journal_account");
        daoEntity.addPkMap(buildColMap("id",Arrays.asList(id0,id1)));
        daoExecutor.deleteForList(daoEntity);

        daoEntity = new DaoEntity();
        daoEntity.setTableName("t_journal_account");
        daoEntity.addColMap(buildColMap("id",Arrays.asList(id0,id1)));
        assertTrue("fail delete all",daoExecutor.load(daoEntity, JournalAccount.class).isEmpty());
    }
}
