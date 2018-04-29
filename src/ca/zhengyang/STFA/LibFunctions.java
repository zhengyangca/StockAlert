package ca.zhengyang.STFA;

import java.util.ArrayList;
import java.util.List;

public class LibFunctions {

	public static List<String> getFriendsListForUser(String userId) {
		// Mock friends' IDs
		List<String> friendsId = new ArrayList<String>();
		if (userId != null) {
			switch (userId) {
			case "1":
				friendsId.add("2");
				friendsId.add("3");
				friendsId.add("4");
				break;
			case "2":
				friendsId.add("1");
				friendsId.add("4");
				friendsId.add("5");
				break;
			case "3":
				friendsId.add("1");
				friendsId.add("4");
				friendsId.add("5");
				break;
			case "4":
				friendsId.add("1");
				friendsId.add("2");
				friendsId.add("3");
				break;
			case "5":
				friendsId.add("2");
				friendsId.add("3");
				break;
			default:
				break;
			}
		}
		return friendsId;
	}

	public static List<String> getTradeTransactionsForUser(String friendId) {
		// Mock transaction data
		List<String> transactions = new ArrayList<String>();
		if (friendId != null) {
			switch (friendId) {
			case "1":
				transactions.add("2018-04-21,BUY,GOOG");
				transactions.add("2018-04-23,SELL,YHOO");
				transactions.add("2018-04-27,BUY,BABA");
				transactions.add("2018-04-27,SELL,TSL");
				break;
			case "2":
				transactions.add("2018-04-22,BUY,GOOG");
				transactions.add("2018-04-27,BUY,BABA");
				transactions.add("2018-04-13,BUY,YHOO");
				break;
			case "3":
				transactions.add("2018-04-27,BUY,BABA");
				transactions.add("2018-04-26,BUY,GOOG");
				transactions.add("2018-04-28,SELL,GOOG");
				transactions.add("2018-04-27,SELL,TSL");
				break;
			case "4":
				transactions.add("2018-04-27,BUY,GOOG");
				transactions.add("2018-04-27,BUY,YHOO");
				transactions.add("2018-04-27,BUY,BABA");
				transactions.add("2018-04-27,BUY,BABA");
				transactions.add("2018-04-27,BUY,TSL");
				transactions.add("2018-04-27,BUY,TSL");
				transactions.add("2018-04-27,BUY,TSL");
				break;
			case "5":
				transactions.add("2018-04-27,BUY,GOOG");
				transactions.add("2018-04-27,BUY,YHOO");
				transactions.add("2018-04-27,BUY,BABA");
				transactions.add("2018-04-27,BUY,BABA");
				break;
			default:
				break;
			}
		}

		return transactions;
	}
}
