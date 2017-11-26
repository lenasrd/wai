package dao.UserDao;

import java.sql.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import dao.CamDao.CamNotFoundException;
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
				
				
				
				user.setId(getNewId());
				pstmt.setInt(1, user.getId());
				pstmt.setString(2, user.getUsername());
				pstmt.setString(3, user.getPassword());
				pstmt.setInt(4, user.getPermissionLevel());
				
				if(user.getCams().size() > 0) {
					pstmt.setArray(5, connection.createArrayOf("Integer[]", user.getCams().toArray()));
				} else {
					pstmt.setNull(5, Types.ARRAY);
				}
				
			// bereits bestehender user
			} else {
				
				if (user.getUsername() == null) {
					throw new IllegalArgumentException("username can not be null");
				}
				// kompletter update
				if(user.getPassword() != null) {
					pstmt = connection.prepareStatement("update users "
							+ "set name = ?, password = ?, permission = ?, cams = ? where id = ?");
				


					pstmt.setString(1, user.getUsername());
					pstmt.setString(2, user.getPassword());
					pstmt.setInt(3, user.getPermissionLevel());
					pstmt.setInt(5, user.getId());
					
					if(user.getCams().size() > 0) {
						pstmt.setArray(5, connection.createArrayOf("Integer[]", user.getCams().toArray()));
					} else {
						pstmt.setNull(4, Types.ARRAY);
					}
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
			System.out.println(e.getMessage());
			throw new UserNotSavedException();
		} finally {
			closeConnection(connection);
		}
	}

	@Override
	public void delete(Integer id) {

		Connection connection = null;	
		
		try {
			connection = jndi.getConnection("jdbc/WAI_DB");
			PreparedStatement pstmt = connection.prepareStatement("DELETE FROM users "
							+ "WHERE id = ?");
			
			pstmt.setInt(1, id);
			pstmt.executeQuery();

		} catch (Exception e) {
			throw new UserNotDeletedException(id);
		} finally {
			closeConnection(connection);
		}
	}
	
	

	@Override
	public UserBean get(Integer id) {

		Connection connection = null;	
		
		try {
			connection = jndi.getConnection("jdbc/WAI_DB");
			PreparedStatement pstmt = connection.prepareStatement("select "
							+ "id, name, password, permission, cams "
							+ "from users where id = ?");

			pstmt.setInt(1, id);
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
				throw new UserNotFoundException(id);
			}	
		} catch (Exception e) {
			throw new UserNotFoundException(id);
		} finally {
			closeConnection(connection);
		}
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

		Connection connection = null;
		List<UserBean> userList = new LinkedList<UserBean>();
		
		try {
			connection = jndi.getConnection("jdbc/WAI_DB");
			PreparedStatement pstmt = connection.prepareStatement("select "
							+ "id, name, password, permission, cams "
							+ "from users");

			ResultSet rs = pstmt.executeQuery();	
			
			
			while(rs.next()) {

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
				userList.add(user);
				System.out.println("user added");

			}
		} catch (Exception e) {
			System.out.println(e.getMessage());;
		} finally {
			closeConnection(connection);
		}	
		return userList;
	}
	
	
	
	
	public int getNewId() {
		int id = 0;
		Connection connection = null;	
		try {
			connection = jndi.getConnection("jdbc/WAI_DB");
			PreparedStatement pstmt = connection.prepareStatement(
							"SELECT id FROM users ORDER BY id DESC LIMIT 1");
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				id = rs.getInt("id");
			}
			id++;
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
			id = 0;
		} finally {
			closeConnection(connection);
		}
		return id;
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
