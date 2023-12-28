package fr.supralog.users.web.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CreateUserRequest {
    @NotBlank(message = "firstName must not be empty")
    private String firstName;
    @NotBlank(message = "lastName must not be empty")
    private String lastName;
    @NotNull(message = "age must not be null")
    private int age;
    private String address = "Place du gaulle 06600 Antibes";
    @NotBlank(message = "country must not be empty")
    private String country;
}
