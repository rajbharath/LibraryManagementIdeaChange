package main.service;

import main.model.Book;
import main.repository.BookRepo;

import java.util.List;

public class BookSearchService {
    private BookRepo bookRepo;

    public BookSearchService(BookRepo bookRepo) {
        this.bookRepo = bookRepo;
    }

    public List<Book> searchBookByName(String name) throws Exception {
        if (name == null) throw new Exception("Null Criteria Found");
        if (name.trim().length() < 1) throw new Exception("Criteria Should be atleast one character");
        return bookRepo.findBooksByName(name);
    }
}
