package model;

import java.util.ArrayList;

public class Author {
	private String name;
	private ArrayList<Category> cats = new ArrayList();
	// private String adress;
	// private int pos;
	// private int authorID;

	public Author() {

	}

	public Author(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	// public String getAdress() {
	// return adress;
	// }
	// public void setAdress(String adress) {
	// this.adress = adress;
	// }
	// public int getPos() {
	// return pos;
	// }
	// public void setPos(int pos) {
	// this.pos = pos;
	// }
	// public int getAuthorID() {
	// return authorID;
	// }
	// public void setAuthorID(int authorID) {
	// this.authorID = authorID;
	// }

	public ArrayList<Category> getCats() {
		return cats;
	}

	public void setCats(ArrayList<Category> cats) {
		this.cats = cats;
	}
}
