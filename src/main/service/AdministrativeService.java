package main.service;

import main.model.*;
import main.repository.BookRepo;

import java.util.ArrayList;
import java.util.List;

public class AdministrativeService {

    private final BookRepo bookRepo;

    public AdministrativeService(BookRepo bookRepo) {
        this.bookRepo = bookRepo;
    }

    public Book addBook(User user, String name, List<String> authorNames, String publisherName) throws Exception {
        if (user == null) throw new NullPointerException("Null User Found");
        if (!user.isAuthorized(Permission.ADD_BOOK)) throw new Exception("User Not Authorized");
        if (authorNames == null) throw new Exception("Book should have atleast one author");
        if (name == null || name.trim().length() < 1) throw new Exception("Book should have name");
        if (publisherName == null || publisherName.trim().length() < 1)
            throw new Exception("Book should have publisher name");

        List<Author> authors = new ArrayList<>();
        for (String authorName : authorNames) {
            authors.add(new Author(authorName));
        }
        Publisher publisher = new Publisher(publisherName);

        Book book = new Book(name, authors, publisher);
        return bookRepo.save(book);

    }

    public boolean removeBook(User user, Book book) throws Exception {
        if (user == null) throw new NullPointerException("Null User Found");
        if (!user.isAuthorized(Permission.REMOVE_BOOK)) throw new Exception("User not authorized for this operation");
        if (book == null) throw new Exception("Book is null");
        if (book.getAuthors() == null) throw new Exception("Book should have atleast one author");
        if (book.getName() == null || book.getName() == null || book.getName().trim().length() < 1)
            throw new Exception("Book should have name");
        if (book.getPublisher() == null || book.getPublisher().getName() == null || book.getPublisher().getName().trim().length() < 1)
            throw new Exception("Book should have publisher name");
        return bookRepo.delete(book);
    }

}
