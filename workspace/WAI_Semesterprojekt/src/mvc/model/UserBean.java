package mvc.model;

import java.util.ArrayList;
import java.util.List;

public class UserBean {
	
	public static final int PERMISSION_LEVEL_ADMIN 	= 1;
	public static final int PERMISSION_LEVEL_USER 	= 2;
	
	public static final int UNDEFINED = -1;
	
	private int 			id;
	private String 			username;
	private String			password;
	private int 			permissionLevel;
	private List<Integer>	cams;
	
	public UserBean() {
		this.id 				= UNDEFINED;
		this.username 			= null;
		this.password 			= null;
		this.permissionLevel 	= PERMISSION_LEVEL_USER;
		this.cams 				= new ArrayList<Integer>();
	}

	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}

	public int getPermissionLevel() {
		return permissionLevel;
	}


	public void setPermissionLevel(int permissionLevel) {
		this.permissionLevel = permissionLevel;
	}


	public List<Integer> getCams() {
		return cams;
	}


	public void setCams(List<Integer> cams) {
		this.cams = cams;
	}
	
	public int getPERMISSION_LEVEL_USER() {
		return PERMISSION_LEVEL_USER;
	}
	
	public int getPERMISSION_LEVEL_ADMIN() {
		return PERMISSION_LEVEL_ADMIN;
	}
	
	public String decodePermission(int permissionLevel) {
		switch(permissionLevel) {
		case PERMISSION_LEVEL_ADMIN:
			return "admin";
		case PERMISSION_LEVEL_USER:
			return "user";
		default:
			return "unknown permission";
		}
	}
	
	
	public String toString() {
		return ("id: " + id + ", username: " + username + 
				" permission: " + decodePermission(permissionLevel) + 
				", cams: " + cams + "\n");
	}
	
}
