package fr.afklm.users.services;

import fr.afklm.users.domain.User;
import fr.afklm.users.exceptions.ForbiddenException;
import fr.afklm.users.mappers.UserMapperImpl;
import fr.afklm.users.exceptions.ResourceNotFoundException;
import fr.afklm.users.mappers.UserMapper;
import fr.afklm.users.repositories.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    private static final Long USER_ID = 1L;
    private final UserService userService;
    private final UserRepository userRepository;
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
        Assertions.assertDoesNotThrow(() -> userService.createUser(user));
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
        var user = userMapper.userToUserDb(buildUser());
        when(userRepository.findById(USER_ID)).thenReturn(Optional.of(user));
        var result = Assertions.assertDoesNotThrow(() -> userService.getUserById(USER_ID));
        assertEquals(user.getId(), result.getId());
    }

    @Test
    void getUserByIdKo() {
        assertThrows(ResourceNotFoundException.class, () -> userService.getUserById(USER_ID));
    }
}
