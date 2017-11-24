package dao.CamDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.LinkedList;
import java.util.List;

import mvc.model.CamBean;
import utils.JNDIFactory;

public class CamDaoImpl implements CamDao{
	
	final JNDIFactory jndi = JNDIFactory.getInstance();

	@Override
	public void save(CamBean cam) {
		
		// TODO Auto-generated method stub
		
		if (cam == null) {
			throw new IllegalArgumentException("cam can not be null");
		}
		
		Connection connection = null;
		
		try {
			connection = jndi.getConnection("jdbc/WAI_DB");		
			PreparedStatement pstmt;
			
			// neue cam
			if (cam.getId() == CamBean.UNDEFINED) {
				
				if (cam.getUrl() == null) {
					throw new IllegalArgumentException("cam url can not be null");
				}
				
				pstmt = connection.prepareStatement(
										"insert into cam (id, url, name) "
										+ "values (?,?,?)");
				
				cam.setId(getNewId());
				pstmt.setInt(1, cam.getId());
				pstmt.setString(2, cam.getUrl());
				pstmt.setString(3, cam.getName());

				
			// bereits bestehende cam
			} else {
				
				if (cam.getUrl() == null) {
					throw new IllegalArgumentException("cam url can not be null");
				}

				pstmt = connection.prepareStatement("update cam "
						+ "set url = ?, name = ? where id = ?");
				
				pstmt.setString(1, cam.getUrl());
				pstmt.setString(2, cam.getName());
				pstmt.setInt(3, cam.getId());
			}
			
			pstmt.executeUpdate();		
			
		} catch(Exception e) {
			System.out.println(e.getMessage());
			throw new CamNotSavedException();
		} finally {
			closeConnection(connection);
		}
	}

	
	
	

	@Override
	public void delete(Integer id) {

		Connection connection = null;	
		
		try {
			connection = jndi.getConnection("jdbc/WAI_DB");
			PreparedStatement pstmt = connection.prepareStatement("DELETE FROM cam "
							+ "WHERE id = ?");
			
			pstmt.setInt(1, id);
			pstmt.executeQuery();

		} catch (Exception e) {
			throw new CamNotDeletedException(id);
		} finally {
			closeConnection(connection);
		}
	}

	
	
	
	
	@Override
	public CamBean get(Integer id) {

		Connection connection = null;	
		
		try {
			connection = jndi.getConnection("jdbc/WAI_DB");
			PreparedStatement pstmt = connection.prepareStatement("select "
							+ "id, url, name "
							+ "from cam where id = ?");

			pstmt.setInt(1, id);
			ResultSet rs = pstmt.executeQuery();							
			if (rs.next()) {
				CamBean cam = new CamBean();
				
				cam.setId(rs.getInt("id"));
				cam.setName(rs.getString("name"));
				cam.setUrl(rs.getString("password"));
				
				return cam;
			} else {
				throw new CamNotFoundException(id);
			}	
		} catch (Exception e) {
			throw new CamNotFoundException(id);
		} finally {
			closeConnection(connection);
		}
	}

	
	
	@Override
	public List<CamBean> list() {
		
		Connection connection = null;
		List<CamBean> camList = new LinkedList<CamBean>();
		
		try {
			connection = jndi.getConnection("jdbc/WAI_DB");
			PreparedStatement pstmt = connection.prepareStatement("select "
							+ "id, url, name"
							+ "from cam");

			ResultSet rs = pstmt.executeQuery();	

			
			while(rs.next()) {

				CamBean cam = new CamBean();
				
				cam.setId(rs.getInt("id"));
				cam.setName(rs.getString("name"));
				cam.setUrl(rs.getString("url"));

				camList.add(cam);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());;
		} finally {
			closeConnection(connection);
		}	
		return camList;
	}

	@Override
	public int getNewId() {
		int id = 0;
		Connection connection = null;	
		try {
			connection = jndi.getConnection("jdbc/WAI_DB");
			PreparedStatement pstmt = connection.prepareStatement(
							"SELECT id FROM cam ORDER BY id DESC LIMIT 1");
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
