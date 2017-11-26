package mvc.model;

public class ImageBean {

	private Integer id;
	private Integer camId;
	private String path;
	private String thumbPath;
	private Integer timestamp;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCamId() {
        return camId;
    }

    public void setCamId(Integer camId) {
        this.camId = camId;
    }

    public String getPath() {
        return path;
    }

    /** TODO / Empfehlung
     * 	setPath dahingehend ab�ndern das dar�ber automatisch thumbs erzeugt werden
     */
    public void setPath(String path) {
        this.path = path;
        // this.thumb = ImageBean::createThumb(path);
    }

    public Integer getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Integer timestamp) {
        this.timestamp = timestamp;
    }

	public String getThumbPath() {
		return thumbPath;
	}

	public void setThumbPath(String thumbPath) {
		this.thumbPath = thumbPath;
	}
    
    
    /* Bilder und Thumbs m�ssen irgendwie ausgeldesen oder erzeugt werden
     * Bilder:
     * - muss das programm das bild laden und als stream verschicken oder kann auf den pfad verwiesen werden?
     * 
     * Thumbs:
     * - erzeugen nach Bedarf, haltung in Klasse oder Thumbs im voraus erzeugen (�ber quarz beim speichern eines neues bildes thumb mitbauen)?
     * - zweites w�re deutlich performanter, w�rde jedoch evtl ein Parallel-Filesystem voraussetzen
     * 
     * Meine empfehlung:
     * Thumbs erstmal via ImageBean erzeugen und halten... sollte deutlich einfacher sein und scheis auf performance
     */
//    private Image thumb;
//    
//    static Image createThumb(String path) {
//    	// https://www.java-forum.org/thema/thumbnails-schneller-erstellen.21437/ �bernehmen?
//    }
	
	public String toString() {
		return ("Image\n" +
				"id: " + id + "\n" +
				"cam_id: " + camId + "\n" +
				"timestamp: " + timestamp + "\n");
	}
    
    
    
}