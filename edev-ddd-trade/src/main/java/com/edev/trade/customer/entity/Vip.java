package com.edev.trade.customer.entity;

import com.edev.support.entity.Entity;

import java.util.Date;

public class Vip extends Entity<Long> {
    protected Long id;
    protected Date createTime;
    protected Date updateTime;
    protected String available;
    protected Long coin;
    protected String vipType;
    protected Customer customer;

    public Vip() {}

    public Vip(Long id, Boolean available, Long coin, String vipType) {
        this(id, null, null, available, coin, vipType);
    }

    public Vip(Long id, Date createTime, Date updateTime, Boolean available, Long coin, String vipType) {
        setId(id);
        setCreateTime(createTime);
        setUpdateTime(updateTime);
        setAvailable(available);
        setCoin(coin);
        setVipType(vipType);
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Boolean isAvailable() {
        return "1".equals(available);
    }

    public void setAvailable(Boolean available) {
        this.available = available ? "1" : "0";
    }

    public Long getCoin() {
        return coin;
    }

    public void setCoin(Long coin) {
        this.coin = coin;
    }

    public String getVipType() {
        return vipType;
    }

    public void setVipType(String vipType) {
        this.vipType = vipType;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    @Override
    protected String[] exclude() {
        return new String[]{"createTime","updateTime"};
    }

    public Double discount() {
        return 0D;
    }
}
