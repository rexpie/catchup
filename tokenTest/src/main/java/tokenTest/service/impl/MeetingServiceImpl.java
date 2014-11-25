/**
 * 
 */
package tokenTest.service.impl;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import tokenTest.Util.Constants;
import tokenTest.Util.DPApiTool;
import tokenTest.Util.IMUtil;
import tokenTest.Util.Status;
import tokenTest.bo.MeetingBo;
import tokenTest.bo.ShopBo;
import tokenTest.bo.UserBo;
import tokenTest.exception.ApplyNotFoundException;
import tokenTest.exception.MeetingNotFoundException;
import tokenTest.exception.ShopNotFoundException;
import tokenTest.exception.TooManyAppliesException;
import tokenTest.exception.UserNotFoundException;
import tokenTest.exception.WrongTokenException;
import tokenTest.model.Meeting;
import tokenTest.model.MeetingApply;
import tokenTest.model.Shop;
import tokenTest.model.User;
import tokenTest.response.ApplyInfo;
import tokenTest.response.MeetingDetail;
import tokenTest.response.MeetingDetailResponse;
import tokenTest.response.MeetingListResponse;
import tokenTest.response.NewApplyResponse;
import tokenTest.response.StatusResponse;
import tokenTest.response.UserInfo;
import tokenTest.response.WithdrawApplyResponse;
import tokenTest.service.IMeetingService;

/**
 * @author pengtao
 * 
 */
