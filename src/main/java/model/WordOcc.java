package model;



/**Word Occ class - defining word, its occurence and tfidf value
 * @author Simon Bruns
 *
 */
public class WordOcc {
	private String word;
	private int occ;
	private double tfidf = 0;


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
