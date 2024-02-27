package fr.afklm.users.repositories;

import fr.afklm.users.config.DatabaseTestConfiguration;
import fr.afklm.users.entities.UserDb;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(DatabaseTestConfiguration.class)
class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        userRepository.deleteAll();
        userRepository.flush();
    }

    private UserDb buildUserDb() {
        var userDb = new UserDb();
        userDb.setFirstName("Jean");
        userDb.setLastName("Dupont");
        userDb.setAge(40);
        userDb.setAddress("7 Rue de la Croix 66600 Antibes");
        userDb.setCountry("France");
        return userDb;
    }
    @Test
    void createUserOk() {
        userRepository.save(buildUserDb());
        var userDbFound = userRepository.findById(1L);
        assertThat(userDbFound)
                .isNotNull()
                .get()
                .extracting(
                        UserDb::getFirstName,
                        UserDb::getLastName,
                        UserDb::getAge,
                        UserDb::getAddress,
                        UserDb::getCountry
                ).containsExactly(
                        "Jean",
                        "Dupont",
                        40,
                        "7 Rue de la Croix 66600 Antibes",
                        "France"
                );
    }

    @Test
    void getUserKo() {
        var userDbFound = userRepository.findById(1L);
        assertThat(userDbFound).isEmpty();
    }
}
