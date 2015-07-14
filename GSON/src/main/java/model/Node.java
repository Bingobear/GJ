package model;

import java.util.ArrayList;

/**Node Interface to define publication and category node info
 * @author Simon
 *
 */
public class Node {
	private String id="";
	private String title;
	private int group = -1;
	private int number = -1;
	private int pdfID = -1;
	private String language = "NON";
	private double relevance = -1;
	private boolean calcRel;
	private ArrayList<Category> genericKeywords = new ArrayList<Category>();
	private ArrayList<WordOcc> wordOcc = new ArrayList<WordOcc>();
	private ArrayList<Author> authors = new ArrayList<Author>();
	private Publication publication;
	private String type;
	private String shorttitle;
	private int edgeDegree = -1;
	private int color = -1;
	private String normtitle="";
	private String fileN="";
	private ArrayList<SimplePDF> publications = new ArrayList<SimplePDF>();



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
		this.id = pdf.getNormtitle();
	}

	public Node(Category cat, int counter) {
		this.id = cat.getTitle();
		this.group = cat.getColor();
		this.title = cat.getTitle();
		this.relevance = cat.getRelevance();
		this.number = counter;
	}

	public Node(PDF pdf, int counter) {
		this.id = pdf.getTitle();
		this.title = pdf.getTitle();
		this.pdfID = pdf.getPublicationID();
		this.language = pdf.getLanguage();
		this.wordOcc = pdf.getWordOccList();
		this.genericKeywords = pdf.getGenericKeywords();
		this.authors = pdf.getAuthors();
		this.number = counter;

	}

	public Node(Category cat, int counter, String type) {
		this.id = cat.getNormtitle();
		this.group = cat.getColor();
		this.title = cat.getTitle();
		this.relevance = cat.getRelevance();
		this.number = counter;
		this.type = type;
		this.edgeDegree = cat.getEdgeDegree();
		this.color = cat.getColor();
		this.shorttitle = cat.getTitle();
		this.normtitle = cat.getNormtitle();
		this.publications = cat.getPdfs();
	}

	public Node(PDF pdf, int counter, String type) {
		this.id = pdf.getNormtitle();
		this.title = pdf.getTitle();
		this.shorttitle = pdf.getShortTitle();
		this.publication = pdf.getPub();
		this.pdfID = pdf.getPublicationID();
		this.language = pdf.getLanguage();
		this.wordOcc = pdf.getWordOccList();
		this.calcRel = pdf.getCalcRel();
		this.genericKeywords = pdf.getGenericKeywords();
		this.number = counter;
		this.type = type;
		this.authors = pdf.getAuthors();
		this.fileN = pdf.getFileN();

	}
}
