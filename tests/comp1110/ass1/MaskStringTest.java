package comp1110.ass1;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class MaskStringTest {

    private static final String X = "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX";
    private static final String Y = "X.X.X.X.X.XX..XX..XX..XXX...XXXX....";

    @Test
    public void noMask() {
        String masked = Mask.maskString("    ", X);
        assertTrue("The empty mask ('    ') was used, so the string should be unchanged, but was '"+masked+"'", masked.equals(X));
        masked = Mask.maskString("    ", Y);
        assertTrue("The empty mask ('    ') was used, so the string should be unchanged, but was '"+masked+"'", masked.equals(Y));
    }

    @Test
    public void simpleSingleMask() {
        String masked = Mask.maskString("A   ", X);
        String expected = "..X....X.XXXXXXXXXXXXXXXXXXXXXXXXXXX";
        assertTrue("The  mask ('A   ') was used on '"+X+"', so expected '"+expected+"' but got '"+masked+"'", masked.equals(expected));
        masked = Mask.maskString(" A  ", Y);
        expected = "....X.....XX..XX..XX..XXX...XXXX....";
        assertTrue("The  mask (' A  ') was used on '"+Y+"', so expected '"+expected+"' but got '"+masked+"'", masked.equals(expected));
    }

    @Test
    public void unrotatedMasks() {
        String masked = Mask.maskString("AEIN", X);
        String expected = "..X....X..X..X....X.......X...X.X...";
        assertTrue("The  mask ('AEIN') was used on '"+X+"', so expected '"+expected+"' but got '"+masked+"'", masked.equals(expected));
        masked = Mask.maskString("AEIN", Y);
        expected = "..X.......X.......X...........X.....";
        assertTrue("The  mask ('AEIN') was used on '"+Y+"', so expected '"+expected+"' but got '"+masked+"'", masked.equals(expected));
    }

    @Test
    public void simpleRotatedMask() {
        String masked = Mask.maskString(" B  ", X);
        String expected = "....XX...XXXXXXXXXXXXXXXXXXXXXXXXXXX";
        assertTrue("The  mask (' B  ') was used on '\"+X+\"', so expected '"+expected+"' but got '"+masked+"'", masked.equals(expected));
        masked = Mask.maskString("  B ", Y);
        expected = "..X...X...XX..XX..XX..XXX...XXXX....";
        assertTrue("The  mask ('  B ') was used on '\"+Y+\"', so expected '"+expected+"' but got '"+masked+"'", masked.equals(expected));
    }


    @Test
    public void otherMasks() {
        String masked = Mask.maskString("JBFO", X);
        String expected = "....XX.....X...X.....X....X.X.....X.";
        assertTrue("The  mask ('JBFO') was used on '\"+X+\"', so expected '"+expected+"' but got '"+masked+"'", masked.equals(expected));
        masked = Mask.maskString("KAGP", Y);
        expected = "....X..............X....X.....X.....";
        assertTrue("The  mask ('KAGP') was used on '\"+Y+\"', so expected '"+expected+"' but got '"+masked+"'", masked.equals(expected));
    }
}
