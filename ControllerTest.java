import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ControllerTest {

    private final InputStream systemIn = System.in;
    private final PrintStream systemOut = System.out;
    private ByteArrayInputStream testIn;
    private ByteArrayOutputStream testOut;

    @Before
    public void setUpOutput() {
        testOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(testOut));
    }

    @After
    public void restoreSystemInputOutput() {
        System.setIn(systemIn);
        System.setOut(systemOut);
    }


    @Test
    public void testDisplayMainMenu(){
        Controller test = new Controller();

        // Redirect System.out to capture printed output
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        // Call the printLogs method
       

        // Reset System.out to normal
        System.setOut(System.out);

        // Expected output with leading/trailing spaces
        testIn = new ByteArrayInputStream("1".getBytes());

        System.setIn(testIn);
        int output = test.displayMainMenu();
        String expectedOutput = "Welcome to Loafr!!\n1. Analyze Logs\n" + "2. Exit\n\n" + "Select Option: ";

        assertEquals(expectedOutput, outContent.toString());
        assertEquals(output, 1);

    }

    @Test
    public void testDisplayAnalysisMenu(){
        Controller test = new Controller();

        // Redirect System.out to capture printed output
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        // Call the printLogs method
       

        // Reset System.out to normal
        System.setOut(System.out);

        // Expected output with leading/trailing spaces
        testIn = new ByteArrayInputStream("4".getBytes());

        System.setIn(testIn);
        int output = test.displayAnalysisMenu();
        String expectedOutput = "\nLog analyzation options:\n1. Search\n2. Filter <TODO>\n3. Sort <TODO>\n4. Save\n5. Count\n6. Exit\n\nSelect Option: ";

        assertEquals(expectedOutput, outContent.toString());
        assertEquals(output, 4);
    }

    @Test
    public void testSelectOption(){
        Controller test = new Controller();

        // Redirect System.out to capture printed output
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        // Call the printLogs method
       

        // Reset System.out to normal
        System.setOut(System.out);

        // Expected output with leading/trailing spaces
        testIn = new ByteArrayInputStream("1".getBytes());

        System.setIn(testIn);
        int output = test.selectOption();
        String expectedOutput = "Select Option: ";

        assertEquals(expectedOutput, outContent.toString());
        assertEquals(output, 1);

    }

    @Test
    public void testdisplaySaveMenu(){
        Controller test = new Controller();

        // Redirect System.out to capture printed output
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        // Call the printLogs method
       

        // Reset System.out to normal
        System.setOut(System.out);

        // Expected output with leading/trailing spaces
        testIn = new ByteArrayInputStream("trashfile".getBytes());

        System.setIn(testIn);
        String output = test.displaySaveMenu();
        String expectedOutput = "Save file as: ";

        assertEquals(expectedOutput, outContent.toString());
        assertEquals(output, "trashfile");

    }

    @Test
    public void testDisplayKeyValueMenu(){
        Controller test = new Controller();

        // Redirect System.out to capture printed output
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        // Call the printLogs method
       

        // Reset System.out to normal
        System.setOut(System.out);

        // Expected output with leading/trailing spaces
        testIn = new ByteArrayInputStream("x\n2\n".getBytes());

        System.setIn(testIn);

        LogFile file = new LogFile("./inputLogsTest/" + "test2.csv");
        String[] output = test.displayKeyValueMenu(file.getHeaders());
        String expectedOutput = "Enter Key: Enter Value: ";

        assertEquals(expectedOutput, outContent.toString());

        String[] expOutPut = new String[]{"x", "2"};
    
        
        assertArrayEquals(output, expOutPut);
    }

    //Test for incorrect input
    @Test
    public void testDisplayKeyValueMenu2(){
        Controller test = new Controller();

        // Redirect System.out to capture printed output
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        // Call the printLogs method
       

        // Reset System.out to normal
        System.setOut(System.out);

        // Expected output with leading/trailing spaces
        testIn = new ByteArrayInputStream("o\n1\nx\n1\n".getBytes());

        System.setIn(testIn);

        LogFile file = new LogFile("./inputLogsTest/" + "test2.csv");
        String[] output = test.displayKeyValueMenu(file.getHeaders());
        String expectedOutput = "Enter Key: Enter Value: Invalid Key, please try again\n\n" +
                "Enter Key: Enter Value: ";

        assertEquals(expectedOutput, outContent.toString());

        String[] expOutPut = new String[]{"x", "1"};
    
        
        assertArrayEquals(output, expOutPut);
    }

    



    
}
