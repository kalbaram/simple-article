package example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.StringTokenizer;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.dbutils.DbUtils;

public class InitDatabase implements ServletContextListener {

	public void contextInitialized(ServletContextEvent sce) {
		try {
			String drivers = sce.getServletContext().getInitParameter("jdbc.drivers");
			if (drivers != null) {
				StringTokenizer driverToken = new StringTokenizer(drivers, " ,");
				while (driverToken.hasMoreTokens()) {
					Class.forName(driverToken.nextToken());
				}
				Class.forName("org.apache.commons.dbcp.PoolingDriver");
			}
			createH2Tables();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private static StringBuilder createQuery;
	static {
		createQuery = new StringBuilder();
		createQuery.append("CREATE TABLE Article (").append("\r\n");
		createQuery.append("	id int primary key auto_increment,").append("\r\n");
		createQuery.append("	subject varchar(100) not null,").append("\r\n");
		createQuery.append("	contents text not null,").append("\r\n");
		createQuery.append("	creater_name varchar(100) not null,").append("\r\n");
		createQuery.append("	status varchar(10) default 'on',").append("\r\n");
		createQuery.append("	visit_count int default 0,").append("\r\n");
		createQuery.append("	date_created timestamp default current_timestamp").append("\r\n");
		createQuery.append(")").append("\r\n");
	}

	private void createH2Tables() {
		Connection conn = null;
		Statement st = null;
		try {
			System.out.println(createQuery);

			conn = DriverManager.getConnection("jdbc:apache:commons:dbcp:/h2");
			st = conn.createStatement();
			st.execute(createQuery.toString());
		}
		catch (SQLException sqlEx) {
			sqlEx.printStackTrace();
		}
		finally {
			DbUtils.closeQuietly(conn, st, null);
		}

	}

	public void contextDestroyed(ServletContextEvent sce) {
	}

}
