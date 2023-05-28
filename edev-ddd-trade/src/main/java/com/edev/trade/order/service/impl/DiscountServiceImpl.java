package com.edev.trade.order.service.impl;

import com.edev.support.dao.BasicDao;
import com.edev.support.exception.ValidException;
import com.edev.trade.order.entity.Discount;
import com.edev.trade.order.entity.Order;
import com.edev.trade.order.service.DiscountService;
import com.edev.trade.order.service.impl.discount.DiscountStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import java.util.*;

public class DiscountServiceImpl implements DiscountService {
    @Autowired
    private ApplicationContext applicationContext;
    private final BasicDao dao;
    private Map<String,DiscountStrategy> strategies;

    public DiscountServiceImpl(BasicDao dao) {
        this.dao = dao;
    }

    private Map<String,DiscountStrategy> getStrategies() {
        if (strategies == null) {
            strategies = applicationContext.getBeansOfType(DiscountStrategy.class);
        }
        return strategies;
    }

    @Override
    public Long create(Discount discount) {
        return dao.insert(discount);
    }

    @Override
    public void modify(Discount discount) {
        dao.update(discount);
    }

    @Override
    public void delete(Discount template) {
        dao.delete(template);
    }

    @Override
    public Discount loadById(Long discountId) {
        return dao.load(discountId, Discount.class);
    }

    @Override
    public Discount load(Discount template) {
        Collection<Discount> collection = dao.loadAll(template);
        if(collection==null||collection.isEmpty())
            return null;
        return collection.iterator().next();
    }

    @Override
    public void deleteById(Long discountId) {
        dao.delete(discountId, Discount.class);
    }

    @Override
    public void doDiscount(Order order) {
        getStrategies().forEach((key,strategy) -> strategy.doDiscount(order));
    }
}
