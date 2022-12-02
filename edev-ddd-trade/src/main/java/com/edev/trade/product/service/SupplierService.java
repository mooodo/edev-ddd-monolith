package com.edev.trade.product.service;

import com.edev.trade.product.entity.Distributor;
import com.edev.trade.product.entity.Supplier;
import com.edev.trade.product.entity.Vendor;

import java.util.Collection;
import java.util.List;

public interface SupplierService {
    Long addSupplier(Supplier supplier);
    void modifySupplier(Supplier supplier);
    void removeSupplier(Supplier supplier);
    void removeDistributor(Long id);
    Distributor getDistributor(Long id);
    Collection<Distributor> listDistributors(List<Long> ids);
    void removeVendor(Long id);
    Vendor getVendor(Long id);
    Collection<Vendor> listVendors(List<Long> ids);
}
