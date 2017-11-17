package application;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.apache.log4j.Logger;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import utils.JNDIFactory;
import utils.SessionList;

public class AppCore implements Job, HttpSessionListener {

	private static Logger jlog = Logger.getLogger(AppCore.class);

	JNDIFactory jndiFactory = JNDIFactory.getInstance();

	public AppCore() {
	}

	private void process() throws Exception {
		
		System.out.println("AppCore triggered");
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;

		try {
			connection = jndiFactory.getConnection("jdbc/WAI_DB");

			statement = connection.createStatement();
			resultSet = statement.executeQuery("select id, value from test");

			jlog.info(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));			
			
			
			while (resultSet.next())
				jlog.info(resultSet.getInt("id") + " has value: "
						+ resultSet.getString("value"));

		} finally {
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
	}

	public void execute(JobExecutionContext context)
			throws JobExecutionException {
		try {
			AppCore core = new AppCore();
			core.process();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void sessionCreated(HttpSessionEvent sessionEvent) {
		SessionList.getInstance().sessionCreated(sessionEvent);
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent sessionEvent) {
		SessionList.getInstance().sessionDestroyed(sessionEvent);
	}

}
