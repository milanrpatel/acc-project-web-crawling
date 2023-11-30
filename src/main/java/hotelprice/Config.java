package hotelprice;

/**
 * Configuration class that holds constant values and URLs used in the hotel
 * price application.
 */
public class Config {
	/**
	 * The file path for the ChromeDriver executable.
	 */
	public static final String CHROME_DRIVER_PATH = "./drivers/chromedriver.exe";

	/**
	 * The domain name for Kayak website.
	 */
	public static final String KAYAK_DOMAIN_NAME = "https://www.ca.kayak.com";
	public static final String KAYAK_BASE_URL = KAYAK_DOMAIN_NAME
			+ "/hotels/Canada-u43/<SD>/<ED>/<NOA>adults?sort=rank_a";

	/**
	 * The domain name for Momondo website.
	 */
	public static final String MOMONDO_DOMAIN_NAME = "https://www.momondo.ca";
	public static final String MOMONDO_BASE_URL = MOMONDO_DOMAIN_NAME
			+ "/hotel-search/Canada-u43/<SD>/<ED>/<NOA>adults?sort=rank_a";

	/**
	 * The domain name for Trip.com website.
	 */
	public static final String TRIP_DOMAIN_NAME = "https://ca.trip.com";
	public static final String TRIP_BASE_URL = TRIP_DOMAIN_NAME
			+ "/hotels/list?countryId=47&checkin=<SD>&checkout=<ED>&barCurr=CAD&searchType=C&searchWord=Canada&adult=<NOA>";
}
