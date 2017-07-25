package comp1110.ass1;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class CanonicalStringTest {

    @Test
    public void testNone() {
        String out = Hide.canonicalString(".");
        assertTrue("Expected an empty string, but got \""+out+"\"", out.equals(""));
    }

    @Test
    public void testOne() {
        String out = Hide.canonicalString("A");
        assertTrue("Expected \"A\", but got \""+out+"\"", out.equals("A"));
    }

    @Test
    public void testSort() {
        String out = Hide.canonicalString("BAC");
        assertTrue("Expected \"ABC\", but got \""+out+"\"", out.equals("ABC"));
    }

    @Test
    public void testLeadingTrailingDots() {
        String out = Hide.canonicalString(".HHEE.");
        assertTrue("Expected \"EEHH\", but got \""+out+"\"", out.equals("EEHH"));
    }

    @Test
    public void testLong() {
        String out = Hide.canonicalString(".ABC.DE..AG..CDEF.ABFCD.FGH...GAHCFD");
        assertTrue("Expected \"AAAABBCCCCDDDDEEFFFFGGGHH\", but got \""+out+"\"", out.equals("AAAABBCCCCDDDDEEFFFFGGGHH"));
    }
}
