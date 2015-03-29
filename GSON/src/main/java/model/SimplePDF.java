package model;


public class SimplePDF {
	private String title;
private double score;
	public SimplePDF() {
		// TODO Auto-generated constructor stub
	}
	public SimplePDF(String name,double score) {
		this.title=name;
		this.score =score;
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


}
