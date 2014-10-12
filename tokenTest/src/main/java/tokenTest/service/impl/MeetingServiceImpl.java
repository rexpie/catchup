/**
 * 
 */
package tokenTest.service.impl;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import tokenTest.Util.Status;
import tokenTest.bo.MeetingBo;
import tokenTest.bo.ShopBo;
import tokenTest.bo.UserBo;
import tokenTest.exception.ApplyNotFoundException;
import tokenTest.exception.MeetingNotFoundException;
import tokenTest.exception.ShopNotFoundException;
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
	public StatusResponse newMeeting(@RequestParam(required = true) Long id,
			@RequestParam(required = true) String token,
			@RequestParam(required = true) Long shopid,
			@RequestParam(required = true) String genderConstraint,
			@RequestParam(required = true) String description) {
		StatusResponse response = new StatusResponse(Status.OK);

		/* 查找用户 */
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

		/* 查找店 */
		Shop shop = null;
		try {
			shop = shopBo.findByShopId(shopid);
		} catch (ShopNotFoundException e) {
			response.setStatus(Status.ERR_SHOP_NOT_FOUND);
			return response;
		}

		/* 新建并保存Meeting */
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

	@RequestMapping(value = { "/getMeetingList**" }, method = RequestMethod.GET)
	public MeetingListResponse getMeetingList(
			@RequestParam(required = true) Double longitude,
			@RequestParam(required = true) Double latitude,
			@RequestParam(required = false, defaultValue = "0") Integer pagenum,
			@RequestParam(required = false, defaultValue = "1") Integer sorttype,
			@RequestParam(required = false, defaultValue = "1000") Integer range,
			@RequestParam(required = false, defaultValue = "") String gender,
			@RequestParam(required = false, defaultValue = "") String job,
			@RequestParam(required = false, defaultValue = "") String shopName) {
		MeetingListResponse meetingListResponse = new MeetingListResponse();

		/* list元素为Object[2]中第一个对象是meeting，第二个是距离，double类型 */
		List list = null;
		try {
			list = meetingBo.getMeetingList(longitude, latitude, pagenum,
					sorttype, range, gender, job, shopName);
		} catch (Exception e) {
			meetingListResponse.setStatus(Status.SERVICE_NOT_AVAILABLE);
		}

		meetingListResponse.setStatus(Status.OK);
		Iterator iterator = list.iterator();
		Object[] objects = null;
		while (iterator.hasNext()) {
			objects = (Object[]) iterator.next();
			meetingListResponse.getMeetingList()
					.add(new MeetingDetail((Meeting) objects[0],
							(Double) objects[1]));
		}
		return meetingListResponse;
	}

	@RequestMapping(value = { "/getMyMeetingList**" }, method = RequestMethod.GET)
	public MeetingListResponse getMyMeetingList(
			@RequestParam(required = true) Long id,
			@RequestParam(required = true) String token,
			@RequestParam(required = false, defaultValue = "0") Integer pagenum) {
		MeetingListResponse meetingListResponse = new MeetingListResponse();

		/* 查找用户 */
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

		/* list元素为Object,是meeting */
		List list = null;
		try {
			list = meetingBo.getMeetingListByUser(user, pagenum);
		} catch (Exception e) {
			meetingListResponse.setStatus(Status.SERVICE_NOT_AVAILABLE);
		}
		meetingListResponse.setStatus(Status.OK);
		Iterator iterator = list.iterator();
		Object[] objects = null;
		while (iterator.hasNext()) {
			meetingListResponse.getMeetingList().add(
					new MeetingDetail((Meeting) iterator.next()));
		}
		return meetingListResponse;
	}

	@RequestMapping(value = { "/getMyPartMeetingList**" }, method = RequestMethod.GET)
	public MeetingListResponse getMyPartMeetingList(
			@RequestParam(required = true) Long id,
			@RequestParam(required = true) String token,
			@RequestParam(required = false, defaultValue = "0") Integer pagenum) {
		MeetingListResponse meetingListResponse = new MeetingListResponse();

		/* 查找用户 */
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

		/* list元素为Object,是meeting */
		List list = null;
		try {
			list = meetingBo.getMeetingListByParticipate(user, pagenum);
		} catch (Exception e) {
			meetingListResponse.setStatus(Status.SERVICE_NOT_AVAILABLE);
		}
		meetingListResponse.setStatus(Status.OK);
		Iterator iterator = list.iterator();
		Object[] objects = null;
		while (iterator.hasNext()) {
			meetingListResponse.getMeetingList().add(
					new MeetingDetail((Meeting) iterator.next()));
		}
		return meetingListResponse;
	}

	@RequestMapping(value = { "/getMeetingDetail**" }, method = RequestMethod.GET)
	public MeetingDetailResponse getMeetingDetail(
			@RequestParam(required = true) Long id,
			@RequestParam(required = true) String token,
			@RequestParam(required = true) Long meetingid) {
		MeetingDetailResponse response = new MeetingDetailResponse();
		/* 查找用户 */
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

		/* 是拥有者，能看到申请信息和参与者信息 */
		if (meeting.getOwner().equals(user)) {
			/* 饭约基本信息 */
			response.setMeetingDetail(new MeetingDetail(meeting));
			/* 参与者信息 */
			Iterator iterator = meeting.getParticipator().iterator();
			while (iterator.hasNext()) {
				response.getParticipates().add(
						new UserInfo((User) iterator.next()));
			}

			/* 申请信息 */
			try {
				iterator = meetingBo.getApplyByMeeting(meeting).iterator();
				while (iterator.hasNext()) {
					response.getApplicants().add(
							new ApplyInfo((MeetingApply) iterator.next()));
				}
			} catch (Exception e) {
				/* 没有参与者,不做处理 */
				// response.setStatus(Status.SERVICE_NOT_AVAILABLE);
				// return response;
			}
		} else if (meeting.getParticipator().contains(user)) {
			/* 是参与者，能看到参与者信息 */
			/* 饭约基本信息 */
			response.setMeetingDetail(new MeetingDetail(meeting));

			/* 参与者信息 */
			Iterator iterator = meeting.getParticipator().iterator();
			while (iterator.hasNext()) {
				response.getParticipates().add(
						new UserInfo((User) iterator.next()));
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
		NewApplyResponse response = new NewApplyResponse();
		/* 查找用户 */
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

		/* 自己发起的饭约或者已经参与的饭约,不能申请 */
		if (meeting.getOwner().equals(user)
				|| meeting.getParticipator().contains(user)) {
			response.setStatus(Status.ERR_CAN_NOT_APPLY_FOR_THE_MEETING);
			return response;
		}

		try {
			meetingBo.applyForMeeting(user, meeting, applyContent);
		} catch (Exception e) {
			response.setStatus(Status.ERR_CAN_NOT_APPLY_FOR_THE_MEETING);
			return response;
		}
		response.setStatus(Status.OK);
		return response;
	}

	@RequestMapping(value = { "/processMeetingApply**" }, method = RequestMethod.GET)
	public NewApplyResponse processMeetingApply(
			@RequestParam(required = true) Long id,
			@RequestParam(required = true) String token,
			@RequestParam(required = true) Long applyid,
			@RequestParam(required = true) Boolean approved) {
		NewApplyResponse response = new NewApplyResponse();
		/* 查找用户 */
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

		/* 查找申请 */
		MeetingApply meetingApply = null;
		try {
			meetingApply = meetingBo.getApplyById(applyid);
		} catch (ApplyNotFoundException e) {
			response.setStatus(Status.ERR_NO_SUCH_APPLY);
			return response;
		}

		/* 查找饭约 */
		Meeting meeting = meetingApply.getToMeeting();
		if (meeting == null) {
			response.setStatus(Status.ERR_MEETING_NOT_FOUND);
			return response;
		}

		/* 不是饭约拥有者，不能处理饭约申请 */
		if (!meeting.getOwner().equals(user)) {
			response.setStatus(Status.ERR_BANNED);
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

		response.setStatus(Status.OK);
		return response;
	}

	public String commentOnMeeting(Long id, String token, Long meetingid,
			String comment) {
		// TODO Auto-generated method stub
		return null;
	}

}
