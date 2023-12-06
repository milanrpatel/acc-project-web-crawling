package hotelprice;

/**
 * Class representing a node in the SplayTree containing query and count
 * information.
 */
class SearchedQueryFrequency implements Comparable<SearchedQueryFrequency> {
    private String query;
    private Integer count;
    public SearchedQueryFrequency left;
    public SearchedQueryFrequency right;
    public SearchedQueryFrequency parent;

    /**
     * Constructor to initialize a SearchedQueryFrequency node with query data and
     * count.
     *
     * @param data  The query string.
     * @param count The count of occurrences for the query.
     */
    public SearchedQueryFrequency(String data, int count) {
        this.query = data;
        this.count = count;
        this.parent = null;
        this.left = null;
        this.right = null;
    }

    /**
     * Overridden method to compare SearchedQueryFrequency nodes based on their
     * query string.
     *
     * @param o The SearchedQueryFrequency to compare against.
     * @return An integer value indicating the comparison result.
     */
    @Override
    public int compareTo(SearchedQueryFrequency o) {
        return this.getQuery().compareTo(o.getQuery());
    }

    /**
     * Retrieves the count of occurrences for the query.
     *
     * @return The count of occurrences for the query.
     */
    public Integer getCount() {
        return count;
    }

    /**
     * Retrieves the query string.
     *
     * @return The query string.
     */
    public String getQuery() {
        return query;
    }

    /**
     * Sets the count of occurrences for the query.
     *
     * @param count The count of occurrences to set.
     */
    public void setCount(Integer count) {
        this.count = count;
    }
}