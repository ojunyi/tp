package seedu.address.model.tutorial;

/**
 * Represents an example tutorial slot in the address book.
 * TO BE DELETED. This class is only used for testing purposes and will be removed in the future.
 */
public class ExampleTutorialSlot extends Tutorial {

    private String slotName;

    public ExampleTutorialSlot(String slotName, String timeSlot, int capacity) {
        super(timeSlot, capacity);
        this.slotName = slotName;
    }

    @Override
    public String getTutorialType() {
        return slotName;
    }

}
