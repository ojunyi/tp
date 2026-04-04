package seedu.coursepilot.model.tutorial;

import static java.util.Objects.requireNonNull;
import static seedu.coursepilot.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import seedu.coursepilot.model.student.Student;

/**
 * Represents a tutorial in CoursePilot.
 * A tutorial has a code, scheduled day, time slot, a maximum capacity.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Tutorial {
    private final TutorialCode tutorialCode;
    private final Day day;
    private final TimeSlot timeSlot;
    private final Capacity capacity;
    private final List<Student> students = new ArrayList<>();

    /**
     * Constructs a {@code Tutorial}.
     *
     * @param tutorialCode unique code identifying the tutorial
     * @param day the day the tutorial is held
     * @param timeSlot the time slot of the tutorial
     * @param capacity the maximum capacity of the tutorial
     */
    public Tutorial(
        TutorialCode tutorialCode, Day day, TimeSlot timeSlot, Capacity capacity) {
        requireAllNonNull(tutorialCode, day, timeSlot, capacity);

        this.tutorialCode = tutorialCode;
        this.day = day;
        this.timeSlot = timeSlot;
        this.capacity = capacity;
    }

    /**
     * Returns the tutorial code.
     */
    public String getTutorialCode() {
        return tutorialCode.value;
    }

    /**
     * Returns the day the tutorial takes place.
     */
    public String getDay() {
        return day.value;
    }

    /**
     * Returns the time slot of the tutorial.
     */
    public String getTimeSlot() {
        return timeSlot.value;
    }

    /**
     * Returns the maximum capacity of the tutorial.
     */
    public int getCapacity() {
        return capacity.value;
    }

    /**
     * Returns the list of students in this tutorial.
     */
    public List<Student> getStudents() {
        return students;
    }

    /**
     * Returns true if this tutorial contains a student with the same identity as {@code Student}
     */
    public boolean hasStudent(Student student) {
        requireNonNull(student);
        return students.stream().anyMatch(student::isSameStudent);
    }
    /**
     * Adds a student to the studentList belonging to this particular tutorial instance
     *
     * @param student the student to be added
     * @throws IllegalStateException if the tutorial is at full capacity
     */
    public void addStudent(Student student) {
        requireNonNull(student);
        if (isFull()) {
            throw new IllegalStateException("Tutorial is at full capacity");
        }
        this.students.add(student);
    }

    /**
     * Edits a student from this tutorial's student list.
     * The student must exist in the tutorial.
     *
     * @param matric the old matric number of the student to be edited
     * @param editedStudent the student to be removed
     */
    public void editStudent(String matric, Student editedStudent) {
        requireNonNull(editedStudent);
        for (int i = 0; i < this.students.size(); i++) {
            Student current = this.students.get(i);

            if (current.getMatriculationNumber().toString().equals(matric)) {
                this.students.set(i, editedStudent);
            }
        }
    }

    /**
     * Removes a student from this tutorial's student list.
     * The student must exist in the tutorial.
     *
     * @param student the student to be removed
     * @return true if the student was removed, false if not found
     */
    public boolean removeStudent(Student student) {
        requireNonNull(student);
        return students.removeIf(s -> s.isSameStudent(student));
    }
    /**
     * Returns true if the tutorial is at full capacity.
     */
    public boolean isFull() {
        return students.size() >= capacity.value;
    }

    /**
     * Returns true if both students have the same tutorial code.
     * This defines a weaker notion of equality between two tutorials.
     */
    public boolean isSameTutorial(Tutorial other) {
        if (other == this) {
            return true;
        }
        return other != null && this.tutorialCode.equals(other.tutorialCode);
    }

    /**
     * Returns true if both tutorials have the same fields.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Tutorial)) {
            return false;
        }

        Tutorial otherTutorial = (Tutorial) other;
        return tutorialCode.equals(otherTutorial.tutorialCode)
            && day.equals(otherTutorial.day)
            && timeSlot.equals(otherTutorial.timeSlot)
            && capacity.equals(otherTutorial.capacity);
    }

    /**
     * Returns the hash code based on tutorial attributes.
     */
    @Override
    public int hashCode() {
        return Objects.hash(tutorialCode, day, timeSlot, capacity);
    }
}
