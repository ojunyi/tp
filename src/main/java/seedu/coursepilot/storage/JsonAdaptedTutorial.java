package seedu.coursepilot.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.coursepilot.commons.exceptions.IllegalValueException;
import seedu.coursepilot.model.student.Student;
import seedu.coursepilot.model.tutorial.Capacity;
import seedu.coursepilot.model.tutorial.Day;
import seedu.coursepilot.model.tutorial.TimeSlot;
import seedu.coursepilot.model.tutorial.Tutorial;
import seedu.coursepilot.model.tutorial.TutorialCode;

/**
 * Jackson-friendly version of {@link Tutorial}.
 */
class JsonAdaptedTutorial {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Tutorial's %s field is missing!";
    public static final String MESSAGE_TUTORIAL_EXCEEDS_CAPACITY =
            "Tutorial contains more students than its declared capacity.";

    private final String tutorialCode;
    private final String day;
    private final String timeSlot;
    private final int capacity;
    private final List<JsonAdaptedStudent> students = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedTutorial} with the given tutorial details.
     */
    @JsonCreator
    public JsonAdaptedTutorial(@JsonProperty("tutorialCode") String tutorialCode, @JsonProperty("day") String day,
                             @JsonProperty("timeSlot") String timeSlot, @JsonProperty("capacity") int capacity,
                               @JsonProperty("students") List<JsonAdaptedStudent> students) {
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
                .map(JsonAdaptedStudent::new)
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
        if (!TutorialCode.isValidTutorialCode(tutorialCode)) {
            throw new IllegalValueException(TutorialCode.MESSAGE_CONSTRAINTS);
        }
        final TutorialCode modelTutorialCode = new TutorialCode(tutorialCode);

        if (day == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "day"));
        }
        if (!Day.isValidDay(day)) {
            throw new IllegalValueException(Day.MESSAGE_CONSTRAINTS);
        }
        final Day modelDay = new Day(day);

        if (timeSlot == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "timeSlot"));
        }
        if (!TimeSlot.isValidTimeSlot(timeSlot)) {
            throw new IllegalValueException(TimeSlot.MESSAGE_CONSTRAINTS);
        }
        final TimeSlot modelTimeSlot = new TimeSlot(timeSlot);

        if (!Capacity.isValidCapacity(capacity)) {
            throw new IllegalValueException(Capacity.MESSAGE_CONSTRAINTS);
        }
        final Capacity modelCapacity = new Capacity(capacity);

        List<Student> modelStudents = new ArrayList<>();
        for (JsonAdaptedStudent jsonStudent : students) {
            modelStudents.add(jsonStudent.toModelType());
        }

        if (modelStudents.size() > modelCapacity.getValue()) {
            throw new IllegalValueException(MESSAGE_TUTORIAL_EXCEEDS_CAPACITY);
        }

        Tutorial tutorial = new Tutorial(modelTutorialCode, modelDay, modelTimeSlot, modelCapacity);

        for (Student student : modelStudents) {
            tutorial.addStudent(student);
        }

        return tutorial;
    }

}
