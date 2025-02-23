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
## Example 3

### Input
~~~
InsertBook(4, "Book4", "Author1", "Yes")
InsertBook(2, "Book2", "Author1", "Yes")
BorrowBook(2001, 2, 3)
InsertBook(5, "Book5", "Author3", "Yes")
BorrowBook(3002, 2, 1)
PrintBook(2)
BorrowBook(3002, 5, 1)
BorrowBook(1003, 2, 4)
PrintBook(4)
BorrowBook(2010, 4, 2)
PrintBooks(2, 5)
BorrowBook(2010, 2, 2)
BorrowBook(1004, 2, 4)
ReturnBook(2001, 2)
ReturnBook(2010, 4)
FindClosestBook(3)
InsertBook(3, "Book3", "Author4", "Yes")
FindClosestBook(3)
DeleteBook(2)
ColorFlipCount()
Quit()
PrintBook(4)
BorrowBook(2)
ReturnBook(1003, 2)
~~~
### Output
~~~
Book 2 Borrowed by Patron 2001

Book 2 Reserved by Patron 3002

BookID = 2
Title = "Book2"
Author = "Author1"
Availability = "No"
BorrowedBy = 2001
Reservations = [3002]

Book 5 Borrowed by Patron 3002

Book 2 Reserved by Patron 1003

BookID = 4
Title = "Book4"
Author = "Author1"
Availability = "Yes"
BorrowedBy = None
Reservations = []

Book 4 Borrowed by Patron 2010

BookID = 2
Title = "Book2"
Author = "Author1"
Availability = "No"
BorrowedBy = 2001
Reservations = [3002, 1003]

BookID = 4
Title = "Book4"
Author = "Author1"
Availability = "No"
BorrowedBy = 2010
Reservations = []

BookID = 5
Title = "Book5"
Author = "Author3"
Availability = "No"
BorrowedBy = 3002
Reservations = []

Book 2 Reserved by Patron 2010

Book 2 Reserved by Patron 1004

Book 2 Returned by Patron 2001

Book 2 Allotted to Patron 3002

Book 4 Returned by Patron 2010

BookID = 2
Title = "Book2"
Author = "Author1"
Availability = "No"
BorrowedBy = 3002
Reservations = [2010, 1003, 1004]

BookID = 4
Title = "Book4"
Author = "Author1"
Availability = "Yes"
BorrowedBy = None
Reservations = []

BookID = 3
Title = "Book3"
Author = "Author4"
Availability = "Yes"
BorrowedBy = None
Reservations = []

Book 2 is no longer available. Reservations made by Patrons 2010, 1003, 1004 have been cancelled!

Colour Flip Count: 3

Program Terminated!!
~~~
