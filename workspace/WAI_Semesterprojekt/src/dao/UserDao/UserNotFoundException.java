package dao.UserDao;

public class UserNotFoundException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	public UserNotFoundException(String name) {
		super("User mit dem Namen " + name + " wurde nicht gefunden!");
	}
	
	public UserNotFoundException() {
		super("User können nicht aufgelistet werden!");
	}
}