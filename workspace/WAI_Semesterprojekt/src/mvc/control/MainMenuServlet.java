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
 * Servlet implementation class MainMenuServlet
 */
public class MainMenuServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MainMenuServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("MainMenuServlet get called");
		
		// proof session
		UserBean user = SessionList.getInstance().getUser(request);
		if(user == null) {
			response.sendRedirect("login");
			return;
		}	
		
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/jsp/main_menu.jsp");
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("MainMenuServlet post called");
		
		// proof session
		UserBean user = SessionList.getInstance().getUser(request);
		if(user == null) {
			response.sendRedirect("login");
			return;
		}	
		
		
		
		
		// parse action
		String key = request.getParameter("key");
		request.getSession().setAttribute("key", key);
					
		switch(key) {
		case "Browse_history_of_all_cameras":
		case "browse_history":
			System.out.println("key: " + key);
			response.sendRedirect("history");
			break;
		case "Administration":
			if(user.getPermissionLevel() != UserBean.PERMISSION_LEVEL_ADMIN) {
				response.sendRedirect("admin");
			}	
			break;
		case "Logout":
			System.out.println("key: " + key);
			SessionList.getInstance().removeSession(request.getSession().getId());
			response.sendRedirect("login");
			break;
		default:
			System.out.println("unknown key: " + key);
		}
		return;
	}

}