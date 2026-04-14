package seedu.coursepilot.model.student;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.function.Predicate;

import seedu.coursepilot.commons.util.ToStringBuilder;

/**
 * Abstract base class for predicates that filter {@code Student} objects by a specific field
 * using a list of keywords.
 */
public abstract class StudentFieldPredicate implements Predicate<Student> {

    protected final List<String> keywords;

    /**
     * Creates a {@code StudentFieldPredicate} with the given list of {@code keywords}.
     */
    public StudentFieldPredicate(List<String> keywords) {
        requireNonNull(keywords);
        this.keywords = keywords;
    }

    public String getSearchDescription() {
        return " Keywords: " + keywords;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (other == null || !getClass().equals(other.getClass())) {
            return false;
        }
        StudentFieldPredicate otherPredicate = (StudentFieldPredicate) other;
        return keywords.equals(otherPredicate.keywords);
    }

    @Override
    public int hashCode() {
        return keywords.hashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keywords", keywords).toString();
    }
}
