package hotelprice;

public class Config {
	public static final String CHROME_DRIVER_PATH = "./drivers/chromedriver.exe";

	// https://www.ca.kayak.com/hotels/Canada-u43/2023-11-24/2023-11-25/2adults?sort=rank_a
	public static final String KAYAK_DOMAIN_NAME = "https://www.ca.kayak.com";
	public static final String KAYAK_BASE_URL = KAYAK_DOMAIN_NAME
			+ "/hotels/Canada-u43/<SD>/<ED>/<NOA>adults?sort=rank_a";

	// https://www.momondo.ca/hotel-search/Canada-u43/2023-11-25/2023-11-26/2adults
	public static final String MOMONDO_DOMAIN_NAME = "https://www.momondo.ca";
	public static final String MOMONDO_BASE_URL = MOMONDO_DOMAIN_NAME
			+ "/hotel-search/Canada-u43/<SD>/<ED>/<NOA>adults?sort=rank_a";

	// https://ca.trip.com/hotels/list?countryId=47&checkin=2023/11/23&checkout=2023/11/24&barCurr=CAD&searchType=C&searchWord=Canada&adult=2
	// Date = yyyy/mm/dd
	public static final String TRIP_DOMAIN_NAME = "https://ca.trip.com";
	public static final String TRIP_BASE_URL = TRIP_DOMAIN_NAME
			+ "/hotels/list?countryId=47&checkin=<SD>&checkout=<ED>&barCurr=CAD&searchType=C&searchWord=Canada&adult=<NOA>";
}
