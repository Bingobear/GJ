package model;

import java.util.ArrayList;

public class AutoComplete {
private ArrayList<Category> gCats;
private ArrayList<Author> authors;


public ArrayList<Category> getgCats() {
	return gCats;
}
public void setgCats(ArrayList<Category> gCats) {
	this.gCats = gCats;
}
public ArrayList<Author> getAuthors() {
	return authors;
}
public void setAuthors(ArrayList<Author> authors) {
	this.authors = authors;
}

	public AutoComplete() {
		// TODO Auto-generated constructor stub
	}
	public AutoComplete(ArrayList<Author> allAuthors,
			ArrayList<Category> globalCategory) {
		this.authors = allAuthors;
		this.gCats = globalCategory;
	}

}
