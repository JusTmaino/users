package tech.byrsa.users.aop;

import tech.byrsa.users.web.dto.request.CreateUserRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class LoggingAspect {

    private long createUserStartTime;

    private long getUserByIdStartTime;

    /**
     * Pointcut that match create user endpoint
     */
    @Pointcut("execution(* tech.byrsa.users.web.UserResource.createUser(..))")
    public void createUserPointcut() {
        // Method is empty as this is just a Pointcut
    }

    /**
     * Advice that logs before executing the create user call
     * @param createUserRequest the request being given to the endpoint
     */
    @Before("createUserPointcut() && args(createUserRequest)")
    void logIncomingCreateUserRequest(CreateUserRequest createUserRequest) {
        createUserStartTime = System.currentTimeMillis();
        log.info("Received user creation request: {}", createUserRequest);
    }

    /**
     * Advice that logs after executing the create user call
     * @param createUserRequest the request being given to the endpoint
     */
    @After("createUserPointcut() && args(createUserRequest)")
    void logOutComingCreateUserRequest(CreateUserRequest createUserRequest) {
        var createUserEndTime = System.currentTimeMillis();
        log.info("Finished created user call within {} milliseconds", createUserEndTime - createUserStartTime);
    }

    /**
     * Pointcut that match get user by id endpoint
     */
    @Pointcut("execution(* tech.byrsa.users.web.UserResource.getUserById(..))")
    public void getUserByIdPointcut() {
        // Method is empty as this is just a Pointcut
    }

    /**
     * Advice that logs before executing the get user by id call
     * @param id given to the endpoint
     */
    @Before("getUserByIdPointcut() && args(id)")
    void logIncomingCreateUserRequest(Long id) {
        getUserByIdStartTime = System.currentTimeMillis();
        log.info("Received get user request with id: {}", id);
    }

    /**
     * Advice that logs after executing the get user by id call
     * @param id given to the endpoint
     */
    @After("getUserByIdPointcut() && args(id)")
    void logOutComingCreateUserRequest(Long id) {
        var getUserByIdEndTime = System.currentTimeMillis();
        log.info("Finished get user request within {} milliseconds", getUserByIdEndTime - getUserByIdStartTime);
    }
}
