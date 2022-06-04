package runner;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import support.DbSupport;
import support.Util;
import support.SharedStaticData;

/**
 * @author phalg
 * Purpose :
 *     1. Entry point.
 *     2. Parsing CLI Arguments to identify file to purse
 */
public class LogManager {
	private final static Log logger = LogFactory.getLog(Util.class);

	public static void main(String[] filename) throws FileNotFoundException {
		String file;
	//	 file = "C:\\Users\\phalg\\eclipse-workspace\\summer\\summer\\inputFiles\\logfile.txt";
		
		logger.debug("Validating input...");

		
		  if (filename.length == 1) 
		  	{
			  file = filename[0]; 
			  DbSupport.fileToBeParsed = file;
			  logger.debug("File name Found : " + file); 
			} 
		  else 
		    { 
			  logger.error("File not found or invalid input!"); 
			  throw new FileNotFoundException("File not found or invalid input!"); 
			}
		 

		logger.debug("Start persing file with FileName  - " + file);
		try {
			BufferedReader fileReader = new BufferedReader(new FileReader(file));
			
			logger.debug("Success : File read with FileName  - " + file);
			DbSupport dbSupport = new DbSupport();
			dbSupport.executeQuery(SharedStaticData.getProperty(SharedStaticData.dbProp, "createTableQuery"));
			logger.debug("Success : Table Creation   - " + file);
			
			logger.debug("Start persing log of file and Store in DB - " + file);
			Util parser = new Util();
			parser.parseFile(fileReader, dbSupport);
			logger.debug("Success - persing log of file and Store in DB - " + file);
			
			logger.debug("Start Reading data Stored in DB - Tablename :  " + SharedStaticData.getProperty(SharedStaticData.dbProp, "tableName"));
			dbSupport.getData();
			logger.debug("Success - End Reading data Stored in DB - Tablename :  " + SharedStaticData.getProperty(SharedStaticData.dbProp, "tableName"));
			
			logger.debug("Delete all data in table :  " + SharedStaticData.getProperty(SharedStaticData.dbProp, "tableName"));
			dbSupport.deleteData();
			
			logger.debug("Close DB Connection :  " + SharedStaticData.getProperty(SharedStaticData.dbProp, "tableName"));
			dbSupport.close();
		} catch (IOException e) {
			logger.error("Fail to parse file or Invalid file name  - " + file);
			e.printStackTrace();
		} catch (SQLException e) {
			logger.error("Error in  DB");
			e.printStackTrace();
		}

		logger.debug("Success - End persing file - " + file);

	}

}
