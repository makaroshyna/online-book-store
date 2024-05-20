package makaroshyna.onlinebookstore.dto.order;

import lombok.Data;
import makaroshyna.onlinebookstore.model.Order.Status;

@Data
public class UpdateOrderRequestDto {
    private Status status;
}
