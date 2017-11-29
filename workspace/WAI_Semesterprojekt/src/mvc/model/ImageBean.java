package mvc.model;

public class ImageBean {

	private Integer id;
	private Integer camId;
	private String path;
	private String thumbPath;
	private Integer year;
	private Integer month;
	private Integer day;
	private Integer hour;


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
     * 	setPath dahingehend abändern das darüber automatisch thumbs erzeugt werden
     */
    public void setPath(String path) {
        this.path = path;
        // this.thumb = ImageBean::createThumb(path);
    }

	public String getThumbPath() {
		return thumbPath;
	}

	public void setThumbPath(String thumbPath) {
		this.thumbPath = thumbPath;
	}
	
    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public Integer getDay() {
        return day;
    }

    public void setDay(Integer day) {
        this.day = day;
    }

    public Integer getHour() {
        return hour;
    }

    public void setHour(Integer hour) {
        this.hour = hour;
    }
    
    public String getDate() {
    	return day + "." + month + "." + year;
    }
    
    public String getTime() {
    	String h = "" + hour;
    	return transformTime(h);
    }
    
    public String getTimeStamp() {
    	return getDate() + "\n" + getTime();
    } 
    
    static public String transformTime(String time) {
    	int len = time.length();
    	String t;
    	switch(len) {
    	case 5:
    		t = time.substring(0, 1) + ":" + time.substring(1, 3) + ":" + time.substring(3, 5);
    		break;
    	case 6:
    		t = time.substring(0, 2) + ":" + time.substring(2, 4) + ":" + time.substring(4, 6);
    		break;
    	default:
    		t = null;	
    	}
    	return t;
    }
}