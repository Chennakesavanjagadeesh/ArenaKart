package com.example.arenakart.Controllers;

import com.example.arenakart.DTO.ApiResponse;
import com.example.arenakart.DTO.OrderDto;
import com.example.arenakart.Entities.Order;
import com.example.arenakart.Services.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
@RequiredArgsConstructor
public class AdminController {
    private final OrderService orderService;
    private final ProductService productService;
    private final UserService userService;
    

    public AdminController(OrderService orderService, ProductService productService, UserService userService) {
		super();
		this.orderService = orderService;
		this.productService = productService;
		this.userService = userService;
	}

	@GetMapping("/orders")
    public ResponseEntity<Page<OrderDto>> getAllOrders(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "20") int size
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        return ResponseEntity.ok(orderService.getAllOrders(pageable));
    }

    @GetMapping("/dashboard/stats")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getDashboardStats() {
        Map<String, Object> stats = new HashMap<>();
        // Add dashboard statistics here
        stats.put("message", "Dashboard stats endpoint - implement metrics as needed");
        return ResponseEntity.ok(ApiResponse.success(stats));
    }

    @PutMapping("/users/{id}/deactivate")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<ApiResponse<Void>> deactivateUser(@PathVariable Long id) {
        userService.deactivateUser(id);
        return ResponseEntity.ok(ApiResponse.success("User deactivated", null));
    }

    @PutMapping("/products/{id}/stock")
    public ResponseEntity<ApiResponse<Void>> updateStock(
        @PathVariable Long id,
        @RequestParam Integer quantity
    ) {
        productService.updateStock(id, quantity);
        return ResponseEntity.ok(ApiResponse.success("Stock updated", null));
    }
}
