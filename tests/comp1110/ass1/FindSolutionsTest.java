package comp1110.ass1;

import org.junit.Test;

import java.util.*;

import static org.junit.Assert.assertTrue;

public class FindSolutionsTest {

    @Test
    public void noSolution() {
        for (String s : NO_SOLUTIONS) {
            Hide h = new Hide(new Objective(s));
            String[] sol = h.getSolutions();
            assertTrue("getSolutions must not return null. It should return an empty array if no solutions are found", sol != null);
            assertTrue("Objective "+s+" has no solutions, but you returned "+sol.length, sol.length == 0);
        }

    }

    static void testNSolutions(int n) {
        Objective o = new Objective(NUM_SOLUTIONS[n]);
        String[] sol = new Hide(o).getSolutions();
        assertTrue("Expected "+n+" solutions for objective "+o.toString()+", but got "+sol.length, sol.length == n);
        for (String s : sol) {
            assertTrue("Reported solution "+s+" for objective "+o.toString()+", but that is not a solution", SAMPLE_SOLUTIONS.get(NUM_SOLUTIONS[n]).contains(s));
        }
    }

    @Test
    public void oneSolution() {
        testNSolutions(1);
    }

    @Test
    public void twoSolutions() {
        testNSolutions(2);
    }

    @Test
    public void threeSolutions() {
        testNSolutions(3);
    }

    @Test
    public void manySolutions() {
        testNSolutions(4);
        testNSolutions(5);
        testNSolutions(6);
    }

    private static final Map<String, Set<String>> SAMPLE_SOLUTIONS;
    private static final String[] NO_SOLUTIONS = {"BCEFFGGHH","DEFFFGGH", "ABBDFFGH", "ABCCFFG" };
    private static final String[] NUM_SOLUTIONS = {"BCEFFGGHH", "EFFGGH", "AACCDH", "AACDFGH", "ABDDF", "AACDFH", "AACDH"};

    static {
        SAMPLE_SOLUTIONS = new HashMap<>();
        Set<String> set;

        set = new HashSet<>();
        set.addAll(Arrays.asList("GCJN"));
        SAMPLE_SOLUTIONS.put("EFFGGH", set);

        set = new HashSet<>();
        set.addAll(Arrays.asList("JMEB", "OFIA"));
        SAMPLE_SOLUTIONS.put("AACCDH", set);

        set = new HashSet<>();
        set.addAll(Arrays.asList("EPIB", "INEB", "NGIA"));
        SAMPLE_SOLUTIONS.put("AACDFGH", set);

        set = new HashSet<>();
        set.addAll(Arrays.asList("DIEM", "EIMA", "HIAM", "KAMF"));
        SAMPLE_SOLUTIONS.put("ABDDF", set);

        set = new HashSet<>();
        set.addAll(Arrays.asList("BOIF", "EMIB", "FOIB", "HDIM", "MFIA"));
        SAMPLE_SOLUTIONS.put("AACDFH", set);

        set = new HashSet<>();
        set.addAll(Arrays.asList("BMIF", "DNEJ", "FMIB", "HNAJ", "OAIF", "PLEA"));
        SAMPLE_SOLUTIONS.put("AACDH", set);
    }

}
