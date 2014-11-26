package test.model;

import main.model.Author;
import main.model.Book;
import main.model.Publisher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class BookTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void shouldBeAvailable() throws Exception {
        List<Author> authors = new ArrayList<>();
        authors.add(new Author("Martin"));
        Book book = new Book("refactoring", authors, new Publisher("Addison"));
        assertTrue("should be available failed", book.isAvailable());
    }

    @Test
    public void shouldNotBeAvailable() throws Exception {
        List<Author> authors = new ArrayList<>();
        authors.add(new Author("Martin"));
        Book book = new Book("refactoring", authors, new Publisher("Addison"));
        book.issue();
        assertFalse("should be available failed", book.isAvailable());
    }


}
