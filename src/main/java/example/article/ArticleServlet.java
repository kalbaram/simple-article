package example.article;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.commons.dbutils.DbUtils;

public class ArticleServlet {

	public void connectionSample() {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection("jdbc:apache:commons:dbcp:/h2");
		}
		catch (SQLException sqlEx) {
		}
		finally {
			DbUtils.closeQuietly(conn, null, null);
		}
	}

}
