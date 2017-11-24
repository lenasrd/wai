package mvc.control;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.CamDao.CamDao;
import dao.CamDao.CamDaoFactory;
import dao.UserDao.UserDao;
import dao.UserDao.UserDaoFactory;
import dao.UserDao.UserNotFoundException;
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
		

		//catch edit and remove calls and get id of the calling source
		String action = request.getParameter("action");
		Long id = null;
		
		if (request.getParameter("id") != null) {
			id = Long.valueOf(request.getParameter("id"));
		}
	
		if(action!= null && action.equals("edit_user")){
			System.out.println("Call edit user "+ id);
			dispatcher = getServletContext().getRequestDispatcher("/jsp/edit_user.jsp");
			dispatcher.forward(request, response);
		}
		
		else if(action!= null && action.equals("remove_user")){
			System.out.println("Call remove user "+ id);
			dispatcher = getServletContext().getRequestDispatcher("/jsp/administration.jsp");
			dispatcher.forward(request, response);
		}
		
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
	
				
		// parse action
		String key 						= request.getParameter("key");	
		System.out.println("key: " + key);
		
		if(key.contains("user")) {
			handleUserAction(request, response);
		}
		else if (key.contains("cam")) {
			handleCamAction(request, response);
		}
		else {
			System.out.println("unknown key: " + key);
			response.sendRedirect("admin");
			return;
		}
	}
	
	
	
	private void handleUserAction(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// parse parameter
		String 	target_username 		= request.getParameter("username");
		String 	target_password 		= request.getParameter("password");
		int 	target_permission 		= (request.getParameter("permission") != null) ?
											UserBean.PERMISSION_LEVEL_ADMIN :
											UserBean.PERMISSION_LEVEL_USER;
		
		UserBean targetUser				= null;
		UserDao  userDao 				= UserDaoFactory.getInstance().getUserDao();
		
		int 	target_id 				= (request.getParameter("targetID") != null) ?
											Integer.parseInt(request.getParameter("targetID")) :
											-1;
											
		RequestDispatcher dispatcher 	= null;
											
		// parse action
		String key 						= request.getParameter("key");
		
		switch(key) {
		case "Add_new_user":
			// rufe jsp zum anlegen eines neuen users auf
			dispatcher = getServletContext().getRequestDispatcher("/jsp/add_user.jsp"); 
			dispatcher.forward(request, response);
			return;
			
		case "Edit_user":
			dispatcher = getServletContext().getRequestDispatcher("/jsp/edit_user.jsp");
			System.out.println("User " + " editiert!");
			dispatcher.forward(request, response);
			return;
			
		case "Delete_user":
			try {
				userDao.delete(target_id);
				System.out.println("User " + target_id + " gelöscht!");
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
			response.sendRedirect("admin");
			return;
			
		case "Add_user_submit":
		case "Edit_user_submit":
			
			if(key.equals("Add_user")) {
				// add new user
				targetUser = new UserBean();
			} else {
				// edit existing user 
				try {
					targetUser = userDao.get(target_id);
					if(targetUser == null) {
						System.out.println("User not found: " + target_username);
					}
				} catch(Exception e) {
					System.out.println(e.getMessage());
					response.sendRedirect("admin");
				}
			}
			
			targetUser.setUsername(target_username);
			targetUser.setPassword(target_password);
			targetUser.setPermissionLevel(target_permission);
			userDao.save(targetUser);
			
			if(key.equals("Add_user"))
				System.out.println("Neuer User angelegt!\n" + targetUser);
			else
				System.out.println("User editiert!\n" + targetUser);
			
			response.sendRedirect("admin");
			return;
		
		default:
			System.out.println("Unknown ke: " + key);
			response.sendRedirect("admin");
			return;
		}
	}
	
	
	
	
	private void handleCamAction(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String target_camname 			= request.getParameter("name");
		String target_camurl			= request.getParameter("url");
		
		CamBean targetCam 				= null;
		CamDao 	camDao 					= CamDaoFactory.getInstance().getCamDao();
		
		int 	target_id 				= (request.getParameter("targetID") != null) ?
											Integer.parseInt(request.getParameter("targetID")) :
											-1;
										
		RequestDispatcher dispatcher 	= null;
											
		// parse action
		String key 						= request.getParameter("key");	
		
		switch(key) {
		case "Add_new_camera":
			// rufe jps zum anlegen einer neuen kamera auf
			dispatcher = getServletContext().getRequestDispatcher("/jsp/add_camera.jsp"); 
			dispatcher.forward(request, response);
			return;	
			
		case "Edit_camera":
			// rufe jps zum anlegen einer neuen kamera auf
			dispatcher = getServletContext().getRequestDispatcher("/jsp/edit_camera.jsp"); 
			dispatcher.forward(request, response);
			return;	
			
		case "Delete_camera":
			try {
				camDao.delete(target_id);
				System.out.println("Cam " + target_id + " gelöscht!");
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
			response.sendRedirect("admin");
			return;
			
		case "Add_camera_submit":
		case "Edit_camera_submit":
			
			if(key.equals("Add_user")) {
				// add new user
				targetCam = new CamBean();
			} else {
				// edit existing user 
				try {
					targetCam = camDao.get(target_id);
					if(targetCam == null) {
						System.out.println("User not found: " + target_camname);
					}
				} catch(Exception e) {
					System.out.println(e.getMessage());
					response.sendRedirect("admin");
				}
			}
			
			targetCam.setName(target_camname);
			targetCam.setUrl(target_camurl);
			camDao.save(targetCam);
			
			if(key.equals("Add_user"))
				System.out.println("Neuer User angelegt!\n" + targetCam);
			else
				System.out.println("User editiert!\n" + targetCam);
			
			response.sendRedirect("admin");
			return;
			
		default:
			System.out.println("unknown key: " + key);
			response.sendRedirect("admin");
			return;
		}
											

	}

}
