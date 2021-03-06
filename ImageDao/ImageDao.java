package dao;

import java.util.List;
import model.Image;

public interface ImageDao {
	public void save(Image image);
	public void delete(Integer id);
	public Image get(Integer id);
	public List<Image> list();
}

