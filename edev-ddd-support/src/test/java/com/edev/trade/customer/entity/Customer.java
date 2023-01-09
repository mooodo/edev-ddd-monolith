/* 
 * Created by 2018年9月9日
 */
package com.edev.trade.customer.entity;

import com.edev.support.entity.Entity;
import com.edev.support.utils.DateUtils;

import java.util.Date;
import java.util.List;

/**
 * The customer entity
 * @author fangang
 */
public class Customer extends Entity<Long> {
	private Long id;
	private String name;
	private String gender;
	private Date birthdate;
	private String identification;
	private String phoneNumber;
	private List<Address> addresses;
	public Customer() {
		super();
	}

	public Customer(Long id, String name, String gender, Date birthdate,
					String identification, String phoneNumber) {
		setId(id);
		setName(name);
		setGender(gender);
		setIdentification(identification);
		setBirthdate(birthdate);
		setPhoneNumber(phoneNumber);
	}

	public Customer(Long id, String name, String gender,
			String identification, String phoneNumber) {
		this(id, name, gender, null, identification, phoneNumber);
	}

	/**
	 * @param id the id to set
	 */
	@Override
	public void setId(Long id) {
		this.id = id;
	}
	
	/**
	 * @return the id
	 */
	@Override
	public Long getId() {
		return id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
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
		if(birthdate != null) this.birthdate = birthdate;
		else setBirthdateByIdentification();
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
		if(identification==null) return;
		String birthdateStr = identification.substring(6,14);
		this.birthdate = DateUtils.getDate(birthdateStr, "yyyyMMdd");
	}
}
