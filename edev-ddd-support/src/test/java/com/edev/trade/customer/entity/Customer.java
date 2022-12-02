/* 
 * Created by 2018年9月9日
 */
package com.edev.trade.customer.entity;

import com.edev.support.entity.Entity;

import java.util.Date;
import java.util.List;

/**
 * The customer entity
 * @author fangang
 */
public class Customer extends Entity<Long> {
	private static final long serialVersionUID = 5704896146658318508L;
	private Long id;
	private String name;
	private String sex;
	private Date birthdate;
	private String identification;
	private String phoneNumber;
	private List<Address> addresses;
	public Customer() {
		super();
	}
	
	public Customer(Long id, String name, String sex, Date birthdate,
			String identification, String phoneNumber) {
		this.id = id;
		this.name = name;
		this.sex = sex;
		this.birthdate = birthdate;
		this.identification = identification;
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
	 * @return the sex
	 */
	public String getSex() {
		return sex;
	}
	/**
	 * @param sex the sex to set
	 */
	public void setSex(String sex) {
		this.sex = sex;
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
}
