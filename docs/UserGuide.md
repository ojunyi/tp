---
layout: page
title: User Guide
---

**CoursePilot** is a **streamlined desktop application for managing student contacts for university tutors and TAs**, merging the lightning speed of a **Command Line Interface (CLI)** with the visual feedback of a **Graphical User Interface (GUI)**. Built for tutors managing any number of tutorial groups, it offers a fast, reliable way to keep all your student information in one central place that works even without an internet connection.

For those who can type fast, **CoursePilot** transforms student management into a "type-and-done" workflow, allowing you to organize, track, and retrieve student contacts across multiple modules with far greater precision and speed than traditional, click-heavy applications.

* Table of Contents
{:toc}

<div style="page-break-after: always;"></div>

## Quick start

1. Ensure you have Java `17` or above installed in your Computer.<br>
   **Mac users:** Ensure you have the precise JDK version prescribed [here](https://se-education.org/guides/tutorials/javaInstallationMac.html).

1. Download the latest `.jar` file from the project releases.

1. Copy the file to the folder you want to use as the _home folder_ for your CoursePilot.
   * **Note:** Do not place the file in a write-protected folder. CoursePilot needs to be able to write data files to the same folder where the `.jar` file is located. Placing it in a write-protected location will prevent the app from saving data correctly.

1. Open a command terminal, `cd` into the folder you placed the jar file in, and run the following command:
   ```
   java -jar coursepilot.jar
   ```
   > **Note:** Double-clicking the `.jar` file may not work on some systems. If nothing happens when you double-click, use the command above in a terminal instead.

   A GUI similar to the one below should appear within a few seconds. The data that you see on your CoursePilot will not match this one exactly. The app will contain some sample tutorials and students to help you get started.

   ![Ui](images/Ui.png)

1. Type the command in the command box and press Enter to execute it. e.g. typing **`help`** and pressing Enter will open the help window.<br>
   Some example commands you can try:

   * `select CS2103T-W13` : Selects the tutorial `CS2103T-W13` as the current working tutorial.

   * `find Alex` : Finds all students in the selected tutorial who has "Alex" in their name.

   * `list -student` : Lists all students in the selected tutorial.

   * `add -student /name Micheal Jackson /phone 98765532 /email mjackson@example.com /matric A000006` : Adds a student named `Micheal Jackson` to the current tutorial.

   * `delete -student 3` : Deletes the 3rd student shown in the current tutorial's student list.

   * `exit` : Exits the app.

1. Refer to the [Features](#features) below for details of each command.

**Tip:** If you are new, start by using `select` before attempting student-related commands.
This ensures commands like `add -student` and `delete -student` work as expected.
Following this workflow can help avoid common errors.
<div style="page-break-after: always;"></div>

## Understanding the UI

![Ui](images/UiAnnotated.png)

CoursePilot's UI is divided into three linked panels at the top, followed by the Display Box, Command Box, and Current Operating Tutorial indicator at the bottom.

* **Tutorial Code List**: Displays all tutorials in the system sorted alphabetically. The currently selected tutorial is highlighted with a `●` indicator.
* **Tutorial Details Panel**: Displays the day, timeslot, and capacity for each tutorial. Rows correspond directly to the tutorials in the **Tutorial Code List** — the first row in the **Tutorial Code List** matches the first row in the **Tutorial Details Panel**.
* **Student List**: Displays students enrolled in the currently selected tutorial. When no tutorial is selected, all students in the system are shown.
* **Display Box**: Shows the resulting message of each command entered into the **Command Box**.
* **Command Box**: This is where you enter commands.
* **Current Operating Tutorial**: Shown at the bottom right corner, this indicates the currently selected tutorial.
<div style="page-break-after: always;"></div>

## Features

<div markdown="block" class="alert alert-info">

**:information_source: Notes about the command format:**<br>

* Words in `UPPER_CASE` are the parameters to be supplied by the user.<br>
  e.g. in `add -student /name NAME`, `NAME` is a parameter which can be used as `add -student /name John Doe`.

* Items in square brackets are optional.<br>
  e.g. `/name NAME [/tag TAG]` can be used as `/name John Doe /tag friend` or as `/name John Doe`.

* Items with `…`​ after them can be used multiple times including zero times.<br>
  e.g. `[/tag TAG]…​` can be used as ` ` (i.e. 0 times), `/tag friend`, `/tag friend /tag family` etc.

* Parameters can be in any order.<br>
  e.g. if the command specifies `/name NAME /phone PHONE_NUMBER`, `/phone PHONE_NUMBER /name NAME` is also acceptable.

* Extraneous parameters for commands that do not take in parameters (such as `help`, `exit` and `clear`) will be ignored.<br>
  e.g. if the command specifies `help 123`, it will be interpreted as `help`.

* If you are using a PDF version of this document, be careful when copying and pasting commands that span multiple lines as space characters surrounding line-breaks may be omitted when copied over to the application.

</div>

<div markdown="block" class="alert alert-info">

**:bulb: Tip: The Current Operating Tutorial**<br>

Many commands require you to first **select a tutorial** using the `select` command. The selected tutorial becomes your **current operating tutorial**. Commands that operate on students — `add -student`, `delete -student`, `list -student`, and `find` — all act within this tutorial. Use `select TUTORIAL_CODE` to set it. Use `select none` to clear it.

</div>
<div style="page-break-after: always;"></div>

### Viewing help : `help`

Shows a message explaining how to access the help page.

Format: `help`

* A pop-up window with a link to the User Guide will appear.
![HelpCommand](images/HelpCommand.png)
<div style="page-break-after: always;"></div>

### Selecting a tutorial : `select`

Sets a tutorial as the **current operating tutorial**. Once selected, student-level commands (`add -student`, `delete -student`, `list -student`, `find`) will operate within this tutorial.

Format: `select TUTORIAL_CODE` or `select none`

* The `TUTORIAL_CODE` is case-insensitive.
* The tutorial code must exactly match a tutorial already in the system (e.g., `CS2103T-W13`).
* The tutorial remains active until you run another `select` command or `select none`.
* If the tutorial code is not found, an error message is shown and the current operating tutorial remains unchanged.
* Use `select none` to clear the current operating tutorial without selecting a new one.

Examples:
* `select CS2103T-W13` : Selects the tutorial with code `CS2103T-W13`.
* `select cs2103t-w13` : Also selects the same tutorial (case-insensitive).
* `select none` : Clears the current operating tutorial.
* `select INVALID` : No tutorial found with code: `INVALID`

![SelectCommand](images/SelectCommand.png)
<div style="page-break-after: always;"></div>

### Listing tutorials or students : `list`

Lists either the details of all available tutorials or all students in the currently selected tutorial.

Format: `list -tutorial` or `list -student`

**You must specify either `-tutorial` or `-student`.**

* `list -tutorial` : Shows all available tutorials details (day, time slot, capacity).
* `list -student` : Shows all students enrolled in the currently selected tutorial.
  * If a tutorial is selected, students in the **current operating tutorial** will be shown.
  * If no tutorial is selected, all students in the system will be shown.

Examples:
* `list -tutorial` : Displays all tutorials.
* `list -student` : Displays all students in the system.
* `select CS2103T-W13` followed by `find dave` then `list -student` : Displays all students in the CS2103T-W13 tutorial.

![ListCommand](images/ListCommand.png)
<div style="page-break-after: always;"></div>

### Adding a student or tutorial : `add`

Adds a student to the **current operating tutorial**, or adds a new tutorial to the system.

#### Adding a student: `add -student`

Format: `add -student /name NAME /phone PHONE_NUMBER /email EMAIL /matric MATRICNUMBER [/tag TAG]…​`

* Requires a tutorial to be selected first.
* The fields (`/name`, `/phone`, `/email`, `/matric`) are mandatory.
* A student can have any number of tags (including 0).
* If the student does not yet exist in the system, they are also added to the global student list. If they already exist (matched by matric number), they are linked to the selected tutorial without creating a duplicate.
* A student cannot be added to the same tutorial twice.

**Field Constraints:**
* **Name**: Must contain at least one alphabetic character. Spaces and symbols such as commas, hyphens, forward slashes, and `@` are allowed (e.g., `O'Brien`, `Anne-Marie`, `Ravi s/o Kumar`). Cannot be blank. Maximum 100 characters long.
* **Phone**: Must contain only digits and be at least 3 digits long to a maximum of 15 digits long.
* **Email**: Must follow standard email format (e.g., `student@u.nus.edu`). Maximum 100 characters long.
* **Matric Number**: Can be any non-blank value. CoursePilot does not enforce a fixed format because the target audience includes tutors working with different matriculation number schemes.
* **Tag**: Optional. Each tag must be a single alphanumeric word (no spaces or special characters). Maximum 30 characters long.
<div style="page-break-after: always;"></div>

Examples:
* `add -student /name John Doe /phone 98765432 /email johnd@example.com /matric A000000`
* `add -student /name Betsy Crowe /tag friend /email betsycrowe@example.com /matric A000001 /phone 1234567 /tag student`
* `add -student /name David Li /phone 91031282 /email lidavid@example.com /matric A000003`

![AddCommandStudent](images/AddCommandStudent.png)

#### Adding a tutorial: `add -tutorial`

Format: `add -tutorial /code CODE /day DAY /timeslot TIMESLOT /capacity CAPACITY`

* All four fields are mandatory.
* Does not require a tutorial to be selected first.
* The tutorial code must be unique.

**Field Constraints:**
* **Code**: Must contain only alphanumeric characters, hyphens, and underscores. Cannot be blank. Maximum 20 characters long.
* **Day**: Must be one of: Mon, Tue, Wed, Thu, Fri, Sat, Sun (case-insensitive).
* **TimeSlot**: Must follow the format `HH:mm-HH:mm` where `H` is the hour number, and `m` is the minute number (e.g., `13:00-14:00`) ranging only from `00:00 to 23:59`. The start time must be before the end time, and both must **occur within the same calendar day**. Time is in 24-hour format. A new tutorial's timeslot cannot overlap with any existing tutorial's timeslot on the same day (e.g., if `CS2103T-W12` runs `10:00-11:00` on `Wed`, adding another tutorial on `Wed` with timeslot `10:30-11:30` will be rejected).
* **Capacity**: Must be a positive whole number starting from 1 to 1000.
<div style="page-break-after: always;"></div>

Examples:
* `add -tutorial /code CS2103T-T01 /day Thu /timeslot 10:00-11:00 /capacity 30`
* `add -tutorial /code CS2103T-W16 /day Thu /timeslot 14:00-15:00 /capacity 15`

![AddCommandTutorial](images/AddCommandTutorial.png)
<div style="page-break-after: always;"></div>

### Editing a student : `edit`

Edits an existing student's details.

Format: `edit INDEX [/name NAME] [/phone PHONE_NUMBER] [/email EMAIL] [/matric MATRICNUMBER] [/tag TAG]…​`

* Requires a tutorial to be selected first.
* Edits the student at the specified `INDEX`. The index refers to the index number shown in the **currently displayed student list**. The index **must be a positive integer** 1, 2, 3, …​
* At least one of the optional fields must be provided.
* Existing values will be updated to the input values.
* When editing tags, the existing tags of the student will be **replaced entirely** (not added to).
* You can remove all the student's tags by typing `/tag` without specifying any tag after it.
* The edit applies globally - it updates the student's details everywhere in the system.

Examples:
* `edit 1 /phone 91234567 /email johndoe@example.com` : Edits the phone number and email address of the 1st student.
* `edit 2 /name Betsy Crower /tag` : Edits the name of the 2nd student and clears all existing tags.
* `edit 3 /name David Li Hao Jun` : Edits the name of the 3rd student and leaves all existing tags untouched.

![EditCommand](images/EditCommand.png)
<div style="page-break-after: always;"></div>

### Locating students : `find`

Finds and lists students in the currently selected tutorial who match the given search criteria.

Format: `find KEYWORD [MORE_KEYWORDS]…` or `find /phone KEYWORD [MORE_KEYWORDS]…` or `find /email KEYWORD [MORE_KEYWORDS]…` or `find /matric KEYWORD [MORE_KEYWORDS]…`

* **Name search (default)**: `find KEYWORD [MORE_KEYWORDS]` — returns students whose name contains any of the keywords as whole words. Case-insensitive.
* **Phone search**: `find /phone KEYWORD [MORE_KEYWORDS]…` — returns students whose phone number **starts with** any of the given keywords.
* **Email search**: `find /email KEYWORD [MORE_KEYWORDS]…` — returns students whose email address **contains** any of the given keywords. Case-insensitive.
* **Matric search**: `find /matric KEYWORD [MORE_KEYWORDS]…` — returns students whose matric number **starts with** any of the given keywords. Case-insensitive.
* When multiple keywords are provided, students matching **at least one** keyword are returned.
* If a tutorial has been selected, only students enrolled in the **current operating tutorial** are searched.
* If no tutorial is selected, the search is performed across all students in the system.

Examples:
* `find John` : Finds all students in the current tutorial whose name contains "John".
* `find alex david` : Returns students whose name contains "alex" or "david".
* `find /email u.nus.edu` : Finds all students whose email contains `u.nus.edu`.
* `find /matric A000` : Finds students whose matric number starts with `A000`.
* `find /phone 992` : Finds students whose phone number starts with `992`.

![FindCommand](images/FindCommand.png)
<div style="page-break-after: always;"></div>

### Deleting a student or tutorial : `delete`

Deletes a student from the current tutorial, or deletes a tutorial from the system.

#### Deleting a student: `delete -student`

Format: `delete -student INDEX`

* Requires a tutorial to be selected first.
* `INDEX` refers to the position in the **current tutorial's student list**. The index **must be a positive integer** 1, 2, 3, …​
* The student is removed from the current tutorial.
* If the student is **not enrolled in any other tutorial**, they are also removed from the global student list entirely.
* If the student **is enrolled in another tutorial**, they remain in the system and in those other tutorials.

Examples:
* `find John` followed by `delete -student 1` : Deletes the 1st student in the results of the `find` command.
* `delete -student 2` : Deletes the 2nd student in the current tutorial.

![DeleteCommandStudent](images/DeleteCommandStudent.png)
<div style="page-break-after: always;"></div>

#### Deleting a tutorial: `delete -tutorial`

Format: `delete -tutorial INDEX`

* Does not require a tutorial to be selected first.
* `INDEX` refers to the position in the **displayed tutorial list**. The index **must be a positive integer** 1, 2, 3, …​
* The tutorial is removed from the system.
* Students who were enrolled in the deleted tutorial remain in any other tutorials they are enrolled in.
* If the student is no longer enrolled in any other tutorial after this deletion, they will also be removed from the global student list entirely, as CoursePilot does not allow students to exist without being enrolled in at least one tutorial.

Examples:
* `delete -tutorial 2` : Deletes the 2nd tutorial in the list.

![DeleteCommandTutorial](images/DeleteCommandTutorial.png)
<div style="page-break-after: always;"></div>

### Clearing all entries : `clear`

Clears **all students and all tutorials** from the system.

Format: `clear`

* Running `clear` will also reset the current operating tutorial selection. You will need to use `select` again after clearing if you wish to perform student-level operations.

<div markdown="span" class="alert alert-warning">:exclamation: **Caution:**
This command permanently deletes all students and all tutorials. This action cannot be undone. Use with care.
</div>

![ClearCommand](images/ClearCommand.png)
<div style="page-break-after: always;"></div>

### Exiting the program : `exit`

Exits the program.

Format: `exit`

![ExitCommand](images/ExitCommand.png)
<div style="page-break-after: always;"></div>

### Command Autocomplete

CoursePilot provides context-aware autocomplete suggestions as you type in the command box. Suggestions appear in a dropdown menu above the input field.

* **Command words**: Start typing and matching commands (e.g., `add`, `delete`, `list`) are suggested.
* **Flags**: After typing a command word, relevant flags are suggested (e.g., `-student`, `-tutorial` for `add`).
* **Prefixes**: After selecting a flag, the required parameter prefixes are suggested (e.g., `/name`, `/phone`, `/email`, `/matric` for `add -student`). Already-used prefixes are excluded from suggestions, except `/tag` which can be used multiple times.

**Keyboard shortcuts:**
* <kbd>Tab</kbd> : Accepts the selected suggestion.
* <kbd>Enter</kbd> : Accepts the selected suggestion.
* <kbd>Escape</kbd> : Dismisses the suggestion menu.
* You can also click on any suggestion to select it.

Autocomplete suggestions are updated dynamically as you type, helping to reduce input errors and improve efficiency.

### Saving the data

CoursePilot automatically saves all data after any command that modifies data. There is no need to save manually.

The data is saved in a JSON file located at `[JAR file location]/data/coursepilot.json`.

---

### Editing the data file

Advanced users can directly edit the data file to make bulk changes. The data file is stored as `[JAR file location]/data/coursepilot.json` in JSON format.

<div markdown="span" class="alert alert-warning">:exclamation: **Caution:**
If your changes to the data file make its format invalid, CoursePilot will discard all data and start with an empty data file on the next run. Ensure you make a backup before editing and thoroughly validate the JSON format after making changes.<br>
Furthermore, manual edits can cause CoursePilot to behave unexpectedly if invalid data is introduced. Only edit the data file if you are confident in your ability to maintain valid JSON structure and data constraints.
</div>
<div style="page-break-after: always;"></div>

## FAQ

**Q**: How do I transfer my data to another computer?<br>
**A**: Install CoursePilot on the other computer and overwrite the empty data file it creates with the `coursepilot.json` file from your previous installation.

**Q**: What happens if I forget to select a tutorial before using write operations like `add -student` or `delete -student`?<br>
**A**: CoursePilot will display an error message: "No tutorial selected. Please select a tutorial to operate on first." Use the `select` command to choose a tutorial before retrying.

**Q**: Can I add a student without selecting a tutorial first?<br>
**A**: No. Students must be added to a tutorial using `add -student` while a tutorial is selected. Use `select TUTORIAL_CODE` first, then `add -student`. This ensures every student is organised under a tutorial from the moment they are added.

**Q**: What happens to a student's data when I delete them from a tutorial?<br>
**A**: If the student is enrolled in other tutorials, they remain in the system. If the deleted tutorial was their only one, they are removed from the global student list as well.

**Q**: Does deleting a tutorial delete its students?<br>
**A**: Students who are in other tutorials remain in the system. Students who were only in the deleted tutorial are removed from the global student list.

**Q**: What should I do if I enter an invalid command?<br>
**A**: CoursePilot will display an error message indicating what went wrong. Use the `help` command to view the correct command format.

**Q**: When should I use `select none`?<br>
**A**: Use `select none` when you want to stop working within a specific tutorial without selecting a new one. This clears the current operating tutorial. Note that `add -student`, `delete -student` and `edit` require a tutorial to be selected and will show an error if used after `select none`. It is useful when you want to use `list -student` or `find` to search across all students in the system.

## Known issues

1. **When using multiple screens**, if you move the application to a secondary screen, and later switch to using only the primary screen, the GUI will open off-screen. The remedy is to delete the `preferences.json` file created by the application before running the application again.
2. **If you minimize the Help Window** and then run the `help` command (or use the `Help` menu, or the keyboard shortcut `F1`) again, the original Help Window will remain minimized, and no new Help Window will appear. The remedy is to manually restore the minimized Help Window.

<div style="page-break-after: always;"></div>

## Known limitations

1. **Matric number validation is left intentionally flexible** to support university tutors and teaching assistants globally. CoursePilot accepts any format - whether numeric, alphanumeric, or containing symbols - as long as the field is not empty. As a tool designed for personal data management, we prioritize versatility, trusting users to maintain the accuracy of their student records across different institutional standards. To justify this position, research into student IDs worldwide identified various formats, such as `A0123456X` (Singapore), `987654321` (USA) and `2024-567-89` (Canada). 
2. **Duplicate phone numbers is not supported** in CoursePilot as we do not support country code prefixes. This means that if two students from different countries share the same number but have different country codes, one of them cannot be added. The suggested work around is to add the country code at the front, but CoursePilot will not help you differentiate between country codes and phone number which still may cause some confusion.
3. **Email validation is intentionally lenient** and accepts unconventional formats such as `11@11`. Since CoursePilot is designed for personal use, we trust tutors to enter accurate information without needing strict formatting rules that may inadvertently reject valid institutional email formats.
4. **Tags must not contain spaces** to ensure they remain concise and scannable. While alphanumeric characters (e.g., `AY2526`, `Year2` or `Y3`) are supported, spaces and special characters are not permitted.
5. **Phone number and email address are required fields when adding a student** as CoursePilot is designed to serve as a contact book for tutors, making these fields central to its purpose. A student entry without contact details would defeat the core value of the application.
6. **Tutorial days must be entered as a 3-letter abbreviation** (e.g.,`Mon`, `Tue`, `Wed`, `Thu`, `Fri`, `Sat` or `Sun`). The input is case-insensitive. We do not allow inputs such as `Monday` or `Tuesday`. This standardised format ensures consistency across all tutorial entries and keeps the display clean and uniform.
7. **Tutorial timeslot** must follow the format `HH:mm-HH:mm` where `H` is the hour number, and `m` is the minute number (e.g., `13:00-14:00`) ranging only from `00:00 to 23:59`. The start time must be before the end time, and both must **occur within the same calendar day**. Time is in 24-hour format.
8. **Tutorial timeslots does not allow crossing over to the next day** as in our reserach into typical lesson timings worldwide, there does not exists any tutorial lesson that takes place in local date time over the period of two or more days. But there is a work around in case such a niche case occurs. You can create multiple tutorial slots, appending `-1` or `-2` and so on to indicate that the tutorials are linked. Then make sure the time of those tutorial slots link up with one another.
9. **`list -tutorial` does not produce a visible change** in most cases, as CoursePilot currently does not support filtering the tutorial list. The tutorial panel always shows all tutorials. The command is best used to reset the student list back to the full global view after a `find` or `list -student` operation.
10. **Searching by tag is not currently supported** and the `find` command only allows search by name, phone, email and matric number.

<div style="page-break-after: always;"></div>

## Command summary

Action | Format, Examples
--------|------------------
**Add student** | `add -student /name NAME /phone PHONE_NUMBER /email EMAIL /matric MATRICNUMBER [/tag TAG]…​` <br> e.g., `add -student /name James Ho /phone 22224444 /email jamesho@example.com /matric A000000 /tag friend`
**Add tutorial** | `add -tutorial /code CODE /day DAY /timeslot TIMESLOT /capacity CAPACITY` <br> e.g., `add -tutorial /code CS2103T-W12 /day Wed /timeslot 10:00-11:00 /capacity 10`
**Clear** | `clear`
**Delete student** | `delete -student INDEX` <br> e.g., `delete -student 3`
**Delete tutorial** | `delete -tutorial INDEX` <br> e.g., `delete -tutorial 1`
**Edit** | `edit INDEX [/name NAME] [/phone PHONE_NUMBER] [/email EMAIL] [/matric MATRICNUMBER] [/tag TAG]…​` <br> e.g., `edit 2 /name James Lee /email jameslee@example.com`
**Find** | `find KEYWORD [MORE_KEYWORDS]…​` or `find /phone KEYWORD` or `find /email KEYWORD` or `find /matric KEYWORD` <br> e.g., `find James`, `find /email u.nus.edu`
**List students** | `list -student`
**List tutorials** | `list -tutorial`
**Select** | `select TUTORIAL_CODE` or `select none` <br> e.g., `select CS2103T-W12`, `select none`
**Help** | `help`
**Exit** | `exit`
