package comp1110.ass1;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class SampleObjectiveTest {

    private int isInSample(int difficulty, Objective objective) {
        for (int i = 0; i < Hide.SAMPLE_OBJECTIVES[difficulty].length; i++) {
            if (objective == Hide.SAMPLE_OBJECTIVES[difficulty][i])
                return i;
        }
        return -1;
    }

    @Test
    public void usesSamples() {
        Objective o = Hide.establishSimpleObjective(0);
        assertTrue("Objective "+o+" is not drawn from Hide.SAMPLE_OBJECTIVES", isInSample(0, o) != -1);
    }

    @Test
    public void usesSamplesCorrectly() {
        Objective o;
        for (int d = 0; d < 4; d++) {
            o = Hide.establishSimpleObjective(2.5*d);
            assertTrue("Objective " + o + " is not drawn from Hide.SAMPLE_OBJECTIVES["+d+"]", isInSample(d, o) != -1);
        }
    }

    private static final int NUM_SAMPLES = 1000;
    @Test
    public void usesSamplesWell() {
        Objective o;
        int[] samples = new int[MAX_SAMPLE_INDEX];
        for (int d = 0; d < 4; d++) {
            for (int i = 0; i < samples.length; i++) { samples[i] = 0; }
            for (int i = 0; i < NUM_SAMPLES; i++) {
                o = Hide.establishSimpleObjective(2.5*d);
                int idx = isInSample(d, o);
                assertTrue("Objective " + o + " is not drawn from Hide.SAMPLE_OBJECTIVES["+d+"]", idx != -1);
                if (idx >= 0)
                    samples[idx]++;
            }
            int expected = NUM_SAMPLES / Hide.SAMPLE_OBJECTIVES[d].length;
            for (int i = 0; i < Hide.SAMPLE_OBJECTIVES[d].length; i++) {
                assertTrue(samples[i] + " samples drawn from index " + i + " at difficulty "+d+", but expected about "+expected, samples[i] > expected/3);
            }
        }
    }

    private static final int MAX_SAMPLE_INDEX;
    static {
        int max = 0;
        for (int i = 0; i < Hide.SAMPLE_OBJECTIVES.length; i++) {
            if (Hide.SAMPLE_OBJECTIVES[i].length > max)
                max = Hide.SAMPLE_OBJECTIVES[i].length;
        }
        MAX_SAMPLE_INDEX = max;
    }
}
