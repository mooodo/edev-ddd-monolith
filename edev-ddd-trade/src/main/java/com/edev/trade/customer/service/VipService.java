package com.edev.trade.customer.service;

import com.edev.trade.customer.entity.Vip;

import java.util.Collection;
import java.util.List;

public interface VipService {
    Long register(Vip vip);
    void modify(Vip vip);
    void delete(Long vipId);
    void deleteVip(Vip vip);
    Vip load(Long vipId);
    void saveAll(List<Vip> list);
    void deleteAll(List<Long> vipIds);
    Collection<Vip> loadAll(List<Long> vipIds);
    Vip loadByCustomer(Long customerId);
    Double discount(Vip vip);
    Double discount(Long customerId);
}
