package test.repository;

import main.model.Permission;
import main.model.User;
import main.repository.BaseDataSource;
import main.repository.UserRepo;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Array;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

public class UserRepoTest {
    @Mock
    private BaseDataSource baseDataSource;
    @Mock
    private Connection connection;
    @Mock
    private Statement statement;
    @Mock
    private ResultSet resultSet;
    @Mock
    private Array permissionArray;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldFetchAvailableUserDetails() throws Exception {

        when(baseDataSource.getConnection()).thenReturn(connection);
        when(connection.createStatement()).thenReturn(statement);
        when(statement.executeQuery(any(String.class))).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getString("username")).thenReturn("rbrajbharath");
        when(resultSet.getArray("permissions")).thenReturn(permissionArray);
        when(permissionArray.getArray()).thenReturn(new Integer[]{1});

        UserRepo userRepo = new UserRepo(baseDataSource);
        User user = userRepo.findByUsernameAndPassword("rbrajbharath", "123456");
        List<Permission> permissions = new ArrayList<Permission>();
        permissions.add(Permission.valueOf(1));
        assertEquals(new User("rbrajbharath", permissions), user);
    }

    @Test
    public void shouldReturnNullForInvalidUserDetails() throws Exception {

        when(baseDataSource.getConnection()).thenReturn(connection);
        when(connection.createStatement()).thenReturn(statement);
        when(statement.executeQuery(any(String.class))).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(false);

        UserRepo userRepo = new UserRepo(baseDataSource);
        User user = userRepo.findByUsernameAndPassword("rbrajbharath", "123456");
        assertNull(user);
    }
}
