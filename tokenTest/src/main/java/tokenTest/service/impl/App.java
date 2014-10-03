package tokenTest.service.impl;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import tokenTest.bo.MeetingBo;
import tokenTest.bo.UserBo;
import tokenTest.model.User;

/*预埋数据,Picture不能独立存在,通过User来操作*/
public class App {
	public static void main(String[] args) {
		@SuppressWarnings("resource")
		ApplicationContext appContext = new ClassPathXmlApplicationContext(
				"spring/config/BeanLocations.xml");

		/*
		 * UserBo userBo = (UserBo) appContext.getBean("userBo");
		 * 
		 * 
		 * User user = new User("123456", "peach", "M", "CCB", "18918760155");
		 * userBo.save(user);
		 * 
		 * user = new User("123456", "rexpie", "M", "IT", "13818860403");
		 * userBo.save(user);
		 * 
		 * user = new User("123456", "xiaozi", "F", "jinqiao", "18918218819");
		 * userBo.save(user);
		 */
		MeetingBo meetingBo = (MeetingBo) appContext.getBean("meetingBo");
		System.out.println(meetingBo.getMeetingList(0.0001, 0.0002, 0, 1, 1000,
				"F", "", ""));
		System.out.println("Done");
	}
}