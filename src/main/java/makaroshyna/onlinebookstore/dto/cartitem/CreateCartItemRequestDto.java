package makaroshyna.onlinebookstore.dto.cartitem;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class CreateCartItemRequestDto {
    @NotEmpty
    private Long bookId;
    @NotEmpty
    @Positive
    private int quantity;
}
