package tests;

import org.junit.Test;

import tokenTest.Util.IMUtil;

public class IMUtilTest {

	@Test
	public void test(){
		System.out.print(IMUtil.getToken("1", "rex", "http://chatime.rexpie.net/chatime/user/getPicture?id=7"));
	}
}
