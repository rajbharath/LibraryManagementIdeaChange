package main.model;

import java.util.List;
import java.util.stream.Collectors;

public class Book {

    private int id;
    private String name;
    private List<Author> authors;
    private Publisher publisher;
    private BookStatus bookStatus;

    public Book(String name, List<Author> authors, Publisher publisher) throws Exception {
        this.name = name;
        this.authors = authors;
        this.publisher = publisher;
        bookStatus = BookStatus.AVAILABLE;
        BookValidator.validate(this);
    }

    public boolean isAvailable() {
        return bookStatus.equals(BookStatus.AVAILABLE);
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setBookStatus(BookStatus bookStatus) {
        this.bookStatus = bookStatus;
    }

    public BookStatus getBookStatus() {
        return bookStatus;
    }

    public void issue() throws Exception {
        if (!isAvailable()) throw new Exception("Book is not available");
        bookStatus = BookStatus.ISSUED;
    }

    public void returned() throws Exception {
        if (isAvailable()) throw new Exception("Already same book Available");
        bookStatus = BookStatus.AVAILABLE;
    }


    public String getName() {
        return name;
    }


    public List<Author> getAuthors() {
        return authors;
    }

    public Publisher getPublisher() {
        return publisher;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Book book = (Book) o;

        if (id != book.id) return false;
        if (!authors.equals(book.authors)) return false;
        if (bookStatus != book.bookStatus) return false;
        if (!name.equals(book.name)) return false;
        if (!publisher.equals(book.publisher)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + name.hashCode();
        result = 31 * result + authors.hashCode();
        result = 31 * result + publisher.hashCode();
        result = 31 * result + bookStatus.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id='" + id + '\'' +
                "name='" + name + '\'' +
                ", authors=" + authors.stream().map(a -> a.getName()).collect(Collectors.joining(",")) +
                ", publisher=" + publisher.getName() +
                ", status=" + bookStatus.toString() +
                '}';
    }

    private static class BookValidator {
        public static void validate(Book book) throws Exception {
            if(book.name == null) throw new Exception("Book name cant be null");
            if(book.authors == null) throw new Exception("Authors cant be null");
            if(book.publisher == null) throw new Exception("Authors cant be null");
            if(book.bookStatus == null) throw new Exception("Book status cant be null");
            if(book.name.trim().length() < 1) throw new Exception("Book should have valid name");
        }
    }

}
