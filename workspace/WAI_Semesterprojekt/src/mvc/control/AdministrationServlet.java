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
		
		RequestDispatcher dispatcher = null;
		
		// parse action
		String key = request.getParameter("key");		
			
		switch(key) {
		case "Add_new_user":
			// rufe jsp zum anlegen eines neuen users auf
			System.out.println("key: " + key);
			dispatcher = getServletContext().getRequestDispatcher("/jsp/add_user.jsp"); 
			dispatcher.forward(request, response);
			return;
			
		case "Add_new_camera":
			// rufe jps zum anlegen einer neuen kamera auf
			System.out.println("key: " + key);
			dispatcher = getServletContext().getRequestDispatcher("/jsp/add_camera.jsp"); 
			dispatcher.forward(request, response);
			return;
			
		case "Add_user":
			// parse formulardaten und lege neuen user an
			System.out.println("key: " + key);
			System.out.println("username " + request.getParameter("username"));
			System.out.println("password " + request.getParameter("password"));
			if(request.getParameter("permission") != null) {
				System.out.println("permission: admin");
			}
			else {
				System.out.println("permission: user");
			}
			response.sendRedirect("admin");
			/** TODO
			 *  Neuen User in Datenbank anlegen
			 *  weiterleitung wohin?
			 */
			return;
			
		case "Add_camera":
			// parse formulardaten und lege neue kamera an
			System.out.println("key: " + key);
			System.out.println("username " + request.getParameter("name"));
			System.out.println("password " + request.getParameter("url"));
			System.out.println("zutat " + request.getParameter("zutat"));
			if(request.getParameter("access") != null) {
				System.out.println("access:  all users");
			}
			else {
				System.out.println("access: nobody");
			}
			
			/** TODO
			 *  Neue Kamera in Datenbank anlegen
			 *  weiterleitung wohin?
			 */
			response.sendRedirect("admin");
			return;
			
		default:
			System.out.println("unknown key: " + key);
			return;
		}
	}

}
