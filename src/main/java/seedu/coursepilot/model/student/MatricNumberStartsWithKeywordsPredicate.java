package seedu.coursepilot.model.student;

import java.util.List;

import seedu.coursepilot.commons.util.StringUtil;

/**
 * Tests that a {@code Student}'s {@code MatricNumber} starts with any of the given keywords.
 * Matching is case-insensitive.
 */
public class MatricNumberStartsWithKeywordsPredicate extends StudentFieldPredicate {

    /**
     * Creates a {@code MatricNumberStartsWithKeywordsPredicate} with the given {@code keywords}.
     */
    public MatricNumberStartsWithKeywordsPredicate(List<String> keywords) {
        super(keywords);
    }

    @Override
    public String getSearchDescription() {
        return "Find matriculation starting with: " + keywords;
    }

    @Override
    public boolean test(Student student) {
        return keywords.stream()
                .anyMatch(keyword ->
                        StringUtil.startsWithString(student.getMatriculationNumber().toString(), keyword));
    }
}
