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

    Map<String, HashSet<String>> indexOfHotelList = new HashMap<>();
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
    public void addToIndex(Hotel hotel) {
        String[] words = hotel.getWords();
        String hotelName = hotel.getName();

        for (String w : new HashSet<String>(Arrays.asList(words))) {
            String word = w.toLowerCase();
            HashSet<String> hotelNameList = indexOfHotelList.get(word);
            if (hotelNameList == null) {
                hotelNameList = new HashSet<String>();
                indexOfHotelList.put(word, hotelNameList);
            }
            hotelNameList.add(hotelName);
        }

    }

    /**
     * Adds the specified hotel to the inverted index.
     *
     * @param hotel The hotel to be added to the index.
     */
    public void createIndex() {
        for (Hotel hotel : hotelMap.values()) {
            addToIndex(hotel);
        }
        // System.out.println(indexList.toString());
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
        Set<String> hotelSet = new HashSet<>();
        for (String w : words) {
            String word = w.toLowerCase();
            HashSet<String> hotelNamesList = indexOfHotelList.get(word);
            if (hotelNamesList != null) {
                for (String hotel : hotelNamesList) {
                    hotelSet.add(hotel);
                }
            }
        }
        return hotelSet;
    }

}