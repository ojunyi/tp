package seedu.coursepilot.model.person;

import java.util.List;
import java.util.function.Predicate;

import seedu.coursepilot.commons.util.StringUtil;
import seedu.coursepilot.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Student}'s {@code MatricNumber} starts with any of the keywords given.
 */
public class MatricNumberStartsWithKeywordsPredicate implements Predicate<Student> {
    private final List<String> keywords;

    public MatricNumberStartsWithKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Student student) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.startsWithString(student.getMatriculationNumber().toString(), keyword));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof MatricNumberStartsWithKeywordsPredicate)) {
            return false;
        }

        MatricNumberStartsWithKeywordsPredicate otherNameContainsKeywordsPredicate =
                (MatricNumberStartsWithKeywordsPredicate) other;
        return keywords.equals(otherNameContainsKeywordsPredicate.keywords);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keywords", keywords).toString();
    }
}
