package ca.zhengyang.STFA.test;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import ca.zhengyang.STFA.FriendsBuySellAlert;

public class StockTest {

	@Test
	public void test() {
		FriendsBuySellAlert friendsBuySellAlert = new FriendsBuySellAlert("3");
		List<String> alertList = friendsBuySellAlert.produceRankedAlerts();
		List<String> expected = Arrays.asList("3,BUY,GOOG", "3,BUY,BABA", "1,BUY,YHOO");
		assertEquals(expected, alertList);
	}

}


//Time complexity - O(m * n)
//