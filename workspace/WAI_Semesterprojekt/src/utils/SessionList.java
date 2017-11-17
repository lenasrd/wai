package utils;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;

import mvc.model.UserBean;



public class SessionList {

	private static SessionList sessionList = new SessionList();

	private static Map<String, UserBean> sessionMap = new HashMap<String, UserBean>();
	
	private SessionList() {
		
	}

	
	public static SessionList getInstance() {
		return sessionList;
	}
	
	
	public UserBean addSession(String sessionID, UserBean user) {
		System.out.println("Session " + sessionID + " alias " + user.getUsername() +" logged in");
		return sessionMap.put(sessionID, user);
	}
	
	
	public UserBean removeSession(String sessionID) {
		// TODO System.out.println("Session " + sessionID + " alias " + getUser(sessionID).getUsername() +" logged out");
		return sessionMap.remove(sessionID);
	}

	
	public UserBean getUser(String sessionID) {
		return sessionMap.get(sessionID);
	}
	
	
	public UserBean getUser(HttpServletRequest request) {
		HttpSession session 	= request.getSession(false);
		if(session == null) {
			return null;
		}
		return getUser(session.getId());
	}

	
	
	public void sessionCreated(HttpSessionEvent sessionEvent) {
		System.out.println("Session " + sessionEvent.getSession().getId() + " created");
	}

	
	
	public void sessionDestroyed(HttpSessionEvent sessionEvent) {
		String sessionID 	= sessionEvent.getSession().getId();
		removeSession(sessionID);
		System.out.println("Session " + sessionID + " deleted");
	}
}
