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

	public void setBirthdate(Date birthdate) {
		if(birthdate != null) this.birthdate = birthdate;
		else setBirthdateByIdentification();
	}

	public void setIdentification(String identification) {
		this.identification = identification;
		if(identification!=null) setBirthdateByIdentification();
	}

	protected void setBirthdateByIdentification() {
		if(identification==null) return;
		String birthdateStr = identification.substring(6,14);
		this.birthdate = DateUtils.getDate(birthdateStr, "yyyyMMdd");
	}
}
