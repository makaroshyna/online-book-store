package makaroshyna.onlinebookstore.service.cartitem;

import lombok.RequiredArgsConstructor;
import makaroshyna.onlinebookstore.dto.cartitem.CreateCartItemRequestDto;
import makaroshyna.onlinebookstore.mapper.CartItemMapper;
import makaroshyna.onlinebookstore.model.CartItem;
import makaroshyna.onlinebookstore.model.ShoppingCart;
import makaroshyna.onlinebookstore.repository.cartitem.CartItemRepository;
import makaroshyna.onlinebookstore.service.book.BookService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartItemServiceImpl implements CartItemService {
    private final CartItemRepository cartItemRepository;
    private final CartItemMapper cartItemMapper;
    private final BookService bookService;

    @Override
    public CartItem save(CreateCartItemRequestDto requestDto, ShoppingCart shoppingCart) {
        CartItem cartItem = cartItemMapper.toModel(requestDto);
        cartItem.setShoppingCart(shoppingCart);
        String title = bookService.getById(requestDto.getBookId()).getTitle();
        cartItem.getBook().setTitle(title);
        return cartItemRepository.save(cartItem);
    }
}
