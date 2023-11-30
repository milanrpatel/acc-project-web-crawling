package hotelprice;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

/**
 * PageRank class to rank hotel pages based on scores.
 */
public class PageRank {

    PriorityQueue<Page> priorityQueue = new PriorityQueue<Page>(10, new PageComparator());;
    Map<String, Integer> hotelNameScoreMap;

    /**
     * Constructor to initialize PageRank with a map of hotel names and scores.
     *
     * @param documentScoreMap The map containing hotel names and their scores.
     */
    public PageRank(Map<String, Integer> documentScoreMap) {
        this.hotelNameScoreMap = documentScoreMap;
    }

    /**
     * Ranks the pages based on their scores and adds them to the priority queue.
     */
    public void rankPages() {
        for (String hotelName : hotelNameScoreMap.keySet()) {
            int score = hotelNameScoreMap.get(hotelName);
            Page page = new Page(hotelName, score);
            priorityQueue.add(page);
        }
    }

    /**
     * Retrieves the top K hotels based on their scores.
     *
     * @param k The number of top hotels to retrieve.
     * @return A list of the top K hotel names.
     */
    public List<String> getTopKHotels(int k) {
        List<String> list = new LinkedList<>();
        for (int i = 0; i < k; i++) {
            if (priorityQueue.isEmpty()) {
                break;
            }
            list.add(priorityQueue.remove().hotelName);
        }
        return list;
    }
}

/**
 * Page class representing a hotel page with a score.
 */
class Page {
    public int score;
    public String hotelName;

    /**
     * Constructor to initialize a Page with a hotel name and a score.
     *
     * @param hotelName The name of the hotel.
     * @param score     The score associated with the hotel page.
     */
    public Page(String hotelName, int score) {
        this.score = score;
        this.hotelName = hotelName;
    }
}

/**
 * PageComparator class to compare Page objects based on their scores.
 */
class PageComparator implements Comparator<Page> {
    /**
     * Compares two Page objects based on their scores.
     *
     * @param page1 The first Page object.
     * @param page2 The second Page object.
     * @return An integer representing the comparison result.
     */
    public int compare(Page page1, Page page2) {
        if (page1.score < page2.score)
            return 1;
        else if (page1.score > page2.score)
            return -1;
        return 0;
    }
}