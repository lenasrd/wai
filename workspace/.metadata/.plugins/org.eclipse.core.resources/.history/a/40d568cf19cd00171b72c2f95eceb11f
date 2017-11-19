package dao.ImageDao;


public class ImageDaoFactory {
	
	private static ImageDaoFactory instance = new ImageDaoFactory();
	
	private ImageDaoFactory() {		
	}
	
	public static ImageDaoFactory getInstance() {
		return instance;
	}
	
	public ImageDao getImageDao() {
		return new ImageDaoImpl();
	}
}