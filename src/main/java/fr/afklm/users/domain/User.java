package fr.afklm.users.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class User {
    private Long id;
    private String firstName;
    private String lastName;
    private int age;
    private String address;
    private String country;
}

