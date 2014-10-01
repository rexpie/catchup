package tokenTest.service.impl;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import tokenTest.bo.UserBo;
import tokenTest.model.User;

/*Ԥ������,Picture���ܶ�������,ͨ��User������*/
public class App {
	public static void main(String[] args) {
		@SuppressWarnings("resource")
		ApplicationContext appContext = new ClassPathXmlApplicationContext(
				"spring/config/BeanLocations.xml");
		UserBo userBo = (UserBo) appContext.getBean("userBo");

		User user = new User("123456", "peach", "M", "CCB", "18918760155");
		userBo.save(user);
		
		user = new User("123456", "rexpie", "M", "IT", "13818860403");
		userBo.save(user);
		
		user = new User("123456", "xiaozi", "F", "jinqiao", "18918218819");
		userBo.save(user);

		System.out.println("Done");
	}
}