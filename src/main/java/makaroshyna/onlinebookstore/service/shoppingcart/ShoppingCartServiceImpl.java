package makaroshyna.onlinebookstore.service.shoppingcart;

import lombok.RequiredArgsConstructor;
import makaroshyna.onlinebookstore.dto.shoppingcart.ShoppingCartResponseDto;
import makaroshyna.onlinebookstore.exception.EntityNotFoundException;
import makaroshyna.onlinebookstore.mapper.ShoppingCartMapper;
import makaroshyna.onlinebookstore.repository.shoppingcart.ShoppingCartRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ShoppingCartServiceImpl implements ShoppingCartService {
    private final ShoppingCartRepository shoppingCartRepository;
    private final ShoppingCartMapper shoppingCartMapper;

    @Override
    public ShoppingCartResponseDto getByUserId(Long userId) {
        return shoppingCartMapper.toDto(shoppingCartRepository
                        .findByUserId(userId)
                        .orElseThrow(() -> new EntityNotFoundException(
                                "Can't find shopping cart by user id: " + userId)));
    }
}
