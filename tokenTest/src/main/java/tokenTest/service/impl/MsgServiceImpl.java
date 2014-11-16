package tokenTest.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import tokenTest.Util.Constants;
import tokenTest.Util.IMUtil;
import tokenTest.Util.Status;
import tokenTest.bo.PictureBo;
import tokenTest.bo.UserBo;
import tokenTest.exception.UserNotFoundException;
import tokenTest.exception.WrongTokenException;
import tokenTest.model.User;
import tokenTest.response.LikeUsersResponse;
import tokenTest.response.MsgTokenResponse;
import tokenTest.service.IMsgService;

@RestController
@RequestMapping("/msg")
@Service("msgService")
public class MsgServiceImpl implements IMsgService {
	@Autowired
	private UserBo userBo;

	@Autowired
	private PictureBo pictureBo;

	@Override
	@RequestMapping(value = { "/getMsgToken**" }, method = RequestMethod.GET)
	public MsgTokenResponse getMsgToken(
			@RequestParam(required = true) Long id,
			@RequestParam(required = true) String token) {
		User user = null;
		try {
			user = userBo.validateUserWithDetail(id, token,
					Constants.USER_LOAD_PHOTO);
		} catch (UserNotFoundException e) {
			return new MsgTokenResponse(Status.ERR_USER_NOT_FOUND);
		} catch (WrongTokenException e) {
			return new MsgTokenResponse(Status.ERR_WRONG_TOKEN);
		} catch (Exception e) {
			e.printStackTrace();
			return new MsgTokenResponse(Status.SERVICE_NOT_AVAILABLE);
		}
		
		if ( user == null ){
			return new MsgTokenResponse(Status.ERR_USER_NOT_FOUND);
		}
		
		String msgToken = IMUtil.getToken(user.getId(), user.getNickname(), getUserPicURL(user.getId()));
		return new MsgTokenResponse(Status.OK, msgToken);
	}

	private String getUserPicURL(Long id) {
		String base = "http://chatime.rexpie.net/chatime/user/getPicture?id=";
		String postfix = "&isThumb=1";
		return base + id + postfix;
	}

}
