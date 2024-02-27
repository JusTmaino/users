package fr.afklm.users.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.afklm.users.domain.User;
import fr.afklm.users.repositories.UserRepository;
import fr.afklm.users.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UserResourceIT {

    private static final Long USER_ID = 1L;

    @Autowired
    private UserService userService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private UserRepository userRepository;

    private User buildUser() {
        var user = new User();
        user.setFirstName("Amine");
        user.setLastName("ELEUCH");
        user.setAge(32);
        user.setAddress("7 chemin de tanit 06160 Antibes");
        user.setCountry("France");
        return user;
    }

    @BeforeEach
    void clean(){
        userRepository.deleteAll();
        userRepository.flush();
    }

    @Test
    void createUserOk() throws Exception {
        var jsonInString = mapper.writeValueAsString(buildUser());
        mockMvc.perform(post("/user")
                        .content(jsonInString)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    void createUserKoAgeNotSatisfied() throws Exception {
        var user = buildUser();
        user.setAge(17);
        var jsonInString = mapper.writeValueAsString(user);
        mockMvc.perform(post("/user")
                        .content(jsonInString)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    void createUserKoCountryNotSatisfied() throws Exception {
        var user = buildUser();
        user.setCountry("Espagne");
        var jsonInString = mapper.writeValueAsString(user);
        mockMvc.perform(post("/user")
                        .content(jsonInString)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    void createUserKoMissingCountry() throws Exception {
        var user = buildUser();
        user.setCountry(null);
        var jsonInString = mapper.writeValueAsString(user);
        mockMvc.perform(post("/user")
                        .content(jsonInString)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getUserByIdOk() throws Exception {
        var user = buildUser();
        userService.createUser(user);
        mockMvc.perform(get("/user/" + USER_ID)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.firstName", is(user.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(user.getLastName())))
                .andExpect(jsonPath("$.age", is(user.getAge())))
                .andExpect(jsonPath("$.address", is(user.getAddress())))
                .andExpect(jsonPath("$.country", is(user.getCountry())));
    }

    @Test
    void getUserByIdKo() throws Exception {
        mockMvc.perform(get("/user/" + USER_ID)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
