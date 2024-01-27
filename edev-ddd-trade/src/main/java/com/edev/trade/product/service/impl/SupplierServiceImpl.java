package com.edev.trade.product.service.impl;

import com.edev.support.dao.BasicDao;
import com.edev.support.exception.ValidException;
import com.edev.trade.product.entity.Supplier;
import com.edev.trade.product.service.SupplierService;
import lombok.NonNull;

import java.util.Collection;
import java.util.List;

public class SupplierServiceImpl implements SupplierService {
    private final BasicDao dao;

    public SupplierServiceImpl(BasicDao dao) {
        this.dao = dao;
    }

    private void valid(@NonNull Supplier supplier) {
        if(supplier.getName()==null) throw new ValidException("The name cannot be null!");
        if(supplier.getSupplierType()==null) throw new ValidException("The supplier type cannot be null!");
    }
    @Override
    public Long register(@NonNull Supplier supplier) {
        valid(supplier);
        return dao.insert(supplier);
    }

    @Override
    public void modify(@NonNull Supplier supplier) {
        dao.update(supplier);
    }

    @Override
    public void remove(@NonNull Supplier template) {
        dao.delete(template);
    }

    @Override
    public void removeById(@NonNull Long id) {
        dao.delete(id, Supplier.class);
    }

    @Override
    public Supplier loadById(@NonNull Long id) {
        return dao.load(id, Supplier.class);
    }

    @Override
    public void saveAll(@NonNull List<Supplier> suppliers) {
        suppliers.forEach(this::valid);
        dao.insertOrUpdateForList(suppliers);
    }

    @Override
    public void deleteAll(@NonNull List<Long> ids) {
        dao.deleteForList(ids, Supplier.class);
    }

    @Override
    public Collection<Supplier> loadAll(@NonNull List<Long> ids) {
        return dao.loadForList(ids, Supplier.class);
    }

    @Override
    public Collection<Supplier> getAll() {
        return dao.loadAll(new Supplier());
    }
}
