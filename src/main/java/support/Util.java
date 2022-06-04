package support;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.google.gson.Gson;

public class Util {
	private static final Log logger = LogFactory.getLog(Util.class);

	public void parseFile(BufferedReader bufferdReader, DbSupport dbSupport) throws IOException, SQLException
	{
		logger.debug("Start Parsing File - " + DbSupport.fileToBeParsed );
		Gson gson = new Gson();
        HashMap<String, ServerLog> eventIdLogMap = new HashMap<String, ServerLog>();        
        String line;
		logger.debug("Start Parsing Lines of - " + DbSupport.fileToBeParsed );

        while ((line = bufferdReader.readLine()) != null) {
            ServerLog log = (ServerLog) gson.fromJson(line, ServerLog.class);
            String id = log.getId();
            if (!eventIdLogMap.containsKey(id)) {
                eventIdLogMap.put(id, log);
                continue;
            }
    		logger.debug("Success : Parsing Lines of - " + DbSupport.fileToBeParsed );
    		
    		logger.debug("Calculate Duration of Events "  );
            ServerLog previousLog = eventIdLogMap.remove(id);
            long duration = Math.abs(log.getTimeStamp() - previousLog.getTimeStamp());
            boolean alert = false;
            if ( SharedStaticData.MAX_DURATION > 4) 
                alert = true;
          
            DataTableHeaders data = new DataTableHeaders.Builder(id, duration, alert)
                    .withHost(log.getHost())
                    .withType(log.getType())
                    .build();
             
        }
		logger.debug("Success - Parsing File - " + DbSupport.fileToBeParsed );

    }
}
