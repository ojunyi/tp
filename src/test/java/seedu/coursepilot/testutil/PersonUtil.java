package seedu.coursepilot.testutil;

import static seedu.coursepilot.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.coursepilot.logic.parser.CliSyntax.PREFIX_MATRICNUMBER;
import static seedu.coursepilot.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.coursepilot.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.coursepilot.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Set;

import seedu.coursepilot.logic.commands.AddCommand;
import seedu.coursepilot.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.coursepilot.model.person.Student;
import seedu.coursepilot.model.tag.Tag;

/**
 * A utility class for Person.
 */
public class PersonUtil {

    /**
     * Returns an add command string for adding the {@code person}.
     */
    public static String getAddCommand(Student student) {
        return AddCommand.COMMAND_WORD + " -student " + getPersonDetails(student);
    }

    /**
     * Returns the part of command string for the given {@code person}'s details.
     */
    public static String getPersonDetails(Student student) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_NAME + student.getName().fullName + " ");
        sb.append(PREFIX_PHONE + student.getPhone().value + " ");
        sb.append(PREFIX_EMAIL + student.getEmail().value + " ");
        sb.append(PREFIX_MATRICNUMBER + student.getMatriculationNumber().matricNumber + " ");
        student.getTags().stream().forEach(
            s -> sb.append(PREFIX_TAG + s.tagName + " ")
        );
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code EditPersonDescriptor}'s details.
     */
    public static String getEditPersonDescriptorDetails(EditPersonDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getName().ifPresent(name -> sb.append(PREFIX_NAME).append(name.fullName).append(" "));
        descriptor.getPhone().ifPresent(phone -> sb.append(PREFIX_PHONE).append(phone.value).append(" "));
        descriptor.getEmail().ifPresent(email -> sb.append(PREFIX_EMAIL).append(email.value).append(" "));
        descriptor.getMatriculationNumber().ifPresent(matric -> sb.append(PREFIX_MATRICNUMBER)
                .append(matric.matricNumber).append(" "));
        if (descriptor.getTags().isPresent()) {
            Set<Tag> tags = descriptor.getTags().get();
            if (tags.isEmpty()) {
                sb.append(PREFIX_TAG);
            } else {
                tags.forEach(s -> sb.append(PREFIX_TAG).append(s.tagName).append(" "));
            }
        }
        return sb.toString();
    }
}
