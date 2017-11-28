package dao.ImageDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
			PreparedStatement pstmt = connection.prepareStatement("select * from image where id = ?");
			pstmt.setInt(1, id);
			ResultSet rs = pstmt.executeQuery();							
			if (rs.next()) {
				ImageBean image = new ImageBean();
				image.setId(rs.getInt("id"));
				image.setCamId(rs.getInt("cam_id"));
				image.setPath(rs.getString("path"));
				image.setThumbPath(rs.getString("thumbpath"));
				image.setYear(Integer.valueOf(rs.getString("year")));
				image.setMonth(Integer.valueOf(rs.getString("month")));
				image.setDay(Integer.valueOf(rs.getString("day")));
				image.setHour(Integer.valueOf(rs.getString("hour")));
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

	public void save(ImageBean image, Integer id) {
		
		if (image == null)
			throw new IllegalArgumentException("image can not be null");
		
		Connection connection = null;		
		try {
			connection = jndi.getConnection("jdbc/WAI_DB");			
			if (image.getId() == null) {
				PreparedStatement pstmt = connection.prepareStatement("insert into image (id, cam_id, path, year, month, day, hour) values (?,?,?,?,?,?,?)");
				pstmt.setInt(1, id);
				pstmt.setInt(2, image.getCamId());
				pstmt.setString(3, image.getPath());
				pstmt.setInt(4, image.getYear());
				pstmt.setInt(5, image.getMonth());
				pstmt.setInt(6, image.getDay());
				pstmt.setInt(7, image.getHour());
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
			
				PreparedStatement pstmt = connection.prepareStatement("select id, cam_id, path, thumbpath, year, month, day, hour from image");				
				ResultSet rs = pstmt.executeQuery();
								
				while (rs.next()) {
					ImageBean image = new ImageBean();
					pstmt.setInt(1, image.getCamId());
					pstmt.setString(2, image.getPath());
					pstmt.setString(3, image.getThumbPath());
					pstmt.setInt(4, image.getYear());
					pstmt.setInt(5, image.getMonth());
					pstmt.setInt(6, image.getDay());
					pstmt.setInt(7, image.getHour());
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
	public List<ImageBean> listIntervalImages(Integer camId, String year, String month, String day, String startTime, String endTime){
		
		List<ImageBean> imageList = new ArrayList<ImageBean>();
		
		Connection connection = null;
		PreparedStatement pstmt = null;
		
		try {
			connection = jndi.getConnection("jdbc/WAI_DB");
			if (camId != null) {
				pstmt = connection.prepareStatement("select * from image where cam_id = ? and year = ? and month = ? and day = ? and hour >= ? and hour <= ?");
				pstmt.setInt(1, camId);
				pstmt.setInt(2, Integer.valueOf(year, 10));
				pstmt.setInt(3, Integer.valueOf(month, 10));
				pstmt.setInt(4, Integer.valueOf(day, 10));
				pstmt.setInt(5, Integer.valueOf(startTime, 10));
				pstmt.setInt(6, Integer.valueOf(endTime, 10));
			}
			else{
				pstmt = connection.prepareStatement("select * from image where year = ? and month = ? and day = ? and hour >= ? and hour <= ?");
				pstmt.setInt(1, Integer.valueOf(year, 10));
				pstmt.setInt(2, Integer.valueOf(month, 10));
				pstmt.setInt(3, Integer.valueOf(day, 10));
				pstmt.setInt(4, Integer.valueOf(startTime, 10));
				pstmt.setInt(5, Integer.valueOf(endTime, 10));
			}
			
			ResultSet rs = pstmt.executeQuery();							
			while (rs.next()) {
				ImageBean image = new ImageBean();
				image.setId(rs.getInt("id"));
				image.setCamId(rs.getInt("cam_id"));
				image.setPath(rs.getString("path"));
				image.setThumbPath(rs.getString("thumbpath"));
				image.setYear(Integer.valueOf(rs.getString("year")));
				image.setMonth(Integer.valueOf(rs.getString("month")));
				image.setDay(Integer.valueOf(rs.getString("day")));
				image.setHour(Integer.valueOf(rs.getString("hour")));
				imageList.add(image);
				System.out.println("available image added to list, id: "+image.getId().toString());
			} 		
		} catch (Exception e) {
			throw new ImageNotFoundException(camId);
		} finally {	
			closeConnection(connection);
		}
		return imageList;
	}
	
	public List<String> getYears(Integer cam_id){
		//read in available years
		JNDIFactory jndiFactory = JNDIFactory.getInstance();
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<String> years = new ArrayList<String>();
		
		try {
			connection = jndiFactory.getConnection("jdbc/WAI_DB");
			if (cam_id != null) {
				pstmt = connection.prepareStatement("select year from image where cam_id = "+cam_id.toString()+" order by year desc");
				rs = pstmt.executeQuery();
			}
			else {
				pstmt = connection.prepareStatement("select year from image order by year desc");
				rs = pstmt.executeQuery();
			}

			while (rs.next()) {
				if (!years.contains(String.valueOf(rs.getInt("year")))){
				years.add(String.valueOf(rs.getInt("year")));
				}
			}
		}
		
		catch(Exception e) {
			System.out.println(e.getMessage());
		}
		finally {
			if (connection != null)
				try {
					connection.close();
				} catch (Exception e) {
					e.printStackTrace();
				}

			if (pstmt != null)
				try {
					pstmt.close();
				} catch (Exception e) {
					e.printStackTrace();
				}

			if (rs != null)
				try {
					rs.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
		}
		return years;
	}
	
	public List<String> getDays(Integer cam_id, String year, String month){
		JNDIFactory jndiFactory = JNDIFactory.getInstance();
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<String> days = new ArrayList<String>();
		
		try {
			connection = jndiFactory.getConnection("jdbc/WAI_DB");
			if (cam_id != null) {
				pstmt = connection.prepareStatement("select day from image where cam_id = ? and year = "+year+" and month = "+month+" order by month desc");
				pstmt.setInt(1, cam_id);
				rs = pstmt.executeQuery();
			}
			else {
				pstmt = connection.prepareStatement("select day from image where year = "+year+" and month = "+month+" order by month desc");
				rs = pstmt.executeQuery();
			}

			while (rs.next()) {
				if (!days.contains(String.valueOf(rs.getInt("day")))){
				days.add(String.valueOf(rs.getInt("day")));
				}
			}
		}
		
		catch(Exception e) {
			System.out.println(e.getMessage());
		}
		finally {
			if (connection != null)
				try {
					connection.close();
				} catch (Exception e) {
					e.printStackTrace();
				}

			if (pstmt != null)
				try {
					pstmt.close();
				} catch (Exception e) {
					e.printStackTrace();
				}

			if (rs != null)
				try {
					rs.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
		}
		return days;
	}
	
	
	public List<String> getHoursStart(Integer cam_id, String year, String month, String day){
		JNDIFactory jndiFactory = JNDIFactory.getInstance();
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<String> hours = new ArrayList<String>();
		
		try {
			connection = jndiFactory.getConnection("jdbc/WAI_DB");
			if (cam_id != null) {
				pstmt = connection.prepareStatement("select hour from image where cam_id = ? and year = "+year+" and month = "+month+" and day = "+day+" order by month desc");
				pstmt.setInt(1, cam_id);
				rs = pstmt.executeQuery();
			}
			else {
				pstmt = connection.prepareStatement("select hour from image where year = "+year+" and month = "+month+" and day = "+day+" order by month desc");
				rs = pstmt.executeQuery();
			}

			while (rs.next()) {
				if (!hours.contains(String.valueOf(rs.getInt("hour")))){
				hours.add(String.valueOf(rs.getInt("hour")));
				}
			}
		}
		
		catch(Exception e) {
			System.out.println(e.getMessage());
		}
		finally {
			if (connection != null)
				try {
					connection.close();
				} catch (Exception e) {
					e.printStackTrace();
				}

			if (pstmt != null)
				try {
					pstmt.close();
				} catch (Exception e) {
					e.printStackTrace();
				}

			if (rs != null)
				try {
					rs.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
		}
		return hours;
	}
	
	public List<String> getHoursEnd(Integer cam_id, String year, String month, String day, String hourStart){
		JNDIFactory jndiFactory = JNDIFactory.getInstance();
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<String> hours = new ArrayList<String>();
		
		try {
			connection = jndiFactory.getConnection("jdbc/WAI_DB");
			if (cam_id != null) {
				pstmt = connection.prepareStatement("select hour from image where cam_id = ? and year = "+year+" and month = "+month+" and day = "+day+" and hour > "+hourStart+" order by month desc");
				pstmt.setInt(1, cam_id);
				rs = pstmt.executeQuery();
			}
			else {
				pstmt = connection.prepareStatement("select hour from image where year = "+year+" and month = "+month+" and day = "+day+" and hour > "+hourStart+" order by month desc");
				rs = pstmt.executeQuery();
			}

			while (rs.next()) {
				if (!hours.contains(String.valueOf(rs.getInt("hour")))){
				hours.add(String.valueOf(rs.getInt("hour")));
				}
			}
		}
		
		catch(Exception e) {
			System.out.println(e.getMessage());
		}
		finally {
			if (connection != null)
				try {
					connection.close();
				} catch (Exception e) {
					e.printStackTrace();
				}

			if (pstmt != null)
				try {
					pstmt.close();
				} catch (Exception e) {
					e.printStackTrace();
				}

			if (rs != null)
				try {
					rs.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
		}
		return hours;
	}
	
	
	public List<String> getMonths(Integer cam_id, String year){
		JNDIFactory jndiFactory = JNDIFactory.getInstance();
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<String> months = new ArrayList<String>();
		
		try {
			connection = jndiFactory.getConnection("jdbc/WAI_DB");
			if (cam_id != null) {
				pstmt = connection.prepareStatement("select month from image where cam_id = ? and year = "+year+" order by month desc");
				pstmt.setInt(1, cam_id);
				rs = pstmt.executeQuery();
			}
			else {
				System.out.println("here");
				pstmt = connection.prepareStatement("select month from image where year = "+year+" order by month desc");
				rs = pstmt.executeQuery();
			}

			while (rs.next()) {
				if (!months.contains(String.valueOf(rs.getInt("month")))){
				months.add(String.valueOf(rs.getInt("month")));
				}
			}
		}
		
		catch(Exception e) {
			System.out.println(e.getMessage());
		}
		finally {
			if (connection != null)
				try {
					connection.close();
				} catch (Exception e) {
					e.printStackTrace();
				}

			if (pstmt != null)
				try {
					pstmt.close();
				} catch (Exception e) {
					e.printStackTrace();
				}

			if (rs != null)
				try {
					rs.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
		}
		return months;
	}
	

	public ImageBean getLatestRecordFromCam(int cam_id) {
		
		ImageBean image = null;
		
		Connection connection = null;		
		try {
			connection = jndi.getConnection("jdbc/WAI_DB");
			
			PreparedStatement pstmt = connection.prepareStatement(
								"SELECT id, cam_id, path, thumbpath, year, month, day, hour " + 
								"FROM image WHERE cam_id = ? " +
								"ORDER BY year DESC, month DESC, day DESC, hour DESC LIMIT 1");
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