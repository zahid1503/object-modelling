package com.crio.jukebox.commands;

    import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;
import com.crio.jukebox.services.UserService;
import com.crio.jukebox.entities.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@DisplayName("CreateUserCommandTest")
@ExtendWith(MockitoExtension.class)
public class CreateUserCommandTest {
    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @Mock
    UserService userService;

    @InjectMocks
    CreateUserCommand createUserCommand;

    @BeforeEach
    public void setup() {
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @Test
    @DisplayName("Create User Successfully")
    public void createUserSuccessfully() {
        String expected = "1 Ravi";
        User user = new User ("1", "Ravi");
        when(userService.creatUser("Ravi")).thenReturn(user);
        createUserCommand.execute(List.of("CREATE-USER", "Ravi"));

        Assertions.assertEquals(expected, outputStreamCaptor.toString().trim());

        verify(userService, times(1)).creatUser("Ravi");
    }

    @AfterEach
    public void tearDown() {
        System.setOut(standardOut);
    }
}
