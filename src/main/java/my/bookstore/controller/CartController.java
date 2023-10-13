package my.bookstore.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import my.bookstore.dto.cart.CartItemQuantityRequestDto;
import my.bookstore.dto.cart.CartItemRequestDto;
import my.bookstore.dto.cart.ShoppingCartDto;
import my.bookstore.model.User;
import my.bookstore.service.ShoppingCartService;
import my.bookstore.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Shopping cart management", description = "Endpoints for managing users shopping cart")
@RequiredArgsConstructor
@RestController
@RequestMapping("/cart")
public class CartController {
    private final ShoppingCartService shoppingCartService;
    private final UserService userService;

    @PostMapping
    @PreAuthorize("hasRole('ROLE_USER')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(
            summary = "Add book to shopping cart",
            description = "Add the selected book to the users shopping cart"
    )
    public void addBookToCart(Authentication auth,
                              @Valid @RequestBody CartItemRequestDto requestDto) {
        User user = getUserByEmail(auth);
        shoppingCartService.addBookToCart(user, requestDto);
    }

    @GetMapping
    @PreAuthorize("hasRole('ROLE_USER')")
    @Operation(
            summary = "Show shopping cart", description = "Show all books in users shopping cart")
    public ShoppingCartDto getShoppingCart(Authentication auth) {
        User user = getUserByEmail(auth);
        return shoppingCartService.getShoppingCart(user);
    }

    @PutMapping("/cart-items/{cartItemId}")
    @PreAuthorize("hasRole('ROLE_USER')")
    @Operation(
            summary = "Update book quantity",
            description = "Change quantity of selected book in users shopping cart"
    )
    public ShoppingCartDto updateBookQuantity(Authentication auth,
                                              @RequestBody CartItemQuantityRequestDto requestDto,
                                              @PathVariable Long cartItemId) {
        User user = getUserByEmail(auth);
        return shoppingCartService.updateCartItemsQuantity(user, requestDto, cartItemId);
    }

    @DeleteMapping ("/cart-items/{cartItemId}")
    @PreAuthorize("hasRole('ROLE_USER')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(
            summary = "Delete book in users shopping cart",
            description = "Delete selected book in users shopping cart"
    )
    public void removeItemFromShoppingCart(Authentication auth, @PathVariable Long cartItemId) {
        User user = getUserByEmail(auth);
        shoppingCartService.removeItemFromShoppingCart(user, cartItemId);
    }

    private User getUserByEmail(Authentication auth) {
        UserDetails details = (UserDetails) auth.getPrincipal();
        String email = details.getUsername();
        return userService.findByEmail(email).orElseThrow(
                () -> new RuntimeException("User with email " + email + " not found"));
    }
}
