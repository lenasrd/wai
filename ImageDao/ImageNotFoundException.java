package exception;

public class ImageNotFoundException extends RuntimeException {
	
	public ImageNotFoundException(Integer id) {
		super("Bild mit der Id " + id + " wurde nicht gefunden!");
	}
	
	public ImageNotFoundException() {
		super("Bilder k�nnen nicht aufgelistet werden!");
	}
}
