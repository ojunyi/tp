package seedu.coursepilot.model.tutorial;

import static java.util.Objects.requireNonNull;
import static seedu.coursepilot.commons.util.AppUtil.checkArgument;

/**
 * Represents a Tutorial's code in CoursePilot.
 * Guarantees: immutable; is valid as declared in {@link #isValidTutorialCode(String)}
 */
public class TutorialCode {

    public static final String MESSAGE_CONSTRAINTS =
            "Tutorial codes should not be blank, should not contain only whitespace, "
            + "and should only contain alphanumeric characters, hyphens, and underscores";

    /*
     * Tutorial code must not be empty or only whitespace, and should contain alphanumeric
     * characters, hyphens, or underscores only.
     */
    public static final String VALIDATION_REGEX = "[\\p{Alnum}_-]+";

    public final String value;

    /**
     * Constructs a {@code TutorialCode}.
     *
     * @param code A valid tutorial code.
     */
    public TutorialCode(String code) {
        requireNonNull(code);
        String trimmedCode = code.trim();
        checkArgument(isValidTutorialCode(trimmedCode), MESSAGE_CONSTRAINTS);
        value = trimmedCode;
    }

    /**
     * Returns true if a given string is a valid tutorial code.
     */
    public static boolean isValidTutorialCode(String test) {
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
        if (!(other instanceof TutorialCode)) {
            return false;
        }

        TutorialCode otherCode = (TutorialCode) other;
        return value.equals(otherCode.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
