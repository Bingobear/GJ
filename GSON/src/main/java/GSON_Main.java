import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import model.*;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;

/**GSON Interface to generate JSON from DB entries
 * @author Simon Bruns
 *
 */
public class GSON_Main {
	//TWO MODES Available
	//modeC true -> generate basic jsons
	//modeC false -> generate wordcloud jsons
	static boolean modeC = true;

	public GSON_Main() {

	}

	/**Handles the creation of all JSONS
	 * @param args
	 */
	public static void main(String[] args) {
		DataBase db = new DataBase();
		if (modeC) {
			Corpus corpus = db.retrieveDB();

			Gson gsona = new Gson();
			DDDFormat djson = calculateJSONV(corpus);
			// Gson gson = new GsonBuilder().setPrettyPrinting().create();
			String author = gsona.toJson(corpus.getAllAuthors());
			ArrayList<String> authorACs = getAuthors(corpus.getAllAuthors());
			ArrayList<String> publishers = getPublisher(corpus);
			String pubs = gsona.toJson(publishers);
			ArrayList<String> origins = getOrigins(corpus);
			String origs = gsona.toJson(origins);
			String authorAC = gsona.toJson(authorACs);
			String alljson = gsona.toJson(djson);
			ArrayList<String> autoCo = createAC(corpus);
			// String autoC = gsona.toJson(new
			// AutoComplete(corpus.getAllAuthors(),corpus.getGlobalCategory()));
			String autoC = gsona.toJson(autoCo);
			String path = "c:/RWTH/Data/";
			writeDJSON(alljson, "hcicorpus",path);// hcicorpus corpus
			writeDJSON(author, "author",path);
			writeDJSON(autoC, "autocomplete",path);
			writeDJSON(authorAC, "authorAC",path);
			writeDJSON(origs, "originsAC",path);
			writeDJSON(pubs, "publishersAC",path);
		} else {
			Corpus corpus = db.retrieveDB();
			ArrayList<PDFWords> completeList = getPWords(corpus);
			Gson gsona = new Gson();
			String wordcloudjson = gsona.toJson(completeList);
			writeDJSON(wordcloudjson, "wordCloud","c:/RWTH/Data/");
		}
	}

	/**Retrieves all origins known to the corpus as a string
	 * @param corpus
	 * @return arraylists with origins
	 */
	private static ArrayList<String> getOrigins(Corpus corpus) {
		ArrayList<PDF> pdfs = corpus.getPdfList();
		ArrayList<String> result = new ArrayList<String>();
		for (int ii = 0; ii < pdfs.size(); ii++) {
			if (pdfs.get(ii).getPub() != null) {
				String current = pdfs.get(ii).getPub().getOrigin();
				if (pdfs.get(ii).getPub().getOrigin() != null) {
					if (!result.contains(current)) {
						result.add(pdfs.get(ii).getPub().getOrigin());
					}
				}
			}
		}
		return result;
	}

	/**Retrieves all publisher known to the corpus as a string
	 * @param corpus
	 * @return arraylist publisher
	 */
	private static ArrayList<String> getPublisher(Corpus corpus) {
		ArrayList<PDF> pdfs = corpus.getPdfList();
		ArrayList<String> result = new ArrayList<String>();
		for (int ii = 0; ii < pdfs.size(); ii++) {
			if (pdfs.get(ii).getPub() != null) {
				String current = pdfs.get(ii).getPub().getPublisher();
				if (pdfs.get(ii).getPub().getPublisher() != null) {
					if (!result.contains(current)) {
						result.add(pdfs.get(ii).getPub().getPublisher());
					}
				}
			}
		}
		return result;
	}

	/**Retrieves all authors known to the corpus in string form
	 * @param allAuthors
	 * @return arraylist authors
	 */
	private static ArrayList<String> getAuthors(ArrayList<Author> allAuthors) {
		ArrayList<String> authors = new ArrayList<String>();
		for (int ii = 0; ii < allAuthors.size(); ii++) {
			String name = allAuthors.get(ii).getName();
			String[] parts = name.split(",");
			authors.add(parts[0]);
		}
		return authors;
	}

