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
                System.out.println("|******************************************************************|");
                System.out.println("|                            MAIN MENU                             |");
                System.out.println("|******************************************************************|");
                System.out.println("| Options:                                                         |");
                System.out.println("|        1. Crawl the website                                      |");
                System.out.println("|        2. Generate word frequency and inverted index             |");
                System.out.println("|        3. Perform a search                                       |");
                System.out.println("|        4. Specify precise search criteria                        |");
                System.out.println("|        5. Quit                                                   |");
                System.out.println("|******************************************************************|");
                System.out.println("\nSelect Option from above: ");
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
                        System.out.println("Exiting...");
                        Thread.sleep(1200);
                        break;
                    default:
                        throw new Exception();
                }
            } catch (Exception e) {
                System.out.println("Invalid selection. Try again (Press Enter key to continue)" + e.getMessage());
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
            System.out.println("Most recently searched query is '" + frequency.getQuery() + "' having frequency "
                    + frequency.getCount() + "\n");

        keywords = getSearchKeywords(sc);
        Set<String> documentSet = invertedIndex.search(keywords);
        Map<String, Integer> scoreMap = wf.calculateScores(keywords, documentSet);

        PageRank pagerank = new PageRank(scoreMap);
        pagerank.rankPages();
        List<String> hotelNameList = pagerank.getTopKHotels(10);
        if (!hotelNameList.isEmpty()) {
            System.out.println("\nSearched string was found in following pages:");
            for (String hotel : hotelNameList) {
                System.out.println("  -- " + hotel);
            }
        } else {
            System.out.println("Sorry! text you entered was not found.");
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
        
        System.out.println("Hotel List: ");
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
        System.out.println("Creating word Frequency map...");
        wf = new WordFrequency(HotelList.getHotelList());
        wf.setWordFrequencies();
        System.out.println("Word Frequency map was created successfuly...\n");

        // wf.printIndex();
        System.out.println("Creating Inverted index...");
        invertedIndex = new InvertedIndex(HotelList.getHotelList());
        invertedIndex.createIndex();
        System.out.println("Inverted index was done successfuly...");
        // invertedIndex.printIndex();
    }

    /**
     * Takes user input for hotel details.
     *
     * @param sc The Scanner object for user input.
     */
    private void userInput(Scanner sc) {
        System.out.println("Asking user for hotel details...");
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
        System.out.print("Enter the Search query: ");
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
