Sites

- https://www.ca.kayak.com/hotels/Canada-u43/2023-11-24/2023-11-25/2adults?sort=rank_a
- https://www.momondo.ca/hotel-search/Canada-u43/2023-11-25/2023-11-26/2adults
- https://ca.trip.com/hotels/list?countryId=47&checkin=2023/11/23&checkout=2023/11/24&barCurr=CAD&searchType=C&searchWord=Canada&adult=2

1. Start from the App.java

   if Option 1 crawl

   1. App.java crawl()
      - Setup the driver config
   2. WebCrawler class's runCrawler()
      1. Building the URL -> buildURL()
      2. Store the first page as start.html -> HTMLUtils.fetchHtml()
      3. Extract hotel list from that page using JSoup
      4. HotelList.addDocumentToList()
   3. Now we have the list of hotels after (2.4) addDocumentToList()
      1. This is the method we need to Modify based on the website structure
      2.
