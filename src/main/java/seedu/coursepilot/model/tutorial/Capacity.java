package seedu.coursepilot.model.tutorial;

import static java.util.Objects.requireNonNull;
import static seedu.coursepilot.commons.util.AppUtil.checkArgument;

/**
 * Represents a Tutorial's capacity in CoursePilot.
 * Guarantees: immutable; is valid as declared in {@link #isValidCapacity(int)}
 */
public class Capacity {

    public static final String MESSAGE_CONSTRAINTS =
            "Capacity should be a positive integer between 1 and 1000";

    public static final int MIN_CAPACITY = 1;
    public static final int MAX_CAPACITY = 1000;

    public final int value;

    /**
     * Constructs a {@code Capacity}.
     *
     * @param capacity A valid capacity as a string.
     */
    public Capacity(String capacity) {
        requireNonNull(capacity);
        checkArgument(isValidCapacity(capacity), MESSAGE_CONSTRAINTS);
        value = Integer.parseInt(capacity);
    }

    /**
     * Constructs a {@code Capacity}.
     *
     * @param capacity A valid capacity.
     */
    public Capacity(int capacity) {
        requireNonNull(capacity);
        checkArgument(isValidCapacity(capacity), MESSAGE_CONSTRAINTS);
        value = capacity;
    }

    /**
     * Returns true if a given integer is a valid capacity.
     */
    public static boolean isValidCapacity(String test) {
        try {
            Integer.parseInt(test);
        } catch (NumberFormatException e) {
            return false;
        }
        int testValue = Integer.parseInt(test);
        return testValue >= MIN_CAPACITY && testValue <= MAX_CAPACITY;
    }

    public static boolean isValidCapacity(int test) {
        return test >= MIN_CAPACITY && test <= MAX_CAPACITY;
    }

    /**
     * Returns the capacity value.
     */
    public int getValue() {
        return value;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Capacity)) {
            return false;
        }

        Capacity otherCapacity = (Capacity) other;
        return value == otherCapacity.value;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(value);
    }
}
