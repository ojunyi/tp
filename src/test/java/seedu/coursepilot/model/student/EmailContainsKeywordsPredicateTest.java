package seedu.coursepilot.model.student;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.coursepilot.testutil.StudentBuilder;

public class EmailContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("alice@example.com");
        List<String> secondPredicateKeywordList = Arrays.asList("alice@example.com", "bob@example.com");

        EmailContainsKeywordsPredicate firstPredicate = new EmailContainsKeywordsPredicate(firstPredicateKeywordList);
        EmailContainsKeywordsPredicate secondPredicate = new EmailContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        EmailContainsKeywordsPredicate firstPredicateCopy =
                new EmailContainsKeywordsPredicate(firstPredicateKeywordList);
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
    public void test_emailContainsKeywords_returnsTrue() {
        // One keyword - full email
        EmailContainsKeywordsPredicate predicate =
                new EmailContainsKeywordsPredicate(Collections.singletonList("alice@example.com"));
        assertTrue(predicate.test(new StudentBuilder().withEmail("alice@example.com").build()));

        // Partial keyword - domain only
        predicate = new EmailContainsKeywordsPredicate(Collections.singletonList("example.com"));
        assertTrue(predicate.test(new StudentBuilder().withEmail("alice@example.com").build()));

        // Multiple keywords - only one matches
        predicate = new EmailContainsKeywordsPredicate(Arrays.asList("alice@example.com", "bob@example.com"));
        assertTrue(predicate.test(new StudentBuilder().withEmail("alice@example.com").build()));

        // Mixed-case keyword
        predicate = new EmailContainsKeywordsPredicate(Collections.singletonList("ALICE@EXAMPLE.COM"));
        assertTrue(predicate.test(new StudentBuilder().withEmail("alice@example.com").build()));
    }

    @Test
    public void test_emailDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        EmailContainsKeywordsPredicate predicate =
                new EmailContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new StudentBuilder().withEmail("alice@example.com").build()));

        // Non-matching keyword
        predicate = new EmailContainsKeywordsPredicate(Collections.singletonList("bob@example.com"));
        assertFalse(predicate.test(new StudentBuilder().withEmail("alice@example.com").build()));

        // Keywords match name, phone and matric, but does not match email
        predicate = new EmailContainsKeywordsPredicate(Arrays.asList("Bob", "91234567", "A123456"));
        assertFalse(predicate.test(new StudentBuilder().withName("Alice").withPhone("91234567")
                .withEmail("alice@example.com").withMatriculationNumber("A123456").build()));
    }

    @Test
    public void toStringMethod() {
        List<String> keywords = List.of("keyword1", "keyword2");
        EmailContainsKeywordsPredicate predicate = new EmailContainsKeywordsPredicate(keywords);

        String expected = EmailContainsKeywordsPredicate.class.getCanonicalName() + "{keywords=" + keywords + "}";
        assertEquals(expected, predicate.toString());
    }
}
