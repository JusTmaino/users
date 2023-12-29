package fr.supralog.users.web;

import fr.supralog.users.services.UserService;
import fr.supralog.users.web.dto.request.CreateUserRequest;
import fr.supralog.users.web.dto.response.CreateUserResponse;
import fr.supralog.users.mappers.UserMapper;
import fr.supralog.users.web.dto.response.GetUserResponse;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserResource {
    private final UserService userService;
    private final UserMapper userMapper;

    @Operation(summary = "create user with given information")
    @PostMapping
    public ResponseEntity<CreateUserResponse> createUser(@RequestBody @Valid final CreateUserRequest createUserRequest) {
        var user = userMapper.createUserRequestToUser(createUserRequest);
        var createdUser = userService.createUser(user);
        return new ResponseEntity<>(new CreateUserResponse(createdUser.getId()), HttpStatus.CREATED);
    }

    @Operation(summary = "get specific user by id")
    @GetMapping("/{id}")
    public ResponseEntity<GetUserResponse> getUserById(@PathVariable final Long id) {
        var user = userService.getUserById(id);
        return ResponseEntity.ok(userMapper.userToGetUserResponse(user));
    }

}
