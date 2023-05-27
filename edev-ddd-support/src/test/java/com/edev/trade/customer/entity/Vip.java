package com.edev.trade.customer.entity;

import com.edev.support.entity.Entity;
import com.edev.support.utils.DateUtils;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = true, exclude = {"createTime","updateTime"})
public class Vip extends Entity<Long> {
    protected Long id;
    protected Date createTime;
    protected Date updateTime;
    protected String available;
    protected Long coin;
    protected String vipType;
    protected Customer customer;

    public static Vip build() {
        return new Vip();
    }

    public Vip setValues(Long id, Boolean available, Long coin, String vipType) {
        return this.setValues(id, null, null, available, coin, vipType);
    }

    public Vip setValues(Long id, Date createTime, Date updateTime, Boolean available, Long coin, String vipType) {
        this.setId(id);
        this.setCreateTime(createTime);
        this.setUpdateTime(updateTime);
        this.setAvailable(available);
        this.setCoin(coin);
        this.setVipType(vipType);
        return this;
    }

    public void setCreateTime(Date createTime) {
        if(createTime == null) createTime = DateUtils.getNow();
        this.createTime = createTime;
    }

    public Boolean getAvailable() {
        return "1".equals(available);
    }

    public void setAvailable(Boolean available) {
        this.available = available ? "1" : "0";
    }

    @Override
    protected String[] exclude() {
        return new String[]{"createTime","updateTime"};
    }

}
