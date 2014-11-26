package test.service;

import main.model.Permission;
import main.model.User;
import main.repository.UserRepo;
import main.service.AuthenticationService;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

public class AuthenticationServiceTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @MockitoAnnotations.Mock
    private UserRepo userRepo;
    @MockitoAnnotations.Mock
    private List<Permission> permissions;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldReturnUserForValidCredentials() throws Exception {
        when(userRepo.findByUsernameAndPassword("rbrajbharath", "123456")).thenReturn(new User("rbrajbharath", permissions));
        AuthenticationService service = new AuthenticationService(userRepo);
        User user = service.authenticate("rbrajbharath", "123456");
        assertEquals(new User("rbrajbharath", permissions), user);
    }

    @Test
    public void shouldThrowExceptionForInvalidCredentials() throws Exception {
        thrown.expect(Exception.class);
        thrown.expectMessage("User not found");
        when(userRepo.findByUsernameAndPassword("invalidusername", "invalidpassword")).thenThrow(new Exception("User not found"));
        AuthenticationService service = new AuthenticationService(userRepo);
        service.authenticate("invalidusername", "invalidpassword");
    }

    @Test
    public void shouldThrowExceptionUsernameAndPasswordSpaces() throws Exception {
        thrown.expect(Exception.class);
        thrown.expectMessage("Username should be entered");
        AuthenticationService service = new AuthenticationService(userRepo);
        service.authenticate("", "");
    }

    @Test
    public void shouldThrowExceptionUsernameSpaces() throws Exception {
        thrown.expect(Exception.class);
        thrown.expectMessage("Username should be entered");
        AuthenticationService service = new AuthenticationService(userRepo);
        service.authenticate("", "123465");
    }

    @Test
    public void shouldThrowExceptionPasswordSpaces() throws Exception {
        thrown.expect(Exception.class);
        thrown.expectMessage("Password should be entered");
        AuthenticationService service = new AuthenticationService(userRepo);
        service.authenticate("rbrajbharath", "");
    }


}
