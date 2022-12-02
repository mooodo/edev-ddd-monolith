package com.edev.trade.customer.service.impl;

import com.edev.support.dao.BasicDao;
import com.edev.support.exception.ValidException;
import com.edev.trade.customer.entity.JournalAccount;
import com.edev.trade.customer.service.JournalAccountService;

import java.util.Collection;

public class JournalAccountServiceImpl implements JournalAccountService {
    private final BasicDao dao;
    public JournalAccountServiceImpl(BasicDao dao) {
        this.dao = dao;
    }

    @Override
    public Long addJournalAccount(JournalAccount journalAccount) {
        if(journalAccount==null)
            throw new ValidException("The journal account cannot be null");
        if(journalAccount.getAccountId()==null)
            throw new ValidException("The account id of the journal account is null:[%]", journalAccount);
        return dao.insert(journalAccount);
    }

    @Override
    public void removeJournalAccount(Long id) {
        if(id==null) throw new ValidException("The id is null");
        dao.delete(id, JournalAccount.class);
    }

    @Override
    public JournalAccount load(Long id) {
        if(id==null) throw new ValidException("The id is null");
        return dao.load(id, JournalAccount.class);
    }

    @Override
    public Collection<JournalAccount> getJournalAccount(Long accountId) {
        if(accountId==null) throw new ValidException("The account id is null");
        JournalAccount journalAccount = new JournalAccount();
        journalAccount.setAccountId(accountId);
        return dao.loadAll(journalAccount);
    }
}
