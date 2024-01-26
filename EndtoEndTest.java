import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Collections;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

public class EndtoEndTest {

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
    public void testEndToEnd() {
        // Arrange
        testIn = new ByteArrayInputStream("1\n1\n1\nkey\nvalue\n5\n".getBytes());
        System.setIn(testIn);

        // Inject the mock LogFile into the Controller
        Controller controller = new Controller();
        controller.run();  // This will execute the simulated user input

        // Assert
        String expectedOutput = "Welcome to Loafr!!\n" +
                "1. Analyze Logs\n" +
                "2. Exit\n" +
                "\n" +
                "Available Files:\n" +
                "1. log1.txt\n" +
                "\n" +
                "Select Option: \n" +
                "\n" +
                "Select Option: \n" +
                "1. Search\n" +
                "2. Filter <TODO>\n" +
                "3. Sort <TODO>\n" +
                "4. Save\n" +
                "5. Exit\n" +
                "\n" +
                "Key: \n" +
                "Value: \n" +
                "Search results:\n" +
                // Insert expected output from search functionality here
                "\n" +
                "Select Option: \n" +
                "Returning to main menu\n" +
                "\n" +
                "Welcome to Loafr!!\n" +
                "1. Analyze Logs\n" +
                "2. Exit\n" +
                "\n";
        assertEquals(expectedOutput, testOut.toString());
    }
}