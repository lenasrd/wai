package exception;

public class ImageNotDeletedException extends RuntimeException {
	
	public ImageNotDeletedException(Integer id) {
		super("Bild mit der Id " + id + " konnte nicht ge�ndert werden!");
	}
}
