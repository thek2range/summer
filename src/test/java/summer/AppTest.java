package summer;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.SQLException;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import support.DbSupport;
import support.Util;


public class AppTest {
	@InjectMocks private Util perser;
    @Mock private BufferedReader bufferReader;
    @Mock private DbSupport dbSupport;
    
 @SuppressWarnings("deprecation")
@Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void parseLogs() throws IOException, SQLException {
    	System.out.println("Test Start");
        String startEvent = "{\"id\":\"scsmbstgra\", \"state\":\"STARTED\", \"timestamp\":1491377495212}";
        String finishEvent = "{\"id\":\"scsmbstgra\", \"state\":\"FINISHED\", \"timestamp\":1491377495217}";
        Mockito.when(bufferReader.readLine())
                .thenReturn(startEvent)
                .thenReturn(finishEvent)
                .thenReturn(null);

        perser.parseFile(bufferReader, dbSupport);

        Mockito.verify(dbSupport, Mockito.times(1));
    	System.out.println("Test End");

    }
}
