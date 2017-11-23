package mvc.control;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.UserDao.UserDao;
import dao.UserDao.UserDaoFactory;
import mvc.model.CamBean;
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
		
		UserDao  userDao = UserDaoFactory.getInstance().getUserDao();
		List<UserBean> userList = userDao.list();
		
		// CamDao camDao = CamDaoFactory.getInstance().getCamDao();
		List<CamBean> camList = null; // = camDao.list();
		
		request.setAttribute("UserList", userList);
		request.setAttribute("CamLList", camList);
		
		// show Page
		RequestDispatcher dispatcher = null;
		dispatcher = getServletContext().getRequestDispatcher("/jsp/administration.jsp"); 
		dispatcher.forward(request, response);
		return;
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
		case "Edit_user":
			// parse formulardaten und lege neuen user an
			System.out.println("key: " + key);

			
			
			String newUser_username = request.getParameter("username");
			String newUser_password = request.getParameter("password");
			int newUser_permission = (request.getParameter("permission") != null) ?
							UserBean.PERMISSION_LEVEL_ADMIN :
							UserBean.PERMISSION_LEVEL_USER;
			
			UserBean newUser 	= null;
			UserDao  userDao 	= UserDaoFactory.getInstance().getUserDao();

			if(key.equals("Add_user")) {
				newUser = new UserBean();
				
			} else {
				try {
					newUser = userDao.get(newUser_username);
					if(newUser == null) {
						System.out.println("User not found: " + newUser_username);
					}
				} catch(Exception e) {
					System.out.println(e.getMessage());
				}
			}
			
			newUser.setUsername(newUser_username);
			newUser.setPassword(newUser_password);
			newUser.setPermissionLevel(newUser_permission);

			userDao.save(newUser);

			
			System.out.println("Neuer User angelegt!\n" + newUser);
			
			response.sendRedirect("admin");
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
			String newCam_url = request.getParameter("url");
			String newCam_name = request.getParameter("name");
			
			
			response.sendRedirect("admin");
			return;
			
		default:
			System.out.println("unknown key: " + key);
			return;
		}
	}

}
