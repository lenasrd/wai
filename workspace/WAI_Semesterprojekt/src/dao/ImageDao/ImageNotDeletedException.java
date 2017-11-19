package dao.ImageDao;

public class ImageNotDeletedException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ImageNotDeletedException(Integer id) {
		super("Bild mit der Id " + id + " konnte nicht geändert werden!");
	}
}