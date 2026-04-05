package seedu.coursepilot.model.tag;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.coursepilot.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class TagTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Tag(null));
    }

    @Test
    public void constructor_invalidTagName_throwsIllegalArgumentException() {
        String invalidTagName = "";
        assertThrows(IllegalArgumentException.class, () -> new Tag(invalidTagName));
    }

    @Test
    public void isValidTagName() {
        // null tag name
        assertThrows(NullPointerException.class, () -> Tag.isValidTagName(null));

        // valid tag names
        assertTrue(Tag.isValidTagName("validTag"));
        assertTrue(Tag.isValidTagName("a")); // single char

        // invalid tag names
        assertFalse(Tag.isValidTagName("")); // empty
        assertFalse(Tag.isValidTagName("thisTagNameIsWayTooLongToBeValid")); // 31 chars
    }

}
