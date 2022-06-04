package support;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class SharedStaticData
{
	public static int MAX_DURATION = 4;
	public static String DBPropPath = "./src/main/resources/db.properties";
	public static Properties dbProp = new Properties();
	 static 
	{
		try {
			dbProp.load(new FileInputStream(DBPropPath));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
   public static String getProperty( Properties prop,String key)
   {
	   return prop.getProperty( key );
   }
   public static String prepareSQL( String metaQuery , String propToReplace )
   {
	   return metaQuery.replace(propToReplace, dbProp.getProperty( propToReplace ));
   }

}
