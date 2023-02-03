package com.edev.trade.customer.service.impl;

import com.edev.support.dao.BasicDao;
import com.edev.support.exception.ValidException;
import com.edev.support.utils.DateUtils;
import com.edev.trade.customer.entity.Account;
import com.edev.trade.customer.service.AccountService;

public class AccountServiceImpl implements AccountService {
    private final BasicDao dao;
    public AccountServiceImpl(BasicDao dao) {
        this.dao = dao;
    }

    private void validAccount(Account account) {
        if(account.getId()==null) throw new ValidException("The id is null");
    }

    @Override
    public Long create(Account account) {
        validAccount(account);
        account.setCreateTime(DateUtils.getNow());
        return dao.insert(account);
    }

    @Override
    public void modify(Account account) {
        validAccount(account);
        account.setUpdateTime(DateUtils.getNow());
        dao.update(account);
    }

    @Override
    public void remove(Long id) {
        dao.delete(id, Account.class);
    }

    @Override
    public Account get(Long id) {
        return dao.load(id, Account.class);
    }

}
