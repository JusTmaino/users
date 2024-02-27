package fr.afklm.users.web;

import fr.afklm.users.exceptions.ForbiddenException;
import fr.afklm.users.web.errors.ErrorResponse;
import fr.afklm.users.web.errors.ValidationError;
import fr.afklm.users.exceptions.ResourceNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(value = ResourceNotFoundException.class)
    public ErrorResponse notFound(final ResourceNotFoundException e) {
        log.error(e.getMessage(), e);
        final ErrorResponse errorResponse = new ErrorResponse("Not found");
        errorResponse.setMessageDetails(e.getMessage());
        return errorResponse;
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(value = ForbiddenException.class)
    public ErrorResponse forbidden(final ForbiddenException e) {
        log.error(e.getMessage(), e);
        final ErrorResponse errorResponse = new ErrorResponse("Forbidden");
        errorResponse.setMessageDetails(e.getMessage());
        return errorResponse;
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(final MethodArgumentNotValidException e, final HttpHeaders headers, final HttpStatusCode status, final WebRequest request) {
        log.error(e.getMessage(), e);
        final List<ValidationError> validationErrorList = new ArrayList<>();
        final BindingResult bindingResult = e.getBindingResult();
        bindingResult.getAllErrors().forEach(error -> {
            if (error instanceof final FieldError fieldError) {
                final ValidationError validationError = new ValidationError(
                        Objects.requireNonNull(fieldError.getDefaultMessage()), fieldError.getField());
                validationErrorList.add(validationError);
            } else {
                final String fields = Arrays.stream(error.getArguments()).map(o -> {
                    if (o instanceof DefaultMessageSourceResolvable) {
                        return null;
                    }
                    return o.toString();
                }).filter(Objects::nonNull).collect(Collectors.joining(", "));
                final ValidationError validationError = new ValidationError(
                        Objects.requireNonNull(error.getDefaultMessage()), fields);
                validationErrorList.add(validationError);
            }
        });
        final ErrorResponse errorDetails = new ErrorResponse("not valid");
        errorDetails.setObjectErrors(validationErrorList);
        return super.handleExceptionInternal(e, errorDetails, headers, status, request);
    }
}
