package makaroshyna.onlinebookstore.dto.order;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import makaroshyna.onlinebookstore.model.Order.Status;

@Data
public class UpdateOrderRequestDto {
    @NotNull
    private Status status;
}
