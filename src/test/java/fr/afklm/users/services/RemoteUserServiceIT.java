package fr.afklm.users.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.afklm.users.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@SpringBootTest
class RemoteUserServiceIT {
    @Autowired
    private RemoteUserService remoteUserService;
    @Autowired
    private ObjectMapper mapper;

    private User buildUser() {
        var user = new User();
        user.setFirstName("Amine");
        user.setLastName("ELEUCH");
        user.setAge(32);
        user.setAddress("7 chemin de tanit 06160 Antibes");
        user.setCountry("France");
        return user;
    }

    @Test
    void getUserInfoOk() throws JsonProcessingException {
        var stub = stubFor(get(urlPathMatching("/api/user/.*"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(mapper.writeValueAsString(buildUser()))));

        assertDoesNotThrow(()-> remoteUserService.getUserInfo(1));

        removeStub(stub);
    }
}
