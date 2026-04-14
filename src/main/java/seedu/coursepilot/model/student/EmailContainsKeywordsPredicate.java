package seedu.coursepilot.model.student;

import java.util.List;

import seedu.coursepilot.commons.util.StringUtil;

/**
 * Tests that a {@code Student}'s {@code Email} contains any of the given keywords.
 * Matching is case-insensitive and checks for substring presence.
 */
public class EmailContainsKeywordsPredicate extends StudentFieldPredicate {

    /**
     * Creates an {@code EmailContainsKeywordsPredicate} with the given {@code keywords}.
     */
    public EmailContainsKeywordsPredicate(List<String> keywords) {
        super(keywords);
    }

    @Override
    public String getSearchDescription() {
        return "Find email containing: " + keywords;
    }

    @Override
    public boolean test(Student student) {
        return keywords.stream()
                .anyMatch(keyword ->
                        StringUtil.containsPartWordIgnoreCase(student.getEmail().toString(), keyword));
    }
}
