package tokenTest.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "ValidationCode")
public class ValidationCode {

	private String code;
	private Date createTime;
	private String phoneNum;
	private int status;
	private Date updateTime;

	@Column(name = "code", nullable = false)
	public String getCode() {
		return code;
	}

	@Column(name = "createTime", nullable = false)
	public Date getCreateTime() {
		return createTime;
	}

	@Column(name = "phoneNum", unique = true, nullable = false)
	public String getPhoneNum() {
		return phoneNum;
	}

	@Column(name = "status")
	public int getStatus() {
		return status;
	}

	@Column(name = "updateTime")
	public Date getUpdateTime() {
		return updateTime;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

}
