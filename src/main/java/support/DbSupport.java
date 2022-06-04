package support;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class DbSupport {
	private static final Log logger = LogFactory.getLog(DbSupport.class);

	private static Connection connection;

	public static String fileToBeParsed = "";
	public DbSupport() throws SQLException, IOException {

		logger.debug("Trying to connect database.");
		connection = DriverManager.getConnection(SharedStaticData.getProperty(SharedStaticData.dbProp, "ConStr"), SharedStaticData.getProperty(SharedStaticData.dbProp, "UName"), SharedStaticData.getProperty(SharedStaticData.dbProp, "Pwd"));
		logger.debug("Success - Connected with database.");
	}

	public void executeQuery(String query) throws SQLException {
		logger.debug("Executing query - " + query);
		connection.createStatement().executeUpdate(query);
		logger.debug("Success - Executing query - " + query);
	}

	public void addData(DataTableHeaders line) throws SQLException {
		logger.debug("Start Insert Data onto table ");
		String insertQuery = SharedStaticData.prepareSQL(SharedStaticData.getProperty(SharedStaticData.dbProp, "insertQuery"), "tableName");
		logger.debug("	Query : " +insertQuery);
		PreparedStatement statement = connection.prepareStatement(insertQuery);
		statement.setString(1, line.getId());
		statement.setFloat(2, line.getDuration());
		statement.setString(3, line.getType());
		statement.setString(4, line.getHost());
		statement.setBoolean(5, line.isAlert());
		statement.executeUpdate();
		logger.debug("Success - Inserted Data onto table - " + insertQuery);

	}

	public void close() throws SQLException {
		logger.info("Starting DB Con Close Operation");
		connection.close();
		logger.info("Success - DB Con Closed");

	}

	public void getData() throws SQLException {
		logger.debug("Start GetData from DB");

		ResultSet resultSet = connection.createStatement().executeQuery(SharedStaticData.getProperty(SharedStaticData.dbProp, "selectQuery"));

		while (resultSet.next()) {
			if (resultSet.getBoolean(5)) {
				logger.debug("Raising ALERT : " + resultSet.getString(1) );
			}
		}
		logger.debug("Success -  GetData from DB End");

	}

	public void deleteData() throws SQLException {
		logger.warn("Start : Deleting all records");
		connection.createStatement().executeUpdate(SharedStaticData.getProperty(SharedStaticData.dbProp, "deleteQuery"));
		logger.warn("Success : Deleting all records END");

	}
}
