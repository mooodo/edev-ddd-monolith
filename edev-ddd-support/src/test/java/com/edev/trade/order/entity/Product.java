/* 
 * Created by 2018年9月9日
 */
package com.edev.trade.order.entity;

import com.edev.support.entity.Entity;

/**
 * The product entity
 * @author fangang
 */
public class Product extends Entity<Long> {
	private static final long serialVersionUID = 7149822235159719740L;
	private Long id;
	private String name;
	private Double price;
	private String unit;
	private Long supplierId;
	private String classify;
	private String image;
	private Double originalPrice;
	private String tip;
	
	public Product() { super(); }
	public Product(Long id, String name, Double price, String unit, Long supplierId,
                   String classify) {
		super();
		this.id = id;
		this.name = name;
		this.price = price;
		this.unit = unit;
		this.supplierId = supplierId;
		this.classify = classify;
	}

	public Product(Long id, String name, Double price, String unit, Long supplierId, String classify, String image, Double originalPrice, String tip) {
		this.id = id;
		this.name = name;
		this.price = price;
		this.unit = unit;
		this.supplierId = supplierId;
		this.classify = classify;
		this.image = image;
		this.originalPrice = originalPrice;
		this.tip = tip;
	}
	/**
	 * @return the id
	 */
	@Override
	public Long getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	@Override
	public void setId(Long id) {
		this.id = (Long)id;
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
	 * @return the price
	 */
	public Double getPrice() {
		return price;
	}
	/**
	 * @param price the price to set
	 */
	public void setPrice(Double price) {
		this.price = price;
	}
	/**
	 * @return the unit
	 */
	public String getUnit() {
		return unit;
	}
	/**
	 * @param unit the unit to set
	 */
	public void setUnit(String unit) {
		this.unit = unit;
	}
	/**
	 * @return the supplierId
	 */
	public Long getSupplierId() {
		return supplierId;
	}
	/**
	 * @param supplierId the supplierId to set
	 */
	public void setSupplierId(Long supplierId) {
		this.supplierId = supplierId;
	}
	/**
	 * @return the classify
	 */
	public String getClassify() {
		return classify;
	}
	/**
	 * @param classify the classify to set
	 */
	public void setClassify(String classify) {
		this.classify = classify;
	}
	/**
	 * @return the image
	 */
	public String getImage() {
		return image;
	}
	/**
	 * @param image the image to set
	 */
	public void setImage(String image) {
		this.image = image;
	}
	/**
	 * @return the originalPrice
	 */
	public Double getOriginalPrice() {
		return originalPrice;
	}
	/**
	 * @param originalPrice the originalPrice to set
	 */
	public void setOriginalPrice(Double originalPrice) {
		this.originalPrice = originalPrice;
	}
	/**
	 * @return the tip
	 */
	public String getTip() {
		return tip;
	}
	/**
	 * @param tip the tip to set
	 */
	public void setTip(String tip) {
		this.tip = tip;
	}
	
}
