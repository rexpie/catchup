/**
 * 
 */
package tokenTest.service.impl;

import java.util.ArrayList;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import tokenTest.Util.Status;
import tokenTest.bo.MeetingBo;
import tokenTest.bo.ShopBo;
import tokenTest.bo.UserBo;
import tokenTest.exception.ShopNotFoundException;
import tokenTest.exception.UserNotFoundException;
import tokenTest.exception.WrongTokenException;
import tokenTest.model.Meeting;
import tokenTest.model.Shop;
import tokenTest.model.User;
import tokenTest.response.MeetingResponse;
import tokenTest.response.StatusResponse;
import tokenTest.service.MeetingServiceInterface;
import antlr.collections.List;

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
	public StatusResponse newMeeting(@RequestParam(required = true) Long id,
			@RequestParam(required = true) String token,
			@RequestParam(required = true) Long shopid,
			@RequestParam(required = true) String genderConstraint,
			@RequestParam(required = true) String description) {
		StatusResponse response = new StatusResponse(Status.OK);
		
		/*查找用户*/
		User user = null;
		try {
			user = userBo.validateUser(id, token);
		} catch (UserNotFoundException e) {
			response.setStatus(Status.ERR_USER_NOT_FOUND);
			return response;
		} catch (WrongTokenException e) {
			response.setStatus(Status.ERR_WRONG_TOKEN);
			return response;
		} catch (Exception e) {
			response.setStatus(Status.SERVICE_NOT_AVAILABLE);
			return response;
		}

		/*查找店*/
		Shop shop = null;
		try {
			shop = shopBo.findByShopId(shopid);
		} catch (ShopNotFoundException e) {
			response.setStatus(Status.ERR_SHOP_NOT_FOUND);
			return response;
		}

		/*新建并保存Meeting*/
		Meeting meeting = new Meeting(user, new Date(), shop, genderConstraint,
				description);
		try {
			meetingBo.save(meeting);
		} catch (Exception e) {
			response.setStatus(Status.SERVICE_NOT_AVAILABLE);
			return response;
		}

		return response;
	}

	public ArrayList<MeetingResponse> getMeetingList(String longtitude,
			String latitude, String pagenum, String sorttype) {
		// TODO Auto-generated method stub
		return null;
	}

	@RequestMapping(value = { "/getMeetingList**" }, method = RequestMethod.GET)
	public ArrayList<MeetingResponse> getMeetingList(Long id, String token,
			String pagenum, String sorttype) {
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
