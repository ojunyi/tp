package seedu.coursepilot.ui;

import java.util.logging.Logger;

import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputControl;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import seedu.coursepilot.commons.core.GuiSettings;
import seedu.coursepilot.commons.core.LogsCenter;
import seedu.coursepilot.logic.Logic;
import seedu.coursepilot.logic.commands.CommandResult;
import seedu.coursepilot.logic.commands.exceptions.CommandException;
import seedu.coursepilot.logic.parser.exceptions.ParseException;

/**
 * The Main Window. Provides the basic application layout containing
 * a menu bar and space where other JavaFX elements can be placed.
 */
public class MainWindow extends UiPart<Stage> {

    private static final String FXML = "MainWindow.fxml";
    private static final double EXIT_DELAY_SECONDS = 1;

    private final Logger logger = LogsCenter.getLogger(getClass());

    private Stage primaryStage;
    private Logic logic;

    // Independent Ui parts residing in this Ui container
    private StudentListPanel studentListPanel;
    private TutorialCodeListPanel tutorialCodeListPanel;
    private TutorialDetailsPanel tutorialDetailsPanel;
    private ResultDisplay resultDisplay;
    private HelpWindow helpWindow;

    @FXML
    private StackPane commandBoxPlaceholder;

    @FXML
    private MenuItem helpMenuItem;

    @FXML
    private StackPane studentListPanelPlaceholder;

    @FXML
    private StackPane tutorialListPanelPlaceholder;

    @FXML
    private StackPane tutorialDetailsPanelPlaceholder;

    @FXML
    private StackPane resultDisplayPlaceholder;

    @FXML
    private StackPane statusbarPlaceholder;

    @FXML
    private SplitPane outerSplitPane;

    @FXML
    private SplitPane innerSplitPane;

    /**
     * Creates a {@code MainWindow} with the given {@code Stage} and {@code Logic}.
     */
    public MainWindow(Stage primaryStage, Logic logic) {
        super(FXML, primaryStage);

        // Set dependencies
        this.primaryStage = primaryStage;
        this.logic = logic;

        // Configure the UI
        setWindowDefaultSize(logic.getGuiSettings());

        setAccelerators();

        helpWindow = new HelpWindow();
    }

    /**
     * Returns the primary stage of this window.
     */
    public Stage getPrimaryStage() {
        return primaryStage;
    }

    /**
     * Sets keyboard accelerators for menu items.
     */
    private void setAccelerators() {
        setAccelerator(helpMenuItem, KeyCombination.valueOf("F1"));
    }

