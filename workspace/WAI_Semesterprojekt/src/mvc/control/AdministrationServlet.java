package mvc.control;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mvc.model.UserBean;
import utils.SessionList;

/**
 * Servlet implementation class AdministrationServlet
 */
public class AdministrationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdministrationServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("AdministrationServlet get called");	
		
		// proof session
		UserBean user = SessionList.getInstance().getUser(request);
		if(user == null || user.getPermissionLevel() != UserBean.PERMISSION_LEVEL_ADMIN) {
			response.sendRedirect("login");
			return;
		}		

		
		// show Page
		RequestDispatcher dispatcher = null;
		dispatcher = getServletContext().getRequestDispatcher("/jsp/administration.jsp"); 
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("AdministrationServlet post called");
		
		// proof session
		UserBean user = SessionList.getInstance().getUser(request);
		if(user == null || user.getPermissionLevel() != UserBean.PERMISSION_LEVEL_ADMIN) {
			response.sendRedirect("login");
			return;
		}	
		

		
		// parse action
		String key = request.getParameter("key");
		request.getSession().setAttribute("key", key);
		RequestDispatcher dispatcher = null;
			
		switch(key) {
		case "Add_new_user":
			dispatcher = getServletContext().getRequestDispatcher("/jsp/add_user.jsp"); 
			System.out.println("key: " + key);
			dispatcher.forward(request, response);
			break;
		case "Add_new_camera":
			dispatcher = getServletContext().getRequestDispatcher("/jsp/add_camera.jsp"); 
			System.out.println("key: " + key);
			dispatcher.forward(request, response);
			break;
		default:
			System.out.println("unknown key: " + key);
		}
	}

}
