package com.example.arenakart.DTO;
import com.example.arenakart.Entities.OrderStatus;
import com.example.arenakart.Entities.PaymentMethod;
import com.example.arenakart.Entities.PaymentStatus;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
public class OrderDto {
    private Long id;
    private String orderNumber;
    private OrderStatus status;
    private List<OrderItemDto> items;
    private BigDecimal totalAmount;
    private AddressDto shippingAddress;
    private PaymentMethod paymentMethod;
    private PaymentStatus paymentStatus;
    private LocalDateTime createdAt;
    private LocalDateTime completedAt;
	public OrderDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	public OrderDto(Long id, String orderNumber, OrderStatus status, List<OrderItemDto> items, BigDecimal totalAmount,
			AddressDto shippingAddress, PaymentMethod paymentMethod, PaymentStatus paymentStatus,
			LocalDateTime createdAt, LocalDateTime completedAt) {
		super();
		this.id = id;
		this.orderNumber = orderNumber;
		this.status = status;
		this.items = items;
		this.totalAmount = totalAmount;
		this.shippingAddress = shippingAddress;
		this.paymentMethod = paymentMethod;
		this.paymentStatus = paymentStatus;
		this.createdAt = createdAt;
		this.completedAt = completedAt;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getOrderNumber() {
		return orderNumber;
	}
	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}
	public OrderStatus getStatus() {
		return status;
	}
	public void setStatus(OrderStatus status) {
		this.status = status;
	}
	public List<OrderItemDto> getItems() {
		return items;
	}
	public void setItems(List<OrderItemDto> items) {
		this.items = items;
	}
	public BigDecimal getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}
	public AddressDto getShippingAddress() {
		return shippingAddress;
	}
	public void setShippingAddress(AddressDto shippingAddress) {
		this.shippingAddress = shippingAddress;
	}
	public PaymentMethod getPaymentMethod() {
		return paymentMethod;
	}
	public void setPaymentMethod(PaymentMethod paymentMethod) {
		this.paymentMethod = paymentMethod;
	}
	public PaymentStatus getPaymentStatus() {
		return paymentStatus;
	}
	public void setPaymentStatus(PaymentStatus paymentStatus) {
		this.paymentStatus = paymentStatus;
	}
	public LocalDateTime getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
	public LocalDateTime getCompletedAt() {
		return completedAt;
	}
	public void setCompletedAt(LocalDateTime completedAt) {
		this.completedAt = completedAt;
	}
	@Override
	public String toString() {
		return "OrderDto [id=" + id + ", orderNumber=" + orderNumber + ", status=" + status + ", items=" + items
				+ ", totalAmount=" + totalAmount + ", shippingAddress=" + shippingAddress + ", paymentMethod="
				+ paymentMethod + ", paymentStatus=" + paymentStatus + ", createdAt=" + createdAt + ", completedAt="
				+ completedAt + "]";
	}
    
    
}