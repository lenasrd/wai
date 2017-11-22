package dao.UserDao;

import java.sql.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


import utils.JNDIFactory;

import mvc.model.UserBean;

public class UserDaoImpl implements UserDao{
	
	final JNDIFactory jndi = JNDIFactory.getInstance();


	@Override
	public void save(UserBean user) {
		// TODO Auto-generated method stub
		
		if (user == null) {
			throw new IllegalArgumentException("user can not be null");
		}
		
		Connection connection = null;
		try {
			connection = jndi.getConnection("jdbc/WAI_DB");		
			PreparedStatement pstmt;
			// neuer user
			if (user.getId() == UserBean.UNDEFINED) {
				
				if (user.getUsername() == null || user.getPassword() == null) {
					throw new IllegalArgumentException("username and userpassword can not be null");
				}
				
				pstmt = connection.prepareStatement(
										"insert into users (id, name, password, permission, cams) "
										+ "values (?,?,?,?,?)");
				
				Array array = connection.createArrayOf("Integer[]", user.getCams().toArray());
				
				pstmt.setInt(1, user.getId());
				pstmt.setString(2, user.getUsername());
				pstmt.setString(3, user.getPassword());
				pstmt.setInt(4, user.getPermissionLevel());
				pstmt.setArray(5, array);
				pstmt.executeUpdate();
				
			// bereits bestehender user
			} else {
				
				if (user.getUsername() == null) {
					throw new IllegalArgumentException("username can not be null");
				}
				// kompletter update
				if(user.getPassword() != null) {
					pstmt = connection.prepareStatement("update users "
							+ "set name = ?, password = ?, permission = ?, cams = ? where id = ?");
				
					Array array = connection.createArrayOf("Integer[]", user.getCams().toArray());

					pstmt.setString(1, user.getUsername());
					pstmt.setString(2, user.getPassword());
					pstmt.setInt(3, user.getPermissionLevel());
					pstmt.setArray(4, array);
					pstmt.setInt(5, user.getId());
				}
				// nur Kamera-Vektor updaten
				else {
					pstmt = connection.prepareStatement("update users "
							+ "set Cams = ? where id = ?");
					Array array = connection.createArrayOf("Integer[]", user.getCams().toArray());

					pstmt.setArray(1, array);
					pstmt.setInt(2, user.getId());
					
				}
			}
			
			pstmt.executeUpdate();		
			
		} catch(Exception e) {
			throw new UserNotSavedException();
		} finally {
			closeConnection(connection);
		}
	}

	@Override
	public void delete(Integer id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public UserBean get(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserBean get(String username) {
		
		if (username == null)
			throw new IllegalArgumentException("image can not be null");
		
		Connection connection = null;	
		
		try {
			connection = jndi.getConnection("jdbc/WAI_DB");
			PreparedStatement pstmt = connection.prepareStatement("select "
							+ "id, name, password, permission, cams "
							+ "from users where name = ?");

			pstmt.setString(1, username);
			ResultSet rs = pstmt.executeQuery();							
			if (rs.next()) {
				UserBean user = new UserBean();
				
				user.setId(rs.getInt("id"));
				user.setUsername(rs.getString("name"));
				user.setPassword(rs.getString("password"));
				user.setPermissionLevel(rs.getInt("permission"));
				
				List<Integer> camList = new ArrayList<Integer>();
				
				if(rs.getArray("cams") != null) {
					camList = Arrays.asList((Integer[])rs.getArray("cams").getArray());
				}
				user.setCams(camList);
				return user;
			} else {
				throw new UserNotFoundException(username);
			}	
		} catch (Exception e) {
			throw new UserNotFoundException(username);
		} finally {
			closeConnection(connection);
		}
	}

	@Override
	public List<UserBean> list() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	private void closeConnection(Connection connection) {
		if (connection != null) {
			try {
				connection.close();
				connection = null;
			} catch (SQLException e) {
				// nothing to do
				e.printStackTrace();
			}				
		}
	}

}