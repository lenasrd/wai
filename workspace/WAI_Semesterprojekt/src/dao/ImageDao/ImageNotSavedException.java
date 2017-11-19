package dao.ImageDao;

public class ImageNotSavedException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	public ImageNotSavedException() {
		super("Bild konnte nicht gespeichert werden!");
	}
}