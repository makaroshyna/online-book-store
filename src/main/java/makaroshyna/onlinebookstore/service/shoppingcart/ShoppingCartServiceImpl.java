package makaroshyna.onlinebookstore.service.shoppingcart;

import jakarta.transaction.Transactional;
import java.util.HashSet;
import lombok.RequiredArgsConstructor;
import makaroshyna.onlinebookstore.dto.cartitem.CartItemResponseDto;
import makaroshyna.onlinebookstore.dto.cartitem.CreateCartItemRequestDto;
import makaroshyna.onlinebookstore.dto.cartitem.UpdateCartItemRequestDto;
import makaroshyna.onlinebookstore.dto.shoppingcart.ShoppingCartResponseDto;
import makaroshyna.onlinebookstore.exception.EntityNotFoundException;
import makaroshyna.onlinebookstore.mapper.CartItemMapper;
import makaroshyna.onlinebookstore.mapper.ShoppingCartMapper;
import makaroshyna.onlinebookstore.model.CartItem;
import makaroshyna.onlinebookstore.model.ShoppingCart;
import makaroshyna.onlinebookstore.model.User;
import makaroshyna.onlinebookstore.repository.shoppingcart.ShoppingCartRepository;
import makaroshyna.onlinebookstore.service.cartitem.CartItemService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ShoppingCartServiceImpl implements ShoppingCartService {
    private final ShoppingCartRepository shoppingCartRepository;
    private final ShoppingCartMapper shoppingCartMapper;
    private final CartItemMapper cartItemMapper;
    private final CartItemService cartItemService;

    @Override
    public ShoppingCartResponseDto getByUserId(Long userId) {
        return shoppingCartMapper.toDto(getShoppingCartForUser(userId));
    }

    @Override
    @Transactional
    public CartItemResponseDto addToCart(CreateCartItemRequestDto requestDto, Long userId) {
        ShoppingCart shoppingCart = getShoppingCartForUser(userId);
        CartItem cartItem = cartItemService.addToCart(requestDto, shoppingCart);
        shoppingCart.getCartItems().add(cartItem);

        return cartItemMapper.toDto(cartItem);
    }

    @Override
    @Transactional
    public CartItemResponseDto updateCart(
            UpdateCartItemRequestDto requestDto,
            Long cartItemId,
            Long userId) {

        ShoppingCart shoppingCart = getShoppingCartForUser(userId);
        CartItem cartItem = cartItemService.update(cartItemId, requestDto);
        shoppingCartRepository.save(shoppingCart);

        return cartItemMapper.toDto(cartItem);
    }

    @Override
    @Transactional
    public void deleteCartItem(Long cartItemId, Long userId) {
        ShoppingCart shoppingCart = getShoppingCartForUser(userId);
        CartItem cartItem = shoppingCart.getCartItems().stream()
                .filter(item -> item.getId().equals(cartItemId))
                .findAny()
                .orElseThrow(() -> new EntityNotFoundException(
                        "Can't find cart item with id " + cartItemId));
        cartItemService.delete(cartItem.getId());
    }

    @Override
    public void createShoppingCartForUser(User user) {
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setUser(user);
        shoppingCart.setCartItems(new HashSet<>());
        shoppingCartRepository.save(shoppingCart);
    }

    private ShoppingCart getShoppingCartForUser(Long userId) {
        return shoppingCartRepository.findByUserId(userId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Cannot find shopping cart for user ID " + userId));
    }
}
