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
import java.util.LinkedList;
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

import dao.CamDao.CamDaoFactory;
import dao.ImageDao.ImageDao;
import dao.ImageDao.ImageDaoFactory;
import mvc.model.CamBean;
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
		
		

		// get action key
		String key = (String) request.getSession().getAttribute("key");
		jlog.debug("key: " + key);
		RequestDispatcher dispatcher = null;
		

		//get target camera
		Integer oneTarget = Integer.parseInt(request.getSession().getAttribute("target").toString());
		Integer targetCam = oneTarget != CamBean.UNDEFINED ? oneTarget : null;
		//hier evtl noch getter in die CamBean für CamName über ID
		String headline =  oneTarget != CamBean.UNDEFINED ? "Browse history | "+ CamDaoFactory.getInstance().getCamDao().get(oneTarget).getName() : "Browse history | All cameras";
		
		
		//Integer targetCam = null;
		//String headline = "test";
		request.getSession().setAttribute("headline", headline);
		
		//read in available years and set them
		List<String> years = new ArrayList<String>();
		years = ImageDaoFactory.getInstance().getImageDao().getYears(targetCam);
		request.setAttribute("years", years);
		
		switch(key) {
		// history aller cams, formular
		case "Browse_history_of_all_cameras":
			// Zeige Eingabemaske zur Suche für alle Kameras
			System.out.println("key: " + key);
			dispatcher = getServletContext().getRequestDispatcher("/jsp/browse_one_cam.jsp"); 
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
		JNDIFactory jndiFactory = JNDIFactory.getInstance();
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;
		
		// get action key
		String key = (String) request.getParameter("key");
		System.out.println("key: " + key);
		

		//get target camera
		Integer oneTarget = Integer.parseInt(request.getSession().getAttribute("target").toString());
		Integer targetCam = oneTarget != CamBean.UNDEFINED ? oneTarget : null;
		
		//Integer targetCam = null;
		
		if(key.equals("Submit_year")) {
			//get chosen year
			String year = request.getParameter("whichYear");
			request.getSession().setAttribute("year", year);
			
			//look up months for this year
			List<String> months = new ArrayList<String>();
			months = ImageDaoFactory.getInstance().getImageDao().getMonths(targetCam, year);
			request.setAttribute("months", months);
			nextPage = "/jsp/datechoice_month.jsp";
		}
		
		else if (key.equals("Submit_month")) {
			//get chosen month and year
			String month = request.getParameter("whichMonth");
			request.getSession().setAttribute("month", month);
			String year = request.getSession().getAttribute("year").toString();
			
			//look up days for this month & year
			List<String> days = new ArrayList<String>();
			days = ImageDaoFactory.getInstance().getImageDao().getDays(targetCam, year, month);
			request.setAttribute("days", days);
			nextPage = "/jsp/datechoice_day.jsp";
				
		}
		
		else if (key.equals("Submit_day")) {
			//get chosen day, year and month
			String day = request.getParameter("whichDay");
			request.getSession().setAttribute("day", day);
			String year = request.getSession().getAttribute("year").toString();
			String month = request.getSession().getAttribute("month").toString();
			
			//look up startHours for this year & day & month
			List<String> hoursStart = new ArrayList<String>();
			List<String> hoursStartString = new LinkedList<String>();
			hoursStart = ImageDaoFactory.getInstance().getImageDao().getHoursStart(targetCam, year, month, day);
			
			for(int i = 0; i < hoursStart.size(); i++) {
				hoursStartString.add(ImageBean.transformTime(hoursStart.get(i)));
			}
			
			request.setAttribute("hoursStart", hoursStart);
			request.setAttribute("hoursStartString", hoursStartString);
			nextPage = "/jsp/datechoice_hour_start.jsp";
		}
		
		else if (key.equals("Submit_hour_start")) {
			//get chosen hourStart, day, year and month
			String hourStart = request.getParameter("whichHourStart");
			request.getSession().setAttribute("hourStart", hourStart);
			String day = request.getSession().getAttribute("day").toString();
			String year = request.getSession().getAttribute("year").toString();
			String month = request.getSession().getAttribute("month").toString();
			
			//look up endHours for this year & day & month
			List<String> hoursEnd = new ArrayList<String>();
			List<String> hoursEndString = new LinkedList<String>();

			//if there is only one hour in the database
			hoursEnd = ImageDaoFactory.getInstance().getImageDao().getHoursEnd(targetCam, year, month, day, hourStart);
			if (hoursEnd.isEmpty()) {
				hoursEnd.add(hourStart);
			}
			
			for(int i = 0; i < hoursEnd.size(); i++) {
				hoursEndString.add(ImageBean.transformTime(hoursEnd.get(i)));
			}
			
			request.setAttribute("hoursEndString", hoursEndString);
			request.setAttribute("hoursEnd", hoursEnd);
			nextPage = "/jsp/datechoice_hour_end.jsp";
		}
		
		else if (key.equals("Submit_hour_end")) {
			//get chosen hourEnd, hourStart, day, year and month
			String hourEnd = request.getParameter("whichHourEnd");
			request.getSession().setAttribute("hourEnd", hourEnd);

			String day = request.getSession().getAttribute("day").toString();
			String year = request.getSession().getAttribute("year").toString();
			String month = request.getSession().getAttribute("month").toString();
			String hourStart = request.getSession().getAttribute("hourStart").toString();
			
			//get images & redirect to display jsp
			ImageDao dao = ImageDaoFactory.getInstance().getImageDao();
			images = dao.listIntervalImages (targetCam, year, month, day, hourStart, hourEnd);
			
			request.setAttribute("ImageList", images);
			
			nextPage = oneTarget != CamBean.UNDEFINED ? "/jsp/history_one_cam.jsp" : "/jsp/history_all_cams.jsp";
		}
		
		else if (key.equals("zoom")) {
			String ImageID = request.getParameter("target");
			request.getSession().setAttribute("target", ImageID);
			response.sendRedirect("image");
			System.out.println("try key: " + key);
			return;
		}
		
		else if (key.equals("back_to_main")) {
			response.sendRedirect("main_menu");
			return;
		}
		else {
			jlog.info(("unknown key: " + key));
			response.sendRedirect("main_menu");
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
