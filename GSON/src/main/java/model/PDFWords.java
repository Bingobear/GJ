package model;

import java.util.ArrayList;

public class PDFWords {
	ArrayList<WordOcc> words = new ArrayList<WordOcc>();
	String fileN;
	
	public PDFWords(ArrayList<WordOcc> words,String fileN){
		this.words=words;
		this.fileN=fileN;
	}

	public ArrayList<WordOcc> getWords() {
		return words;
	}

	public void setWords(ArrayList<WordOcc> words) {
		this.words = words;
	}

	public String getFileN() {
		return fileN;
	}

	public void setFileN(String fileN) {
		this.fileN = fileN;
	}
}
