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
            "Time slots should be of the format HH:mm-HH:mm (24-hour clock) without spaces.\n"
            + "Time must be between 00:00 and 23:59, and the start time must be before the end time.\n"
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

    /**
     * Returns the start time of this slot.
     */
    public LocalTime getStartTime() {
        return LocalTime.parse(value.split("-")[0]);
    }

    /**
     * Returns the end time of this slot.
     */
    public LocalTime getEndTime() {
        return LocalTime.parse(value.split("-")[1]);
    }

    /**
     * Returns true if this time slot overlaps with {@code other}.
     * Slots that merely touch at a boundary (e.g. 10:00-11:00 and 11:00-12:00) do not overlap.
     */
    public boolean overlapsWith(TimeSlot other) {
        requireNonNull(other);
        return getStartTime().isBefore(other.getEndTime())
                && other.getStartTime().isBefore(getEndTime());
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
