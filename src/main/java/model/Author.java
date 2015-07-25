package model;

import java.util.ArrayList;


/**author class 
 * @author Simon Bruns
 *
 */

public class Author {
	private String name;
	private ArrayList<SimpleCategory> cats = new ArrayList();

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

	public ArrayList<SimpleCategory> getCats() {
		return cats;
	}

	public void setCats(ArrayList<SimpleCategory> cats) {
		this.cats = cats;
	}
}
