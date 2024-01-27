package com.edev.trade.order.service.impl;

import com.edev.support.dao.BasicDao;
import com.edev.trade.order.entity.Discount;
import com.edev.trade.order.entity.Order;
import com.edev.trade.order.service.DiscountService;
import com.edev.trade.order.service.impl.discount.DiscountStrategy;
import lombok.NonNull;
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
    public Long create(@NonNull Discount discount) {
        return dao.insert(discount);
    }

    @Override
    public void modify(@NonNull Discount discount) {
        dao.update(discount);
    }

    @Override
    public void delete(@NonNull Discount template) {
        dao.delete(template);
    }

    @Override
    public Discount loadById(@NonNull Long discountId) {
        return dao.load(discountId, Discount.class);
    }

    @Override
    public Discount load(@NonNull Discount template) {
        Collection<Discount> collection = dao.loadAll(template);
        if(collection==null||collection.isEmpty())
            return null;
        return collection.iterator().next();
    }

    @Override
    public void deleteById(@NonNull Long discountId) {
        dao.delete(discountId, Discount.class);
    }

    @Override
    public void doDiscount(@NonNull Order order) {
        getStrategies().forEach((key,strategy) -> strategy.doDiscount(order));
    }
}
