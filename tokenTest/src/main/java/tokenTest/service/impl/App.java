package tokenTest.service.impl;

import java.util.Date;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import tokenTest.bo.MeetingBo;
import tokenTest.bo.UserBo;
import tokenTest.bo.ValidationCodeBo;
import tokenTest.model.User;
import tokenTest.model.ValidationCode;
import tokenTest.response.MeetingDetail;
import tokenTest.response.MeetingDetailResponse;
import tokenTest.response.MeetingListResponse;
import tokenTest.service.IMeetingService;
import tokenTest.service.IUserService;

/*预埋数据,Picture不能独立存在,通过User来操作*/
public class App {
	public static void main(String[] args) {
		@SuppressWarnings("resource")
		ApplicationContext appContext = new ClassPathXmlApplicationContext(
				"spring/config/BeanLocations.xml");
		UserBo userBo = (UserBo) appContext.getBean("userBo");
		MeetingBo meetingBo = (MeetingBo) appContext.getBean("meetingBo");
		IUserService userService = (IUserService) appContext.getBean("userService");
		IMeetingService meetingService = (IMeetingService) appContext.getBean("meetingService");
		ValidationCodeBo validationCodeBo = (ValidationCodeBo) appContext.getBean("validationCodeBo");

		genUsers(userBo,validationCodeBo, userService);
		
		genMeetings(userBo,meetingBo,validationCodeBo, meetingService);
		
		System.out.println("Done");
	}

	private static void genMeetings(UserBo userBo, MeetingBo meetingBo,
			ValidationCodeBo validationCodeBo, IMeetingService meetingService) {
		User user1 = userBo.findByUserId(1L);
		User user2 = userBo.findByUserId(2L);
		User user3 = userBo.findByUserId(3L);
		User user4 = userBo.findByUserId(4L);
		User user5 = userBo.findByUserId(5L);
		meetingService.newMeeting(user1.getId(), user1.getToken(), 6162303L, "F", "hi");
		meetingService.newMeeting(user2.getId(), user2.getToken(), 6013798L, "M", "hi");
		
		MeetingListResponse meetings = meetingService.getMeetingList(121.38308, 31.245836, 0, 1, 1000, "", "", "", user3.getId(), user3.getToken());
		
		for (MeetingDetail meeting:meetings.getMeetingList()){
			meetingService.applyForMeeting(user3.getId(), user3.getToken(), meeting.getMeetingid(), "apply content");
			meetingService.applyForMeeting(user4.getId(), user4.getToken(), meeting.getMeetingid(), "apply content");
		}

		MeetingDetailResponse meetingDetail = meetingService.getMeetingDetail(user1.getId(), user1.getToken(), meetings.getMeetingList().get(0).getMeetingid());
		
//		meetingService.processMeetingApply(user1.getId(), user1.getToken(), meetingDetail.getApplicants().get(0).getApplyId(), true);
	}

	private static void genUsers(UserBo userBo, ValidationCodeBo validationCodeBo, IUserService service) {
		Date d = new Date();
		validationCodeBo.save(new ValidationCode("8888", d, "13818860403", 0, d));

		//gen rex
		service.userRegister("rexpie", "pass", "13818860403", "8888", null, null, null,null);

		User rexpie = userBo.findByNickOrPhone("rexpie");
//		service.updateUserProfile(rexpie.getId(), rexpie.getToken(), null, "嘉里城", null, "M", "analyst","IT", "MS");
		
		//gen peach
		validationCodeBo.save(new ValidationCode("8888", d, "13818860404", 0, d));
		service.userRegister("peach", "ppass", "13818860404", "8888", null, null, null,null);

		User peach = userBo.findByNickOrPhone("peach");
//		service.updateUserProfile(peach.getId(), peach.getToken(), null, "??", null, "M", "analyst","IT", "CCB");
		
		//gen NPC
		for (int i = 0;i <20;i++){
			validationCodeBo.save(new ValidationCode("8888", d, "" + (13818800000L + i), 0, d));
			service.userRegister("NPC"+i, "ppass", (13818800000L + i) + "", "8888", null, null, null,null);

			User npc = userBo.findByNickOrPhone("NPC"+i);
//			service.updateUserProfile(npc.getId(), npc.getToken(), null, "building"+i, null, i%2==0?"M":"F", "job"+i,"IT", "?");

		}
	}
}