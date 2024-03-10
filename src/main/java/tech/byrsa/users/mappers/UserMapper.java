package tech.byrsa.users.mappers;

import tech.byrsa.users.domain.User;
import tech.byrsa.users.web.dto.response.GetUserResponse;
import tech.byrsa.users.entities.UserDb;
import tech.byrsa.users.web.dto.request.CreateUserRequest;
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
