package seedu.coursepilot.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.coursepilot.logic.parser.CliSyntax.PREFIX_CAPACITY;
import static seedu.coursepilot.logic.parser.CliSyntax.PREFIX_DAY;
import static seedu.coursepilot.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.coursepilot.logic.parser.CliSyntax.PREFIX_MATRICNUMBER;
import static seedu.coursepilot.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.coursepilot.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.coursepilot.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.coursepilot.logic.parser.CliSyntax.PREFIX_TIMESLOT;
import static seedu.coursepilot.logic.parser.CliSyntax.PREFIX_TUTORIALCODE;

import seedu.coursepilot.commons.util.ToStringBuilder;
import seedu.coursepilot.logic.Messages;
import seedu.coursepilot.logic.commands.exceptions.CommandException;
import seedu.coursepilot.model.Model;
import seedu.coursepilot.model.person.Student;
import seedu.coursepilot.model.tutorial.Tutorial;
/**
 * Adds a person to the address book.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + " -student: Adds a person to the address book. "
            + "Parameters: add -student "
            + PREFIX_NAME + " NAME "
            + PREFIX_PHONE + " PHONE "
            + PREFIX_EMAIL + " EMAIL "
            + PREFIX_MATRICNUMBER + " MATRICNUMBER "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " -student "
            + PREFIX_NAME + " John Doe "
            + PREFIX_PHONE + " 98765432 "
            + PREFIX_EMAIL + " johnd@example.com "
            + PREFIX_MATRICNUMBER + " A000000 "
            + PREFIX_TAG + " friends "
            + PREFIX_TAG + " owesMoney"
            + "\nAlternatively, \n"
            + COMMAND_WORD + " -tutorial: Adds a tutorial to the address book. "
            + "\nParameters: add -tutorial "
            + PREFIX_TUTORIALCODE + " CODE "
            + PREFIX_DAY + " DAY "
            + PREFIX_TIMESLOT + " TIMESLOT "
            + PREFIX_CAPACITY + " CAPACITY "
            + "\nExample: " + COMMAND_WORD + " -tutorial "
            + PREFIX_TUTORIALCODE + " CS2103T-W13 "
            + PREFIX_DAY + " Wed "
            + PREFIX_TIMESLOT + " 1pm-2pm "
            + PREFIX_CAPACITY + " 10 ";

    public static final String MESSAGE_SUCCESS_STUDENT = "New student added: %1$s";
    public static final String MESSAGE_SUCCESS_TUTORIAL = "New tutorial added: %1$s";
    public static final String MESSAGE_DUPLICATE_PERSON =
            "This person or matriculation number already exists in the address book";
    public static final String MESSAGE_DUPLICATE_TUTORIAL =
            "This tutorial already exists in the system";
    public static final String MESSAGE_NO_CURRENT_OPERATING_TUTORIAL =
            "No current operating tutorial selected. Use find first.";

    private final Student toAdd;
    private final Tutorial tutorialToAdd;

    /**
     * Specifies the target of the add command.
     */
    public enum AddTarget {
        STUDENT,
        TUTORIAL
    }

    private final AddTarget addTarget;

    /**
     * Creates an AddCommand to add the specified {@code Person}
     */
    public AddCommand(Student student) {
        requireNonNull(student);
        toAdd = student;
        addTarget = AddTarget.STUDENT;
        tutorialToAdd = null;
    }

    /**
     * Creates an AddCommand to add the specified {@code Tutorial}
     */
    public AddCommand(Tutorial tutorial) {
        requireNonNull(tutorial);
        tutorialToAdd = tutorial;
        addTarget = AddTarget.TUTORIAL;
        toAdd = null;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (addTarget == AddTarget.STUDENT) {
            if (model.getCurrentOperatingTutorial().isEmpty()) {
                throw new CommandException(MESSAGE_NO_CURRENT_OPERATING_TUTORIAL);
            }

            Tutorial currentOperatingTutorial = model.getCurrentOperatingTutorial()
                    .orElseThrow(() -> new CommandException(MESSAGE_NO_CURRENT_OPERATING_TUTORIAL));

            if (!model.hasPerson(toAdd)) {
                model.addPerson(toAdd);
            }

            if (!currentOperatingTutorial.hasStudent(toAdd)) {
                currentOperatingTutorial.addStudent(toAdd);
                return new CommandResult(String.format(MESSAGE_SUCCESS_STUDENT, Messages.format(toAdd)));
            }
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }
        if (addTarget == AddTarget.TUTORIAL) {
            model.addTutorial(tutorialToAdd);
            return new CommandResult(String.format(MESSAGE_SUCCESS_TUTORIAL, Messages.format(tutorialToAdd)));
        }
        throw new CommandException("Unexpected error, add command was not done on student or tutorial");
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddCommand)) {
            return false;
        }

        AddCommand otherAddCommand = (AddCommand) other;
        if (this.addTarget == AddTarget.STUDENT) {
            return toAdd.equals(otherAddCommand.toAdd);
        } else if (this.addTarget == AddTarget.TUTORIAL) {
            return tutorialToAdd.equals(otherAddCommand.tutorialToAdd);
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("toAdd", toAdd)
                .toString();
    }
}
