package main.model;

import java.util.Date;

public class Reading {
    private static final String EFFECTIVE = "EFFECTIVE";
    private static final String OVER_DUE = "OVER_DUE";
    private static final String RETURNED = "RETURNED";
    private static final long RENTAL_PERIOD = 1000 * 60 * 60 * 24 * 15;

    private int id;
    private User user;
    private Book book;
    private Date borrowedDate;
    private Date dueDate;
    private Date returnedDate;

    public Reading(User user, Book book, Date borrowedDate) {
        this.user = user;
        this.book = book;
        this.borrowedDate = borrowedDate;
        this.dueDate = new Date(borrowedDate.getTime() + RENTAL_PERIOD);
    }


    public void returnReading() {
        returnedDate = new Date(System.currentTimeMillis());
    }

    public String getStatus() {
        if (returnedDate != null) return RETURNED;
        if (dueDate.getTime() >= System.currentTimeMillis()) return EFFECTIVE;
        return OVER_DUE;
    }


    public User getUser() {
        return user;
    }

    public Book getBook() {
        return book;
    }


    public Date getBorrowedDate() {
        return borrowedDate;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public String getUsername() {
        return user.getUsername();
    }

    public String getBookName() {
        return book.getName();
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public Date getReturnedDate() {
        return returnedDate;
    }

    @Override
    public String toString() {
        return "Reading{" +
                "username='" + user.getUsername() + '\'' +
                ", bookName='" + book.getName() + '\'' +
                ", status='" + getStatus() + '\'' +
                '}';
    }

}
