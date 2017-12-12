package com.coupon.model;

import java.util.TimerTask;

import com.friend_list.model.FriendListChatServer;




public class CouponTask extends TimerTask {

	@Override
	public void run() {
		// TODO Auto-generated method stub
		String message = "剩餘1分鐘，開搶折價券 !!";
		String who = "MB";
		FriendListChatServer.onMessage(who,message);
		
		

		
	}

}
