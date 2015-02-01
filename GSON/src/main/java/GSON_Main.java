import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import model.*;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GSON_Main {
	public GSON_Main() {

	}

	public static void main(String[] args) {
		DataBase db = new DataBase();

		Corpus corpus = db.retrieveDB();
		// System.out.println(corpus.getPdfList().size());

		/*
		 * with white spaces
		 * 
		 * String jsonOutput = gson.toJson(someObject);
		 */
		Gson gsona = new Gson();
		DDDFormat djson = calculateJSONV(corpus);
		// Gson gson = new GsonBuilder().setPrettyPrinting().create();

		String alljson = gsona.toJson(djson);
		writeDJSON(alljson);
	}

	private static DDDFormat  calculateJSONV(Corpus corpus) {
		corpus.setPdfList(normPDFRel(corpus.getPdfList()));
		ArrayList<Link> links = generateLinks(corpus);
		ArrayList<Node> nodes = generateNodes(corpus);
		DDDFormat djson = new DDDFormat(nodes, links);
		return djson;
		
	}

	//NORMALIZE CATEGORY RELEVANCE PER PDF
	private static ArrayList<PDF> normPDFRel(ArrayList<PDF> pdfList) {
		double max = 100;
		double min = 25;
		double range = max - min;
		for (int ii = 0; ii < pdfList.size(); ii++) {
			ArrayList<Category> pdfCats = pdfList.get(ii).getGenericKeywords();
			double maxOld = getMaxRel(pdfList.get(ii));
			double minOld = getMinRel(pdfList.get(ii));
			double rangeOld = maxOld - minOld;
			for (int jj = 0; jj < pdfCats.size(); jj++) {
				double currentVal = pdfCats.get(jj).getRelevance();
				if (rangeOld == 0) {
					pdfCats.get(jj).setRelevance(50);
				} else {
					pdfCats.get(jj).setRelevance((int)
							(((currentVal - minOld) * range) / rangeOld) + min);
				}
			}
		}
		return pdfList;
	}

	private static double getMinRel(PDF pdf) {
		ArrayList<Category> pdfCats = pdf.getGenericKeywords();
		double min = 999;
		for (int jj = 0; jj < pdfCats.size(); jj++) {
			if (min > pdfCats.get(jj).getRelevance()) {
				min = pdfCats.get(jj).getRelevance();
			}
		}
		return min;
	}

	private static double getMaxRel(PDF pdf) {
		ArrayList<Category> pdfCats = pdf.getGenericKeywords();
		double max = 0;
		for (int jj = 0; jj < pdfCats.size(); jj++) {
			if (max < pdfCats.get(jj).getRelevance()) {
				max = pdfCats.get(jj).getRelevance();
			}
		}
		return max;
	}

	private static ArrayList<Node> generateNodes(Corpus corpus) {
		ArrayList<Category> cats = corpus.getGlobalCategory();
		ArrayList<PDF> pdfs = corpus.getPdfList();
		ArrayList<Node> nodes = new ArrayList<Node>();
		int counter = 1;
		for (Category cat : cats) {
			nodes.add(new Node(cat, counter, "circle"));
			counter++;
		}
		for (PDF pdf : pdfs) {
			nodes.add(new Node(pdf, counter, "square"));
			counter++;
		}
		return nodes;
	}

	private static void writeDJSON(String alljson) {
		try {
			// write converted json data to a file named "CountryGSON.json"
			FileWriter writer = new FileWriter("c:/RWTH/Data/hcicorpus.json");

			writer.write(alljson);
			writer.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private static ArrayList<Link> generateLinks(Corpus corpus) {
		ArrayList<PDF> pdfList = corpus.getPdfList();
		ArrayList<Category> gCatList = corpus.getGlobalCategory();
		ArrayList<Link> links = new ArrayList<Link>();
		ArrayList<Category> newgCat = new ArrayList<Category>();
		for (int counter = 0; counter < pdfList.size(); counter++) {
			ArrayList<Category> pCatList = pdfList.get(counter)
					.getGenericKeywords();
			for (Category cat : pCatList) {
				String pdfCtitle = cat.getNormtitle();

				for (int counterG = 0; counterG < gCatList.size(); counterG++) {
					String gCtitle = gCatList.get(counterG).getNormtitle();
					if (pdfCtitle == null) {
						break;
					}
					if (gCtitle == null) {
						continue;
					}
					Boolean found = false;
					if (pdfCtitle.equals(gCtitle)) {
						found = true;
					} else if (cat.getAssGC() != null) {
						if (cat.getAssGC().equals(gCtitle)) {
							found = true;
						}
					}

					// if
					// ((pdfCtitle.equals(gCtitle))||(cat.getAssGC().equals(gCtitle)))
					// {
					if (found) {
						// System.out.println(cat.getRelevance());
						// if(pdfCtitle.equals("AGE")){
						// String test = "";
						// }
						int position = -1;
						if (newgCat.isEmpty()) {
							//TODO Evaluate if incEdgeDegree works
							gCatList.get(counterG).incEdgeDegree();
							newgCat.add(gCatList.get(counterG));
							position = newgCat.size() - 1;
							links.add(new Link(counter, position, cat
									.getRelevance()));
							break;
						} else {
							for (int ii = 0; ii < newgCat.size(); ii++) {
								String wordA = newgCat.get(ii).getNormtitle();
								String wordB = gCatList.get(counterG)
										.getNormtitle();
								int ld = AlgorithmUtil.LevenshteinDistance(
										wordA, wordB);
								double sim = 0;
								if (wordA.length() > wordB.length()) {
									sim = AlgorithmUtil.calculateWordSim(wordA,
											ld);
								} else {
									sim = AlgorithmUtil.calculateWordSim(wordB,
											ld);
								}
								if (sim <= 0.2) {
									// if (newgCat
									// .get(ii)
									// .getTitle()
									// .equals(gCatList.get(counterG)
									// .getTitle())) {
									position = ii;
									newgCat.get(ii).incEdgeDegree();
									break;
								}
							}
							if (position == -1) {
								gCatList.get(counterG).incEdgeDegree();
								newgCat.add(gCatList.get(counterG));
								position = newgCat.size() - 1;

							}
						}

						links.add(new Link(counter, position, cat
								.getRelevance()));
					}
				}
			}

		}
		for (Link current : links) {
			current.setSource(current.getSource() + newgCat.size());
		}
		for (int ii = 0; ii < newgCat.size(); ii++) {
			newgCat.get(ii).setColor(ii);
		}
		corpus.setGlobalCategory(newgCat);
		return links;
	}

}
