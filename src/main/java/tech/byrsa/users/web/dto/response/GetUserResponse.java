package tech.byrsa.users.web.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Schema(description = "Get user response")
public class GetUserResponse {
    private Long id;
    private String firstName;
    private String lastName;
    private int age;
    private String address;
    private String country;
}
