package model;

import java.util.ArrayList;

public class Node {
	private String title;
	//HOW TO COLOR CODE GROUPS
	private int group=-1;
	private int number=-1;
	private int pdfID = -1;
	private String language="NON";
	private double relevance=-1;
	private ArrayList<Category> genericKeywords=new ArrayList<Category>();
	private ArrayList<WordOcc> wordOcc = new ArrayList<WordOcc>();
	
	public Node(Category cat) {
		this.title = cat.getTitle();
		this.group = cat.getColor();
		this.relevance = cat.getRelevance();
	}
	public Node(PDF pdf) {
		this.title = pdf.getTitle();
		this.pdfID = pdf.getPublicationID();
		this.language = pdf.getLanguage();
		this.wordOcc = pdf.getWordOccList();
		this.genericKeywords = pdf.getGenericKeywords();
	}
	public Node(Category cat, int counter) {
		this.group=cat.getColor();
		this.title = cat.getTitle();
		this.relevance = cat.getRelevance();
		this.number = counter;
	}
	public Node(PDF pdf, int counter) {
		this.title = pdf.getTitle();
		this.pdfID = pdf.getPublicationID();
		this.language = pdf.getLanguage();
		this.wordOcc = pdf.getWordOccList();
		this.genericKeywords = pdf.getGenericKeywords();
		this.number = counter;
	}

}
