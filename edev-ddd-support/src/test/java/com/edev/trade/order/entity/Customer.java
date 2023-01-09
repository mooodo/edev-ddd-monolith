/* 
 * Created by 2018年9月9日
 */
package com.edev.trade.order.entity;

import com.edev.support.entity.Entity;
import com.edev.support.utils.DateUtils;

import java.util.Date;

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
	public Customer() {
		super();
	}
	
	public Customer(Long id, String name, String gender,
                    String identification, String phoneNumber) {
		this.id = id;
		this.name = name;
		this.gender = gender;
		this.identification = identification;
		setBirthdateByIdentification();
		this.phoneNumber = phoneNumber;
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

	protected void setBirthdateByIdentification() {
		String birthdateStr = identification.substring(6,14);
		this.birthdate = DateUtils.getDate(birthdateStr, "yyyyMMdd");
	}

	@Override
	protected String[] exclude() {
		return new String[]{"birthdate"};
	}
}
