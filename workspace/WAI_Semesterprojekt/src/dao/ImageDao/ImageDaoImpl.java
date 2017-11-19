package dao.ImageDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import utils.JNDIFactory;

import mvc.model.ImageBean;



public class ImageDaoImpl implements ImageDao {
	
	final JNDIFactory jndi = JNDIFactory.getInstance();

	@Override
	public void delete(Integer id) {
		
		if (id == null)
			throw new IllegalArgumentException("id can not be null");
		
		Connection connection = null;		
		try {
			connection = jndi.getConnection("jdbc/WAI_DB");		
			PreparedStatement pstmt = connection.prepareStatement("delete from images where id = ?");
			pstmt.setInt(1, id);
			pstmt.executeUpdate();			
		} catch (Exception e) {
			throw new ImageNotDeletedException(id);
		} finally {
			closeConnection(connection);
		}
	}

	@Override
	public ImageBean get(Integer id) {
		
		if (id == null)
			throw new IllegalArgumentException("id can not be null");
		
		Connection connection = null;		
		try {
			connection = jndi.getConnection("jdbc/WAI_DB");			
			PreparedStatement pstmt = connection.prepareStatement("select id, camId, path, timestamp from images where id = ?");
			pstmt.setInt(1, id);
			ResultSet rs = pstmt.executeQuery();							
			if (rs.next()) {
				ImageBean image = new ImageBean();
				image.setId(rs.getInt("id"));
				image.setCamId(rs.getInt("camId"));
				image.setPath(rs.getString("path"));
				image.setTimestamp(rs.getInt("timestamp"));
				return image;
			} else {
				throw new ImageNotFoundException(id);
			}			
		} catch (Exception e) {
			throw new ImageNotFoundException(id);
		} finally {	
			closeConnection(connection);
		}
	}

	@Override
	public void save(ImageBean image) {
		
		if (image == null)
			throw new IllegalArgumentException("image can not be null");
		
		Connection connection = null;		
		try {
			connection = jndi.getConnection("jdbc/WAI_DB");			
			if (image.getId() == null) {
				PreparedStatement pstmt = connection.prepareStatement("insert into images (camId, path, timestamp) values (?,?,?)");
				pstmt.setInt(1, image.getCamId());
				pstmt.setString(2, image.getPath());
				pstmt.setInt(3, image.getTimestamp());
				pstmt.executeUpdate();
			} else {
				PreparedStatement pstmt = connection.prepareStatement("update images set camId = ?, path = ?, timestamp = ? where id = ?");
				pstmt.setInt(1, image.getCamId());
				pstmt.setString(2, image.getPath());
				pstmt.setInt(3, image.getTimestamp());
				pstmt.executeUpdate();
			}			
		} catch (Exception e) {
			throw new ImageNotSavedException();
		} finally {
			closeConnection(connection);
		}
	}	

	@Override
	public List<ImageBean> list() {
		
		List<ImageBean> imageList = new ArrayList<ImageBean>();
		
		Connection connection = null;		
		try {
			connection = jndi.getConnection("jdbc/WAI_DB");			
			
				PreparedStatement pstmt = connection.prepareStatement("select id, camId, path, timestamp from images");				
				ResultSet rs = pstmt.executeQuery();
								
				while (rs.next()) {
					ImageBean image = new ImageBean();
					pstmt.setInt(1, image.getCamId());
					pstmt.setString(2, image.getPath());
					pstmt.setInt(3, image.getTimestamp());
					imageList.add(image);
				}			
			
			return imageList;
		} catch (Exception e) {
			throw new ImageNotFoundException();
		} finally {	
			closeConnection(connection);
		}
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