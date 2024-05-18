package makaroshyna.onlinebookstore.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import makaroshyna.onlinebookstore.dto.cartitem.CartItemResponseDto;
import makaroshyna.onlinebookstore.dto.cartitem.CreateCartItemRequestDto;
import makaroshyna.onlinebookstore.dto.cartitem.UpdateCartItemRequestDto;
import makaroshyna.onlinebookstore.dto.shoppingcart.ShoppingCartResponseDto;
import makaroshyna.onlinebookstore.model.User;
import makaroshyna.onlinebookstore.service.shoppingcart.ShoppingCartService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Shopping cart management", description = "Endpoints for managing shopping carts")
@RequiredArgsConstructor
@RestController
@RequestMapping("/cart")
public class ShoppingCartController {
    private final ShoppingCartService shoppingCartService;

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping
    @Operation(summary = "Get a shopping cart of a user",
            description = "Get a shopping cart of authenticated user")
    public ShoppingCartResponseDto getCart(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return shoppingCartService.getByUserId(user.getId());
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping
    @Operation(summary = "Add a book to the shopping cart",
            description = "Add a book to the shopping cart of the user")
    public CartItemResponseDto addBookToCart(
            @RequestBody @Valid CreateCartItemRequestDto requestDto,
            Authentication authentication) {

        User user = (User) authentication.getPrincipal();
        return shoppingCartService.addToCart(requestDto, user);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PutMapping("/cart-items/{cartItemId}")
    @Operation(summary = "Update a book by cart item id",
            description = "Update a books quantity by cart item ID")
    public CartItemResponseDto updateBookQuantity(
            @RequestBody @Valid UpdateCartItemRequestDto requestDto,
            @PathVariable Long cartItemId,
            Authentication authentication) {

        User user = (User) authentication.getPrincipal();
        return shoppingCartService.updateCart(requestDto, cartItemId, user);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/cart-items/{cartItemId}")
    public void deleteCartItem(@PathVariable Long cartItemId, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        shoppingCartService.deleteCartItem(cartItemId, user);
    }
}