    /**
     * Sets the accelerator of a MenuItem.
     * @param keyCombination the KeyCombination value of the accelerator
     */
    private void setAccelerator(MenuItem menuItem, KeyCombination keyCombination) {
        menuItem.setAccelerator(keyCombination);

        /*
         * TODO: the code below can be removed once the bug reported here
         * https://bugs.openjdk.java.net/browse/JDK-8131666
         * is fixed in later version of SDK.
         *
         * According to the bug report, TextInputControl (TextField, TextArea) will
         * consume function-key events. Because CommandBox contains a TextField, and
         * ResultDisplay contains a TextArea, thus some accelerators (e.g F1) will
         * not work when the focus is in them because the key event is consumed by
         * the TextInputControl(s).
         *
         * For now, we add following event filter to capture such key events and open
         * help window purposely so to support accelerators even when focus is
         * in CommandBox or ResultDisplay.
         */
        getRoot().addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getTarget() instanceof TextInputControl && keyCombination.match(event)) {
                menuItem.getOnAction().handle(new ActionEvent());
                event.consume();
            }
        });
    }

    /**
     * Fills up all the placeholders of this window.
     */
    void fillInnerParts() {
        logger.info("Initialising UI panels");
        tutorialCodeListPanel = new TutorialCodeListPanel(logic.getFilteredTutorialList(),
                logic.getCurrentOperatingTutorialProperty());
        tutorialListPanelPlaceholder.getChildren().add(tutorialCodeListPanel.getRoot());

        studentListPanel = new StudentListPanel(logic.getFilteredStudentList(), logic.getFilteredTutorialList());
        studentListPanelPlaceholder.getChildren().add(studentListPanel.getRoot());

        tutorialDetailsPanel = new TutorialDetailsPanel(logic.getFilteredTutorialList());
        tutorialDetailsPanelPlaceholder.getChildren().add(tutorialDetailsPanel.getRoot());

        resultDisplay = new ResultDisplay();
        resultDisplayPlaceholder.getChildren().add(resultDisplay.getRoot());

        StatusBarFooter statusBarFooter = new StatusBarFooter(
            logic.getCurrentOperatingTutorialProperty());
        statusbarPlaceholder.getChildren().add(statusBarFooter.getRoot());

        CommandBox commandBox = new CommandBox(this::executeCommand);
        commandBoxPlaceholder.getChildren().add(commandBox.getRoot());

        lockSplitPaneDividers(outerSplitPane);
        lockSplitPaneDividers(innerSplitPane);

        Platform.runLater(() -> syncTableViewScrollBars(
            tutorialCodeListPanel.getTableView(),
            tutorialDetailsPanel.getTableView()
        ));
    }

    /**
     * Disables all dividers in a SplitPane to prevent manual resizing.
     */
    private void lockSplitPaneDividers(SplitPane splitPane) {
        for (SplitPane.Divider divider : splitPane.getDividers()) {
            final double[] lockedPosition = {
                divider.getPosition()
            };
            final boolean[] isLocking = {
                false
            };
            divider.positionProperty().addListener((obs, oldVal, newVal) -> {
                if (!isLocking[0]) {
                    isLocking[0] = true;
                    divider.setPosition(lockedPosition[0]);
                    isLocking[0] = false;
                }
            });
        }
    }

    /**
     * Sets the default size based on {@code guiSettings}.
     */
    private void setWindowDefaultSize(GuiSettings guiSettings) {
        logger.fine("Setting window size to " + guiSettings.getWindowWidth() + "x" + guiSettings.getWindowHeight());
        primaryStage.setHeight(guiSettings.getWindowHeight());
        primaryStage.setWidth(guiSettings.getWindowWidth());
        if (guiSettings.getWindowCoordinates() != null) {
            primaryStage.setX(guiSettings.getWindowCoordinates().getX());
            primaryStage.setY(guiSettings.getWindowCoordinates().getY());
        }
    }

    /**
     * Syncs the vertical scroll bars of two TableViews so scrolling one scrolls the other.
     */
    private void syncTableViewScrollBars(TableView<?> table1, TableView<?> table2) {
        ScrollBar scrollBar1 = getVerticalScrollBar(table1);
        ScrollBar scrollBar2 = getVerticalScrollBar(table2);

        if (scrollBar1 == null || scrollBar2 == null) {
            logger.warning("Could not find scroll bars to sync; table scrolling will not be synchronised");
            return;
        }

        final boolean[] isLocking = { false };

        scrollBar1.valueProperty().addListener((obs, oldVal, newVal) -> {
            if (!isLocking[0]) {
                isLocking[0] = true;
                scrollBar2.setValue(newVal.doubleValue());
                isLocking[0] = false;
            }
        });
        scrollBar2.valueProperty().addListener((obs, oldVal, newVal) -> {
            if (!isLocking[0]) {
                isLocking[0] = true;
                scrollBar1.setValue(newVal.doubleValue());
                isLocking[0] = false;
            }
        });
    }

    /**
     * Extracts the vertical ScrollBar from a TableView.
     */
    private ScrollBar getVerticalScrollBar(TableView<?> tableView) {
        for (Node node : tableView.lookupAll(".scroll-bar")) {
            if (node instanceof ScrollBar) {
                ScrollBar scrollBar = (ScrollBar) node;
                if (scrollBar.getOrientation() == Orientation.VERTICAL) {
                    return scrollBar;
                }
            }
        }
        return null;
    }

    /**
     * Opens the help window or focuses on it if it's already opened.
     */
    @FXML
    public void handleHelp() {
        if (!helpWindow.isShowing()) {
            helpWindow.show();
        } else {
            helpWindow.focus();
        }
    }

    /**
     * Shows the primary stage.
     */
    void show() {
        primaryStage.show();
    }

    /**
     * Closes CoursePilot.
     */
    @FXML
    private void handleExit() {
        logger.info("Exiting CoursePilot");
        GuiSettings guiSettings = new GuiSettings(primaryStage.getWidth(), primaryStage.getHeight(),
                (int) primaryStage.getX(), (int) primaryStage.getY());
        logic.setGuiSettings(guiSettings);
        helpWindow.hide();

        PauseTransition delay = new PauseTransition(Duration.seconds(EXIT_DELAY_SECONDS));
        delay.setOnFinished(event -> primaryStage.hide());
        delay.play();
    }

    /**
     * Executes the command and returns the result.
     *
     * @see seedu.coursepilot.logic.Logic#execute(String)
     */
    private CommandResult executeCommand(String commandText) throws CommandException, ParseException {
        try {
            CommandResult commandResult = logic.execute(commandText);
            logger.info("Result: " + commandResult.getFeedbackToUser());
            resultDisplay.setFeedbackToUser(commandResult.getFeedbackToUser());
            studentListPanel.refresh();

            if (commandResult.isShowHelp()) {
                handleHelp();
            }

            if (commandResult.isExit()) {
                handleExit();
            }

            return commandResult;
        } catch (CommandException | ParseException e) {
            logger.warning("An error occurred while executing command: " + commandText);
            resultDisplay.setFeedbackToUser(e.getMessage());
            throw e;
        }
    }
}
