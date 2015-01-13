import java.io.FileWriter;
import java.io.IOException;

import model.*;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GSON_Main {
	public GSON_Main() {

	}
	public static void main(String[] args)   {
		DataBase db = new DataBase();


		Corpus corpus = new Corpus(db.retrieveDB());
		//System.out.println(corpus.getPdfList().size());

		
		Test_Object obj = new Test_Object();
		/*with white spaces

		String jsonOutput = gson.toJson(someObject);*/
		Gson gsona = new Gson();
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String json = gsona.toJson(corpus);  

		   try {  
			   //write converted json data to a file named "CountryGSON.json"  
			   FileWriter writer = new FileWriter("c:/RWTH/Data/hcicorpus.json");  
			   writer.write(json);  
			   writer.close();  
			    
			  } catch (IOException e) {  
			   e.printStackTrace();  
			  }  




//		Gson gson = new Gson();
//		gson.toJson(1);            
//		gson.toJson("abcd");      
//		gson.toJson(new Long(10)); 
//		int[] values = { 1 };
//		gson.toJson(values); 
		//System.out.print(json);

		}


}
