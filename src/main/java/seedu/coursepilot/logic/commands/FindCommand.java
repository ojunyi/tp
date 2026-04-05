package seedu.coursepilot.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Arrays;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import seedu.coursepilot.commons.util.ToStringBuilder;
import seedu.coursepilot.logic.Messages;
import seedu.coursepilot.logic.commands.exceptions.CommandException;
import seedu.coursepilot.model.Model;
import seedu.coursepilot.model.student.Student;

/**
 * Finds and lists all students in coursepilot whose name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindCommand extends Command {

    /**
     * Specifies the valid flags of FindCommand
     */
    public enum Flag {
        /** Matches students by phone number prefix. */
        PHONE("/phone"),
        /** Matches students by email substring. */
        EMAIL("/email"),
        /** Matches students by matric number prefix. */
        MATRIC("/matric");

        private final String value;

        Flag(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        /**
         * Returns the {@code Flag} constant whose value matches the given string, or {@code null} if no match is found.
         * Matching is case-insensitive.
         *
         * @param value the flag string to match against (e.g. {@code "/email"})
         * @return the matching {@code Flag}, or {@code null} if unrecognised
         */
        public static Flag fromString(String value) {
            for (Flag flag : Flag.values()) {
                if (flag.value.equalsIgnoreCase(value)) {
                    return flag;
                }
            }
            return null;
        }

        /**
         * Returns a formatted string of all valid flag values, for use in user-facing messages.
         *
         * @return a comma-separated string of all valid flag values (e.g. {@code "/phone, /email, /matric"})
         */
        public static String validFlagsString() {
            return Arrays.stream(Flag.values())
                    .map(Flag::getValue)
                    .collect(Collectors.joining(", "));
        }
    }

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Finds students by name or by field prefix.\n"
            + "Prefixes: /phone, /email, /matric\n"
            + "Parameters: [PREFIX] KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " John\n"
            + "Example: " + COMMAND_WORD + " /email @u.nus.edu";

    public static final String MESSAGE_USAGE_FLAG = COMMAND_WORD + ": Valid flags are: "
            + Flag.validFlagsString() + "\n"
            + "Example: " + COMMAND_WORD + " /email @u.nus.edu @gmail";

    private final Predicate<Student> predicate;

    /**
     * Creates a FindCommand that filters students using the given {@code predicate}.
     */
    public FindCommand(Predicate<Student> predicate) {
        requireNonNull(predicate);
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        assert predicate != null;

        if (model.getCurrentOperatingTutorial().isEmpty()) {
            model.updateFilteredStudentList(predicate);
            assert model.getFilteredStudentList() != null;
            return new CommandResult(
                String.format(Messages.MESSAGE_STUDENTS_LISTED_OVERVIEW,
                        model.getFilteredStudentList().size()));
        }

        model.updateFilteredStudentList(
            student -> predicate.test(student) && model.isStudentInCurrentTutorial(student));
        assert model.getFilteredStudentList() != null;
        return new CommandResult(
                String.format(Messages.MESSAGE_STUDENTS_LISTED_OVERVIEW, model.getFilteredStudentList().size()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof FindCommand)) {
            return false;
        }

        FindCommand otherFindCommand = (FindCommand) other;
        return predicate.equals(otherFindCommand.predicate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicate", predicate)
                .toString();
    }
}
