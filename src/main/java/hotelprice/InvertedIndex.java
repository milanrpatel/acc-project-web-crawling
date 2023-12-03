package hotelprice;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Represents an inverted index that maps words to the set of hotels containing
 * those words.
 */
public class InvertedIndex {

    // Map that stores the index information, mapping words to the set of hotel
    // names containing those words
    Map<String, HashSet<String>> indexOfHotelList = new HashMap<>();

    // Map of hotels to be indexed
    Map<String, Hotel> hotelMap;

    /**
     * Constructs an InvertedIndex with the specified hotel map.
     *
     * @param hotelMap The map of hotels to be indexed.
     */
    public InvertedIndex(Map<String, Hotel> hotelMap) {
        this.hotelMap = hotelMap;
    }

    /**
     * Adds the specified hotel to the inverted index.
     *
     * @param hotel The hotel to be added to the index.
     */
    public void addToIndexOfHotel(Hotel hotel) {
        // Retrieve the words associated with the hotel
        String[] words = hotel.getWords();
        // Get the name of the hotel
        String hotelName = hotel.getName();

        // Iterate through the words and update the index
        for (String w : new HashSet<>(Arrays.asList(words))) {
            // Convert the word to lowercase for case-insensitive indexing
            String word = w.toLowerCase();
            // Retrieve the set of hotel names associated with the word
            HashSet<String> hotelNameList = indexOfHotelList.get(word);

            // If the set is not already created, create a new one
            if (hotelNameList == null) {
                hotelNameList = new HashSet<>();
                indexOfHotelList.put(word, hotelNameList);
            }

            // Add the current hotel name to the set
            hotelNameList.add(hotelName);
        }
    }

    /**
     * Creates the inverted index by adding all hotels from the hotel map.
     */
    public void createIndex() {
        // Iterate through all hotels in the map and add them to the index
        for (Hotel hotel : hotelMap.values()) {
            addToIndexOfHotel(hotel);
        }
    }

    /**
     * Searches the inverted index for the specified words and returns the set of
     * hotels
     * containing those words.
     *
     * @param words The array of words to search for.
     * @return The set of hotel names containing the specified words.
     */
    public Set<String> search(String[] words) {
        // Set to store the resulting hotel names
        Set<String> hotelSet = new HashSet<>();

        // Iterate through the input words
        for (String w : words) {
            // Convert the word to lowercase for case-insensitive searching
            String word = w.toLowerCase();
            // Retrieve the set of hotel names associated with the word
            HashSet<String> hotelNamesList = indexOfHotelList.get(word);

            // If the set is not null, add all hotel names to the result set
            if (hotelNamesList != null) {
                for (String hotel : hotelNamesList) {
                    hotelSet.add(hotel);
                }
            }
        }

        // Return the set of hotel names containing the specified words
        return hotelSet;
    }
}
