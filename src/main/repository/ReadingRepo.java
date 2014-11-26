package main.repository;

import main.model.Book;
import main.model.Reading;
import main.model.User;

import java.sql.*;

public class ReadingRepo {


    private final Connection connection;
    private final BookRepo bookRepo;


    public ReadingRepo(BaseDataSource baseDataSource, BookRepo bookRepo) throws SQLException, ClassNotFoundException {
        this.connection = baseDataSource.getConnection();
        this.bookRepo = bookRepo;
    }

    public boolean save(Reading reading) throws Exception {
        boolean resultCode = true;
        String sql = "insert into reading(username,bookname,borrowed_date,due_date,status) values(?,?,?,?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setString(1, reading.getUsername());
        preparedStatement.setString(2, reading.getBookName());
        preparedStatement.setDate(3, new Date(reading.getBorrowedDate().getTime()));
        preparedStatement.setDate(4, new Date(reading.getDueDate().getTime()));
        preparedStatement.setString(5, reading.getStatus());
        int insertedId = preparedStatement.executeUpdate();
        reading.setId(insertedId);
        resultCode &= insertedId > 0;
        resultCode &= bookRepo.update(reading.getBook());
        return resultCode;
    }

    public boolean update(Reading reading) throws SQLException {
        boolean resultCode = true;
        String sql = "update reading set returned_date=?,due_date=?,status=? where id=?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setDate(1, new Date(reading.getReturnedDate().getTime()));
        preparedStatement.setDate(2, new Date(reading.getDueDate().getTime()));
        preparedStatement.setString(3, reading.getStatus());
        preparedStatement.setInt(4, reading.getId());
        resultCode &= preparedStatement.executeUpdate() > 0;
        resultCode &= bookRepo.update(reading.getBook());
        return resultCode;
    }

    public Reading findByUserAndBook(User user, Book book) throws SQLException {
        String sql = "select id,username,bookname,borrowed_date,due_date,returned_date,status from reading where username =? and bookname=?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, user.getUsername());
        preparedStatement.setString(2, book.getName());
        ResultSet resultSet = preparedStatement.executeQuery();

        return buildReadingFromResultSet(resultSet, user);
    }

    private Reading buildReadingFromResultSet(ResultSet resultSet, User user) throws SQLException {
        Reading reading = null;

        if (resultSet.next()) {
            Book book = bookRepo.findByName(resultSet.getString("bookname"));
            reading = new Reading(user, book, new java.util.Date(resultSet.getDate("borrowed_date").getTime()));
            reading.setId(resultSet.getInt("id"));
        }
        return reading;
    }
}
