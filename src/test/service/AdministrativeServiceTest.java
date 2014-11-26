package test.service;

import main.model.*;
import main.repository.AuthorRepo;
import main.repository.BaseDataSource;
import main.repository.BookRepo;
import main.repository.PublisherRepo;
import main.service.AdministrativeService;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class AdministrativeServiceTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();


    @Mock
    private BookRepo bookRepo;
    @Mock
    private AuthorRepo authorRepo;
    @Mock
    private PublisherRepo publisherRepo;

    @Mock
    User user;
    @Mock
    private BaseDataSource baseDataSource;

    private final List<Author> authors = Arrays.asList(new Author[]{new Author("Martin Fowler")});

    private final List<String> authorNames = Arrays.asList(new String[]{"Martin Fowler", "fowler"});

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldAddBookWithValidValues() throws Exception {

        List<Author> authors = new ArrayList<>();
        authors.add(new Author("Martin Fowler"));
        authors.add(new Author("fowler"));
        Book expectedBook = new Book("P EAA", authors, new Publisher("Addison-Wesly"));

        when(bookRepo.save(any(Book.class))).thenReturn(expectedBook);

        when(user.isAuthorized(Permission.ADD_BOOK)).thenReturn(true);
        AdministrativeService service = new AdministrativeService(bookRepo);

        Book book = service.addBook(user, "P EAA", authorNames, "Addison-Wesly");
        verify(user).isAuthorized(Permission.ADD_BOOK);
        assertEquals("Should Add Book Failed.", expectedBook, book);
    }

    @Test
    public void shouldThrowExceptionUnauthorizedUserToAddBook() throws Exception {
        thrown.expect(Exception.class);
        thrown.expectMessage("User Not Authorized");
        when(user.isAuthorized(Permission.ADD_BOOK)).thenReturn(false);
        AdministrativeService service = new AdministrativeService(bookRepo);
        service.addBook(user, "P EAA", authorNames, "Addison-Wesly");
    }

    @Test
    public void shouldThrowExceptionForNullUser() throws Exception {
        thrown.expect(Exception.class);
        thrown.expectMessage("Null User Found");
        AdministrativeService service = new AdministrativeService(bookRepo);
        service.addBook(null, "P EAA", authorNames, "Addison-Wesly");
    }

    @Test
    public void shouldThrowExceptionForNullAuthor() throws Exception {
        thrown.expect(Exception.class);
        thrown.expectMessage("Book should have atleast one author");
        when(user.isAuthorized(Permission.ADD_BOOK)).thenReturn(true);
        AdministrativeService service = new AdministrativeService(bookRepo);
        service.addBook(user, "P EAA", null, "Addison-Wesly");
    }

    @Test
    public void shouldThrowExceptionForNullBookName() throws Exception {
        thrown.expect(Exception.class);
        thrown.expectMessage("Book should have name");
        when(user.isAuthorized(Permission.ADD_BOOK)).thenReturn(true);
        AdministrativeService service = new AdministrativeService(bookRepo);
        service.addBook(user, null, authorNames, "Addison-Wesly");
    }

    @Test
    public void shouldThrowExceptionBookNameSpaces() throws Exception {
        thrown.expect(Exception.class);
        thrown.expectMessage("Book should have name");
        when(user.isAuthorized(Permission.ADD_BOOK)).thenReturn(true);
        AdministrativeService service = new AdministrativeService(bookRepo);
        service.addBook(user, "   ", authorNames, "Addison-Wesly");
    }

    @Test
    public void shouldThrowExceptionForNotHavingPublisher() throws Exception {
        thrown.expect(Exception.class);
        thrown.expectMessage("Book should have publisher name");
        when(user.isAuthorized(Permission.ADD_BOOK)).thenReturn(true);
        AdministrativeService service = new AdministrativeService(bookRepo);
        service.addBook(user, "P EAA", authorNames, null);
    }

    @Test
    public void shouldThrowExceptionPublisherNameSpaces() throws Exception {
        thrown.expect(Exception.class);
        thrown.expectMessage("Book should have publisher name");
        when(user.isAuthorized(Permission.ADD_BOOK)).thenReturn(true);
        AdministrativeService service = new AdministrativeService(bookRepo);
        service.addBook(user, "P EAA", authorNames, "    ");
    }

    @Test
    public void shouldRemoveBook() throws Exception {
        when(user.isAuthorized(Permission.REMOVE_BOOK)).thenReturn(true);
        when(bookRepo.delete(any(Book.class))).thenReturn(true);
        AdministrativeService service = new AdministrativeService(bookRepo);
        boolean isDeleted = service.removeBook(user, new Book("P EAA", authors, new Publisher("Addison-Wesly")));
        assertTrue("should Delete Book Failed. Expected: true Got: False", isDeleted);
    }

    @Test
    public void shouldThrowExceptionToRemoveNullBook() throws Exception {
        thrown.expect(Exception.class);
        thrown.expectMessage("Book is null");
        when(user.isAuthorized(Permission.REMOVE_BOOK)).thenReturn(true);
        AdministrativeService service = new AdministrativeService(bookRepo);
        service.removeBook(user, null);
    }

    @Test
    public void shouldThrowExceptionToRemoveBookUnauthorizedUser() throws Exception {
        thrown.expect(Exception.class);
        thrown.expectMessage("User not authorized for this operation");
        when(user.isAuthorized(Permission.REMOVE_BOOK)).thenReturn(false);
        when(bookRepo.delete(any(Book.class))).thenReturn(true);
        AdministrativeService service = new AdministrativeService(bookRepo);
        service.removeBook(user, new Book("P EAA", authors, new Publisher("Addison-Wesly")));
    }

    @Test
    public void shouldThrowExceptionToRemoveBookUserIsNull() throws Exception {
        thrown.expect(Exception.class);
        thrown.expectMessage("Null User Found");
        when(user.isAuthorized(Permission.REMOVE_BOOK)).thenReturn(true);
        AdministrativeService service = new AdministrativeService(bookRepo);
        service.removeBook(null, new Book("P EAA", authors, new Publisher("Addison-Wesly")));
    }

    @Test
    public void shouldThrowExceptionToRemoveBookNullBookName() throws Exception {
        thrown.expect(Exception.class);
        thrown.expectMessage("Book should have name");
        when(user.isAuthorized(Permission.REMOVE_BOOK)).thenReturn(true);
        AdministrativeService service = new AdministrativeService(bookRepo);
        service.removeBook(user, new Book(null, authors, new Publisher("Addison-Wesly")));
    }

    @Test
    public void shouldThrowExceptionToRemoveBookWithBookNameSpaces() throws Exception {
        thrown.expect(Exception.class);
        thrown.expectMessage("Null User Found");
        when(user.isAuthorized(Permission.REMOVE_BOOK)).thenReturn(true);
        AdministrativeService service = new AdministrativeService(bookRepo);
        service.removeBook(null, new Book("         ", authors, new Publisher("Addison-Wesly")));
    }

    @Test
    public void shouldThrowExceptionToRemoveBookAuthorIsNull() throws Exception {
        thrown.expect(Exception.class);
        thrown.expectMessage("Book should have atleast one author");
        when(user.isAuthorized(Permission.REMOVE_BOOK)).thenReturn(true);
        AdministrativeService service = new AdministrativeService(bookRepo);
        service.removeBook(user, new Book("P EAA", null, new Publisher("Addison-Wesly")));
    }


    @Test
    public void shouldThrowExceptionToRemoveBookPublisherIsNull() throws Exception {
        thrown.expect(Exception.class);
        thrown.expectMessage("Book should have publisher name");
        when(user.isAuthorized(Permission.REMOVE_BOOK)).thenReturn(true);
        AdministrativeService service = new AdministrativeService(bookRepo);
        service.removeBook(user, new Book("P EAA", authors, null));
    }

    @Test
    public void shouldThrowExceptionToRemoveBookPublisherNameSpaces() throws Exception {
        thrown.expect(Exception.class);
        thrown.expectMessage("Book should have publisher name");
        when(user.isAuthorized(Permission.REMOVE_BOOK)).thenReturn(true);
        AdministrativeService service = new AdministrativeService(bookRepo);
        service.removeBook(user, new Book("P EAA", authors, new Publisher("     ")));
    }
}
