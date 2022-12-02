package com.edev.trade.customer.service;

import com.edev.trade.customer.entity.Account;

public interface AccountService {
    Long create(Account account);
    void modify(Account account);
    void remove(Long id);
    Account get(Long id);
}
