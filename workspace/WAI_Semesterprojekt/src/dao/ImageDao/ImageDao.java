package dao.ImageDao;

import java.util.List;
import mvc.model.ImageBean;

public interface ImageDao {
	public void save(ImageBean image);
	public void delete(Integer id);
	public ImageBean get(Integer id);
	public List<ImageBean> list();
}