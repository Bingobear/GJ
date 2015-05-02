package model;

public class Publication {
	private String title;
	private String format;
	private int releaseDate;
	private String publisher;
	private String idBTH;
	private String origin;
	private String journaltitle;
	private String location;

	public Publication(){
		
	}
	public Publication(String title, String format, int releaseDate, String publisher,String idBTH,String origin) {
		this.setTitle(title);
		this.setFormat(format);
		this.setReleaseDate(releaseDate);
		this.setPublisher(publisher);
		this.setIdBTH(idBTH);
		this.setOrigin(origin);
		this.setLocation("RWTH, Hochschulbibliothek "); //? really only rwth bib
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getFormat() {
		return format;
	}
	public void setFormat(String format) {
		this.format = format;
	}
	public int getReleaseDate() {
		return releaseDate;
	}
	public void setReleaseDate(int releaseDate) {
		this.releaseDate = releaseDate;
	}
	public String getPublisher() {
		return publisher;
	}
	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}
	public String getIdBTH() {
		return idBTH;
	}
	public void setIdBTH(String idBTH) {
		this.idBTH = idBTH;
	}
	public String getOrigin() {
		return origin;
	}
	public void setOrigin(String origin) {
		this.origin = origin;
	}
	public String getJournaltitle() {
		return journaltitle;
	}
	public void setJournaltitle(String journaltitle) {
		this.journaltitle = journaltitle;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
}
