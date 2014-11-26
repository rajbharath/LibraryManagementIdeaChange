package main.service;

import main.repository.BaseDataSource;
import main.repository.RepoFactory;

import java.sql.SQLException;

public class ServiceManager {
    private AdministrativeService administrativeService;
    private AuthenticationService authenticationService;
    private ReadingService readingService;
    private BookSearchService bookSearchService;
    private RepoFactory repoFactory;

    public ServiceManager(BaseDataSource baseDataSource) {
        repoFactory = new RepoFactory(baseDataSource);
    }

    public AdministrativeService getAdministrativeService() throws SQLException, ClassNotFoundException {
        if (administrativeService == null) administrativeService = new AdministrativeService(repoFactory.getBookRepo());
        return administrativeService;
    }

    public AuthenticationService getAuthenticationService() throws SQLException, ClassNotFoundException {
        if (authenticationService == null) authenticationService = new AuthenticationService(repoFactory.getUserRepo());
        return authenticationService;
    }

    public ReadingService getReadingService() throws SQLException, ClassNotFoundException {
        if (readingService == null) readingService = new ReadingService(repoFactory.getReadingRepo());
        return readingService;
    }

    public BookSearchService getBookSearchService() throws SQLException, ClassNotFoundException {
        if (bookSearchService == null) bookSearchService = new BookSearchService(repoFactory.getBookRepo());
        return bookSearchService;
    }
}
