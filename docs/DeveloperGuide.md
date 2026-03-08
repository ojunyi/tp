---
layout: page
title: Developer Guide
---
* Table of Contents
{:toc}

--------------------------------------------------------------------------------------------------------------------

## **Acknowledgements**

* {list here sources of all reused/adapted ideas, code, documentation, and third-party libraries -- include links to the original source as well}

--------------------------------------------------------------------------------------------------------------------

## **Setting up, getting started**

Refer to the guide [_Setting up and getting started_](SettingUp.md).

--------------------------------------------------------------------------------------------------------------------

## **Design**

<div markdown="span" class="alert alert-primary">

:bulb: **Tip:** The `.puml` files used to create diagrams are in this document `docs/diagrams` folder. Refer to the [_PlantUML Tutorial_ at se-edu/guides](https://se-education.org/guides/tutorials/plantUml.html) to learn how to create and edit diagrams.
</div>

### Architecture

<img src="images/ArchitectureDiagram.png" width="280" />

The ***Architecture Diagram*** given above explains the high-level design of the App.

Given below is a quick overview of main components and how they interact with each other.

**Main components of the architecture**

**`Main`** (consisting of classes [`Main`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/Main.java) and [`MainApp`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/MainApp.java)) is in charge of the app launch and shut down.
* At app launch, it initializes the other components in the correct sequence, and connects them up with each other.
* At shut down, it shuts down the other components and invokes cleanup methods where necessary.

The bulk of the app's work is done by the following four components:

* [**`UI`**](#ui-component): The UI of the App.
* [**`Logic`**](#logic-component): The command executor.
* [**`Model`**](#model-component): Holds the data of the App in memory.
* [**`Storage`**](#storage-component): Reads data from, and writes data to, the hard disk.

[**`Commons`**](#common-classes) represents a collection of classes used by multiple other components.

**How the architecture components interact with each other**

The *Sequence Diagram* below shows how the components interact with each other for the scenario where the user issues the command `delete 1`.

<img src="images/ArchitectureSequenceDiagram.png" width="574" />

Each of the four main components (also shown in the diagram above),

* defines its *API* in an `interface` with the same name as the Component.
* implements its functionality using a concrete `{Component Name}Manager` class (which follows the corresponding API `interface` mentioned in the previous point.

For example, the `Logic` component defines its API in the `Logic.java` interface and implements its functionality using the `LogicManager.java` class which follows the `Logic` interface. Other components interact with a given component through its interface rather than the concrete class (reason: to prevent outside component's being coupled to the implementation of a component), as illustrated in the (partial) class diagram below.

<img src="images/ComponentManagers.png" width="300" />

The sections below give more details of each component.

### UI component

The **API** of this component is specified in [`Ui.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/ui/Ui.java)

![Structure of the UI Component](images/UiClassDiagram.png)

The UI consists of a `MainWindow` that is made up of parts e.g.`CommandBox`, `ResultDisplay`, `PersonListPanel`, `StatusBarFooter` etc. All these, including the `MainWindow`, inherit from the abstract `UiPart` class which captures the commonalities between classes that represent parts of the visible GUI.

The `UI` component uses the JavaFx UI framework. The layout of these UI parts are defined in matching `.fxml` files that are in the `src/main/resources/view` folder. For example, the layout of the [`MainWindow`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/ui/MainWindow.java) is specified in [`MainWindow.fxml`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/resources/view/MainWindow.fxml)

The `UI` component,

* executes user commands using the `Logic` component.
* listens for changes to `Model` data so that the UI can be updated with the modified data.
* keeps a reference to the `Logic` component, because the `UI` relies on the `Logic` to execute commands.
* depends on some classes in the `Model` component, as it displays `Person` object residing in the `Model`.

### Logic component

**API** : [`Logic.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/logic/Logic.java)

Here's a (partial) class diagram of the `Logic` component:

<img src="images/LogicClassDiagram.png" width="550"/>

The sequence diagram below illustrates the interactions within the `Logic` component, taking `execute("delete 1")` API call as an example.

![Interactions Inside the Logic Component for the `delete 1` Command](images/DeleteSequenceDiagram.png)

<div markdown="span" class="alert alert-info">:information_source: **Note:** The lifeline for `DeleteCommandParser` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline continues till the end of diagram.
</div>

How the `Logic` component works:

1. When `Logic` is called upon to execute a command, it is passed to an `AddressBookParser` object which in turn creates a parser that matches the command (e.g., `DeleteCommandParser`) and uses it to parse the command.
1. This results in a `Command` object (more precisely, an object of one of its subclasses e.g., `DeleteCommand`) which is executed by the `LogicManager`.
1. The command can communicate with the `Model` when it is executed (e.g. to delete a person).<br>
   Note that although this is shown as a single step in the diagram above (for simplicity), in the code it can take several interactions (between the command object and the `Model`) to achieve.
1. The result of the command execution is encapsulated as a `CommandResult` object which is returned back from `Logic`.

Here are the other classes in `Logic` (omitted from the class diagram above) that are used for parsing a user command:

<img src="images/ParserClasses.png" width="600"/>

How the parsing works:
* When called upon to parse a user command, the `AddressBookParser` class creates an `XYZCommandParser` (`XYZ` is a placeholder for the specific command name e.g., `AddCommandParser`) which uses the other classes shown above to parse the user command and create a `XYZCommand` object (e.g., `AddCommand`) which the `AddressBookParser` returns back as a `Command` object.
* All `XYZCommandParser` classes (e.g., `AddCommandParser`, `DeleteCommandParser`, ...) inherit from the `Parser` interface so that they can be treated similarly where possible e.g, during testing.

### Model component
**API** : [`Model.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/model/Model.java)

<img src="images/ModelClassDiagram.png" width="450" />


The `Model` component,

* stores the address book data i.e., all `Person` objects (which are contained in a `UniquePersonList` object).
* stores the currently 'selected' `Person` objects (e.g., results of a search query) as a separate _filtered_ list which is exposed to outsiders as an unmodifiable `ObservableList<Person>` that can be 'observed' e.g. the UI can be bound to this list so that the UI automatically updates when the data in the list change.
* stores a `UserPref` object that represents the user’s preferences. This is exposed to the outside as a `ReadOnlyUserPref` objects.
* does not depend on any of the other three components (as the `Model` represents data entities of the domain, they should make sense on their own without depending on other components)

<div markdown="span" class="alert alert-info">:information_source: **Note:** An alternative (arguably, a more OOP) model is given below. It has a `Tag` list in the `AddressBook`, which `Person` references. This allows `AddressBook` to only require one `Tag` object per unique tag, instead of each `Person` needing their own `Tag` objects.<br>

<img src="images/BetterModelClassDiagram.png" width="450" />

</div>


### Storage component

**API** : [`Storage.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/storage/Storage.java)

<img src="images/StorageClassDiagram.png" width="550" />

The `Storage` component,
* can save both address book data and user preference data in JSON format, and read them back into corresponding objects.
* inherits from both `AddressBookStorage` and `UserPrefStorage`, which means it can be treated as either one (if only the functionality of only one is needed).
* depends on some classes in the `Model` component (because the `Storage` component's job is to save/retrieve objects that belong to the `Model`)

### Common classes

Classes used by multiple components are in the `seedu.address.commons` package.

--------------------------------------------------------------------------------------------------------------------

## **Implementation**

This section describes some noteworthy details on how certain features are implemented.

### \[Proposed\] Undo/redo feature

#### Proposed Implementation

The proposed undo/redo mechanism is facilitated by `VersionedAddressBook`. It extends `AddressBook` with an undo/redo history, stored internally as an `addressBookStateList` and `currentStatePointer`. Additionally, it implements the following operations:

* `VersionedAddressBook#commit()` — Saves the current address book state in its history.
* `VersionedAddressBook#undo()` — Restores the previous address book state from its history.
* `VersionedAddressBook#redo()` — Restores a previously undone address book state from its history.

These operations are exposed in the `Model` interface as `Model#commitAddressBook()`, `Model#undoAddressBook()` and `Model#redoAddressBook()` respectively.

Given below is an example usage scenario and how the undo/redo mechanism behaves at each step.

Step 1. The user launches the application for the first time. The `VersionedAddressBook` will be initialized with the initial address book state, and the `currentStatePointer` pointing to that single address book state.

![UndoRedoState0](images/UndoRedoState0.png)

Step 2. The user executes `delete 5` command to delete the 5th person in the address book. The `delete` command calls `Model#commitAddressBook()`, causing the modified state of the address book after the `delete 5` command executes to be saved in the `addressBookStateList`, and the `currentStatePointer` is shifted to the newly inserted address book state.

![UndoRedoState1](images/UndoRedoState1.png)

Step 3. The user executes `add n/David …​` to add a new person. The `add` command also calls `Model#commitAddressBook()`, causing another modified address book state to be saved into the `addressBookStateList`.

![UndoRedoState2](images/UndoRedoState2.png)

<div markdown="span" class="alert alert-info">:information_source: **Note:** If a command fails its execution, it will not call `Model#commitAddressBook()`, so the address book state will not be saved into the `addressBookStateList`.

</div>

Step 4. The user now decides that adding the person was a mistake, and decides to undo that action by executing the `undo` command. The `undo` command will call `Model#undoAddressBook()`, which will shift the `currentStatePointer` once to the left, pointing it to the previous address book state, and restores the address book to that state.

![UndoRedoState3](images/UndoRedoState3.png)

<div markdown="span" class="alert alert-info">:information_source: **Note:** If the `currentStatePointer` is at index 0, pointing to the initial AddressBook state, then there are no previous AddressBook states to restore. The `undo` command uses `Model#canUndoAddressBook()` to check if this is the case. If so, it will return an error to the user rather
than attempting to perform the undo.

</div>

The following sequence diagram shows how an undo operation goes through the `Logic` component:

![UndoSequenceDiagram](images/UndoSequenceDiagram-Logic.png)

<div markdown="span" class="alert alert-info">:information_source: **Note:** The lifeline for `UndoCommand` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline reaches the end of diagram.

</div>

Similarly, how an undo operation goes through the `Model` component is shown below:

![UndoSequenceDiagram](images/UndoSequenceDiagram-Model.png)

The `redo` command does the opposite — it calls `Model#redoAddressBook()`, which shifts the `currentStatePointer` once to the right, pointing to the previously undone state, and restores the address book to that state.

<div markdown="span" class="alert alert-info">:information_source: **Note:** If the `currentStatePointer` is at index `addressBookStateList.size() - 1`, pointing to the latest address book state, then there are no undone AddressBook states to restore. The `redo` command uses `Model#canRedoAddressBook()` to check if this is the case. If so, it will return an error to the user rather than attempting to perform the redo.

</div>

Step 5. The user then decides to execute the command `list`. Commands that do not modify the address book, such as `list`, will usually not call `Model#commitAddressBook()`, `Model#undoAddressBook()` or `Model#redoAddressBook()`. Thus, the `addressBookStateList` remains unchanged.

![UndoRedoState4](images/UndoRedoState4.png)

Step 6. The user executes `clear`, which calls `Model#commitAddressBook()`. Since the `currentStatePointer` is not pointing at the end of the `addressBookStateList`, all address book states after the `currentStatePointer` will be purged. Reason: It no longer makes sense to redo the `add n/David …​` command. This is the behavior that most modern desktop applications follow.

![UndoRedoState5](images/UndoRedoState5.png)

The following activity diagram summarizes what happens when a user executes a new command:

<img src="images/CommitActivityDiagram.png" width="250" />

#### Design considerations:

**Aspect: How undo & redo executes:**

* **Alternative 1 (current choice):** Saves the entire address book.
  * Pros: Easy to implement.
  * Cons: May have performance issues in terms of memory usage.

* **Alternative 2:** Individual command knows how to undo/redo by
  itself.
  * Pros: Will use less memory (e.g. for `delete`, just save the person being deleted).
  * Cons: We must ensure that the implementation of each individual command are correct.

_{more aspects and alternatives to be added}_

### \[Proposed\] Data archiving

_{Explain here how the data archiving feature will be implemented}_


--------------------------------------------------------------------------------------------------------------------

## **Documentation, logging, testing, configuration, dev-ops**

* [Documentation guide](Documentation.md)
* [Testing guide](Testing.md)
* [Logging guide](Logging.md)
* [Configuration guide](Configuration.md)
* [DevOps guide](DevOps.md)

--------------------------------------------------------------------------------------------------------------------

## **Appendix: Requirements**

### Product scope

**Target user profile**:

* University tutors or teaching assistants (TA) managing one or more tutorial groups
* TA who needs to track student participation, attendance, and grading progress across recurring weekly assessments
* TA who prefers local desktop apps over web-based platforms like Canvas
* Who can type fast and prefers typing to mouse interactions
* Who is reasonably comfortable using CLI apps

**Value proposition**: a single hub that reduces teaching admin overhead by keeping student group organization, 
grading progress, and participation records in one place, so TAs can run sessions consistently and grade faster than using web-based tools like Canvas


### User stories

Priorities: High (must have) - `* * *`, Medium (nice to have) - `* *`, Low (unlikely to have) - `*`

| Priority | Role  | Feature                                                  | Benefit |
|----------|-------|----------------------------------------------------------|---|
| * * *    | Tutor | Ask for help when using the product                      | I can refer to instructions when I forget how to use the App |
| * * *    | Tutor | Add a student to the address book                        | I can keep track of all my students in one place |
| * * *    | Tutor | Delete a student from the address book                   | I can remove students who are no longer relevant |
| * * *    | Tutor | List all student contacts                                | I can quickly view all students stored in the system |
| * * *    | Tutor | Edit existing student records                            | I can keep student information accurate and up to date |
| * * *    | Tutor | Find a student record                                    | I can quickly find a specific student's detail |
| * * *    | Tutor | Clear all existing student record                        | I can reset the system when the data is no longer needed |
| * * *    | Tutor | Exit the program                                         | I can safely close the application after use |
| * * *    | Tutor | Archive the program data                                 | I do not have to repopulate all the data again |
| * *      | Tutor | Check student contact details                            | I can contact students when needed |
| * *      | Tutor | View a tutorial                                          | I can check the contents of the tutorial |
| * * *    | Tutor | List all tutorials                                       | I can check all the tutorials offered |
| * * *    | Tutor | Assign students to certain tutorial slots                | I can organize students into the correct tutorials |
| * * *    | Tutor | View which students are assigned to which tutorial slots | I can understand the distribution of students across tutorials |
| * *      | Tutor | Assign tutorial slots to teach in                        | I can manage which tutorials I am responsible for |
| * *      | Tutor | View which tutors are assigned to which tutorial slots   | I can know who is responsible for each tutorial |
| * * *    | Tutor | Filter available tutorial slots                          | I can easily find tutorial slots that match my conditions |
| * *      | Tutor | Edit tutorial slots                                      | I can update tutorial arrangements |
| *        | Tutor | Add tutorial resources                                   | I can share useful materials with students |
| *        | Tutor | Delete tutorial resources                                | I can remove outdated or unnecessary materials |
| *        | Tutor | Edit tutorial resources                                  | I can replace or reupload outdated materials |
| * *      | Tutor | Track tutorial of each student                           | I can monitor each student's tutorial participation |
| *        | Tutor | Add assessments                                          | I can manage student evaluations and grading components |
| *        | Tutor | Delete assessments                                       | I can remove assessments that are no longer required |
| *        | Tutor | Edit assessments                                         | I can update assessment details when necessary |
| *        | Tutor | Grade assessments                                        | I can evaluate student performance |
| *        | Tutor | Track assessments                                        | I can monitor students' assessments and results |
| *        | Tutor | Comment on student's work                                | I can provide constructive feedback to improve their learning |
| * * *    | Tutor | Mark student attendance                                  | I can record who attended each tutorial |
| * *      | Tutor | Unmark student attendance                                | I can correct attendance records if mistakes occur |
| * *      | Tutor | Edit student attendance                                  | I can update attendance records when needed |
| * *      | Tutor | Track attendance                                         | I can monitor student participation over time |
| *        | Tutor | Create groups                                            | I can organize students for collaborative activities |
| *        | Tutor | Send student private message                             | I can communicate directly with a specific student |
| *        | Tutor | Send student group message                               | I can communicate important information to multiple students at once |
| * *      | Tutor | Provide feedback to students                             | I can help students improve their learning |
| * *      | Tutor | Add deadlines                                            | I can remind myself of important dates |
| * *      | Tutor | Delete deadlines                                         | I can remove deadlines that are no longer relevant |
| * *      | Tutor | Edit deadlines                                           | I can update deadlines if schedules change |
| * *      | Tutor | Be reminded of deadlines                                 | I do not miss any important deadlines |

### Use cases

(For all use cases below, the **System** is `CoursePilot` and the **Actor** is the `tutor`, unless specified otherwise)

**Use case: UC01 - Add a student**

**MSS**

1.  Tutor enters the command to add a student with the student's name, email, and tutorial group.
2.  CoursePilot adds the student and displays a confirmation message.

    Use case ends.

**Extensions**

* 1a. The student's name or email is missing.

    * 1a1. CoursePilot shows an error message indicating required fields.

      Use case resumes at step 1.

* 1b. A student with the same email already exists.

    * 1b1. CoursePilot shows an error message indicating duplicate entry.

      Use case resumes at step 1.

**Use case: UC02 - Delete a student**

**MSS**

1.  Tutor requests to <u>list students (UC06)</u>.
2.  Tutor requests to delete a specific student in the list. 
3.  CoursePilot deletes the student and displays a confirmation message.

    Use case ends.

**Extensions**

* 2a. The list is empty.

  Use case ends.

* 3a. The given index is invalid.

    * 3a1. CoursePilot shows an error message.

      Use case resumes at step 2.

**Use case: UC03 - Mark attendance**

**MSS**

1.  Tutor requests to <u>list students (UC06)</u>.
2.  Tutor enters the command to mark a student's attendance for a specific tutorial session.
3.  CoursePilot records the attendance and displays a confirmation message.

    Use case ends.

**Extensions**

* 3a. The given student index is invalid.

    * 3a1. CoursePilot shows an error message.

      Use case resumes at step 2.

* 3b. The specified tutorial session does not exist.

    * 3b1. CoursePilot shows an error message.

      Use case resumes at step 2.

**Use case: UC04 - Record a grade**

**MSS**

1.  Tutor enters the command to record a grade for a student for a specific assessment.
2.  CoursePilot records the grade and displays a confirmation message.

    Use case ends.

**Extensions**

* 1a. The given student index is invalid.

    * 1a1. CoursePilot shows an error message.

      Use case resumes at step 1.

* 1b. The specified assessment does not exist.

    * 1b1. CoursePilot shows an error message.

      Use case resumes at step 1.

* 1c. The grade value is out of the valid range.

    * 1c1. CoursePilot shows an error message.

      Use case resumes at step 1.

**Use case: UC05 - Ask for help**

**MSS**

1.  Tutor requests for help.
2.  CoursePilot shows the details of possible user commands.

    Use case ends.

**Use case: UC06 - List students**

**MSS**

1.  Tutor requests to list students.
2.  CoursePilot shows a list of students.

    Use case ends.

**Use case: UC07 - Add a tutorial**

**MSS**

1.  Tutor enters the command to add a student with the tutorial's module, day of the week, 
timing, and students.
2.  CoursePilot adds the tutorial and displays a confirmation message.

    Use case ends.

**Extensions**

* 1a. The tutorial's field(s) is/are missing.

    * 1a1. CoursePilot shows an error message indicating required fields.

      Use case resumes at step 1.

**Use case: UC08 - Delete a tutorial**

**MSS**

1.  Tutor requests to <u>list tutorials (UC09)</u>.
2.  Tutor requests to delete a specific tutorial in the list.
3.  CoursePilot deletes the tutorial and displays a confirmation message.

    Use case ends.

**Extensions**

* 2a. The list is empty.

  Use case ends.

* 3a. The given index is invalid.

    * 3a1. CoursePilot shows an error message.

      Use case resumes at step 2.

**Use case: UC09 - List tutorials**

**MSS**

1.  Tutor requests to list tutorials.
2.  CoursePilot shows a list of tutorials.

    Use case ends.

**Use case: UC10 - Find student**

**MSS**

1.  Tutor requests to find a student and specifies details to be searched.
2.  CoursePilot shows details of student.

    Use case ends.

**Extensions**

* 1a. No such student is found.

    * 1a1. CoursePilot shows no such student is found.

  Use case ends.

**Use case: UC11 - Find tutorial**

**MSS**

1.  Tutor requests to find a tutorial and specifies details to be searched.
2.  CoursePilot shows details of tutorial.

    Use case ends.

**Extensions**

* 1a. No such tutorial is found.

    * 1a1. CoursePilot shows no such tutorial is found.

  Use case ends.

### Non-Functional Requirements

1.  Should work on any _mainstream OS_ as long as it has Java `17` or above installed.
2.  Should be able to hold up to 1000 students without a noticeable sluggishness in performance for typical usage.
3.  A tutor with above average typing speed for regular English text should be able to accomplish most of the tasks faster using commands than using the mouse.
4.  Data should be stored locally in a human-editable file format (e.g., JSON).
5.  The application should not require a database management system (DBMS).
6.  The application should not depend on any remote server.
7.  The application should work without requiring an installer.
8.  The application should be packaged into a single JAR file not exceeding 100MB.
9.  The GUI should work well for standard screen resolutions of 1920x1080 and higher at 100% and 125% scaling, and should be usable at 1280x720 and higher at 150% scaling.
10. The application should work on JDK 17.
11. The application should follow an object-oriented design.

### Glossary

* **Mainstream OS**: Windows, Linux, Unix, MacOS
* **Tutor/TA**: A teaching assistant at a university responsible for conducting tutorial sessions, marking attendance, and grading assessments
* **Tutorial group**: A group of students assigned to a specific tutorial session managed by a tutor
* **Assessment**: A graded component of a course such as an assignment, quiz, or exam
* **Attendance**: A record of whether a student was present for a specific tutorial session
* **CLI**: Command Line Interface, a text-based interface for interacting with the application

--------------------------------------------------------------------------------------------------------------------

## **Appendix: Instructions for manual testing**

Given below are instructions to test the app manually.

<div markdown="span" class="alert alert-info">:information_source: **Note:** These instructions only provide a starting point for testers to work on;
testers are expected to do more *exploratory* testing.

</div>

### Launch and shutdown

1. Initial launch

   1. Download the jar file and copy into an empty folder

   1. Double-click the jar file Expected: Shows the GUI with a set of sample contacts. The window size may not be optimum.

1. Saving window preferences

   1. Resize the window to an optimum size. Move the window to a different location. Close the window.

   1. Re-launch the app by double-clicking the jar file.<br>
       Expected: The most recent window size and location is retained.

1. _{ more test cases …​ }_

### Deleting a person

1. Deleting a person while all persons are being shown

   1. Prerequisites: List all persons using the `list` command. Multiple persons in the list.

   1. Test case: `delete 1`<br>
      Expected: First contact is deleted from the list. Details of the deleted contact shown in the status message. Timestamp in the status bar is updated.

   1. Test case: `delete 0`<br>
      Expected: No person is deleted. Error details shown in the status message. Status bar remains the same.

   1. Other incorrect delete commands to try: `delete`, `delete x`, `...` (where x is larger than the list size)<br>
      Expected: Similar to previous.

1. _{ more test cases …​ }_

### Saving data

1. Dealing with missing/corrupted data files

   1. _{explain how to simulate a missing/corrupted file, and the expected behavior}_

1. _{ more test cases …​ }_
