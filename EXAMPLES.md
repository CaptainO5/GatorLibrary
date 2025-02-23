# Library System Examples

## Example 1

### Input
~~~
InsertBook(1, "Book1", "Author1", "Yes")
PrintBook(1)
BorrowBook(101, 1, 1)
InsertBook(2, "Book2", "Author2", "Yes")
BorrowBook(102, 1, 2)
PrintBooks(1, 2)
ReturnBook(101, 1)
Quit()
~~~
### Output
~~~
BookID = 1
Title = "Book1"
Author = "Author1"
Availability = "Yes"
BorrowedBy = None
Reservations = []

Book 1 Borrowed by Patron 101

Book 1 Reserved by Patron 102

BookID = 1
Title = "Book1"
Author = "Author1"
Availability = "No"
BorrowedBy = 101
Reservations = [102]

BookID = 2
Title = "Book2"
Author = "Author2"
Availability = "Yes"
BorrowedBy = None
Reservations = []

Book 1 Returned by Patron 101

Book 1 Allotted to Patron 102

Program Terminated!!
~~~
## Example 2

### Input
~~~
InsertBook(1, "Book1", "Author1", "Yes")
BorrowBook(101, 1, 1)
BorrowBook(102, 1, 2)
BorrowBook(106, 1, 4)
BorrowBook(505, 1, 5)
ReturnBook(101, 1)
Quit()
~~~
### Output
~~~
Book 1 Borrowed by Patron 101

Book 1 Reserved by Patron 102

Book 1 Reserved by Patron 106

Book 1 Reserved by Patron 505

Book 1 Returned by Patron 101

Book 1 Allotted to Patron 102

Program Terminated!!
~~~
