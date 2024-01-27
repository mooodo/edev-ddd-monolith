package com.edev.trade.product.service.impl;

import com.edev.support.dao.BasicDao;
import com.edev.support.exception.ValidException;
import com.edev.trade.product.entity.Product;
import com.edev.trade.product.service.ProductService;
import lombok.NonNull;

import java.util.Collection;
import java.util.List;

public class ProductServiceImpl implements ProductService {
    private final BasicDao dao;
    public ProductServiceImpl(BasicDao dao) {
        this.dao = dao;
    }

    private void validProduct(@NonNull Product product) {
        if(product.getId()==null) throw new ValidException("The id is null");
        if(product.getName()==null) throw new ValidException("The name is null");
    }

    @Override
    public Long saveProduct(@NonNull Product product) {
        validProduct(product);
        return dao.insertOrUpdate(product);
    }

    @Override
    public void saveProducts(@NonNull List<Product> products) {
        products.forEach(this::validProduct);
        dao.insertOrUpdateForList(products);
    }

    @Override
    public void deleteProduct(@NonNull Long id) {
        dao.delete(id, Product.class);
    }

    @Override
    public void deleteProducts(@NonNull List<Long> ids) {
        dao.deleteForList(ids, Product.class);
    }

    @Override
    public Product getProduct(@NonNull Long id) {
        return dao.load(id, Product.class);
    }

    @Override
    public Collection<Product> listProducts(@NonNull List<Long> ids) {
        return dao.loadForList(ids, Product.class);
    }
}
