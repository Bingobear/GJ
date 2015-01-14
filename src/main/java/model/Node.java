package model;

import java.util.ArrayList;

public class Node {
	public Node(Category cat) {
		this.title = cat.getTitle();
		this.relevance = cat.getRelevance();
	}
	public Node(PDF pdf) {
		this.pdfID = pdf.getPublicationID();
		this.title = pdf.getTitle();
		this.language = pdf.getLanguage();
		this.wordOcc = pdf.getWordOccList();
		this.genericKeywords = pdf.getGenericKeywords();
	}
	private int pdfID = -1;
	private String title;
	private String language="NON";
	private double relevance=-1;
	private ArrayList<Category> genericKeywords=new ArrayList<Category>();
	private ArrayList<WordOcc> wordOcc = new ArrayList<WordOcc>();
}
