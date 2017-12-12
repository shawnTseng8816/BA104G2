package com.order_master.model;

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
import javax.websocket.CloseReason;

@ServerEndpoint("/OrderMasterChatServer/{myName}/{merOr}")
public class OrderMasterChatServer  {
		
	private static Map<String, ArrayList<Session>> groupBuySessions = Collections.synchronizedMap(new HashMap<String, ArrayList<Session>>());
		
		@OnOpen
		public void onOpen(@PathParam("myName") String myName, Session userSession) throws IOException {
			
			if (groupBuySessions.containsKey(myName)) {
				
				groupBuySessions.get(myName).add(userSession);
				
			} else {
				
				ArrayList<Session> userSessions = new ArrayList<Session>();
				userSessions.add(userSession);
				groupBuySessions.put(myName, userSessions);
				
			}
			
			System.out.println(userSession.getId() + ": 已連線");
			System.out.println(myName + ": 已連線");
		}
		
		@OnMessage
		public void onMessage(Session userSession, String message) {
			
			JSONObject jsonObj = null;
			
			try {
				
				jsonObj = new JSONObject(message);
				
				if ("sendMessages".equals(jsonObj.get("action"))) {
					
					if (groupBuySessions.containsKey((String) jsonObj.get("targetNumber"))) {
						
						JSONObject sendJsonObj = new JSONObject();
						
						MemberProfileService mps = new MemberProfileService();
						
						String senderName = mps.getMyProfile((String)jsonObj.get("senderNumber")).getMem_name();
						
						sendJsonObj.put("action", "talkMessage");
						sendJsonObj.put("senderName", senderName);
						sendJsonObj.put("senderNumber", (String)jsonObj.get("senderNumber"));
						sendJsonObj.put("targetNumber", (String)jsonObj.get("targetNumber"));
						sendJsonObj.put("message", (String)jsonObj.get("message"));
						
						for (Session targetSession : groupBuySessions.get((String)jsonObj.get("targetNumber"))) {
							
							if (targetSession.isOpen()) {
									
								targetSession.getAsyncRemote().sendText(sendJsonObj.toString());
									
							}
							
						}
						
						for (Session senderSession : groupBuySessions.get((String)jsonObj.get("senderNumber"))) {
							
							if (senderSession.isOpen()) {
									
								senderSession.getAsyncRemote().sendText(sendJsonObj.toString());
									
							}
							
						}
						
					}
					
					System.out.println("Message received: " + (String)jsonObj.get("message"));
					
				} else if ("sendIsRead".equals(jsonObj.get("action"))) {
					
					if (groupBuySessions.containsKey((String) jsonObj.get("targetNumber"))) {
						
						JSONObject sendJsonObj = new JSONObject();
						
						sendJsonObj.put("action", "sendIsRead");
						sendJsonObj.put("senderNumber", (String)jsonObj.get("senderNumber"));
						sendJsonObj.put("message", (String)jsonObj.get("message"));
						
						for (Session targetSession : groupBuySessions.get((String)jsonObj.get("targetNumber"))) {
							
							if (targetSession.isOpen()) {
									
								targetSession.getAsyncRemote().sendText(sendJsonObj.toString());
									
							}
							
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
			
			if (groupBuySessions.containsKey(myName)) {
				
				if(groupBuySessions.get(myName).contains(userSession)){
					
					groupBuySessions.get(myName).remove(userSession);
					
					System.out.println("ID:" + userSession.getId() + "=>Disconnected: " + Integer.toString(reason.getCloseCode().getCode()));
					
				}
				
			}
			
		}
		
		
	public static Map<String, ArrayList<Session>> getFriendListSessions() {
		return groupBuySessions;
	}
	 
}
