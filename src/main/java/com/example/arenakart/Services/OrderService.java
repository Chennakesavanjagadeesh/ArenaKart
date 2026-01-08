package com.example.arenakart.Services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.arenakart.DTO.*;
import com.example.arenakart.Entities.*;
import com.example.arenakart.Exceptions.*;
import com.example.arenakart.Repositories.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final AddressRepository addressRepository;
    private final CartService cartService;
    private final ProductService productService;
    private final UserService userService;
    

    public OrderService(OrderRepository orderRepository, AddressRepository addressRepository, CartService cartService,
			ProductService productService, UserService userService) {
		super();
		this.orderRepository = orderRepository;
		this.addressRepository = addressRepository;
		this.cartService = cartService;
		this.productService = productService;
		this.userService = userService;
	}

	@Transactional
    public OrderDto createOrder(Long userId, CreateOrderDto dto) {
        com.example.arenakart.Entities.User user = (com.example.arenakart.Entities.User) userService.getUserById(userId);
        Cart cart = cartService.getCartByUserId(userId);

        if (cart.getItems().isEmpty()) {
            throw new EmptyCartException("Cannot create order with empty cart");
        }

        for (CartItem item : cart.getItems()) {
            if (!productService.checkStock(item.getProduct().getId(), item.getQuantity())) {
                throw new InsufficientStockException(
                        "Insufficient stock for: " + item.getProduct().getName()
                );
            }
        }

        Address shippingAddress = addressRepository.findById(dto.getShippingAddressId())
                .orElseThrow(() -> new ResourceNotFoundException("Address not found"));

        // Create order without builder
        Order order = new Order();
        order.setOrderNumber(generateOrderNumber());
        order.setUser(user);
        order.setStatus(OrderStatus.PENDING);
        order.setShippingAddress(shippingAddress);
        order.setPaymentMethod(dto.getPaymentMethod());
        order.setPaymentStatus(PaymentStatus.PENDING);
        order.setTotalAmount(cart.getTotalPrice());

        Set<OrderItem> orderItems = new HashSet<>();
        for (CartItem cartItem : cart.getItems()) {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProduct(cartItem.getProduct());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setPrice(cartItem.getProduct().getPrice());
            orderItems.add(orderItem);

            // Reduce stock
            productService.updateStock(cartItem.getProduct().getId(), -cartItem.getQuantity());
        }
        order.setItems(orderItems);

        order = orderRepository.save(order);

        cartService.clearCart(userId);

        return convertToDto(order);
    }

    public OrderDto getOrderById(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));
        return convertToDto(order);
    }

    public OrderDto getOrderByNumber(String orderNumber) {
        Order order = orderRepository.findByOrderNumber(orderNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));
        return convertToDto(order);
    }

    public Page<OrderDto> getUserOrders(Long userId, Pageable pageable) {
        return orderRepository.findByUserId(userId, pageable)
                .map(this::convertToDto);
    }

    public Page<OrderDto> getAllOrders(Pageable pageable) {
        return orderRepository.findAll(pageable)
                .map(this::convertToDto);
    }

    @Transactional
    public OrderDto updateOrderStatus(Long orderId, OrderStatus status) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));

        order.setStatus(status);

        if (status == OrderStatus.DELIVERED) {
            order.setCompletedAt(LocalDateTime.now());
        }

        if (status == OrderStatus.CANCELLED) {
            for (OrderItem item : order.getItems()) {
                productService.updateStock(item.getProduct().getId(), item.getQuantity());
            }
        }

        order = orderRepository.save(order);
        return convertToDto(order);
    }

    @Transactional
    public OrderDto updatePaymentStatus(Long orderId, PaymentStatus status) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));

        order.setPaymentStatus(status);

        if (status == PaymentStatus.COMPLETED) {
            order.setStatus(OrderStatus.CONFIRMED);
        }

        order = orderRepository.save(order);
        return convertToDto(order);
    }

    private String generateOrderNumber() {
        return "ORD-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    private OrderDto convertToDto(Order order) {
        OrderDto dto = new OrderDto();
        dto.setId(order.getId());
        dto.setOrderNumber(order.getOrderNumber());
        dto.setStatus(order.getStatus());
        dto.setItems(order.getItems().stream()
                .map(this::convertItemToDto)
                .collect(Collectors.toList()));
        dto.setTotalAmount(order.getTotalAmount());
        dto.setShippingAddress(convertAddressToDto(order.getShippingAddress()));
        dto.setPaymentMethod(order.getPaymentMethod());
        dto.setPaymentStatus(order.getPaymentStatus());
        dto.setCreatedAt(order.getCreatedAt());
        dto.setCompletedAt(order.getCompletedAt());
        return dto;
    }

    private OrderItemDto convertItemToDto(OrderItem item) {
        OrderItemDto dto = new OrderItemDto();
        dto.setId(item.getId());
        dto.setProductId(item.getProduct().getId());
        dto.setProductName(item.getProduct().getName());
        dto.setQuantity(item.getQuantity());
        dto.setPrice(item.getPrice());
        dto.setSubtotal(item.getSubtotal());
        return dto;
    }

    private AddressDto convertAddressToDto(Address address) {
        AddressDto dto = new AddressDto();
        dto.setId(address.getId());
        dto.setStreet(address.getStreet());
        dto.setCity(address.getCity());
        dto.setState(address.getState());
        dto.setZipCode(address.getZipCode());
        dto.setCountry(address.getCountry());
        dto.setDefault(address.isDefault());
        return dto;
    }
}
