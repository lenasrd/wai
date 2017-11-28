package mvc.control;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

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
	private static final Level lOGLEVEL = Level.DEBUG; 
       
	private static Logger jlog = Logger.getLogger(AdministrationServlet.class);
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdministrationServlet() {
        super();
        jlog.setLevel(lOGLEVEL);
    }

    
    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		jlog.debug("AdministrationServlet get called");
		
		// proof session
		UserBean user = SessionList.getInstance().getUser(request);
		if(user == null || user.getPermissionLevel() != UserBean.PERMISSION_LEVEL_ADMIN) {
			response.sendRedirect("login");
			return;
		}	

		RequestDispatcher dispatcher = null;
		
		UserDao  userDao = UserDaoFactory.getInstance().getUserDao();
		List<UserBean> userList = userDao.list();
		
		CamDao camDao = CamDaoFactory.getInstance().getCamDao();
		List<CamBean> camList = camDao.list();
		
		request.setAttribute("UserList", userList);
		request.setAttribute("CamList", camList);
		
		dispatcher = getServletContext().getRequestDispatcher("/jsp/administration.jsp");
		dispatcher.forward(request, response);
		return;
	}

	
	
	
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		jlog.debug("AdministrationServlet post called");
		
		// proof session
		UserBean user = SessionList.getInstance().getUser(request);
		if(user == null || user.getPermissionLevel() != UserBean.PERMISSION_LEVEL_ADMIN) {
			response.sendRedirect("login");
			return;
		}	
		
		// parse action
		String key = request.getParameter("key");
		System.out.println(key);
		
		switch(key) {
		case "back_to_main":
			response.sendRedirect("main_menu");
			return;
		case "back_to_administration":
			response.sendRedirect("admin");
			return;
		}

		if(key.contains("user")) {
			handlePostUserAction(request, response);
		}
		else if (key.contains("cam")) {
			handlePostCamAction(request, response);
		}
		else {
			System.out.println("unknown key: " + key);
			response.sendRedirect("admin");
			return;
		}
	}
	
	
	
	private void handlePostUserAction(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		
		
		// parse parameter
		String key 		= request.getParameter("key");
		String target 	= request.getParameter("target");
		
		UserDao  userDao 				= UserDaoFactory.getInstance().getUserDao();
		UserBean targetUser 			= null;
		
		int target_id 					= -1;	
		
		CamDao camDao 					= CamDaoFactory.getInstance().getCamDao();
		List<CamBean> camList 			= camDao.list();
		
		
		RequestDispatcher dispatcher 	= null;
		
		
		

		
											
		// parse action
		switch(key) {

		case "add_new_user":
			request.setAttribute("CamList", 	camList);
			dispatcher = getServletContext().getRequestDispatcher("/jsp/add_user.jsp");
			dispatcher.forward(request, response);
			return;
			
			
		case "edit_user":			
			try {
				System.out.println("target: " + target);
				targetUser = userDao.get(Integer.parseInt(target));
			} catch(Exception E) {
				jlog.error(E.getMessage());
				// TODO errorpage
				response.sendRedirect("admin");
				return;
			}
			
			List<Integer> userCams 			= null;
			List<Boolean> allowedCams 		= new LinkedList<Boolean>();
			
			if(targetUser != null) {
				target_id = targetUser.getId();	
				userCams = targetUser.getCams();
			}

			for(int i = 0; i < camList.size(); i++) {
				Boolean allowed = false;
				if(targetUser != null) {
					allowed = userCams.contains(camList.get(i).getId());
				}
				allowedCams.add(allowed);
			}
						
			request.setAttribute("TargetUser", 	targetUser);
			request.setAttribute("CamList", 	camList);
			request.setAttribute("allowedCams", allowedCams);
			
			dispatcher = getServletContext().getRequestDispatcher("/jsp/edit_user.jsp");
			dispatcher.forward(request, response);
			return;
			
					
		case "delete_user":
			try {
				userDao.delete(Integer.parseInt(target));
				System.out.println("User " + target_id + " gelöscht!");
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
			response.sendRedirect("admin");
			return;
			
			
			
		case "Add_user_submit":
		case "Edit_user_submit":
			
			String 	target_username 		= request.getParameter("username");
			String 	target_password 		= request.getParameter("password");
			int 	target_permission 		= (request.getParameter("permission") != null) ?
												UserBean.PERMISSION_LEVEL_ADMIN :
												UserBean.PERMISSION_LEVEL_USER;
			
			if(key.equals("Add_user_submit")) {
				// add new user
				try {
					// prüfe ob user bereits existiert
					targetUser = userDao.get(target_username);
					if(targetUser != null) {
						System.out.println("User mit dem namen " + target_username + " bereits vorhanden!");
						response.sendRedirect("admin");
						return;
					}
				}
				catch(Exception e) {
					targetUser = new UserBean();
				}
			} else {
				// edit existing user 
				try {
					targetUser = userDao.get(Integer.parseInt(target));
					
				} catch(Exception e) {
					System.out.println(e.getMessage());
					response.sendRedirect("admin");
					return;
				}
			}
			
			
			if(targetUser == null) {
				System.out.println("User not found: " + target_username);
			}
			
			List<Integer> target_cams = new LinkedList<Integer>();
			
			System.out.println("target: " + target);

			for(int i = 0; i < camList.size(); i++) {
				System.out.println( "checkbox " + i + ": " + request.getParameter("check_list[" + i + "]") );
				if(request.getParameter("check_list[" + i + "]") != null) {
					target_cams.add(camList.get(i).getId());
				}
				
			}
			
			System.out.println("allowed cams: " + target_cams);
				
			targetUser.setUsername(target_username);
			targetUser.setPassword(target_password);
			targetUser.setPermissionLevel(target_permission);
			targetUser.setCams(target_cams);
			userDao.save(targetUser);
			
			
			if(key.equals("Add_user_submit"))
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
	
	
	
	
	private void handlePostCamAction(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String key 		= request.getParameter("key");
		String target 	= request.getParameter("target");
		
		String target_camname 			= request.getParameter("name");
		String target_camurl			= request.getParameter("url");
		
		CamBean targetCam 				= null;
		CamDao 	camDao 					= CamDaoFactory.getInstance().getCamDao();
		
		
										
		RequestDispatcher dispatcher 	= null;

		
		switch(key) {
		
		case "add_new_camera":
			// rufe jps zum anlegen einer neuen kamera auf
			dispatcher = getServletContext().getRequestDispatcher("/jsp/add_camera.jsp"); 
			dispatcher.forward(request, response);
			return;	
			

		case "edit_cam":	
			try {
				targetCam = camDao.get(Integer.parseInt(target));
			} catch(Exception E) {
				jlog.error(E.getMessage());
				// TODO errorpage
				response.sendRedirect("admin");
			}
			System.out.println("cam: " + targetCam);
			request.setAttribute("TargetCam", targetCam);
			dispatcher = getServletContext().getRequestDispatcher("/jsp/edit_camera.jsp");
			dispatcher.forward(request, response);
			return;

			
		case "delete_cam":
			try {
				camDao.delete(Integer.parseInt(target));
				System.out.println("Cam " + Integer.parseInt(target) + " gelöscht!");
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
			response.sendRedirect("admin");
			return;
			
			
			
		case "Add_camera_submit":
		case "Edit_camera_submit":
						
			if(key.equals("Add_camera_submit")) {
				// add new user
				targetCam = new CamBean();
			} else {
				// edit existing user 
				try {
					targetCam = camDao.get(Integer.parseInt(target));
					if(targetCam == null) {
						System.out.println("Kamera not found: " + target_camname);
					}
				} catch(Exception e) {
					System.out.println(e.getMessage());
					response.sendRedirect("admin");
				}
			}
			
			targetCam.setName(target_camname);
			targetCam.setUrl(target_camurl);
			camDao.save(targetCam);
			
			if(key.equals("Add_camera_submit"))
				System.out.println("Neue Cam angelegt!\n" + targetCam);
			else
				System.out.println("Cam editiert!\n" + targetCam);
			
			response.sendRedirect("admin");
			return;
			
		
			
		default:
			System.out.println("unknown key: " + key);
			response.sendRedirect("admin");
			return;
		}
											

	}

}
