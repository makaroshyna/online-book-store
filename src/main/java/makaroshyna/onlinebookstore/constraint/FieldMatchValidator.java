package makaroshyna.onlinebookstore.constraint;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.Objects;
import makaroshyna.onlinebookstore.dto.user.UserRegistrationRequestDto;

public class FieldMatchValidator implements
        ConstraintValidator<FieldMatch, UserRegistrationRequestDto> {
    @Override
    public boolean isValid(UserRegistrationRequestDto requestDto,
                           ConstraintValidatorContext constraintValidatorContext) {
        return Objects.equals(requestDto.getPassword(), requestDto.getRepeatPassword());
    }
}
