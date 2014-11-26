package test.model;

import main.model.Permission;
import main.model.User;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

public class UserTest {
    @Mock
    List<Permission> permissions;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

    }

    @Test
    public void shouldBeAuthorizedForAddBook() throws Exception {
        when(permissions.contains(Permission.ADD_BOOK)).thenReturn(true);
        User user = new User("username", permissions);
        assertTrue(user.isAuthorized(Permission.ADD_BOOK));
    }
}
