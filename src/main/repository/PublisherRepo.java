package main.repository;

import main.model.Publisher;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class PublisherRepo {
    private final Connection connection;

    public PublisherRepo(BaseDataSource dataSource) throws SQLException, ClassNotFoundException {
        connection = dataSource.getConnection();
    }

    public int save(String publisherName) throws SQLException {
        Statement statement = connection.createStatement();
        int id = -1;
        String sql = "insert into publisher(name) values('" + publisherName + "')";
        statement.executeUpdate(sql);
        sql = "select last_value from publisher_id_seq;";
        ResultSet resultSet = statement.executeQuery(sql);
        if (resultSet.next()) {
            id = resultSet.getInt(1);
        }
        return id;
    }

    public int findIdByName(String publisherName) throws SQLException {
        int id = -1;
        Statement statement = connection.createStatement();
        String sql = "select id from publisher where name='" + publisherName + "'";
        ResultSet resultSet = statement.executeQuery(sql);
        if (resultSet.next()) {
            id = resultSet.getInt(1);
        }
        return id;
    }

    public Publisher findById(int publisherId) throws SQLException {
        Statement statement = connection.createStatement();
        String sql = "select name from publisher where id=" + publisherId;
        ResultSet resultSet = statement.executeQuery(sql);

        if (resultSet.next()) {
            return new Publisher(resultSet.getString("name"));
        }
        return null;
    }


}
