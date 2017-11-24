package dao.CamDao;

public class CamNotSavedException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	public CamNotSavedException() {
		super("Cam konnte nicht gespeichert werden!");
	}
}