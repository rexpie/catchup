package tokenTest.response;

import java.util.Date;
import java.util.Set;

import com.google.common.collect.Sets;

import tokenTest.Util.Status;
import tokenTest.Util.Utils;
import tokenTest.model.Tag;
import tokenTest.model.User;

public class UserDetailResponse extends StatusResponse {

	UserDetailInnerResponse user = null;

	public UserDetailInnerResponse getUser() {
		return user;
	}

	public void setUser(UserDetailInnerResponse user) {
		this.user = user;
	}

	public UserDetailResponse(User _user, boolean isSelf) {
		super(Status.OK);
		user = new UserDetailInnerResponse(_user, isSelf);
	}

	public UserDetailResponse(Enum<Status> status) {
		super(status);
		// TODO Auto-generated constructor stub
	}

	/*
	 * public Picture getPic() { return pic; }
	 * 
	 * public void setPic(Picture pic) { this.pic = pic; }
	 */

	public static UserDetailResponse getError(Status status) {
		if (status != Status.OK)
			return new UserDetailResponse(status);
		else {
			return new UserDetailResponse(Status.ERR_GENERIC);
		}
	}

	public void setAlreadyLiked(boolean flag) {
		if (user != null) {
			user.setAlreadyLiked(flag);
		}
	}
	
	public void setBlacklisted(boolean flag){
		if (user !=null){
			user.setBlacklisted(flag);
		}
	}

}
