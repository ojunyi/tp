package seedu.coursepilot.model.student;

import java.util.List;

import seedu.coursepilot.commons.util.StringUtil;

/**
 * Tests that a {@code Student}'s {@code Phone} starts with any of the given keywords.
 */
public class PhoneStartsWithKeywordsPredicate extends StudentFieldPredicate {

    /**
     * Creates a {@code PhoneStartsWithKeywordsPredicate} with the given {@code keywords}.
     */
    public PhoneStartsWithKeywordsPredicate(List<String> keywords) {
        super(keywords);
    }

    @Override
    public String getSearchDescription() {
        return "Find phone starting with: " + keywords;
    }

    @Override
    public boolean test(Student student) {
        return keywords.stream()
                .anyMatch(keyword ->
                        StringUtil.startsWithString(student.getPhone().toString(), keyword));
    }
}
