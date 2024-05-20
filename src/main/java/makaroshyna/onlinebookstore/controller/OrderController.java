package makaroshyna.onlinebookstore.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import makaroshyna.onlinebookstore.dto.order.CreateOrderRequestDto;
import makaroshyna.onlinebookstore.dto.order.OrderResponseDto;
import makaroshyna.onlinebookstore.dto.order.UpdateOrderRequestDto;
import makaroshyna.onlinebookstore.dto.orderitem.OrderItemResponseDto;
import makaroshyna.onlinebookstore.model.User;
import makaroshyna.onlinebookstore.service.order.OrderService;
import makaroshyna.onlinebookstore.service.orderitem.OrderItemService;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Order management", description = "Endpoints for managing orders")
@RequiredArgsConstructor
@RestController
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;
    private final OrderItemService orderItemService;

    @PreAuthorize("hasRole('USER')")
    @GetMapping
    @Operation(summary = "Get all orders",
            description = "Get all orders of the user")
    public List<OrderResponseDto> getAllOrders(Pageable pageable, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return orderService.getAllOrders(pageable, user);
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/{orderId}/items")
    @Operation(summary = "Get all order items for an order",
            description = "Get all order items by order ID")
    public List<OrderItemResponseDto> getOrderItems(@PathVariable Long orderId) {
        return orderItemService.getAllOrderItems(orderId);
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/{orderId}/items/{itemId}")
    @Operation(summary = "Get order item by ID",
            description = "Get order item by order ID and item ID")
    public OrderItemResponseDto getOrderItem(
            @PathVariable Long orderId,
            @PathVariable Long itemId) {

        return orderItemService.getOrderItem(orderId, itemId);
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping
    @Operation(summary = "Place an order",
            description = "Place an order of all the books in users shopping cart")
    public OrderResponseDto createOrder(
            @RequestBody @Valid CreateOrderRequestDto requestDto,
            Authentication authentication) {

        User user = (User) authentication.getPrincipal();
        return orderService.saveOrder(requestDto, user);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/{id}")
    @Operation(summary = "Update order status",
            description = "Update order status of the user")
    public void updateOrder(
            @RequestBody @Valid UpdateOrderRequestDto requestDto,
            @PathVariable Long id) {

        orderService.updateOrder(requestDto, id);
    }
}
