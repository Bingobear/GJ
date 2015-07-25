package model;

/**Simplified Category class - to minimize data 
 * @author Simon Bruns
 *
 */
public class SimpleCategory {
private String title;
private String normtitle;
private String assGC;
	public String getTitle() {
	return title;
}
public void setTitle(String title) {
	this.title = title;
}
	public SimpleCategory() {
		// TODO Auto-generated constructor stub
	}
	public SimpleCategory(String title) {
		this.setTitle(title);
	}
	
	public SimpleCategory(String name,double relevance) {
		this.setTitle(name);
	}
	public SimpleCategory(String title2, String normtitle) {
		this.setTitle(title2);
		this.setNormtitle(normtitle);
	}
	public SimpleCategory(String name, String normtitle2, String assGC) {
		this.setTitle(name);
		this.setNormtitle(normtitle2);
		this.setAssGC(assGC);
	}
	public SimpleCategory(String name, String normtitle2, double relevance2,
			String assGC) {
		this.setTitle(name);
		this.setNormtitle(normtitle2);
		this.setAssGC(assGC);
	}

	public String getNormtitle() {
		return normtitle;
	}
	public void setNormtitle(String normtitle) {
		this.normtitle = normtitle;
	}
	public String getAssGC() {
		return assGC;
	}
	public void setAssGC(String assGC) {
		this.assGC = assGC;
	}


}
