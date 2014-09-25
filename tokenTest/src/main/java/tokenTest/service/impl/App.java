package tokenTest.service.impl;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import tokenTest.bo.MeetingBo;
import tokenTest.bo.PictureBo;
import tokenTest.bo.ShopBo;
import tokenTest.bo.TagBo;
import tokenTest.bo.UserBo;
import tokenTest.model.Meeting;
import tokenTest.model.Picture;
import tokenTest.model.Shop;
import tokenTest.model.Tag;
import tokenTest.model.User;

public class App {
	public static void main(String[] args) {
		@SuppressWarnings("resource")
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
		//Picture picture = new Picture(new Date(), "bob photo two");
		/*
		 * pictureBo.save(picture);
		 * 
		 * ShopBo shopBo = (ShopBo) appContext.getBean("shopBo"); Shop shop =
		 * new Shop(); shop.setArea("徐家汇"); shop.setCity("上海");
		 * shop.setType("中餐"); shop.setPrice(99.99);
		 */
		// shopBo.save(shop);

		/*
		 * TagBo tagBo = (TagBo) appContext.getBean("tagBo"); Tag tag = new
		 * Tag("抠脚大汉"); tagBo.save(tag);
		 */
		/*
		 * tagBo.save(new Tag("抠鼻大叔")); tagBo.save(new Tag("抠脚大汉"));
		 * tagBo.save(new Tag("office达人"));
		 */

		UserBo userBo = (UserBo) appContext.getBean("userBo");
		User user = userBo.findByUserId(4L);
		Picture picture = pictureBo.findById(10L);
		Set<Picture> pictures = user.getPicture();
		pictures.remove(picture);
		user.setPicture(pictures);
		/*HashSet<Tag> tagSet = new HashSet<Tag>();
		tagSet.add(tag);
		user.setTags(tagSet);*/
		userBo.update(user);

		/*
		 * MeetingBo meetingBo = (MeetingBo) appContext.getBean("meetingBo");
		 * Meeting meeting = new Meeting(user, new Date(), 2, 2, "F", 30,
		 * "一起喝茶");
		 */
		// meetingBo.save(meeting);
		System.out.println(System.getProperty("user.dir"));
		System.out.println("Done");
	}
}