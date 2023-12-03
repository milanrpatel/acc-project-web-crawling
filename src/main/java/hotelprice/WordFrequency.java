package hotelprice;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class representing word frequencies for hotels and providing methods to
 * calculate scores.
 */
public class WordFrequency {

    Map<String, Map<String, Integer>> eachHotelFrequencyList = new HashMap<>();
    Map<String, Hotel> hotelsMap;

    /**
     * Constructs a WordFrequency object with the given hotel map.
     *
     * @param hotelMap The map containing hotel information.
     */
    public WordFrequency(Map<String, Hotel> hotelMap) {
        this.hotelsMap = hotelMap;
    }

    /**
     * Sets the word frequencies for each hotel based on their descriptions.
     */
    public void setWordFrequencies() {
        for (Hotel hotel : hotelsMap.values()) {
            String[] words = hotel.getWords();
            String hotelName = hotel.getName();

            Map<String, Integer> wordFrequencyMap = new HashMap<>();

            // Defining regex-pattern for extractingWords (without regard for punctuation or
            // case).
            Pattern wordPattern = Pattern.compile("\\b\\w+\\b");

            for (String word : words) {
                Matcher matcher = wordPattern.matcher(word.toLowerCase());

                while (matcher.find()) {
                    String matchedWord = matcher.group();
                    wordFrequencyMap.put(matchedWord, wordFrequencyMap.getOrDefault(matchedWord, 0) + 1);
                }
            }
            eachHotelFrequencyList.put(hotelName, wordFrequencyMap);
        }
    }

    /**
     * Calculates scores based on given keywords and a set of hotel names.
     *
     * @param keywordsList      The array of keywords to calculate scores.
     * @param hotelNamesList The set of hotel names to consider for scoring.
     * @return A map containing hotel names and their corresponding scores.
     */
    public Map<String, Integer> calculateScores(String[] keywordsList, Set<String> hotelNamesList) {
        Map<String, Integer> documentScoreList = new HashMap<>();
        for (String hotelName : hotelNamesList) {
            Map<String, Integer> wordFrequencyList = eachHotelFrequencyList.get(hotelName);
            int ratingScore = 0;
            for (String keyword : keywordsList) {
                if (wordFrequencyList.containsKey(keyword)) {
                    ratingScore += wordFrequencyList.get(keyword);
                }
            }
            documentScoreList.put(hotelName, ratingScore);
        }
        return documentScoreList;
    }

    public void printIndex() {
        this.eachHotelFrequencyList.forEach((key, value) -> System.out.println(key + ":" + value));
    }
}
