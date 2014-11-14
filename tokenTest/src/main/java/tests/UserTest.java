package tests;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;

import junit.framework.Assert;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import tokenTest.Util.Constants;
import tokenTest.Util.Status;
import tokenTest.bo.UserBo;
import tokenTest.bo.ValidationCodeBo;
import tokenTest.model.User;
import tokenTest.model.ValidationCode;
import tokenTest.response.StatusResponse;
import tokenTest.response.UserDetailResponse;
import tokenTest.service.IUserService;


public class UserTest {

	@Test
	public void testUser() {
		ApplicationContext appContext = new ClassPathXmlApplicationContext(
				"spring/config/BeanLocations.xml");
		UserBo userBo = (UserBo) appContext.getBean("userBo");
		IUserService userService = (IUserService) appContext
				.getBean("userService");
		ValidationCodeBo validationCodeBo = (ValidationCodeBo) appContext.getBean("validationCodeBo");

		// register and login
		String nickName1 = RandomStringUtils
				.randomAlphanumeric(Constants.TOKEN_LENGTH);
		String pass1 = RandomStringUtils
				.randomAlphanumeric(Constants.TOKEN_LENGTH);
		String phone1 = RandomStringUtils.randomNumeric(11);
		String newpass = RandomStringUtils
				.randomAlphanumeric(Constants.TOKEN_LENGTH);

		String nickName2 = RandomStringUtils
				.randomAlphanumeric(Constants.TOKEN_LENGTH);
		String pass2 = RandomStringUtils
				.randomAlphanumeric(Constants.TOKEN_LENGTH);
		String phone2 = RandomStringUtils.randomNumeric(11);
		Date d = new Date();
		validationCodeBo.save(new ValidationCode("8888", d, phone1, 0, d));
		validationCodeBo.save(new ValidationCode("8888", d, phone2, 0, d));
		ok(userService.userRegister(nickName1, pass1, phone1,"8888", null, null, null,
				null));
		ok(userService.userRegister(nickName2, pass2, phone2, "8888", null, null, null,
				null));

		User user1 = userBo.findByNickOrPhone(nickName1);
		User user2 = userBo.findByNickOrPhone(nickName2);
		ok(userService.userLogout(user1.getId(), user1.getToken()));

		ok(userService.userLogin(phone1, pass1));
		ok(userService.userLogout(user1.getId(), user1.getToken()));

		ok(userService.userLogin(nickName1, pass1));
		ok(userService.userLogout(user1.getId(), user1.getToken()));

		ok(userService.changePassword(user1.getId(), pass1, newpass));

		ok(userService.userLogin(user1.getPhone_number(), newpass));
		ok(userService.userLogout(user1.getId(), user1.getToken()));

		ok(userService.userLogin(user1.getNickname(), newpass));
		ok(userService.userLogout(user1.getId(), user1.getToken()));

		user1 = userBo.findByUserId(user1.getId());
		
		ok(userService.block(user1.getId(), user1.getToken(), user2.getId()));
		ok(userService.blacklist(user1.getId(), user1.getToken()));
		Assert.assertEquals(userService.blacklist(user1.getId(), user1.getToken()).users.get(0), user2.getId());
		
		ok(userService.unblock(user1.getId(), user1.getToken(), user2.getId()));
		Assert.assertEquals(userService.blacklist(user1.getId(), user1.getToken()).users.size(), 0);

		ok(userService.getUserDetail(user1.getId(), user1.getToken(), null));
		Assert.assertEquals(userService.getViewers(user2.getId(), user2.getToken()).users.size(), 0);
		Assert.assertEquals(userService.getViewers(user1.getId(), user1.getToken()).users.size(), 0);
		ok(userService.getUserDetail(user1.getId(), user1.getToken(), user2.getId()));
		Assert.assertEquals(userService.getViewers(user2.getId(), user2.getToken()).users.get(0), user1.getId());
		
		
		Assert.assertEquals(userService.getLikeUsers(user1.getId(), user1.getToken()).users.size(),0);
		ok(userService.like(user2.getId(), user2.getToken(), user1.getId()));
		Assert.assertEquals(userService.getLikeUsers(user1.getId(), user1.getToken()).users.get(0),user2.getId());
		ok(userService.cancelLike(user2.getId(), user2.getToken(), user1.getId()));
		Assert.assertEquals(userService.getLikeUsers(user1.getId(), user1.getToken()).users.size(),0);
		
		userBo.update(user1);
		ok(userService.setTags(user1.getId(), user1.getToken(), "tag1,tag2,something"));
		Assert.assertTrue(userService.getUserDetail(user1.getId(), user1.getToken(), null).getUser().getTags().contains("tag1"));
		Assert.assertTrue(userService.getUserDetail(user1.getId(), user1.getToken(), null).getUser().getTags().contains("tag2"));
		Assert.assertTrue(userService.getUserDetail(user1.getId(), user1.getToken(), null).getUser().getTags().contains("something"));
		
		ok(userService.complain(user1.getId(), user1.getToken(), user2.getId(), "hentai"));
		ok(userService.report(user1.getId(), user1.getToken(), "reason to report"));
		
		String building = "buil";
		String birthday = "20000101";
		String sex = "M";
		String job = "a job";
		String industry = "an ind";
		String company = "random.co";
		ok(userService.updateUserProfile(user1.getId(), user1.getToken(), user1.getNickname(), building, birthday, sex, job, industry, company));
		UserDetailResponse res = userService.getUserDetail(user1.getId(), user1.getToken(), user1.getId());
		
		Assert.assertEquals(res.getUser().getAge(), 14);
		Assert.assertEquals(res.getUser().getBuilding(), building);
		Assert.assertEquals(res.getUser().getCompany(), company);
		Assert.assertEquals(res.getUser().getSex(), sex);
		Assert.assertEquals(res.getUser().getIndustry(), industry);
		Assert.assertEquals(res.getUser().getJob(), job);
		
		
		try {
			MultipartFile file1 = new MockMultipartFile("file", "file",
					"image/png", new FileInputStream(new File(
							"C:\\Users\\youzinvxia\\Pictures\\证件照.png")));
			MultipartFile file2 = new MockMultipartFile("file", "file",
					"image/png", new FileInputStream(new File(
							"C:\\Users\\youzinvxia\\Pictures\\身份证正面.png")));
			ok(userService.addPhoto(user1.getId(), user1.getToken(), file1,
					"no", true));
			ok(userService.addPhoto(user1.getId(), user1.getToken(), file2,
					"noo", false));
			ok(userService.addPhoto(user1.getId(), user1.getToken(), file2,
					"noo", false));
			ok(userService.addPhoto(user1.getId(), user1.getToken(), file2,
					"noo", false));

			String firstpicid = userService.listPhoto(user1.getId(),
					user1.getToken()).ids.get(0);
			ok(userService.deletePhoto(user1.getId(), user1.getToken(),
					firstpicid));

			Assert.assertEquals(2,
					userService.listPhoto(user1.getId(), user1.getToken()).ids.size());

			for (String id : userService.listPhoto(user1.getId(),
					user1.getToken()).ids) {
				ok(userService.deletePhoto(user1.getId(), user1.getToken(), id));
			}

			Assert.assertEquals(0,
					userService.listPhoto(user1.getId(), user1.getToken()).ids.size());

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void ok(StatusResponse s) {
		Assert.assertEquals(Status.OK, s.getStatus());
	}
}
