package mvc.model;

public class UserBean {
	
	public static final int PERMISSION_LEVEL_ADMIN 	= 1;
	public static final int PERMISSION_LEVEL_USER 	= 2;
	
	private long 	id;
	private String 	username;
	private String	password;
	private int 	permissionLevel;
	private int[]	cams;
	
	public UserBean() {
		this.id 				= -1;
		this.username 			= null;
		this.password 			= null;
		this.permissionLevel 	= PERMISSION_LEVEL_USER;
		this.cams 				= null;
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
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}

	public int getPermissionLevel() {
		return permissionLevel;
	}


	public void setPermissionLevel(int permissionLevel) {
		this.permissionLevel = permissionLevel;
	}


	public int[] getCams() {
		return cams;
	}


	public void setCams(int[] cams) {
		this.cams = cams;
	}
}