package hotelprice;

import java.io.Serializable;

/**
 * Represents a hotel with various attributes such as price, location, score,
 * etc.
 */
public class Hotel implements Serializable {

    // Attributes of the Hotel
    private String price; // Price of hotel
    private String location; // Location of hotel
    private String score; // Score/rating of hotel
    private String url; // URL of hotel
    private String name; // Name of hotel
    private String pageData; // Data associated with hotel's webpage
    private String[] words; // An array of words associated with hotel
    private String website; // Website associated with hotel

    /**
     * Constructs a Hotel object with the provided attributes.
     *
     * @param price    price of hotel.
     * @param location location of hotel.
     * @param score    score/rating of hotel.
     * @param name     name of hotel.
     * @param url      URL of hotel.
     * @param words    An array of words associated with hotel.
     * @param website  website associated with hotel.
     */
    public Hotel(String price, String location, String score, String name, String url, String[] words, String website) {
        this.price = price;
        this.location = location;
        this.score = score;
        this.name = name;
        this.url = url;
        this.words = words;
        this.website = website;
    }

    /**
     * Returns a string representation of hotel (in this case, hotel name).
     *
     * @return The name of hotel.
     */
    @Override
    public String toString() {
        return name;
    }

    // Getter methods to retrieve specific attributes of hotel

    /**
     * Gets the price of hotel.
     *
     * @return The price of hotel.
     */
    public String getPrice() {
        return price;
    }

    /**
     * Gets the location of hotel.
     *
     * @return The location of hotel.
     */
    public String getLocation() {
        return location;
    }

    /**
     * Gets the score/rating of hotel.
     *
     * @return The score/rating of hotel.
     */
    public String getScore() {
        return score;
    }

    /**
     * Gets the URL of hotel.
     *
     * @return The URL of hotel.
     */
    public String getUrl() {
        return url;
    }

    /**
     * Gets the name of hotel.
     *
     * @return The name of hotel.
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the page data associated with hotel.
     *
     * @return The page data of hotel.
     */
    public String getPageData() {
        return pageData;
    }

    /**
     * Gets the array of words associated with hotel.
     *
     * @return An array of words associated with hotel.
     */
    public String[] getWords() {
        return words;
    }

    /**
     * Gets the website associated with hotel.
     *
     * @return The website name of hotel.
     */
    public String getWebsite() {
        return website;
    }
}
