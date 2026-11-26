# 🎓 Student Record Manager

A console-based student record manager written in Java. Users can add students, view all records in a formatted table, search by ID, remove students and list the unique courses. Records are kept in memory while the program runs.

## Features

- **Add student** – enter a unique ID, name, course and age; names and courses are auto-formatted to Title Case
- **View students** – bordered table sorted by ID, with column widths that adjust to the data and a total count
- **Search student** – look up a student by ID and see the full details
- **Remove student** – delete a record by ID
- **Unique courses** – list every distinct course across all students
- **Input validation** – rejects non-numeric input, non-positive or duplicate IDs, invalid names, empty courses and ages outside 10-100
- **Custom exception** – `StudentNotFoundException` for IDs that do not exist

## Validation Rules

| Field | Rule |
|-------|------|
| Student ID | Positive whole number, must be unique |
| Name | Letters and spaces only |
| Course | Cannot be empty |
| Age | Between 10 and 100 |

## Java Concepts Used

| Concept | Where it is used |
|---------|------------------|
| `HashMap` | Stores students with the ID as the unique key |
| `TreeSet` | Gives IDs in sorted order when viewing students |
| `HashSet` | Removes duplicate course names automatically |
| `StringBuilder` | Builds the table, detail view, course list and Title Case text |
| Custom checked exception | `StudentNotFoundException` |
| Exception handling | `NumberFormatException`, `IllegalArgumentException` and the custom exception are caught in `main` |

## Project Structure

```
Task6/
└── Task6.java    # Menu, Student class, custom exception and all operations
```

## Getting Started

### Prerequisites

- Java Development Kit (JDK) 8 or later

### Compile

```bash
javac Task6.java
```

### Run

```bash
java Task6
```

## Usage

```
===== Student Record Manager =====
1. Add Student
2. View Students
3. Search Student
4. Remove Student
5. Display Unique Courses
6. Exit
Enter your choice:
```

Example flow: add a few students, view them in the table, search one by ID, remove one, then display the unique courses.

### Sample Output (View Students)

```
+----+-------------+---------+-----+
| ID | Name        | Course  | Age |
+----+-------------+---------+-----+
| 1  | Amey Naik   | Java    | 20  |
| 2  | Rahul Patil | Python  | 21  |
| 3  | Sneha Rao   | Java    | 19  |
+----+-------------+---------+-----+
Total students: 3
```

## Limitations

- Data is stored in memory only, so all records are lost when the program exits
- Console interface only
- Course names are compared as text, so "Java" and "Java Programming" count as different courses
- The order of courses in the unique list is not guaranteed (`HashSet`)

## Future Improvements

- Save and load records from a file or database
- Update/edit an existing student record
- Sort or filter students by name, course or age
- GUI (Swing/JavaFX)
- Unit tests for the validation and record logic

## Author

Amey Naik – [@Ameynaik2005](https://github.com/Ameynaik2005)

Built as the Week 4 mini project during the internship at InternNova.
