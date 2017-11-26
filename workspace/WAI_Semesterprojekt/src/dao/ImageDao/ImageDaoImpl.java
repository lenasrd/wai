package dao.ImageDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
			PreparedStatement pstmt = connection.prepareStatement("delete from image where id = ?");
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
			PreparedStatement pstmt = connection.prepareStatement("select id, cam_id, path, thumbpath from image where id = ?");
			pstmt.setInt(1, id);
			ResultSet rs = pstmt.executeQuery();							
			if (rs.next()) {
				ImageBean image = new ImageBean();
				image.setId(rs.getInt("id"));
				image.setCamId(rs.getInt("cam_id"));
				image.setPath(rs.getString("path"));
				image.setThumbPath(rs.getString("thumbpath"));
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
				PreparedStatement pstmt = connection.prepareStatement("insert into image (cam_id, path, timestamp) values (?,?,?)");
				pstmt.setInt(1, image.getCamId());
				pstmt.setString(2, image.getPath());
				pstmt.setInt(3, image.getTimestamp());
				pstmt.executeUpdate();
			} else {
				PreparedStatement pstmt = connection.prepareStatement("update image set cam_id = ?, path = ?, timestamp = ? where id = ?");
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
	public List<ImageBean> listAllImages() {
		
		List<ImageBean> imageList = new ArrayList<ImageBean>();
		
		Connection connection = null;		
		try {
			connection = jndi.getConnection("jdbc/WAI_DB");			
			
				PreparedStatement pstmt = connection.prepareStatement("select id, cam_id, path, timestamp from image");				
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
	

	@Override
	public List<ImageBean> listIntervalImages(String year, String month, String day, String startTime, String endTime){
		
		List<ImageBean> imageList = new ArrayList<ImageBean>();
		
		Connection connection = null;		
		try {
			connection = jndi.getConnection("jdbc/WAI_DB");
			
			SimpleDateFormat sdf = new SimpleDateFormat("d-M-yy hh:mm:ss");
			
			String dateInString = day + month + year + " " + startTime + ":00";
			Date startDate = sdf.parse(dateInString);
			long startUnix = startDate.getTime() / 1000;
			
			dateInString = day + month + year + " " + endTime + ":00";
			Date endDate = sdf.parse(dateInString);
			long endUnix = endDate.getTime() / 1000;
			
			PreparedStatement pstmt = connection.prepareStatement("select id, cam_id, path, timestamp from image WHERE timestamp >= startUnix AND timestamp < endUnix");				
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
	

	public ImageBean getLatestRecordFromCam(int cam_id) {
		
		ImageBean image = null;
		
		Connection connection = null;		
		try {
			connection = jndi.getConnection("jdbc/WAI_DB");
			
			PreparedStatement pstmt = connection.prepareStatement(
								"SELECT id, cam_id, path, thumbpath, year, month, day, hour " + 
								"FROM image WHERE cam_id = ? " +
								"ORDER BY year, month, day, hour DESC LIMIT 1");
			pstmt.setInt(1, cam_id);
			ResultSet rs = pstmt.executeQuery();
							
			if (rs.next()) {
				image = new ImageBean();
				image.setId(rs.getInt("id"));
				image.setPath(rs.getString("path"));
				image.setThumbPath(rs.getString("thumbpath"));
				image.setCamId(rs.getInt("cam_id"));
			}	
			return image;
		} catch (Exception e) {
			System.out.println(e.getMessage());
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