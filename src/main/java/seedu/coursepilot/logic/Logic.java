package seedu.coursepilot.logic;

import java.nio.file.Path;
import java.util.Optional;

import javafx.beans.property.ObjectProperty;
import javafx.collections.ObservableList;
import seedu.coursepilot.commons.core.GuiSettings;
import seedu.coursepilot.logic.commands.CommandResult;
import seedu.coursepilot.logic.commands.exceptions.CommandException;
import seedu.coursepilot.logic.parser.exceptions.ParseException;
import seedu.coursepilot.model.ReadOnlyCoursePilot;
import seedu.coursepilot.model.student.Student;
import seedu.coursepilot.model.tutorial.Tutorial;

/**
 * API of the Logic component
 */
public interface Logic {
    /**
     * Executes the command and returns the result.
     * @param commandText The command as entered by the user.
     * @return the result of the command execution.
     * @throws CommandException If an error occurs during command execution.
     * @throws ParseException If an error occurs during parsing.
     */
    CommandResult execute(String commandText) throws CommandException, ParseException;

    /**
     * Returns the CoursePilot.
     *
     * @see seedu.coursepilot.model.Model#getCoursePilot()
     */
    ReadOnlyCoursePilot getCoursePilot();

    /** Returns an unmodifiable view of the filtered list of students */
    ObservableList<Student> getFilteredStudentList();

    /** Returns an unmodifiable view of the tutorial list. */
    ObservableList<Tutorial> getFilteredTutorialList();

    /**
     * Returns the current operating tutorial, if any.
     */
    Optional<Tutorial> getCurrentOperatingTutorial();

    /**
     * Returns the current operating tutorial, for JavaFX UI.
     */
    ObjectProperty<Tutorial> getCurrentOperatingTutorialProperty();

    /**
     * Returns the user prefs' CoursePilot file path.
     */
    Path getCoursePilotFilePath();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Set the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);
}
