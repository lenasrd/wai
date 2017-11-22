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
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("login get called");
		
		
		// TODO delete (TOP)
		/* Syntax insert user in database:
			INSERT INTO public.users(
				id, name, password, permission, cams)
				VALUES (1, 'admin', 'admin', 1, '{1, 2, 7, 9}');
		 */
		JNDIFactory jndiFactory = JNDIFactory.getInstance();
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;
		try {
			connection = jndiFactory.getConnection("jdbc/WAI_DB");

			statement = connection.createStatement();
			resultSet = statement.executeQuery("select id, name, password, permission, cams from users");
			
			System.out.println("TODO: DELETE ME!!!!");
			System.out.println("Vorhandene Accounts:");
			while (resultSet.next()) {
				System.out.println(resultSet.getInt("id") + " | Name: "
						+ resultSet.getString("name") + ", Passwort: "
						+ resultSet.getString("password") + ", permission: "
						+ resultSet.getInt("permission") + ", cams: "
						+ resultSet.getArray("cams"));
			}
		} 
		catch(Exception e) {
			System.out.println(e.getMessage());
		}
		finally {
			if (connection != null)
				try {
					connection.close();
				} catch (Exception e) {
					e.printStackTrace();
				}

			if (statement != null)
				try {
					statement.close();
				} catch (Exception e) {
					e.printStackTrace();
				}

			if (resultSet != null)
				try {
					resultSet.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
		}
		// TODO DELETE (BOTTOM)
		
		
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/jsp/login.jsp");
		dispatcher.forward(request, response);
	}

	
	
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String username = request.getParameter("username");
		String password = request.getParameter("password");

		UserBean user = new UserBean();
		try {
			user = UserDaoFactory.getInstance().getUserDao().get(username);
		} catch (UserNotFoundException e) {
			System.out.println(e.getMessage());
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/jsp/login.jsp");
			dispatcher.forward(request, response);
			return;
		} catch(Exception e) {
			System.out.println(e.getMessage());
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/jsp/login.jsp");
			dispatcher.forward(request, response);
			return;
		}
		
		if (user.getPassword().equals(password)){

			// Generiere Session
			HttpSession session 	= request.getSession();
			SessionList sessionList = SessionList.getInstance();
			sessionList.addSession(session.getId(), user);
				
			if(username.equals("admin") && password.equals("admin")) {
				user.setPermissionLevel(UserBean.PERMISSION_LEVEL_ADMIN);
			}
			System.out.println("-- Login as --\n" + user);
			session.setAttribute("user", user);	
			response.sendRedirect("main_menu");
			return;
		} else {
			System.out.println("-- Login as --\n" + user);
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/jsp/login.jsp");
			dispatcher.forward(request, response);
			return;
		}
	}
}
