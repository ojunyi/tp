package seedu.coursepilot.logic.parser;

import static seedu.coursepilot.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.coursepilot.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static seedu.coursepilot.logic.commands.CommandTestUtil.EMAIL_DESC_BOB;
import static seedu.coursepilot.logic.commands.CommandTestUtil.INVALID_EMAIL_DESC;
import static seedu.coursepilot.logic.commands.CommandTestUtil.INVALID_MATRIC_DESC;
import static seedu.coursepilot.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.coursepilot.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static seedu.coursepilot.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.coursepilot.logic.commands.CommandTestUtil.MATRIC_DESC_AMY;
import static seedu.coursepilot.logic.commands.CommandTestUtil.MATRIC_DESC_BOB;
import static seedu.coursepilot.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.coursepilot.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.coursepilot.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.coursepilot.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static seedu.coursepilot.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.coursepilot.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.coursepilot.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static seedu.coursepilot.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
import static seedu.coursepilot.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.coursepilot.logic.commands.CommandTestUtil.VALID_MATRIC_BOB;
import static seedu.coursepilot.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.coursepilot.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.coursepilot.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.coursepilot.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.coursepilot.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.coursepilot.logic.parser.CliSyntax.PREFIX_MATRICNUMBER;
import static seedu.coursepilot.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.coursepilot.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.coursepilot.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.coursepilot.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.coursepilot.testutil.TypicalPersons.AMY;
import static seedu.coursepilot.testutil.TypicalPersons.BOB;

import org.junit.jupiter.api.Test;

import seedu.coursepilot.logic.Messages;
import seedu.coursepilot.logic.commands.AddCommand;
import seedu.coursepilot.model.person.Email;
import seedu.coursepilot.model.person.MatricNumber;
import seedu.coursepilot.model.person.Name;
import seedu.coursepilot.model.person.Phone;
import seedu.coursepilot.model.person.Student;
import seedu.coursepilot.model.tag.Tag;
import seedu.coursepilot.testutil.PersonBuilder;

public class AddCommandParserTest {
    private AddCommandParser parser = new AddCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Student expectedStudent = new PersonBuilder(BOB).withTags(VALID_TAG_FRIEND).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + MATRIC_DESC_BOB + TAG_DESC_FRIEND, new AddCommand(expectedStudent));


        // multiple tags - all accepted
        Student expectedStudentMultipleTags = new PersonBuilder(BOB).withTags(VALID_TAG_FRIEND, VALID_TAG_HUSBAND)
                .build();
        assertParseSuccess(parser,
                NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + MATRIC_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                new AddCommand(expectedStudentMultipleTags));
    }

    @Test
    public void parse_repeatedNonTagValue_failure() {
        String validExpectedPersonString = NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + MATRIC_DESC_BOB + TAG_DESC_FRIEND;

        // multiple names
        assertParseFailure(parser, NAME_DESC_AMY + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        // multiple phones
        assertParseFailure(parser, PHONE_DESC_AMY + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE));

        // multiple emails
        assertParseFailure(parser, EMAIL_DESC_AMY + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_EMAIL));

        // multiple addresses
        assertParseFailure(parser, MATRIC_DESC_AMY + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_MATRICNUMBER));

        // multiple fields repeated
        assertParseFailure(parser,
                validExpectedPersonString + PHONE_DESC_AMY + EMAIL_DESC_AMY + NAME_DESC_AMY + MATRIC_DESC_AMY
                        + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME, PREFIX_MATRICNUMBER,
                        PREFIX_EMAIL, PREFIX_PHONE));

        // invalid value followed by valid value

        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        // invalid email
        assertParseFailure(parser, INVALID_EMAIL_DESC + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_EMAIL));

        // invalid phone
        assertParseFailure(parser, INVALID_PHONE_DESC + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE));

        // invalid address
        assertParseFailure(parser, INVALID_MATRIC_DESC + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_MATRICNUMBER));

        // valid value followed by invalid value

        // invalid name
        assertParseFailure(parser, validExpectedPersonString + INVALID_NAME_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        // invalid email
        assertParseFailure(parser, validExpectedPersonString + INVALID_EMAIL_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_EMAIL));

        // invalid phone
        assertParseFailure(parser, validExpectedPersonString + INVALID_PHONE_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE));

        // invalid address
        assertParseFailure(parser, validExpectedPersonString + INVALID_MATRIC_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_MATRICNUMBER));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero tags
        Student expectedStudent = new PersonBuilder(AMY).withTags().build();
        assertParseSuccess(parser, NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY + MATRIC_DESC_AMY,
                new AddCommand(expectedStudent));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, VALID_NAME_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + MATRIC_DESC_BOB,
                expectedMessage);

        // missing phone prefix
        assertParseFailure(parser, NAME_DESC_BOB + VALID_PHONE_BOB + EMAIL_DESC_BOB + MATRIC_DESC_BOB,
                expectedMessage);

        // missing email prefix
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + VALID_EMAIL_BOB + MATRIC_DESC_BOB,
                expectedMessage);

        // missing address prefix
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + VALID_MATRIC_BOB,
                expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_NAME_BOB + VALID_PHONE_BOB + VALID_EMAIL_BOB + VALID_MATRIC_BOB,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC + PHONE_DESC_BOB + EMAIL_DESC_BOB + MATRIC_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Name.MESSAGE_CONSTRAINTS);

        // invalid phone
        assertParseFailure(parser, NAME_DESC_BOB + INVALID_PHONE_DESC + EMAIL_DESC_BOB + MATRIC_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Phone.MESSAGE_CONSTRAINTS);

        // invalid email
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + INVALID_EMAIL_DESC + MATRIC_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Email.MESSAGE_CONSTRAINTS);

        // invalid address
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + INVALID_MATRIC_DESC
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, MatricNumber.MESSAGE_CONSTRAINTS);

        // invalid tag
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + MATRIC_DESC_BOB
                + INVALID_TAG_DESC + VALID_TAG_FRIEND, Tag.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_NAME_DESC + PHONE_DESC_BOB + EMAIL_DESC_BOB + INVALID_MATRIC_DESC,
                Name.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + MATRIC_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
    }
}
