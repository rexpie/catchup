package org.test1.common;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javassist.expr.NewArray;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.test1.bo.MeetingBo;
import org.test1.bo.PictureBo;
import org.test1.bo.ShopBo;
import org.test1.bo.TagBo;
import org.test1.bo.UserBo;
import org.test1.model.Meeting;
import org.test1.model.Picture;
import org.test1.model.Shop;
import org.test1.model.Tag;
import org.test1.model.User;

public class App {
	public static void main(String[] args) {
		ApplicationContext appContext = new ClassPathXmlApplicationContext(
				"spring/config/BeanLocations.xml");

		/* StockBo stockBo = (StockBo)appContext.getBean("stockBo"); */

		/** insert **/
		/*
		 * Stock stock = new Stock(); stock.setStockCode("7668");
		 * stock.setStockName("HAIO"); stockBo.save(stock);
		 *//** select **/
		/*
		 * Stock stock2 = stockBo.findByStockCode("7668");
		 * System.out.println(stock2);
		 *//** update **/
		/*
		 * stock2.setStockName("HAIO-1"); stockBo.update(stock2);
		 *//** delete **/
		/*
		 * stockBo.delete(stock2);
		 */

		PictureBo pictureBo = (PictureBo) appContext.getBean("pictureBo");
		Picture picture = new Picture(new Date(), "peach photo", "peach");
		pictureBo.save(picture);

		ShopBo shopBo = (ShopBo) appContext.getBean("shopBo");
		Shop shop = new Shop();
		shop.setArea("徐家汇");
		shop.setCity("上海");
		shop.setType("中餐");
		shop.setPrice(99.99);
//		shopBo.save(shop);
		
		
		TagBo tagBo = (TagBo) appContext.getBean("tagBo");
		Tag tag = new Tag("ds");
//		tagBo.save(tag);
		/*tagBo.save(new Tag("抠鼻大叔"));
		tagBo.save(new Tag("抠脚大汉"));
		tagBo.save(new Tag("office达人"));*/
		
		UserBo userBo = (UserBo) appContext.getBean("userBo");
		User user = new User("123456", "rexpie", "M", "IT", "ms", new Date(), 100, "13818860403", "rexpie@gmail.com");
		user.setPic(picture);
		HashSet<Tag> tagSet = new HashSet<Tag>();
		tagSet.add(tag);
		user.setTags(tagSet);
		userBo.save(user);
		
		
		MeetingBo meetingBo = (MeetingBo) appContext.getBean("meetingBo");
		Meeting meeting = new Meeting(user, new Date(), 2, 2, 5, 30, "一起喝茶");
//		meetingBo.save(meeting);

		System.out.println("Done");
	}
}