package mvc.control;

import java.io.IOException;
import java.sql.Array;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import com.sun.prism.Image;

import dao.ImageDao.ImageDaoFactory;
import mvc.model.ImageBean;
import mvc.model.UserBean;
import utils.JNDIFactory;
import utils.SessionList;

/**
 * Servlet implementation class HistoryServlet
 */
public class HistoryServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Level lOGLEVEL = Level.DEBUG; 
	
	private static Logger jlog = Logger.getLogger(HistoryServlet.class);
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public HistoryServlet() {
        super();
        jlog.setLevel(lOGLEVEL);
    }

    private List<ImageBean> images = new ArrayList<ImageBean>();

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		jlog.debug("HistoryServlet get called");
		
		// proof session
		UserBean user = SessionList.getInstance().getUser(request);
		if(user == null) {
			response.sendRedirect("login");
			return;
		}
		
		JNDIFactory jndiFactory = JNDIFactory.getInstance();
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;
		try {
			connection = jndiFactory.getConnection("jdbc/WAI_DB");
			
			//START LENA
			//read in images from database and save in list<imagebean>
			statement = connection.createStatement();
			resultSet = statement.executeQuery("select year from images where camid = 1 order by year desc");
			List<String> years = new ArrayList<String>();
			
			System.out.println("TODO: DELETE ME!!!!");
			System.out.println("Vorhandene Images:");
			while (resultSet.next()) {
				/*System.out.println("ID: "
						+ resultSet.getInt("id") + ", CamId: "
						+ resultSet.getInt("camid") + ", path: "
						+ resultSet.getString("path") + ", timestamp: "
						+ resultSet.getInt("timestamp"));*/
				
				years.add(String.valueOf(resultSet.getInt("year")));
				request.setAttribute("years", years);
				
				/*ImageBean newImage = new ImageBean();
				newImage.setId(resultSet.getInt("id"));
				newImage.setCamId(resultSet.getInt("camid"));
				newImage.setPath(resultSet.getString("path"));
				newImage.setTimestamp(resultSet.getInt("year"));
				newImage.setTimestamp(resultSet.getString("month"));
				newImage.setTimestamp(resultSet.getInt("year"));
				newImage.setTimestamp(resultSet.getInt("year"));
				images.add(newImage);*/
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

			if (statement != null)
				try {
					statement.close();
				} catch (Exception e) {
					e.printStackTrace();
				}

			if (resultSet != null)
				try {
					//START LENA
					//set years with read data
					/*List<String> years = new ArrayList<String>();
					for(int i =0; i<images.size(); i++) {
						int time = images.get(i).getTimestamp();
						java.util.Date date=new java.util.Date((long)time*1000);
						SimpleDateFormat df = new SimpleDateFormat("yyyy");
						years.add(df.format(date));
						request.setAttribute("years", years)
					};*/
					resultSet.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
		}
		

		// get action key
		String key = (String) request.getSession().getAttribute("key");
		System.out.println("key: " + key);
		RequestDispatcher dispatcher = null;
		
		
		
		switch(key) {
		// history aller cams, formular
		case "Browse_history_of_all_cameras":
			// Zeige Eingabemaske zur Suche für alle Kameras
			System.out.println("key: " + key);
			dispatcher = getServletContext().getRequestDispatcher("/jsp/browse_all_cams.jsp"); 
			dispatcher.forward(request, response);
			break;
		
		// history einzelner cam, formular
		case "browse_history":
			// Zeige Eingabemaske zur Suche für einer Kamera
			System.out.println("key: " + key);
			dispatcher = getServletContext().getRequestDispatcher("/jsp/browse_one_cam.jsp"); 
			dispatcher.forward(request, response);	
			break;

		default:
			System.out.println("unknown key: " + key);
		}
		return;
	}

	
	
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		jlog.debug("HistoryServlet post called");

		// proof session
		UserBean user = SessionList.getInstance().getUser(request);
		if(user == null) {
			response.sendRedirect("login");
			return;
		}
		
		RequestDispatcher dispatcher 	= null;
		String nextPage 				= null;
		List<ImageBean> imageList 		= null;
		JNDIFactory jndiFactory = JNDIFactory.getInstance();
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;
		
		// get action key
		String key = (String) request.getParameter("key");
		System.out.println("key: " + key);
		
		/** TODO
		 * 	Parse Formular
		 * 	Input Formulardaten:
		 * 	- KameraId
		 * 	- Year
		 * 	- Month
		 * 	- Day
		 * 	- Time_from
		 * 	- Time_to
		 * 
		 **/
		
		
		if(key.equals("Request_images_all_cams")) {
			/** history aller cams, view
			 * Ausgelöst durch: browse_all_cams.jsp
			 * Input Formulardaten:
			 * 
			 * Ziel:
			 * Zeige Ergebnis der Suche über alle Kameras
			 * Auflistung der Ergebnisse (Bilder als Thumbs)
			 **/
			System.out.println("key: " + key);

			/** TODO
			 * 	Datenbankabfrage
			 *
			 * 	Beispielquery (mein spontaner brainstrom)
			 *  SELECT path, name, date FROM images WHERE cam_id = user.getCams().get(i) AND Date >= Time_from AND Date <= Time_to
			 *  
			 *  Pro verfügbare Kamera eine Listenabfrage
			 *  Ergebnisse in GesamtListe sammeln
			 *  
			 *  Abfrage über ImageDao
			 **/ 
//			for (int i = 0; i < user.getCams().size(); i++) {
//				
//				List<ImageBean> tempImageList = ImageDaoFactory.getInstance().getImageDao().listIntervalImages(
//	  			year, month, day, startTime, endTime);
//				for(int k = 0; k < tempImageList.size(); k++) {
//					imageList.add(imageList.get(i)));
//				}
//			}

			nextPage = "/jsp/history_all_cams.jsp";


		} else if (key.equals("Request_images_one_cam")) {
			/** history einzelner cam, view
			 * Ausgelöst durch: browse_one_cam.jsp
			 *
			 * Ziel:
			 * Zeige Ergebnis der Suche über einer Kameras
			 * Auflistung der Ergebnisse (Bilder als Thumbs)
			 **/
			System.out.println("key: " + key);
			
			/** TODO
			 *  Einlesen der Formulardaten
			 *  - Cam_id
			 */
			
			/** TODO
			 *  Datenbankabfrage
			 *  
			 *  Beispielquery (mein spontaner brainstrom)
			 *  SELECT path, name, date FROM images WHERE cam_id = id AND Date >= Time_from AND Date <= Time_to
			 *  
			 *  Abfrage über ImageDao
			 */
			;
			
//			imageList = ImageDaoFactory.getInstance().getImageDao().listIntervalImages(
//			  			year, month, day, startTime, endTime);
			//nextPage = "/jsp/history_one_cam.jsp";

			try {
				connection = jndiFactory.getConnection("jdbc/WAI_DB");
			} catch (NamingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			//START LENA
			//read in images from database and save in list<imagebean>
			try {
				statement = connection.createStatement();
				resultSet = statement.executeQuery("select month from images where camid = 1");
				List<String> months = new ArrayList<String>();
				while (resultSet.next()) {
					months.add(resultSet.getString("month"));
					request.setAttribute("months", months);
					System.out.println(months);
					nextPage = "/jsp/browse_one_cam.jsp";
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return;
		}
		
		else if (key.equals("zoom")) {
			response.sendRedirect("image");
			System.out.println("try key: " + key);
			return;
		}
		
		/** TODO
		 * 	generiere Liste von Thumbnails
		 * 	evtl taugt der code von https://www.java-forum.org/thema/thumbnails-schneller-erstellen.21437/ etwas
		 * 	Würde vorschlagen die funktion zum generieren in der ImageBean zu verankern, evtl sogar automatisch
		 * 	beim setzen des pfades ( setPath() )
		 */
		
		/** TODO
		 * 	Verpacke die Daten in der Session
		 */
//		request.getSession().setAttribute("ImageList", imageList);
		
		dispatcher = getServletContext().getRequestDispatcher(nextPage); 
		dispatcher.forward(request, response);	
	
	}

}
