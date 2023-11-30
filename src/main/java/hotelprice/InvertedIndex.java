package hotelprice;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * This represents an Inverted_Index that will map the words to the set of hotels that contain
 * those words that are needed.
 */
public class InvertedIndex {

    Map<String, HashSet<String>> indexOfHotelList = new HashMap<>();
    Map<String, Hotel> hotelMap;

    /**
     * This will Construct an InvertedIndex with the specified hotel map.
     *
     * @param hotelMap The map of hotels to be indexed.
     */

    public InvertedIndex(Map<String, Hotel> hotelMap) {
        this.hotelMap = hotelMap;
    }

    /**
     * This code adds the specified hotel to the Inverted_Index.
     *
     * @param hotel The hotel to be added to the index.
     */
    public void addToIndex(Hotel hotel) {
        String[] words = hotel.getWords(); // This will get the words that are required.
        String hotelName = hotel.getName(); // This will get the name of the Hotels.

        for (String w : new HashSet<String>(Arrays.asList(words))) {
            String word = w.toLowerCase(); // Converting it to a lowercase.
            HashSet<String> hotelNameList = indexOfHotelList.get(word);
            if (hotelNameList == null) {
                hotelNameList = new HashSet<String>();
                indexOfHotelList.put(word, hotelNameList); //This will put the words and Hotel Name list to the Hash Set.
            }
            hotelNameList.add(hotelName);
        }

    }

    /**
     * This code adds the specified hotel to the Inverted_Index.
     *
     * @param hotel The hotel to be added to the index.
     */
    public void createIndex() {
        for (Hotel hotel : hotelMap.values()) {
            addToIndex(hotel); // This will add to the index.
        }
        // System.out.println(indexList.toString());
    }
    /**
     * This function will searche the Inverted_Index for the specified words and  it will return the set of
     * hotels that
     * contains those words.
     *
     * @param words The array of words to search for.
     * @return The set of hotel names containing the specified words.
     */
    public Set<String> search(String[] words) {
        Set<String> hotelSet = new HashSet<>();
        for (String w : words) {
            String word = w.toLowerCase(); // Converting it to a lowercase.
            HashSet<String> hotelNamesList = indexOfHotelList.get(word);
            if (hotelNamesList != null) {
                for (String hotel : hotelNamesList) {
                    hotelSet.add(hotel); //This will add the Hotel to the Hash Set
                }
            }
        }
        return hotelSet; //returns
    }
}