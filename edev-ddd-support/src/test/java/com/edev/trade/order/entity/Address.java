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

	public static Address build() {
		return new Address();
	}

	public Address setValues(Long id, Long customerId, String country, String province,
															String city, String zone, String address, String phoneNumber) {
		this.setId(id);
		this.setCustomerId(customerId);
		this.setCountry(country);
		this.setProvince(province);
		this.setCity(city);
		this.setZone(zone);
		this.setAddress(address);
		this.setPhoneNumber(phoneNumber);
		return this;
	}
}
