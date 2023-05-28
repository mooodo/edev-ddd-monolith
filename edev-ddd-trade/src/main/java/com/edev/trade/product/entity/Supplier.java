package com.edev.trade.product.entity;

import com.edev.support.entity.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class Supplier extends Entity<Long> {
	private Long id;
	private String name;
	private String supplierType;

	public static Supplier build() {
		return new Supplier();
	}

	public Supplier setValues(Long id, String name, String supplierType) {
		this.id = id;
		this.name = name;
		this.supplierType = supplierType;
		return this;
	}
}
