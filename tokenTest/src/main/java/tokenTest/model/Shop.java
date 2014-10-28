package tokenTest.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "shop")
public class Shop implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -889560859827810858L;

	private Long id;
	private String dianping_id;
	private String name;
	private String city;
	private String area;
	private String country;
	private String description;
	private Double latitude;
	private Double longitude;
	private String type;
	private String classtype;
	private Double price;
	private String url;
	private String photoUrl;
	
	@Column(length=256)
	public String getPhotoUrl() {
		return photoUrl;
	}

	public void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
	}

	@Column(name = "url",length=256)
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Shop(String dianping_id, String name, String city, String area,
			String country, String description, double latitude,
			double longitude, String type, String classtype, double price) {
		super();
		this.dianping_id = dianping_id;
		this.name = name;
		this.city = city;
		this.area = area;
		this.country = country;
		this.description = description;
		this.latitude = latitude;
		this.longitude = longitude;
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
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
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

	@Column(name = "city", length = 256)
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	@Column(name = "area", length = 256)
	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	@Column(name = "country", length = 256)
	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	@Column(name = "description", length = 2048)
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "latitude")
	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	@Column(name = "longitude")
	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	@Column(name = "type", length = 128)
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Column(name = "classtype", length = 128)
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

	@Override
	public int hashCode() {
		final int prime = 47;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final Shop other = (Shop) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!this.id.equals(other.id))
			return false;
		return true;
	}

}
