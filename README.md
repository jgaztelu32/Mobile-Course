# Notes App – Android Project

A simple Android notes app built with Java in Android Studio. You can create, open, edit and delete notes, each with a different icons.

## Features

- **Create notes** with a title and content
- **Edit notes** by tapping any note in the list
- **Delete notes** with a confirmation (trash icon on each note)
- **Categorize notes** with one of three icons, the selected icon shows up next to the note in the list
- **Compact view toggle**, hide the content preview in the list to see more notes at once or to make the list cleaner
- **Empty state**, a message appears when there are no notes

## Project requirements covered

1. **Functionality with components** — FloatingActionButton, EditText fields, Buttons, ImageButtons, Switch toggle, AlertDialog, Toast messages
2. **Multiple views** — two Activities (MainActivity for the list, NoteDetailActivity for create/edit)
3. **A component to display information** — ListView with a custom BaseAdapter (NoteAdapter) showing each note as a row with icon, title, content and delete button

## How to run the project

### Requirements

- Android Studio
- An Android emulator or a physical device with USB or Wifi debugging enabled

### Steps

1. Clone or download the project folder.
2. Open Android Studio and then select and open the project folder.
3. Android Studio might ask to install missing SDK components, if this is the case, install them by accepting.
4. Once syncing finishes, pick a device in the top right toolbar (emulator or connected phone).
5. Click to run the project in Android Studio.
6. The app installs and launches on the selected device.
