package dao.CamDao;

public class CamNotDeletedException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public CamNotDeletedException(Integer id) {
		super("Kamera mit der Id " + id + " konnte nicht gelöscht werden!");
	}
}