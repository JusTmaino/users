package fr.supralog.users.web.errors;

import lombok.Data;
import lombok.NonNull;

@Data
public class ValidationError {
	@NonNull
	private String message;
	@NonNull
	private String field;
}
