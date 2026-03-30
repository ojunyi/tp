package seedu.coursepilot.ui;

import java.util.logging.Logger;

import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.coursepilot.commons.core.LogsCenter;
import seedu.coursepilot.model.student.Student;
import seedu.coursepilot.model.tutorial.Tutorial;

/**
 * Panel containing the list of students.
 */
public class StudentListPanel extends UiPart<Region> {
    private static final String FXML = "StudentListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(StudentListPanel.class);

    @FXML
    private ListView<Student> studentListView;

    private final ObservableList<Tutorial> tutorialList;

    /**
     * Creates a {@code StudentListPanel} with the given {@code ObservableList}.
     */
    public StudentListPanel(ObservableList<Student> studentList, ObservableList<Tutorial> tutorialList) {
        super(FXML);
        this.tutorialList = tutorialList;
        studentListView.setItems(studentList);
        studentListView.setCellFactory(listView -> new StudentListViewCell());
        studentListView.setSelectionModel(null);
        studentListView.addEventFilter(javafx.scene.input.KeyEvent.ANY, javafx.event.Event::consume);

        tutorialList.addListener((ListChangeListener<Tutorial>) change -> {
            studentListView.refresh();
        });

        tutorialList.forEach(tutorial ->
                tutorial.getStudents().addListener((ListChangeListener<Student>) change ->
                        studentListView.refresh()));

        tutorialList.addListener((ListChangeListener<Tutorial>) change -> {
            while (change.next()) {
                if (change.wasAdded()) {
                    change.getAddedSubList().forEach(tutorial ->
                            tutorial.getStudents().addListener((ListChangeListener<Student>) c ->
                                    studentListView.refresh()));
                }
            }
        });
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Student} using a {@code StudentCard}.
     */
    class StudentListViewCell extends ListCell<Student> {
        @Override
        protected void updateItem(Student student, boolean empty) {
            super.updateItem(student, empty);

            if (empty || student == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new StudentCard(student, getIndex() + 1, tutorialList).getRoot());
            }
        }
    }

}