@RestController
@RequestMapping("/meeting")
@Service("meetingService")
public class MeetingServiceImpl implements IMeetingService {
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
			@RequestParam(required = true) String description,
			@RequestParam(required = false) String job,
			@RequestParam(required = false) String building,
			@RequestParam(required = false) String company
			) {
		StatusResponse response = new StatusResponse(Status.OK);

		/* 鏌ユ壘鐢ㄦ埛 */
		User user = null;
		try {
			user = userBo.validateUserWithDetail(id, token, Constants.USER_LOAD_PHOTO);
		} catch (UserNotFoundException e) {
			response.setStatus(Status.ERR_USER_NOT_FOUND);
			return response;
		} catch (WrongTokenException e) {
			response.setStatus(Status.ERR_WRONG_TOKEN);
			return response;
		} catch (Exception e) {
			e.printStackTrace();
			response.setStatus(Status.SERVICE_NOT_AVAILABLE);
			return response;
		}
		boolean updateFlag = false;
		if (!StringUtils.isEmpty(job)){
			user.setJob(job);
			updateFlag = true;
		}
		if (!StringUtils.isEmpty(company)){
			user.setCompany(company);
			updateFlag = true;
		}
		if (!StringUtils.isEmpty(building)){
			user.setBuilding(building);
			updateFlag = true;
		}

		if (StringUtils.isEmpty(user.getJob())) {
			response.setStatus(Status.ERR_NEW_MEETING_MUST_HAVE_JOB);
			return response;
		}

		if (StringUtils.isEmpty(user.getBuilding())) {
			response.setStatus(Status.ERR_NEW_MEETING_MUST_HAVE_BUILDING);
			return response;
		}
		
		if (user.getPic() == null) {
			response.setStatus(Status.ERR_NEW_MEETING_MUST_HAVE_PIC);
			return response;
		}

		if (StringUtils.isEmpty(user.getSex())) {
			response.setStatus(Status.ERR_NEW_MEETING_MUST_HAVE_GENDER);
			return response;
		}

		/* 鏌ユ壘搴� */
		Shop shop = null;
		try {
			shop = shopBo.findByDianpingId(shopid);
		} catch (ShopNotFoundException e) {
			shop = DPApiTool.getBusiness(shopid);
			if (shop == null) {
				response.setStatus(Status.ERR_SHOP_NOT_FOUND);
				return response;
			} else {
				shopBo.save(shop);
			}
		}

		if (!StringUtils.equals("F", genderConstraint)
				&& !StringUtils.equals("M", genderConstraint)
				&& !StringUtils.equals("N", genderConstraint)) {
			response.setStatus(Status.ERR_INVALID_GENDER);
			return response;
		}

		/* 鏂板缓骞朵繚瀛楳eeting */
		Meeting meeting = new Meeting(user, new Date(), shop, genderConstraint,
				description);
		try {
			meetingBo.save(meeting);
		} catch (Exception e) {
			response.setStatus(Status.SERVICE_NOT_AVAILABLE);
			return response;
		}

		//TODO async
		if (updateFlag){
			userBo.update(user);
		}
		return response;
	}

	@RequestMapping(value = { "/getMeetingList**" }, method = RequestMethod.GET)
	public MeetingListResponse getMeetingList(
			@RequestParam(required = true) Double longitude,
			@RequestParam(required = true) Double latitude,
			@RequestParam(required = false, defaultValue = "0") Integer pagenum,
			@RequestParam(required = false, defaultValue = "1") Integer sorttype,
			@RequestParam(required = false, defaultValue = "1000") Integer range,
			@RequestParam(required = false, defaultValue = "") String gender,
			@RequestParam(required = false, defaultValue = "") String job,
			@RequestParam(required = false, defaultValue = "") String shopName,
			@RequestParam(required = false, defaultValue = "") Long id,
			@RequestParam(required = false, defaultValue = "") String token) {
		MeetingListResponse meetingListResponse = new MeetingListResponse(Status.OK);

		User user = null;
		if (id != null && token != null) {
			try {
				user = userBo.validateUser(id, token);
			} catch (UserNotFoundException e) {
				meetingListResponse.setStatus(Status.ERR_USER_NOT_FOUND);
				return meetingListResponse;
			} catch (WrongTokenException e) {
				meetingListResponse.setStatus(Status.ERR_WRONG_TOKEN);
				return meetingListResponse;
			} catch (Exception e) {
				meetingListResponse.setStatus(Status.SERVICE_NOT_AVAILABLE);
				return meetingListResponse;
			}
		}
		/* list鍏冪礌涓篛bject[2]涓涓�釜瀵硅薄鏄痬eeting锛岀浜屼釜鏄窛绂伙紝double绫诲瀷 */
		@SuppressWarnings("rawtypes")
		List list = null;
		try {
			if (user == null) {
				list = meetingBo.getMeetingList(longitude, latitude, pagenum,
						sorttype, range, gender, job, shopName);
			} else {
				list = meetingBo.getMeetingListForUser(user, longitude,
						latitude, pagenum, sorttype, range, gender, job,
						shopName);
			}
		} catch (Exception e) {
			e.printStackTrace();
			meetingListResponse.setStatus(Status.SERVICE_NOT_AVAILABLE);
			return meetingListResponse;
		}

		meetingListResponse.setStatus(Status.OK);
		if (list != null) {
			@SuppressWarnings("rawtypes")
			Iterator iterator = list.iterator();
			Object[] objects = null;
			int index = 0;
			while (iterator.hasNext()) {
				objects = (Object[]) iterator.next();
				Meeting meeting = (Meeting) objects[0];
				MeetingDetail meetingDetail = new MeetingDetail(
						meeting, (Double) objects[1]);
				meetingDetail.setIndex(index++);
				meetingDetail.setPageNum(pagenum);
				meetingDetail.setJoined(meetingBo.testIfApplied(meeting, user));
				meetingListResponse.getMeetingList().add(meetingDetail);
			}
		}
		return meetingListResponse;
	}

	@RequestMapping(value = { "/getMyMeetingList**" }, method = RequestMethod.GET)
	public MeetingListResponse getMyMeetingList(
			@RequestParam(required = true) Long id,
			@RequestParam(required = true) String token,
			@RequestParam(required = false, defaultValue = "0") Integer pagenum) {
		MeetingListResponse meetingListResponse = new MeetingListResponse(Status.OK);

		/* 鏌ユ壘鐢ㄦ埛 */
		User user = null;
		try {
			user = userBo.validateUser(id, token);
		} catch (UserNotFoundException e) {
			meetingListResponse.setStatus(Status.ERR_USER_NOT_FOUND);
			return meetingListResponse;
		} catch (WrongTokenException e) {
			meetingListResponse.setStatus(Status.ERR_WRONG_TOKEN);
			return meetingListResponse;
		} catch (Exception e) {
			meetingListResponse.setStatus(Status.SERVICE_NOT_AVAILABLE);
			return meetingListResponse;
		}

		/* list鍏冪礌涓篛bject,鏄痬eeting */
		@SuppressWarnings("rawtypes")
		List list = null;
		try {
			list = meetingBo.getMeetingListByUser(user, pagenum);
		} catch (Exception e) {
			meetingListResponse.setStatus(Status.SERVICE_NOT_AVAILABLE);
		}
		meetingListResponse.setStatus(Status.OK);
		@SuppressWarnings("rawtypes")
		Iterator iterator = list.iterator();
		int index = 0;
		Object[] objects = null;
		while (iterator.hasNext()) {
			MeetingDetail meetingDetail = new MeetingDetail(
					(Meeting) iterator.next());
			meetingDetail.setIndex(index);
			meetingDetail.setPageNum(pagenum);
			meetingListResponse.getMeetingList().add(meetingDetail);
			index++;
		}
		return meetingListResponse;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = { "/getMyPartMeetingList**" }, method = RequestMethod.GET)
	public MeetingListResponse getMyPartMeetingList(
			@RequestParam(required = true) Long id,
			@RequestParam(required = true) String token,
			@RequestParam(required = false, defaultValue = "0") Integer pagenum) {
		MeetingListResponse meetingListResponse = new MeetingListResponse(Status.OK);

		/* 鏌ユ壘鐢ㄦ埛 */
		User user = null;
		try {
			user = userBo.validateUser(id, token);
		} catch (UserNotFoundException e) {
			meetingListResponse.setStatus(Status.ERR_USER_NOT_FOUND);
			return meetingListResponse;
		} catch (WrongTokenException e) {
			meetingListResponse.setStatus(Status.ERR_WRONG_TOKEN);
			return meetingListResponse;
		} catch (Exception e) {
			meetingListResponse.setStatus(Status.SERVICE_NOT_AVAILABLE);
			return meetingListResponse;
		}

		/* list鍏冪礌涓篛bject,鏄痬eeting */
		List<Meeting> list = null;
		try {
			list = meetingBo.getMeetingListByParticipate(user, pagenum);
		} catch (Exception e) {
			meetingListResponse.setStatus(Status.SERVICE_NOT_AVAILABLE);
		}
		meetingListResponse.setStatus(Status.OK);
		Iterator iterator = list.iterator();
		Object[] objects = null;
		int index = 0;
		while (iterator.hasNext()) {
			MeetingDetail meetingDetail = new MeetingDetail((Meeting)iterator.next());
			meetingDetail.setPageNum(pagenum);
			meetingDetail.setIndex(index);
			meetingListResponse.getMeetingList().add(meetingDetail);
			index++;
		}
		return meetingListResponse;
	}

	@RequestMapping(value = { "/getMeetingDetail**" }, method = RequestMethod.GET)
	public MeetingDetailResponse getMeetingDetail(
			@RequestParam(required = true) Long id,
			@RequestParam(required = true) String token,
			@RequestParam(required = true) Long meetingid) {
		MeetingDetailResponse response = new MeetingDetailResponse();
		/* 鏌ユ壘鐢ㄦ埛 */
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
		Meeting meeting = null;
		try {
			meeting = meetingBo.getMeetingById(meetingid);
		} catch (MeetingNotFoundException e) {
			response.setStatus(Status.ERR_MEETING_NOT_FOUND);
			return response;
		}

		response.setMeetingDetail(new MeetingDetail(meeting));
		Iterator<User> userIterator = meeting.getParticipator().iterator();
		while (userIterator.hasNext()) {
			response.getParticipates().add(
					new UserInfo(userIterator.next()));
		}
		/* 鏄嫢鏈夎�锛岃兘鐪嬪埌鐢宠淇℃伅鍜屽弬涓庤�淇℃伅 */
		if (meeting.getOwner().equals(user)) {
			/* 楗害鍩烘湰淇℃伅 */
			/* 鍙備笌鑰呬俊鎭� */

			/* 鐢宠淇℃伅 */
			try {
				Iterator<MeetingApply> iterator = meetingBo.getApplyByMeeting(
						meeting).iterator();
				while (iterator.hasNext()) {
					response.getApplicants()
							.add(new ApplyInfo(iterator.next()));
				}
			} catch (Exception e) {
				/* 娌℃湁鍙備笌鑰�涓嶅仛澶勭悊 */
				// response.setStatus(Status.SERVICE_NOT_AVAILABLE);
				// return response;
			}
		}  
		response.setStatus(Status.OK);
		return response;
	}

	@RequestMapping(value = { "/applyForMeeting**" }, method = RequestMethod.GET)
	public NewApplyResponse applyForMeeting(
			@RequestParam(required = true) Long id,
			@RequestParam(required = true) String token,
			@RequestParam(required = true) Long meetingid,
			@RequestParam(required = false, defaultValue = "") String applyContent) {
		NewApplyResponse response = new NewApplyResponse(null);
		/* 鏌ユ壘鐢ㄦ埛 */
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
		Meeting meeting = null;
		try {
			meeting = meetingBo.getMeetingById(meetingid);
		} catch (MeetingNotFoundException e) {
			response.setStatus(Status.ERR_MEETING_NOT_FOUND);
			return response;
		}

		/* 鑷繁鍙戣捣鐨勯キ绾︽垨鑰呭凡缁忓弬涓庣殑楗害,涓嶈兘鐢宠 */
		if (meeting.getOwner().equals(user)
				|| meeting.getParticipator().contains(user)) {
			response.setStatus(Status.ERR_CAN_NOT_APPLY_FOR_THE_MEETING);
			return response;
		}

		User owner = userBo.findByUserIdWithDetail(meeting.getOwner().getId(), Constants.USER_LOAD_BLACKLIST);
		if (owner.getBlacklist().contains(user)) {
			response.setStatus(Status.ERR_BLACKLISTED);
			return response;
		}

		if (meeting.getGenderConstraint() != null
				&& !StringUtils.equals("N", meeting.getGenderConstraint())
				&& !StringUtils.equals(user.getSex(),
						meeting.getGenderConstraint())) {
			response.setStatus(Status.ERR_INVALID_GENDER);
			return response;
		}

		try {
			meetingBo.applyForMeeting(user, meeting, applyContent);
		} catch (TooManyAppliesException e) {
			response.setStatus(Status.ERR_TOO_MANY_APPLY);
			return response;
		}
		IMUtil.startTextConversation(user.getId(), meeting.getOwner().getId(), Constants.MSG_APPLY_MEETING);
		response.setStatus(Status.OK);
		return response;
	}

	@RequestMapping(value = { "/processMeetingApply**" }, method = RequestMethod.GET)
	public StatusResponse processMeetingApply(
			@RequestParam(required = true) Long id,
			@RequestParam(required = true) String token,
			@RequestParam(required = true) Long applyid,
			@RequestParam(required = true) Boolean approved) {
		StatusResponse response = new StatusResponse(null);
		/* 鏌ユ壘鐢ㄦ埛 */
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

		/* 鏌ユ壘鐢宠 */
		MeetingApply meetingApply = null;
		try {
			meetingApply = meetingBo.getApplyById(applyid);
		} catch (ApplyNotFoundException e) {
			response.setStatus(Status.ERR_NO_SUCH_APPLY);
			return response;
		}

		/* 鏌ユ壘楗害 */
		Meeting meeting = meetingApply.getToMeeting();
		if (meeting == null) {
			response.setStatus(Status.ERR_MEETING_NOT_FOUND);
			return response;
		}

		/* 涓嶆槸楗害鎷ユ湁鑰咃紝涓嶈兘澶勭悊楗害鐢宠 */
		if (!meeting.getOwner().equals(user)) {
			response.setStatus(Status.ERR_NOT_MEETING_OWNER);
			return response;
		}
		try {
			if (approved) {
				meetingBo.processMeetingApply(meetingApply, true);
			} else {
				meetingBo.processMeetingApply(meetingApply, false);
			}
		} catch (Exception e) {
			response.setStatus(Status.SERVICE_NOT_AVAILABLE);
			return response;
		}

		if (approved){
			IMUtil.startTextConversation(user.getId(), meeting.getOwner().getId(), Constants.MSG_APPLY_APPROVED);
		}
		response.setStatus(Status.OK);
		return response;
	}

	public String commentOnMeeting(Long id, String token, Long meetingid,
			String comment) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@RequestMapping(value = { "/withdrawMeetingApply**" }, method = RequestMethod.GET)
	public WithdrawApplyResponse withdrawMeetingApply(
			@RequestParam(required = true) Long id,
			@RequestParam(required = true) String token,
			@RequestParam(required = true) Long applyid,
			@RequestParam(required = true) String withdrawReason) {
		// TODO Auto-generated method stub
		WithdrawApplyResponse response = new WithdrawApplyResponse(null);
		/* 鏌ユ壘鐢ㄦ埛 */
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

		/* 鏌ユ壘鐢宠 */
		MeetingApply meetingApply = null;
		try {
			meetingApply = meetingBo.getApplyById(applyid);
		} catch (ApplyNotFoundException e) {
			response.setStatus(Status.ERR_NO_SUCH_APPLY);
			return response;
		}

		// check if meeting apply exists and owner matches
		if (!user.equals(meetingApply.getFromUser())) {
			response.setStatus(Status.ERR_NOT_APPLIER);
			return response;
		}

		try {
			meetingBo.withdrawMeetingApply(meetingApply);
		} catch (Exception e) {
			e.printStackTrace();
			response.setStatus(Status.SERVICE_NOT_AVAILABLE);
			return response;
		}

		response.setStatus(Status.OK);
		return response;

	}

	@Override
	@RequestMapping(value = { "/stopMeeting**" }, method = RequestMethod.GET)
	public StatusResponse stopMeeting(@RequestParam(required = true) Long id,
			@RequestParam(required = true) String token,
			@RequestParam(required = true) Long meetingid,
			@RequestParam(required = false) String stopReason) {
		StatusResponse response = new StatusResponse(null);
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

		Meeting meeting = null;
		try {
			meeting = meetingBo.getMeetingById(meetingid);
		} catch (MeetingNotFoundException e) {
			response.setStatus(Status.ERR_MEETING_NOT_FOUND);
			return response;
		}

		if (!user.equals(meeting.getOwner())) {
			response.setStatus(Status.ERR_NOT_MEETING_OWNER);
			return response;
		}

		try {
			meetingBo.stopMeeting(meeting, stopReason);
		} catch (Exception e) {
			e.printStackTrace();
			response.setStatus(Status.SERVICE_NOT_AVAILABLE);
			return response;
		}

		//TODO async
		for (User target : meeting.getParticipator()){
			IMUtil.startSystemDelegateConversation(user.getNickname(), target.getId(), Constants.MSG_MEETING_STOP);
		}
		response.setStatus(Status.OK);
		return response;
	}

	@Override
	public StatusResponse newMeeting(Long id, String token, Long shopid,
			String genderConstraint, String description) {
		return newMeeting(id,token,shopid,genderConstraint,description,null,null,null);
	}

}
