package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jndi.JndiFactory;

import model.Image;

import exception.ImageNotDeletedException;
import exception.ImageNotFoundException;
import exception.ImageNotSavedException;

public class ImageDaoImpl implements ImageDao {
	
	final JndiFactory jndi = JndiFactory.getInstance();

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
	public Image get(Integer id) {
		
		if (id == null)
			throw new IllegalArgumentException("id can not be null");
		
		Connection connection = null;		
		try {
			connection = jndi.getConnection("jdbc/WAI_DB");			
			PreparedStatement pstmt = connection.prepareStatement("select id, camId, path, timestamp from images where id = ?");
			pstmt.setInt(1, id);
			ResultSet rs = pstmt.executeQuery();							
			if (rs.next()) {
				Image image = new Image();
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
	public void save(Image image) {
		
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
	public List<Image> list() {
		
		List<Image> imageList = new ArrayList<Image>();
		
		Connection connection = null;		
		try {
			connection = jndi.getConnection("jdbc/WAI_DB");			
			
				PreparedStatement pstmt = connection.prepareStatement("select id, camId, path, timestamp from images");				
				ResultSet rs = pstmt.executeQuery();
								
				while (rs.next()) {
					Image image = new Image();
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
