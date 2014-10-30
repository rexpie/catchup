package tokenTest.service.impl;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import tokenTest.bo.UserBo;
import tokenTest.model.User;
import tokenTest.service.MeetingServiceInterface;
import tokenTest.service.UserServiceInterface;

/*预埋数据,Picture不能独立存在,通过User来操作*/
public class App {
	public static void main(String[] args) {
		@SuppressWarnings("resource")
		ApplicationContext appContext = new ClassPathXmlApplicationContext(
				"spring/config/BeanLocations.xml");
		UserBo userBo = (UserBo) appContext.getBean("userBo");
		UserServiceInterface userService = (UserServiceInterface) appContext.getBean("userService");
		MeetingServiceInterface meetingService = (MeetingServiceInterface) appContext.getBean("meetingService");
		
		genUsers(userBo, userService);
		
		System.out.println("Done");
	}

	private static void genUsers(UserBo userBo, UserServiceInterface service) {
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