package mvc.model;

public class CamBean {
	

	private int 	id;
	private String 	url;
	private String 	name;
	
	public CamBean() { 
		setId(-1);
		setUrl(null);
		setName(null);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String toString() {
		return ("ID  : " + id + "\n" +
				"URL : " + url + "\n" +
				"Name: " + name + "\n");
	}
	

}
