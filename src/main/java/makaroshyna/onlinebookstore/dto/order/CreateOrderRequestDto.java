package makaroshyna.onlinebookstore.dto.order;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreateOrderRequestDto {
    @NotBlank
    @Size(min = 3, max = 255)
    private String shippingAddress;
}
