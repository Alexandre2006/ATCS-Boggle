import java.util.*;

public class Boggle {

    // Keep track of Boggle Game
    static char[][] board;
    static boolean[][] visited;

    static List<String> foundWords = new ArrayList<>();
    static String currentWord = "";

    // Dictionary
    static TrieNode trie = new TrieNode();

    public static String[] findWords(char[][] board, String[] dictionary) {

        // Keep track of visited tiles (and reset variables)
        visited = new boolean[board.length][board.length];
        Boggle.board = board;
        foundWords = new ArrayList<>();
        trie = new TrieNode();

        // Add words to Trie
        for (String word : dictionary) {
            trie.addWord(word);
        }

        // Run DFS for each tile
        for (int x = 0; x < board.length; x++) {
            for (int y = 0; y < board.length; y++) {
                DFS(x, y, trie);
            }
        }

        // Sort found words
        String[] sorted = new String[foundWords.size()];
        foundWords.toArray(sorted);
        Arrays.sort(sorted);
        System.out.println(Arrays.toString(sorted));
        return sorted;
    }

    private static void DFS(int x, int y, TrieNode parentNode) {
        // Base Case (1/3 - Ensure tile exists)
        if ((x < 0 || x >= board.length) || (y < 0 || y >= board.length)) {
            return;
        }

        // Base Case (2/3 - Ensure tile hasn't been visited)
        if (visited[y][x]) {
            return;
        }

        // Base case (3/3 - No possible words)
        char currentLetter = board[y][x];
        int currentLetterOffset = currentLetter - 97;
        if (parentNode.children[currentLetterOffset] == null) {
            return;
        } else if (parentNode.children[currentLetterOffset].isWord) {
            // Special Case (Is a valid word)
            foundWords.add(currentWord + currentLetter);
            parentNode.children[currentLetterOffset].isWord = false;
        }

        // Mark current tile as visited
        visited[y][x] = true;
        currentWord += currentLetter;

        // Visit neighbouring tiles (1/4 - NORTH)
        DFS(x, y+1, parentNode.children[currentLetterOffset]);

        // Visit neighbouring tiles (2/4 - EAST)
        DFS(x+1, y, parentNode.children[currentLetterOffset]);

        // Visit neighbouring tiles (3/4 - SOUTH)
        DFS(x, y-1, parentNode.children[currentLetterOffset]);

        // Visit neighbouring tiles (4/4 - WEST)
        DFS(x-1, y, parentNode.children[currentLetterOffset]);

        // Mark current tile as unvisited
        visited[y][x] = false;
        currentWord = currentWord.substring(0, currentWord.length() - 1);
    }

    private static class TrieNode {
        TrieNode[] children = new TrieNode[26];
        boolean isWord = false;

        void addWord(String word) {
            // If word is empty, mark current word as word
            if (Objects.equals(word, "")) {
                isWord = true;
                return;
            }

            // Otherwise, recurse
            int nextNode = word.charAt(0) - 97;
            if (children[nextNode] == null) {
                children[nextNode] = new TrieNode();
            }
            children[nextNode].addWord(word.substring(1));
        }
    }
}