package model;



public class WordOcc {
	private String word;
	private int occ;
	private double tf = 0;
	private double tfidf = 0;
	private double idf = 0;
	private int keyinPDF = 0;
	private int keyinCat = 0;
	//before 1
	private int catRel = 0;
	private double catIDF = 0;
	private double catTF = 0;
	private double catTFIDF = 0;
	private boolean catRet = false;

	WordOcc() {

	}



	public WordOcc(String word, int count, double tfidf) {
		this.setWord(word);
		this.occ=count;
		this.tfidf = tfidf;
	}


	public int getOcc() {
		return occ;
	}

	public void setOcc(int occ) {
		this.occ = occ;
	}

	public double getTf() {
		return tf;
	}

	public void setTf(double tf) {
		this.tf = tf;
	}

	public double getTfidf() {
		return tfidf;
	}

	public void setTfidf(double tfidf) {
		this.tfidf = tfidf;
	}

	public int getKeyinPDF() {
		return keyinPDF;
	}

	public void incKeyinPDF() {
		this.keyinPDF++;
	}

	public void setKeyinPDF(int keyinPDF) {
		this.keyinPDF = keyinPDF;
	}

	public double getIdf() {
		return idf;
	}

	public void setIdf(double idf) {
		this.idf = idf;
	}

	public int getCatRel() {
		return catRel;
	}

	public void setCatRel(int catRel) {
		this.catRel = catRel;
	}

	public double getCatIDF() {
		return catIDF;
	}

	public void setCatIDF(double d) {
		this.catIDF = d;
	}

	public double getCatTFIDF() {
		return catTFIDF;
	}

	public void setCatTFIDF(double tfidf2) {
		this.catTFIDF = tfidf2;
	}

	public int getKeyinCat() {
		return keyinCat;
	}

	public void setKeyinCat(int keyinCat) {
		this.keyinCat = keyinCat;
	}

	public void incKeyinCat() {
		this.keyinCat++;
	}

	public double getCatTF() {
		return catTF;
	}

	public void setCatTF(double catTF) {
		this.catTF = catTF;
	}

	public boolean isCatRet() {
		return catRet;
	}

	public void setCatRet(boolean catRet) {
		this.catRet = catRet;
	}



	public String getWord() {
		return word;
	}



	public void setWord(String word) {
		this.word = word;
	}
}
