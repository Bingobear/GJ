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
		// Gson gson = new GsonBuilder().setPrettyPrinting().create();

		ArrayList<Link> links = generateLinks(corpus);
		ArrayList<Node> nodes = generateNodes(corpus);
		DDDFormat djson = new DDDFormat(nodes, links);
		String alljson = gsona.toJson(djson);
		writeDJSON(alljson);

		// Gson gson = new Gson();
		// gson.toJson(1);
		// gson.toJson("abcd");
		// gson.toJson(new Long(10));
		// int[] values = { 1 };
		// gson.toJson(values);
		// System.out.print(json);

	}

	private static ArrayList<Node> generateNodes(Corpus corpus) {
		ArrayList<Category> cats = corpus.getGlobalCategory();
		ArrayList<PDF> pdfs = corpus.getPdfList();
		ArrayList<Node> nodes = new ArrayList<Node>();
		int counter = 1;
		for (Category cat : cats) {
			nodes.add(new Node(cat,counter,"circle"));
			counter++;
		}
		for (PDF pdf : pdfs) {
			nodes.add(new Node(pdf,counter,"square"));
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
				if(pdfCtitle.equals("AGE")){
					String test = "";
				}
				for (int counterG = 0; counterG < gCatList.size(); counterG++) {
					String gCtitle = gCatList.get(counterG).getNormtitle();
					if(pdfCtitle==null){
						break;
					}
					if(gCtitle ==null){
						continue;
					}
					Boolean found = false;
					if(pdfCtitle.equals(gCtitle)){
						found = true;
					}
					else if(cat.getAssGC()!=null){
						if(cat.getAssGC().equals(gCtitle)){
							found = true;
						}
					}

//					if ((pdfCtitle.equals(gCtitle))||(cat.getAssGC().equals(gCtitle))) {
					if(found){
						System.out.println(cat.getRelevance());
						if(pdfCtitle.equals("AGE")){
							String test = "";
						}
						int position = -1;
						if (newgCat.isEmpty()) {
							newgCat.add(gCatList.get(counterG));
							position = newgCat.size() - 1;
							links.add(new Link(counter, position, cat
									.getRelevance()));
							break;
						} else {
							for (int ii = 0; ii < newgCat.size(); ii++) {
								String wordA = newgCat
										.get(ii)
										.getNormtitle();
								String wordB = gCatList.get(counterG)
										.getNormtitle();
								int ld = AlgorithmUtil.LevenshteinDistance(wordA, wordB);
								double sim = 0;
								if(wordA.length()>wordB.length()){
									sim = AlgorithmUtil.calculateWordSim(wordA, ld);
								}else{
									sim = AlgorithmUtil.calculateWordSim(wordB, ld);
								}
								if(sim<=0.2){
//								if (newgCat
//										.get(ii)
//										.getTitle()
//										.equals(gCatList.get(counterG)
//												.getTitle())) {
									position = ii;
									break;
								}
							}
							if (position == -1) {
								newgCat.add(gCatList.get(counterG));
								position = newgCat.size() - 1;
								if(pdfCtitle.equals("AGE")){
									String test = "";
								}
							}
						}
						if(pdfCtitle.equals("AGE")){
							String test = "";
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
		for(int ii=0;ii<newgCat.size();ii++){
			newgCat.get(ii).setColor(ii);
		}
		corpus.setGlobalCategory(newgCat);
		return links;
	}

}
