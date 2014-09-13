package tokenTest.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "tag")
public class Tag implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4723328084800076160L;
	private long tag;
	
	/*±êÇ©ÄÚÈÝ*/
	private String name;

	public Tag() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Tag(String name) {
		super();
		this.name = name;
	}

	@GenericGenerator(name = "generator", strategy = "increment")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "id", unique = true, nullable = false)
	public long getTag() {
		return tag;
	}

	public void setTag(long tag) {
		this.tag = tag;
	}

	@Column(name = "name", unique = true, nullable = false)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
