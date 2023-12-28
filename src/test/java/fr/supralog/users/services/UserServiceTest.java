package fr.supralog.users.services;

import fr.supralog.users.domain.User;
import fr.supralog.users.exceptions.ForbiddenException;
import fr.supralog.users.exceptions.ResourceNotFoundException;
import fr.supralog.users.mappers.UserMapper;
import fr.supralog.users.mappers.UserMapperImpl;
import fr.supralog.users.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    private static final Long USER_ID = 1L;
    private final UserService userService;
    @Spy
    private UserRepository userRepository;
    private final UserMapper userMapper;

    public UserServiceTest() {
        userRepository = Mockito.mock(UserRepository.class);
        userMapper = new UserMapperImpl();
        userService = new UserService(userRepository, userMapper);
    }

    private User buildUser() {
        var user = new User();
        user.setId(USER_ID);
        user.setFirstName("Amine");
        user.setLastName("ELEUCH");
        user.setAge(32);
        user.setAddress("7 chemin de tanit 06160 Antibes");
        user.setCountry("France");
        return user;
    }

    @Test
    void createUserOk() {
        var user = buildUser();
        assertDoesNotThrow(() -> userService.createUser(user));
    }

    @Test
    void createUserKoAgeNotSatisfied() {
        var user = buildUser();
        user.setAge(17);
        assertThrows(ForbiddenException.class, () -> userService.createUser(user));
    }

    @Test
    void createUserKoCountryNotSatisfied() {
        var user = buildUser();
        user.setCountry("Espagne");
        assertThrows(ForbiddenException.class, () -> userService.createUser(user));
    }

    @Test
    void createUserKoMissingCountry() {
        var user = buildUser();
        user.setCountry(null);
        assertThrows(ForbiddenException.class, () -> userService.createUser(user));
    }

    @Test
    void getUserByIdOk() {
        var user = userMapper.UserToUserDb(buildUser());
        when(userRepository.findById(USER_ID)).thenReturn(Optional.of(user));
        var result = assertDoesNotThrow(() -> userService.getUserById(USER_ID));
        assertEquals(user.getId(), result.getId());
    }

    @Test
    void getUserByIdKo() {
        assertThrows(ResourceNotFoundException.class, () -> userService.getUserById(USER_ID));
    }
}
