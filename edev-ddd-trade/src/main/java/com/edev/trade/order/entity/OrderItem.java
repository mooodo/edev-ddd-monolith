package com.edev.trade.order.entity;

import com.edev.support.entity.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class OrderItem extends Entity<Long> {
	private Long id;
	private Long orderId;
	private Long productId;
	private Long quantity;
	private Double price;
	private Double amount;
	private Product product;

	public static OrderItem build() {
		return new OrderItem();
	}

	public OrderItem setValues(Long id, Long orderId, Long productId, Long quantity, Double price, Double amount) {
		this.id = id;
		this.orderId = orderId;
		this.productId = productId;
		this.quantity = quantity;
		this.price = price;
		setAmount(amount);
		return this;
	}

	public void setAmount(Double amount) {
		this.amount = (amount==null) ? 0D : amount;
	}
}