	/**Generates pdf with only words and their filename
	 * @param corpus
	 * @return
	 */
	private static ArrayList<PDFWords> getPWords(Corpus corpus) {
		ArrayList<PDFWords> result = new ArrayList<PDFWords>();
		ArrayList<PDF> pdfL = corpus.getPdfList();
		for (int ii = 0; ii < pdfL.size(); ii++) {
			String fileN = pdfL.get(ii).getFileN();
			ArrayList<WordOcc> words = pdfL.get(ii).getWordOccList();
			result.add(new PDFWords(words, fileN));
		}
		return result;
	}

	/**Generates Autocomplete for keywords
	 * @param corpus
	 * @return
	 */
	private static ArrayList<String> createAC(Corpus corpus) {
		ArrayList<String> all = new ArrayList<String>();

		for (int ii = 0; ii < corpus.getGlobalCategory().size(); ii++) {
			all.add(corpus.getGlobalCategory().get(ii).getTitle());
		}
		return all;
	}

	/**Formats corpus into json object consisting of nodes,links (for graph component)
	 * @param corpus
	 * @return
	 */
	private static DDDFormat calculateJSONV(Corpus corpus) {
		corpus.setPdfList(normPDFRel(corpus.getPdfList()));
		ArrayList<Link> links = generateLinks(corpus);
		// addCatToPDFAuthor(corpus);
		ArrayList<Node> nodes = generateNodes(corpus);
		DDDFormat djson = new DDDFormat(nodes, links);
		return djson;

	}

	/** Old not used
	 * @param corpus
	 */
	private static void addCatToPDFAuthor(Corpus corpus) {
		for (PDF pdf : corpus.getPdfList()) {
			int numberAuth = 0;
			for (Author auth : pdf.getAuthors()) {
				for (Author global : corpus.getAllAuthors()) {
					if (global.getName().equals(auth.getName())) {
						auth.setCats(global.getCats());
						numberAuth++;
						break;

					}
				}
				if (numberAuth > pdf.getAuthors().size()) {
					break;
				}
			}
		}
	}

