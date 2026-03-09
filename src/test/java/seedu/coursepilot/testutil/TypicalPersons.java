package seedu.coursepilot.testutil;

import static seedu.coursepilot.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.coursepilot.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.coursepilot.logic.commands.CommandTestUtil.VALID_MATRIC_AMY;
import static seedu.coursepilot.logic.commands.CommandTestUtil.VALID_MATRIC_BOB;
import static seedu.coursepilot.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.coursepilot.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.coursepilot.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.coursepilot.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.coursepilot.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.coursepilot.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.coursepilot.model.AddressBook;
import seedu.coursepilot.model.person.Student;

/**
 * A utility class containing a list of {@code Person} objects to be used in tests.
 */
public class TypicalPersons {

    public static final Student ALICE = new PersonBuilder().withName("Alice Pauline")
            .withMatriculationNumber("A111111").withEmail("alice@example.com")
            .withPhone("94351253")
            .withTags("friends").build();
    public static final Student BENSON = new PersonBuilder().withName("Benson Meier")
            .withMatriculationNumber("A222222")
            .withEmail("johnd@example.com").withPhone("98765432")
            .withTags("owesMoney", "friends").build();
    public static final Student CARL = new PersonBuilder().withName("Carl Kurz").withPhone("95352563")
            .withEmail("heinz@example.com").withMatriculationNumber("A333333").build();
    public static final Student DANIEL = new PersonBuilder().withName("Daniel Meier").withPhone("87652533")
            .withEmail("cornelia@example.com").withMatriculationNumber("A444444").withTags("friends").build();
    public static final Student ELLE = new PersonBuilder().withName("Elle Meyer").withPhone("9482224")
            .withEmail("werner@example.com").withMatriculationNumber("A555555").build();
    public static final Student FIONA = new PersonBuilder().withName("Fiona Kunz").withPhone("9482427")
            .withEmail("lydia@example.com").withMatriculationNumber("A666666").build();
    public static final Student GEORGE = new PersonBuilder().withName("George Best").withPhone("9482442")
            .withEmail("anna@example.com").withMatriculationNumber("A777777").build();

    // Manually added
    public static final Student HOON = new PersonBuilder().withName("Hoon Meier").withPhone("8482424")
            .withEmail("stefan@example.com").withMatriculationNumber("A888888").build();
    public static final Student IDA = new PersonBuilder().withName("Ida Mueller").withPhone("8482131")
            .withEmail("hans@example.com").withMatriculationNumber("A999999").build();

    // Manually added - Person's details found in {@code CommandTestUtil}
    public static final Student AMY = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
            .withEmail(VALID_EMAIL_AMY).withMatriculationNumber(VALID_MATRIC_AMY).withTags(VALID_TAG_FRIEND).build();
    public static final Student BOB = new PersonBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
            .withEmail(VALID_EMAIL_BOB).withMatriculationNumber(VALID_MATRIC_BOB)
            .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalPersons() {} // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical persons.
     */
    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        for (Student student : getTypicalPersons()) {
            ab.addPerson(student);
        }
        return ab;
    }

    public static List<Student> getTypicalPersons() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }
}
