package hotelprice;

/**
 * Utility class for calculating the edit distance between two strings.
 */
public class EditDistance {

	/**
	 * Calculates the edit distance between two input strings using dynamic
	 * programming.
	 *
	 * @param source The first input string.
	 * @param target The second input string.
	 * @return The edit distance between the two input strings.
	 */
	public static int editDistance(String source, String target) {
		int sourceLength = source.length();
		int targetLength = target.length();

		// Create a matrix to store intermediate results, with dimensions (sourceLength
		// + 1) x (targetLength + 1)
		int[][] editDistanceMatrix = new int[sourceLength + 1][targetLength + 1];

		// Initialize the first column of the matrix with values from 0 to sourceLength
		for (int i = 0; i <= sourceLength; i++) {
			editDistanceMatrix[i][0] = i;
		}

		// Initialize the first row of the matrix with values from 0 to targetLength
		for (int j = 0; j <= targetLength; j++) {
			editDistanceMatrix[0][j] = j;
		}

		// Iterate through each character in the source and target strings
		for (int i = 0; i < sourceLength; i++) {
			char sourceChar = source.charAt(i);

			for (int j = 0; j < targetLength; j++) {
				char targetChar = target.charAt(j);

				// Check if the characters at the current positions are equal
				if (sourceChar == targetChar) {
					// If equal, update the edit distance value based on the diagonal value
					editDistanceMatrix[i + 1][j + 1] = editDistanceMatrix[i][j];
				} else {
					// If not equal, calculate the minimum edit distance considering replacement,
					// insertion, and deletion
					int replace = editDistanceMatrix[i][j] + 1;
					int insert = editDistanceMatrix[i][j + 1] + 1;
					int delete = editDistanceMatrix[i + 1][j] + 1;

					// Determine the minimum of the three operations and update the edit distance
					// value
					int min = Math.min(Math.min(replace, insert), delete);
					editDistanceMatrix[i + 1][j + 1] = min;
				}
			}
		}

		// The final edit distance is stored in the bottom-right corner of the matrix
		return editDistanceMatrix[sourceLength][targetLength];
	}
}
