package org.test1.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "shop")
public class Shop implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -889560859827810858L;
	private long id;
	private String dianping_id;
	private String name;
	private String city;
	private String area;
	private String country;
	private String description;
	private String latitude;
	private String longtitude;
	private String type;
	private String classtype;
	private double price;
	
	
	public Shop(String dianping_id, String name, String city, String area,
			String country, String description, String latitude,
			String longtitude, String type, String classtype, double price) {
		super();
		this.dianping_id = dianping_id;
		this.name = name;
		this.city = city;
		this.area = area;
		this.country = country;
		this.description = description;
		this.latitude = latitude;
		this.longtitude = longtitude;
		this.type = type;
		this.classtype = classtype;
		this.price = price;
	}



	public Shop() {
		super();
		// TODO Auto-generated constructor stub
	}

	

	@GenericGenerator(name = "generator", strategy = "increment")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "id", unique = true, nullable = false)
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Column(name = "dianping_id")
	public String getDianping_id() {
		return dianping_id;
	}

	public void setDianping_id(String dianping_id) {
		this.dianping_id = dianping_id;
	}

	@Column(name = "name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "city", length=256)
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	@Column(name = "area", length=256)
	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	@Column(name = "country", length=256)
	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	@Column(name = "description", length=2048)
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "latitude", length=128)
	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	@Column(name = "longtitude", length=128)
	public String getLongtitude() {
		return longtitude;
	}

	public void setLongtitude(String longtitude) {
		this.longtitude = longtitude;
	}

	@Column(name = "type", length=128)
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Column(name = "classtype", length=128)
	public String getClasstype() {
		return classtype;
	}

	public void setClasstype(String classtype) {
		this.classtype = classtype;
	}

	@Column(name = "price")
	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

}
