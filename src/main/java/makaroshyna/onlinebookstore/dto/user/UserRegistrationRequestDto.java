package makaroshyna.onlinebookstore.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import makaroshyna.onlinebookstore.constraint.FieldMatch;

@Data
@FieldMatch(first = "password",
        second = "repeatPassword",
        message = "The password fields must match")
public class UserRegistrationRequestDto {
    @Email
    @NotBlank
    private String email;
    @Size(min = 8, max = 35)
    @NotBlank
    private String password;
    @Size(min = 8, max = 35)
    @NotBlank
    private String repeatPassword;
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    private String shippingAddress;
}
