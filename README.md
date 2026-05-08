# BookTracker

BookTracker is an Android application designed to help readers manage their personal library, track their reading progress, and maintain a history of completed books.

## Summary
The following aspects are covered in this README:
* [Purpose](#purpose) - The main goal of the application.
* [Main Functions](#main-functions) - Key features available to users.
* [Architecture](#architecture) - The structural design and patterns used.
* [Technologies](#technologies) - The stack and libraries integrated into the project.

---

## Purpose
The primary purpose of **BookTracker** is to provide a digital tool for book enthusiasts to digitize their reading habits. It allows users to store details about their books, update their current reading page to see visual progress, and categorize books into "Reading" or "Read" states, ensuring they never lose track of their literary journey.

## Main Functions
* **Library Management**: Add new books with details like title, author, category, description, and total pages.
* **Progress Tracking**: Update the last page read and visualize the completion percentage through a progress bar.
* **Status Filtering**: Easily switch between all books in the library and those already finished using bottom navigation.
* **Edit/Delete Actions**: Modify existing book information or remove books from the collection with a confirmation dialog.
* **Empty State Handling**: Informative views when no books are found in the lists.
* **Persistent Storage**: All data is saved locally using a database, allowing for offline access.

## Architecture
The application is built using the **MVVM (Model-View-ViewModel)** architectural pattern, which promotes a clean separation of concerns and a reactive data flow.

![BookTracker Architecture Diagram](docs/BookTracker%20-%20Architecture%20Diagram.png)

### Structural Components:
* **Data Layer**: 
    * **Room Database**: Handles local data persistence.
    * **BookDao**: Interface for database operations.
    * **OfflineBookRepository**: Abstracted data source that mediates between the database and the UI.
* **UI Layer**:
    * **Fragments**: View controllers (List, Detail, Add/Edit) that observe state from the ViewModel.
    * **Adapters**: Adapter fragments layouts to the ViewHolder.
* **ViewModel Layer**:
    * **BookViewModel**: Shared ViewModel that maintains the UI state and communicates with the repository using Kotlin Flows.

## Technologies
* **Language**: Kotlin
* **Architecture**: MVVM, Repository Pattern
* **Persistence**: Room Database
* **Jetpack Components**: Navigation Component, ViewModel, View Binding
* **Asynchrony**: Kotlin Coroutines and StateFlow
* **UI**: XML Components
