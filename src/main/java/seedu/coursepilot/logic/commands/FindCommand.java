package seedu.coursepilot.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.function.Predicate;

import seedu.coursepilot.commons.util.ToStringBuilder;
import seedu.coursepilot.logic.Messages;
import seedu.coursepilot.model.Model;
import seedu.coursepilot.model.person.Student;
import seedu.coursepilot.model.person.TutorialKeywordPredicate;
import seedu.coursepilot.model.tutorial.Tutorial;

/**
 * Finds and lists all students in coursepilot whose name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons whose names contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " alice bob charlie";

    private final Predicate<Student> predicate;

    public FindCommand(Predicate<Student> predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredPersonList(predicate);

        // Temporary fix to navigate tutorial slots
        // TODO: Move this section to a Select Command
        if (predicate instanceof TutorialKeywordPredicate) {
            TutorialKeywordPredicate tutorialPredicate = (TutorialKeywordPredicate) predicate;
            String tutorialKeyword = tutorialPredicate.getKeywords().get(1);
            System.out.println("Tutorial keyword: " + tutorialKeyword);
            Tutorial tutorial = model.getTutorialList().stream()
                .filter(tut -> tut.getTutorialCode().contains(tutorialKeyword))
                .findFirst()
                .orElse(model.getTutorialList().get(0));
            model.setCurrentOperatingTutorial(tutorial);
        }

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
