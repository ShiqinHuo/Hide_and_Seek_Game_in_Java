package comp1110.ass1;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
//import java.util.Scanner;
/**
 * This class represents a game of 'hide', which is based directly on a children's game
 * from 'SmartGames' called "Pirates Jr -- Hide and seek"
 *
 *    http://www.smartgames.eu/en/smartgames/pirates-jr-–-hide-seek
 *
 * The class and the those that it refer to provide the core logic of
 * the game, which is used by the GUI, which runs the game in a window.
 *
 *
 * The game uses the following encodings to represent game state.
 *
 * The board is represented by a string of 36 characters (3x3x4 = 36),
 * each character is either a '.' (meaning that the corresponding square
 * is unoccupied or masked), or by a letter from 'A' to 'H' corresponding
 * to one of the eight animals.   The board is fixed.  It never changes.
 *
 * The 36 board positions are indexed starting with the nine positions
 * in the top-left quadrant, then those in the top-right, then the
 * bottom-left, then finally the bottom-right.   Within each quadrant, the
 * pieces are numbered consecutively starting at top left, continuing
 * across the first row, then to the second row, finishing with the bottom
 * right square in the quadrant.   Thus:
 *   -  0 indexes the top-left square in the game
 *   - 11 indexes the top-right square in the game
 *   - 24 indexes the bottom-left square in the game
 *   - 35 indexes the bottom-right square in the game
 *   - etc.
 *
 * The four masks are represented by the four letters 'W', 'X', 'Y', and 'Z'.
 *
 * The images of the eight shapes and four masks can be seen in the
 * gui/assets folder.
 *
 * The state of the masks (where each one is placed, and with what orientation)
 * is represented by a string of four characters.   Each character is either
 * a space ' ', indicating that that mask has not been placed, or is a
 * letter from 'A' to 'P' which encodes where that mask has been placed, and at
 * what orientation (two of the masks, 'Y' and 'Z', have only two orientations, since
 * the other two orientations are are simple reflections).  These encodings
 * are described in detail in the Mask class.
 */
public class Hide {

    /* a basic board configuration */
    public static final String BOARD = ".ABC.DE..AG..CDEF.ABFCD.FGH...GAHCFD";  // string describing the basic game board
    public static char EMPTY_CHAR = '.';                                        // used to denote a masked or unoccupied game square

    /* constants describing the shape of the board */
    public static final int SMALL_SIDE = 3;                         // dimensions of sub-board
    public static final int SMALL_PLACES = SMALL_SIDE*SMALL_SIDE;   // number of places in sub-board
    public static final int SIDE = SMALL_SIDE * 2;                  // dimensions of overall board
    public static final int PLACES = SIDE*SIDE;                     // number of places in board

    /* a trivial objective that can be used to drive the game */
    public static final Objective TRIVIAL_OBJECTIVE = new Objective("AAAA", 3);

    /* a set of progressively harder objectives that can be used to drive the game */
    public static final Objective[][] SAMPLE_OBJECTIVES = {
            /* EASY */
            {new Objective("FFFF", 0), new Objective("DDDD", 3), new Objective("CCCC", 1)},
            /* HARDER */
            {new Objective("DF"  ), new Objective("AAAF"  ), new Objective("CCH")},
            /* HARD */
            {new Objective("AFFH"), new Objective("BCCCEF"), new Objective("DEFG")},
            /* HARDEST */
            {new Objective("CCFF"), new Objective("CCDG"  ),   new Objective("AABEF")},
    };


    private Objective objective;                  // the objective of this instance of the game
    private String solution;                      // the solution to the current game


    /**
     * Constructor for a game, given a level of difficulty for the new game
     *
     * This should create a new game with a single valid solution and a level of
     * difficulty that corresponds to the argument difficulty.
     *
     * @param difficulty A value between 0.0 (easiest) and 10.0 (hardest) specifying the desired level of difficulty.
     */
    public Hide(double difficulty) {
        objective = establishInterestingObjective(difficulty);
        if (objective == null)
            objective = establishSimpleObjective(difficulty);
    }


    /**
     * Constructor for a game, given a particular objective for that game
     *
     * This should create a new game with the given objective.
     *
     * @param objective The objective for the new game
     */
    public Hide(Objective objective) {
        this.objective = objective;
    }


