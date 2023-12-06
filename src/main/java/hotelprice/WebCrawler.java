package hotelprice;

import org.openqa.selenium.WebDriver;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.text.SimpleDateFormat;
import org.jsoup.nodes.Document;

/**
 * Class representing a web crawler for hotel data from different travel
 * websites.
 */
public class WebCrawler {
	// List<Hotel> hotelList = new ArrayList<Hotel>();
	Map<Integer, Map<String, Integer>> wordFrequency = new HashMap<>();
	// Map<String, Map<String, Integer>> wordToDocMap = new HashMap<>();
	InvertedIndex invertedIndex = new InvertedIndex(HotelList.getHotelList());

	private Date startDate;
	private Date endDate;
	private int numberOfAdults;
	private WebDriver driver;

	/**
	 * Constructs a WebCrawler object with the specified parameters.
	 *
	 * @param startDate      The start date for hotel booking.
	 * @param endDate        The end date for hotel booking.
	 * @param numberOfAdults The number of adults for the booking.
	 * @param driver         The WebDriver for web scraping.
	 */
	public WebCrawler(Date startDate, Date endDate, int numberOfAdults, WebDriver driver) {
		this.startDate = startDate;
		this.endDate = endDate;
		this.numberOfAdults = numberOfAdults;
		this.driver = driver;
	}

	/**
	 * Builds the URL for Kayak based on the start date, end date, and number of
	 * adults.
	 *
	 * @param startDate      The start date for hotel booking.
	 * @param endDate        The end date for hotel booking.
	 * @param numberOfAdults The number of adults for the booking.
	 * @return The constructed URL for Kayak.
	 */
	private String buildKayakURL(Date startDate, Date endDate, int numberOfAdults) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY-MM-dd");
		Calendar c = Calendar.getInstance();
		c.setTime(endDate);
		c.add(Calendar.DATE, 1);
		String url = Config.KAYAK_BASE_URL.replace("<SD>", dateFormat.format(startDate));
		url = url.replace("<ED>", dateFormat.format(c.getTime()));
		url = url.replace("<NOA>", Integer.toString(numberOfAdults));
		return url;
	}

	/**
	 * Builds the URL for Momondo based on the start date, end date, and number of
	 * adults.
	 *
	 * @param startDate      The start date for hotel booking.
	 * @param endDate        The end date for hotel booking.
	 * @param numberOfAdults The number of adults for the booking.
	 * @return The constructed URL for Momondo.
	 */
	private String buildMonondoURL(Date startDate, Date endDate, int numberOfAdults) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY-MM-dd");
		Calendar c = Calendar.getInstance();
		c.setTime(endDate);
		c.add(Calendar.DATE, 1);
		String url = Config.MOMONDO_BASE_URL.replace("<SD>", dateFormat.format(startDate));
		url = url.replace("<ED>", dateFormat.format(c.getTime()));
		url = url.replace("<NOA>", Integer.toString(numberOfAdults));
		return url;
	}

	/**
	 * Builds the URL for Trip based on the start date, end date, and number of
	 * adults.
	 *
	 * @param startDate      The start date for hotel booking.
	 * @param endDate        The end date for hotel booking.
	 * @param numberOfAdults The number of adults for the booking.
	 * @return The constructed URL for Trip.
	 */
	private String buildTripURL(Date startDate, Date endDate, int numberOfAdults) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY/MM/dd");
		Calendar c = Calendar.getInstance();
		c.setTime(endDate);
		c.add(Calendar.DATE, 1);
		String url = Config.TRIP_BASE_URL.replace("<SD>", dateFormat.format(startDate));
		url = url.replace("<ED>", dateFormat.format(c.getTime()));
		url = url.replace("<NOA>", Integer.toString(numberOfAdults));
		return url;
	}

	/**
	 * Executes the web crawling process for Kayak, Momondo, and Trip.
	 */
	public void runCrawler() {

		String url, name, html;
		Document domForHotelList;

		// Momondo Crawling
		System.out.println("\nMomondo Web crawling");
		url = this.buildMonondoURL(startDate, endDate, 2);
		System.out.println(url);
		name = "momondo_start";
		html = HTMLUtils.fetchHtml(this.driver, url, name, 25000);
		domForHotelList = HTMLUtils.parse(html);
		HotelList.addMomondoHotelDocumentToList(domForHotelList, this.driver,
				startDate, endDate);

		// Kayak Crawling
		System.out.println("\nKayak Web crawling");
		url = this.buildKayakURL(startDate, endDate, 2);
		System.out.println(url);
		name = "kayak_start";
		html = HTMLUtils.fetchHtml(this.driver, url, name, 25000);
		domForHotelList = HTMLUtils.parse(html);
		HotelList.addKayakHotelDocumentToList(domForHotelList, this.driver,
				startDate, endDate);

		// Trip Crawling
		System.out.println("\nTrip Web crawling");
		url = this.buildTripURL(startDate, endDate, 2);
		System.out.println(url);
		name = "trip_start";
		html = HTMLUtils.fetchHtml(this.driver, url, name, 2000);
		domForHotelList = HTMLUtils.parse(html);
		HotelList.addTripHotelDocumentToList(domForHotelList, this.driver, startDate,
				endDate);

	}

}
