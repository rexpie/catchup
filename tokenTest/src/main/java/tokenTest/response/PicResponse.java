package tokenTest.response;

import tokenTest.Util.Status;

public class PicResponse {
	private Enum<Status> status;
	private Long picid;
	
	public Long getPicid() {
		return picid;
	}
	public void setPicid(Long picid) {
		this.picid = picid;
	}
	public PicResponse(Enum<Status> status) {
		super();
		this.status = status;
	}
	
	public PicResponse(Status status, Long picid){
		this(status);
		setPicid(picid);
	}
	public Enum<Status> getStatus() {
		return status;
	}
	public void setStatus(Enum<Status> status) {
		this.status = status;
	}
}
