import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import model.*;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GSON_Main {
	public GSON_Main() {

	}
	public static void main(String[] args)   {
		DataBase db = new DataBase();


		Corpus corpus = db.retrieveDB();
		//System.out.println(corpus.getPdfList().size());

		
		Test_Object obj = new Test_Object();
		/*with white spaces

		String jsonOutput = gson.toJson(someObject);*/
		Gson gsona = new Gson();
//		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String jsonNodes = gsona.toJson(corpus); 
		ArrayList<Link> links = generateLinks(corpus);
		String jsonLinks = gsona.toJson(links);
		writeDJSON(jsonNodes,jsonLinks);






//		Gson gson = new Gson();
//		gson.toJson(1);            
//		gson.toJson("abcd");      
//		gson.toJson(new Long(10)); 
//		int[] values = { 1 };
//		gson.toJson(values); 
		//System.out.print(json);

		}
	private static void writeDJSON(String jsonNodes, String jsonLinks) {
		   try {  
			   //write converted json data to a file named "CountryGSON.json"  
			   FileWriter writer = new FileWriter("c:/RWTH/Data/hcicorpusPDF.json"); 
			   FileWriter writer2 = new FileWriter("c:/RWTH/Data/hcicorpusCat.json"); 
			   writer.write(jsonNodes);
			   writer.close(); 
			   writer2.write(jsonLinks);
			   writer2.close();
			    
			  } catch (IOException e) {  
			   e.printStackTrace();  
			  }  
		
	}
	private static ArrayList<Link> generateLinks(Corpus corpus) {
		ArrayList<PDF> pdfList = corpus.getPdfList();
		ArrayList<Category> gCatList = corpus.getGlobalCategory();
		ArrayList<Link> links = new ArrayList<Link>();
		for(int counter=0;counter<pdfList.size();counter++){
			ArrayList<Category> pCatList = pdfList.get(counter).getGenericKeywords();
			for(Category cat:pCatList){
				String pdfCtitle = cat.getTitle();
				for (int counterG = 0;counterG<gCatList.size();counterG++){
					String gCtitle = gCatList.get(counterG).getTitle();
					if(pdfCtitle.equals(gCtitle)){
						links.add(new Link(counter+gCatList.size(),counterG,cat.getRelevance()));
					}
				}
				
			}
		}
		return links;
	}


}
