package mvc.control;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

		String key = (String) request.getSession().getAttribute("key");
		request.getSession().setAttribute("key", key);
		System.out.println("key: " + key);
		RequestDispatcher dispatcher = null;
		
		switch(key) {
		// history aller cams, formular
		case "Browse_history_of_all_cameras":
			System.out.println("key: " + key);
			dispatcher = getServletContext().getRequestDispatcher("/jsp/browse_all_cams.jsp"); 
			dispatcher.forward(request, response);
			break;
		// history aller cams, view
		case "Request_images_all_cams":
			System.out.println("key: " + key);
			dispatcher = getServletContext().getRequestDispatcher("/jsp/history_all_cams.jsp"); 
			dispatcher.forward(request, response);	
			break;
		// history einzelner cam, formular
		case "browse_history":
			System.out.println("key: " + key);
			dispatcher = getServletContext().getRequestDispatcher("/jsp/browse_one_cam.jsp"); 
			dispatcher.forward(request, response);	
			break;
		// history einzelner cam, view
		case "Request_images_one_cam":
			System.out.println("key: " + key);
			dispatcher = getServletContext().getRequestDispatcher("/jsp/history_one_cam.jsp"); 
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
		
		
		String key = (String) request.getSession().getAttribute("key");
		request.getSession().setAttribute("key", key);
		
		System.out.println("key: " + key);
	}

}
