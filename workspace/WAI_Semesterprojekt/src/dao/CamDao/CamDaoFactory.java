package dao.CamDao;

import dao.CamDao.CamDao;
import dao.CamDao.CamDaoFactory;
import dao.CamDao.CamDaoImpl;

public class CamDaoFactory {
	
	private static CamDaoFactory instance = new CamDaoFactory();
	
	private CamDaoFactory() {		
	}
	
	public static CamDaoFactory getInstance() {
		return instance;
	}
	
	public CamDao getCamDao() {
		return new CamDaoImpl();
	}
}