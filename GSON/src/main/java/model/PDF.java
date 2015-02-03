package model;

import java.util.ArrayList;

public class PDF {
	private int pdfID;
	private ArrayList<Category> genericKeywords;
	private ArrayList<WordOcc> wordOcc;
	private ArrayList<Author> authors;
	private String language;
	private int wordcount;
	//TODDO cat -> scoring optimized
	private int catnumb;

	private String shortTitle;

	private String firstPage;

	private String title;

	public int getWordcount() {
		return wordcount;
	}

	public void setWordcount(int wordcount) {
		this.wordcount = wordcount;
	}


	public PDF(ArrayList<WordOcc> words, String language, int wordcount) {
		this.wordOcc = words;
		this.wordcount = wordcount;
		this.language = language;
	}

	public PDF(ArrayList<WordOcc> words, String lang, int wordcount,
			String titlePage) {
		this.wordOcc = words;
		this.wordcount = wordcount;
		this.language = lang;
		this.firstPage = titlePage;
	}


	public PDF(String title, String shorttitle,String language, ArrayList<WordOcc> words,
			ArrayList<Category> cats, int id, ArrayList<Author> authors) {
		this.wordOcc = words;
		this.title = title;

		this.shortTitle = shorttitle;

		this.language = language;
		this.genericKeywords = cats;
		this.pdfID=id;
		this.authors = authors;
	}

	public ArrayList<WordOcc> getWordOccList() {
		return wordOcc;
	}

	public void addWordOcc(WordOcc word) {
		this.addWordOcc(word);
	}


	public void setWordOcc(ArrayList<WordOcc> wordocc) {
		this.wordOcc = wordocc;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public ArrayList<Category> getGenericKeywords() {
		return genericKeywords;
	}

	public void setGenericKeywords(ArrayList<Category> genericKeywords) {
		this.genericKeywords = genericKeywords;
	}

	public String getFirstPage() {
		return firstPage;
	}

	public void setFirstPage(String firstPage) {
		this.firstPage = firstPage;
	}

	public int getPublicationID() {
		return pdfID;
	}

	public void setPublicationID(int publicationID) {
		this.pdfID = publicationID;
	}

	public int getCatnumb() {
		return catnumb;
	}

	public void setCatnumb(int catnumb) {
		this.catnumb = catnumb;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}


	public String getShortTitle() {
		return shortTitle;
	}

	public void setShortTitle(String shortTitle) {
		this.shortTitle = shortTitle;
	}

	public ArrayList<Author> getAuthors() {
		return authors;
	}

	public void setAuthors(ArrayList<Author> authors) {
		this.authors = authors;
	}


}
