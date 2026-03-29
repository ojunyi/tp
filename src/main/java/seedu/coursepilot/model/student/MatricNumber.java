package seedu.coursepilot.model.student;

import static java.util.Objects.requireNonNull;
import static seedu.coursepilot.commons.util.AppUtil.checkArgument;

/**
 * Represents a Student's matriculation number in CoursePilot.
 * Guarantees: immutable; is valid as declared in {@link #isValidMatricNumber(String)}
 */
public class MatricNumber {

    public static final String MESSAGE_CONSTRAINTS =
            "Matric Number should be in the form of Axxxxxx, where x is any digit";

    /*
     * The first character of the matric number must be 'A', followed by 6 digits.
     */
    public static final String VALIDATION_REGEX = "A\\d{6}";

    public final String matricNumber;

    /**
     * Constructs a {@code MatricNumber}.
     *
     * @param matricNumber A valid matriculation number.
     */
    public MatricNumber(String matricNumber) {
        requireNonNull(matricNumber);
        checkArgument(isValidMatricNumber(matricNumber), MESSAGE_CONSTRAINTS);
        this.matricNumber = matricNumber;
    }

    /**
     * Returns true if a given string is a valid matric number.
     */
    public static boolean isValidMatricNumber(String test) {
        return test.matches(VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return matricNumber;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof MatricNumber)) {
            return false;
        }

        MatricNumber otherMatric = (MatricNumber) other;
        return matricNumber.equals(otherMatric.matricNumber);
    }

    @Override
    public int hashCode() {
        return matricNumber.hashCode();
    }

}
