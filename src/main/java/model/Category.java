package model;

import java.util.ArrayList;

/**Category class
 * ~ keywords known to corpus
 * @author Simon
 *
 */
public class Category {
	private String title;
	private int color;
	private String normtitle;
	// private int wOcc;
	private double relevance;
	private String assGC;

	private int edgeDegree = 0;
	private ArrayList<SimplePDF> publications = new ArrayList<SimplePDF>();

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

	public Category(String name, double relevance) {
		this.setTitle(name);
		this.setRelevance(relevance);
	}

	public Category(String title2, String normtitle) {
		this.setTitle(title2);
		this.setNormtitle(normtitle);

	}

	public Category(String name, String normtitle2, double relevance2) {
		this.setTitle(name);
		this.setNormtitle(normtitle2);
		this.setRelevance(relevance2);
	}

	public Category(String name, String normtitle2, double relevance2,
			String assGC) {
		this.setTitle(name);
		this.setNormtitle(normtitle2);
		this.setRelevance(relevance2);
		this.setAssGC(assGC);
	}

	public double getRelevance() {
		return this.relevance;
	}

	// //TODO Not relevance but #t in cat
	// public void setwOcc(int relevance) {
	// this.wOcc = relevance;
	// }

	public void incwOcc(int i) {
		this.relevance = this.relevance + 1;
	}

	public void setRelevance(double relevance) {
		this.relevance = relevance;
	}

	public void incRelevance(double i) {
		// this.relevance=this.relevance+1;
		this.relevance = this.relevance + i;
	}

	public int getColor() {
		return color;
	}

	public void setColor(int color) {
		this.color = color;
	}

	public String getNormtitle() {
		return normtitle;
	}

	public void setNormtitle(String normtitle) {
		this.normtitle = normtitle;
	}

	public String getAssGC() {
		return assGC;
	}

	public void setAssGC(String assGC) {
		this.assGC = assGC;
	}

	public int getEdgeDegree() {
		return edgeDegree;
	}

	public void setEdgeDegree(int edgeDegree) {
		this.edgeDegree = edgeDegree;
	}

	public void incEdgeDegree() {
		this.edgeDegree++;
	}

	public ArrayList<SimplePDF> getPdfs() {
		return publications;
	}

	public void setPdfs(ArrayList<SimplePDF> pdfs) {
		this.publications = pdfs;
	}
	
	public void addPDF(SimplePDF pdf){
		this.publications.add(pdf);
	}



}
