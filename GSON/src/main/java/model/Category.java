package model;

public class Category {
private String title;
private int color;
//private int wOcc;
private double relevance;
	public String getTitle() {
	return title;
}
public void setTitle(String title) {
	this.title = title;
}
	public Category() {
		// TODO Auto-generated constructor stub
	}
	public Category(String title) {
		this.setTitle(title);
	}
	
	public Category(String name,double relevance) {
		this.setTitle(name);
		this.setRelevance(relevance);
	}
	public double getRelevance() {
		return this.relevance;
	}
	
//	//TODO Not relevance but #t in cat
//	public void setwOcc(int relevance) {
//		this.wOcc = relevance;
//	}
	
	public void incwOcc(int i) {
		this.relevance=this.relevance+1;
	}


	public void setRelevance(double relevance) {
		this.relevance = relevance;
	}
	
	public void incRelevance(double i) {
		//this.relevance=this.relevance+1;
		this.relevance=this.relevance+i;
	}
	public int getColor() {
		return color;
	}
	public void setColor(int color) {
		this.color = color;
	}

}
