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

import seedu.coursepilot.model.CoursePilot;
import seedu.coursepilot.model.student.Student;
import seedu.coursepilot.model.tutorial.Capacity;
import seedu.coursepilot.model.tutorial.Day;
import seedu.coursepilot.model.tutorial.TimeSlot;
import seedu.coursepilot.model.tutorial.Tutorial;
import seedu.coursepilot.model.tutorial.TutorialCode;

/**
 * A utility class containing a list of {@code Student} objects to be used in tests.
 */
public class TypicalStudents {

    public static final Student ALICE = new StudentBuilder().withName("Alice Pauline")
            .withMatriculationNumber("A111111").withEmail("alice@example.com")
            .withPhone("94351253")
            .withTags("friends").build();
    public static final Student BENSON = new StudentBuilder().withName("Benson Meier")
            .withMatriculationNumber("A222222")
            .withEmail("johnd@example.com").withPhone("98765432")
            .withTags("owesMoney", "friends").build();
    public static final Student CARL = new StudentBuilder().withName("Carl Kurz").withPhone("95352563")
            .withEmail("heinz@example.com").withMatriculationNumber("A333333").build();
    public static final Student DANIEL = new StudentBuilder().withName("Daniel Meier").withPhone("87652533")
            .withEmail("cornelia@example.com").withMatriculationNumber("A444444").withTags("friends").build();
    public static final Student ELLE = new StudentBuilder().withName("Elle Meyer").withPhone("9482224")
            .withEmail("werner@example.com").withMatriculationNumber("A555555").build();
    public static final Student FIONA = new StudentBuilder().withName("Fiona Kunz").withPhone("9482427")
            .withEmail("lydia@example.com").withMatriculationNumber("A666666").build();
    public static final Student GEORGE = new StudentBuilder().withName("George Best").withPhone("9482442")
            .withEmail("anna@example.com").withMatriculationNumber("A777777").build();

    // Manually added
    public static final Student HOON = new StudentBuilder().withName("Hoon Meier").withPhone("8482424")
            .withEmail("stefan@example.com").withMatriculationNumber("A888888").build();
    public static final Student IDA = new StudentBuilder().withName("Ida Mueller").withPhone("8482131")
            .withEmail("hans@example.com").withMatriculationNumber("A999999").build();

    // Manually added - Student's details found in {@code CommandTestUtil}
    public static final Student AMY = new StudentBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
            .withEmail(VALID_EMAIL_AMY).withMatriculationNumber(VALID_MATRIC_AMY).withTags(VALID_TAG_FRIEND).build();
    public static final Student BOB = new StudentBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
            .withEmail(VALID_EMAIL_BOB).withMatriculationNumber(VALID_MATRIC_BOB)
            .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalStudents() {} // prevents instantiation

    /**
     * Returns an {@code CoursePilot} with all the typical students and a typical tutorial.
     */
    public static CoursePilot getTypicalCoursePilot() {
        CoursePilot ab = new CoursePilot();
        for (Student student : getTypicalStudents()) {
            ab.addStudent(student);
        }
        Tutorial tutorial = new Tutorial(new TutorialCode("CS2103T-W13"), new Day("Wed"),
                new TimeSlot("13:00-14:00"), new Capacity(10));
        for (Student student : getTypicalStudents()) {
            tutorial.addStudent(student);
        }
        ab.addTutorial(tutorial);
        return ab;
    }

    public static List<Student> getTypicalStudents() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }
}
