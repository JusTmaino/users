package fr.supralog.users.services;

import fr.supralog.users.domain.User;
import fr.supralog.users.exceptions.ForbiddenException;
import fr.supralog.users.exceptions.ResourceNotFoundException;
import fr.supralog.users.mappers.UserMapper;
import fr.supralog.users.repositories.UserRepository;
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
     *
     * @param user to be created
     * @return the created user if it fulfills the conditions
     */
    @Transactional
    public User createUser(User user) {
        if(user.getAge() > 18 && FRANCE.equals(user.getCountry())){
            var userDb = userMapper.UserToUserDb(user);
            var createdUser = userRepository.save(userDb);
            log.info("user has been created");
            return userMapper.UserDbToUser(createdUser);
        } else {
            throw new ForbiddenException("Only adults ( age > 18 years) and that live in France can create an account!");
        }
    }

    /**
     *
     * @param id of the specific user to get
     * @return specific user by id
     */
    @Transactional(readOnly = true)
    public User getUserById(Long id) {
        var userDb = userRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("User not found"));
        return userMapper.UserDbToUser(userDb);
    }
}
