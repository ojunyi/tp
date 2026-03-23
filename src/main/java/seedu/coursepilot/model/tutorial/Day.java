package seedu.coursepilot.model.tutorial;

import static java.util.Objects.requireNonNull;
import static seedu.coursepilot.commons.util.AppUtil.checkArgument;

/**
 * Represents a Tutorial's day in the course pilot.
 * Guarantees: immutable; is valid as declared in {@link #isValidDay(String)}
 */
public class Day {

    public static final String MESSAGE_CONSTRAINTS =
            "Days should be valid day names (e.g., Monday, Mon, Tuesday, Tue, etc.) "
            + "or common abbreviations, and should not be blank";

    /*
     * Day must not be empty and should contain only alphabetic characters.
     * Accepts full names (Monday) and abbreviations (Mon).
     */
    public static final String VALIDATION_REGEX = "[A-Za-z]+";

    public final String value;

    /**
     * Constructs a {@code Day}.
     *
     * @param day A valid day.
     */
    public Day(String day) {
        requireNonNull(day);
        String trimmedDay = day.trim();
        checkArgument(isValidDay(trimmedDay), MESSAGE_CONSTRAINTS);
        value = trimmedDay;
    }

    /**
     * Returns true if a given string is a valid day.
     */
    public static boolean isValidDay(String test) {
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
        if (!(other instanceof Day)) {
            return false;
        }

        Day otherDay = (Day) other;
        return value.equals(otherDay.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
