package hotelprice;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.Set;

/**
 * Class representing user input for hotel booking.
 */
public class UserInput {
    private static String startDate;
    private static String endDate;
    private static String location;

    /**
     * Constructs a UserInput object with the provided Scanner for input.
     *
     * @param sc The Scanner object for user input.
     * @throws ParseException If there is an error parsing date.
     */
    public UserInput(Scanner sc) throws ParseException {
        Date startDate1 = new Date();
        Date endDate1 = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        // Asking user to enter Start Date for Booking
        System.out.println("Please provide the start date using the format dd/MM/yyyy: ");
        startDate = sc.nextLine();
        while (!validateDate(startDate)) {
            try {
                startDate1 = sdf.parse(startDate);
                System.out.println(
                        "\nThe date format provided is not valid. Kindly input the start date using the format dd/MM/yyyy: ");
                startDate = sc.nextLine();
            } catch (Exception e) {
                System.out.println("The provided date is incorrect. Please input a valid date.");
                startDate = sc.nextLine();
            }
        }
        // Asking user to enter End Date for Booking
        System.out.println("\nPlease provide the end date using the format dd/MM/yyyy: ");
        endDate = sc.nextLine();
        while (!validateDate(endDate)) {
            try {
                endDate1 = sdf.parse(endDate);
                System.out.println(
                        "\nThe date format provided is not valid. Kindly input the start date using the format dd/MM/yyyy: ");
                endDate = sc.nextLine();
            } catch (Exception e) {
                System.out.println("The provided date is incorrect. Please input a valid date.");
                startDate = sc.nextLine();
            }
        }

        do {
            System.out.println("\nKindly provide Location: ");
            location = sc.nextLine();
            if (validateLocation(location))
                break;
            location = suggestNearestMatchingWord(sc, location, HotelList.getLocationMap().keySet());
            sc.nextLine();
        } while (!validateLocation(location));

        if (validateDate(startDate) && validateDate(endDate) && validateLocation(location)) {
            System.out.println(
                    "\nStarting Date: " + startDate + "\nEnding Date: " + endDate + "\nLocation: " + location + "\n");
            // find accurate results
            Set<String> locationHotels = HotelList.getLocationMap().get(location);

            startDate1 = sdf.parse(startDate);
            endDate1 = sdf.parse(endDate);
            Calendar start = Calendar.getInstance();
            start.setTime(startDate1);
            Calendar end = Calendar.getInstance();
            end.setTime(endDate1);

            Set<String> startDateHotels = HotelList.getStartDateMap().get(startDate);
            Set<String> commonStartDateHotels = startDateHotels;
            Map<String, Hotel> hotelList = HotelList.getHotelList();

            for (Date date = start.getTime(); start.before(end); start.add(Calendar.DATE, 1), date = start.getTime()) {
                startDateHotels = HotelList.getStartDateMap().get(Common.convertDateToSimpleFormat(date));

                if (startDateHotels != null && commonStartDateHotels != null && !startDateHotels.isEmpty()
                        && !commonStartDateHotels.isEmpty()) {
                    commonStartDateHotels = getCommonHotels(startDateHotels, commonStartDateHotels);
                }
            }

            Set<String> commonHotels = new HashSet<>();
            if (locationHotels != null && commonStartDateHotels != null && !locationHotels.isEmpty()
                    && !commonStartDateHotels.isEmpty()) {
                commonHotels = getCommonHotels(locationHotels, commonStartDateHotels);
            }

            if (!commonHotels.isEmpty()) {
                // Printing Hotel Names based on Ratings
                System.out.println("\nHotel(s) list according to Ratings:");
                List<String> ratingHotels = sortHotelsAccordingToCriteria(commonHotels, HotelList.getHotelList(), 1);
                for (String hotel : ratingHotels) {
                    System.out.println("    -- Name: " + hotelList.get(hotel).getName());
                    System.out.println("       Price: " + hotelList.get(hotel).getPrice());
                    System.out.println("       Location: " + hotelList.get(hotel).getLocation());
                    System.out.println("       Ratings: " + hotelList.get(hotel).getScore());
                    System.out.println("       Website: " + hotelList.get(hotel).getWebsite());
                    System.out.print("\n");
                }
                // Printing Hotel Names based on Price
                System.out.println("\nHotel(s) list according to Price:");
                List<String> priceHotels = sortHotelsAccordingToCriteria(commonHotels, HotelList.getHotelList(), 2);
                for (String hotel : priceHotels) {
                    System.out.println("    -- Name: " + hotelList.get(hotel).getName());
                    System.out.println("       Price: " + hotelList.get(hotel).getPrice());
                    System.out.println("       Location: " + hotelList.get(hotel).getLocation());
                    System.out.println("       Ratings: " + hotelList.get(hotel).getScore());
                    System.out.println("       Website: " + hotelList.get(hotel).getWebsite());
                    System.out.print("\n");
                }

                System.out.println("\nWould you like to look for different input? Type 'y' for yes or 'n' for no.");
                String searchAgain = sc.nextLine();
                if (searchAgain.equals("y")) {
                    new UserInput(sc);
                }

            } else {
                System.out.println("No accommodations were discovered within the range of dates provided.");

                System.out.println("\nFind alternative input:");
                new UserInput(sc);
            }
        }
    }