	/**NORMALIZE CATEGORY RELEVANCE OF ALL PDFs
	 * @param pdfList
	 * @return
	 */
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
					pdfList.get(ii).setCalcRel(false);
				} else {
					pdfCats.get(jj).setRelevance(
							(int) (((currentVal - minOld) * range) / rangeOld)
									+ min);
				}
			}
		}
		return pdfList;
	}

	/**Calculates min relevance of current pdf
	 * @param pdf
	 * @return min relevance
	 */
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

	/**Calculates max relevance of current pdf
	 * @param pdf
	 * @return max relevance
	 */
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

	/**Generates all Nodes Java for GSON
	 * @param corpus
	 * @return arraylist of nodes
	 */
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

	/**Creates json file
	 * @param alljson
	 * @param fileN
	 * @param path (location where to save json)
	 */
	private static void writeDJSON(String alljson, String fileN,String path) {
		try {
			// write converted json data to a file named "CountryGSON.json"
			FileWriter writer = new FileWriter(path + fileN
					+ ".json");

			writer.write(alljson);
			writer.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**Generate links for GSON (for graph)
	 * Thereby calculates globalcategories and joins similar ones together
	 * @param corpus
	 * @return
	 */
	private static ArrayList<Link> generateLinks(Corpus corpus) {
		ArrayList<PDF> pdfList = corpus.getPdfList();
		ArrayList<Category> gCatList = corpus.getGlobalCategory();
		ArrayList<Link> links = new ArrayList<Link>();
		//List of categories, which are unique
		ArrayList<Category> newgCat = new ArrayList<Category>();
		for (int counter = 0; counter < pdfList.size(); counter++) {
			PDF current = pdfList.get(counter);
			ArrayList<Category> pCatList = pdfList.get(counter)
					.getGenericKeywords();
			// work around for authors
			ArrayList<Category> pgCat = new ArrayList<Category>();
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
							//added so not unnecessary values exist
							pdfCtitle = gCtitle;
							cat.setTitle(gCatList.get(counterG).getTitle());
							cat.setNormtitle(gCtitle);
							found = true;
						}
					}

					if (found) {
						int position = -1;
						if (newgCat.isEmpty()) {
							pgCat.add(gCatList.get(counterG));
							gCatList.get(counterG).incEdgeDegree();
							if (current.getPub() == null) {
								gCatList.get(counterG).addPDF(
										new SimplePDF(current.getTitle(), cat
												.getRelevance(),current.getNormtitle(),current.getGenericKeywords()));
							} else {
								Publication pub = current.getPub();
								gCatList.get(counterG).addPDF(
										new SimplePDF(current.getTitle(), cat
												.getRelevance(), pub
												.getReleaseDate(), pub
												.getJournaltitle(), pub
												.getOrigin(), pub
												.getPublisher(),current.getCalcRel(),current.getNormtitle(),current.getGenericKeywords()));
							}
							newgCat.add(gCatList.get(counterG));
							position = newgCat.size() - 1;
							links.add(new Link(counter, position, cat
									.getRelevance(), pdfList.get(counter)//getTitle
									.getNormtitle(), newgCat.get(position)
									.getNormtitle()));
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
									position = ii;
									newgCat.get(ii).incEdgeDegree();
									if (current.getPub() == null) {
										newgCat.get(ii).addPDF(
												new SimplePDF(current
														.getTitle(), cat
														.getRelevance(),current.getNormtitle(),current.getGenericKeywords()));
									} else {
										Publication pub = current.getPub();
										newgCat.get(ii).addPDF(
												new SimplePDF(current
														.getTitle(), cat
														.getRelevance(), pub
														.getReleaseDate(), pub
														.getJournaltitle(), pub
														.getOrigin(), pub
														.getPublisher(),current.getCalcRel(),current.getNormtitle(),current.getGenericKeywords()));
									}
									pgCat.add(newgCat.get(ii));
									break;
								}
							}
							if (position == -1) {
								gCatList.get(counterG).incEdgeDegree();
								if (current.getPub() == null) {
									gCatList.get(counterG).addPDF(
											new SimplePDF(current.getTitle(),
													cat.getRelevance(),current.getNormtitle(),current.getGenericKeywords()));
								} else {
									Publication pub = current.getPub();
									gCatList.get(counterG).addPDF(
											new SimplePDF(current.getTitle(),
													cat.getRelevance(), pub
															.getReleaseDate(),
													pub.getJournaltitle(), pub
															.getOrigin(), pub
															.getPublisher(),current.getCalcRel(),current.getNormtitle(),current.getGenericKeywords()));
								}
								newgCat.add(gCatList.get(counterG));
								pgCat.add(gCatList.get(counterG));
								position = newgCat.size() - 1;

							}
						}
						links.add(new Link(counter, position, cat
								.getRelevance(), pdfList.get(counter)
								.getNormtitle(), newgCat.get(position)
								.getNormtitle()));
					}
				}
			}
			addCatToAuthor(pdfList.get(counter), corpus, pgCat);

		}
		for (int ii = 0; ii < newgCat.size(); ii++) {
			newgCat.get(ii).setColor(ii);
		}
		ArrayList<Author> authHelp = new ArrayList<Author>();

		for (int ii = 0; ii < corpus.getAllAuthors().size(); ii++) {
			if (corpus.getAllAuthors().get(ii).getCats().size() > 0) {
				authHelp.add(corpus.getAllAuthors().get(ii));
			}
		}
		corpus.setAllAuthors(authHelp);

		corpus.setGlobalCategory(newgCat);
		return links;
	}


	/**Add to authors their corresponding categories
	 * @param pdf
	 * @param corpus
	 * @param pgCat
	 */
	private static void addCatToAuthor(PDF pdf, Corpus corpus,
			ArrayList<Category> pgCat) {
		ArrayList<Author> authors = corpus.getAllAuthors();

		for (int ii = 0; ii < pdf.getAuthors().size(); ii++) {
			for (int jj = 0; jj < authors.size(); jj++) {
		
				if (pdf.getAuthors().get(ii).getName()
						.equals(authors.get(jj).getName())) {
					for (int kk = 0; kk < pgCat.size(); kk++) {
						if (!authors.get(jj).getCats().contains(pgCat.get(kk))) {
							Category current = pgCat.get(kk);
							SimpleCategory newCat = new SimpleCategory(
									current.getTitle(), current.getNormtitle(),
									current.getAssGC());
							authors.get(jj).getCats().add(newCat);
						}
					}
				}
			}
		}
	}
}
