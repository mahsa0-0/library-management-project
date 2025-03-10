package database;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

import data.model.Member;
import data.model.Book;
import util.DateConverter;

import javafx.scene.control.Label;
// The dataHelper class provides utility methods for managing member and book data in CSV files.
public class dataHelper {
    //Checks if a member with the given ID exists in the members.csv file.
    public static boolean isMemberExists(String id) {
        try {
            BufferedReader br = new BufferedReader(new FileReader("src/resources/dataBase/members.csv"));
            String line;
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] arr = line.split(",");
                if (arr[0].equals(id)) {
                    return true;
                }
            }
            br.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return false;
    }
//Checks if a book with the given ID exists in the books.csv file
    public static boolean isBookExists(String bookId) {
        try {
            BufferedReader br = new BufferedReader(new FileReader("src/resources/dataBase/books.csv"));
            String line;
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] arr = line.split(",");
                if (arr[0].equals(bookId)) {
                    return true;
                }
            }
            br.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return false;
    }
//Inserts a new member into the members.csv file.
    public static boolean insertNewMember(Member member) {
        try {
            FileWriter fw = new FileWriter("src/resources/dataBase/members.csv", true);
            PrintWriter printWriter = new PrintWriter(fw);
            printWriter.printf("%s,%s,%s,%s,%s\n", member.getStdNumber(), member.getName(), member.getLevel(), member.getDepartment(), member.getPassword());
            printWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return false;
    }
//Inserts a new book into the books.csv file.
    public static boolean insertNewBook(Book book) {
        try {
            FileWriter fw = new FileWriter("src/resources/dataBase/books.csv", true);
            PrintWriter printWriter = new PrintWriter(fw);
            printWriter.printf("%s,%s,%s,%s\n", book.getId(), book.getName(), book.getCategory(), book.getAvailability());
            printWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return false;
    }
//Checks if a book is already issued based on its ID and availability status.
    public static boolean isBookIssued(String bookId) {
        try {
            BufferedReader br = new BufferedReader(new FileReader("src/resources/dataBase/books.csv"));
            String line;
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] arr = line.split(",");
                if (arr[0].equals(bookId) && arr[3].equals("ناموجود")) {
                    return true;
                }
            }

            br.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return false;
    }
//Issues a book by adding an entry to the issuedBook.csv file and updating the book's status.
    public static boolean issueBook(String bookId, String memberId, String issueDate, String returnDate) {
        try {
            FileWriter fw = new FileWriter("src/resources/dataBase/issuedBook.csv", true);
            PrintWriter printWriter = new PrintWriter(fw);
            printWriter.printf("%s,%s,%s,%s\n", bookId, memberId, issueDate, returnDate);
            printWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String fileName = "src/resources/dataBase/books.csv";
        updateBookStatus(bookId, fileName);
        return false;
    }
//Updates the status of a book in the books.csv file to "ناموجود".
    private static void updateBookStatus(String bookId, String fileName) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(fileName));
            String line;
            StringBuilder sb = new StringBuilder();
            while ((line = br.readLine()) != null) {
                String[] arr = line.split(",");
                if (arr[0].equals(bookId)) {
                    sb.append(arr[0]).append(",").append(arr[1]).append(",").append(arr[2]).append(",").append("ناموجود").append("\n");
                } else {
                    sb.append(line).append("\n");
                }
            }
            br.close();
            FileOutputStream fos = new FileOutputStream("src/resources/dataBase/books.csv");
            fos.write(sb.toString().getBytes());
            fos.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    // Utility class to retrieve and manage information about a specific issued book.
    // Fields to store book, user, and issue-related details.
    public static class showIssuedBookData {
        public String bookId;
        public String userId;
        public String issueDate;
        public String userName;
        public String userLevel;
        public String userDepartment;

        public String bookName;
        public String bookAvailablity;
        public String bookCategory;
        public String returnDate;
        public LocalDate returnDateTemp; //Temporary storage for date calculations

        public showIssuedBookData(String bookId) {
            this.bookId = bookId;
            readIssueData();
            readUserData();
            readBookData();
        }
        // Reads issue data from the "issuedBook.csv" file.
        public void readIssueData() {
            try {
                BufferedReader br = new BufferedReader(new FileReader("src/resources/dataBase/issuedBook.csv"));
                String line;
                br.readLine();
                while ((line = br.readLine()) != null) {
                    String[] arr = line.split(",");
                    if (arr[0].equals(bookId)) {
                        this.userId = arr[1];
                        this.issueDate = arr[2];
                        returnDateTemp = LocalDate.parse(issueDate);// Parse issue date
                        // Calculate return date by adding the issued period (in days)
                        this.returnDate = returnDateTemp.plusDays(Long.parseLong(arr[3])).toString();
                        break;
                    }
                }
                br.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        // Reads user data from the "members.csv" file.
        public void readUserData() {
            try {
                BufferedReader br = new BufferedReader(new FileReader("src/resources/dataBase/members.csv"));
                String line;
                br.readLine();
                while ((line = br.readLine()) != null) {
                    String[] arr = line.split(",");
                    if (arr[0].equals(userId)) {
                        this.userName = arr[1];
                        this.userLevel = arr[2];
                        this.userDepartment = arr[3];
                        break;
                    }
                }
                br.close();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
        // Reads book data from the "books.csv" file.
        public void readBookData() {
            try {
                BufferedReader br = new BufferedReader(new FileReader("src/resources/dataBase/books.csv"));
                String line;
                br.readLine();
                while ((line = br.readLine()) != null) {
                    String[] arr = line.split(",");
                    if (arr[0].equals(bookId)) {
                        this.bookName = arr[1];
                        this.bookCategory = arr[2];
                        this.bookAvailablity = arr[3];
                        break;
                    }
                }
                br.close();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }

        public String getBookId() {
            return bookId;
        }

        public String getUserId() {
            return userId;
        }

        public String getIssueDate() {
            issueDate = DateConverter.GregorianToJalali((issueDate).split("-")[0], (issueDate).split("-")[1], (issueDate).split("-")[2]);
            return issueDate;
        }

        public String getUserName() {
            return userName;
        }

        public String getUserLevel() {
            return userLevel;
        }

        public String getUserDepartment() {
            return userDepartment;
        }

        public String getBookName() {
            return bookName;
        }

        public String getBookAvailability() {
            return bookAvailablity;
        }

        public String getBookCategory() {
            return bookCategory;
        }
        // Getter method for return date (calculates days remaining or overdue)
        public String getReturnDate() {
            int remainingDays = (int) ChronoUnit.DAYS.between(LocalDate.now(), LocalDate.parse(returnDate));
            returnDate = DateConverter.GregorianToJalali((returnDate).split("-")[0], (returnDate).split("-")[1], (returnDate).split("-")[2]);
            if (remainingDays < 0) {
                return returnDate + "    [ ( " + ((-1) * remainingDays) + " ) روز گذشته است ]";
            }
            return returnDate + "    [ ( " + remainingDays + " ) روز باقی مانده است ]";
        }
        // Alternate getter method for return date with less detailed status
        public String getReturnDateIssuedList() {
            int remainingDays = (int) ChronoUnit.DAYS.between(LocalDate.now(), LocalDate.parse(returnDate));
            returnDate = DateConverter.GregorianToJalali((returnDate).split("-")[0], (returnDate).split("-")[1], (returnDate).split("-")[2]);
            if (remainingDays < 0) {
                return returnDate + " ( " + remainingDays + " ) ";
            }
            return returnDate + " ( " + remainingDays + " ) ";
        }
    }
    // Utility class to retrieve and manage data about a specific user.
    public static class showUserData {
        String stdNumber, name, level, department;

        public showUserData(String id) {
            try {
                BufferedReader br = new BufferedReader(new FileReader("src/resources/dataBase/members.csv"));
                String line;
                br.readLine();
                while ((line = br.readLine()) != null) {
                    String[] arr = line.split(",");
                    if (arr[0].equals(id)) {
                        stdNumber = arr[0];
                        name = arr[1];
                        level = arr[2];
                        department = arr[3];
                        //return;
                    }
                }
                br.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        public String getUserId() {
            return stdNumber;
        }

        public String getUserName() {
            return name;
        }

        public String getUserLevel() {
            return level;
        }

        public String getUserDepartment() {
            return department;
        }


    }
    // Utility class to retrieve and manage data about a specific book.
    public static class showBookdata {
        String id, name, category, availability;

        public showBookdata(String id) {
            try {
                BufferedReader br = new BufferedReader(new FileReader("src/resources/dataBase/books.csv"));
                String line;
                br.readLine();
                while ((line = br.readLine()) != null) {
                    String[] arr = line.split(",");
                    if (arr[0].equals(id)) {
                        this.id = arr[0];
                        this.name = arr[1];
                        this.category = arr[2];
                        this.availability = arr[3];
                    }
                }

                br.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        public String getBookId() {
            return id;
        }

        public String getBookName() {
            return name;
        }

        public String getBookCategory() {
            return category;
        }

        public String getBookAvailability() {
            return availability;
        }
    }

    public static boolean returnBook(String bookId) {
        try {
            BufferedReader br = new BufferedReader(new FileReader("src/resources/dataBase/issuedBook.csv"));
            String line;
            StringBuilder sb = new StringBuilder();
            while ((line = br.readLine()) != null) {
                String[] arr = line.split(",");
                if (arr[0].equals(bookId)) {
                    sb.append("");
                } else {
                    sb.append(line).append("\n");
                }
            }
            br.close();
            FileOutputStream fos = new FileOutputStream("src/resources/dataBase/issuedBook.csv");
            fos.write(sb.toString().getBytes());
            fos.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            BufferedReader br = new BufferedReader(new FileReader("src/resources/dataBase/books.csv"));
            String line;
            StringBuilder sb = new StringBuilder();
            while ((line = br.readLine()) != null) {
                String[] arr = line.split(",");
                if (arr[0].equals(bookId)) {
                    sb.append(arr[0]).append(",").append(arr[1]).append(",").append(arr[2]).append(",").append("موجود").append("\n");
                } else {
                    sb.append(line).append("\n");
                }
            }
            br.close();
            FileOutputStream fos = new FileOutputStream("src/resources/dataBase/books.csv");
            fos.write(sb.toString().getBytes());
            fos.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    public static void currentUser(String userId, String userName) {
        try {
            FileWriter fw = new FileWriter("src/resources/dataBase/currentUser.csv");
            PrintWriter printWriter = new PrintWriter(fw);
            printWriter.printf("%s,%s\n", userId, userName);
            printWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    // Utility class to manage and display the current logged-in user information.
    public static class showCurrentUser {
        String userId, userName;

        public showCurrentUser(Label showUserId, Label showUserName) {
            readData();
            showUserId.setText(userId);
            showUserName.setText(userName);
        }

        public showCurrentUser() {
            readData();
        }

        public void readData() {
            try {
                BufferedReader br = new BufferedReader(new FileReader("src/resources/dataBase/currentUser.csv"));
                String line;
                while ((line = br.readLine()) != null) {
                    String[] arr = line.split(",");
                    this.userId = arr[0];
                    this.userName = arr[1];
                }
                br.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        public String getUserId() {
            return userId;
        }

        public String getUserName() {
            return userName;
        }

    }

}
