package tokenTest.response;

import tokenTest.Util.Status;

public class PicResponse extends StatusResponse {
	private String picid;
	
	public String getPicid() {
		return picid;
	}
	public void setPicid(String picid) {
		this.picid = picid;
	}
	public PicResponse(Enum<Status> status) {
		super(status);
	}
	
	public PicResponse(Status status, String picid){
		this(status);
		setPicid(picid);
	}
}
