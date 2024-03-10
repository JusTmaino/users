package tech.byrsa.users.web.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Schema(description = "Payload to create a user")
public class CreateUserRequest {
    @NotBlank(message = "firstName must not be empty")
    private String firstName;
    @NotBlank(message = "lastName must not be empty")
    private String lastName;
    @Positive(message = "age must be a number strictly higher than 0")
    private int age;
    private String address = "Place du gaulle 06600 Antibes";
    @NotBlank(message = "country must not be empty")
    private String country;
}
