/*
 * created by 2019年7月23日 下午3:46:50
 */
package com.edev.trade.order.entity;

import com.edev.support.entity.Entity;

/**
 * @author fangang
 */
public class OrderItem extends Entity<Long> {
	private Long id;
	private Long orderId;
	private Long productId;
	private Long quantity;
	private Double price;
	private Double amount;
	private Product product;

	public OrderItem() {}

	public OrderItem(Long id, Long orderId, Long productId, Long quantity, Double price, Double amount) {
		this.id = id;
		this.orderId = orderId;
		this.productId = productId;
		this.quantity = quantity;
		this.price = price;
		setAmount(amount);
	}

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public Long getQuantity() {
		return quantity;
	}

	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = (amount==null) ? 0D : amount;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}
}
