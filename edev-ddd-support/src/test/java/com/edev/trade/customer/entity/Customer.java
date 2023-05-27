package com.edev.trade.customer.entity;

import com.edev.support.entity.Entity;
import com.edev.support.utils.DateUtils;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class Customer extends Entity<Long> {
	private Long id;
	private String name;
	private String gender;
	private Date birthdate;
	private String identification;
	private String phoneNumber;
	private List<Address> addresses;

	public static Customer build() {
		return new Customer();
	}

	public Customer setValues(Long id, String name, String gender, Date birthdate,
					String identification, String phoneNumber) {
		this.setId(id);
		this.setName(name);
		this.setGender(gender);
		this.setIdentification(identification);
		this.setBirthdate(birthdate);
		this.setPhoneNumber(phoneNumber);
		return this;
	}

	public Customer setValues(Long id, String name, String gender,
			String identification, String phoneNumber) {
		return setValues(id, name, gender, null, identification, phoneNumber);
	}

	/**
	 * @param birthdate the birthdate to set
	 */
	public void setBirthdate(Date birthdate) {
		if(birthdate != null) this.birthdate = birthdate;
		else setBirthdateByIdentification();
	}

	protected void setBirthdateByIdentification() {
		if(identification==null) return;
		String birthdateStr = identification.substring(6,14);
		this.birthdate = DateUtils.getDate(birthdateStr, "yyyyMMdd");
	}
}
