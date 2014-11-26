package main.repository;

import java.sql.SQLException;

public class RepoFactory {
    private BaseDataSource baseDataSource;
    private ReadingRepo readingRepo;
    private BookRepo bookRepo;
    private UserRepo userRepo;

    public RepoFactory(BaseDataSource baseDataSource) {
        this.baseDataSource = baseDataSource;
    }

    public ReadingRepo getReadingRepo() throws SQLException, ClassNotFoundException {
        if (readingRepo == null) readingRepo = new ReadingRepo(baseDataSource, getBookRepo());
        return readingRepo;
    }

    public BookRepo getBookRepo() throws SQLException, ClassNotFoundException {
        if (bookRepo == null)
            bookRepo = new BookRepo(baseDataSource, new PublisherRepo(baseDataSource), new AuthorRepo(baseDataSource));
        return bookRepo;
    }

    public UserRepo getUserRepo() throws SQLException, ClassNotFoundException {
        if (userRepo == null) userRepo = new UserRepo(baseDataSource);
        return userRepo;
    }
}
