package application;

import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.imageio.ImageIO;
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
			// Datum für Dateinamen
			Date date = new Date() ;
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss") ;
			
			//Aufteilung Datum
			SimpleDateFormat sdfmt = new SimpleDateFormat();
			sdfmt.applyPattern("yyyy");
			String year = sdfmt.format(date);
			sdfmt.applyPattern("MM");
			String month = sdfmt.format(date);
			sdfmt.applyPattern("dd");
			String day = sdfmt.format(date);
			sdfmt.applyPattern("HHmmss");
			String hour = sdfmt.format(date);
			
			//DB Connection
			connection = jndiFactory.getConnection("jdbc/WAI_DB");
			statement = connection.createStatement();
			
			//Kamerainformationen
			ResultSet cam = statement.executeQuery("SELECT url, name,id FROM public.cam" );
			String cam_url = "leer";
			String cam_name = "leer";
			String cam_id_string = "leer";
			int cam_id = 0;
		
		        while(cam.next())
		        {
		        	cam_id_string = cam.getString("id");
		            cam_url  = cam.getString("url");
		            cam_name = cam.getString("name");
		            cam_id = Integer.parseInt(cam_id_string);
		      	            
		            // Erstellen der Ordnerstruktur (ABSOLUTE PATH?!?!)

		            File files = new File("C:/Users/LW16/wai/workspace/WAI_Semesterprojekt/pic/"+cam_id+"/" + year + "/" + month + "/" + day + "/");

		            	if (!files.exists()) 
		            	{
		            			if (files.mkdirs()) {jlog.info("Ordnerstruktur wurder erfolgreich erstellt!");} 
		            			else {jlog.info("Fehler bei der Erstellung der Ordnerstruktur!");}
		            	}	
					        
			        // Bildbeschaffung
			     	File savedpic = new File(files + "/"+cam_name+"_" + dateFormat.format(date) + ".jpg") ;
			     	String filename = savedpic.getAbsolutePath();	
			     	BufferedImage pic = ImageIO.read( new URL(cam_url) );
			     	ImageIO.write(pic, "jpg", savedpic);
			     	jlog.info("Download von " + dateFormat.format(date) + ".jpg beendet");
				
	       
					//Letzte geschriebene id aus der Datenbank holen
			     	statement = connection.createStatement();
					ResultSet result = statement.executeQuery("SELECT id FROM public.image ORDER BY id DESC LIMIT 1" );
					int id = 0;
					while (result.next()) { id=Integer.parseInt(result.getString(1)); }
					id++;
					
					//Datum String to int
					int int_year = Integer.parseInt(year);
					int int_month = Integer.parseInt(month);
					int int_day = Integer.parseInt(day);
					int int_hour = Integer.parseInt(hour);
					
					//Datenbankeintrag von geladenem Bild hinzufügen
					String query = "INSERT INTO public.image(id, cam_id, path, year, month, day, hour) VALUES ("+id+","+cam_id+",'"+filename+"',"+int_year+","+int_month+","+int_day+","+int_hour+")";
					statement.executeUpdate(query);
					jlog.info("Datenbankeintrag hinzugefügt");
					
					//DB Logeintrag des letzten geschriebenen Bildes
					resultSet = statement.executeQuery("select id, cam_id, path from public.image");
			
						while (resultSet.next())
						{
							if (resultSet.isLast())
							jlog.info(resultSet.getInt("id") +" von " + resultSet.getString("cam_id")+" liegt unter "+resultSet.getString("path"));
						}
				

		        }//Ende "große" while


			}//Ende try
			
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
