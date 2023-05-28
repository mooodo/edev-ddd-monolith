package com.edev.trade.inventory.entity;

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
}
