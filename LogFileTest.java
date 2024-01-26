import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.Assert.fail;


import org.junit.jupiter.api.DisplayName;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


class LogFileTest {

    //Test constructor
    @Test
    @DisplayName("Constructor Test")
    public void testConstructor(){
        LogFile log = new LogFile("inputLogsTest//test.csv");
        List<String> headers = log.getHeaders();
        // Check that logMap is not empty
        assertFalse(log.getLogMap().isEmpty());
        assertFalse(headers.isEmpty());

        // Check that logMap and headers is not null
        assertNotNull(log.getLogMap());
        assertNotNull(headers);
        assertEquals(log.getFileName(), "inputLogsTest//test.csv");

         // Check that each entry in logMap has the same number of fields as headers
        for (Map<String, String> logEntry : log.getLogMap()) {
            assertEquals(headers.size(), logEntry.size());
        }

        // Check that program exists and doesn't assign variables if file does not exist.
        LogFile log2 = new LogFile("DOES NOT EXIST");
        assertTrue(log2.getLogMap() == null);
        assertEquals(log2.getFileName(), "DOES NOT EXIST");
        assertTrue(log2.getHeaders() == null);
    }



    //Test generation of log map
    @Test
    @DisplayName("Generate Log Map Test")
    public void testGenerateLogMap() {
        LogFile log = new LogFile("inputLogsTest//test.csv");
        assertNotNull(log.getLogMap());

        List<Map<String, String>> generatedLogMap = log.getLogMap();
        List<Map<String, String>> testMaps = new ArrayList<>();
       
        //Expected map
        String[] actions = {"Login", "Login", "Logout", "Login", "Logout", "Login", "Logout", "Login", "Login", "Logout", "Login", "Logout", "Login", "Logout"};
        String[] usernames = {"user1", "user2", "user1", "user3", "user2", "user1", "user3", "user4", "user5", "user4", "user6", "user5", "user4", "user6"};
        String[] devices = {"Desktop", "Laptop", "Desktop", "Tablet", "Laptop", "Desktop", "Tablet", "Desktop", "Laptop", "Desktop", "Mobile", "Laptop", "Desktop", "Mobile"};
        String[] pages = {"HomePage", "Dashboard", "HomePage", "ProfilePage", "Dashboard", "ProfilePage", "ProfilePage", "HomePage", "Dashboard", "HomePage", "ProfilePage", "Dashboard", "ProfilePage", "ProfilePage"};
        String[] ipAddresses = {"192.168.1.101", "192.168.1.102", "192.168.1.101", "192.168.1.103", "192.168.1.102", "192.168.1.101", "192.168.1.103", "192.168.1.104", "192.168.1.105", "192.168.1.104", "192.168.1.106", "192.168.1.105", "192.168.1.104", "192.168.1.106"};
        String[] timestamps = {"2023-11-20 10:00:00", "2023-11-20 10:05:00", "2023-11-20 10:15:00", "2023-11-20 10:20:00", "2023-11-20 10:30:00", "2023-11-20 10:35:00", "2023-11-20 10:45:00", "2023-11-20 11:00:00", "2023-11-20 11:10:00", "2023-11-20 11:15:00", "2023-11-20 11:25:00", "2023-11-20 11:35:00", "2023-11-20 11:40:00", "2023-11-20 11:50:00"};

        // Populate the list maps
        for (int i = 0; i < actions.length; i++) {
            Map<String, String> logEntry = new HashMap<>();
            logEntry.put("Action", actions[i]);
            logEntry.put("Username", usernames[i]);
            logEntry.put("Device", devices[i]);
            logEntry.put("Page", pages[i]);
            logEntry.put("IPAddress", ipAddresses[i]);
            logEntry.put("Timestamp", timestamps[i]);

            testMaps.add(logEntry);
        }
        assertEquals(generatedLogMap, testMaps);

        

        LogFile log2 = new LogFile("inputLogsTest//test2.csv");
        List<Map<String, String>> generatedLogMap2 = log2.getLogMap();
        List<Map<String, String>> testMaps2 = new ArrayList<>();
        
        String[] x = {"1","4"};
        String[] y = {"2","5"};
        String[] z = {"3", "6"};
        for(int i = 0; i < 2; i ++){
            Map<String, String> logEntry2 = new HashMap<>();
            logEntry2.put("x", x[i]);
            logEntry2.put("y", y[i]);
            logEntry2.put("z", z[i]);
                 
            testMaps2.add(logEntry2);      
        }
        assertEquals(generatedLogMap2, testMaps2);
    }

