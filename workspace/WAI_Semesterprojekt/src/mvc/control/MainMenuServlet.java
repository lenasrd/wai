package mvc.control;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import application.AppCore;
import dao.CamDao.CamDao;
import dao.CamDao.CamDaoFactory;
import dao.CamDao.CamNotFoundException;
import dao.ImageDao.ImageDao;
import dao.ImageDao.ImageDaoFactory;
import dao.UserDao.UserDao;
import dao.UserDao.UserDaoFactory;
import mvc.model.CamBean;
import mvc.model.ImageBean;
import mvc.model.UserBean;
import utils.SessionList;

/**
 * Servlet implementation class MainMenuServlet
 */
public class MainMenuServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Level lOGLEVEL = Level.DEBUG; 
       
	private static Logger jlog = Logger.getLogger(MainMenuServlet.class);

    /**
     * @see HttpServlet#HttpServlet()
     */
    public MainMenuServlet() {
        super();
        jlog.setLevel(lOGLEVEL);
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		jlog.debug("MainMenuServlet get called");
		
		// proof session
		UserBean user = SessionList.getInstance().getUser(request);
		if(user == null) {
			response.sendRedirect("login");
			return;
		}
		
		String visibility;
		

		CamDao camDao 			= CamDaoFactory.getInstance().getCamDao();
		ImageDao imgDao 		= ImageDaoFactory.getInstance().getImageDao();
		
		List<CamBean> camList 		= new LinkedList<CamBean>();
		List<ImageBean> imgList 	= new LinkedList<ImageBean>();
		
		
		if(user.getPermissionLevel() == UserBean.PERMISSION_LEVEL_ADMIN) {
			visibility = "submit";
			camList = camDao.list();
		} 
		else {
			
			visibility = "hidden";
			List<Integer> userCamArray	= user.getCams();

			for (int i = 0; i < user.getCams().size(); i++) {
				try {
					camList.add(camDao.get(userCamArray.get(i)));
				} catch(CamNotFoundException E) {
					jlog.info(E.getMessage());
					continue;
				} catch(Exception E) {
					jlog.error(E.getMessage());
					break;
				}
			}
		}
		for(int i = 0; i < camList.size(); i++) {
			imgList.add(imgDao.getLatestRecordFromCam(camList.get(i).getId()));
		}

		jlog.debug(camList.size() + " cams listed");
		
		request.setAttribute("CamList", camList);
		request.setAttribute("ImageList", imgList);
		request.setAttribute("adminVisibility", visibility);
				
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/jsp/main_menu.jsp");
		dispatcher.forward(request, response);
	}

	
	
	
	
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		jlog.debug("MainMenuServlet post called");
		
		// proof session
		UserBean user = SessionList.getInstance().getUser(request);
		if(user == null) {
			response.sendRedirect("login");
			return;
		}	
		
		
		
		// parse action
		String key = request.getParameter("key");
		request.getSession().setAttribute("key", key);
		System.out.println("key: " + key);
					
		switch(key) {
		case "Browse_history_of_all_cameras":
		case "zoom":
			response.sendRedirect("image");
			return;
		case "browse_history":
			response.sendRedirect("history");
			return;
		case "Administration":
			if(user.getPermissionLevel() == UserBean.PERMISSION_LEVEL_ADMIN) {
				response.sendRedirect("admin");
			}	
			else {
				// TODO errorpage
				System.out.println("no permission");
				response.sendRedirect("main_menu");
			}
			return;
		case "Logout":
			SessionList.getInstance().removeSession(request.getSession().getId());
			response.sendRedirect("login");
			return;
		default:
			System.out.println("unknown key: " + key);
			// TODO errorpage?
			return;
		}
	}

}
