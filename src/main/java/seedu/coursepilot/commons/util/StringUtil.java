package seedu.coursepilot.commons.util;

import static java.util.Objects.requireNonNull;
import static seedu.coursepilot.commons.util.AppUtil.checkArgument;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;

/**
 * Helper functions for handling strings.
 */
public class StringUtil {

    /**
     * Returns true if the {@code sentence} contains the {@code word}.
     *   Ignores case, but a full word match is required.
     *   <br>examples:<pre>
     *       containsWordIgnoreCase("ABc def", "abc") == true
     *       containsWordIgnoreCase("ABc def", "DEF") == true
     *       containsWordIgnoreCase("ABc def", "AB") == false //not a full word match
     *       </pre>
     * @param sentence cannot be null
     * @param word cannot be null, cannot be empty, must be a single word
     */
    public static boolean containsWordIgnoreCase(String sentence, String word) {
        requireNonNull(sentence);
        requireNonNull(word);

        String preppedWord = word.trim();
        checkArgument(!preppedWord.isEmpty(), "Word parameter cannot be empty");
        checkArgument(preppedWord.split("\\s+").length == 1, "Word parameter should be a single word");

        String preppedSentence = sentence;
        String[] wordsInPreppedSentence = preppedSentence.split("\\s+");

        return Arrays.stream(wordsInPreppedSentence)
                .anyMatch(preppedWord::equalsIgnoreCase);
    }

    /**
     * Returns true if the {@code sentence} contains the {@code word}.
     *   Ignores case.
     *   <br>examples:<pre>
     *       containsWordIgnoreCase("ABc def", "abc") == true
     *       containsWordIgnoreCase("ABc def", "DEF") == true
     *       containsWordIgnoreCase("ABc def", "AB") == false //not a full word match
     *       </pre>
     * @param word cannot be null
     * @param substring cannot be null, cannot be empty, must be a single word
     */
    public static boolean containsPartWordIgnoreCase(String word, String substring) {
        requireNonNull(word);
        requireNonNull(substring);

        String preppedWord = word.trim();
        String preppedSubstring = substring.trim();
        checkArgument(preppedWord.split("\\s+").length == 1, "Word parameter should be a single word");
        checkArgument(!preppedSubstring.isEmpty(), "Substring parameter cannot be empty");
        checkArgument(preppedSubstring.split("\\s+").length == 1, "Substring parameter should be a single substring");

        return preppedWord.contains(preppedSubstring);
    }

    /**
     * Returns true if the {@code value} starts with {@code prefix}.
     *   Ignores cases.
     * @param word cannot be null
     * @param prefix cannot be null, cannot be empty, must be a single prefix
     */
    public static boolean startsWithString(String word, String prefix) {
        requireNonNull(word);
        requireNonNull(prefix);

        String preppedWord = word.trim();
        String preppedPrefix = prefix.trim();
        checkArgument(preppedWord.split("\\s+").length == 1, "Word parameter should be a single word");
        checkArgument(!preppedPrefix.isEmpty(), "Prefix parameter cannot be empty");
        checkArgument(preppedPrefix.split("\\s+").length == 1, "Prefix parameter should be a single substring");

        return preppedWord.startsWith(preppedPrefix);
    }

    /**
     * Returns a detailed message of the t, including the stack trace.
     */
    public static String getDetails(Throwable t) {
        requireNonNull(t);
        StringWriter sw = new StringWriter();
        t.printStackTrace(new PrintWriter(sw));
        return t.getMessage() + "\n" + sw.toString();
    }

    /**
     * Returns true if {@code s} represents a non-zero unsigned integer
     * e.g. 1, 2, 3, ..., {@code Integer.MAX_VALUE} <br>
     * Will return false for any other non-null string input
     * e.g. empty string, "-1", "0", "+1", and " 2 " (untrimmed), "3 0" (contains whitespace), "1 a" (contains letters)
     * @throws NullPointerException if {@code s} is null.
     */
    public static boolean isNonZeroUnsignedInteger(String s) {
        requireNonNull(s);

        try {
            int value = Integer.parseInt(s);
            return value > 0 && !s.startsWith("+"); // "+1" is successfully parsed by Integer#parseInt(String)
        } catch (NumberFormatException nfe) {
            return false;
        }
    }
}