    //Test search
    @Test
    @DisplayName("Search Test")
    public void testSearch(){
        LogFile log = new LogFile("inputLogsTest//test2.csv");
        log.search("x", "1");

        List<Map<String, String>> newLogMap = log.getLogMap();
        List<Map<String, String>> testMap = new ArrayList<>();
        
        String[] x = {"1"};
        String[] y = {"2"};
        String[] z = {"3"};

        //create expected output
         for(int i = 0; i < x.length; i ++){
            Map<String, String> logEntry = new HashMap<>();
            logEntry.put("x", x[i]);
            logEntry.put("y", y[i]);
            logEntry.put("z", z[i]);
                 
            testMap.add(logEntry);      
        }

        assertEquals(newLogMap, testMap);
        assertNotNull(newLogMap);
        assertNotNull(testMap);

        // Check that each entry in resultLogMap has the searched key-value pair
        LogFile log2 = new LogFile("inputLogsTest//test.csv");
        log2.search("Username", "user1");
        List<Map<String, String>> resultLogMap = log2.getLogMap();
        for (Map<String, String> logEntry : resultLogMap) {
            assertEquals("user1", logEntry.get("Username"));
        }
        // Check for invalid values
        LogFile log3 = new LogFile("inputLogsTest//test.csv");
        log3.search("Username", "user99");
        assertTrue(log3.getLogMap().isEmpty());
    }

    @Test
     @DisplayName("Invalid Key Log Map Test")
    public void testInvalidSearchKey() {
        // Expecting a NullPointerException when searching with an invalid key
        LogFile log3 = new LogFile("inputLogsTest//test.csv");
        assertThrows(NullPointerException.class,
            ()->{
                log3.search("Invalid", "user1");
            });
     
      
    }

