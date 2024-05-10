package makaroshyna.onlinebookstore.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserLoginRequestDto {
    @Email
    @NotBlank
    private String email;
    @Size(min = 8, max = 35)
    @NotBlank
    private String password;
}
