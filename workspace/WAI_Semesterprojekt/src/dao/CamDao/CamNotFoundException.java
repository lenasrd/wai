package dao.CamDao;

public class CamNotFoundException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	public CamNotFoundException(int id) {
		super("Cam mit der ID " + id + " wurde nicht gefunden!");
	}
	
	public CamNotFoundException() {
		super("Cams können nicht aufgelistet werden!");
	}
}