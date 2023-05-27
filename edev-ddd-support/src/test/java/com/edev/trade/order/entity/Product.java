package com.edev.trade.order.entity;

import com.edev.support.entity.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class Product extends Entity<Long> {
	private Long id;
	private String name;
	private Double price;
	private String unit;
	private Long supplierId;
	private String classify;
	private String image;
	private Double originalPrice;
	private String tip;

	public static Product build() {
		return new Product();
	}

	public Product setValues(Long id, String name, Double price, String unit, Long supplierId,
														   String classify) {
		return setValues(id, name, price, unit, supplierId, classify,
				null, null, null);
	}

	public Product setValues(Long id, String name, Double price, String unit, Long supplierId, String classify, String image, Double originalPrice, String tip) {
		this.id = id;
		this.name = name;
		this.price = price;
		this.unit = unit;
		this.supplierId = supplierId;
		this.classify = classify;
		this.image = image;
		this.originalPrice = originalPrice;
		this.tip = tip;
		return this;
	}
}
