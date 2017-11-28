package mvc.control;

import java.io.IOException;
import java.sql.Array;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import application.AppCore;
import dao.ImageDao.ImageDaoFactory;
import dao.UserDao.UserDaoFactory;
import dao.UserDao.UserNotFoundException;
import mvc.model.UserBean;
import utils.JNDIFactory;
import utils.SessionList;

/**
 * Servlet implementation class loginServlet
 */
// http://localhost:8080/WAI_Semesterprojekt/login
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Level lOGLEVEL = Level.INFO; 
	
	private static Logger jlog = Logger.getLogger(LoginServlet.class);

	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        jlog.setLevel(lOGLEVEL);
    }

    
    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		jlog.debug("login get called");

		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/jsp/login.jsp");
		dispatcher.forward(request, response);
	}

	
	
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		jlog.debug("login post called");
		
		String username = request.getParameter("username");
		String password = request.getParameter("password");

		UserBean user = new UserBean();
		try {
			user = UserDaoFactory.getInstance().getUserDao().get(username);
		} catch (UserNotFoundException e) {
			System.out.println(e.getMessage());
			jlog.info(e.getMessage());
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/jsp/login.jsp");
			dispatcher.forward(request, response);
			return;
		} catch(Exception e) {
			jlog.error(e.getMessage());
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/jsp/login.jsp");
			dispatcher.forward(request, response);
			return;
		}
		
		if (user.getPassword().equals(password)){

			// Generiere Session
			HttpSession session 	= request.getSession();
			SessionList sessionList = SessionList.getInstance();
			sessionList.addSession(session.getId(), user);
				
			jlog.info("Login as " + user);
			session.setAttribute("user", user);	
			response.sendRedirect("main_menu");
			return;
		} else {
			jlog.info("wrong password: " + user);
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/jsp/login.jsp");
			dispatcher.forward(request, response);
			return;
		}
	}
}
