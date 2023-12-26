package com.edev.trade.customer.service.impl;

import com.edev.support.dao.BasicDao;
import com.edev.support.exception.ValidException;
import com.edev.support.utils.DateUtils;
import com.edev.trade.customer.entity.Account;
import com.edev.trade.customer.entity.JournalAccount;
import com.edev.trade.customer.service.AccountService;
import com.edev.trade.customer.service.JournalAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

public class AccountServiceImpl implements AccountService {
    private final BasicDao dao;
    @Autowired
    private JournalAccountService journalAccountService;
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
    @Override
    @Transactional
    public Double topUp(Long id, Double amount) {
        if(id==null||amount==null)
            throw new ValidException("The id[%d] or amount[%d] is null", id, amount);
        Account account = this.get(id);
        if(account==null)
            throw new ValidException("The account[id:%d] isn't available", id);
        Double balance = account.getBalance() + amount;
        account.setBalance(balance);
        this.modify(account);

        JournalAccount journalAccount =
                JournalAccount.build().setValues(account.getId(), amount, "topUp");
        journalAccountService.addJournalAccount(journalAccount);
        return balance;
    }

    @Override
    @Transactional
    public Double payoff(Long id, Double amount) {
        if(id==null||amount==null)
            throw new ValidException("The id[%d] or amount[%d] is null", id, amount);
        Account account = this.get(id);
        if(account==null)
            throw new ValidException("The account[id:%d] isn't available", id);
        if(account.getBalance() < amount)
            throw new ValidException("The account[id:%d] has no enough money[%d]", id, account.getBalance());
        Double balance = account.getBalance() - amount;
        account.setBalance(balance);
        this.modify(account);

        JournalAccount journalAccount =
                JournalAccount.build().setValues(account.getId(), amount, "payoff");
        journalAccountService.addJournalAccount(journalAccount);
        return balance;
    }

    @Override
    @Transactional
    public Double refund(Long id, Double amount) {
        if(id==null||amount==null)
            throw new ValidException("The id[%d] or amount[%d] is null", id, amount);
        Account account = this.get(id);
        if(account==null)
            throw new ValidException("The account[id:%d] isn't available", id);
        Double balance = account.getBalance() + amount;
        account.setBalance(balance);
        this.modify(account);

        JournalAccount journalAccount =
                JournalAccount.build().setValues(account.getId(), amount, "refund");
        journalAccountService.addJournalAccount(journalAccount);
        return balance;
    }
}
