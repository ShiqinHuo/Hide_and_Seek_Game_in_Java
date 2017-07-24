package comp1110.ass1;

import org.junit.Test;

import java.util.*;

import static org.junit.Assert.assertTrue;

public class InterestingObjectiveTest {

    private static final Set<String> SAMPLE_OBJECTIVE_STRINGS;
    static {
        SAMPLE_OBJECTIVE_STRINGS = new HashSet<>();
        SAMPLE_OBJECTIVE_STRINGS.add(Hide.TRIVIAL_OBJECTIVE.getExposed());
        for (int d = 0; d < 4; d++) {
            for (int i = 0; i < Hide.SAMPLE_OBJECTIVES[d].length; i++)
                SAMPLE_OBJECTIVE_STRINGS.add(Hide.SAMPLE_OBJECTIVES[d][i].getExposed());
        }
    }


    private static final int FRESH_SAMPLES = 4000;
    @Test
    public void usesFreshObjectives() {
        Random r = new Random();
        for (int i = 0; i < FRESH_SAMPLES; i++) {
            Objective o = Hide.establishInterestingObjective(r.nextDouble()*10);
            assertTrue("Objective "+o+" is used in the provided samples.", !SAMPLE_OBJECTIVE_STRINGS.contains(o.getExposed()));
        }
    }

    @Test
    public void deterministicallyDistinguishesDifficulty() {
        Map<String, Double> diffmap = new HashMap<>();
        for (int d = 0; d < 8; d++) {
            double diff = d * 10.0 / 8.0;
            for (int i = 0; i < FRESH_SAMPLES; i++) {
                String objective = Hide.establishInterestingObjective(diff).toString();
                if (diffmap.containsKey(objective))
                    assertTrue("Objective "+objective+" is generated with difficulty "+diffmap.get(objective)+" and "+diff, diffmap.get(objective) == diff);
                else
                    diffmap.put(objective, diff);
            }

        }
    }

    private void ensureFunctionalSolver() {
        FindSolutionsTest.testNSolutions(3);
    }

    @Test
    public void utilizesOrientationConstraints() {
        ensureFunctionalSolver();
        for (int i = 0; i < FRESH_SAMPLES; i++) {
            Objective o = Hide.establishInterestingObjective(0);
            if (o.getMaskWConstraint() != -1) {
                Hide h = new Hide(o);
                String[] sol = h.getSolutions();
                assertTrue("Objective " + o + " has no solutions.", sol.length != 0);
                assertTrue("Objective " + o + " has more than one solution.", sol.length == 1);
                return;  // found an example, so test is OK.
            }
        }
        assertTrue("Did not generate any objectives with orientation constraints, despite "+FRESH_SAMPLES+" attempts at difficulty zero", false);
    }

    private static final int MIN_DIFF_OBJECTIVES = 10;
    @Test
    public void generatesManyObjectives() {
        for (int d = 0; d < 8; d++) {
            double diff = d * 10.0 / 8.0;
            Set<String> objectives = new HashSet<>();
            for (int i = 0; i < FRESH_SAMPLES; i++) {
                objectives.add(Hide.establishInterestingObjective(diff).toString());
            }
            int results = objectives.size();
            assertTrue("Only generated " + results + " different objectives, for difficulty " + diff + " (expected at least " + MIN_DIFF_OBJECTIVES + ")", results >= MIN_DIFF_OBJECTIVES);
        }
    }
}
