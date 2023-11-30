package hotelprice;

import java.io.FileWriter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.openqa.selenium.WebDriver;

/**
 * Utility class for working with HTML content, parsing documents, and fetching
 * HTML from web pages.
 */
public class HTMLUtils {

	/**
	 * Parses the given HTML string into a JSoup Document.
	 *
	 * @param html The HTML string to parse.
	 * @return A JSoup Document representing the parsed HTML.
	 */
	public static Document parse(String html) {
		return Jsoup.parse(html);
	}

	/**
	 * Fetches the HTML content from a web page using a WebDriver and saves it to a
	 * file.
	 *
	 * @param driver The WebDriver for interacting with the web page.
	 * @param url    The URL of the web page.
	 * @param name   The name associated with the content (used for file naming).
	 * @return The fetched HTML content as a string.
	 */
	public static String fetchHtml(WebDriver driver, String url, String name) {
		driver.get(url);

		try {
			Thread.sleep(30000);
		} catch (Exception e) {
		}
		String s = driver.getPageSource();

		try {
			FileWriter myWriter = new FileWriter("webpages\\" + name + ".html");
			myWriter.write(s);
			myWriter.close();
		} catch (Exception e) {
			System.out.println(e);
		}
		return s;
	}
}
