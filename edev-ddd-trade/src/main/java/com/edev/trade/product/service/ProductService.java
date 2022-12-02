package com.edev.trade.product.service;

import com.edev.trade.product.entity.Product;

import java.util.Collection;
import java.util.List;

public interface ProductService {
    Long saveProduct(Product product);
    void saveProducts(List<Product> products);
    void deleteProduct(Long id);
    void deleteProducts(List<Long> ids);
    Product getProduct(Long id);
    Collection<Product> listProducts(List<Long> ids);
}
