package hotelprice;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.text.SimpleDateFormat;
import org.jsoup.nodes.Document;

public class WebCrawler {
	// Map to store word frequencies
	// List<Hotel> hotelList = new ArrayList<Hotel>();
	Map<Integer, Map<String, Integer>> wordFrequency = new HashMap<>();
	// Map<String, Map<String, Integer>> wordToDocMap = new HashMap<>();
	// Inverted index to be initialized with HotelList data
	InvertedIndex invertedIndex = new InvertedIndex(HotelList.getHotelList());

	private Date startDate;
	private Date endDate;
	private int numberOfAdults;
	private WebDriver driver;

    // Constructor to initialize WebCrawler object with necessary parameters
	public WebCrawler(Date startDate, Date endDate, int numberOfAdults, WebDriver driver) {
		this.startDate = startDate;
		this.endDate = endDate;
		this.numberOfAdults = numberOfAdults;
		this.driver = driver;
	}
     // Method to build Kayak URL based on input parameters
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
    // Method to build Momondo URL based on input parameters
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
	// Method to build Trip URL based on input parameters
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
	// Main method to execute the web crawling process
	public void runCrawler() {

		String url, name, html;
		Document domForHotelList;

		// // 1. Kayak Crawling
		System.out.println("\nKayak Web crawling");
		// url = this.buildKayakURL(startDate, endDate, 2);
		// name = "kayak_start";
		// html = HTMLUtils.fetchHtml(this.driver, url, name);
		// domForHotelList = HTMLUtils.parse(html);
		// HotelList.addKayakHotelDocumentToList(domForHotelList, this.driver,
		// startDate, endDate);

		// // 2. Momondo Crawling
		System.out.println("\nMomondo Web crawling");
		// url = this.buildMonondoURL(startDate, endDate, 2);
		// name = "momondo_start";
		// html = HTMLUtils.fetchHtml(this.driver, url, name);
		// domForHotelList = HTMLUtils.parse(html);
		// HotelList.addMomondoHotelDocumentToList(domForHotelList, this.driver,
		// startDate, endDate);

		// 3. Trip Crawling
		System.out.println("\nTrip Web crawling");
		url = this.buildTripURL(startDate, endDate, 2);
		name = "trip_start";
		html = HTMLUtils.fetchHtml(this.driver, url, name);
		domForHotelList = HTMLUtils.parse(html);
		HotelList.addTripHotelDocumentToList(domForHotelList, this.driver, startDate,
				endDate);

		HotelList.saveValues();
	}

}
