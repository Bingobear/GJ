package model;



public class WordOcc {
	private String word;
	private int occ;
//	private double tf = 0;
	private double tfidf = 0;
//	private double idf = 0;
//	private int keyinPDF = 0;
//	private int keyinCat = 0;
	//before 1
//	private int catRel = 0;
//	private double catIDF = 0;
//	private double catTF = 0;
//	private double catTFIDF = 0;
//	private boolean catRet = false;

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



	public double getTfidf() {
		return tfidf;
	}

	public void setTfidf(double tfidf) {
		this.tfidf = tfidf;
	}

//	public int getKeyinPDF() {
//		return keyinPDF;
//	}
//
//	public void incKeyinPDF() {
//		this.keyinPDF++;
//	}
//
//	public void setKeyinPDF(int keyinPDF) {
//		this.keyinPDF = keyinPDF;
//	}




	public String getWord() {
		return word;
	}



	public void setWord(String word) {
		this.word = word;
	}
}
