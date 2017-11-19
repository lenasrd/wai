package dao.UserDao;

public class UserNotSavedException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	public UserNotSavedException() {
		super("User konnte nicht gespeichert werden!");
	}
}