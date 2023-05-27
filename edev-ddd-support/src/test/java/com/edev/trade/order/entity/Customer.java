package com.edev.trade.order.entity;

import com.edev.support.entity.Entity;
import com.edev.support.utils.DateUtils;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = true, exclude = {"birthdate"})
public class Customer extends Entity<Long> {
	private Long id;
	private String name;
	private String gender;
	private Date birthdate;
	private String identification;
	private String phoneNumber;


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

	public void setIdentification(String identification) {
		this.identification = identification;
		setBirthdateByIdentification();
	}

	protected void setBirthdateByIdentification() {
		String birthdateStr = identification.substring(6,14);
		this.birthdate = DateUtils.getDate(birthdateStr, "yyyyMMdd");
	}

	@Override
	protected String[] exclude() {
		return new String[]{"birthdate"};
	}
}
