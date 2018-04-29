package ca.zhengyang.STFA;

import java.util.List;

public class Main {

	public static void main(String[] args) {
		FriendsBuySellAlert friendsBuySellAlert = new FriendsBuySellAlert("4");
		List<String> rankedAlerts = friendsBuySellAlert.produceRankedAlerts();
		for (String alerts : rankedAlerts) {
			System.out.println(alerts);
		}
	}

}
