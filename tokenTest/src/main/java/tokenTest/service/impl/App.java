package tokenTest.service.impl;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import tokenTest.bo.MeetingBo;
import tokenTest.bo.UserBo;
import tokenTest.model.User;
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
		
//		genUsers(userBo, userService);
		
		genMeetings(userBo,meetingBo, meetingService);
		
		System.out.println("Done");
	}

	private static void genMeetings(UserBo userBo, MeetingBo meetingBo,
			IMeetingService meetingService) {
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
		
		meetingService.processMeetingApply(user1.getId(), user1.getToken(), meetingDetail.getApplicants().get(0).getApplyId(), true);
	}

	private static void genUsers(UserBo userBo, IUserService service) {
		//gen rex
		service.userRegister("rexpie", "pass", null, "13818860403", null, null,null);

		User rexpie = userBo.findByNickOrPhone("rexpie");
		service.updateUserProfile(rexpie.getId(), rexpie.getToken(), null, "嘉里城", null, "M", "analyst","IT", "MS");
		
		//gen peach
		service.userRegister("peach", "ppass", null, "13818860404", null, null,null);

		User peach = userBo.findByNickOrPhone("peach");
		service.updateUserProfile(peach.getId(), peach.getToken(), null, "??", null, "M", "analyst","IT", "CCB");
		
		//gen NPC
		for (int i = 0;i <20;i++){
			service.userRegister("NPC"+i, "ppass", null, (13818800000L + i) + "", null, null,null);

			User npc = userBo.findByNickOrPhone("NPC"+i);
			service.updateUserProfile(npc.getId(), npc.getToken(), null, "building"+i, null, i%2==0?"M":"F", "job"+i,"IT", "?");

		}
	}
}