    /**
     * Set the game's objective using the given difficulty level and the sample
     * objectives provided in SAMPLE_OBJECTIVES.
     *
     * The code should index into the samples according to the difficulty, using the
     * first arrays for difficulty values less than 2.5/10, the next for values
     * less than 5.0/10, etc.
     *
     * The code should choose within the arrays randomly, so for a given difficulty
     * level, any one of the three sample values might be used.
     *
     * For example, if the difficulty level was 1/10, then the first ('EASY') values
     * of each array should be used.   A random number generator should then choose
     * an index between 0 and 2 and set the objective accordingly, so if the randomly
     * generated value was 1, then it would be set to an objective with "DDDD" and 3.
     *
     * @param difficulty A value between 0.0 (easiest) and 10.0 (hardest) specifying the desired level of difficulty.
     */
    public static Objective establishSimpleObjective(double difficulty) {

        // FIXME Task 3:  Replace the code below with code that draws from SAMPLE_OBJECTIVES -- DONE
        Objective obj = TRIVIAL_OBJECTIVE;
        int number = (int)(Math.random()*3);
        if (difficulty<2.5) obj = SAMPLE_OBJECTIVES[0][number];
        if (2.5<=difficulty && difficulty <5.0) obj = SAMPLE_OBJECTIVES[1][number];
        if (difficulty>=5.0 && difficulty <7.5) obj = SAMPLE_OBJECTIVES[2][number];
        if (difficulty>=7.5 && difficulty <10.0) obj = SAMPLE_OBJECTIVES[3][number];
        return obj;
    }


    /**
     * Set the game's objective using the given difficulty level.
     *
     * This method should generate different objectives according to the following:
     *
     *   - It should respect the given difficulty, using some *principled* and *documented*
     *     approach determining the difficulty of a particular objective.
     *
     *   - It should not use TRIVIAL_OBJECTIVE or objectives from SAMPLE_OBJECTIVES.
     *
     *   - It should provide a rich number of objectives (much more than SAMPLE_OBJECTIVES),
     *     so that the player is not likely to be given the same objective repeatedly.
     *
     * This requires a deeper understanding of the problem, and some way of determining
     * what makes a particular objective difficult or easy.
     *
     * @param difficulty A value between 0.0 (easiest) and 10.0 (hardest) specifying the desired level of difficulty.
     */
    public static Objective establishInterestingObjective(double difficulty) {
        // FIXME Task 7: Replace this code with a good objective generator that does not draw from a simple set of samples



        return null;
    }


    /** @return the objective of the current game. */
    public Objective getObjective() {
        return objective;
    }


    /**
     * Return the list of exposed shapes, encoded as a sorted string of letters, one for
     * each shape that is exposed by the given masks.
     *
     * @param maskPositions The current mask, encoded as a four letter string, each letter
     *                     representing the masks from 0 to 3, and each letter representing
     *                     a placement and rotation.
     * @return Return a string that is the sorted list of shapes exposed by the given mask.
     */
    public String getExposed(String maskPositions) {
        return canonicalString(Mask.maskString(maskPositions, BOARD));
    }


    /**
     * Take a non-empty string composed only of letters 'A' to 'H' and the EMPTY_CHAR ('.'), and return a
     * new string that contains only the letters, with the letters sorted.   The number of each
     * letter must be exactly the same, no spaces or any other character should appear, and the
     * letters must be sorted lexicographically (alphabetically).
     *
     * Examples:
     *
     *   in:  "A B  F  A C "    out: "AABCF"
     *   in:  "    CGC CA F"    out: "ACCCFG"
     *
     * Hint: You may want to convert from String to array of char using toCharArray(), and then
     * do your work using the char array before converting back by creating a new String with
     * the char array as the argument to the constructor.
     *
     * @param in A string containing only the letters 'A' to 'H' and the EMPTY_CHAR ('.')
     * @return the input string with its characters sorted and spaces removed.
     */
    public static String canonicalString(String in) { //FIXME TASK 2 -- DONE
        //Scanner input = new Scanner(System.in);
        String r = "";
        for (int i = 0; i < in.length();i ++){
            if (in.charAt(i) != '.') r += in.charAt(i);
        }
        char[] chars = r.toCharArray();
        Arrays.sort(chars);
        //String sorted = new String(chars);
        return new String(chars);
    }


