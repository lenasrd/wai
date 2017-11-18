package dao;

import dao.ImageDaoImpl;

public class DaoFactory {
	
	private static DaoFactory instance = new DaoFactory();
	
	private DaoFactory() {		
	}
	
	public static DaoFactory getInstance() {
		return instance;
	}
	
	public ImageDao getImageDao() {
		return new ImageDaoImpl();
	}
}
