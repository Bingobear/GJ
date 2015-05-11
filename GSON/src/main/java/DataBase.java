import java.lang.AutoCloseable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.Normalizer;
import java.util.ArrayList;

import model.AlgorithmUtil;
import model.Author;
import model.Category;
import model.Corpus;
import model.PDF;
import model.PDFWords;
import model.Publication;
import model.WordOcc;

public class DataBase {
	private Connection connect = null;
	private Statement statement = null;
	private PreparedStatement preparedStatement = null;
	private ResultSet resultSet = null;
	private String dbName = "hcicorpus";//hcicorpus

	// you need to close all three to make sure
	private void close() {
		close(resultSet);
		close(statement);
		close(connect);
	}

	private void close(AutoCloseable c) {
		try {
			if (c != null) {
				c.close();
			}
		} catch (Exception e) {
			// don't throw now as it might leave following closables in
			// undefined state
		}
	}

	public Corpus retrieveDB() {
		// this will load the MySQL driver, each DB has its own driver

		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		// setup the connection with the DB.
		try {
			connect = DriverManager.getConnection("jdbc:mysql://localhost/"
					+ dbName + "?" + "user=test&password=test");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ArrayList<PDF> pdfList = new ArrayList<PDF>();
		ArrayList<Category> gCat = new ArrayList<Category>();
		ArrayList<Author> allAuthor = new ArrayList<Author>();
		try {
			gCat = createGlobalCat(connect);
			pdfList = createPDFlist(connect);
			allAuthor = createAllAuthor(connect);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Corpus corpus = new Corpus(gCat, pdfList, allAuthor);
		return corpus;
	}

	private ArrayList<Author> createAllAuthor(Connection connect2)
			throws SQLException {
		ArrayList<Author> authors = new ArrayList<Author>();
		Statement state = connect.createStatement();

		// state.setFetchSize(100);
		ResultSet resultSetCategory = state.executeQuery("SELECT * FROM  "
				+ dbName + ".author");
		while (resultSetCategory.next()) {

			int id = resultSetCategory.getInt("idAuthor");
			// System.out.println(id);
			String name = resultSetCategory.getString("name");
			String nameNorm = Normalizer.normalize(name, Normalizer.Form.NFD)
					.replaceAll("[^\\p{ASCII}]", "");
			Author aut = new Author(nameNorm);
			// System.out.println(pdf.getPublicationID());
			authors.add(aut);
		}
		resultSetCategory.close();
		state.close();
		return authors;
	}

	private ArrayList<Category> createGlobalCat(Connection connect2)
			throws SQLException {
		// TODO Auto-generated method stub
		ArrayList<Category> gCatList = new ArrayList<Category>();
		Statement state = connect.createStatement();

		// state.setFetchSize(100);
		ResultSet resultSetCategory = state.executeQuery("SELECT * FROM  "
				+ dbName + ".globalcategory");
		while (resultSetCategory.next()) {

			int id = resultSetCategory.getInt("idGlobalCategory");
			// System.out.println(id);
			String title = resultSetCategory.getString("title");
			String normtitle = resultSetCategory.getString("normtitle");
			Category cat = new Category(title, normtitle);
			// System.out.println(pdf.getPublicationID());
			gCatList.add(cat);
		}
		resultSetCategory.close();
		state.close();
		return gCatList;
	}

	private ArrayList<PDF> createPDFlist(Connection connect)
			throws SQLException {
		// TODO Auto-generated method stub
		ArrayList<PDF> pdfList = new ArrayList<PDF>();
		connect.setAutoCommit(false);
		Statement state = connect.createStatement();

		state.setFetchSize(10);
		ResultSet resultSetPDF = state.executeQuery("SELECT * FROM  " + dbName
				+ ".pdf");
		int counter = 0;
		while (resultSetPDF.next()) {
			counter++;
			if (counter == -1) {
				break;
			}

			int id = resultSetPDF.getInt("idPDF");
			int pubID = resultSetPDF.getInt("Publication_idPublication");
			// System.out.println(id);
			String title = resultSetPDF.getString("title");
			// title = title.toLowerCase();
			String shorttitle = title;
			if (title.length() > 20) {
				shorttitle = title.substring(0, 20) + " (...)";
			}
			String fileN = resultSetPDF.getString("fileName");
			String language = resultSetPDF.getString("language");

			ArrayList<WordOcc> words = createWords(connect, id);
			// TODO TOO MUCH INFO FOR PROTOTYPE VERSION (INCLUDE LATER)
			if(GSON_Main.modeC){
				words = null;
				}
			ArrayList<Category> cats = createCats(connect, id);

			ArrayList<Author> authors = createAuthors(connect, id);
			// TODO HOTFIX NOT FINAL SOLUTION

			PDF pdf = new PDF(shorttitle, title, language, words, cats, id,
					authors, fileN);
			if (pubID > 0) {
				Publication pub = getPublication(pubID, connect);
				pdf.setPub(pub);
			}

			// System.out.println(pdf.getPublicationID());
			pdfList.add(pdf);
		}
		resultSetPDF.close();
		state.close();
		return pdfList;
	}

	private Publication getPublication(int pubID, Connection connect2)
			throws SQLException {
		Publication pub = null;
		preparedStatement = connect2.prepareStatement("SELECT * FROM  "
				+ dbName + ".publication WHERE idPublication=" + pubID);
		resultSet = preparedStatement.executeQuery();
		int journaltitle = 0;
		while (resultSet.next()) {
			String title = resultSet.getString("title");
			String format = resultSet.getString("format");
			int relDate = resultSet.getInt("releaseDate");
			String publisher = resultSet.getString("publisher");
			String idBTH = resultSet.getString("idBTH");
			String origin = resultSet.getString("origin");
			pub = new Publication(title, format, relDate, publisher, idBTH,
					origin);
			journaltitle = resultSet.getInt("Journal_idJournal");

		}
		if (journaltitle > 0) {
			preparedStatement = connect2.prepareStatement("SELECT * FROM  "
					+ dbName + ".journal WHERE idJournal=" + pubID);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				pub.setJournaltitle(resultSet.getString("title"));
			}
		}
		return pub;
	}

	private ArrayList<Author> createAuthors(Connection connect2, int id)
			throws SQLException {
		ArrayList<Integer> authids = new ArrayList<Integer>();
		preparedStatement = connect.prepareStatement("SELECT * FROM  " + dbName
				+ ".PDF_has_Author WHERE PDF_idPDF=" + id);
		resultSet = preparedStatement.executeQuery();
		while (resultSet.next()) {
			authids.add(resultSet.getInt("Author_idAuthor"));
		}
		ArrayList<Author> authors = new ArrayList<Author>();
		for (int counter = 0; counter < authids.size(); counter++) {
			preparedStatement = connect
					.prepareStatement("SELECT name FROM  " + dbName
							+ ".author WHERE idAuthor=" + authids.get(counter));
			ResultSet resultSetcat = preparedStatement.executeQuery();
			resultSetcat.next();
			String name = resultSetcat.getString("name");
			String nameNorm = Normalizer.normalize(name, Normalizer.Form.NFD)
					.replaceAll("[^\\p{ASCII}]", "");
			Author author = new Author(nameNorm);

			authors.add(author);
		}
		return authors;
	}

	private ArrayList<Category> createCats(Connection connect, int id)
			throws SQLException {
		ArrayList<Integer> catids = new ArrayList<Integer>();
		preparedStatement = connect
				.prepareStatement("SELECT Category_idCategory FROM  " + dbName
						+ ".PDF_has_Category WHERE PDF_idPDF=" + id);
		resultSet = preparedStatement.executeQuery();
		while (resultSet.next()) {
			catids.add(resultSet.getInt("Category_idCategory"));
		}
		ArrayList<Category> cats = new ArrayList<Category>();
		for (int counter = 0; counter < catids.size(); counter++) {
			preparedStatement = connect
					.prepareStatement("SELECT name, relevance,normtitle,associatedGCat FROM  "
							+ dbName
							+ ".Category WHERE idCategory="
							+ catids.get(counter));
			ResultSet resultSetcat = preparedStatement.executeQuery();
			resultSetcat.next();
			String name = resultSetcat.getString("name");
			String normtitle = resultSetcat.getString("normtitle");
			double relevance = resultSetcat.getDouble("relevance");
			String assGC = resultSetcat.getString("associatedGCat");
			Category cat = null;
			if (assGC != null) {
				cat = new Category(name, normtitle, relevance);
			} else {
				cat = new Category(name, normtitle, relevance, assGC);
			}
			cats.add(cat);
		}
		return cats;
	}

	private ArrayList<WordOcc> createWords(Connection connect, int id)
			throws SQLException {
		ArrayList<WordOcc> words = new ArrayList<WordOcc>();
		ArrayList<Author> authors  = createAllAuthor(connect);
		preparedStatement = connect
				.prepareStatement("SELECT PDF_idPDF,word,count,tfidf_score FROM  "
						+ dbName + ".Keyword WHERE PDF_idPDF=" + id+"  ORDER BY tfidf_score DESC");
		resultSet = preparedStatement.executeQuery();
		while (resultSet.next()) {

			String word = resultSet.getString("word");
			String eva = word.toLowerCase();
			boolean author =false;
			for(int ii =0;ii<authors.size();ii++){
				//potentially use levenstein to find writing errors
				if(authors.get(ii).getName().toLowerCase().contains(eva)){
					author = true;
				}
			}
			int count = resultSet.getInt("count");
			double tfidf = resultSet.getDouble("tfidf_score")*10000;

			WordOcc wordocc = new WordOcc(word, count, tfidf);
			words.add(wordocc);
		}
		if(words.size()>50){
			words = new ArrayList(words.subList(0, 50));
			int factorToInt = 1;
			for(int ii=0;ii<words.size();ii++){
				double value = words.get(ii).getTfidf();
				value= value*factorToInt*10;
				while(value<10){
					value= value*factorToInt*10;
					factorToInt++;
				}
			}
			for(int ii=0;ii<words.size();ii++){
				double value = words.get(ii).getTfidf();
				words.get(ii).setTfidf(Math.round(value*factorToInt*10));
			}
		}
		return words;
	}

	
}
