package seedu.coursepilot.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.coursepilot.commons.exceptions.IllegalValueException;
import seedu.coursepilot.model.person.Student;
import seedu.coursepilot.model.tutorial.Tutorial;

/**
 * Jackson-friendly version of {@link Tutorial}.
 */
class JsonAdaptedTutorial {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Tutorial's %s field is missing!";

    private final String tutorialCode;
    private final String day;
    private final String timeSlot;
    private final int capacity;
    private final List<JsonAdaptedPerson> students = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedTutorial} with the given tutorial details.
     */
    @JsonCreator
    public JsonAdaptedTutorial(@JsonProperty("tutorialCode") String tutorialCode, @JsonProperty("day") String day,
                             @JsonProperty("timeSlot") String timeSlot, @JsonProperty("capacity") int capacity,
                               @JsonProperty("students") List<JsonAdaptedPerson> students) {
        this.tutorialCode = tutorialCode;
        this.day = day;
        this.timeSlot = timeSlot;
        this.capacity = capacity;
        if (students != null) {
            this.students.addAll(students);
        }
    }

    /**
     * Converts a given {@code Tutorial} into this class for Jackson use.
     */
    public JsonAdaptedTutorial(Tutorial source) {
        tutorialCode = source.getTutorialCode();
        day = source.getDay();
        timeSlot = source.getTimeSlot();
        capacity = source.getCapacity();
        students.addAll(source.getStudents().stream()
                .map(JsonAdaptedPerson::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this Jackson-friendly adapted tutorial object into the model's {@code Tutorial} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted tutorial.
     */
    public Tutorial toModelType() throws IllegalValueException {

        if (tutorialCode == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "tutorialCode"));
        }

        if (day == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "day"));
        }

        if (timeSlot == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "timeSlot"));
        }

        List<Student> modelStudents = new ArrayList<>();
        for (JsonAdaptedPerson jsonStudent : students) {
            modelStudents.add(jsonStudent.toModelType());
        }

        Tutorial tutorial = new Tutorial(tutorialCode, day, timeSlot, capacity);

        for (Student student : modelStudents) {
            tutorial.addStudent(student);
        }

        return tutorial;
    }

}
