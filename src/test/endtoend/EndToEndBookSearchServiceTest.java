package test.endtoend;

import main.model.Book;
import main.model.Permission;
import main.repository.BaseDataSource;
import main.repository.DataSourceBuilder;
import main.repository.RepoFactory;
import main.service.BookSearchService;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class EndToEndBookSearchServiceTest {

    @Test
    public void endToEndShouldSearchBooksByName() throws Exception {
        BaseDataSource baseDataSource = DataSourceBuilder.build("org.postgresql.Driver", "jdbc:postgresql://localhost:5432/library_mgmt_upgraded", "postgres", "1");
        RepoFactory repoFactory = new RepoFactory(baseDataSource);
        BookSearchService service = new BookSearchService(repoFactory.getBookRepo());
        List<Permission> permissions = new ArrayList<>();
        permissions.add(Permission.ADD_BOOK);
        permissions.add(Permission.SEARCH_BY_BOOKNAME);

        List<Book> books = service.searchBookByName("P");
        List<Book> expected = new ArrayList<>();
        expected.add(new Book("P EAAA", null, null));
        assertEquals("should find By Name Book . Expected: List of Books Got: " + books.get(0).getName(), books.get(0).getName(), expected.get(0).getName());
    }
}
