package ca.zhengyang.STFA.test;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import ca.zhengyang.STFA.FriendsBuySellAlert;

public class StockTest {

	@Test
	public void testMain() {
		FriendsBuySellAlert friendsBuySellAlert = new FriendsBuySellAlert("3");
		List<String> alertList = friendsBuySellAlert.produceRankedAlerts();
		List<String> expected = Arrays.asList("3,BUY,BABA", "2,BUY,GOOG", "1,BUY,YHOO");
		assertEquals(expected, alertList);
	}

	@Test
	public void testGetPastWeekFriendTransactions() {
		FriendsBuySellAlert friendsBuySellAlert = new FriendsBuySellAlert("3");
		List<String> friendTransList = friendsBuySellAlert.getPastWeekFriendTransactions(Arrays.asList("1", "4", "5"));
		List<String> expected = Arrays.asList("1/YHOO,-1/BABA,1/TSL,-1/", "4/GOOG,1/YHOO,1/BABA,2/TSL,3/",
				"5/GOOG,1/YHOO,1/BABA,2/");
		assertEquals(expected, friendTransList);
	}

	@Test
	public void testProcessBuySellTrans() {
		FriendsBuySellAlert friendsBuySellAlert = new FriendsBuySellAlert("3");
		Map<String, Integer> rawAlerts = friendsBuySellAlert.processBuySellTrans(
				Arrays.asList("1/YHOO,-1/BABA,1/TSL,-1/", "4/GOOG,1/YHOO,1/BABA,2/TSL,3/", "5/GOOG,1/YHOO,1/BABA,2/"));
		Map<String, Integer> expected = new HashMap<>();
		expected.put("YHOO", 1);
		expected.put("BABA", 3);
		expected.put("TSL", 0);
		expected.put("GOOG", 2);
		assertEquals(expected, rawAlerts);
	}

	@Test
	public void testRankAlerts() {
		FriendsBuySellAlert friendsBuySellAlert = new FriendsBuySellAlert("3");
		Map<String, Integer> rawAlerts = new HashMap<>();
		rawAlerts.put("YHOO", 1);
		rawAlerts.put("BABA", 3);
		rawAlerts.put("TSL", 0);
		rawAlerts.put("GOOG", 2);
		List<String> rankedAlerts = friendsBuySellAlert.rankAlerts(rawAlerts);
		List<String> expected = Arrays.asList("3,BUY,BABA", "2,BUY,GOOG", "1,BUY,YHOO");
		assertEquals(expected, rankedAlerts);

	}
	
	@Test
	public void testCalcDaysBetween() {
		// ...
	}

}

// Time complexity - O(nlogn)
// Space complexity - O(n)