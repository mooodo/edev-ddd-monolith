package com.edev.trade.product.service;

import com.edev.trade.product.entity.Supplier;

public interface SupplierService {
    Long register(Supplier supplier);
    void modify(Supplier supplier);
    void remove(Supplier template);
    void removeById(Long id);
    Supplier loadById(Long id);
}
