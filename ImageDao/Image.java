package model;

public class Image {

	private Integer id;
	private Integer camId;
	private String path;
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

    public void setPath(String path) {
        this.path = path;
    }

    public Integer getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Integer timestamp) {
        this.timestamp = timestamp;
    }
}