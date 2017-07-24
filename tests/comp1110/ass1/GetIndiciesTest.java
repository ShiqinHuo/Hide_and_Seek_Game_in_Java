package comp1110.ass1;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertTrue;

public class GetIndiciesTest {

    private String intArrayToString(int[] in) {
        String out = "{";
        for (int i = 0; i < in.length; i++) {
            out += " " + in[i];
            if (i != in.length -1 )
                out += ",";
        }
        out += " }";
        return out;
    }

    private void checkIndicies(Mask m, char placement, int[] reference) {
        int[] actual = m.getIndices(placement);
        Arrays.sort(actual);
        String r = intArrayToString(reference);
        String a = intArrayToString(actual);
        assertTrue("Incorrect number of indices. Expected "+r+", but got "+a, a.equals(r));
    }

    @Test
    public void trivialPlacementW() {
        checkIndicies(Mask.W, 'A', new int[]{ 0, 1, 3, 4, 5, 6, 8});
    }

    @Test
    public void trivialPlacementX() {
        checkIndicies(Mask.X, 'A', new int[]{ 0, 2, 3, 5, 6, 7, 8 });
    }

    @Test
    public void rotatedPlacement() {
        checkIndicies(Mask.W, 'B', new int[]{ 0, 1, 2, 4, 5, 6, 7 });
        checkIndicies(Mask.W, 'C', new int[]{ 0, 2, 3, 4, 5, 7, 8 });
        checkIndicies(Mask.W, 'D', new int[]{ 1, 2, 3, 4, 6, 7, 8 });
        checkIndicies(Mask.X, 'C', new int[]{ 0, 1, 2, 3, 5, 6, 8 });
        checkIndicies(Mask.X, 'D', new int[]{ 0, 1, 2, 5, 6, 7, 8 });
        checkIndicies(Mask.Z, 'B', new int[]{ 0, 1, 2, 4, 6, 7, 8 });
        checkIndicies(Mask.Z, 'D', new int[]{ 0, 1, 2, 4, 6, 7, 8 });
    }

    @Test
    public void translatedPlacement() {
        checkIndicies(Mask.W, 'E', new int[]{ 9, 10, 12, 13, 14, 15, 17 });
        checkIndicies(Mask.X, 'I', new int[]{ 18, 20, 21, 23, 24, 25, 26 });
        checkIndicies(Mask.Y, 'I', new int[]{ 19, 20, 21, 22, 23, 24, 25 });
        checkIndicies(Mask.Z, 'M', new int[]{ 27, 29, 30, 31, 32, 33, 35 });
    }

    @Test
    public void translatedRotatedPlacement() {
        checkIndicies(Mask.W, 'F', new int[]{ 9, 10, 11, 13, 14, 15, 16 });
        checkIndicies(Mask.X, 'O', new int[]{ 27, 28, 29, 30, 32, 33, 35 });
        checkIndicies(Mask.Y, 'G', new int[]{ 10, 11, 12, 13, 14, 15, 16 });
        checkIndicies(Mask.Z, 'L', new int[]{ 18, 19, 20, 22, 24, 25, 26 });
    }

}
