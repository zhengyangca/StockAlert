package ca.zhengyang.STFA;

import java.util.List;

public class Main {

	public static void main(String[] args) {
		FriendsBuySellAlert friendsBuySellAlert = new FriendsBuySellAlert("3");
		List<String> rankedAlerts = friendsBuySellAlert.produceRankedAlerts();
		System.out.println(rankedAlerts);
	}

}
