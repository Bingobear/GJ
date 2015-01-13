package model;

import java.util.ArrayList;

public class Corpus {
	ArrayList<PDF> pdfList = new ArrayList<PDF>();
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

}
