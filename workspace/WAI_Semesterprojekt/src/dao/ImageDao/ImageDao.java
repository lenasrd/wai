package dao.ImageDao;

import java.util.List;
import mvc.model.ImageBean;

public interface ImageDao {
	public void save(ImageBean image);
	public void delete(Integer id);
	public ImageBean get(Integer id);
	public List<ImageBean> listAllImages();
	
	/* Muss modifiziert werden, kamera id muss ber�cksichtig werden
	 * - L�sungsvorschlag A: weiterer Parameter int cam_id  		- w�rde dann in einer schleife aufgerufen werden
	 * - L�sungsvorschlag B: weiterer Parameter Integer[] cam_ids 	- alle ben�tigten Bilder auf einen schlag
	 */
	public List<ImageBean> listIntervalImages(String year, String month, String day, String startTime, String endTime);
	public ImageBean getLatestRecordFromCam(int cam_id);
}