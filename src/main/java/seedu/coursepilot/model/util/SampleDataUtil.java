package seedu.coursepilot.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.coursepilot.model.AddressBook;
import seedu.coursepilot.model.ReadOnlyAddressBook;
import seedu.coursepilot.model.person.Email;
import seedu.coursepilot.model.person.MatricNumber;
import seedu.coursepilot.model.person.Name;
import seedu.coursepilot.model.person.Phone;
import seedu.coursepilot.model.person.Student;
import seedu.coursepilot.model.tag.Tag;
import seedu.coursepilot.model.tutorial.Tutorial;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Student[] getSamplePersons1() {
        return new Student[] {
            new Student(new Name("Alex Yeoh"), new Phone("87438807"), new Email("alexyeoh@example.com"),
                new MatricNumber("A000000"),
                getTagSet("friends")),
            new Student(new Name("Bernice Yu"), new Phone("99272758"), new Email("berniceyu@example.com"),
                new MatricNumber("A000001"),
                getTagSet("colleagues", "friends")),
        };
    }

    public static Student[] getSamplePersons2() {
        return new Student[] {
            new Student(new Name("Charlotte Oliveiro"), new Phone("93210283"), new Email("charlotte@example.com"),
                new MatricNumber("A000002"),
                getTagSet("neighbours")),
            new Student(new Name("David Li"), new Phone("91031282"), new Email("lidavid@example.com"),
                new MatricNumber("A000003"),
                getTagSet("family")),
        };
    }

    public static Student[] getSamplePersons3() {
        return new Student[] {
            new Student(new Name("Irfan Ibrahim"), new Phone("92492021"), new Email("irfan@example.com"),
                new MatricNumber("A000004"),
                getTagSet("classmates")),
            new Student(new Name("Roy Balakrishnan"), new Phone("92624417"), new Email("royb@example.com"),
                new MatricNumber("A000005"),
                getTagSet("colleagues"))
        };
    }

    public static ObservableList<Tutorial> getSampleTutorials() {
        return FXCollections.observableArrayList(
                new Tutorial("CS2103T-W13", "Monday", "10:00-11:00", 20),
                new Tutorial("CS2103T-W14", "Wednesday", "12:00-13:00", 15),
                new Tutorial("CS2103T-W15", "Friday", "14:00-15:00", 10)
        );
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
        for (Student sampleStudent : getSamplePersons1()) {
            sampleAb.addPerson(sampleStudent);
        }
        return sampleAb;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(Tag::new)
                .collect(Collectors.toSet());
    }

}
