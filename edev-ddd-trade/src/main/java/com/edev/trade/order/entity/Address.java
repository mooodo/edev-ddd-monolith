package com.edev.trade.order.entity;

import com.edev.support.entity.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class Address extends Entity<Long> {
	private Long id;
	private Long customerId;
	private String country;
	private String province;
	private String city;
	private String zone;
	private String address;
	private String phoneNumber;
}
