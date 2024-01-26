import java.util.List;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.io.BufferedWriter;
import java.io.FileWriter;

public class LogFile {
    private String fileName;
    private List<Map<String, String>> logMap;
    private List<String> headers;
    
    // Constructor to initialize the LogFile object with a given fileName
    public LogFile(String fileName) {
        this.fileName = fileName;
        generateLogMap();
    }

    // Print the logs to the console
    public void printLogs() {
        for (String header : headers) {
            System.out.printf("%-20s   ", header);
        }
        System.out.println();
        for (Map<String, String> log : logMap){
            for (Map.Entry<String, String> entry: log.entrySet()) {
                String entryValue = entry.getValue();
                System.out.printf("%-20s   ", entryValue);
            }
            System.out.println(); 
        }
    }

    // Search for logs with a specific key-value pair
    public void search(String key, String value) {
        List<Map<String, String>> resultLogMap = new ArrayList<>();
        for (Map<String, String> log : logMap){
            if (log.get(key).equals(value)) {
                resultLogMap.add(log);
            }
        }
        logMap = resultLogMap;
    }

    // Count for logs with a specific key-value pair
    public int count(String key, String value) {
        int count = 0;
        for (Map<String, String> log : logMap){
            if (log.get(key).equals(value)) {
                count += 1;
            }
        }
        return count;
    }
    
    // Generate log map from the log file
    public void generateLogMap() {
        List<Map<String, String>> logMap = new ArrayList<>();
        List<String> headers = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;

            // Read the first line to get headers
            if ((line = reader.readLine()) == null) {
                return;
            }

            String[] logLine = line.split(",");
            for (int i = 0; i < logLine.length; i++) {
                headers.add(logLine[i]);
            }

            // Read subsequent lines to populate logMap
            while ((line = reader.readLine()) != null) {
                Map<String, String> log = new HashMap<>();
                logLine = line.split(",");
                for (int i = 0; i < logLine.length; i++) {
                    log.put(headers.get(i), logLine[i]);
                }
                logMap.add(log);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        headers.clear();

        // Populate headers list with keys from the first log entry
        for (String key: logMap.get(0).keySet()) {
            headers.add(key);
        }
        
        this.headers = headers;
        this.logMap = logMap;
    }
    
    // Save the current logMap to a new CSV file
    public void saveLogMap(String fileName) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("./processed_logs/" + fileName + ".csv"))) {
            for (String header : headers) {
                writer.write(header + ",");
            }
            writer.newLine();
            for (Map<String, String> log : logMap) {
                for (String header : logMap.get(0).keySet()) {
                    writer.write(log.get(header) + ",");
                }
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Getter method to retrieve headers
    public List<String> getHeaders() {
        return headers;
    }

    // Getter method to retrieve logMap
    public List<Map<String, String>> getLogMap() {
        return logMap;
    }
    
    // Getter method to retrieve the file name
    public String getFileName(){
        return this.fileName;
    }
}
