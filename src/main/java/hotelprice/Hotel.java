package hotelprice;

import java.io.Serializable;

/**
 * Represents a hotel with various attributes such as price, location, score,
 * etc.
 */
public class Hotel implements Serializable {

    // Attributes of the Hotel
    private String price; // The price of the hotel
    private String location; // The location of the hotel
    private String score; // The score/rating of the hotel
    private String url; // The URL of the hotel
    private String name; // The name of the hotel
    private String pageData; // Data associated with the hotel's webpage
    private String[] words; // An array of words associated with the hotel
    private String website; // The website associated with the hotel

    /**
     * Constructs a Hotel object with the provided attributes.
     *
     * @param price    The price of the hotel.
     * @param location The location of the hotel.
     * @param score    The score/rating of the hotel.
     * @param name     The name of the hotel.
     * @param url      The URL of the hotel.
     * @param words    An array of words associated with the hotel.
     * @param website  The website associated with the hotel.
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
     * Returns a string representation of the hotel (in this case, the hotel name).
     *
     * @return The name of the hotel.
     */
    @Override
    public String toString() {
        return name;
    }

    // Getter methods to retrieve specific attributes of the hotel

    /**
     * Gets the price of the hotel.
     *
     * @return The price of the hotel.
     */
    public String getPrice() {
        return price;
    }

    /**
     * Gets the location of the hotel.
     *
     * @return The location of the hotel.
     */
    public String getLocation() {
        return location;
    }

    /**
     * Gets the score/rating of the hotel.
     *
     * @return The score/rating of the hotel.
     */
    public String getScore() {
        return score;
    }

    /**
     * Gets the URL of the hotel.
     *
     * @return The URL of the hotel.
     */
    public String getUrl() {
        return url;
    }

    /**
     * Gets the name of the hotel.
     *
     * @return The name of the hotel.
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the page data associated with the hotel.
     *
     * @return The page data of the hotel.
     */
    public String getPageData() {
        return pageData;
    }

    /**
     * Gets the array of words associated with the hotel.
     *
     * @return An array of words associated with the hotel.
     */
    public String[] getWords() {
        return words;
    }

    /**
     * Gets the website associated with the hotel.
     *
     * @return The website name of the hotel.
     */
    public String getWebsite() {
        return website;
    }
}
