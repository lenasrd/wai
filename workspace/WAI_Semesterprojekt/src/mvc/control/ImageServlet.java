package mvc.control;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import mvc.model.UserBean;
import utils.SessionList;

/**
 * Servlet implementation class ImageServlet
 */
public class ImageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Level lOGLEVEL = Level.DEBUG; 
       
	private static Logger jlog = Logger.getLogger(ImageServlet.class);
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ImageServlet() {
        super();
        jlog.setLevel(lOGLEVEL);
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		jlog.debug("ImageServlet get called");
		
		// proof session
		UserBean user = SessionList.getInstance().getUser(request);
		if(user == null) {
			response.sendRedirect("login");
			return;
		}	
		
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/jsp/image_zoom.jsp");
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		jlog.debug("ImageServlet post called");
		
		// proof session
		UserBean user = SessionList.getInstance().getUser(request);
		if(user == null) {
			response.sendRedirect("login");
			return;
		}	
		return;
	}

}
