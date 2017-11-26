package dao.CamDao;

import java.util.List;

import mvc.model.CamBean;


public interface CamDao {

	public void save(CamBean cam) 	throws CamNotSavedException;
	public void delete(Integer id) 	throws CamNotDeletedException;
	public CamBean get(Integer id) 	throws CamNotFoundException;
	public List<CamBean> list();
	public int getNewId();
	
	
}
