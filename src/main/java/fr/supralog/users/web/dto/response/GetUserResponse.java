package fr.supralog.users.web.dto.response;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GetUserResponse {
    private Long id;
    private String firstName;
    private String lastName;
    private int age;
    private String address;
    private String country;
}
