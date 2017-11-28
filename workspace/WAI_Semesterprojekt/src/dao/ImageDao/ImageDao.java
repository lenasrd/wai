package dao.ImageDao;

import java.util.List;
import mvc.model.ImageBean;

public interface ImageDao {
	public void save(ImageBean image, Integer id);
	public void delete(Integer id);
	public ImageBean get(Integer id);
	public List<String> getYears(Integer cam_id);
	public List<String> getMonths(Integer cam_id, String year);
	public List<String> getDays(Integer cam_id, String year, String month);
	public List<String> getHoursStart(Integer cam_id, String year, String month, String day);
	public List<String> getHoursEnd(Integer cam_id, String year, String month, String day, String hourStart);
	public List<ImageBean> listAllImages();
	
	/* Muss modifiziert werden, kamera id muss berücksichtig werden
	 * - Lösungsvorschlag A: weiterer Parameter int cam_id  		- würde dann in einer schleife aufgerufen werden
	 * - Lösungsvorschlag B: weiterer Parameter Integer[] cam_ids 	- alle benötigten Bilder auf einen schlag
	 */
	public List<ImageBean> listIntervalImages(Integer cam_id, String year, String month, String day, String startTime, String endTime);
	public ImageBean getLatestRecordFromCam(int cam_id);
	
}