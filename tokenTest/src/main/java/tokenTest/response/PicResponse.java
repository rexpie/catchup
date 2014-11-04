package tokenTest.response;

import tokenTest.Util.Status;

public class PicResponse {
	private Enum<Status> status;
	private String picid;
	
	public String getPicid() {
		return picid;
	}
	public void setPicid(String picid) {
		this.picid = picid;
	}
	public PicResponse(Enum<Status> status) {
		super();
		this.status = status;
	}
	
	public PicResponse(Status status, String picid){
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
