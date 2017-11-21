package mvc.control;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sun.prism.Image;

import dao.ImageDao.ImageDaoFactory;
import mvc.model.ImageBean;
import mvc.model.UserBean;
import utils.SessionList;

/**
 * Servlet implementation class HistoryServlet
 */
public class HistoryServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public HistoryServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("HistoryServlet get called");
		
		// proof session
		UserBean user = SessionList.getInstance().getUser(request);
		if(user == null) {
			response.sendRedirect("login");
			return;
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
		// TODO Auto-generated method stub
		System.out.println("HistoryServlet post called");

		// proof session
		UserBean user = SessionList.getInstance().getUser(request);
		if(user == null) {
			response.sendRedirect("login");
			return;
		}
		
		RequestDispatcher dispatcher 	= null;
		String nextPage 				= null;
		List<ImageBean> imageList 		= null;
		
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
			nextPage = "/jsp/history_all_cams.jsp";
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
