package model;

public class SimplePDF {
	private String title;
	private double score;
	private int releaseDate;
	private String journaltitle;
	private String origin;
	private String publisher;

	public SimplePDF() {
		// TODO Auto-generated constructor stub
	}
public SimplePDF (String name, double score){
	this.title = name;
	this.score = score;
}
	public SimplePDF(String name, double score,int releaseDate,String journaltitle,String origin, String publisher) {
		this.title = name;
		this.score = score;
		this.releaseDate=releaseDate;
		this.journaltitle=journaltitle;
		this.origin = origin;
		this.publisher = publisher;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public double getScore() {
		return score;
	}

	public void setScore(double score) {
		this.score = score;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public int getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(int releaseDate) {
		this.releaseDate = releaseDate;
	}

}
