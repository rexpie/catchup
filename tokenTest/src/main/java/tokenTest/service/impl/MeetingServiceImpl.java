/**
 * 
 */
package tokenTest.service.impl;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import antlr.collections.List;
import tokenTest.Util.Status;
import tokenTest.bo.MeetingBo;
import tokenTest.bo.ShopBo;
import tokenTest.bo.UserBo;
import tokenTest.model.Meeting;
import tokenTest.model.Shop;
import tokenTest.model.User;
import tokenTest.response.LoginResponse;
import tokenTest.response.MeetingResponse;
import tokenTest.response.UserDetailResponse;
import tokenTest.service.MeetingServiceInterface;

/**
 * @author pengtao
 * 
 */
@RestController
@RequestMapping("/meeting")
public class MeetingServiceImpl implements MeetingServiceInterface {
	@Autowired
	private UserBo userBo;

	@Autowired
	private ShopBo shopBo;

	@Autowired
	private MeetingBo meetingBo;

	@RequestMapping(value = { "/newMeeting**" }, method = RequestMethod.GET)
	public MeetingResponse newMeeting(@RequestParam(required = true) Long id,
			@RequestParam(required = true) String token,
			@RequestParam(required = true) Long shopid,
			@RequestParam(required = true) String genderConstraint,
			@RequestParam(required = true) String description) {
		User user = null;

		try {
			user = userBo.validateUser(id, token);
		} catch (Exception e) {
			// 不知道啥错
			return new MeetingResponse(Status.SERVICE_NOT_AVAILABLE);
		}

		/* 用户不存在或者令牌不正确 */
		if (user == null) {
			return new MeetingResponse(Status.ERR_USER_NOT_FOUND);
		}

		Shop shop = null;
		try {
			shop = shopBo.findByShopId(shopid);
		} catch (Exception e) {
			// TODO: handle exception
			return new MeetingResponse(Status.SERVICE_NOT_AVAILABLE);
		}
		if (shop == null) {
			// 没有这家店，id为-1的店需要预埋
			return new MeetingResponse(Status.ERR_GENERIC);
		}

		Meeting meeting = new Meeting(user, new Date(), shop, genderConstraint,
				description);

		meetingBo.save(meeting);

		return new MeetingResponse(Status.OK);
	}

	public List getMeetingList(String longtitude, String latitude,
			String pagenum, String sorttype) {
		// TODO Auto-generated method stub
		return null;
	}

	public List getMeetingList(Long id, String token, String pagenum) {
		// TODO Auto-generated method stub
		return null;
	}

	public MeetingResponse getMeetingDetail(Long meetingid) {
		// TODO Auto-generated method stub
		return null;
	}

	public String applyForMeeting(Long id, String token, Long meetingid) {
		// TODO Auto-generated method stub
		return null;
	}

	public String approveMeetingApply(Long id, String token, Long meetingid,
			Long applyid) {
		// TODO Auto-generated method stub
		return null;
	}

	public String disapproveMeetingApply(Long id, String token, Long meetingid,
			Long applyid) {
		// TODO Auto-generated method stub
		return null;
	}

	public String commentOnMeeting(Long id, String token, Long meetingid,
			String comment) {
		// TODO Auto-generated method stub
		return null;
	}

}
