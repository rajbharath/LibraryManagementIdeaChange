package main.repository;

import java.sql.Connection;

public class DataSourceBuilder {
    String dbDriver;
    String dbUrl;
    String dbUser;
    String dbPassword;
    private boolean autoCommit = true;
    private Connection connection;

    public static BaseDataSource build(String dbDriver, String dbUrl, String dbUser, String dbPassword) {
        return new BaseDataSource(dbDriver, dbUrl, dbUser, dbPassword);
    }

}
