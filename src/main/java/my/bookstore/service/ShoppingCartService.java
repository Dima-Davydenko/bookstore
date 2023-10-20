package my.bookstore.service;

import my.bookstore.dto.cart.CartItemQuantityRequestDto;
import my.bookstore.dto.cart.CartItemRequestDto;
import my.bookstore.dto.cart.ShoppingCartDto;
import my.bookstore.model.User;

public interface ShoppingCartService {
    void registerNewShoppingCart(User user);

    void addBookToCart(User user, CartItemRequestDto requestDto);

    ShoppingCartDto getShoppingCart(User user);

    ShoppingCartDto updateCartItemsQuantity(User user,
                                            CartItemQuantityRequestDto requestDto,
                                            Long cartItemId);

    void removeItemFromShoppingCart(User user, Long cartItemId);

    void clear(Long shoppingCartId);
}
