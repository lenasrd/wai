package dao.UserDao;

import java.util.List;
import mvc.model.UserBean;

public interface UserDao {
	public void save(UserBean user);
	public void delete(Integer id);
	public UserBean get(Integer id);
	public UserBean get(String username);
	public List<UserBean> list();
}