package main.repository;

import main.model.Author;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class AuthorRepo {

    private final Connection connection;

    public AuthorRepo(BaseDataSource dataSource) throws SQLException, ClassNotFoundException {
        connection = dataSource.getConnection();
    }

    public int save(String author) throws SQLException {
        Statement statement = connection.createStatement();
        int id = -1;
        String sql = "insert into author(name) values('" + author + "')";
        statement.executeUpdate(sql);
        sql = "select last_value from author_id_seq";
        ResultSet resultSet = statement.executeQuery(sql);
        if (resultSet.next()) {
            id = resultSet.getInt(1);
        }
        return id;
    }

    public int findIdByName(String authorName) throws SQLException {
        Statement statement = connection.createStatement();
        String sql = "select id from author where name='" + authorName + "'";
        ResultSet resultSet = statement.executeQuery(sql);
        int id = -1;
        if (resultSet.next()) {
            id = resultSet.getInt(1);
        }
        return id;
    }

    public Author findById(Integer authorId) throws SQLException {
        Statement statement = connection.createStatement();
        String sql = "select name from author where id=" + authorId;
        ResultSet resultSet = statement.executeQuery(sql);
        Author author = null;
        if (resultSet.next()) {
            author = new Author(resultSet.getString("name"));
        }
        return author;
    }
}
