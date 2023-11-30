package hotelprice;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class representing Trie data structure to store and search strings
 * efficiently.
 */
class Trie implements Serializable {
    /**
     * Inner class representing a node in the Trie.
     */
    public class TrieNode implements Serializable {
        Map<Character, TrieNode> children;
        char c;
        boolean isWord;

        /**
         * Constructor to initialize a TrieNode with a character.
         *
         * @param c The character associated with the TrieNode.
         */
        public TrieNode(char c) {
            this.c = c;
            children = new HashMap<>();
        }

        /**
         * Default constructor to initialize a TrieNode.
         */
        public TrieNode() {
            children = new HashMap<>();
        }

        /**
         * Inserts a word into the Trie.
         *
         * @param word The word to be inserted.
         * @param root The root of the Trie.
         */
        public void insert(String word, TrieNode root) {
            if (word == null || word.isEmpty())
                return;
            char firstChar = word.charAt(0);
            TrieNode child = children.get(firstChar);
            if (child == null) {
                child = new TrieNode(firstChar);
                children.put(firstChar, child);
            }

            if (word.length() > 1)
                child.insert(word.substring(1), child);
            else
                child.isWord = true;
        }
    }

    TrieNode root;

    /**
     * Inserts a list of words into the Trie.
     *
     * @param words The list of words to be inserted.
     */
    public void insertWords(List<String> words) {
        if (root == null)
            root = new TrieNode();
        for (String word : words)
            root.insert(word, root);
    }

    /**
     * Finds a prefix in the Trie.
     *
     * @param prefix The prefix to search for.
     * @param exact  Flag indicating exact match or not.
     * @return True if the prefix is found, false otherwise.
     */
    public boolean find(String prefix, boolean exact) {
        TrieNode lastNode = root;
        for (char c : prefix.toCharArray()) {
            lastNode = lastNode.children.get(c);
            if (lastNode == null)
                return false;
        }
        return !exact || lastNode.isWord;
    }

    /**
     * Finds a prefix in the Trie.
     *
     * @param prefix The prefix to search for.
     * @return True if the prefix is found, false otherwise.
     */
    public boolean find(String prefix) {
        return find(prefix, false);
    }

    /**
     * Recursively completes words given a TrieNode.
     *
     * @param root The root of the subtree.
     * @param list The list to store completed words.
     * @param curr The current word being constructed.
     */
    public void completeWordRecurse(TrieNode root, List<String> list, StringBuffer curr) {
        if (root.isWord) {
            list.add(curr.toString());
        }

        if (root.children == null || root.children.isEmpty())
            return;

        for (TrieNode child : root.children.values()) {
            completeWordRecurse(child, list, curr.append(child.c));
            curr.setLength(curr.length() - 1);
        }
    }

    /**
     * Completes words for a given prefix.
     *
     * @param prefix The prefix to complete words for.
     * @return The list of completed words.
     */
    public List<String> completeWord(String prefix) {
        List<String> list = new ArrayList<>();
        TrieNode lastNode = root;
        StringBuffer curr = new StringBuffer();
        for (char c : prefix.toCharArray()) {
            lastNode = lastNode.children.get(c);
            if (lastNode == null)
                return list;
            curr.append(c);
        }
        completeWordRecurse(lastNode, list, curr);
        return list;
    }

    /**
     * Prints the Trie recursively given a TrieNode.
     *
     * @param root The root of the subtree.
     * @param curr The current word being constructed.
     */
    public void printTrie(TrieNode root, StringBuffer curr) {
        if (root.isWord) {
            System.out.println(curr.toString());
        }

        if (root.children == null || root.children.isEmpty())
            return;

        for (TrieNode child : root.children.values()) {
            printTrie(child, curr.append(child.c));
            curr.setLength(curr.length() - 1);
        }
    }

    /**
     * Prints the entire Trie.
     */
    public void printTrie() {
        StringBuffer curr = new StringBuffer();
        printTrie(root, curr);
    }

    /**
     * Main method to demonstrate Trie functionalities.
     *
     * @param args Command line arguments.
     */
    public static void main(String[] args) {
        List<String> words = List.of("hello", "dog", "hell", "hellooooooooooo", "cat", "a", "hel", "help", "helps",
                "helping");
        Trie trie = new Trie();
        trie.insertWords(words);

        System.out.println(trie.completeWord("hel"));
        trie.printTrie();
    }

}