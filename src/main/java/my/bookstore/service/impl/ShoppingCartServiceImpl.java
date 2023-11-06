package my.bookstore.service.impl;

import lombok.RequiredArgsConstructor;
import my.bookstore.dto.cart.CartItemQuantityRequestDto;
import my.bookstore.dto.cart.CartItemRequestDto;
import my.bookstore.dto.cart.ShoppingCartDto;
import my.bookstore.exception.EntityNotFoundException;
import my.bookstore.mapper.ShoppingCartMapper;
import my.bookstore.model.Book;
import my.bookstore.model.CartItem;
import my.bookstore.model.ShoppingCart;
import my.bookstore.model.User;
import my.bookstore.repository.book.BookRepository;
import my.bookstore.repository.cart.CartItemRepository;
import my.bookstore.repository.cart.ShoppingCartRepository;
import my.bookstore.service.ShoppingCartService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ShoppingCartServiceImpl implements ShoppingCartService {
    private final BookRepository bookRepository;
    private final CartItemRepository cartItemRepository;
    private final ShoppingCartMapper shoppingCartMapper;
    private final ShoppingCartRepository shoppingCartRepository;

    @Override
    public void registerNewShoppingCart(User user) {
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setUser(user);
        shoppingCartRepository.save(shoppingCart);
    }

    @Override
    @Transactional
    public void addBookToCart(User user, CartItemRequestDto cartItemRequestDto) {
        ShoppingCart shoppingCart = shoppingCartRepository.findByUser(user);
        Book book = bookRepository.findById(cartItemRequestDto.getBookId()).orElseThrow(
                () -> new EntityNotFoundException("Can't find book with id "
                        + cartItemRequestDto.getBookId())
        );
        CartItem cartItem = new CartItem();
        cartItem.setShoppingCart(shoppingCart);
        cartItem.setBook(book);
        cartItem.setQuantity(cartItemRequestDto.getQuantity());
        CartItem savedCartItem = cartItemRepository.save(cartItem);
        shoppingCart.getCartItems().add(savedCartItem);
        shoppingCartRepository.save(shoppingCart);
    }

    @Override
    public ShoppingCartDto getShoppingCart(User user) {
        ShoppingCart shoppingCart = shoppingCartRepository.findByUser(user);
        return shoppingCartMapper.toDto(shoppingCart);
    }

    @Override
    @Transactional
    public ShoppingCartDto updateCartItemsQuantity(User user,
                                                   CartItemQuantityRequestDto requestDto,
                                                   Long cartItemId) {
        ShoppingCart shoppingCart = shoppingCartRepository.findByUser(user);
        CartItem cartItem = getItemFromShoppingCartById(shoppingCart, cartItemId);
        cartItem.setQuantity(requestDto.getQuantity());
        ShoppingCart savedShoppingCart = shoppingCartRepository.save(shoppingCart);
        return shoppingCartMapper.toDto(savedShoppingCart);
    }

    @Override
    @Transactional
    public void removeItemFromShoppingCart(User user, Long cartItemId) {
        ShoppingCart shoppingCart = shoppingCartRepository.findByUser(user);
        CartItem cartItem = getItemFromShoppingCartById(shoppingCart, cartItemId);
        shoppingCart.getCartItems().remove(cartItem);
        cartItemRepository.deleteByIdAndShoppingCartId(cartItemId, shoppingCart.getId());
        shoppingCartRepository.save(shoppingCart);
    }

    @Override
    public void clear(Long shoppingCartId) {
        cartItemRepository.deleteAllByShoppingCartId(shoppingCartId);
    }

    private CartItem getItemFromShoppingCartById(ShoppingCart shoppingCart, Long id) {
        return shoppingCart.getCartItems().stream()
                .filter(ci -> ci.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("Can't find item with id " + id));
    }
}
