package dao.CamDao;

import java.util.List;

import mvc.model.CamBean;

public interface CamDao {

	public void save(CamBean cam);
	public void delete(Integer id);
	public CamBean get(Integer id);
	public List<CamBean> list();
	public int getNewId();
	
}
