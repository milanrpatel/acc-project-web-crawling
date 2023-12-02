package hotelprice;

import java.util.Scanner;
import java.util.Set;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.text.ParseException;
import java.util.Arrays;

/**
 * Main class containing the application logic.
 */
public final class App {
    WordFrequency wf;
    InvertedIndex invertedIndex;

    public String[] keywords;

    /**
     * Displays the main menu and handles user inputs for various options.
     */
    private void menu() {
        int swValue = 0;
        HotelList hotelListObj = new HotelList();

        // Display menu graphics
        Scanner sc = new Scanner(System.in);
        WordFrequency wf = null;
        InvertedIndex invertedIndex = null;
        while (swValue != 4) {
            try {
                // Runtime.getRuntime().exec("cls");
                // System.out.print("\033[H\033[2J");
                System.out.flush();
                System.out.println("\n\n");
                System.out.println("---------------MENU---------------\n");
            
                System.out.println("Choose your Preference: ");
                System.out.println("        1. Crawl the site");
                System.out.println("        2. Create word freq and inverted index");
                System.out.println("        3. Search anything");
                System.out.println("        4. Enter precise search criteria");
                System.out.println("        5. Exit");
                System.out.println("\nEnter your choice: ");
                String next = sc.nextLine();
                swValue = Integer.parseInt(next);
                // Switch construct
                switch (swValue) {
                    case 1:
                        crawl(sc);
                        Thread.sleep(1200);
                        break;
                    case 2:
                        createIndex(sc);
                        Thread.sleep(1200);
                        break;
                    case 3:
                        search(sc);
                        Thread.sleep(1200);
                        break;
                    case 4:
                        userInput(sc);
                        Thread.sleep(1200);
                        break;
                    case 5:
                        System.out.println("Quiting Application!!!");
                        Thread.sleep(1200);
                        break;
                    default:
                        throw new Exception();
                }
            } catch (Exception e) {
                System.out.println("Selection not recognized. Please retry. (Press the Enter key to proceed)" + e.getMessage());
                sc.nextLine();
            }
        }
    }

    /**
     * Performs a search based on user input keywords.
     *
     * @param sc The Scanner object for user input.
     */
    void search(Scanner sc) {
        SplayTree splayTree = HotelList.getSplayTree();
        SearchedQueryFrequency frequency = splayTree.root;
        if (frequency != null)
            System.out.println("The query that was searched for last is '" + frequency.getQuery() + "' with frequency "
                    + frequency.getCount() + "\n");

        keywords = getSearchKeywords(sc);
        Set<String> documentSet = invertedIndex.search(keywords);
        Map<String, Integer> scoreMap = wf.calculateScores(keywords, documentSet);

        PageRank pagerank = new PageRank(scoreMap);
        pagerank.rankPages();
        List<String> hotelNameList = pagerank.getTopKHotels(10);
        if (!hotelNameList.isEmpty()) {
            System.out.println("\nThe string that was searched for has been located on the following pages:");
            for (String hotel : hotelNameList) {
                System.out.println("  -- " + hotel);
            }
        } else {
            System.out.println("Apologies! The entered text could not be located.");
        }
        System.out.print("\n");
    }

    /**
     * Prints the word frequency and inverted index.
     */
    void printIndex() {
        System.out.println("Word Frequency: ");
        wf.printIndex();
        System.out.println("Inverted Index: ");
        // invertedIndex.printIndex();
    }

    /**
     * Initiates crawling of the site for hotel data.
     *
     * @param sc The Scanner object for user input.
     */
    void crawl(Scanner sc) {
        Date starDate = new Date();
        Date endDate = new Date(starDate.getTime() + 7 * 24 * 60 * 60 * 1000);

        for (Date date = starDate; date.before(endDate); date = new Date(date.getTime() + 24 * 60 * 60 * 1000)) {
            System.setProperty("webdriver.chrome.driver", Config.CHROME_DRIVER_PATH);
            WebDriver driver = new ChromeDriver();
            WebCrawler webCrawler = new WebCrawler(date, date, 2, driver);
            webCrawler.runCrawler();

            driver.close();
        }
        HotelList.saveValues();
        
        System.out.println("List of Hotel(s): ");
        for (String hotel : HotelList.getHotelList().keySet()) {
            System.out.println(hotel);
        }
    }

    /**
     * Creates word frequency and inverted index based on crawled data.
     *
     * @param sc The Scanner object for user input.
     */
    void createIndex(Scanner sc) {
        System.out.println("Generating word Frequency map..");
        wf = new WordFrequency(HotelList.getHotelList());
        wf.setWordFrequencies();
        System.out.println("The creation of the word frequency map was completed successfully..\n");

        // wf.printIndex();
        System.out.println("Generating Inverted index...");
        invertedIndex = new InvertedIndex(HotelList.getHotelList());
        invertedIndex.createIndex();
        System.out.println("The creation of the inverted index was completed successfully..");
        // invertedIndex.printIndex();
    }

    /**
     * Takes user input for hotel details.
     *
     * @param sc The Scanner object for user input.
     */
    private void userInput(Scanner sc) {
        System.out.println("Requesting information regarding the hotel..");
        try {
            new UserInput(sc);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    /**
     * Retrieves search keywords from user input.
     *
     * @param sc The Scanner object for user input.
     * @return An array of search keywords.
     */
    String[] getSearchKeywords(Scanner sc) {
        System.out.print("Please input the search query: ");
        String query = sc.nextLine();

        saveInSplayTree(query);

        String[] words = query.toLowerCase().split("\\s+");
        System.out.println(Arrays.toString(words));
        return words;
    }

    /**
     * Saves searched queries in a SplayTree data structure.
     *
     * @param query The search query to be saved.
     */
    private void saveInSplayTree(String query) {

        SplayTree splayTree = HotelList.getSplayTree();
        SearchedQueryFrequency queryFrequency = new SearchedQueryFrequency(query, 1);
        SearchedQueryFrequency searchedQueryFrequency = splayTree.search(queryFrequency);
        if (searchedQueryFrequency != null)
            searchedQueryFrequency.setCount(searchedQueryFrequency.getCount() + 1);
        else {
            splayTree.insert(queryFrequency);
        }
    }

    /**
     * Main method to initiate the application.
     *
     * @param args The arguments passed to the program.
     */
    public static void main(String[] args) {
        App app = new App();
        app.menu();
    }
}
