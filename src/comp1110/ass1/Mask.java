package comp1110.ass1;

//import com.sun.deploy.util.ArrayUtil;
//import com.sun.tools.javac.util.ArrayUtils;

/**
 * An enumeration representing the four masks in the game hide.
 *
 * In the provided version of this class, each of the masks do not have any
 * associated state.  You may wish to add that.  You will then need to use
 * constructors to initialized that state.
 *
 * You may want to look at the 'Planet' example in the Oracle enum tutorial for
 * an example of how to associate state (radius, density in that case) with each
 * item in the enumeration.
 *
 * http://docs.oracle.com/javase/tutorial/java/javaOO/enum.html
 */
public enum Mask {
    W(0),X(1),Y(2),Z(3);     // These do not use any state (or constructors).  You may want to add them.
//use id to present:
//https://stackoverflow.com/questions/15380243/how-to-retrieve-enum-name-using-the-id
    private int id;
    Mask(int id){
        this.id = id;
    }

//Always exist two blanks in these 4 masks. Below is the blank index array for 16 kinds of situations
//here we only consider the first quadrant, similar for other quadrants.
    static int[][][] position = {
            {{2,7},{3,8},{1,6},{0,5}}, // W : A B C D
            {{1,4},{4,5},{4,7},{3,4}}, // X : A B C D
            {{0,8},{2,6},{0,8},{2,6}}, // Y : A B C D
            {{1,7},{3,5},{1,7},{3,5}}  // Z : A B C D
    };

    /**
     * Return indicies corresponding to which board squares would be covered
     * by this mask given the provided placement.
     *
     * The placement character describes the place as follows:
     *    - letters 'A' to 'D' describe the first quadrant of the board,
     *      corresponding to board positions 0-8.
     *    - letters 'E' to 'H' describe the second quadrant of the board,
     *      corresponding to board positions 9-17.
     *    - letters 'I' to 'L' describe the third quadrant of the board,
     *      corresponding to board positions 18-26.
     *    - letters 'M' to 'P' describe the fourth quadrant of the board,
     *      corresponding to board positions 27-35.
     *    - letters 'A', 'E', 'I', and 'M' describe the mask upright
     *    - letters 'B', 'F', 'J', and 'N' describe the mask turned 90 degrees clockwise
     *    - letters 'C', 'G', 'K', and 'O' describe the mask turned 180 degrees
     *    - letters 'D', 'H', 'L', and 'P' describe the mask turned 270 degrees clockwise
     *
     * Examples:
     *
     *   Given the placement character 'A', the mask 'W' would return the indices: {0,1,3,4,5,6,8}. 2 7 -- H mask
     *   Given the placement character 'O', the mask 'X' would return the indices: {27,28,29,30,32,33,35}. 4 8-- U mask
     *
     * Hint: You can associate values with each entry in the enum using a constructor,
     * so you could use that to somehow encode the properties of each of the four masks.
     * Then in this method you could use the value to calculate the required indicies.
     *
     * The tutorial here: http://docs.oracle.com/javase/tutorial/java/javaOO/enum.html
     * has an example of a Planet enum, which includes two doubles in each planet's
     * constructor representing the mass and radius.   Those values are used in the
     * surfaceGravity() method, for example.
     *
     * @param placement A character describing the placement of this mask, as per the above encoding
     * @return A set of indices corresponding to the board positions that would be covered by this mask
     */
    public int[] getIndices(char placement) {
        //
        // FIXME Task 4 -- Done! : implement code that correctly creates an array of integers specifying the indicies of masked pieces
        int index, accumulate = 0;
        int[] temp = new int[7];

        if (placement>='A' && placement <='D')
            index = placement - 'A';

        else if (placement >= 'E' && placement <= 'H'){
            accumulate = 9; index = placement - 'E';}

        else if (placement >= 'I' && placement <= 'L'){
            accumulate = 18;index = placement - 'I';}

        else {accumulate = 27;index = placement - 'M';}

        for (int i = 0,j = 0; i <= 8; i++){ // remove the blank positions in the mask
            if (i!=position[id][index][0] && (i!=position[id][index][1])) {
                temp[j] = i;
                j++;
            }
        }
        for (int k = 0; k< temp.length;k++){
            temp[k]+=accumulate;
        }//https://stackoverflow.com/questions/15933596/trying-to-add-1-to-an-integer-array-for-every-value-java
        return temp;
    }

    /**
     * Mask an input string with a given string of mask positions.   The
     * four characters composing the mask position string describe the
     * positions and rotations of each of four masks.
     *
     * The first character in the string describes the position and rotation
     * of the 'W' mask.  The second, third and fourth describe the positions
     * of the 'X', 'Y', and 'Z' positions respectively.
     *
     * If the character is a space ' ', then that means that the given mask
     * is not used.   Otherwise the encoding described above in getIndices() is used.
     *
     * Hint: The values() method of any enum type will return an array of the values in the enum.
     *
     * Hint: You cannot change strings, but you can convert from strings to
     * char arrays (.toCharArray()), and you can create new strings from
     * char arrays.
     *
     * @param maskPositions A string describing the positions of each of the masks, as per above
     * @param input An input string of 36 characters
     * @return The result of masking the input with the given mask, with masked characters replaced
     * by Hide.EMPTY_CHAR ('.').
     */
    public static String maskString(String maskPositions, String input) {
        // FIXME Task 5: implement code that correctly creates a masked string according to the comment above
        char[] posArray = maskPositions.toCharArray();
        char[] inArray = input.toCharArray();
        int[] masked_W = getIndices((posArray[0]));
        int[] masked_X = getIndices((posArray[1]));
        int[] masked_Y = getIndices((posArray[2]));
        int[] masked_Z = getIndices((posArray[3]));

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 7; j++) {
                if (masked_W[j] == inArray[i]) inArray[i] = '.';
            }
        }
        for (int i = 9; i < 18; i++) {
            for (int j = 0; j < 7; j++) {
                if (masked_X[j] == inArray[i]) inArray[i] = '.';
            }
        }
        for (int i = 18; i < 27; i++) {
            for (int j = 0; j < 7; j++) {
                if (masked_Y[j] == inArray[i]) inArray[i] = '.';
            }
        }
        for (int i = 27; i < 36; i++) {
            for (int j = 0; j < 7; j++) {
                if (masked_Z[j] == inArray[i]) inArray[i] = '.';
            }
        }//https://stackoverflow.com/questions/7655127/how-to-convert-a-char-array-back-to-a-string
        return new String(inArray);
    }
}
