//Christian Palma
// CS 145
// 7/26/2023
import java.util.Scanner;
import java.util.Random;

// The word search generator interacts with the user to create 
// a word puzzle. The size of the grid can be determined in the main method
public class WordSearchGenerator {
    private char[][] wordSearch;
    private char[][] wordSearchSolution;
    private String[] wordList;
    private int wordSearchSize;

    // Set the initial size of the word search grid
    public static void main(String[] args) {
        WordSearchGenerator generator = new WordSearchGenerator(20); 
        generator.run();
    }
    // Calculate the maximum word length in the word list
    // Adjust the word search size based on the longest word
        int maxWordLength = 0;
    public WordSearchGenerator(int size) {
        wordSearchSize = size;
        wordSearch = new char[wordSearchSize][wordSearchSize];
        wordSearchSolution = new char[wordSearchSize][wordSearchSize];
        wordList = new String[0];
    
        
        for (String word : wordList) {
            maxWordLength = Math.max(maxWordLength, word.length());
        }
    
        
        wordSearchSize = Math.max(maxWordLength * 2, wordSearchSize);
    }
    
    
//intoduces the options to the user
    public void printIntro() {
        System.out.println("Welcome to Word Search Generator!");
        System.out.println("Choose an action:");
        System.out.println("g - Generate a new word search");
        System.out.println("p - Print the current word search");
        System.out.println("s - Show the solution");
        System.out.println("q - Quit the program");
    }

    // the user words are stored in a list. This method is 
    // where the grid of the puzzle is made base on the largest word
    public void generate() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the number of words: ");
        int numWords = Integer.parseInt(scanner.nextLine());

        wordList = new String[numWords];
        int maxLength = 0;

        for (int i = 0; i < numWords; i++) {
            System.out.print("Enter word " + (i + 1) + ": ");
            wordList[i] = scanner.nextLine().toUpperCase();
            maxLength = Math.max(maxLength, wordList[i].length());
        }

        int maxWordLength = 0;
        for (String word : wordList) {
            maxWordLength = Math.max(maxWordLength, word.length());
        }
        wordSearchSize = Math.max(maxLength * 2, wordSearchSize);

        wordSearch = new char[wordSearchSize][wordSearchSize];
        wordSearchSolution = new char[wordSearchSize][wordSearchSize];

        for (int i = 0; i < wordSearchSize; i++) {
            for (int j = 0; j < wordSearchSize; j++) {
                wordSearch[i][j] = 'X';
                wordSearchSolution[i][j] = 'X';
            }
        }

        // Attempt to place each word in the word search
        for (String word : wordList) {
            if (!placeWord(word)) {
                System.out.println("Could not place word '" + word + "' in the word search.");
            }
        }

        // Fill the remaining cells with random letters
        Random random = new Random();
        for (int i = 0; i < wordSearchSize; i++) {
            for (int j = 0; j < wordSearchSize; j++) {
                if (wordSearch[i][j] == 'X') {
                    wordSearch[i][j] = (char) (random.nextInt(26) + 'A');
                    wordSearchSolution[i][j] = wordSearch[i][j]; 
                }
            }
        }
    }
    // this method places the words from the user in a random order and direction
    // it checks if there is a word there and returns true or false
    // if the cell is filled with and X the word can be places in there.
    private boolean placeWord(String word) {
        Random random = new Random();
        int wordLength = word.length();

        for (int attempt = 0; attempt < attempt + 1; attempt++) { // attempt + 1 is an infinate
            int direction = random.nextInt(3); // 0: horizontal, 1: vertical, 2: diagonal
            int startRow = random.nextInt(wordSearchSize);
            int startCol = random.nextInt(wordSearchSize);

            boolean canPlaceWord = true;

            for (int i = 0; i < wordLength; i++) {
                if (startRow >= wordSearchSize || startCol >= wordSearchSize) {
                    canPlaceWord = false;
                    break;
                }

                char currentCell = wordSearch[startRow][startCol];
                char currentSolutionCell = wordSearchSolution[startRow][startCol];

                if (currentCell != 'X' && currentCell != word.charAt(i)) {
                    canPlaceWord = false;
                    break;
                }

                if (currentSolutionCell != 'X' && currentSolutionCell != word.charAt(i)) {
                    canPlaceWord = false;
                    break;
                }

                switch (direction) {
                    case 0: 
                        startCol++;
                        break;
                    case 1:
                        startRow++;
                        break;
                    case 2: 
                        startRow++;
                        startCol++;
                        break;
                }
            }

            if (canPlaceWord) {
                startRow = random.nextInt(wordSearchSize);
                startCol = random.nextInt(wordSearchSize);

                for (int i = 0; i < wordLength; i++) {
                    wordSearch[startRow][startCol] = word.charAt(i);
                    wordSearchSolution[startRow][startCol] = word.charAt(i);

                    switch (direction) {
                        case 0: 
                            startCol++;
                            break;
                        case 1: 
                            startRow++;
                            break;
                        case 2: 
                            startRow++;
                            startCol++;
                            break;
                    }
                }
                return true;
            }
        }

        return false;
    }
    // prints out the puzzle structure and all random letters
    public void printWordSearch(char[][] grid) {
        for (int i = 0; i < wordSearchSize; i++) {
            for (int j = 0; j < wordSearchSize; j++) {
                System.out.print(grid[i][j] + " ");
            }
            System.out.println();
        }
    }
// the solutions are prionted in the grid by chacking if the cell contains 
// a random letter reprecented by X or a letter from the word list
    public void printWordSearchSolution() {
        for (int i = 0; i < wordSearchSize; i++) {
            for (int j = 0; j < wordSearchSize; j++) {
                char cell = wordSearchSolution[i][j];
                if (Character.isUpperCase(cell)) {
                    System.out.print(Character.toUpperCase(cell) + " "); // Print uppercase letter
                } else if (cell == 'X') {
                    System.out.print('X' + " "); 
                } else {
                    System.out.print(cell + " "); 
                }
            }
            System.out.println();
        }
    }
    // the options for the user are given a task in this infinate 
    // loop untill the user desides to quit the program
    public void run() {
        Scanner scanner = new Scanner(System.in);
        printIntro();

        while (true) {
            System.out.print("Enter your choice: ");
            String choice = scanner.nextLine().toLowerCase();

            switch (choice) {
                case "g":
                    generate();
                    break;
                case "p":
                    if (wordList.length == 0) {
                        System.out.println("Please generate a word search first.");
                    } else {
                        printWordSearch(wordSearch);
                    }
                    break;
                    case "s":
                    if (wordList.length == 0) {
                        System.out.println("Please generate a word search first.");
                    } else {
                        printWordSearchSolution();
                        System.out.println("Word List:");
                        for (String word : wordList) {
                            System.out.println(word);
                        }
                    }
                    break;
                case "q":
                    System.out.println("Exiting the program.");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
