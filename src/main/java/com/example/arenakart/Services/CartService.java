package com.example.arenakart.Services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.arenakart.DTO.AddToCartDto;
import com.example.arenakart.DTO.CartDto;
import com.example.arenakart.DTO.CartItemDto;
import com.example.arenakart.Entities.Cart;
import com.example.arenakart.Entities.CartItem;
import com.example.arenakart.Entities.Product;
import com.example.arenakart.Exceptions.InsufficientStockException;
import com.example.arenakart.Exceptions.ResourceNotFoundException;
import com.example.arenakart.Repositories.CartItemRepository;
import com.example.arenakart.Repositories.CartRepository;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@Service
public class CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductService productService;
    

    public CartService(CartRepository cartRepository, CartItemRepository cartItemRepository,
			ProductService productService) {
		super();
		this.cartRepository = cartRepository;
		this.cartItemRepository = cartItemRepository;
		this.productService = productService;
	}

	public Cart getCartByUserId(Long userId) {
        return cartRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found"));
    }

    public CartDto getCartDto(Long userId) {
        Cart cart = getCartByUserId(userId);
        return convertToDto(cart);
    }

    @Transactional
    public CartDto addToCart(Long userId, AddToCartDto dto) {
        Cart cart = getCartByUserId(userId);
        Product product = productService.getProductById(dto.getProductId());

        // Check stock availability
        if (!productService.checkStock(product.getId(), dto.getQuantity())) {
            throw new InsufficientStockException("Insufficient stock for product: " + product.getName());
        }

        // Check if product already in cart
        CartItem cartItem = cartItemRepository
                .findByCartIdAndProductId(cart.getId(), product.getId())
                .orElse(null);

        if (cartItem != null) {
            // Update quantity
            int newQuantity = cartItem.getQuantity() + dto.getQuantity();
            if (!productService.checkStock(product.getId(), newQuantity)) {
                throw new InsufficientStockException("Insufficient stock for product: " + product.getName());
            }
            cartItem.setQuantity(newQuantity);
        } else {
            // Create new cart item
            cartItem = new CartItem();
            cartItem.setCart(cart);
            cartItem.setProduct(product);
            cartItem.setQuantity(dto.getQuantity());
            cart.getItems().add(cartItem);
        }

        cart.setUpdatedAt(LocalDateTime.now());
        cartRepository.save(cart);
        return convertToDto(cart);
    }

    @Transactional
    public CartDto updateCartItem(Long userId, Long cartItemId, Integer quantity) {
        Cart cart = getCartByUserId(userId);
        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart item not found"));

        if (!cart.getItems().contains(cartItem)) {
            throw new ResourceNotFoundException("Cart item not found in user's cart");
        }

        if (quantity <= 0) {
            cart.getItems().remove(cartItem);
            cartItemRepository.delete(cartItem);
        } else {
            if (!productService.checkStock(cartItem.getProduct().getId(), quantity)) {
                throw new InsufficientStockException("Insufficient stock");
            }
            cartItem.setQuantity(quantity);
        }

        cart.setUpdatedAt(LocalDateTime.now());
        cartRepository.save(cart);
        return convertToDto(cart);
    }

    @Transactional
    public void removeFromCart(Long userId, Long cartItemId) {
        Cart cart = getCartByUserId(userId);
        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart item not found"));

        if (!cart.getItems().contains(cartItem)) {
            throw new ResourceNotFoundException("Cart item not found in user's cart");
        }

        cart.getItems().remove(cartItem);
        cartItemRepository.delete(cartItem);
        cart.setUpdatedAt(LocalDateTime.now());
        cartRepository.save(cart);
    }

    @Transactional
    public void clearCart(Long userId) {
        Cart cart = getCartByUserId(userId);
        cart.getItems().clear();
        cart.setUpdatedAt(LocalDateTime.now());
        cartRepository.save(cart);
    }

    private CartDto convertToDto(Cart cart) {
        CartDto dto = new CartDto();
        dto.setId(cart.getId());
        dto.setItems(cart.getItems().stream()
                .map(this::convertItemToDto)
                .collect(Collectors.toList()));
        dto.setTotalPrice(cart.getTotalPrice());
        dto.setTotalItems(cart.getItems().stream()
                .mapToInt(CartItem::getQuantity)
                .sum());
        return dto;
    }

    private CartItemDto convertItemToDto(CartItem item) {
        CartItemDto dto = new CartItemDto();
        dto.setId(item.getId());
        dto.setProductId(item.getProduct().getId());
        dto.setProductName(item.getProduct().getName());
        dto.setPrice(item.getProduct().getPrice());
        dto.setQuantity(item.getQuantity());
        dto.setSubtotal(item.getSubtotal());
        dto.setImageUrl(item.getProduct().getImageUrl());
        return dto;
    }
}
