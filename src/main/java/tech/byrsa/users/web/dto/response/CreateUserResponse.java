package tech.byrsa.users.web.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(description = "Create user response")
public class CreateUserResponse {
    private long id;
}
