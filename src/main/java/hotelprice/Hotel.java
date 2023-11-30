package hotelprice;

import java.io.Serializable;

/**
 * Represents a hotel with various attributes such as price, location, score,
 * etc.
 */
public class Hotel implements Serializable {

    private String price;
    private String location;
    private String score;
    private String url;
    private String name;
    private String pageData;
    private String[] words;
    private String website;

    /**
     * Constructs a Hotel object with the provided attributes.
     *
     * @param price    The price of the hotel.
     * @param location The location of the hotel.
     * @param score    The score/rating of the hotel.
     * @param name     The name of the hotel.
     * @param url      The URL of the hotel.
     * @param words    An array of words associated with the hotel.
     */
    public Hotel(String price, String location, String score, String name, String url, String[] words, String website) {
        this.price = price;
        this.location = location;
        this.score = score;
        this.name = name;
        this.url = url;
        // this.pageData = pageData;
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
     * @return the website name of the hotel
     */
    public String getWebsite() {
        return website;
    }
}
