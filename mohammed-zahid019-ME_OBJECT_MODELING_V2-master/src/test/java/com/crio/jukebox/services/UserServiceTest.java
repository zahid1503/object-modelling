package com.crio.jukebox.services;

    import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.crio.jukebox.entities.User;
import com.crio.jukebox.repositories.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@DisplayName("UserServiceTest")
@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    UserRepository userRepository;

    @InjectMocks
    UserService userService;

    @Test
    @DisplayName("Create User Successful")
    public void createUser() {
        User user = new User("1", "Ravi");
        when(userRepository.save(any(User.class))).thenReturn(user);
        
        Assertions.assertEquals(user, userService.creatUser("Ravi"));

        verify(userRepository, times(1)).save(any(User.class));
    }
}
