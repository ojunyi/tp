package seedu.coursepilot.model.student;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.coursepilot.testutil.StudentBuilder;

public class PhoneStartsWithKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("91234567");
        List<String> secondPredicateKeywordList = Arrays.asList("91234567", "98765432");

        PhoneStartsWithKeywordsPredicate firstPredicate =
                new PhoneStartsWithKeywordsPredicate(firstPredicateKeywordList);
        PhoneStartsWithKeywordsPredicate secondPredicate =
                new PhoneStartsWithKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        PhoneStartsWithKeywordsPredicate firstPredicateCopy =
                new PhoneStartsWithKeywordsPredicate(firstPredicateKeywordList);
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
    public void test_phoneStartsWithKeywords_returnsTrue() {
        // One keyword - full phone number
        PhoneStartsWithKeywordsPredicate predicate =
                new PhoneStartsWithKeywordsPredicate(Collections.singletonList("91234567"));
        assertTrue(predicate.test(new StudentBuilder().withPhone("91234567").build()));

        // Partial keyword - prefix only
        predicate = new PhoneStartsWithKeywordsPredicate(Collections.singletonList("912"));
        assertTrue(predicate.test(new StudentBuilder().withPhone("91234567").build()));

        // Multiple keywords - only one matches
        predicate = new PhoneStartsWithKeywordsPredicate(Arrays.asList("912", "987"));
        assertTrue(predicate.test(new StudentBuilder().withPhone("91234567").build()));
    }

    @Test
    public void test_phoneDoesNotStartWithKeywords_returnsFalse() {
        // Zero keywords
        PhoneStartsWithKeywordsPredicate predicate =
                new PhoneStartsWithKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new StudentBuilder().withPhone("91234567").build()));

        // Non-matching keyword
        predicate = new PhoneStartsWithKeywordsPredicate(Collections.singletonList("98765432"));
        assertFalse(predicate.test(new StudentBuilder().withPhone("91234567").build()));

        // Keyword matches suffix but not prefix
        predicate = new PhoneStartsWithKeywordsPredicate(Collections.singletonList("4567"));
        assertFalse(predicate.test(new StudentBuilder().withPhone("91234567").build()));

        // Keywords match name, email and matric, but does not match phone
        predicate = new PhoneStartsWithKeywordsPredicate(Arrays.asList("Alice", "alice@example.com", "A123456"));
        assertFalse(predicate.test(new StudentBuilder().withName("Alice").withPhone("91234567")
                .withEmail("alice@example.com").withMatriculationNumber("A123456").build()));
    }

    @Test
    public void toStringMethod() {
        List<String> keywords = List.of("keyword1", "keyword2");
        PhoneStartsWithKeywordsPredicate predicate = new PhoneStartsWithKeywordsPredicate(keywords);

        String expected = PhoneStartsWithKeywordsPredicate.class.getCanonicalName() + "{keywords=" + keywords + "}";
        assertEquals(expected, predicate.toString());
    }
}