    /**
     * Find all solutions to this game, and return them as an array of strings, each string
     * describing a placement of the masks as a four-character sequence, according to the way
     * mask placements are encoded.
     *
     * @return An array of strings representing the set of all solutions to this game.  If
     * there are no solutions, the array should be empty (not null).
     */
    public String[] getSolutions() {
        // FIXME Task 6: replace this code with code that determines all solutions for this game's objective
        //System.out.println(getObjective().getExposed());
        String in =".ABC.DE..AG..CDEF.ABFCD.FGH...GAHCFD";//According to the given board.
        //LION:A; CAT:B; RAT:C; SHEEP:D; SQUIRE:E; COW:F; DOG:G; RABBIT:H
        String[][] input = {
                {"A","B","C","D"},
                {"E","F","G","H"},
                {"I","J","K","L"},
                {"M","N","O","P"}};
        List<String> ans = new ArrayList<>(); //The length is undefined, so here ArrayList is used.
        //https://stackoverflow.com/questions/15039519/how-to-dynamically-add-elements-to-string-array
        for (int i=0; i<4;i++) {
            for (int j = 0; j < 4; j++) {
                for (int k = 0; k < 4; k++) {
                    for (int m = 0; m < 4; m++) {
                        String[] method = new String[24];//24 kinds combinations for 4 certain chars
                        String choice1 = input[0][i];
                        String choice2 = input[1][j];
                        String choice3 = input[2][k];
                        String choice4 = input[3][m];
                        //24 kinds combinations for 4 certain chars
                        method[0] = choice1 + choice2 + choice3 + choice4;
                        method[1] = choice1 + choice2 + choice4 + choice3;
                        method[2] = choice1 + choice3 + choice2 + choice4;
                        method[3] = choice1 + choice3 + choice4 + choice2;
                        method[4] = choice1 + choice4 + choice2 + choice3;
                        method[5] = choice1 + choice4 + choice3 + choice2;
                        method[6] = choice2 + choice1 + choice3 + choice4;
                        method[7] = choice2 + choice1 + choice4 + choice3;
                        method[8] = choice2 + choice3 + choice1 + choice4;
                        method[9] = choice2 + choice3 + choice4 + choice1;
                        method[10] = choice2 + choice4 + choice3 + choice1;
                        method[11] = choice2 + choice4 + choice1 + choice3;
                        method[12] = choice3 + choice1 + choice2 + choice4;
                        method[13] = choice3 + choice1 + choice4 + choice2;
                        method[14] = choice3 + choice2 + choice1 + choice4;
                        method[15] = choice3 + choice2 + choice4 + choice1;
                        method[16] = choice3 + choice4 + choice1 + choice2;
                        method[17] = choice3 + choice4 + choice2 + choice1;
                        method[18] = choice4 + choice1 + choice2 + choice3;
                        method[19] = choice4 + choice1 + choice3 + choice2;
                        method[20] = choice4 + choice2 + choice1 + choice3;
                        method[21] = choice4 + choice2 + choice3 + choice1;
                        method[22] = choice4 + choice3 + choice1 + choice2;
                        method[23] = choice4 + choice3 + choice2 + choice1;
                        // finished 24-ele method for each chosen 4 chars
                        for (int n = 0; n < 24; n++) {
                            String sorted;
                            sorted = canonicalString(Mask.maskString(method[n], in));
                            if (sorted.equals(getObjective().getExposed())){
                                char[] chars = new char[4];
                                chars[0] = method[n].charAt(0);
                                chars[1] = method[n].charAt(1);
                                chars[2] = method[n].charAt(2);
                                chars[3] = method[n].charAt(3);
                                // We notice that mask Z and mask Y is symmetric so there exist duplicates in the character-string presenting.
                                // For instance,
                                // a four-char string "OFIA" represents the same solution with what "OFKA", "OFIC", "OFKC" do.
                                // Similarly, a four-char string "JMEB" represents the same solution with what "JMGB", "JMED", "JMGD" do.
                                // Generally , N/P, J/L, F/H, B/D, M/O, I/K, E/G, A/C can be replaced by their counterpart in each pair respectively,
                                // For the place whose index is 2 (represents mask Y) or 3 (represents mask Y) in the 4-char string
                                switch (chars[2]){
                                    case 'D': chars[2] ='B';break;
                                    case 'C': chars[2] ='A';break;
                                    case 'G': chars[2] ='E';break;
                                    case 'K': chars[2] ='I';break;
                                    case 'O': chars[2] ='M';break;
                                    case 'H': chars[2] ='F';break;
                                    case 'L': chars[2] ='J';break;
                                    case 'P': chars[2] ='N';break;
                                }
                                switch (chars[3]) {
                                    case 'D': chars[3] ='B';break;
                                    case 'C': chars[3] ='A';break;
                                    case 'G': chars[3] ='E';break;
                                    case 'K': chars[3] ='I';break;
                                    case 'O': chars[3] ='M';break;
                                    case 'H': chars[3] ='F';break;
                                    case 'L': chars[3] ='J';break;
                                    case 'P': chars[3] ='N';break;
                                }
                                String same = new String(chars);
                                if (!ans.contains(same)) ans.add(method[n]);
                            }//https://stackoverflow.com/questions/224311/cleanest-way-to-toggle-a-boolean-variable-in-java
                        }//https://stackoverflow.com/questions/15039519/how-to-dynamically-add-elements-to-string-array
                    }
                }
            }
        }
        //System.out.println(ans);
        String[] stringArray = ans.toArray(new String[ans.size()]);
        Arrays.sort(stringArray);
        return stringArray;}


    /**
     * Return the solution to the game.  The solution is calculated lazily, so first
     * check whether it's already been calculated.
     *
     * @return A string representing the solution to this habitat.
     */
    public String getSolution() {

        if (solution == null) setSolution();
            return solution;
    }

    /**
     * Establish the solution to this game.
     */
    private void setSolution() {
        String[] solutions = getSolutions();
        if (solutions.length != 1) {
            throw new IllegalArgumentException("Hide "+objective+" "+(solutions.length == 0 ? " has no " : " has more than one ")+"solution");
        } else
            solution = solutions[0];
    }
}
