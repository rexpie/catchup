package tests;

import org.junit.Ignore;
import org.junit.Test;

import tokenTest.Util.IMUtil;

public class IMUtilTest {

	@Test
	@Ignore
	public void test() {
		System.out.print(IMUtil.getToken("1", "rex",
				"http://chatime.rexpie.net/chatime/user/getPicture?id=7"));
	}

	@Test
	public void testConversation() {
		System.out.println(IMUtil.startTextConversation("1", "2",
				"from 1 to 2, by server").toString());
		System.out.println(IMUtil.startTextConversation("2", "1",
				"from 2 to 1, by server").toString());
	}
}
