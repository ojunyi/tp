package seedu.coursepilot.model.tutorial;

import static java.util.Objects.requireNonNull;
import static seedu.coursepilot.commons.util.AppUtil.checkArgument;

/**
 * Represents a Tutorial's time slot in the course pilot.
 * Guarantees: immutable; is valid as declared in {@link #isValidTimeSlot(String)}
 */
public class TimeSlot {

    public static final String MESSAGE_CONSTRAINTS =
            "Time slots should not be blank and should contain valid time representations. "
            + "Common formats include '10:00-11:00', '1pm-2pm', '14:00-15:00', etc.";

    /*
     * Time slot must not be empty or only whitespace, and should contain
     * alphanumeric characters, colons, hyphens, spaces, and common time indicators (am/pm).
     */
    public static final String VALIDATION_REGEX = "[\\p{Alnum}:;, -]+";

    public final String value;

    /**
     * Constructs a {@code TimeSlot}.
     *
     * @param timeSlot A valid time slot.
     */
    public TimeSlot(String timeSlot) {
        requireNonNull(timeSlot);
        String trimmedTimeSlot = timeSlot.trim();
        checkArgument(isValidTimeSlot(trimmedTimeSlot), MESSAGE_CONSTRAINTS);
        value = trimmedTimeSlot;
    }

    /**
     * Returns true if a given string is a valid time slot.
     */
    public static boolean isValidTimeSlot(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof TimeSlot)) {
            return false;
        }

        TimeSlot otherTimeSlot = (TimeSlot) other;
        return value.equals(otherTimeSlot.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
