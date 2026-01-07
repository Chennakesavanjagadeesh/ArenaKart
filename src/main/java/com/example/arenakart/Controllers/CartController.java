package com.example.arenakart.Controllers;

import com.example.arenakart.DTO.*;
import com.example.arenakart.Services.CartService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;
    private final CurrentUser currentUser;
    

    public CartController(CartService cartService, CurrentUser currentUser) {
		super();
		this.cartService = cartService;
		this.currentUser = currentUser;
	}

	@GetMapping
    public ResponseEntity<ApiResponse<CartDto>> getCart(@AuthenticationPrincipal UserDetails userDetails) {
        Long userId = currentUser.getUserId(userDetails);
        CartDto cart = cartService.getCartDto(userId);
        return ResponseEntity.ok(ApiResponse.success(cart));
    }

    @PostMapping("/items")
    public ResponseEntity<ApiResponse<CartDto>> addToCart(
        @Valid @RequestBody AddToCartDto dto,
        @AuthenticationPrincipal UserDetails userDetails
    ) {
        Long userId = currentUser.getUserId(userDetails);
        CartDto cart = cartService.addToCart(userId, dto);
        return ResponseEntity.ok(ApiResponse.success("Item added to cart", cart));
    }

    @PutMapping("/items/{cartItemId}")
    public ResponseEntity<ApiResponse<CartDto>> updateCartItem(
        @PathVariable Long cartItemId,
        @RequestParam Integer quantity,
        @AuthenticationPrincipal UserDetails userDetails
    ) {
        Long userId = currentUser.getUserId(userDetails);
        CartDto cart = cartService.updateCartItem(userId, cartItemId, quantity);
        return ResponseEntity.ok(ApiResponse.success("Cart item updated", cart));
    }

    @DeleteMapping("/items/{cartItemId}")
    public ResponseEntity<ApiResponse<Void>> removeFromCart(
        @PathVariable Long cartItemId,
        @AuthenticationPrincipal UserDetails userDetails
    ) {
        Long userId = currentUser.getUserId(userDetails);
        cartService.removeFromCart(userId, cartItemId);
        return ResponseEntity.ok(ApiResponse.success("Item removed from cart", null));
    }

    @DeleteMapping
    public ResponseEntity<ApiResponse<Void>> clearCart(@AuthenticationPrincipal UserDetails userDetails) {
        Long userId = currentUser.getUserId(userDetails);
        cartService.clearCart(userId);
        return ResponseEntity.ok(ApiResponse.success("Cart cleared", null));
    }
}