    /**
     * Validates the start and end date based on the given format.
     *
     * @param startDate2 The start date string.
     * @param endDate2   The end date string.
     * @return True if the start date is before or equal to the end date, false
     *         otherwise.
     */
    private static boolean startEndDate(String startDate2, String endDate2) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Date date1 = sdf.parse(startDate2);
            Date date2 = sdf.parse(endDate2);
            if (date1.compareTo(date2) > 0) {
                return false;
            }
            return true;
        } catch (ParseException e) {
            return false; // not proper format of date is given
        }

    }

    /**
     * Class representing an entry with a key-value pair.
     */
    public class Entry implements Comparable<Entry> {
        private String key;
        private Float value;

        /**
         * Constructs an Entry object with the provided key and value.
         *
         * @param key   The key of the entry.
         * @param value The value of the entry.
         */
        public Entry(String key, float value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public int compareTo(Entry other) {
            if (other.value > this.value)
                return -1;
            else
                return 1;
        }
    }

    /**
     * Sorts hotels based on specified criteria such as ratings or price.
     *
     * @param commonHotels Set of hotels to be sorted.
     * @param hotelList    Map of hotels with their details.
     * @param criteria     The criteria for sorting (1 for ratings, 2 for price).
     * @return A sorted list of hotel names.
     */
    private List<String> sortHotelsAccordingToCriteria(Set<String> commonHotels, Map<String, Hotel> hotelList,
            int criteria) {

        PriorityQueue<Entry> pq1 = new PriorityQueue<>(Collections.reverseOrder());
        PriorityQueue<Entry> pq2 = new PriorityQueue<>();
        PriorityQueue<Entry> pq = (criteria == 1) ? pq1 : pq2;

        for (String hotelName : commonHotels) {
            Hotel hotel = hotelList.get(hotelName);
            String hotelRating = hotel.getScore();

            String hotelPrice = hotel.getPrice().substring(3).replace(",", "");

            float rating = Float.parseFloat(hotelRating);
            float price = Float.parseFloat(hotelPrice);

            if (criteria == 1) {
                pq1.offer(new Entry(hotelName, rating));
            } else if (criteria == 2) {
                pq2.offer(new Entry(hotelName, price));
            }
        }

        List<String> result = new ArrayList<>();
        while (!pq.isEmpty()) {
            result.add(pq.poll().key);
        }
        return result;
    }

    /**
     * Provides word completion suggestions for a given location.
     *
     * @param sc          The Scanner object for user input.
     * @param location    The location for which suggestions are needed.
     * @param locationSet The set of available locations.
     * @return The suggested location.
     */
    private String suggestWordCompletion(Scanner sc, String location, Set<String> locationSet) {
        System.out.println("Completing Word:");
        Trie locationTrie = HotelList.getLocationTrie();
        List<String> completedWords = locationTrie.completeWord(location);
        int count = 1;

        for (int i = 0; i < ((completedWords.size() < 5) ? completedWords.size() : 5); i++) {
            System.out.println("Are you intending to say------" + completedWords.get(i) + "? " + "Type " + count);
            count++;
        }
        int userInput = sc.nextInt();
        switch (userInput) {
            case 1:
                return completedWords.get(0);
            case 2:
                return completedWords.get(1);
            case 3:
                return completedWords.get(2);
            case 4:
                return completedWords.get(3);
            case 5:
                return completedWords.get(4);
            case 9:
                return "";
        }

        return location;
    }

    /**
     * Suggests the nearest matching word for a given location.
     *
     * @param sc          The Scanner object for user input.
     * @param location    The location for which a suggestion is needed.
     * @param locationSet The set of available locations.
     * @return The suggested location.
     */
    private String suggestNearestMatchingWord(Scanner sc, String location, Set<String> locationSet) {
        // edit distance
        int min = Integer.MAX_VALUE;
        String minWord = "";

        int secondMin = Integer.MAX_VALUE;
        String secondMinWord = "";

        for (String loc : locationSet) {
            int distance = EditDistance.editDistance(loc, location);
            if (distance < min) {
                secondMin = min;
                secondMinWord = minWord;

                min = distance;
                minWord = loc;
            } else if (distance < secondMin) {
                secondMin = distance;
                secondMinWord = loc;
            }
        }
        int c = 1;
        if (!minWord.isEmpty()) {
            System.out.println("\nAre you intending to say------" + minWord + "? " + "Type " + c);
            c++;
        }
        if (!secondMinWord.isEmpty()) {
            System.out.println("Are you intending to say------" + secondMinWord + "? " + "Type " + c);
        }
        System.out.println("To Complete the Words:----" + "Click 0");
        System.out.println("To again enter the text:------" + "Click 9");

        int userInput = sc.nextInt();

        switch (userInput) {
            case 0:
                return suggestWordCompletion(sc, location, HotelList.getLocationMap().keySet());
            case 1:
                return minWord;
            case 2:
                return secondMinWord;
            case 9:
                return "";
        }
        return "";

    }

    /**
     * Validates the date format based on the given rules.
     *
     * @param date The date string to validate.
     * @return True if the date is in a valid format and after the current date,
     *         false otherwise.
     * @throws ParseException If there is an error parsing date.
     */
    public static boolean validateDate(String date) throws ParseException {
        if (startDate != null && !startDate.isEmpty()) {
            if (!startEndDate(startDate, date)) {
                return false;
            }
        }
        Date todayDate = new Date();
        Calendar todayCalendar = setDateToCalendar(todayDate);

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            sdf.setLenient(false);
            Date date1 = sdf.parse(date);
            if (date1.compareTo(todayCalendar.getTime()) < 0) {
                return false;
            }
            return true;
        } catch (ParseException e) {
            return false; // not proper format of date is given
        }
    }

    /**
     * Sets the time of the calendar object to the start of the day.
     *
     * @param date The date to set the calendar with.
     * @return The Calendar object set to the provided date.
     * @throws ParseException If there is an error parsing date.
     */
    public static Calendar setDateToCalendar(Date date) throws ParseException {

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal;
    }

    /**
     * Finds the common hotels between two sets of hotels.
     *
     * @param locationHotels  Set of hotels based on location.
     * @param startDateHotels Set of hotels based on start date.
     * @return Set of common hotels.
     */
    public static Set<String> getCommonHotels(Set<String> locationHotels, Set<String> startDateHotels) {
        Set<String> commonHotels = new HashSet<>();
        for (String hotel : locationHotels) {
            if (startDateHotels.contains(hotel)) {
                commonHotels.add(hotel);
            }
        }
        return commonHotels;
    }

    /**
     * Validates if the given location exists in the list of available locations.
     *
     * @param location The location to validate.
     * @return True if the location exists, false otherwise.
     */
    public static boolean validateLocation(String location) {
        Set<String> locations = HotelList.getLocationMap().keySet();
        if (locations.contains(location)) {
            return true;
        } else {

            return false;

        }

    }
}
