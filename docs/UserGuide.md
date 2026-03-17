---
layout: page
title: User Guide
---

CoursePilot is a **desktop app for managing tutorial groups and students, optimized for use via a Command Line Interface** (CLI) while still having the benefits of a Graphical User Interface (GUI). If you can type fast, CoursePilot can get your tutorial management tasks done faster than traditional GUI apps.

* Table of Contents
{:toc}

--------------------------------------------------------------------------------------------------------------------

## Quick start

1. Ensure you have Java `17` or above installed in your Computer.<br>
   **Mac users:** Ensure you have the precise JDK version prescribed [here](https://se-education.org/guides/tutorials/javaInstallationMac.html).

1. Download the latest `.jar` file from the project releases.

1. Copy the file to the folder you want to use as the _home folder_ for your CoursePilot.

1. Open a command terminal, `cd` into the folder you put the jar file in, and use the `java -jar coursepilot.jar` command to run the application.<br>
   A GUI similar to the below should appear in a few seconds. Note how the app contains some sample tutorials and students.<br>
   ![Ui](images/Ui.png)

1. Type the command in the command box and press Enter to execute it. e.g. typing **`help`** and pressing Enter will open the help window.<br>
   Some example commands you can try:

   * `list -tutorial` : Lists all tutorials.

   * `select CS2103T-W13` : Selects the tutorial `CS2103T-W13` to work with.

   * `list -student` : Lists all students in the selected tutorial.

   * `add -student /name John Doe /phone 98765432 /matric A000000 /email johnd@example.com` : Adds a student named `John Doe` to the currently selected tutorial.

   * `delete -student 3` : Deletes the 3rd student shown in the current list.

   * `exit` : Exits the app.

1. Refer to the [Features](#features) below for details of each command.

--------------------------------------------------------------------------------------------------------------------

## Features

<div markdown="block" class="alert alert-info">

**:information_source: Notes about the command format:**<br>

* Words in `UPPER_CASE` are the parameters to be supplied by the user.<br>
  e.g. in `add -student /name NAME`, `NAME` is a parameter which can be used as `add -student /name John Doe`.

* Items in square brackets are optional.<br>
  e.g `/name NAME [/tag TAG]` can be used as `/name John Doe /tag friend` or as `/name John Doe`.

* Items with `…`​ after them can be used multiple times including zero times.<br>
  e.g. `[/tag TAG]…​` can be used as ` ` (i.e. 0 times), `/tag friend`, `/tag friend /tag family` etc.

* Parameters can be in any order.<br>
  e.g. if the command specifies `/name NAME /phone PHONE`, `/phone PHONE /name NAME` is also acceptable.

* Extraneous parameters for commands that do not take in parameters (such as `help`, `exit` and `clear`) will be ignored.<br>
  e.g. if the command specifies `help 123`, it will be interpreted as `help`.

* If you are using a PDF version of this document, be careful when copying and pasting commands that span multiple lines as space characters surrounding line-breaks may be omitted when copied over to the application.

**:bulb: Tip: About Tutorials**<br>
Most student management commands require you to first **select a tutorial**. The app displays all available tutorials on the left panel. Use the `select` command or click on a tutorial to make it your current working tutorial. Once selected, commands like `list -student`, `find`, and `add -student` will operate on students within that tutorial.
</div>

### Viewing help : `help`

Shows a message explaining how to access the help page.

Format: `help`

### Selecting a tutorial : `select`

Selects a tutorial to work with. Once selected, this becomes your current operating tutorial, and commands like `list -student`, `find`, and `add -student` will apply to students within this tutorial only.

Format: `select TUTORIAL_CODE`

* The `TUTORIAL_CODE` is case-insensitive.
* The tutorial code must match a tutorial in the system exactly (e.g., `CS2103T-W13`).
* Once selected, the tutorial remains active until you select a different tutorial or run `list -tutorial`.
* If no tutorial with the given code is found, an informational message is shown.

Examples:
* `select CS2103T-W13` : Selects the tutorial with code `CS2103T-W13`.
* `select cs2103t-w13` : Also selects the same tutorial (case-insensitive).

### Listing tutorials or students : `list`

Lists either all available tutorials or students in the currently selected tutorial.

Format: `list -tutorial` or `list -student`

**You must specify either `-tutorial` or `-student`. Omitting this flag results in an error.**

* `list -tutorial` : Shows all available tutorials. Also **clears** the currently selected tutorial, so you will need to use `select` again before running student-specific commands.
* `list -student` : Shows all students enrolled in the currently selected tutorial.
  * Requires a tutorial to be selected first (use `select` command).
  * If no tutorial is selected, an error message will be displayed.

Examples:
* `list -tutorial` : Displays all tutorials (and deselects any currently selected tutorial).
* `select CS2103T-W13` followed by `list -student` : Displays all students in the CS2103T-W13 tutorial.

### Adding a student or tutorial : `add`

Adds a student to the currently selected tutorial, or adds a new tutorial to the system.

#### Adding a student

Format: `add -student /name NAME /phone PHONE /email EMAIL /matric MATRICNUMBER [/tag TAG]…​`

* **Requires a tutorial to be selected first.** The student is added to that tutorial.
* If the student (identified by name or matric number) does not yet exist in the system's global student list, they are also added globally.
* A student cannot be added to the same tutorial twice.
* A student can have any number of tags (including 0).

**Field Constraints:**
* **Name**: Must contain only alphanumeric characters and spaces. Cannot be blank. Must start with an alphanumeric character.
* **Phone**: Must contain only digits and be at least 3 digits long.
* **Email**: Must follow standard email format (`local-part@domain`). The local part may contain alphanumeric characters and `+`, `_`, `.`, `-` (but not at the start or end). The domain must end with a label of at least 2 characters.
* **Matric Number**: Must be exactly `A` followed by 6 digits (e.g., `A000000`, `A123456`).
* **Tag**: Alphanumeric word with no spaces.

**Duplicate detection:** A student is considered a duplicate within a tutorial if they share the same **name** or the same **matric number** as a student already in that tutorial.

Examples:
* `add -student /name John Doe /phone 98765432 /matric A000000 /email johnd@example.com`
* `add -student /name Betsy Crowe /tag friend /email betsycrowe@example.com /matric A000001 /phone 1234567 /tag student`

#### Adding a tutorial

Format: `add -tutorial /code CODE /day DAY /timeslot TIMESLOT /capacity CAPACITY`

* All fields (`/code`, `/day`, `/timeslot`, `/capacity`) are mandatory.
* The tutorial code must be unique — you cannot add a tutorial with the same code as an existing one.
* `/capacity` must be an integer.

Examples:
* `add -tutorial /code CS2103T-W13 /day Monday /timeslot 10:00-11:00 /capacity 20`
* `add -tutorial /code CS2103T-W14 /day Wednesday /timeslot 12:00-13:00 /capacity 15`

### Editing a student : `edit`

Edits an existing student's details in the currently displayed student list.

Format: `edit INDEX [/name NAME] [/phone PHONE] [/email EMAIL] [/matric MATRICNUMBER] [/tag TAG]…​`

* Edits the student at the specified `INDEX`. The index refers to the index number shown in the displayed student list. The index **must be a positive integer** 1, 2, 3, …​
* At least one of the optional fields must be provided.
* Existing values will be updated to the input values.
* When editing tags, the existing tags of the student will be **replaced** (not added to).
* You can remove all the student's tags by typing `/tag` without specifying any value after it.
* If the edited details would result in a duplicate (same name or matric number as another student), an error is shown.

Examples:
* `edit 1 /phone 91234567 /email johndoe@example.com` : Edits the phone number and email address of the 1st student.
* `edit 2 /name Betsy Crower /tag` : Edits the name of the 2nd student and clears all existing tags.

### Locating students : `find`

Finds and lists students in the currently selected tutorial based on specified criteria.

Format: `find KEYWORD [MORE_KEYWORDS]…​` or `find /phone KEYWORD` or `find /email KEYWORD` or `find /matric KEYWORD`

* **Requires a tutorial to be selected** using the `select` command. If no tutorial is selected, an error will occur.
* Results are filtered to students within the currently selected tutorial only.
* Multiple keywords are treated as OR — students matching **any** keyword are returned.

**Search behavior by mode:**

| Mode | Flag | Matching logic |
|------|------|----------------|
| Name (default) | _(none)_ | Case-insensitive, **whole-word** match against student's full name |
| Phone | `/phone` | Matches if phone number **starts with** the given keyword |
| Email | `/email` | Case-insensitive **partial substring** match against email address |
| Matric number | `/matric` | Matches if matric number **starts with** the given keyword |

**Note on name search:** Only full word matches count. For example, `Han` will **not** match `Hans`.

Examples:
* `find John` : Finds all students whose name contains the word `John` in the selected tutorial.
* `find alex david` : Returns students named `Alex Yeoh` or `David Li`.
* `find /email @u.nus.edu` : Finds all students whose email contains `@u.nus.edu`.
* `find /phone 9876` : Finds students whose phone number starts with `9876`.
* `find /matric A000` : Finds students whose matric number starts with `A000`.

### Deleting a student or tutorial : `delete`

Deletes the specified student from the displayed list, or deletes a tutorial from the system.

#### Deleting a student

Format: `delete -student INDEX`

* Deletes the student at the specified `INDEX` from the currently displayed student list.
* The index refers to the index number shown in the displayed student list.
* The index **must be a positive integer** 1, 2, 3, …​

Examples:
* `list -student` followed by `delete -student 2` : Deletes the 2nd student in the current tutorial.
* `find John` followed by `delete -student 1` : Deletes the 1st student in the results of the `find` command.

#### Deleting a tutorial

Format: `delete -tutorial INDEX`

* Deletes the tutorial at the specified `INDEX` from the tutorials list.
* The index refers to the index number shown in the tutorials panel.
* The index **must be a positive integer** 1, 2, 3, …​

Examples:
* `list -tutorial` followed by `delete -tutorial 1` : Deletes the 1st tutorial in the list.

### Clearing all entries : `clear`

Clears **all** data from the system — both all students and all tutorials.

Format: `clear`

<div markdown="span" class="alert alert-warning">:exclamation: **Caution:**
This command permanently deletes ALL students and ALL tutorials. This action cannot be undone. Use with care.
</div>

### Exiting the program : `exit`

Exits the program.

Format: `exit`

### Saving the data

CoursePilot automatically saves all data after any command that modifies data. There is no need to save manually.

The data is saved in a JSON file located at `[JAR file location]/data/addressbook.json`.

### Editing the data file

Advanced users can directly edit the data file to make bulk changes. The data file is stored as `[JAR file location]/data/addressbook.json` in JSON format.

<div markdown="span" class="alert alert-warning">:exclamation: **Caution:**
If your changes to the data file make its format invalid, CoursePilot will discard all data and start with an empty data file on the next run. Ensure you make a backup before editing and thoroughly validate the JSON format after making changes.<br>
Furthermore, manual edits can cause CoursePilot to behave unexpectedly if invalid data is introduced. Only edit the data file if you are confident in your ability to maintain valid JSON structure and data constraints.
</div>

--------------------------------------------------------------------------------------------------------------------

## FAQ

**Q**: How do I transfer my data to another computer?<br>
**A**: Install CoursePilot on the other computer and overwrite the empty data file it creates with the `addressbook.json` file from your previous installation.

**Q**: What happens if I forget to select a tutorial before using `list -student`, `find`, or `add -student`?<br>
**A**: CoursePilot will display an error message reminding you to select a tutorial first using the `select` command.

**Q**: Can I add a student who is already in the system to a different tutorial?<br>
**A**: Yes. If a student with the same name or matric number already exists in the global student list, CoursePilot will not create a duplicate global record but will still enrol them in the newly selected tutorial (as long as they are not already in that tutorial).

**Q**: What should I do if I enter an invalid command?<br>
**A**: CoursePilot will display an error message indicating what went wrong. Use the `help` command to view the correct command format.

**Q**: Does `list -tutorial` affect my currently selected tutorial?<br>
**A**: Yes. Running `list -tutorial` clears your current tutorial selection. You will need to use `select` again before running any student-specific commands.

--------------------------------------------------------------------------------------------------------------------

## Known issues

1. **When using multiple screens**, if you move the application to a secondary screen, and later switch to using only the primary screen, the GUI will open off-screen. The remedy is to delete the `preferences.json` file created by the application before running the application again.
2. **If you minimize the Help Window** and then run the `help` command (or use the `Help` menu, or the keyboard shortcut `F1`) again, the original Help Window will remain minimized, and no new Help Window will appear. The remedy is to manually restore the minimized Help Window.

--------------------------------------------------------------------------------------------------------------------

## Command summary

Action | Format, Examples
--------|------------------
**Add student** | `add -student /name NAME /phone PHONE /email EMAIL /matric MATRICNUMBER [/tag TAG]…​` <br> e.g., `add -student /name James Ho /phone 22224444 /email jamesho@example.com /matric A000000 /tag friend`
**Add tutorial** | `add -tutorial /code CODE /day DAY /timeslot TIMESLOT /capacity CAPACITY` <br> e.g., `add -tutorial /code CS2103T-W13 /day Monday /timeslot 10:00-11:00 /capacity 20`
**Clear** | `clear`
**Delete student** | `delete -student INDEX`<br> e.g., `delete -student 3`
**Delete tutorial** | `delete -tutorial INDEX`<br> e.g., `delete -tutorial 1`
**Edit** | `edit INDEX [/name NAME] [/phone PHONE] [/email EMAIL] [/matric MATRICNUMBER] [/tag TAG]…​`<br> e.g., `edit 2 /name James Lee /email jameslee@example.com`
**Find** | `find KEYWORD [MORE_KEYWORDS]…​` or `find /phone KEYWORD` or `find /email KEYWORD` or `find /matric KEYWORD`<br> e.g., `find James`, `find /email @u.nus.edu`, `find /phone 9876`
**List students** | `list -student`
**List tutorials** | `list -tutorial`
**Select** | `select TUTORIAL_CODE`<br> e.g., `select CS2103T-W13`
**Help** | `help`
**Exit** | `exit`
