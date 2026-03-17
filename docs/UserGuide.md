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

   * `select CS2103T-W12` : Selects the tutorial `CS2103T-W12` to work with.

   * `list -student` : Lists all students in the selected tutorial.

   * `add /name John Doe /phone 98765432 /matric A000000 /email johnd@example.com` : Adds a student named `John Doe` to CoursePilot.

   * `delete 3` : Deletes the 3rd student shown in the current list.

   * `exit` : Exits the app.

1. Refer to the [Features](#features) below for details of each command.

--------------------------------------------------------------------------------------------------------------------

## Features

<div markdown="block" class="alert alert-info">

**:information_source: Notes about the command format:**<br>

* Words in `UPPER_CASE` are the parameters to be supplied by the user.<br>
  e.g. in `add /name NAME`, `NAME` is a parameter which can be used as `add /name John Doe`.

* Items in square brackets are optional.<br>
  e.g `/name NAME [/tag TAG]` can be used as `/name John Doe /tag friend` or as `/name John Doe`.

* Items with `…`​ after them can be used multiple times including zero times.<br>
  e.g. `[/tag TAG]…​` can be used as ` ` (i.e. 0 times), `/tag friend`, `/tag friend /tag family` etc.

* Parameters can be in any order.<br>
  e.g. if the command specifies `/name NAME /phone PHONE_NUMBER`, `/phone PHONE_NUMBER /name NAME` is also acceptable.

* Extraneous parameters for commands that do not take in parameters (such as `help`, `exit` and `clear`) will be ignored.<br>
  e.g. if the command specifies `help 123`, it will be interpreted as `help`.

* If you are using a PDF version of this document, be careful when copying and pasting commands that span multiple lines as space characters surrounding line-breaks may be omitted when copied over to the application.

**:bulb: Tip: About Tutorials**<br>
Most student management commands require you to first **select a tutorial**. The app displays all available tutorials on the left panel. Use the `select` command or click on a tutorial to make it your current working tutorial. Once selected, commands like `list -student` and `find` will operate on students within that tutorial.
</div>

### Viewing help : `help`

Shows a message explaining how to access the help page.

Format: `help`

### Selecting a tutorial : `select`

Selects a tutorial to work with. Once selected, this becomes your current operating tutorial, and commands like `list -student` and `find` will apply to students within this tutorial only.

Format: `select TUTORIAL_CODE`

* The `TUTORIAL_CODE` is case-insensitive.
* The tutorial code must match a tutorial in the system exactly (e.g., `CS2103T-W12`).
* Once selected, the tutorial is highlighted in the tutorials list and remains active until you select a different tutorial.

Examples:
* `select CS2103T-W12` : Selects the tutorial with code `CS2103T-W12`.
* `select cs2103t-w12` : Also selects the same tutorial (case-insensitive).

### Listing tutorials or students : `list`

Lists either all available tutorials or students in the currently selected tutorial.

Format: `list -tutorial` or `list -student`

**Without arguments, this command is ambiguous and will result in an error. You must specify either `-tutorial` or `-student`.**

* `list -tutorial` : Shows all available tutorials and their details (day, time slot, capacity).
* `list -student` : Shows all students in the currently selected tutorial.
  * Requires a tutorial to be selected first (use `select` command).
  * If no tutorial is selected, an error message will be displayed.

Examples:
* `list -tutorial` : Displays all tutorials.
* `select CS2103T-W12` followed by `list -student` : Displays all students in the CS2103T-W12 tutorial.

### Adding a student: `add`

Adds a student to the system.

Format: `add /name NAME /phone PHONE_NUMBER /email EMAIL /matric MATRICNUMBER [/tag TAG]…​`

* All fields (`/name`, `/phone`, `/email`, `/matric`) are mandatory.
* A student can have any number of tags (including 0).
* Students are added globally and can be assigned to tutorials. When you add a student, they are available system-wide.

**Field Constraints:**
* **Name**: Must contain only alphanumeric characters and spaces. Cannot be blank.
* **Phone**: Must contain only digits and be at least 3 digits long.
* **Email**: Must follow standard email format (e.g., `student@u.nus.edu`).
* **Matric Number**: Must follow the format `Axxxxxx` where `x` is a digit (e.g., `A000000`, `A123456`).
* **Tag**: Can be any single word or phrase with no special requirements.

Examples:
* `add /name John Doe /phone 98765432 /matric A000000 /email johnd@example.com`
* `add /name Betsy Crowe /tag friend /email betsycrowe@example.com /matric A000001 /phone 1234567 /tag student`

### Editing a student : `edit`

Edits an existing student's details.

Format: `edit INDEX [/name NAME] [/phone PHONE] [/email EMAIL] [/matric MATRICNUMBER] [/tag TAG]…​`

* Edits the student at the specified `INDEX`. The index refers to the index number shown in the displayed student list. The index **must be a positive integer** 1, 2, 3, …​
* At least one of the optional fields must be provided.
* Existing values will be updated to the input values.
* When editing tags, the existing tags of the student will be replaced (not added to). 
* You can remove all the student's tags by typing `/tag` without specifying any tags after it.

Examples:
* `edit 1 /phone 91234567 /email johndoe@example.com` : Edits the phone number and email address of the 1st student.
* `edit 2 /name Betsy Crower /tag` : Edits the name of the 2nd student and clears all existing tags.

### Locating students : `find`

Finds and lists students in the currently selected tutorial based on specified criteria.

Format: `find KEYWORD [MORE_KEYWORDS]…​` or `find /phone KEYWORDS` or `find /email KEYWORDS` or `find /matric KEYWORDS`

* **Default behavior (by name)**: `find KEYWORD` searches by student name.
* **Search by phone**: `find /phone KEYWORD` searches by phone number.
* **Search by email**: `find /email KEYWORD` searches by email address.
* **Search by matric**: `find /matric KEYWORD` searches by matric number.
* The search is case-insensitive.
* The order of keywords does not matter.
* Only full word matches are returned (e.g., `Han` will not match `Hans`).
* Students matching at least one keyword will be returned (OR search).
* **Important**: You must have a tutorial selected using the `select` command before using `find`. If no tutorial is selected, an error will occur.

Examples:
* `select CS2103T-W12` followed by `find John` : Finds all students named John in the CS2103T-W12 tutorial.
* `find alex david` : Returns students named `Alex Yeoh` or `David Li`.
* `find /email @u.nus.edu` : Finds all students whose email contains `@u.nus.edu`.
* `find /phone 98765` : Finds students whose phone number contains `98765`.
* `find /matric A000000` : Finds the student with matric number A000000.

### Deleting a student : `delete`

Deletes the specified student from the system.

Format: `delete INDEX`

* Deletes the student at the specified `INDEX`.
* The index refers to the index number shown in the displayed student list.
* The index **must be a positive integer** 1, 2, 3, …​

Examples:
* `list -student` followed by `delete 2` : Deletes the 2nd student in the current tutorial.
* `find John` followed by `delete 1` : Deletes the 1st student in the results of the `find` command.

### Clearing all entries : `clear`

Clears all students from the system.

Format: `clear`

<div markdown="span" class="alert alert-warning">:exclamation: **Caution:**
This command will delete all students. Use with care.
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

**Q**: What happens if I forget to select a tutorial before using `list -student` or `find`?<br>
**A**: CoursePilot will display an error message reminding you to select a tutorial first using the `select` command.

**Q**: Can I add students without assigning them to a tutorial first?<br>
**A**: Yes, students are added globally to the system. Once added, they can be viewed and managed. The tutorial structure is separate and manages group assignments.

**Q**: What should I do if I enter an invalid command?<br>
**A**: CoursePilot will display an error message indicating what went wrong. Use the `help` command to view the correct command format.

--------------------------------------------------------------------------------------------------------------------

## Known issues

1. **When using multiple screens**, if you move the application to a secondary screen, and later switch to using only the primary screen, the GUI will open off-screen. The remedy is to delete the `preferences.json` file created by the application before running the application again.
2. **If you minimize the Help Window** and then run the `help` command (or use the `Help` menu, or the keyboard shortcut `F1`) again, the original Help Window will remain minimized, and no new Help Window will appear. The remedy is to manually restore the minimized Help Window.

--------------------------------------------------------------------------------------------------------------------

## Command summary

Action | Format, Examples
--------|------------------
**Add** | `add /name NAME /phone PHONE_NUMBER /email EMAIL /matric MATRICNUMBER [/tag TAG]…​` <br> e.g., `add /name James Ho /phone 22224444 /email jamesho@example.com /matric A000000 /tag friend`
**Clear** | `clear`
**Delete** | `delete INDEX`<br> e.g., `delete 3`
**Edit** | `edit INDEX [/name NAME] [/phone PHONE_NUMBER] [/email EMAIL] [/matric MATRICNUMBER] [/tag TAG]…​`<br> e.g., `edit 2 /name James Lee /email jameslee@example.com`
**Find** | `find KEYWORD [MORE_KEYWORDS]…​` or `find /phone KEYWORDS` or `find /email KEYWORDS` or `find /matric KEYWORDS`<br> e.g., `find James`, `find /email @u.nus.edu`
**List** | `list -student` or `list -tutorial`<br> e.g., `list -student`
**Select** | `select TUTORIAL_CODE`<br> e.g., `select CS2103T-W12`
**Help** | `help`
**Exit** | `exit`
