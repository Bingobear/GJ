package model;

import java.util.ArrayList;

/**Corpus class 
 * @author Simon Bruns
 *
 */
public class Corpus {
	private ArrayList<Category> globalCategory = new ArrayList<Category>();

	private ArrayList<PDF> pdfList = new ArrayList<PDF>();
	
	private ArrayList<Author> allAuthors = new ArrayList<Author>();
	public ArrayList<Category> getGlobalCategory() {
		return globalCategory;
	}
	public void setGlobalCategory(ArrayList<Category> globalCategory) {
		this.globalCategory = globalCategory;
	}
	public ArrayList<PDF> getPdfList() {
		return pdfList;
	}
	public void setPdfList(ArrayList<PDF> pdfList) {
		this.pdfList = pdfList;
	}

	public Corpus(ArrayList<PDF> pdfList) {
		// TODO Auto-generated constructor stub
		this.setPdfList(pdfList);
	}
	public Corpus(ArrayList<Category> gCat, ArrayList<PDF> pdfList2,ArrayList<Author> allAuthor) {
		this.setPdfList(pdfList2);
		this.setGlobalCategory(gCat);
		this.setAllAuthors(allAuthor);
	}
	public ArrayList<Author> getAllAuthors() {
		return allAuthors;
	}
	public void setAllAuthors(ArrayList<Author> allAuthors) {
		this.allAuthors = allAuthors;
	}

}
