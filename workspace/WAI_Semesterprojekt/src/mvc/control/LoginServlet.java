package mvc.control;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
		
		
		
		JNDIFactory jndiFactory = JNDIFactory.getInstance();
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;

		try {
			connection = jndiFactory.getConnection("jdbc/WAI_DB");

			statement = connection.createStatement();
			resultSet = statement.executeQuery("select id, value from test");

			System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));			
			
			
			while (resultSet.next())
				System.out.println(resultSet.getInt("id") + " has value: "
						+ resultSet.getString("value"));

		} 
		catch(Exception e) {}
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
		
		
		
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/jsp/login.jsp");
		dispatcher.forward(request, response);
	}

	
	
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String username = request.getParameter("username");
		String password = request.getParameter("password");

		
		System.out.println("login post called\n\tusername: " + username + "\n\tpassword: " + password +"\n");
		
		// TODO Suche User in Datenbank, falls vorhanden hole ID
		// TODO prüfe auf Admin via Datenbak
		UserBean user = new UserBean();
		user.setUsername(username);
		user.setId(0); // TODO set id by id-generator

		
		
		// Generiere Session
		HttpSession session 	= request.getSession();
		SessionList sessionList = SessionList.getInstance();
		sessionList.addSession(session.getId(), user);
			
		if(username.equals("admin") && password.equals("admin")) {
			user.setPermissionLevel(UserBean.PERMISSION_LEVEL_ADMIN);
		}
		
		session.setAttribute("user", user);	
		response.sendRedirect("main_menu");
	}
}
