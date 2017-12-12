package com.friend_list.model;

import java.io.*;
import java.util.*;

import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import org.json.JSONException;
import org.json.JSONObject;

import com.member_profile.model.MemberProfileService;

import javax.websocket.Session;
import javax.websocket.OnOpen;
import javax.websocket.OnMessage;
import javax.websocket.OnError;
import javax.websocket.OnClose;
import javax.servlet.http.Part;
import javax.websocket.CloseReason;

@ServerEndpoint("/FriendListChatServer/{myName}")
public class FriendListChatServer  {
		
	private static Map<String, Set<Session>> friendListSessions = Collections.synchronizedMap(new HashMap<String, Set<Session>>());
	private static Map<String, Set<Session>> groupBuySessions = Collections.synchronizedMap(new HashMap<String, Set<Session>>());	
	
	private static final Map<String, Session> all = Collections.synchronizedMap(new HashMap<String, Session>());
	private static final Map<String, String> log = Collections.synchronizedMap(new HashMap<String, String>());
		@OnOpen
		public void onOpen(@PathParam("myName") String myName, Session userSession) throws IOException {
			
			if (!myName.isEmpty()) {
				
				all.put(myName, userSession);
				
				if(log.containsKey(myName)){
					String message = log.get(myName);
					
					all.get(myName).getAsyncRemote().sendText(message);
					log.remove(myName);
					System.out.println("已將訊息送給會員 : "+ myName);
				}
				
			}
			
			
			if ("MB".equals(myName.substring(0, 2))) {
				
				if (friendListSessions.containsKey(myName)) {
					
					friendListSessions.get(myName).add(userSession);
					
				} else {
					
					Set<Session> userSessions = Collections.synchronizedSet(new HashSet<Session>());
					userSessions.add(userSession);
					friendListSessions.put(myName, userSessions);
					
				}
				
				System.out.println("FriendList" + userSession.getId() + ": 已連線");
				System.out.println("FriendList" + myName + ": 已連線");
				
			} else if ("MO".equals(myName.substring(0, 2))) {
				
				if (groupBuySessions.containsKey(myName)) {
					
					groupBuySessions.get(myName).add(userSession);
					
				} else {
					
					Set<Session> userSessions = Collections.synchronizedSet(new HashSet<Session>());
					userSessions.add(userSession);
					groupBuySessions.put(myName, userSessions);
					
				}
				
				System.out.println("GroupBuy" + userSession.getId() + ": 已連線");
				System.out.println("GroupBuy" + myName + ": 已連線");
				
			}
			
		}
		
