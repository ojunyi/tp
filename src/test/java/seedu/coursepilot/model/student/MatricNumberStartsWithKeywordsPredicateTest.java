package seedu.coursepilot.model.student;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.coursepilot.testutil.StudentBuilder;

public class MatricNumberStartsWithKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("A123456");
        List<String> secondPredicateKeywordList = Arrays.asList("A123456", "A654321");

        MatricNumberStartsWithKeywordsPredicate firstPredicate =
                new MatricNumberStartsWithKeywordsPredicate(firstPredicateKeywordList);
        MatricNumberStartsWithKeywordsPredicate secondPredicate =
                new MatricNumberStartsWithKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        MatricNumberStartsWithKeywordsPredicate firstPredicateCopy =
                new MatricNumberStartsWithKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different predicate -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));

        // different predicate type, same keywords -> returns false
        NameContainsKeywordsPredicate namePredicate =
                new NameContainsKeywordsPredicate(firstPredicateKeywordList);
        assertFalse(firstPredicate.equals(namePredicate));
    }

    @Test
    public void test_matricNumberStartsWithKeywords_returnsTrue() {
        // One keyword - full matric number
        MatricNumberStartsWithKeywordsPredicate predicate =
                new MatricNumberStartsWithKeywordsPredicate(Collections.singletonList("A123456"));
        assertTrue(predicate.test(new StudentBuilder().withMatriculationNumber("A123456").build()));

        // Partial keyword - prefix only
        predicate = new MatricNumberStartsWithKeywordsPredicate(Collections.singletonList("A12"));
        assertTrue(predicate.test(new StudentBuilder().withMatriculationNumber("A123456").build()));

        // Multiple keywords - only one matches
        predicate = new MatricNumberStartsWithKeywordsPredicate(Arrays.asList("A12", "A65"));
        assertTrue(predicate.test(new StudentBuilder().withMatriculationNumber("A123456").build()));

        // Mixed-case keyword
        predicate = new MatricNumberStartsWithKeywordsPredicate(Collections.singletonList("a12"));
        assertTrue(predicate.test(new StudentBuilder().withMatriculationNumber("A123456").build()));
    }

    @Test
    public void test_matricNumberDoesNotStartWithKeywords_returnsFalse() {
        // Zero keywords
        MatricNumberStartsWithKeywordsPredicate predicate =
                new MatricNumberStartsWithKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new StudentBuilder().withMatriculationNumber("A123456").build()));

        // Non-matching keyword
        predicate = new MatricNumberStartsWithKeywordsPredicate(Collections.singletonList("A654321"));
        assertFalse(predicate.test(new StudentBuilder().withMatriculationNumber("A123456").build()));

        // Keyword matches suffix but not prefix
        predicate = new MatricNumberStartsWithKeywordsPredicate(Collections.singletonList("3456"));
        assertFalse(predicate.test(new StudentBuilder().withMatriculationNumber("A123456").build()));

        // Keywords match name, phone and email, but does not match matric
        predicate = new MatricNumberStartsWithKeywordsPredicate(Arrays.asList("Alice", "91234567",
                "alice@example.com"));
        assertFalse(predicate.test(new StudentBuilder().withName("Alice").withPhone("91234567")
                .withEmail("alice@example.com").withMatriculationNumber("A123456").build()));
    }

    @Test
    public void toStringMethod() {
        List<String> keywords = List.of("keyword1", "keyword2");
        MatricNumberStartsWithKeywordsPredicate predicate =
                new MatricNumberStartsWithKeywordsPredicate(keywords);

        String expected = MatricNumberStartsWithKeywordsPredicate.class.getCanonicalName()
                + "{keywords=" + keywords + "}";
        assertEquals(expected, predicate.toString());
    }
}
