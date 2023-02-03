package com.edev.trade.order.service.impl;

import com.edev.support.dao.BasicDao;
import com.edev.trade.order.entity.Discount;
import com.edev.trade.order.entity.Order;
import com.edev.trade.order.service.DiscountService;
import com.edev.trade.order.service.impl.discount.DiscountStrategy;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class DiscountServiceImpl implements DiscountService {
    private final BasicDao dao;
    private final List<DiscountStrategy> strategies;

    public DiscountServiceImpl(BasicDao dao, List<DiscountStrategy> strategies) {
        this.dao = dao;
        this.strategies = strategies;
    }

    @Override
    public void create(Discount discount) {
        dao.insert(discount);
    }

    @Override
    public void modify(Discount discount) {
        dao.update(discount);
    }

    @Override
    public Discount load(Long discountId) {
        return dao.load(discountId, Discount.class);
    }

    @Override
    public Discount load(Discount template) {
        Collection<Discount> collection = dao.loadAll(template);
        if(collection==null||collection.isEmpty())
            return new Discount();
        return collection.iterator().next();
    }

    @Override
    public void delete(Long discountId) {
        dao.delete(discountId, Discount.class);
    }

    @Override
    public void doDiscount(Order order) {
        strategies.forEach(strategy -> strategy.doDiscount(order));
    }
}
