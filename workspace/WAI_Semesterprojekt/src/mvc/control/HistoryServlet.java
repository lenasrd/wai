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
		
		//read in available years
		JNDIFactory jndiFactory = JNDIFactory.getInstance();
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;
		try {
			connection = jndiFactory.getConnection("jdbc/WAI_DB");
			statement = connection.createStatement();
			resultSet = statement.executeQuery("select year from images where camid = 1 order by year desc");
			List<String> years = new ArrayList<String>();
			
			//set years in selector
			while (resultSet.next()) {
				if (!years.contains(String.valueOf(resultSet.getInt("year")))){
				years.add(String.valueOf(resultSet.getInt("year")));
				request.setAttribute("years", years);
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

			if (statement != null)
				try {
					statement.close();
				} catch (Exception e) {
					e.printStackTrace();
				}

			if (resultSet != null)
				try {
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
		
		if(key.equals("Request_images_all_cams")) {
			/** history aller cams, view
			 * Ausgelöst durch: browse_all_cams.jsp
			 * Input Formulardaten:
			 * 
			 * Ziel:
			 * Zeige Ergebnis der Suche über alle Kameras
			 * Auflistung der Ergebnisse (Bilder als Thumbs)
			 **/

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


		} else if (key.equals("Submit_year")) {
			try {
				connection = jndiFactory.getConnection("jdbc/WAI_DB");
			} catch (NamingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			try {
				//get chosen year
				String year = request.getParameter("whichYear");
				request.getSession().setAttribute("year", year);
				
				//look up months for this year
				statement = connection.createStatement();
				resultSet = statement.executeQuery("select month from images where year = " + year + " order by month");
				List<String> months = new ArrayList<String>();
				while (resultSet.next()) {
					if (!months.contains(String.valueOf(resultSet.getInt("month")))){
						months.add(String.valueOf(resultSet.getInt("month")));
						request.setAttribute("months", months);
					}
					nextPage = "/jsp/datechoice_month.jsp";
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		else if (key.equals("Submit_month")) {
			System.out.println("key: " + key);
		
			try {
				connection = jndiFactory.getConnection("jdbc/WAI_DB");
			} catch (NamingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			try {
				//get chosen month and year
				String month = request.getParameter("whichMonth");
				System.out.println("selected month = " + month);
				request.getSession().setAttribute("month", month);
				String year = request.getSession().getAttribute("year").toString();
				
				//look up days for this month & year
				statement = connection.createStatement();
				resultSet = statement.executeQuery("select day from images where year = " + year + " and month = " + month + " order by day");
				List<String> days = new ArrayList<String>();
				while (resultSet.next()) {
					days.add(resultSet.getString("day"));
					request.setAttribute("days", days);
					System.out.println(days);
					nextPage = "/jsp/datechoice_day.jsp";
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		else if (key.equals("Submit_day")) {
			System.out.println("key: " + key);
		
			try {
				connection = jndiFactory.getConnection("jdbc/WAI_DB");
			} catch (NamingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			try {
				//get chosen day, year and month
				String day = request.getParameter("whichDay");
				request.getSession().setAttribute("day", day);
				String year = request.getSession().getAttribute("year").toString();
				String month = request.getSession().getAttribute("month").toString();
				
				//look up startHours for this year & day & month
				statement = connection.createStatement();
				resultSet = statement.executeQuery("select hour from images where year = " + year + " and month = " + month + " and day = " + day);
				List<String> hoursStart = new ArrayList<String>();
				while (resultSet.next()) {
					hoursStart.add(resultSet.getString("hour"));
					request.setAttribute("hoursStart", 	hoursStart);
					System.out.println(hoursStart);
					nextPage = "/jsp/datechoice_hour_start.jsp";
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		else if (key.equals("Submit_hour_start")) {
			System.out.println("key: " + key);
		
			try {
				connection = jndiFactory.getConnection("jdbc/WAI_DB");
			} catch (NamingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			try {
				//get chosen hourStart, day, year and month
				String hourStart = request.getParameter("whichHourStart");
				request.getSession().setAttribute("hourStart", hourStart);
				System.out.println("selected hourStart was = " + hourStart);

				String day = request.getSession().getAttribute("day").toString();
				System.out.println("selected day was = " + day);
				String year = request.getSession().getAttribute("year").toString();
				System.out.println("selected year was = " + year);
				String month = request.getSession().getAttribute("month").toString();
				System.out.println("selected month was = " + month);
				
				//look up endHours for this year & day & month
				statement = connection.createStatement();
				resultSet = statement.executeQuery("select hour from images where year = " + year + " and month = " + month + " and day = " + day + " and hour > " + hourStart);
				List<String> hoursEnd = new ArrayList<String>();
				while (resultSet.next()) {
					hoursEnd.add(resultSet.getString("hour"));
					request.setAttribute("hoursEnd", hoursEnd);
					System.out.println(hoursEnd);
				}
				if(hoursEnd.isEmpty()) {
					hoursEnd.add(hourStart);
					request.setAttribute("hoursEnd", hoursEnd);
				}
				nextPage = "/jsp/datechoice_hour_end.jsp";
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		else if (key.equals("Submit_hour_end")) {
			System.out.println("key: " + key);
		
			try {
				connection = jndiFactory.getConnection("jdbc/WAI_DB");
			} catch (NamingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			//get chosen hourEnd, hourStart, day, year and month
			String hourEnd = request.getParameter("whichHourEnd");
			request.getSession().setAttribute("hourEnd", hourEnd);
			
			System.out.println("selected hourEnd was = " + hourEnd);
			nextPage = "/jsp/history_one_cam.jsp";
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
