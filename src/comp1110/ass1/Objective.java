package comp1110.ass1;

/**
 * This class is used to describe the objective of a given game.
 */
public class Objective {
    private String exposed;        // The set of shapes that must be left exposed
    private int maskWConstraint;   // A constraint on the rotation of mask 'W', if any

    /**
     * Constructor with no rotation constraint provided.
     *
     * @param exposed A string describing the set of shapes that must be left exposed
     */
    public Objective(String exposed) {
        this.exposed = exposed;
        this.maskWConstraint = -1;
    }

    /**
     * Constructor with a rotation constraint on mask 'W' provided.
     *
     * @param exposed A string describing the set of shapes that must be left exposed
     * @param maskWConstraint A number describing the rotation constraint on piece 'W'.
     *                      -1 means no constraint, 0 means upright, 1 means 90 degrees clockwise,
     *                       2 means 180 degrees, etc.
     */
    public Objective(String exposed, int maskWConstraint) {
        this.exposed = exposed;
        this.maskWConstraint = maskWConstraint;
    }

    /** @return the mask constraint */
    public int getMaskWConstraint() { return maskWConstraint; }


    /** @return the string describing the shapes that are to be exposed */
    public String getExposed() { return exposed; }

    /** @return a string representing the objective */
    public String toString() {
        if (maskWConstraint > -1)
            return "("+exposed+", "+maskWConstraint+")";
        else
            return "("+exposed+")";
    }
}
