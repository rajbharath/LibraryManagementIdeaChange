package main.repository;

import main.model.Permission;
import main.model.User;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserRepo {
    private Connection connection;

    public UserRepo(BaseDataSource dataSource) throws SQLException, ClassNotFoundException {
        connection = dataSource.getConnection();
    }

    public User findByUsernameAndPassword(String username, String password) {
        ResultSet resultSet = null;
        try {
            Statement statement = connection.createStatement();
            String sql = "select u.username,r.permissions from \"user\" u join role r on u.role = r.id and username='" + username + "' and password='" + password + "'";
            resultSet = statement.executeQuery(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return buildUserFromResultSet(resultSet);
    }

    private User buildUserFromResultSet(ResultSet resultSet) {
        User user = null;
        try {
            if (resultSet != null && resultSet.next()) {
                String username = resultSet.getString("username");
                Integer[] permissionValues = (Integer[]) resultSet.getArray("permissions").getArray();
                List<Permission> permissions = new ArrayList<>();
                for (Integer permissionValue : permissionValues) {
                    permissions.add(Permission.valueOf(permissionValue));
                }
                user = new User(username, permissions);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

}
