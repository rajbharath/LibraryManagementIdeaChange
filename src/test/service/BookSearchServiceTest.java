package test.service;

import main.repository.BookRepo;
import main.service.BookSearchService;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;

public class BookSearchServiceTest {
    @Mock
    private BookRepo bookRepo;

    @Rule
    public ExpectedException thrown = ExpectedException.none();


    @Test
    public void shouldThrowExceptionWithSearchStringSpaces() throws Exception {
        thrown.expect(Exception.class);
        thrown.expectMessage("Criteria Should be atleast one character");
        BookSearchService service = new BookSearchService(bookRepo);
        service.searchBookByName("     ");

    }

    @Test
    public void shouldThrowExceptionSearchStringIsNull() throws Exception {
        thrown.expect(Exception.class);
        thrown.expectMessage("Null Criteria Found");
        BookSearchService service = new BookSearchService(bookRepo);
        service.searchBookByName(null);

    }
}
