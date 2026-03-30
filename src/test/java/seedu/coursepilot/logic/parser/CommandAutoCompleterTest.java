package seedu.coursepilot.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

/**
 * Unit tests for {@link CommandAutoCompleter}.
 */
public class CommandAutoCompleterTest {

    private final CommandAutoCompleter completer = new CommandAutoCompleter();

    // ==================== Command word suggestions ====================

    @Test
    public void getSuggestions_emptyInput_returnsAllCommands() {
        List<String> result = completer.getSuggestions("");
        assertEquals(List.of("add", "clear", "delete", "edit", "exit", "find", "help", "list", "select"), result);
    }

    @Test
    public void getSuggestions_partialCommand_filtersCorrectly() {
        List<String> result = completer.getSuggestions("a");
        assertEquals(List.of("add"), result);
    }

    @Test
    public void getSuggestions_partialCommandDe_returnsDelete() {
        List<String> result = completer.getSuggestions("de");
        assertEquals(List.of("delete"), result);
    }

    @Test
    public void getSuggestions_partialCommandE_returnsEditAndExit() {
        List<String> result = completer.getSuggestions("e");
        assertEquals(List.of("edit", "exit"), result);
    }

    @Test
    public void getSuggestions_completeCommandNoSpace_returnsEmpty() {
        List<String> result = completer.getSuggestions("add");
        assertTrue(result.isEmpty());
    }

    @Test
    public void getSuggestions_unknownCommand_returnsEmpty() {
        List<String> result = completer.getSuggestions("xyz ");
        assertTrue(result.isEmpty());
    }

    // ==================== Add command suggestions ====================

    @Test
    public void getSuggestions_addSpace_returnsFlags() {
        List<String> result = completer.getSuggestions("add ");
        assertEquals(List.of("-student", "-tutorial"), result);
    }

    @Test
    public void getSuggestions_addPartialFlag_filters() {
        List<String> result = completer.getSuggestions("add -s");
        assertEquals(List.of("-student"), result);
    }

    @Test
    public void getSuggestions_addStudentSpace_returnsStudentPrefixes() {
        List<String> result = completer.getSuggestions("add -student /");
        assertTrue(result.contains("/name"));
        assertTrue(result.contains("/phone"));
        assertTrue(result.contains("/email"));
        assertTrue(result.contains("/matric"));
        assertTrue(result.contains("/tag"));
    }

    @Test
    public void getSuggestions_addTutorialSpace_returnsTutorialPrefixes() {
        List<String> result = completer.getSuggestions("add -tutorial /");
        assertTrue(result.contains("/code"));
        assertTrue(result.contains("/day"));
        assertTrue(result.contains("/timeslot"));
        assertTrue(result.contains("/capacity"));
    }

    @Test
    public void getSuggestions_addStudentWithUsedPrefix_excludesUsed() {
        List<String> result = completer.getSuggestions("add -student /name Alice /");
        assertTrue(!result.contains("/name"));
        assertTrue(result.contains("/phone"));
    }

    @Test
    public void getSuggestions_addStudentTagAlwaysSuggested() {
        List<String> result = completer.getSuggestions("add -student /tag friend /");
        assertTrue(result.contains("/tag"));
    }

    // ==================== Delete command suggestions ====================

    @Test
    public void getSuggestions_deleteSpace_returnsFlags() {
        List<String> result = completer.getSuggestions("delete ");
        assertEquals(List.of("-student", "-tutorial"), result);
    }

    // ==================== List command suggestions ====================

    @Test
    public void getSuggestions_listSpace_returnsFlags() {
        List<String> result = completer.getSuggestions("list ");
        assertEquals(List.of("-student", "-tutorial"), result);
    }

    // ==================== Find command suggestions ====================

    @Test
    public void getSuggestions_findSpace_returnsFindFlags() {
        List<String> result = completer.getSuggestions("find ");
        assertEquals(List.of("/phone", "/email", "/matric"), result);
    }

    // ==================== Edit command suggestions ====================

    @Test
    public void getSuggestions_editWithIndex_returnsPrefixes() {
        List<String> result = completer.getSuggestions("edit 1 /");
        assertTrue(result.contains("/name"));
        assertTrue(result.contains("/phone"));
    }

    @Test
    public void getSuggestions_editWithUsedPrefix_excludesUsed() {
        List<String> result = completer.getSuggestions("edit 1 /name Alice /");
        assertTrue(!result.contains("/name"));
        assertTrue(result.contains("/phone"));
    }

    // ==================== No-arg commands ====================

    @Test
    public void getSuggestions_clearSpace_returnsEmpty() {
        List<String> result = completer.getSuggestions("clear ");
        assertTrue(result.isEmpty());
    }

    @Test
    public void getSuggestions_helpSpace_returnsEmpty() {
        List<String> result = completer.getSuggestions("help ");
        assertTrue(result.isEmpty());
    }

    @Test
    public void getSuggestions_exitSpace_returnsEmpty() {
        List<String> result = completer.getSuggestions("exit ");
        assertTrue(result.isEmpty());
    }

    @Test
    public void getSuggestions_selectSpace_returnsEmpty() {
        List<String> result = completer.getSuggestions("select ");
        assertTrue(result.isEmpty());
    }

    @Test
    public void getSuggestions_nullInput_returnsAllCommands() {
        List<String> result = completer.getSuggestions(null);
        assertEquals(List.of("add", "clear", "delete", "edit", "exit", "find", "help", "list", "select"), result);
    }
}
