package seedu.coursepilot.model.tutorial;

import static java.util.Objects.requireNonNull;
import static seedu.coursepilot.commons.util.AppUtil.checkArgument;

import java.time.LocalTime;

/**
 * Represents a Tutorial's time slot in CoursePilot.
 * Guarantees: immutable; is valid as declared in {@link #isValidTimeSlot(String)}
 */
public class TimeSlot {

    public static final String MESSAGE_CONSTRAINTS =
            "Time slots should be of the format XX:XX-XX:XX where X is a integer and following 24-hour format."
            + "\n Start time should also be before end time. No spaces in between."
            + " E.g '10:00-11:00', '14:00-15:00', etc.";

    /*
     * Time slot must not be empty or only whitespace, and should
     * follow the format XX:XX-XX:XX
     */
    public static final String VALIDATION_REGEX = "([01]\\d|2[0-3]):[0-5]\\d-([01]\\d|2[0-3]):[0-5]\\d";

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
        if (!test.matches(VALIDATION_REGEX)) {
            return false;
        }
        String[] parts = test.split("-");
        LocalTime start = LocalTime.parse(parts[0]);
        LocalTime end = LocalTime.parse(parts[1]);
        return start.isBefore(end);
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
