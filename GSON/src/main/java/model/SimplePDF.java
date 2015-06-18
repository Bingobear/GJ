package model;

public class SimplePDF {
	private String title;
	private double score;
	private String id;
	private int releaseDate;
	private String journaltitle;
	private String origin;
	private String publisher;
	private boolean calcRel;

	public SimplePDF() {
		// TODO Auto-generated constructor stub
	}
public SimplePDF (String name, double score,String id){
	this.title = name;
	this.score = score;
	this.id = id;
}
	public SimplePDF(String name, double score,int releaseDate,String journaltitle,String origin, String publisher,boolean calcRel,String id) {
		this.title = name;
		this.score = score;
		this.releaseDate=releaseDate;
		this.journaltitle=journaltitle;
		this.origin = origin;
		this.publisher = publisher;
		this.calcRel = calcRel;
		this.id = id;
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
	public boolean isCalcRel() {
		return calcRel;
	}
	public void setCalcRel(boolean calcRel) {
		this.calcRel = calcRel;
	}

}
