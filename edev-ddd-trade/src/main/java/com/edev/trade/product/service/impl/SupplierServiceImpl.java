package com.edev.trade.product.service.impl;

import com.edev.support.dao.BasicDao;
import com.edev.support.exception.ValidException;
import com.edev.trade.product.entity.Supplier;
import com.edev.trade.product.service.SupplierService;

import java.util.Collection;
import java.util.List;

public class SupplierServiceImpl implements SupplierService {
    private final BasicDao dao;

    public SupplierServiceImpl(BasicDao dao) {
        this.dao = dao;
    }

    private void valid(Supplier supplier) {
        if(supplier==null) throw new ValidException("The supplier cannot be null!");
        if(supplier.getName()==null) throw new ValidException("The name cannot be null!");
        if(supplier.getSupplierType()==null) throw new ValidException("The supplier type cannot be null!");
    }
    @Override
    public Long register(Supplier supplier) {
        valid(supplier);
        return dao.insert(supplier);
    }

    @Override
    public void modify(Supplier supplier) {
        valid(supplier);
        dao.update(supplier);
    }

    @Override
    public void remove(Supplier template) {
        dao.delete(template);
    }

    @Override
    public void removeById(Long id) {
        dao.delete(id, Supplier.class);
    }

    @Override
    public Supplier loadById(Long id) {
        return dao.load(id, Supplier.class);
    }

    @Override
    public void saveAll(List<Supplier> suppliers) {
        suppliers.forEach(this::valid);
        dao.insertOrUpdateForList(suppliers);
    }

    @Override
    public void deleteAll(List<Long> ids) {
        dao.deleteForList(ids, Supplier.class);
    }

    @Override
    public Collection<Supplier> loadAll(List<Long> ids) {
        return dao.loadForList(ids, Supplier.class);
    }
}
