package seedu.coursepilot.model.student;

import java.util.List;

import seedu.coursepilot.commons.util.StringUtil;

/**
 * Tests that a {@code Student}'s {@code Name} contains any of the given keywords.
 * Keyword matching is case-insensitive and checks for whole-word presence.
 */
public class NameContainsKeywordsPredicate extends StudentFieldPredicate {

    /**
     * Creates a {@code NameContainsKeywordsPredicate} with the given {@code keywords}.
     */
    public NameContainsKeywordsPredicate(List<String> keywords) {
        super(keywords);
    }

    @Override
    public boolean test(Student student) {
        return keywords.stream()
                .anyMatch(keyword ->
                        StringUtil.containsWordIgnoreCase(student.getName().toString(), keyword));
    }
}
