package seedu.address.model.tutorial;

/**
 * Represents an example tutorial slot in the address book.
 * TO BE DELETED. This class is only used for testing purposes and will be removed in the future.
 */
public class ExampleTutorialSlot extends Tutorial {

    private String slotName;

    /**
     * Creates an example tutorial slot with the given slot name, time slot, and capacity.
     *
     * @param slotName
     * @param timeSlot
     * @param capacity
     */
    public ExampleTutorialSlot(String slotName, String timeSlot, int capacity) {
        super(timeSlot, capacity);
        this.slotName = slotName;
    }

    /**
     * Returns the type of the tutorial.
     *
     * @return the tutorial type
     */
    @Override
    public String getTutorialType() {
        return slotName;
    }

}
