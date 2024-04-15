package com.edev.trade.order.entity;

import com.edev.support.entity.Entity;
import com.edev.support.utils.DateUtils;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class Order extends Entity<Long> {
	private Long id;
	private Long customerId;
	private Long addressId;
	private Double amount;
	private Date orderTime;
	private Date modifyTime;
	private String status;
	private Customer customer;
	private Address address;
	private Payment payment;
	private List<OrderItem> orderItems;

	public void setOrderTime(Date orderTime) {
		this.orderTime = (orderTime==null) ? DateUtils.getNow() : orderTime;
	}

	public void setStatus(String status) {
		this.status = (status==null) ? "CREATE" : status;
	}

	public void addOrderItem(OrderItem orderItem) {
		if(this.orderItems==null) this.orderItems = new ArrayList<>();
		this.orderItems.add(orderItem);
	}
}