    //Test save
    @Test
    @DisplayName("Save Log Map Test")
    public void testSaveLogMap() {
        LogFile log = new LogFile("inputLogsTest//test2.csv");
        log.search("x", "4");

        String TEST_SAVE_FILE_NAME = "test_output";
        log.saveLogMap(TEST_SAVE_FILE_NAME);

        // Check if the saved file exists
        File savedFile = new File("./processed_logs/" + TEST_SAVE_FILE_NAME + ".csv");
        assertTrue(savedFile.exists());

        // Check if the saved file has content
        LogFile logtest = new LogFile("./processed_logs/" + TEST_SAVE_FILE_NAME + ".csv");
        List<Map<String, String>> savedLogMap = logtest.getLogMap();
        assertFalse(savedLogMap.isEmpty());

        // Check if the file has the expected content
        List<Map<String, String>> testMap = new ArrayList<>();
        
        String[] x = {"4"};
        String[] y = {"5"};
        String[] z = {"6"};

         for(int i = 0; i < x.length; i ++){
            Map<String, String> logEntry = new HashMap<>();
            logEntry.put("x", x[i]);
            logEntry.put("y", y[i]);
            logEntry.put("z", z[i]);
                 
            testMap.add(logEntry);      
        }
        assertEquals(testMap, savedLogMap);
    }

    
    @Test
    @DisplayName("Print Logs Test")
    public void testPrintLogs() {
        LogFile log = new LogFile("inputLogsTest//test2.csv");

        // Redirect System.out to capture printed output
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        // Call the printLogs method
        log.printLogs();

        // Reset System.out to normal
        System.setOut(System.out);

        // Expected output with leading/trailing spaces
        String expectedOutput = "x                      y                      z                      \n" +
                                "1                      2                      3                      \n" +
                                "4                      5                      6                      \n";

        assertEquals(expectedOutput, outContent.toString());

         // Test for "test.csv"
        LogFile log2 = new LogFile("inputLogsTest//test.csv");

        // Redirect System.out to capture printed output
        ByteArrayOutputStream outContent2 = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent2));

        // Call the printLogs method for "test.csv"
        log2.printLogs();

        // Reset System.out to normal
        System.setOut(System.out);

        String expectedOutput2 = "Action                 Username               Device                 Page                   IPAddress              Timestamp              \n" +
                        "Login                  user1                  Desktop                HomePage               192.168.1.101          2023-11-20 10:00:00    \n" +
                        "Login                  user2                  Laptop                 Dashboard              192.168.1.102          2023-11-20 10:05:00    \n" +
                        "Logout                 user1                  Desktop                HomePage               192.168.1.101          2023-11-20 10:15:00    \n" +
                        "Login                  user3                  Tablet                 ProfilePage            192.168.1.103          2023-11-20 10:20:00    \n" +
                        "Logout                 user2                  Laptop                 Dashboard              192.168.1.102          2023-11-20 10:30:00    \n" +
                        "Login                  user1                  Desktop                ProfilePage            192.168.1.101          2023-11-20 10:35:00    \n" +
                        "Logout                 user3                  Tablet                 ProfilePage            192.168.1.103          2023-11-20 10:45:00    \n" +
                        "Login                  user4                  Desktop                HomePage               192.168.1.104          2023-11-20 11:00:00    \n" +
                        "Login                  user5                  Laptop                 Dashboard              192.168.1.105          2023-11-20 11:10:00    \n" +
                        "Logout                 user4                  Desktop                HomePage               192.168.1.104          2023-11-20 11:15:00    \n" +
                        "Login                  user6                  Mobile                 ProfilePage            192.168.1.106          2023-11-20 11:25:00    \n" +
                        "Logout                 user5                  Laptop                 Dashboard              192.168.1.105          2023-11-20 11:35:00    \n" +
                        "Login                  user4                  Desktop                ProfilePage            192.168.1.104          2023-11-20 11:40:00    \n" +
                        "Logout                 user6                  Mobile                 ProfilePage            192.168.1.106          2023-11-20 11:50:00    \n";

        assertEquals(expectedOutput2, outContent2.toString());
    }


    //Test search
    @Test
    @DisplayName("Count Test")
    public void testCount(){
        LogFile log = new LogFile("inputLogsTest//test2.csv");
        log.count("x", "1");

        List<Map<String, String>> newLogMap = log.getLogMap();
        List<Map<String, String>> testMap = new ArrayList<>();
        
        String[] x = {"1", "4"};
        String[] y = {"2", "5"};
        String[] z = {"3", "6"};

        //Check that the logmap did not change
         for(int i = 0; i < x.length; i ++){
            Map<String, String> logEntry = new HashMap<>();
            logEntry.put("x", x[i]);
            logEntry.put("y", y[i]);
            logEntry.put("z", z[i]);
                 
            testMap.add(logEntry);        
        }

        assertEquals(newLogMap, testMap);
        assertNotNull(newLogMap);
        assertNotNull(testMap);

        //Check that it returns the correct output
        LogFile log2 = new LogFile("inputLogsTest//test.csv");
        int result = log2.count("Action", "Login");
        assertEquals(8, result);
        
        //Check for invalid key
         assertThrows(NullPointerException.class,
            ()->{
                log2.count("Invalid", "Invalid");
            });
     
        //Check for invalid value
        int result3 = log2.count("Username", "Nonexistent");
        assertEquals(0, result3);

    }   
}
