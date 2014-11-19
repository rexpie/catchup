package tests;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import junit.framework.Assert;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import tokenTest.Util.Constants;
import tokenTest.Util.Status;
import tokenTest.bo.MeetingBo;
import tokenTest.bo.ShopBo;
import tokenTest.bo.UserBo;
import tokenTest.bo.ValidationCodeBo;
import tokenTest.model.Meeting;
import tokenTest.model.Shop;
import tokenTest.model.User;
import tokenTest.model.ValidationCode;
import tokenTest.response.MeetingDetailResponse;
import tokenTest.response.MeetingListResponse;
import tokenTest.response.StatusResponse;
import tokenTest.service.IMeetingService;
import tokenTest.service.IUserService;

public class MeetingTest {
	@Test
	public void test() {
		ApplicationContext appContext = new ClassPathXmlApplicationContext(
				"spring/config/BeanLocations.xml");
		UserBo userBo = (UserBo) appContext.getBean("userBo");
		MeetingBo meetingBo = (MeetingBo) appContext.getBean("meetingBo");
		ValidationCodeBo validationCodeBo = (ValidationCodeBo) appContext
				.getBean("validationCodeBo");
		for (long i = 0; i < 10; i++) {
			try {
				Meeting m = meetingBo.getMeetingById(i);
				if (m != null) {
					meetingBo.stopMeeting(m, "");
					meetingBo.delete(m);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		User user[] = new User[10];
		ShopBo shopBo = (ShopBo) appContext.getBean("shopBo");
		IMeetingService service = (IMeetingService) appContext
				.getBean("meetingService");
		IUserService userService = (IUserService) appContext
				.getBean("userService");

		String[] nickName = new String[10];
		String[] pass = new String[10];
		String[] phone = new String[10];

		for (int i = 1; i < 10; i++) {
			nickName[i] = RandomStringUtils
					.randomAlphanumeric(Constants.TOKEN_LENGTH);
			pass[i] = RandomStringUtils
					.randomAlphanumeric(Constants.TOKEN_LENGTH);
			phone[i] = RandomStringUtils.randomNumeric(11);
			ValidationCode code = new ValidationCode();
			code.setCode("8888");
			code.setPhoneNum(phone[i]);
			validationCodeBo.save(code);
			ok(userService.userRegister(nickName[i], pass[i], phone[i], "8888",
					null, null, null, null));
			user[i] = userBo.findByNickOrPhone(nickName[i]);
		}

		notok(service.newMeeting(user[1].getId(), user[1].getToken(),
				10349478L, "F", "new meeting"),
				Status.ERR_NEW_MEETING_MUST_HAVE_PIC);

		MultipartFile file1;
		try {
			file1 = new MockMultipartFile("file", "file", "image/png",
					new FileInputStream(new File(
							"C:\\Users\\youzinvxia\\Pictures\\证件照.png")));
			for (int i = 1; i < 9; i++) {
				ok(userService.addPhoto(user[i].getId(), user[i].getToken(),
						file1, "no", true));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		notok(service.newMeeting(user[1].getId(), user[1].getToken(),
				10349478L, "N", "new meeting"),
				Status.ERR_NEW_MEETING_MUST_HAVE_GENDER);

		String building = "buil";
		String birthday = "20000101";
		String sex = "M";
		String job = "a job";
		String industry = "an ind";
		String company = "random.co";
		ok(userService.updateUserProfile(user[1].getId(), user[1].getToken(),
				user[1].getNickname(), null, null, sex, null, null, null, null));

		notok(service.newMeeting(user[1].getId(), user[1].getToken(),
				10349478L, "N", "new meeting"),
				Status.ERR_NEW_MEETING_MUST_HAVE_JOB);

		ok(userService.updateUserProfile(user[1].getId(), user[1].getToken(),
				user[1].getNickname(), null, null, null, job, null, null, null));

		notok(service.newMeeting(user[1].getId(), user[1].getToken(),
				10349478L, "N", "new meeting"),
				Status.ERR_NEW_MEETING_MUST_HAVE_BUILDING);

		ok(userService.updateUserProfile(user[1].getId(), user[1].getToken(),
				user[1].getNickname(), building, null, null, null, null, null,
				null));

		notok(service.newMeeting(user[1].getId(), user[1].getToken(), -1L, "N",
				"new meeting"), Status.ERR_SHOP_NOT_FOUND);

		notok(service.newMeeting(user[1].getId(), user[1].getToken(),
				10349478L, "x", "new meeting"), Status.ERR_INVALID_GENDER);

		ok(service.newMeeting(user[1].getId(), user[1].getToken(), 10349478L,
				"N", "new meeting"));

		for (int i = 2; i < 9; i++) {
			ok(userService.updateUserProfile(user[i].getId(),
					user[i].getToken(), user[i].getNickname(), building, null,
					"F", job, null, industry, null));
			ok(service.newMeeting(user[i].getId(), user[i].getToken(),
					10349478L, "F", "new meeting"));
		}
		Shop shop = shopBo.findByDianpingId(10349478L);
		MeetingListResponse meetingListResponse = service.getMeetingList(
				shop.getLongitude(), shop.getLatitude(), 0, 1, 1000, "", "",
				"", null, null);
		ok(meetingListResponse);
		Assert.assertEquals(8, meetingListResponse.getMeetingList().size());

		ok(service.applyForMeeting(user[2].getId(), user[2].getToken(), 1L, ""));
		notok(service.applyForMeeting(user[9].getId(), user[9].getToken(), 2L,
				""), Status.ERR_INVALID_GENDER);
		ok(userService.updateUserProfile(user[9].getId(), user[9].getToken(),
				user[9].getNickname(), null, null, "M", null, null, null, null));
		notok(service.applyForMeeting(user[9].getId(), user[9].getToken(), 2L,
				""), Status.ERR_INVALID_GENDER);
		ok(userService.updateUserProfile(user[9].getId(), user[9].getToken(),
				user[9].getNickname(), null, null, "F", null, null, null, null));
		ok(service.applyForMeeting(user[9].getId(), user[9].getToken(), 2L, ""));

		ok(userService.block(user[1].getId(), user[1].getToken(),
				user[2].getId()));
		notok(service.applyForMeeting(user[2].getId(), user[2].getToken(), 1L,
				""), Status.ERR_BLACKLISTED);

		ok(userService.unblock(user[1].getId(), user[1].getToken(),
				user[2].getId()));
		ok(service.applyForMeeting(user[2].getId(), user[2].getToken(), 1L, ""));

		notok(service.applyForMeeting(user[2].getId(), user[2].getToken(), -1L,
				""), Status.ERR_MEETING_NOT_FOUND);

		notok(service.applyForMeeting(user[1].getId(), user[1].getToken(), 1L,
				""), Status.ERR_CAN_NOT_APPLY_FOR_THE_MEETING);

		ok(service.applyForMeeting(user[2].getId(), user[2].getToken(), 3L, ""));
		ok(service.applyForMeeting(user[2].getId(), user[2].getToken(), 4L, ""));
		notok(service.applyForMeeting(user[2].getId(), user[2].getToken(), 5L,
				""), Status.ERR_TOO_MANY_APPLY);

		ok(service.applyForMeeting(user[4].getId(), user[4].getToken(), 1L, ""));
		ok(service.applyForMeeting(user[5].getId(), user[5].getToken(), 1L, ""));
		ok(service.applyForMeeting(user[6].getId(), user[6].getToken(), 1L, ""));
		ok(service.applyForMeeting(user[7].getId(), user[7].getToken(), 1L, ""));

		MeetingListResponse mlr = service.getMyMeetingList(user[1].getId(),
				user[1].getToken(), 0);
		ok(mlr);

		MeetingDetailResponse mdr = service.getMeetingDetail(user[1].getId(),
				user[1].getToken(), mlr.getMeetingList().get(0).getMeetingid());
		ok(mdr);

		Assert.assertEquals(5, mdr.getApplicants().size());

		Assert.assertEquals(0, mdr.getParticipates().size());

		long applierid = mdr.getApplicants().get(0).getUserId();

		mlr = service.getMyPartMeetingList(applierid,
				userBo.findByUserId(applierid).getToken(), 0);
		Assert.assertEquals(0, mlr.getMeetingList().size());

		mlr = service.getMeetingList(shop.getLongitude(), shop.getLatitude(),
				0, 1, 1000, "", "", "", applierid,
				userBo.findByUserId(applierid).getToken());

		ok(service.processMeetingApply(user[1].getId(), user[1].getToken(), mdr
				.getApplicants().get(0).getApplyId(), true));

		mlr = service.getMyMeetingList(user[1].getId(), user[1].getToken(), 0);
		mdr = service.getMeetingDetail(user[1].getId(), user[1].getToken(), mlr
				.getMeetingList().get(0).getMeetingid());
		ok(mdr);

		Assert.assertEquals(4, mdr.getApplicants().size());

		Assert.assertEquals(1, mdr.getParticipates().size());

		mlr = service.getMyPartMeetingList(applierid,
				userBo.findByUserId(applierid).getToken(), 0);
		ok(mlr);
		Assert.assertEquals(1, mlr.getMeetingList().size());

	}

	private void ok(StatusResponse s) {
		Assert.assertEquals(Status.OK, s.getStatus());
	}

	private void notok(StatusResponse s, Status exp) {
		Assert.assertEquals(s.getStatus(), exp);
	}
}
