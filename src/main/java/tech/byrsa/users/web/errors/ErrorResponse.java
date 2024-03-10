package tech.byrsa.users.web.errors;

import lombok.Data;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.List;

@Data
public class ErrorResponse {
    @NonNull
    private String message;
    private String messageDetails;
    private List<ValidationError> objectErrors = new ArrayList<>();
}
