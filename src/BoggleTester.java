import org.junit.Test;
import org.junit.jupiter.api.Timeout;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class BoggleTester {
    private char[][] board;
    private String[] words;

    @Test
    public void testCorrectSmall() {
        setTestData(0);
    }

    @Test
    public void testTricky() {
        setTestData(1);
    }

    @Test
    public void testLarger() {
        setTestData(2);
    }

    @Test
    @Timeout(value = 100, unit = TimeUnit.MILLISECONDS)
    public void testEfficientLarge() {
        setTestData(3);
    }

    @Test
    @Timeout(value = 600, unit = TimeUnit.MILLISECONDS)
    public void testEdgeCase() {
        setTestData(4);
    }

    @Test
    @Timeout(value = 200, unit = TimeUnit.MILLISECONDS)
    public void testFullDictionary() {
        setTestData(5);
    }


    private void setTestData(int testNumber) {
        // Open files
        try {
            BufferedReader testReader = new BufferedReader(new FileReader("test_files/" + testNumber + ".txt"));
            BufferedReader answerReader = new BufferedReader(new FileReader("test_files/" + testNumber + "_answers.txt"));

            // Read solution
            String[] answer = new String[Integer.parseInt(answerReader.readLine())];
            for (int i = 0; i < answer.length; i++) {
                answer[i] = answerReader.readLine();
            }

            // Get the expected and provided solutions
            loadTest(testReader);
            String[] sol = Boggle.findWords(board, words);

            // Check for matching lengths first
            assertEquals(answer.length, sol.length, "Solution length does not match expected answer");

            // Check for matching Strings
            for (int i = 0; i < answer.length; i++)
                assertEquals(answer[i], sol[i],
                    "Test " + testNumber + " failed. Check test files for solution.");

        } catch (IOException e) {
            System.out.println("Error opening test file #" + testNumber);
            e.printStackTrace();
        }
    }

    private void loadTest(BufferedReader br) {
        String line;
        try {

            // Update instance variables with test data
            int n = Integer.parseInt(br.readLine());
            int m = Integer.parseInt(br.readLine());
            board = new char[n][m];

            for (int i = 0; i < n; i++) {
                line = br.readLine();
                for (int j = 0; j < m; j++) {
                    board[i][j] = line.charAt(j);
                }
            }

            words = new String[Integer.parseInt(br.readLine())];
            for (int i = 0; i < words.length; i++) {
                words[i] = br.readLine();
            }

        } catch (IOException e) {
            System.out.println("Error opening test file");
            e.printStackTrace();
        }
    }
}
