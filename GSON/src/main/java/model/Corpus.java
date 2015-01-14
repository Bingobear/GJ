package model;

import java.util.ArrayList;

public class Corpus {
	private ArrayList<Category> globalCategory = new ArrayList<Category>();

	private ArrayList<PDF> pdfList = new ArrayList<PDF>();
	
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
	public Corpus() {
		// TODO Auto-generated constructor stub
	}
	public Corpus(ArrayList<PDF> pdfList) {
		// TODO Auto-generated constructor stub
		this.setPdfList(pdfList);
	}
	public Corpus(ArrayList<Category> gCat, ArrayList<PDF> pdfList2) {
		this.setPdfList(pdfList2);
		this.setGlobalCategory(gCat);
	}

}
