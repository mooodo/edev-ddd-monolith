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
	private String flag;
	private Customer customer;
	private Address address;
	private Payment payment;
	private List<OrderItem> orderItems;

	public static Order build() {
		return new Order();
	}

	public Order setValues(Long id, Long customerId, Long addressId, Double amount, Date orderTime, String flag) {
		setId(id);
		setCustomerId(customerId);
		setAddressId(addressId);
		setAmount(amount);
		setOrderTime(orderTime);
		setFlag(flag);
		return this;
	}

	public Order setValues(Long id, Long customerId, Long addressId, Double amount) {
		return setValues(id, customerId, addressId, amount, null, "CREATE");
	}

	public void setAmount(Double amount) {
		this.amount = (amount==null) ? 0D : amount;
	}

	public void setOrderTime(Date orderTime) {
		this.orderTime = (orderTime==null) ? DateUtils.getNow() : orderTime;
	}

	public void setFlag(String flag) {
		this.flag = (flag==null) ? "CREATE" : flag;
	}

	public void addOrderItem(OrderItem orderItem) {
		if(this.orderItems==null) this.orderItems = new ArrayList<>();
		this.orderItems.add(orderItem);
	}
}
