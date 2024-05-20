package makaroshyna.onlinebookstore.service.order;

import java.util.List;
import makaroshyna.onlinebookstore.dto.order.CreateOrderRequestDto;
import makaroshyna.onlinebookstore.dto.order.OrderResponseDto;
import makaroshyna.onlinebookstore.dto.order.UpdateOrderRequestDto;
import makaroshyna.onlinebookstore.model.User;
import org.springframework.data.domain.Pageable;

public interface OrderService {
    OrderResponseDto saveOrder(CreateOrderRequestDto requestDto, User user);

    List<OrderResponseDto> getAllOrders(Pageable pageable, User user);

    void updateOrder(UpdateOrderRequestDto requestDto, Long id);
}
