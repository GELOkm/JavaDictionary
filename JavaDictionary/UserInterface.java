/** GELOkm

 * This class provides a console-based user interface for a dictionary application.
 * It allows users to search for words, translate words from English to French,
 * view their search history up to a certain number of entries, and exit the application.
 */


import java.util.Scanner;

/**
 * Provides a console-based user interface for interacting with a dictionary application.
 * It allows users to search for English words, translate them to French, view their search history,
 * or exit the application. The class demonstrates basic input/output operations, conditionals,
 * and looping structures in Java.
 */
public class UserInterface {
    /**
     * Path to the dictionary file.
     */
    static String path = "Assets/dictionaryFile.csv";

    /**
     * StringBuilder to accumulate user search history.
     */
    static StringBuilder history = new StringBuilder();

    /**
     * Counter to keep track of the number of items in the search history.
     */
    static int historyCount = 0;

    /**
     * Main method that drives the user interface.
     * It handles user input for searching and translating words,
     * viewing search history, and exiting the application.
     *
     * @param args Command line arguments (not used).
     */
    
    public static void main(String[] args) {
        Dictionary.loadDictionary(path); // Load the dictionary from a file.
        Scanner scanner = new Scanner(System.in); // Scanner for reading user input.
        showMenu(); // Display the main menu options to the user.

        String userWord = ""; // Variable to hold user's word input.
        int choice = 0; // Variable to hold user's menu choice.
        boolean validEntry = false; // Flag to check for valid menu entry.

        // Loop until a valid menu choice is made.
        while (!validEntry) {
            if (scanner.hasNextInt()) {
                choice = scanner.nextInt(); // Read the user's choice.
                if (choice >= 1 && choice <= 4) { // Validate the user's choice.
                    validEntry = true; // Mark the entry as valid.
                } else {
                    System.out.println("Invalid entry. Please enter 1, 2, 3 or 4.");
                }
            } else {
                System.out.println("Invalid entry. Please enter 1, 2, 3 or 4.");
                scanner.next(); // Consume the invalid input.
            }
        }

        // Process the user's choice based on the validated input.
        if (choice == 1) { // Search for a word
            enterSearch(); // Prompt user to enter a word to search
            Scanner scanner2 = new Scanner(System.in);
            userWord = scanner2.nextLine();

            if (!userWord.trim().isEmpty()) {
                historyFunc(userWord); // Add word to search history

                // Print the search result
                System.out.println(Dictionary.getEntry(userWord.toLowerCase()));
                System.out.println("Press \"Enter\" for main menu.");
                Scanner scanner3 = new Scanner(System.in);
                scanner3.nextLine();

                // Restart the main method to show the main menu again
                main(args);

            } else {
                // If no word is entered, show the main menu again
                main(args);
            }

        } else if (choice == 2) { // Translate a word
            enterTranslate(); // Prompt user to enter a word to translate
            Scanner scanner2 = new Scanner(System.in);
            userWord = scanner2.nextLine();

            if (!userWord.trim().isEmpty()) {
                historyFunc(userWord); // Add word to search history

                // Print the translation result
                System.out.println(Dictionary.getFrenchWord(userWord.toLowerCase()));
                System.out.println("Press \"Enter\" for main menu.");
                Scanner scanner3 = new Scanner(System.in);
                scanner3.nextLine();

                // Restart the main method to show the main menu again
                main(args);

            } else {
                // If no word is entered, show the main menu again
                main(args);
            }

        } else if (choice == 3) { // Show search history
            showHistory(); // Display the search history to the user

            System.out.println("Press \"Enter\" for main menu.");
            Scanner scanner1 = new Scanner(System.in);
            scanner1.nextLine();

            // Restart the main method to show the main menu again
            main(args);
        }
        // Close scanner
        scanner.close();
    }


    /**
     * Displays the main menu options to the user.
     * This menu allows the user to choose between searching for a word, translating a word,
     * viewing their search history, or quitting the application.
     */
    public static void showMenu() {
        System.out.println("""
                Welcome to the Java Dictionary!
                Enter 1 to search a word
                Enter 2 to translate a word
                Enter 3 to show your search history
                Enter 4 to quit""");
    }

    /**
     * Prompts the user to enter an English word for searching in the dictionary.
     * This method facilitates the search functionality of the application.
     */
    public static void enterSearch() {
        System.out.println("Enter word to search:");
    }

    /**
     * Prompts the user to enter an English word for translation.
     * This method supports the translation functionality of the application,
     * allowing users to find French equivalents of English words.
     */
    public static void enterTranslate() {
        System.out.println("Enter word to translate");
    }

    /**
     * Displays the search history to the user.
     * This method shows the words that have been previously searched or translated,
     * providing a quick way to review past queries.
     */
    public static void showHistory() {
        System.out.println(history);
    }

    /**
     * Adds a user's word to the search history and manages the size of the history.
     * If the history exceeds 8 entries, the oldest entry is removed, ensuring the history
     * remains a manageable size and focuses on the most recent searches.
     *
     * @param userWord The word entered by the user to be added to the search history.
     */
    public static void historyFunc(String userWord) {
        StringBuilder temp = new StringBuilder(); // Temporary StringBuilder to hold the new history.
        temp.append(userWord).append("\n").append(history); // Prepend the new word to the history.
        history = temp; // Update the history with the new entry.
        historyCount++; // Increment the count of history entries.

        if (historyCount >= 8) { // If history exceeds 8 entries, remove the oldest.
            int lastNewlineIndex = history.lastIndexOf("\n");
            if (lastNewlineIndex != -1) {
                history.delete(lastNewlineIndex, history.length()); // Remove the oldest entry.
            }
        }
    }
}