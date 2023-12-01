package hotelprice;

// public class SearchedQueryFrequency implements
// Comparable<SearchedQueryFrequency> {
// private String query;
// private Integer count;

// public Integer getCount() {
// return count;
// }

// public String getQuery() {
// return query;
// }

// public void setCount(Integer count) {
// this.count = count;
// }

// public SearchedQueryFrequency(String query, Integer count) {
// this.query = query;
// this.count = count;
// }

// @Override
// public int compareTo(SearchedQueryFrequency o) {
// return this.getQuery().compareTo(o.getQuery());
// }

// }

class SearchedQueryFrequency implements Comparable<SearchedQueryFrequency> {
    private String query;
    private Integer count;
    public SearchedQueryFrequency left;
    public SearchedQueryFrequency right;
    public SearchedQueryFrequency parent;

    // Constructor to initialize a node with query and count
    public SearchedQueryFrequency(String data, int count) {
        this.query = data;
        this.count = count;
        this.parent = null;
        this.left = null;
        this.right = null;
    }
    
    // CompareTo method for sorting nodes based on the query string
    @Override
    public int compareTo(SearchedQueryFrequency o) {
        return this.getQuery().compareTo(o.getQuery());
    }

    // Getter method to retrieve the count of the searched query
    public Integer getCount() {
        return count;
    }

    // Getter method to retrieve the searched query string
    public String getQuery() {
        return query;
    }

    // Setter method to set the count of the searched query
    public void setCount(Integer count) {
        this.count = count;
    }
}