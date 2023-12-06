package hotelprice;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.*;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.WebDriver;

/**
 * Represents a list of hotels with various utilities for crawling, processing,
 * and managing hotel data.
 */
public class HotelList implements Serializable {
	private static Map<String, Hotel> hotelList = new HashMap<>();
	private static Map<String, HashSet<String>> locationMap = new HashMap<>();
	private static Map<String, HashSet<String>> startDateMap = new HashMap<>();
	private static Trie locationTrie = new Trie();
	private static Trie allWordsTrie = new Trie();
	private static String fileName = "./serializedValues";
	private static SplayTree splayTree = new SplayTree();

	/**
	 * Default constructor that reads serialized values upon object creation.
	 */
	public HotelList() {
		readValues();
	}

	/**
	 * Builds a Trip.com hotel URL based on the provided parameters.
	 *
	 * @param domainName     The domain name of the website.
	 * @param hotelId        The ID of the hotel.
	 * @param checkIn        The check-in date.
	 * @param checkOut       The check-out date.
	 * @param numberOfAdults The number of adults.
	 * @return The constructed Trip.com hotel URL.
	 */
	public static String buildTripHotelURL(String domainName, String hotelId, Date checkIn, Date checkOut,
			int numberOfAdults) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY/MM/dd");
		Calendar c = Calendar.getInstance();
		c.setTime(checkOut);
		c.add(Calendar.DATE, 1);
		return domainName + "/hotels/detail/?hotelId=" + hotelId +
				"&checkIn=" + dateFormat.format(checkIn) +
				"&checkOut=" + dateFormat.format(c.getTime()) +
				"&adult=" + numberOfAdults;
	}

	/**
	 * Adds hotel documents from the Kayak website to the hotel list.
	 *
	 * @param doc       The HTML document containing hotel information.
	 * @param driver    The WebDriver for interacting with the web page.
	 * @param startDate The start date of the hotel stay.
	 * @param endDate   The end date of the hotel stay.
	 */
	public static void addKayakHotelDocumentToList(Document doc, WebDriver driver, Date startDate, Date endDate) {
		Elements elements = doc.getElementsByClass("kzGk");
		// System.out.println(doc.html());
		int count = 0;
		for (Element element : elements) {
			// if (count == 1)
			// break;
			// count++;
			int index = hotelList.size();
			String price = element.getElementsByClass("zV27-price").first() != null
					? element.getElementsByClass("zV27-price").first().text()
					: "N/A";

			System.out.println("Price: " + price);
			String location = element.getElementsByClass("FLpo-location-name").first() != null
					? element.getElementsByClass("FLpo-location-name").first().text().toLowerCase()
					: "N/A";
			String score = element.getElementsByClass("FLpo-score").first() != null
					? element.getElementsByClass("FLpo-score").first().text()
					: "N/A";
			String name = element.getElementsByClass("FLpo-big-name").first() != null
					? element.getElementsByClass("FLpo-big-name").first().text().toLowerCase()
					: "N/A";
			String url = Config.KAYAK_DOMAIN_NAME + element.getElementsByClass("FLpo-big-name").first().attr("href");

			String text = fetchTextFromUrl(driver, url, name, "kayak").toLowerCase();

			String[] words = text.split(" ");
			words = Arrays.asList(words).stream().map(String::toLowerCase).toArray(String[]::new);
			allWordsTrie.insertWords(Arrays.asList(words));
			List<String> locationList = new ArrayList<>();
			locationList.add(location.toLowerCase());
			locationTrie.insertWords(locationList);

			System.out.println("Crawled: " + name);

			// create maps based on location, startDate.
			// Map has a key as location/startDate and value is a index of hotels.
			addToMap(locationMap, location, name);

			addToMap(startDateMap, Common.convertDateToSimpleFormat(startDate), name);

			hotelList.put(name, new Hotel(price, location, score, name, url, words, "kayak"));
		}
		// saveValues();
	}

	/**
	 * Adds hotel documents from the Momondo website to the hotel list.
	 *
	 * @param doc       The HTML document containing hotel information.
	 * @param driver    The WebDriver for interacting with the web page.
	 * @param startDate The start date of the hotel stay.
	 * @param endDate   The end date of the hotel stay.
	 */
	public static void addMomondoHotelDocumentToList(Document doc, WebDriver driver, Date startDate, Date endDate) {
		Elements elements = doc.getElementsByClass("kzGk");
		// System.out.println(doc.html());
		int count = 0;
		for (Element element : elements) {
			// if (count == 1)
			// break;
			// count++;
			int index = hotelList.size();
			String price = element.getElementsByClass("zV27-price").first() != null
					? element.getElementsByClass("zV27-price").first().text()
					: "N/A";
			System.out.println("Price: " + price);
			String location = element.getElementsByClass("FLpo-location-name").first() != null
					? element.getElementsByClass("FLpo-location-name").first().text().toLowerCase()
					: "N/A";
			String score = element.getElementsByClass("FLpo-score").first() != null
					? element.getElementsByClass("FLpo-score").first().text()
					: "N/A";
			String name = element.getElementsByClass("FLpo-big-name").first() != null
					? element.getElementsByClass("FLpo-big-name").first().text().toLowerCase()
					: "N/A";
			String url = Config.MOMONDO_DOMAIN_NAME + element.getElementsByClass("FLpo-big-name").first().attr("href");

			String text = fetchTextFromUrl(driver, url, name, "momondo").toLowerCase();

			String[] words = text.split(" ");
			words = Arrays.asList(words).stream().map(String::toLowerCase).toArray(String[]::new);
			allWordsTrie.insertWords(Arrays.asList(words));
			List<String> locationList = new ArrayList<>();
			locationList.add(location.toLowerCase());
			locationTrie.insertWords(locationList);

			System.out.println("Crawled: " + name);

			// create maps based on location, startDate.
			// Map has a key as location/startDate and value is a index of hotels.
			addToMap(locationMap, location, name);

			addToMap(startDateMap, Common.convertDateToSimpleFormat(startDate), name);

			hotelList.put(name, new Hotel(price, location, score, name, url, words, "momondo"));
		}
		// saveValues();
	}

	/**
	 * Adds hotel documents from the Trip.com website to the hotel list.
	 *
	 * @param doc       The HTML document containing hotel information.
	 * @param driver    The WebDriver for interacting with the web page.
	 * @param startDate The start date of the hotel stay.
	 * @param endDate   The end date of the hotel stay.
	 */
	public static void addTripHotelDocumentToList(Document doc, WebDriver driver, Date startDate, Date endDate) {
		Elements elements = doc.getElementsByClass("compressmeta-hotel-wrap-v8");
		// System.out.println(doc.html());
		int count = 0;
		for (Element element : elements) {
			// if (count == 1)
			// break;
			// count++;
			int index = hotelList.size();
			String price = element.getElementsByClass("not-break").first() != null
					? element.getElementsByClass("not-break").first().text()
					: "N/A";
			System.out.println("Price: " + price);

			String location = element.getElementsByClass("list-card-transport-v8").first() != null
					? element.getElementsByClass("list-card-transport-v8").first().getElementsByTag("span").get(1)
							.text()
					: "N/A";
			System.out.println("Location: " + location);

			String scoreStr = element.getElementsByClass("score").first() != null
					? element.getElementsByClass("score").first().getElementsByClass("real").first().text()
					: "N/A";
			String score = (Float.parseFloat(scoreStr) * 2) + "";
			System.out.println("Score: " + score);

			String name = element.getElementsByClass("list-card-title").first() != null
					? element.getElementsByClass("list-card-title").first().getElementsByClass("name").first().text()
							.toLowerCase()
					: "N/A";
			System.out.println("Name: " + name);
			String hotelIId = element.attr("id");
			System.out.println("Hotel ID: " + hotelIId);

			String url = buildTripHotelURL(Config.TRIP_DOMAIN_NAME, hotelIId, startDate, endDate, 2);

			System.out.println(url);

			String text = fetchTextFromUrl(driver, url, name, "trip").toLowerCase();

			String[] words = text.split(" ");
			words = Arrays.asList(words).stream().map(String::toLowerCase).toArray(String[]::new);
			allWordsTrie.insertWords(Arrays.asList(words));
			List<String> locationList = new ArrayList<>();
			locationList.add(location.toLowerCase());
			locationTrie.insertWords(locationList);

			System.out.println("Crawled: " + name);

			// create maps based on location, startDate.
			// Map has a key as location/startDate and value is a index of hotels.
			addToMap(locationMap, location, name);

			addToMap(startDateMap, Common.convertDateToSimpleFormat(startDate), name);

			hotelList.put(name, new Hotel(price, location, score, name, url, words, "trip"));
		}
		// saveValues();
	}

	/**
	 * Adds hotel names to a set in the provided map based on the specified key.
	 *
	 * @param map       The map to which hotel names are added.
	 * @param key       The key in the map.
	 * @param hotelName The name of the hotel to be added.
	 */
	public static void addToMap(Map<String, HashSet<String>> map, String key, String hotelName) {
		if (map.containsKey(key)) {
			map.get(key).add(hotelName);
		} else {
			HashSet<String> set = new HashSet<>();
			set.add(hotelName);
			map.put(key, set);
		}
	}

	/**
	 * Fetches text content from a given URL using a WebDriver.
	 *
	 * @param driver  The WebDriver for interacting with the web page.
	 * @param url     The URL from which text is to be fetched.
	 * @param name    The name associated with the hotel.
	 * @param website The website name.
	 * @return The text content fetched from the URL.
	 */
	public static String fetchTextFromUrl(WebDriver driver, String url, String name, String website) {
		String html = HTMLUtils.fetchHtml(driver, url, website + "_" + name, Config.DEFAULT_TIMEOUT);
		Document doc = HTMLUtils.parse(html);
		return doc.body().text();
	}

	public static Map<String, HashSet<String>> getLocationMap() {
		return locationMap;
	}

	public static Map<String, Hotel> getHotelList() {
		return hotelList;
	}

	public static Map<String, HashSet<String>> getStartDateMap() {
		return startDateMap;
	}

	public static SplayTree getSplayTree() {
		return splayTree;
	}

	public static Trie getAllWordsTrie() {
		return allWordsTrie;
	}

	/**
	 * Saves serialized values to a file.
	 */
	public static void saveValues() {
		try {
			FileOutputStream fileOut = new FileOutputStream(fileName);
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(hotelList);
			out.writeObject(locationMap);
			out.writeObject(startDateMap);
			out.writeObject(locationTrie);
			out.writeObject(allWordsTrie);
			out.close();
			fileOut.close();
			System.out.println("Serialized data is saved in " + fileName);
		} catch (IOException i) {
			i.printStackTrace();
		}
	}

	/**
	 * Reads serialized values from a file.
	 */
	public static void readValues() {
		try {
			FileInputStream fileIn = new FileInputStream(fileName);
			ObjectInputStream in = new ObjectInputStream(fileIn);
			hotelList = (Map<String, Hotel>) in.readObject();
			System.out.println("Hotel Size: " + hotelList.size());
			locationMap = (Map<String, HashSet<String>>) in.readObject();
			System.out.println("Location Size: " + locationMap.size());
			startDateMap = (Map<String, HashSet<String>>) in.readObject();
			System.out.println("Start Date Size: " + startDateMap.size());
			locationTrie = (Trie) in.readObject();
			allWordsTrie = (Trie) in.readObject();
			in.close();
			fileIn.close();
		} catch (IOException i) {
			// i.printStackTrace();
			System.out.println("Here in file");
			return;
		} catch (ClassNotFoundException c) {
			System.out.println("Employee class not found");
			c.printStackTrace();
			return;
		}
	}

	/**
	 * Gets the location trie.
	 *
	 * @return The location trie.
	 */
	public static Trie getLocationTrie() {
		return locationTrie;
	}
}
