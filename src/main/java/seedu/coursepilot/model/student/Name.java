package seedu.coursepilot.model.student;

import static java.util.Objects.requireNonNull;
import static seedu.coursepilot.commons.util.AppUtil.checkArgument;

/**
 * Represents a Student's name in CoursePilot.
 * Guarantees: immutable; is valid as declared in {@link #isValidName(String)}
 */
public class Name {
    public static final int MAX_NAME_LENGTH = 100;

    public static final String MESSAGE_CONSTRAINTS =
            "Names should contain at least one alphabetic character, may include spaces and symbols, "
            + "should not be blank, and should be between 1 and 100 characters long";

    private static final String LETTER_REGEX = ".*[a-zA-Z].*";
    private static final String VALID_CHARACTERS_REGEX = "[^\\p{Cntrl}]+";

    public final String fullName;

    /**
     * Constructs a {@code Name}.
     *
     * @param name A valid name.
     */
    public Name(String name) {
        requireNonNull(name);
        checkArgument(isValidName(name), MESSAGE_CONSTRAINTS);
        fullName = name;
    }

    /**
     * Returns true if a given string is a valid name.
     */
    public static boolean isValidName(String test) {
        requireNonNull(test);
        return !test.isBlank()
                && test.length() <= MAX_NAME_LENGTH
                && test.matches(VALID_CHARACTERS_REGEX)
                && test.matches(LETTER_REGEX);
    }


    @Override
    public String toString() {
        return fullName;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Name)) {
            return false;
        }

        Name otherName = (Name) other;
        return fullName.equals(otherName.fullName);
    }

    @Override
    public int hashCode() {
        return fullName.hashCode();
    }

}
