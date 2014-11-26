package test.endtoend;

import main.model.*;
import main.repository.BaseDataSource;
import main.repository.DataSourceBuilder;
import main.repository.ReadingRepo;
import main.repository.RepoFactory;
import main.service.AuthenticationService;
import main.service.ReadingService;
import main.service.ServiceManager;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class EndToEndReadingServiceTest {

    ReadingRepo readingRepo;
    User user;

    Book book;

    @Before
    public void setUp() throws Exception {
        BaseDataSource baseDataSource = DataSourceBuilder.build("org.postgresql.Driver", "jdbc:postgresql://localhost:5432/library_mgmt_upgraded", "postgres", "1");
        readingRepo = new RepoFactory(baseDataSource).getReadingRepo();
        MockitoAnnotations.initMocks(this);
        List<Author> authors = new ArrayList<>();
        authors.add(new Author("Martin Fowler"));
        authors.add(new Author("fowler"));
        book = new Book("P EAAA", authors, new Publisher("Addison-Wesly"));
        book.setId(89);

        List<Permission> permissions = new ArrayList<>();
        permissions.add(Permission.BORROW_BOOK);
        permissions.add(Permission.RETURN_BOOK);
        user = new User("rbrajbharath",permissions);
    }

    @Test
    public void shouldBorrowBook() throws Exception {

        ReadingService service = new ReadingService(readingRepo);
        assertTrue("should borrow book but got failed", service.borrowBook(user, book));
        service.returnBook(user, book);
    }

    @Test
    public void shouldReturnBorrowedBook() throws Exception {

        ReadingService service = new ReadingService(readingRepo);
        service.borrowBook(user, book);
        assertTrue("should return book but got failed", service.returnBook(user, book));
    }


}