		public static void OneByOne(String num,String webSocketAction ,String message){
			
			JSONObject sendOrderMessage = new JSONObject();
			try {			
				sendOrderMessage.put("action", webSocketAction);
				sendOrderMessage.put("message", message);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if(all.containsKey(num)){
				System.out.println("會員 ： "+ num + "在線上 !");
				all.get(num).getAsyncRemote().sendText(sendOrderMessage.toString());
				System.out.println("會員 ： "+ num +"收到訊息 : " + message);
			}else{
				System.out.println("會員"+ num + "不在線上 ");
				log.put(num, sendOrderMessage.toString());
				System.out.println("會員 ： "+ num +" 不在線上 已將訊息儲存 ");
			}
		
	}
	
		public static void onMessage(String who, String message) {

			// ************傳送訊息給所有會員************
			
			JSONObject sendCouponMessage = new JSONObject();
			try {
				sendCouponMessage.put("message", message);
				sendCouponMessage.put("action","couponInfo");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if (who.equals("MB")) {
				System.out.println("進入會員推~");
				for (String mb : all.keySet()) {
					if (mb.contains("MB")) {
						if (all.get(mb).isOpen()) {
							all.get(mb).getAsyncRemote().sendText(sendCouponMessage.toString());
						
						}
					}
				}
			}

			// ************傳送訊息給所有店家************
			else if (who.equals("ST")) {
				System.out.println("進入店家推~");
				for (String mb : all.keySet()) {
					if (mb.contains("ST")) {
						if (all.get(mb).isOpen()) {
							all.get(mb).getAsyncRemote().sendText(sendCouponMessage.toString());
						}
					}
				}
			}

			// for (Session session : allSessions) {
			// if (session.isOpen())
			// session.getAsyncRemote().sendText(message);
			// }
			
		}
		
		
		@OnMessage
		synchronized public static void onMessage(Session userSession, String message) {
			
			JSONObject jsonObj = null;
			
			try {
				
				jsonObj = new JSONObject(message);
				
				if ("talkMessage".equals((String) jsonObj.get("action"))) {
					
//					JSONObject sendJsonObj = new JSONObject();
					
					MemberProfileService mps = new MemberProfileService();
					
					boolean isOnline = false;
					
					String senderName = mps.getMyProfile((String) jsonObj.get("senderNumber")).getMem_name();
					
					jsonObj.put("senderName", senderName);
					
//					sendJsonObj.put("action", "talkMessage");
//					sendJsonObj.put("senderName", senderName);
//					sendJsonObj.put("senderNumber", (String)jsonObj.get("senderNumber"));
//					sendJsonObj.put("targetNumber", (String)jsonObj.get("targetNumber"));
//					sendJsonObj.put("message", (String)jsonObj.get("message"));
					
					if (friendListSessions.containsKey((String) jsonObj.get("targetNumber"))) {
						
						isOnline = true;
						
						for (Session targetSession : friendListSessions.get((String)jsonObj.get("targetNumber"))) {
							
							if (targetSession.isOpen()) {
								
								synchronized(targetSession){
									
									targetSession.getAsyncRemote().sendText(jsonObj.toString());
									
								}
									
							}
							
						}
						
					}
						
//					if (friendListSessions.containsKey((String) jsonObj.get("senderNumber"))) {
						
						for (Session senderSession : friendListSessions.get((String)jsonObj.get("senderNumber"))) {
							
							if (senderSession.isOpen()) {
								
								synchronized(senderSession){
									
									senderSession.getAsyncRemote().sendText(jsonObj.toString());
								}
								
								if (!isOnline) {
									
									JSONObject sendTargetOffline = new JSONObject();
									
									sendTargetOffline.put("action", "sendIsRead");
									sendTargetOffline.put("senderNumber", (String)jsonObj.get("targetNumber"));
									sendTargetOffline.put("message", "N");
									
									try {
										Thread.sleep(200);
									} catch (InterruptedException e) {
										e.printStackTrace();
									}
									
									synchronized(senderSession){
										
										senderSession.getAsyncRemote().sendText(sendTargetOffline.toString());
									}
									
								}
								
//								if (!isOnline) {
//									
//									JSONObject sendJsonObj = new JSONObject();
//									
//									sendJsonObj.put("action", "sendIsRead");
//									sendJsonObj.put("senderNumber", (String)jsonObj.get("senderNumber"));
//									sendJsonObj.put("message", "N");
//									
//									synchronized(senderSession){
//										
//										senderSession.getAsyncRemote().sendText(jsonObj.toString());
//										
//									}
//									
//								}
									
							}
							
						}
						
//					}
					
					System.out.println("Message received: " + (String)jsonObj.get("message"));
					
				} else if ("sendIsRead".equals((String) jsonObj.get("action"))) {
					
					if (friendListSessions.containsKey((String) jsonObj.get("targetNumber"))) {
						
//						JSONObject sendJsonObj = new JSONObject();
						
//						sendJsonObj.put("action", "sendIsRead");
//						sendJsonObj.put("senderNumber", (String)jsonObj.get("senderNumber"));
//						sendJsonObj.put("message", (String)jsonObj.get("message"));
						
						for (Session targetSession : friendListSessions.get((String)jsonObj.get("targetNumber"))) {
							
							if (targetSession.isOpen()) {
									
								synchronized(targetSession){
									
									targetSession.getAsyncRemote().sendText(jsonObj.toString());
									
								}
									
							}
							
						}
						
					}
					
				} else if ("checkOut".equals((String) jsonObj.get("action"))) {
					
					if (groupBuySessions.containsKey((String) jsonObj.get("merOr"))) {
						
//						JSONObject sendJsonObj = new JSONObject();
//						
//						sendJsonObj.put("action", "getcheckOut");
						
						for (Session targetSession : groupBuySessions.get((String) jsonObj.get("merOr"))) {
							
							if (targetSession.isOpen()) {
									
								synchronized(targetSession){
									
									targetSession.getAsyncRemote().sendText(jsonObj.toString());
									
								}
									
							}
							
						}
						
					}
					
				} else if ("groupBuyTalk".equals((String) jsonObj.get("action"))) {
					
					if (groupBuySessions.containsKey((String) jsonObj.get("merOr"))) {
						
//						JSONObject sendJsonObj = new JSONObject();
						
//						MemberProfileService mps = new MemberProfileService();
						
//						String senderName = mps.getMyProfile((String)jsonObj.get("senderNumber")).getMem_name();
						
						jsonObj.put("mySessionID", userSession.getId());
//						sendJsonObj.put("action", "groupBuyTalk");
//						sendJsonObj.put("senderName", senderName);
//						sendJsonObj.put("mySessionID", userSession.getId());
//						sendJsonObj.put("senderNumber", (String)jsonObj.get("senderNumber"));
//						sendJsonObj.put("message", (String)jsonObj.get("message"));
//						sendJsonObj.put("merOr", (String)jsonObj.get("merOr"));
						
						for (Session targetSession : groupBuySessions.get((String)jsonObj.get("merOr"))) {
								
							if (targetSession.isOpen()) {
										
								synchronized(targetSession){
										
									targetSession.getAsyncRemote().sendText(jsonObj.toString());
										
								}
										
							}
							
						}
						
					}
					
				} else if ("groupBuysendIsRead".equals((String) jsonObj.get("action"))) {
					
					if (groupBuySessions.containsKey((String) jsonObj.get("merOr"))) {
						
//						JSONObject sendJsonObj = new JSONObject();
						
//						sendJsonObj.put("action", "groupBuysendIsRead");
//						sendJsonObj.put("message", (String)jsonObj.get("message"));
						
						for (Session targetSession : groupBuySessions.get((String) jsonObj.get("merOr"))) {
							
							if (!jsonObj.has("targetSessionID")) {
								
								synchronized(targetSession){
									
									targetSession.getAsyncRemote().sendText(jsonObj.toString());
									
								}
								
							} else if (targetSession.getId().equals((String) jsonObj.get("targetSessionID")) && targetSession.isOpen()) {
								
								synchronized(targetSession){
								
									targetSession.getAsyncRemote().sendText(jsonObj.toString());
									
								}
									
							}
							
						} 
						
					}
					
				} else if ("GroupBuySendImg".equals((String) jsonObj.get("action"))) {
					
					if (groupBuySessions.containsKey((String) jsonObj.get("merOr"))) {
						
						jsonObj.put("action", "groupBuyTalk");
						jsonObj.put("mySessionID", userSession.getId());
						jsonObj.put("sendImg", jsonObj.get("message"));
						jsonObj.remove("message");
						
						for (Session targetSession : groupBuySessions.get((String) jsonObj.get("merOr"))) {
							
							if (targetSession.isOpen()) {
								
								synchronized(targetSession){
									
									targetSession.getAsyncRemote().sendText(jsonObj.toString());
									
								}
								
							}
							
						} 
						
					}
					
				} else if ("sendImg".equals((String) jsonObj.get("action"))) {
					
					MemberProfileService mps = new MemberProfileService();
					
					String senderName = mps.getMyProfile((String) jsonObj.get("senderNumber")).getMem_name();
					
					jsonObj.put("action", "talkMessage");
					jsonObj.put("senderName", senderName);
					jsonObj.put("sendImg", jsonObj.get("message"));
					jsonObj.remove("message");
						
					if (friendListSessions.containsKey((String) jsonObj.get("targetNumber"))) {
						
						for (Session friendSession : friendListSessions.get((String) jsonObj.get("targetNumber"))) {
									
							if (friendSession.isOpen()) {
										
								friendSession.getAsyncRemote().sendText(jsonObj.toString());
										
							}
								
						}
						
					}
						
					for (Session friendSession : friendListSessions.get((String) jsonObj.get("senderNumber"))) {
									
						if (friendSession.isOpen()) {
										
							friendSession.getAsyncRemote().sendText(jsonObj.toString());
										
						}
								
					}
					
				}
				
			} catch (JSONException e) {
				e.printStackTrace();
			}
			
		}
		
		@OnError
		public void onError(Session userSession, Throwable e){
			e.printStackTrace();
		}
		
		@OnClose
		public void onClose(Session userSession, CloseReason reason, @PathParam("myName") String myName) {
			
			all.remove(myName);
			System.out.println( myName +" 已離線");
			
			if (friendListSessions.containsKey(myName)) {
				
				if(friendListSessions.get(myName).contains(userSession)){
					
					friendListSessions.get(myName).remove(userSession);
					
					System.out.println("ID:" + userSession.getId() + "=>Disconnected: " + Integer.toString(reason.getCloseCode().getCode()));
					
				}
				
			}
			
			
			if (groupBuySessions.containsKey(myName)) {
				
				if(groupBuySessions.get(myName).contains(userSession)){
					
					groupBuySessions.get(myName).remove(userSession);
					
					System.out.println("ID:" + userSession.getId() + "=>Disconnected: " + Integer.toString(reason.getCloseCode().getCode()));
					
				}
				
			}
			
		}
		
		
	public static Map<String, Set<Session>> getFriendListSessions() {
		return friendListSessions;
	}
	
	public static Map<String, Set<Session>> getGroupBuySessions() {
		return groupBuySessions;
	}
	 
}
