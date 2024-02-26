package fr.supralog.users.mappers;

import fr.supralog.users.domain.User;
import fr.supralog.users.entities.UserDb;
import fr.supralog.users.web.dto.request.CreateUserRequest;
import fr.supralog.users.web.dto.response.GetUserResponse;
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
