package seedu.coursepilot.model.person;

import java.util.List;
import java.util.function.Predicate;

import seedu.coursepilot.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Student}'s {@code Tutorial} starts with the keyword given.
 */
public class TutorialKeywordPredicate implements Predicate<Student> {
    private final List<String> keywords;

    public TutorialKeywordPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    public List<String> getKeywords() {
        return this.keywords;
    }

    // Temporary placeholder to make all tests false
    // TODO: Update this according to select
    @Override
    public boolean test(Student student) {
        return false;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof TutorialKeywordPredicate)) {
            return false;
        }

        TutorialKeywordPredicate otherNameContainsKeywordsPredicate = (TutorialKeywordPredicate) other;
        return keywords.equals(otherNameContainsKeywordsPredicate.keywords);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keywords", keywords).toString();
    }
}
