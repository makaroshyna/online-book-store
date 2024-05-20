package makaroshyna.onlinebookstore.dto.order;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;
import lombok.Data;
import makaroshyna.onlinebookstore.dto.orderitem.OrderItemResponseDto;
import makaroshyna.onlinebookstore.model.Order.Status;

@Data
public class OrderResponseDto {
    private Long id;
    private Long userId;
    private Set<OrderItemResponseDto> orderItems;
    private LocalDateTime orderDate;
    private BigDecimal totalPrice;
    private Status status;
}
