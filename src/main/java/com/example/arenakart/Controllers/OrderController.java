package com.example.arenakart.Controllers;

import com.example.arenakart.Entities.OrderStatus;
import com.example.arenakart.Entities.PaymentStatus;
import com.example.arenakart.DTO.ApiResponse;
import com.example.arenakart.DTO.CreateOrderDto;
import com.example.arenakart.DTO.OrderDto;
import com.example.arenakart.Services.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    private final CurrentUser currentUser;
    

    public OrderController(OrderService orderService, com.example.arenakart.Controllers.CurrentUser currentUser) {
		super();
		this.orderService = orderService;
		this.currentUser = currentUser;
	}

	@PostMapping
    public ResponseEntity<ApiResponse<OrderDto>> createOrder(
        @Valid @RequestBody CreateOrderDto dto,
        @AuthenticationPrincipal UserDetails userDetails
    ) {
        Long userId = currentUser.getUserId(userDetails);
        OrderDto order = orderService.createOrder(userId, dto);
        return ResponseEntity.ok(ApiResponse.success("Order created successfully", order));
    }

    @GetMapping
    public ResponseEntity<Page<OrderDto>> getUserOrders(
        @AuthenticationPrincipal UserDetails userDetails,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size
    ) {
        Long userId = currentUser.getUserId(userDetails);
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        return ResponseEntity.ok(orderService.getUserOrders(userId, pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<OrderDto>> getOrderById(@PathVariable Long id) {
        OrderDto order = orderService.getOrderById(id);
        return ResponseEntity.ok(ApiResponse.success(order));
    }

    @GetMapping("/number/{orderNumber}")
    public ResponseEntity<ApiResponse<OrderDto>> getOrderByNumber(@PathVariable String orderNumber) {
        OrderDto order = orderService.getOrderByNumber(orderNumber);
        return ResponseEntity.ok(ApiResponse.success(order));
    }

    @PutMapping("/{id}/status")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<ApiResponse<OrderDto>> updateOrderStatus(
        @PathVariable Long id,
        @RequestParam OrderStatus status
    ) {
        OrderDto order = orderService.updateOrderStatus(id, status);
        return ResponseEntity.ok(ApiResponse.success("Order status updated", order));
    }

    @PutMapping("/{id}/payment-status")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<ApiResponse<OrderDto>> updatePaymentStatus(
        @PathVariable Long id,
        @RequestParam PaymentStatus status
    ) {
        OrderDto order = orderService.updatePaymentStatus(id, status);
        return ResponseEntity.ok(ApiResponse.success("Payment status updated", order));
    }
}