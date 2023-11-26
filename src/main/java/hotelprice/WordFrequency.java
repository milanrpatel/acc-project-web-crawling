package hotelprice;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WordFrequency {
    // first integer is for documentIndex, and map represents word to count mapping.
    // count is document-wise for the given word.
    Map<String, Map<String, Integer>> frequencyMapOfEachHotel = new HashMap<>();
    Map<String, Hotel> hotelMap;

    public WordFrequency(Map<String, Hotel> hotelMap) {
        this.hotelMap = hotelMap;
    }

    /*public void setWordFrequencies() {
        for (Hotel hotel : hotelMap.values()) {
            String[] words = hotel.getWords();
            String hotelName = hotel.getName();

            // store word frequencies for current document
            Map<String, Integer> wordFrequencyMap = new HashMap<>();
            for (String w : words) {
                String word = w.toLowerCase();
                if (wordFrequencyMap.containsKey(word)) {
                    // System.out.println("inside contains");
                    wordFrequencyMap.put(word, wordFrequencyMap.get(word) + 1);
                } else {
                    wordFrequencyMap.put(word, 1);
                }
            }

            // put map wordFrequencyMap with current document
            frequencyMapOfEachHotel.put(hotelName, wordFrequencyMap);
        }
        // System.out.println(wordFrequency.toString());
    }

    public Map<String, Integer> calculateScores(String[] keywords, Set<String> hotelNamesSet) {
        Map<String, Integer> documentScoreMap = new HashMap<>();
        for (String hotelName : hotelNamesSet) {
            Map<String, Integer> wordFrequencyMap = frequencyMapOfEachHotel.get(hotelName);
            int score = 0;
            for (String keyword : keywords) {
                if (wordFrequencyMap.containsKey(keyword)) {
                    score += wordFrequencyMap.get(keyword);
                }
            }
            documentScoreMap.put(hotelName, score);
        }
        return documentScoreMap;
    }*/

    public void setWordFrequencies() {
        for (Hotel hotel : hotelMap.values()) {
            String[] words = hotel.getWords();
            String hotelName = hotel.getName();

            // store word frequencies for the current document
            Map<String, Integer> wordFrequencyMap = new HashMap<>();

            // Define a regex pattern for extracting words (ignoring punctuation and case)
            Pattern wordPattern = Pattern.compile("\\b\\w+\\b");

            for (String word : words) {
                // Use regular expression to match words
                Matcher matcher = wordPattern.matcher(word.toLowerCase());

                // Iterate through matches and update word frequencies
                while (matcher.find()) {
                    String matchedWord = matcher.group();
                    wordFrequencyMap.put(matchedWord, wordFrequencyMap.getOrDefault(matchedWord, 0) + 1);
                }
            }

            // put map wordFrequencyMap with the current document
            frequencyMapOfEachHotel.put(hotelName, wordFrequencyMap);
        }
    }

    /**
     * @param keywords
     * @param hotelNamesSet
     * @return
     */
    public Map<String, Integer> calculateScores(String[] keywords, Set<String> hotelNamesSet) {
        Map<String, Integer> documentScoreMap = new HashMap<>();
        for (String hotelName : hotelNamesSet) {
            Map<String, Integer> wordFrequencyMap = frequencyMapOfEachHotel.get(hotelName);
            int score = 0;
            for (String keyword : keywords) {
                if (wordFrequencyMap.containsKey(keyword)) {
                    score += wordFrequencyMap.get(keyword);
                }
            }
            documentScoreMap.put(hotelName, score);
        }
        return documentScoreMap;
    }

    public void printIndex() {
        this.frequencyMapOfEachHotel.forEach((key, value) -> System.out.println(key + ":" + value));
    }
}
