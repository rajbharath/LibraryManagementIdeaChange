package main.service;

import main.model.Book;
import main.model.Permission;
import main.model.Reading;
import main.model.User;
import main.repository.ReadingRepo;

import java.util.Date;


public class ReadingService {

    private final ReadingRepo readingRepo;


    public ReadingService(ReadingRepo readingRepo) {
        this.readingRepo = readingRepo;
    }

    public boolean borrowBook(User user, Book book) throws Exception {
        if (user == null) throw new Exception("Null User Found");
        if (user.getUsername() == null || user.getUsername().trim().length() < 1)
            throw new Exception("User should have a valid username");
        if (book == null) throw new Exception("Null Book Found");
        if (!book.isAvailable()) throw new Exception("Book Not available");
        if (!user.isAuthorized(Permission.BORROW_BOOK)) throw new Exception("User not authorized to borrow book");
        if (book.getName() == null || book.getName().trim().length() < 1) throw new Exception("Book should have name");

        Reading reading = new Reading(user, book, new Date(System.currentTimeMillis()));
        book.issue();
        return readingRepo.save(reading);
    }

    public boolean returnBook(User user, Book book) throws Exception {
        if (user == null) throw new Exception("Null User Found");
        if (user.getUsername() == null || user.getUsername().trim().length() < 1)
            throw new Exception("User should have a valid username");
        if (!user.isAuthorized(Permission.RETURN_BOOK)) throw new Exception("User not authorized to return book");
        Reading reading = readingRepo.findByUserAndBook(user, book);
        if (reading == null) throw new Exception("User currently has no reading on the given book");
        if (book == null) throw new Exception("Null Book Found");
        if (book.getName() == null || book.getName().trim().length() < 1) throw new Exception("Book should have name");
        book.returned();
        reading.returnReading();
        return readingRepo.update(reading);
    }
}
