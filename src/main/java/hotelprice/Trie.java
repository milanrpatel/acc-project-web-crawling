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
        Map<Character, TrieNode> childrenMap;
        char ch;
        boolean isWord;

        /**
         * Constructor to initialize a TrieNode with a character.
         *
         * @param ch The character associated with the TrieNode.
         */
        public TrieNode(char ch) {
            this.ch = ch;
            childrenMap = new HashMap<>();
        }

        /**
         * Default constructor to initialize a TrieNode.
         */
        public TrieNode() {
            childrenMap = new HashMap<>();
        }

        /**
         * Inserts a word into the Trie.
         *
         * @param wordString The word to be inserted.
         * @param root       The root of the Trie.
         */
        public void insert(String wordString, TrieNode root) {
            if (wordString == null || wordString.isEmpty())
                return;
            char firstCharacter = wordString.charAt(0);
            TrieNode child = childrenMap.get(firstCharacter);
            if (child == null) {
                child = new TrieNode(firstCharacter);
                childrenMap.put(firstCharacter, child);
            }

            if (wordString.length() > 1)
                child.insert(wordString.substring(1), child);
            else
                child.isWord = true;
        }
    }

    TrieNode root;

    /**
     * Inserts a list of words into the Trie.
     *
     * @param wordsList The list of words to be inserted.
     */
    public void insertWords(List<String> wordsList) {
        if (root == null)
            root = new TrieNode();
        for (String word : wordsList)
            root.insert(word, root);
    }

    /**
     * Finds a prefix in the Trie.
     *
     * @param prefixStr The prefix to search for.
     * @param flag      Flag indicating exact match or not.
     * @return True if the prefix is found, false otherwise.
     */
    public boolean find(String prefixStr, boolean flag) {
        TrieNode lastNode = root;
        for (char ch : prefixStr.toCharArray()) {
            lastNode = lastNode.childrenMap.get(ch);
            if (lastNode == null)
                return false;
        }
        return !flag || lastNode.isWord;
    }

    /**
     * Finds a prefix in the Trie.
     *
     * @param prefixStr The prefix to search for.
     * @return True if the prefix is found, false otherwise.
     */
    public boolean find(String prefixStr) {
        return find(prefixStr, false);
    }

    /**
     * Recursively completes words given a TrieNode.
     *
     * @param rootNode    The root of the subtree.
     * @param listOfWords The list to store completed words.
     * @param currWord    The current word being constructed.
     */
    public void completeWordRecurse(TrieNode rootNode, List<String> listOfWords, StringBuffer currWord) {
        if (rootNode.isWord) {
            listOfWords.add(currWord.toString());
        }

        if (rootNode.childrenMap == null || rootNode.childrenMap.isEmpty())
            return;

        for (TrieNode child : rootNode.childrenMap.values()) {
            completeWordRecurse(child, listOfWords, currWord.append(child.ch));
            currWord.setLength(currWord.length() - 1);
        }
    }

    /**
     * Completes words for a given prefix.
     *
     * @param prefixStr The prefix to complete words for.
     * @return The list of completed words.
     */
    public List<String> completeWord(String prefixStr) {
        List<String> listOfWords = new ArrayList<>();
        TrieNode lastNode = root;
        StringBuffer curr = new StringBuffer();
        for (char ch : prefixStr.toCharArray()) {
            lastNode = lastNode.childrenMap.get(ch);
            if (lastNode == null)
                return listOfWords;
            curr.append(ch);
        }
        completeWordRecurse(lastNode, listOfWords, curr);
        return listOfWords;
    }

    /**
     * Prints the Trie recursively given a TrieNode.
     *
     * @param rootNode The root of the subtree.
     * @param currWord The current word being constructed.
     */
    public void printTrie(TrieNode rootNode, StringBuffer currWord) {
        if (rootNode.isWord) {
            System.out.println(currWord.toString());
        }

        if (rootNode.childrenMap == null || rootNode.childrenMap.isEmpty())
            return;

        for (TrieNode child : rootNode.childrenMap.values()) {
            printTrie(child, currWord.append(child.ch));
            currWord.setLength(currWord.length() - 1);
        }
    }

    /**
     * Prints the entire Trie.
     */
    public void printTrie() {
        StringBuffer currWord = new StringBuffer();
        printTrie(root, currWord);
    }

    /**
     * Main method to demonstrate Trie functionalities.
     *
     * @param args Command line arguments.
     */
    public static void main(String[] args) {
        List<String> words = List.of("doc", "an", "hell", "hello", "hellooooooooooo", "cat", "a", "hel", "help",
                "helps",
                "helping");
        Trie trie = new Trie();
        trie.insertWords(words);

        System.out.println(trie.completeWord("hel"));
        trie.printTrie();
    }

}