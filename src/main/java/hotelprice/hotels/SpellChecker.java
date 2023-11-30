package hotelprice.hotels;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

/**
 * Utility class for spell checking and providing suggestions based on edit
 * distances.
 */
public class SpellChecker {
	/**
	 * Calculates the edit distance between two strings using dynamic programming.
	 *
	 * @param s1 The first string.
	 * @param s2 The second string.
	 * @return The edit distance between the two strings.
	 */
	public static int calculateEditDistance(String s1, String s2) {
		int s1Len = s1.length();
		int s2Len = s2.length();
		int[][] arr = new int[2][s1Len + 1];
		for (int i = 0; i <= s1Len; i++)
			arr[0][i] = i;
		for (int i = 1; i <= s2Len; i++) {
			for (int j = 0; j <= s1Len; j++) {
				if (j == 0)
					arr[i % 2][j] = i;
				else if (s1.charAt(j - 1) == s2.charAt(i - 1)) {
					arr[i % 2][j] = arr[(i - 1) % 2][j - 1];
				} else {
					arr[i % 2][j] = 1
							+ Math.min(arr[(i - 1) % 2][j], Math.min(arr[i % 2][j - 1], arr[(i - 1) % 2][j - 1]));
				}
			}
		}

		return arr[s2Len % 2][s1Len];
	}

	/**
	 * Calculates edit distances between a keyword and a list of words and returns a
	 * map of words
	 * with their corresponding edit distances.
	 *
	 * @param keyword The keyword to compare distances against.
	 * @param words   The list of words to calculate distances for.
	 * @param n       The number of least distances to consider.
	 * @return A map containing words and their edit distances sorted in ascending
	 *         order.
	 */
	public static Map<String, Integer> getEditDistances(String keyword, ArrayList<String> words, int n) {
		Map<String, Integer> distances = new HashMap<String, Integer>();
		for (String s : words) {
			distances.put(s, calculateEditDistance(keyword, s));
		}
		Comparator<Entry<String, Integer>> valueComparator = new Comparator<Entry<String, Integer>>() {
			@Override
			public int compare(Entry<String, Integer> e1, Entry<String, Integer> e2) {
				Integer v1 = e1.getValue();
				Integer v2 = e2.getValue();
				return v1.compareTo(v2);
			}
		};
		Set<Entry<String, Integer>> entries = distances.entrySet();
		List<Entry<String, Integer>> listOfEntries = new ArrayList<Entry<String, Integer>>(entries);
		Collections.sort(listOfEntries, valueComparator);
		LinkedHashMap<String, Integer> sortedByValue = new LinkedHashMap<String, Integer>(listOfEntries.size());
		for (Entry<String, Integer> entry : listOfEntries) {
			sortedByValue.put(entry.getKey(), entry.getValue());
		}
		int counter = 0;
		Map<String, Integer> leastNDistances = new HashMap<String, Integer>();
		for (Entry<String, Integer> mapping : sortedByValue.entrySet()) {
			if (counter == n)
				break;
			leastNDistances.put(mapping.getKey(), mapping.getValue());
			counter++;
		}
		return leastNDistances;
	}

	/**
	 * Retrieves suggestions for each keyword based on edit distances from a list of
	 * words.
	 *
	 * @param keywords The array of keywords to get suggestions for.
	 * @param words    The list of words to compare against.
	 * @return A map where each keyword is associated with a list of suggested
	 *         words.
	 */
	public Map<String, List<String>> getSuggestions(String[] keywords, ArrayList<String> words) {
		Map<String, List<String>> map = new HashMap<>();
		for (String keyword : keywords) {
			Map<String, Integer> distances = new HashMap<>();
			List<String> suggestedWordList = new ArrayList<>();
			distances = getEditDistances(keyword, words, 3);
			for (String suggestedWord : distances.keySet()) {
				suggestedWordList.add(suggestedWord);
			}
			map.put(keyword, suggestedWordList);
		}
		return map;
	}
}
