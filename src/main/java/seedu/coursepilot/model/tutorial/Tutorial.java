package seedu.coursepilot.model.tutorial;

/**
 * Represents a tutorial in the course pilot.
 */
public abstract class Tutorial {

    private String timeSlot;
    private int capacity;

    /**
     * Creates a tutorial with the given time slot and capacity.
     *
     * @param timeSlot
     * @param capacity
     */
    public Tutorial(String timeSlot, int capacity) {
        this.timeSlot = timeSlot;
        this.capacity = capacity;
    }
}
