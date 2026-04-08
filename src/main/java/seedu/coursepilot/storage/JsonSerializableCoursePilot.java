package seedu.coursepilot.storage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.coursepilot.commons.exceptions.IllegalValueException;
import seedu.coursepilot.model.CoursePilot;
import seedu.coursepilot.model.ReadOnlyCoursePilot;
import seedu.coursepilot.model.student.Student;
import seedu.coursepilot.model.tutorial.Tutorial;

/**
 * An Immutable CoursePilot that is serializable to JSON format.
 */
@JsonRootName(value = "addressbook")
class JsonSerializableCoursePilot {

    public static final String MESSAGE_DUPLICATE_STUDENT = "Students list contains duplicate student(s).";
    public static final String MESSAGE_DUPLICATE_STUDENT_CONTACT =
        "Students list contains duplicate phone number(s) or email(s).";
    public static final String MESSAGE_DUPLICATE_TUTORIAL = "Tutorials list contains duplicate tutorial(s).";
    public static final String MESSAGE_DUPLICATE_TUTORIAL_STUDENT =
        "Tutorials list contains duplicate student(s) within a tutorial.";
    public static final String MESSAGE_TUTORIAL_STUDENT_NOT_IN_STUDENT_LIST =
        "Tutorials list contains student(s) not present in students list.";
    public static final String MESSAGE_TUTORIAL_STUDENT_DETAILS_MISMATCH =
        "Tutorials list contains student(s) with details inconsistent with students list.";
    public static final String MESSAGE_STUDENT_NOT_ASSIGNED_TO_TUTORIAL =
        "Students list contains student(s) not assigned to any tutorial.";

    private final List<JsonAdaptedStudent> students = new ArrayList<>();
    private final List<JsonAdaptedTutorial> tutorials = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableCoursePilot} with the given students.
     */
    @JsonCreator
    public JsonSerializableCoursePilot(@JsonProperty("students") List<JsonAdaptedStudent> students,
                                       @JsonProperty("tutorials") List<JsonAdaptedTutorial> tutorials) {
        if (students != null) {
            this.students.addAll(students);
        }
        if (tutorials != null) {
            this.tutorials.addAll(tutorials);
        }
    }

    /**
     * Converts a given {@code ReadOnlyCoursePilot} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableCoursePilot}.
     */
    public JsonSerializableCoursePilot(ReadOnlyCoursePilot source) {
        students.addAll(source.getStudentList().stream().map(JsonAdaptedStudent::new).collect(Collectors.toList()));
        tutorials.addAll(source.getTutorialList().stream().map(JsonAdaptedTutorial::new).collect(Collectors.toList()));
    }

    /**
     * Converts this course pilot into the model's {@code CoursePilot} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public CoursePilot toModelType() throws IllegalValueException {
        CoursePilot coursePilot = new CoursePilot();
        List<Student> studentsInCoursePilot = new ArrayList<>();

        for (JsonAdaptedStudent jsonAdaptedStudent : students) {
            Student student = jsonAdaptedStudent.toModelType();
            if (coursePilot.hasStudent(student)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_STUDENT);
            }
            if (hasDuplicateContact(studentsInCoursePilot, student)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_STUDENT_CONTACT);
            }
            coursePilot.addStudent(student);
            studentsInCoursePilot.add(student);
        }

        Map<String, Student> studentsByMatric = new HashMap<>();
        for (Student student : studentsInCoursePilot) {
            studentsByMatric.put(student.getMatriculationNumber().toString(), student);
        }

        Set<String> matricNumbersAssignedToTutorials = new HashSet<>();
        for (JsonAdaptedTutorial jsonAdaptedTutorial : tutorials) {
            Tutorial tutorial = jsonAdaptedTutorial.toModelType();
            validateTutorialStudents(tutorial, studentsByMatric, matricNumbersAssignedToTutorials);
            if (coursePilot.hasTutorial(tutorial)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_TUTORIAL);
            }
            coursePilot.addTutorial(tutorial);
        }

        if (hasUnassignedStudent(studentsInCoursePilot, matricNumbersAssignedToTutorials)) {
            throw new IllegalValueException(MESSAGE_STUDENT_NOT_ASSIGNED_TO_TUTORIAL);
        }

        return coursePilot;
    }

    private static boolean hasDuplicateContact(List<Student> existingStudents, Student studentToAdd) {
        return existingStudents.stream().anyMatch(existingStudent ->
                existingStudent.getPhone().equals(studentToAdd.getPhone())
                        || existingStudent.getEmail().equals(studentToAdd.getEmail()));
    }

    private static void validateTutorialStudents(Tutorial tutorial, Map<String, Student> studentsByMatric,
                                                 Set<String> assignedMatricNumbers) throws IllegalValueException {
        Set<String> seenMatricNumbers = new HashSet<>();
        for (Student tutorialStudent : tutorial.getStudents()) {
            String tutorialStudentMatric = tutorialStudent.getMatriculationNumber().toString();

            if (!seenMatricNumbers.add(tutorialStudentMatric)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_TUTORIAL_STUDENT);
            }

            Student studentFromStudentsList = studentsByMatric.get(tutorialStudentMatric);
            if (studentFromStudentsList == null) {
                throw new IllegalValueException(MESSAGE_TUTORIAL_STUDENT_NOT_IN_STUDENT_LIST);
            }

            if (!studentFromStudentsList.equals(tutorialStudent)) {
                throw new IllegalValueException(MESSAGE_TUTORIAL_STUDENT_DETAILS_MISMATCH);
            }

            assignedMatricNumbers.add(tutorialStudentMatric);
        }
    }

    private static boolean hasUnassignedStudent(
            List<Student> studentsInCoursePilot, Set<String> assignedMatricNumbers) {
        return studentsInCoursePilot.stream()
                .map(student -> student.getMatriculationNumber().toString())
                .anyMatch(matriculationNumber -> !assignedMatricNumbers.contains(matriculationNumber));
    }

}
