package tech.byrsa.users.services;

import tech.byrsa.users.domain.User;
import tech.byrsa.users.exceptions.ForbiddenException;
import tech.byrsa.users.exceptions.ResourceNotFoundException;
import tech.byrsa.users.mappers.UserMapper;
import tech.byrsa.users.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private static final String FRANCE = "France";
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    /**
     * create user with a given informations
     *
     * @param user to be created
     * @return the created user if it fulfills the conditions
     */
    @Transactional
    public User createUser(User user) {
        if(user.getAge() > 18 && FRANCE.equals(user.getCountry())){
            var userDb = userMapper.userToUserDb(user);
            var createdUser = userRepository.save(userDb);
            log.info("user has been created successfully");
            return userMapper.userDbToUser(createdUser);
        } else {
            throw new ForbiddenException("Only adults ( age > 18 years) and that live in France can create an account!");
        }
    }

    /**
     * Get specific user by id
     *
     * @param id of the specific user to get
     * @return specific user that match the id
     */
    @Transactional(readOnly = true)
    public User getUserById(Long id) {
        var userDb = userRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("User not found"));
        return userMapper.userDbToUser(userDb);
    }
}
