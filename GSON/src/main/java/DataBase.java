
import java.lang.AutoCloseable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import model.Category;
import model.PDF;
import model.WordOcc;

public class DataBase {
	private Connection connect = null;
	private Statement statement = null;
	private PreparedStatement preparedStatement = null;
	private ResultSet resultSet = null;

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

	public ArrayList<PDF> retrieveDB()   {
		// this will load the MySQL driver, each DB has its own driver

			try {
				Class.forName("com.mysql.jdbc.Driver");
			} catch (ClassNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		// setup the connection with the DB.
		try {
			connect = DriverManager.getConnection("jdbc:mysql://localhost/corpus?"
					+ "user=test&password=test");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ArrayList<PDF> pdfList = new ArrayList<PDF>();
		try {
			pdfList = createPDFlist(connect);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return pdfList;
	}

	private ArrayList<PDF> createPDFlist(Connection connect) throws SQLException  {
		// TODO Auto-generated method stub
		ArrayList<PDF> pdfList = new ArrayList<PDF>();
		preparedStatement = connect
				.prepareStatement("SELECT idPDF,title, language FROM corpus.PDF");
		resultSet = preparedStatement.executeQuery();
		ResultSet resultSetPDF = resultSet;
		while (resultSetPDF.next()) {
			// it is possible to get the columns via name
			// also possible to get the columns via the column number
			// which starts at 1
			// e.g., resultSet.getSTring(2);
			int id = resultSetPDF.getInt("idPDF");
			String title = resultSetPDF.getString("title");
			String language = resultSetPDF.getString("language");
			ArrayList<WordOcc> words = createWords(connect,id);
			ArrayList<Category> cats = createCats(connect,id);
			PDF pdf = new PDF(title,language,words,cats);
			pdfList.add(pdf);
		}
		return pdfList;
	}

	private ArrayList<Category> createCats(Connection connect, int id) throws SQLException {
		ArrayList<Category> cats = new ArrayList<Category>();
		preparedStatement = connect
				.prepareStatement("SELECT PDF_idPDF,name, relevance FROM corpus.Category WHERE PDF_idPDF="+id);
		resultSet = preparedStatement.executeQuery();
		while (resultSet.next()) {

			String name = resultSet.getString("name");
			int relevance = resultSet.getInt("relevance");
			Category cat = new Category(name,relevance);
			cats.add(cat);



		}
		
		return cats;
	}

	private ArrayList<WordOcc> createWords(Connection connect, int id) throws SQLException {
		ArrayList<WordOcc> words = new ArrayList<WordOcc>();
		preparedStatement = connect
				.prepareStatement("SELECT PDF_idPDF,word,count,tfidf_score FROM corpus.Keyword WHERE PDF_idPDF="+id);
		resultSet = preparedStatement.executeQuery();
		while (resultSet.next()) {

			String word = resultSet.getString("word");
			int count = resultSet.getInt("count");
			double tfidf = resultSet.getDouble("tfidf_score");
			WordOcc wordocc = new WordOcc(word,count,tfidf); 
			words.add(wordocc);
		}
		return words;
	}
}
