package makaroshyna.onlinebookstore.service.order;

import makaroshyna.onlinebookstore.dto.order.CreateOrderRequestDto;
import makaroshyna.onlinebookstore.dto.order.OrderResponseDto;
import makaroshyna.onlinebookstore.model.User;

public interface OrderService {
    OrderResponseDto saveOrder(CreateOrderRequestDto requestDto, User user);
}
