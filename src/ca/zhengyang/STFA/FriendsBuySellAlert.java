package ca.zhengyang.STFA;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class FriendsBuySellAlert {
	private String userId;
	private List<String> friendIds;
	private List<String> friendTransactions;
	private Map<String, Integer> rawAlerts;
	private List<String> rankedALerts;

	public FriendsBuySellAlert(String userId) {
		this.userId = userId;
		if (!userId.equalsIgnoreCase(null)) {
			this.friendIds = LibFunctions.getFriendsListForUser(userId);
		}
		this.friendTransactions = new ArrayList<String>();
	}

	public List<String> produceRankedAlerts() {
		List<String> rankedAlerts = new ArrayList<String>();

		// Get all transactions of the user's friend in the past week
		this.friendTransactions = getPastWeekFriendTransactions(this.friendIds);

		// Get All alerts for each ticker
		if (this.friendTransactions == null) {
			System.err.println("Friend Transaction List is null");
			return null;
		}
		this.rawAlerts = processBuySellTrans(this.friendTransactions);

		// Rank alerts
		if (this.rawAlerts == null) {
			System.err.println("Friend Transaction Alert List is null");
			return null;
		}
		rankedAlerts = rankAlerts(rawAlerts);
		this.rankedALerts = rankedAlerts;
		
		return rankedAlerts;
	}

	public List<String> rankAlerts(Map<String, Integer> rawAlerts) {
		List<String> rankedAlerts = new ArrayList<String>();
		if (rawAlerts.size() <= 0) {
			System.err.println("Raw alerts Map is empty");
		}

		List<Entry<String, Integer>> entryList = new ArrayList<>(rawAlerts.entrySet());
		entryList.sort(new Comparator<Entry<String, Integer>>() {
			@Override
			public int compare(Entry<String, Integer> a, Entry<String, Integer> b) {
				return Math.abs(b.getValue()) - Math.abs(a.getValue());
			}
		});

		String currAlert = "";
		for (Map.Entry<String, Integer> entry : entryList) {
			if (entry.getValue() > 0) {
				currAlert = Integer.toString(entry.getValue());
				currAlert += ",BUY,";
				currAlert += entry.getKey();
			} else if (entry.getValue() < 0) {
				currAlert = Integer.toString(Math.abs(entry.getValue()));
				currAlert += ",SELL,";
				currAlert += entry.getKey();
			} else {
				continue;
			}
			rankedAlerts.add(currAlert);
		}

		return rankedAlerts;
	}

	/**
	 * Receive the list of friends transactions in the format e.g., "1/GOOG,2/YHOO,-3/",
	 * return friends buy/sell alert without order.
	 * 
	 * @param listsOfTrans
	 * @return rawAlerts
	 */
	public Map<String, Integer> processBuySellTrans(List<String> listOfTrans) {
		if (listOfTrans.size() <= 0) {
			return null;
		}
		Map<String, Integer> alerts = new LinkedHashMap<>();

		for (String userData : listOfTrans) {
			String[] params = userData.split("/");
			for (int i = 1; i < params.length; i++) {
				String[] ticker_num = params[i].split(",");
				String ticker = ticker_num[0];
				int share = Integer.parseInt(ticker_num[1]);
				if (!alerts.containsKey(ticker)) {
					if (share > 0) {
						alerts.put(ticker, 1);
					} else if (share < 0) {
						alerts.put(ticker, -1);
					}
				} else {
					if (share > 0) {
						alerts.put(ticker, alerts.get(ticker) + 1);
					} else if (share < 0) {
						alerts.put(ticker, alerts.get(ticker) - 1);
					}
				}
			}
		}
		return alerts;
	}

	/**
	 * Load our friends' IDs and all friend transactions, hence generate a list of string 
	 * in the format e.g., "1/GOOG,2/YHOO,-3/".
	 * 
	 * @param friends' IDs
	 * @return friendTransList
	 */
	public List<String> getPastWeekFriendTransactions(List<String> friendIds) {
		List<String> friendTransList = new ArrayList<String>();
		Date today = new Date();
		Date transactionDate;

		for (String friendId : friendIds) {
			List<String> currFriendTrads = LibFunctions.getTradeTransactionsForUser(friendId);
			String currFriendTickers = "";

			if (currFriendTrads == null) {
				continue;
			}
			Map<String, Integer> netTickers = new LinkedHashMap<String, Integer>();
			for (String currFriendTrad : currFriendTrads) {
				if (currFriendTrad == "" || currFriendTrad == null) {
					continue;
				}
				try {
					String[] params = currFriendTrad.split(",");
					transactionDate = new SimpleDateFormat("yyyy-MM-dd").parse(params[0]);
					String tradeType = params[1];
					String ticker = params[2];
					if (CalcDaysBetween(transactionDate, today) <= 7) {
						if (!netTickers.containsKey(ticker)) {
							if (tradeType.equalsIgnoreCase("BUY")) {
								netTickers.put(ticker, 1);
							} else if (tradeType.equalsIgnoreCase("SELL")) {
								netTickers.put(ticker, -1);
							}
						} else {
							if (tradeType.equalsIgnoreCase("BUY")) {
								netTickers.put(ticker, netTickers.get(ticker) + 1);
							} else if (tradeType.equalsIgnoreCase("SELL")) {
								netTickers.put(ticker, netTickers.get(ticker) - 1);
							}
						}
					}
				} catch (ParseException e) {
					System.out.println("Date parse error");
					e.printStackTrace();
				}
			}
			currFriendTickers = "" + friendId + "/";
			for (Map.Entry<String, Integer> entry : netTickers.entrySet()) {
				currFriendTickers += entry.getKey() + "," + entry.getValue() + "/";
			}
			friendTransList.add(currFriendTickers);
		}
		return friendTransList;
	}

	public int CalcDaysBetween(Date date1, Date date2) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date1);
		long time1 = calendar.getTimeInMillis();
		calendar.setTime(date2);
		long time2 = calendar.getTimeInMillis();
		long daysBetween;
		if (time2 >= time1) {
			daysBetween = (time2 - time1) / (1000 * 3600 * 24);
		} else {
			daysBetween = (time1 - time2) / (1000 * 3600 * 24);
		}
		return Integer.parseInt(String.valueOf(daysBetween));
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public List<String> getRankedALerts() {
		return rankedALerts;
	}

	public void setRankedALerts(List<String> rankedALerts) {
		this.rankedALerts = rankedALerts;
	}

}
