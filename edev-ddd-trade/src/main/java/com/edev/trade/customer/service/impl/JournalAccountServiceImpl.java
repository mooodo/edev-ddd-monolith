package com.edev.trade.customer.service.impl;

import com.edev.support.dao.BasicDao;
import com.edev.support.exception.ValidException;
import com.edev.trade.customer.entity.JournalAccount;
import com.edev.trade.customer.service.JournalAccountService;
import lombok.NonNull;

import java.util.Collection;

public class JournalAccountServiceImpl implements JournalAccountService {
    private final BasicDao dao;
    public JournalAccountServiceImpl(BasicDao dao) {
        this.dao = dao;
    }

    @Override
    public Long addJournalAccount(@NonNull JournalAccount journalAccount) {
        if(journalAccount.getAccountId()==null)
            throw new ValidException("The account id of the journal account is null:[%]", journalAccount);
        return dao.insert(journalAccount);
    }

    @Override
    public void removeJournalAccount(@NonNull Long id) {
        dao.delete(id, JournalAccount.class);
    }

    @Override
    public JournalAccount load(@NonNull Long id) {
        return dao.load(id, JournalAccount.class);
    }

    @Override
    public Collection<JournalAccount> getJournalAccount(@NonNull Long accountId) {
        JournalAccount journalAccount = new JournalAccount();
        journalAccount.setAccountId(accountId);
        return dao.loadAll(journalAccount);
    }
}
