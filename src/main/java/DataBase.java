import java.lang.AutoCloseable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.Normalizer;
import java.util.ArrayList;

import model.Author;
import model.Category;
import model.Corpus;
import model.PDF;
import model.WordOcc;

public class DataBase {
	private Connection connect = null;
	private Statement statement = null;
	private PreparedStatement preparedStatement = null;
	private ResultSet resultSet = null;
	private String dbName = "hcicorpus";

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
		Corpus corpus = new Corpus(gCat, pdfList,allAuthor);
		return corpus;
	}

	private ArrayList<Author> createAllAuthor(Connection connect2) throws SQLException {
		ArrayList<Author> authors = new ArrayList<Author>();
		Statement state = connect.createStatement();

		// state.setFetchSize(100);
		ResultSet resultSetCategory = state.executeQuery("SELECT * FROM  "
				+ dbName + ".author");
		while (resultSetCategory.next()) {

			int id = resultSetCategory.getInt("idAuthor");
			// System.out.println(id);
			String name = resultSetCategory.getString("name");
			String nameNorm = Normalizer.normalize(name,
					Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
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
			// System.out.println(id);
			String title = resultSetPDF.getString("title");
			//title = title.toLowerCase();
			String shorttitle = title;
			if (title.length() > 20) {
				shorttitle = title.substring(0, 20)+" (...)";
			}
			String fileN = resultSetPDF.getString("fileName");
			String language = resultSetPDF.getString("language");


			ArrayList<WordOcc> words = createWords(connect, id);
			// TODO TOO MUCH INFO FOR PROTOTYPE VERSION (INCLUDE LATER)
			words = null;
			ArrayList<Category> cats = createCats(connect, id);
			
			ArrayList<Author> authors = createAuthors(connect,id);
			// TODO ADD AUTHOR
			PDF pdf = new PDF(shorttitle,title, language, words, cats, id,authors,fileN);
			// System.out.println(pdf.getPublicationID());
			pdfList.add(pdf);
		}
		resultSetPDF.close();
		state.close();
		return pdfList;
	}

	private ArrayList<Author> createAuthors(Connection connect2, int id) throws SQLException {
		ArrayList<Integer> authids = new ArrayList<Integer>();
		preparedStatement = connect
				.prepareStatement("SELECT * FROM  "
						+ dbName + ".PDF_has_Author WHERE PDF_idPDF=" + id);
		resultSet = preparedStatement.executeQuery();
		while(resultSet.next()){
			authids.add(resultSet.getInt("Author_idAuthor"));
		}
		ArrayList<Author> authors = new ArrayList<Author>();
		for(int counter=0;counter<authids.size();counter++){
			preparedStatement = connect
					.prepareStatement("SELECT name FROM  "
							+ dbName + ".author WHERE idAuthor=" + authids.get(counter));
			ResultSet resultSetcat = preparedStatement.executeQuery();
			resultSetcat.next();
			String name = resultSetcat.getString("name");
			String nameNorm = Normalizer.normalize(name,
					Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
			Author author = new Author(nameNorm);

			authors.add(author);
		}
		return authors;
	}

	private ArrayList<Category> createCats(Connection connect, int id)
			throws SQLException {
		ArrayList<Integer> catids = new ArrayList<Integer>();
		preparedStatement = connect
				.prepareStatement("SELECT Category_idCategory FROM  "
						+ dbName + ".PDF_has_Category WHERE PDF_idPDF=" + id);
		resultSet = preparedStatement.executeQuery();
		while(resultSet.next()){
			catids.add(resultSet.getInt("Category_idCategory"));
		}
		ArrayList<Category> cats = new ArrayList<Category>();
		for(int counter=0;counter<catids.size();counter++){
			preparedStatement = connect
					.prepareStatement("SELECT name, relevance,normtitle,associatedGCat FROM  "
							+ dbName + ".Category WHERE idCategory=" + catids.get(counter));
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
		preparedStatement = connect
				.prepareStatement("SELECT PDF_idPDF,word,count,tfidf_score FROM  "
						+ dbName + ".Keyword WHERE PDF_idPDF=" + id);
		resultSet = preparedStatement.executeQuery();
		while (resultSet.next()) {

			String word = resultSet.getString("word");
			int count = resultSet.getInt("count");
			double tfidf = resultSet.getDouble("tfidf_score");
			WordOcc wordocc = new WordOcc(word, count, tfidf);
			words.add(wordocc);
		}
		return words;
	}
}
