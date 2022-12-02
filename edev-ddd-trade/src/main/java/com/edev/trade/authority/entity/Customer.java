/* 
 * Created by 2018年9月9日
 */
package com.edev.trade.authority.entity;

import com.edev.support.utils.DateUtils;
import com.edev.trade.customer.entity.Address;

import java.util.Date;
import java.util.List;

/**
 * The customer entity
 * @author fangang
 */
public class Customer extends User {
	private String gender;
	private Date birthdate;
	private String identification;
	private String phoneNumber;
	private List<Address> addresses;

	public Customer() {
	}

	public Customer(Long id, String name, String password, String userType, String gender, String identification, String phoneNumber) {
		super(id, name, password, userType);
		this.gender = gender;
		this.identification = identification;
		setBirthdateByIdentification();
		this.phoneNumber = phoneNumber;
	}

	/**
	 * @return the gender
	 */
	public String getGender() {
		return gender;
	}
	/**
	 * @param gender the gender to gender
	 */
	public void setGender(String gender) {
		this.gender = gender;
	}
	/**
	 * @return the birthdate
	 */
	public Date getBirthdate() {
		return birthdate;
	}

	/**
	 * @param birthdate the birthdate to set
	 */
	public void setBirthdate(Date birthdate) {
		this.birthdate = birthdate;
	}

	/**
	 * @return the identification
	 */
	public String getIdentification() {
		return identification;
	}
	/**
	 * @param identification the identification to set
	 */
	public void setIdentification(String identification) {
		this.identification = identification;
		setBirthdateByIdentification();
	}
	/**
	 * @return the phoneNumber
	 */
	public String getPhoneNumber() {
		return phoneNumber;
	}
	/**
	 * @param phoneNumber the phoneNumber to set
	 */
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	/**
	 * @return the addresses
	 */
	public List<Address> getAddresses() {
		return addresses;
	}

	/**
	 * @param addresses the addresses to set
	 */
	public void setAddresses(List<Address> addresses) {
		this.addresses = addresses;
	}

	protected void setBirthdateByIdentification() {
		String birthdateStr = identification.substring(6,14);
		this.birthdate = DateUtils.getDate(birthdateStr, "yyyyMMdd");
	}
}
