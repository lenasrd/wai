package dao.UserDao;

public class UserNotDeletedException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public UserNotDeletedException(Integer id) {
		super("User mit der Id " + id + " konnte nicht gelöscht werden!");
	}
}