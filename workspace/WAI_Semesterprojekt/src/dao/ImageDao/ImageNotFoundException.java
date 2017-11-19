package dao.ImageDao;

public class ImageNotFoundException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	public ImageNotFoundException(Integer id) {
		super("Bild mit der Id " + id + " wurde nicht gefunden!");
	}
	
	public ImageNotFoundException() {
		super("Bilder können nicht aufgelistet werden!");
	}
}