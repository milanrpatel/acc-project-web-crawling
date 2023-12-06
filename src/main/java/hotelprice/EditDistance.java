package hotelprice;

/**
 * Utility class for determining the edit distance among strings.
 */
public class EditDistance {

	/**
	 * Edit distance algorithm between 2 input strings using dynamic
	 * programming.
	 *
	 * @param source input1 string.
	 * @param target input2 string.
	 * @return The edit distance measurement between the two provided strings.
	 */
	public static int editDistance(String source, String target) {
		int sourceLength = source.length();
		int targetLength = target.length();

		// Create a matrix to store intermediate results, with dimensions (sourceLength
		// + 1) x (targetLength + 1)
		int[][] editDistanceMatrixFinal = new int[sourceLength + 1][targetLength + 1];

		// Setting initial column of the matrix with values from 0 to sourceLength
		for (int i = 0; i <= sourceLength; i++) {
			editDistanceMatrixFinal[i][0] = i;
		}

		// Init first row of the matrix with values from 0 to targetLength
		for (int j = 0; j <= targetLength; j++) {
			editDistanceMatrixFinal[0][j] = j;
		}

		// Iterate through each character in the source and target strings
		for (int i = 0; i < sourceLength; i++) {
			char sourceChar = source.charAt(i);

			for (int j = 0; j < targetLength; j++) {
				char targetChar = target.charAt(j);

				// Check if the characters at the current positions are equal
				if (sourceChar == targetChar) {
					// If equal, update the edit distance value based on the diagonal value
					editDistanceMatrixFinal[i + 1][j + 1] = editDistanceMatrixFinal[i][j];
				} else {
					// If not equal, calculate the minimum edit distance considering replacement,
					// insertion, and deletion
					int replace = editDistanceMatrixFinal[i][j] + 1;
					int insert = editDistanceMatrixFinal[i][j + 1] + 1;
					int delete = editDistanceMatrixFinal[i + 1][j] + 1;

					// Determine the minimum of the three operations and update the edit distance
					// value
					int min = Math.min(Math.min(replace, insert), delete);
					editDistanceMatrixFinal[i + 1][j + 1] = min;
				}
			}
		}

		// edit distance in number of matrix
		return editDistanceMatrixFinal[sourceLength][targetLength];
	}
}
