package model;

import java.util.ArrayList;

/**
 * Minimized PDF class to minimize file size
 * 
 * @author Simon
 *
 */
public class SimplePDF {
	private String title;
	private double score;
	private String id;
	private int releaseDate;
	private String journaltitle;
	private String origin;
	private String publisher;
	private boolean calcRel;
	private ArrayList<String> keywordList = new ArrayList<String>();

	public SimplePDF() {
		// TODO Auto-generated constructor stub
	}

	public SimplePDF(String name, double score, String id,
			ArrayList<Category> keyw) {
		this.title = name;
		this.score = score;
		this.id = id;
		for (int ii = 0; ii < keyw.size(); ii++) {
			keywordList.add(keyw.get(ii).getNormtitle());
		}
	}

	public SimplePDF(String name, double score, int releaseDate,
			String journaltitle, String origin, String publisher,
			boolean calcRel, String id, ArrayList<Category> keyw) {
		this.title = name;
		this.score = score;
		this.releaseDate = releaseDate;
		this.journaltitle = journaltitle;
		this.origin = origin;
		this.publisher = publisher;
		this.calcRel = calcRel;
		this.id = id;
		String affe = this.id;

		for (int ii = 0; ii < keyw.size(); ii++) {
			keywordList.add(keyw.get(ii).getNormtitle());
		}
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public double getScore() {
		return score;
	}

	public void setScore(double score) {
		this.score = score;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public int getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(int releaseDate) {
		this.releaseDate = releaseDate;
	}

	public boolean isCalcRel() {
		return calcRel;
	}

	public void setCalcRel(boolean calcRel) {
		this.calcRel = calcRel;
	}

}
