package dao.UserDao;

public class UserDaoFactory {
	
	private static UserDaoFactory instance = new UserDaoFactory();
	
	private UserDaoFactory() {		
	}
	
	public static UserDaoFactory getInstance() {
		return instance;
	}
	
	public UserDao getImageDao() {
		return new UserDaoImpl();
	}
}