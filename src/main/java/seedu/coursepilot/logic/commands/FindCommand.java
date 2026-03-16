package seedu.coursepilot.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Arrays;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import seedu.coursepilot.commons.util.ToStringBuilder;
import seedu.coursepilot.logic.Messages;
import seedu.coursepilot.logic.commands.exceptions.CommandException;
import seedu.coursepilot.model.Model;
import seedu.coursepilot.model.person.Student;

/**
 * Finds and lists all students in coursepilot whose name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindCommand extends Command {

    /**
     * Specifies the valid flags of FindCommand
     */
    public enum Flag {
        PHONE("/phone"),
        EMAIL("/email"),
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

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons whose names contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " alice bob charlie";

    public static final String MESSAGE_USAGE_FLAG = COMMAND_WORD + ": Valid flags are: "
            + Flag.validFlagsString() + "\n"
            + "Example: " + COMMAND_WORD + " /email @u.nus.edu @gmail";

    public static final String MESSAGE_NO_CURRENT_OPERATING_TUTORIAL =
        "No current operating tutorial selected. Use select first.";

    private final Predicate<Student> predicate;

    public FindCommand(Predicate<Student> predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.getCurrentOperatingTutorial().isEmpty()) {
            throw new CommandException(MESSAGE_NO_CURRENT_OPERATING_TUTORIAL);
        }

        model.updateFilteredPersonList(
            student -> predicate.test(student)
                    && model.getCurrentOperatingTutorial()
                            .map(tutorial -> tutorial.hasStudent(student))
                            .orElse(false));
        return new CommandResult(
                String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, model.getFilteredPersonList().size()));
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
