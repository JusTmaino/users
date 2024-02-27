package fr.afklm.users.mappers;

import fr.afklm.users.domain.User;
import fr.afklm.users.web.dto.response.GetUserResponse;
import fr.afklm.users.entities.UserDb;
import fr.afklm.users.web.dto.request.CreateUserRequest;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface UserMapper {

    User userDbToUser(final UserDb userDb);

    UserDb userToUserDb(final User user);

    @Mapping(target = "id", ignore = true)
    User createUserRequestToUser(CreateUserRequest createUserRequest);

    GetUserResponse userToGetUserResponse(User user);


}
