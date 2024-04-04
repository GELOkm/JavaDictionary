/** GELOkm


  This class handles the reading of the dictionary file, the initialisation of each word
  and the logic that will provide the entry and translation of each word.
  It also handles the logic for word suggestions.
 */


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

/**
 * Handles the initialization and logic for dictionary operations including
 * reading the dictionary file, providing translations, dictionary entries, and
 * word suggestions.
 */
public class Dictionary {
    /** Static dictionary to hold Word objects with English words as keys. */
    private static final HashMap<String, Word> dictionary = new HashMap<>();

    /** Default path for the dictionary file. */
    static String path = "Assets/dictionaryFile.csv";

    /**
     * Constructor to load the dictionary from a specified file path.
     * @param path The path of the dictionary file to be loaded.
     */
    public Dictionary(String path) {
        loadDictionary(path);
    }

    /**
     * Loads the dictionary from a given file path into a HashMap.
     * @param path The file path of the dictionary data.
     */
    public static void loadDictionary(String path) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            String line;
            br.readLine(); // Skip the header line

            while ((line = br.readLine()) != null) {
                String[] values = line.split(",", 4);
                if (values.length >= 4) {
                    dictionary.put(values[0].trim().toLowerCase(),
                            new Word(values[0].trim(), values[1].trim(), values[2].trim(), values[3].trim()));
                }
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Dictionary file not found: " + e.getMessage(), e);
        } catch (IOException e) {
            throw new RuntimeException("Error reading dictionary file: " + e.getMessage(), e);
        }
    }

    /**
     * Retrieves the French translation of a given English word.
     * @param userWord The English word to translate.
     * @return The French translation of the word if found, otherwise suggests close words.
     */
    public static String getFrenchWord(String userWord) {
        Word foundWord = dictionary.get(userWord.toLowerCase());
        return (foundWord != null) ? foundWord.getFrenchWord() : closeWords(userWord);
    }

    /**
     * Retrieves the dictionary entry for a given English word.
     * @param userWord The English word to look up.
     * @return The dictionary entry if the word is found, otherwise suggests close words.
     */
    public static String getEntry(String userWord) {
        Word foundWord = dictionary.get(userWord.toLowerCase());
        if (foundWord != null) {
            return String.format("%s %s %s", foundWord.getEnglishWord(), foundWord.getType(), foundWord.getDefinition());
        } else {
            return closeWords(userWord);
        }
    }

    /**
     * Represents a word in the dictionary with its English word, French translation,
     * part of speech, and definition.
     */
    public static class Word {
        private String englishWord;
        private String frenchWord;
        private String type;
        private String definition;

        /**
         * Constructs a Word object with English and French words, part of speech, and definition.
         * @param englishWord The English word.
         * @param frenchWord The French translation of the English word.
         * @param type The part of speech of the word.
         * @param definition The definition of the word.
         */



        public Word(String englishWord, String frenchWord, String type, String definition) {
            this.englishWord = englishWord;
            this.frenchWord = frenchWord;
            this.type = type;
            this.definition = definition;
        }

        /**
         * Gets the English word.
         *
         * @return The English word.
         */
        public String getEnglishWord() { return englishWord; }

        /**
         * Gets the French translation of the English word.
         *
         * @return The French translation.
         */
        public String getFrenchWord() { return frenchWord; }

        /**
         * Gets the part of speech of the word.
         *
         * @return The part of speech.
         */
        public String getType() { return type; }

        /**
         * Gets the definition of the word.
         *
         * @return The definition.
         */
        public String getDefinition() { return definition; }
    }


    // Static method to suggest words based the user's word's prefixes
    /**
     * Suggests words that are close to the user's input based on prefixes if the exact word is not found in the dictionary.
     * This method is useful for providing suggestions for misspelled words or partial word inputs.
     *
     * @param userWord The user's input word.
     * @return A string containing close word suggestions or a message indicating no similar words were found.
     */
    public static String closeWords(String userWord) {
        StringBuilder similarWords = new StringBuilder();
        int count = 0;

        // Loop through each word in the dictionary
        for (String key : dictionary.keySet()) {
            // If the dictionary key starts with the user's input word, add it to the list of suggestions
            if (key.startsWith(userWord)) {
                similarWords.append(dictionary.get(key).englishWord).append("\n");
                count++;
            }
        }
        if (count > 0) {
            // Return the list of suggestions, removing the last newline character
            return "Your word was not found. Try these similar words:\n" + similarWords.toString().trim();
        } else if (!userWord.isEmpty() && userWord.length() > 1) {
            // If no similar words are found and the userWord is not empty, recursively call the method with one less character
            return closeWords(userWord.substring(0, userWord.length() - 1));
        } else {
            // If no similar words are found and no more characters can be removed, return a message indicating no suggestions
            return "No similar words were found :(";
        }
    }


}
