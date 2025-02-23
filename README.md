# Gator Library System

A Java-based library management system that allows users to insert, borrow, return, delete, and search for books using a Red-Black Tree for efficient storage and retrieval.

## Features
- **Book Management**: Add, remove, and search for books.
- **Borrow & Return**: Handle book borrowing and returns with a priority-based reservation system.
- **Efficient Storage**: Uses a **Red-Black Tree** for book storage and a **Priority Queue** for reservations.
- **Command Processing**: Reads commands from a file and executes library operations.

## Project Structure
- `Book.java` – Represents a book with details such as ID, title, author, and reservation queue.
- `PriorityQueue.java` – Implements a min-heap priority queue for managing book reservations.
- `RedBlackTree.java` – Implements a Red-Black Tree for book storage and fast retrieval.
- `gatorLibrary.java` – Main program that processes commands from an input file.

## Usage
Compile and run:
```sh
javac *.java
java gatorLibrary input_file.txt
