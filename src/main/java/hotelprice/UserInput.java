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
        System.out.println("Kindly provide the start date using the format of dd/MM/yyyy: ");
        startDate = sc.nextLine();
        while (!validateDate(startDate)) {
            try {
                startDate1 = sdf.parse(startDate);
                System.out.println(
                        "\nThe date format provided is not valid. Kindly input the start date using the format of dd/MM/yyyy: ");
                startDate = sc.nextLine();
            } catch (Exception e) {
                System.out.println("The provided date is incorrect. Please input a valid date.");
                startDate = sc.nextLine();
            }
        }
        // Asking user to enter End Date for Booking
        System.out.println("\nKindly share the end date in the format of dd/MM/yyyy: ");
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
            if (checkLocation(location))
                break;
            location = suggestTheNearestMatchingWordString(sc, location, HotelList.getLocationMap().keySet());
            sc.nextLine();
        } while (!checkLocation(location));

        if (validateDate(startDate) && validateDate(endDate) && checkLocation(location)) {
            System.out.println(
                    "\nStarting Date: " + startDate + "\nEnding Date: " + endDate + "\nLocation: " + location + "\n");
            // find accurate results
            Set<String> locationHotelsSet = HotelList.getLocationMap().get(location);

            startDate1 = sdf.parse(startDate);
            endDate1 = sdf.parse(endDate);
            Calendar startC = Calendar.getInstance();
            startC.setTime(startDate1);
            Calendar endC = Calendar.getInstance();
            endC.setTime(endDate1);

            Set<String> startDateHotels = HotelList.getStartDateMap().get(startDate);
            Set<String> commonStartDateHotelsSet = startDateHotels;
            Map<String, Hotel> listOfHotels = HotelList.getHotelList();

            for (Date date = startC.getTime(); startC.before(endC); startC.add(Calendar.DATE, 1), date = startC.getTime()) {
                startDateHotels = HotelList.getStartDateMap().get(Common.convertDateToSimpleFormat(date));

                if (startDateHotels != null && commonStartDateHotelsSet != null && !startDateHotels.isEmpty()
                        && !commonStartDateHotelsSet.isEmpty()) {
                    commonStartDateHotelsSet = getCommonHotelsSet(startDateHotels, commonStartDateHotelsSet);
                }
            }

            Set<String> commonHotelsSet = new HashSet<>();
            if (locationHotelsSet != null && commonStartDateHotelsSet != null && !locationHotelsSet.isEmpty()
                    && !commonStartDateHotelsSet.isEmpty()) {
                commonHotelsSet = getCommonHotelsSet(locationHotelsSet, commonStartDateHotelsSet);
            }

            if (!commonHotelsSet.isEmpty()) {
                // Printing Hotel Names based on Ratings
                System.out.println("\nHotel(s) list according to Ratings:");
                List<String> ratingBasedHotelsList = sortHotelsAccordingToMeasures(commonHotelsSet, HotelList.getHotelList(), 1);
                for (String singleRatingHotel : ratingBasedHotelsList) {
                    System.out.println("  <--> Name: " + listOfHotels.get(singleRatingHotel).getName());
                    System.out.println("       Price: " + listOfHotels.get(singleRatingHotel).getPrice());
                    System.out.println("       Location: " + listOfHotels.get(singleRatingHotel).getLocation());
                    System.out.println("       Ratings: " + listOfHotels.get(singleRatingHotel).getScore());
                    System.out.println("       Website: " + listOfHotels.get(singleRatingHotel).getWebsite());
                    System.out.print("\n");
                }
                // Printing Hotel Names based on Price
                System.out.println("\nHotel(s) list according to Price:");
                List<String> priceBasedHotelsList = sortHotelsAccordingToMeasures(commonHotelsSet, HotelList.getHotelList(), 2);
                for (String singlePriceHotel : priceBasedHotelsList) {
                    System.out.println("    -- Name: " + listOfHotels.get(singlePriceHotel).getName());
                    System.out.println("       Price: " + listOfHotels.get(singlePriceHotel).getPrice());
                    System.out.println("       Location: " + listOfHotels.get(singlePriceHotel).getLocation());
                    System.out.println("       Ratings: " + listOfHotels.get(singlePriceHotel).getScore());
                    System.out.println("       Website: " + listOfHotels.get(singlePriceHotel).getWebsite());
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
     * @param startDate2 Starting date string.
     * @param endDate2   Ending date string.
     * @return Boolean true of false
     */
    private static boolean startEndDate(String startDate2, String endDate2) {
        try {
            SimpleDateFormat sdFormate = new SimpleDateFormat("dd/MM/yyyy");
            Date date1 = sdFormate.parse(startDate2);
            Date date2 = sdFormate.parse(endDate2);
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
     * @param commonHotelsSet Set of hotels to be sorted.
     * @param hotelListMap    Map of hotels with their details.
     * @param criteria     The criteria for sorting (1 for ratings, 2 for price).
     * @return A sorted list of hotel names.
     */
    private List<String> sortHotelsAccordingToMeasures(Set<String> commonHotelsSet, Map<String, Hotel> hotelListMap,
            int criteria) {

        PriorityQueue<Entry> firstPQ = new PriorityQueue<>(Collections.reverseOrder());
        PriorityQueue<Entry> secondPQ = new PriorityQueue<>();
        PriorityQueue<Entry> pqFS = (criteria == 1) ? firstPQ : secondPQ;

        for (String commonHotelName : commonHotelsSet) {
            Hotel hotel = hotelListMap.get(commonHotelName);
            String hotelRatingStr = hotel.getScore();

            String hotelPriceStr = hotel.getPrice().substring(3).replace(",", "");

            float ratingOfHotel = Float.parseFloat(hotelRatingStr);
            float priceOfHotel = Float.parseFloat(hotelPriceStr);

            if (criteria == 1) {
                firstPQ.offer(new Entry(commonHotelName, ratingOfHotel));
            } else if (criteria == 2) {
                secondPQ.offer(new Entry(commonHotelName, priceOfHotel));
            }
        }

        List<String> result = new ArrayList<>();
        while (!pqFS.isEmpty()) {
            result.add(pqFS.poll().key);
        }
        return result;
    }

    /**
     * Provides word completion suggestions for a given location.
     *
     * @param sc          The Scanner object for user input.
     * @param locationStr    The location for which suggestions are needed.
     * @param locationSet The set of available locations.
     * @return The suggested location.
     */
    private String suggestCompletionOfWord(Scanner sc, String locationStr, Set<String> locationSet) {
        System.out.println("Completing Word:");
        Trie locationTrie = HotelList.getLocationTrie();
        List<String> completedWordsList = locationTrie.completeWord(locationStr);
        int count = 1;

        for (int i = 0; i < ((completedWordsList.size() < 5) ? completedWordsList.size() : 5); i++) {
            System.out.println("Are you intending to say------" + completedWordsList.get(i) + "? " + "Type " + count);
            count++;
        }
        int userInput = sc.nextInt();
        switch (userInput) {
            case 1:
                return completedWordsList.get(0);
            case 2:
                return completedWordsList.get(1);
            case 3:
                return completedWordsList.get(2);
            case 4:
                return completedWordsList.get(3);
            case 5:
                return completedWordsList.get(4);
            case 9:
                return "";
        }

        return locationStr;
    }

    /**
     * Suggests the nearest matching word for a given location.
     *
     * @param sc          The Scanner object for user input.
     * @param location    The location for which a suggestion is needed.
     * @param locationSet The set of available locations.
     * @return The suggested location.
     */
    private String suggestTheNearestMatchingWordString(Scanner sc, String location, Set<String> locationSet) {
        // edit distance
        int min = Integer.MAX_VALUE;
        String minWord = "";

        int secondMin = Integer.MAX_VALUE;
        String secondMinWord = "";

        for (String l : locationSet) {
            int distance = EditDistance.editDistance(l, location);
            if (distance < min) {
                secondMin = min;
                secondMinWord = minWord;

                min = distance;
                minWord = l;
            } else if (distance < secondMin) {
                secondMin = distance;
                secondMinWord = l;
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
                return suggestCompletionOfWord(sc, location, HotelList.getLocationMap().keySet());
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
        Calendar todayCalendar = setDateInCalendar(todayDate);

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
     * @param dateObj The date to set the calendar with.
     * @return The Calendar object set to the provided date.
     * @throws ParseException If there is an error parsing date.
     */
    public static Calendar setDateInCalendar(Date dateObj) throws ParseException {

        Calendar calObj = Calendar.getInstance();
        calObj.setTime(dateObj);
        calObj.set(Calendar.HOUR_OF_DAY, 0);
        calObj.set(Calendar.MINUTE, 0);
        calObj.set(Calendar.SECOND, 0);
        calObj.set(Calendar.MILLISECOND, 0);
        return calObj;
    }

    /**
     * Finds the common hotels between two sets of hotels.
     *
     * @param locationHotels  Set of hotels based on location.
     * @param startDateHotels Set of hotels based on start date.
     * @return Set of common hotels.
     */
    public static Set<String> getCommonHotelsSet(Set<String> locationHotels, Set<String> startDateHotels) {
        Set<String> commonHotelsSet = new HashSet<>();
        for (String singleHotel : locationHotels) {
            if (startDateHotels.contains(singleHotel)) {
                commonHotelsSet.add(singleHotel);
            }
        }
        return commonHotelsSet;
    }

    /**
     * Validates if the given location exists in the list of available locations.
     *
     * @param locationStr The location to validate.
     * @return True if the location exists, false otherwise.
     */
    public static boolean checkLocation(String locationStr) {
        Set<String> locationsSet = HotelList.getLocationMap().keySet();
        if (locationsSet.contains(locationStr)) {
            return true;
        } else {

            return false;

        }

    }
}
