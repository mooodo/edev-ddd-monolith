package com.edev.trade.product.service;

import com.edev.trade.product.entity.Supplier;

import java.util.Collection;
import java.util.List;

public interface SupplierService {
    Long register(Supplier supplier);
    void modify(Supplier supplier);
    void remove(Supplier template);
    void removeById(Long id);
    Supplier loadById(Long id);
    void saveAll(List<Supplier> suppliers);
    void deleteAll(List<Long> ids);
    Collection<Supplier> loadAll(List<Long> ids);
    Collection<Supplier> getAll();
}
