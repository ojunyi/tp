package seedu.address.model.tutorial;

/**
 * Represents a tutorial in the address book.
 */
public abstract class Tutorial {

    private String timeSlot;
    private int capacity;

    public Tutorial(String timeSlot, int capacity) {
        this.timeSlot = timeSlot;
        this.capacity = capacity;
    }

    /**
     * Returns the type of the tutorial.
     *
     * @return the tutorial type
     */
    public abstract String getTutorialType();

    /**
     * Returns the time slot of the tutorial.
     *
     * @return
     */
    public String getTimeSlot() {
        return timeSlot;
    }

    /**
     * Returns the capacity of the tutorial.
     *
     * @return
     */
    public int getCapacity() {
        return capacity;
    }
}
