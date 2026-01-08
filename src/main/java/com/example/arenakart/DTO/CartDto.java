package com.example.arenakart.DTO;

import lombok.Data;
import java.math.BigDecimal;
import java.util.List;

public class CartDto {
    private Long id;
    private List<CartItemDto> items;
    private BigDecimal totalPrice;
    private Integer totalItems;
	public CartDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	public CartDto(Long id, List<CartItemDto> items, BigDecimal totalPrice, Integer totalItems) {
		super();
		this.id = id;
		this.items = items;
		this.totalPrice = totalPrice;
		this.totalItems = totalItems;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public List<CartItemDto> getItems() {
		return items;
	}
	public void setItems(List<CartItemDto> items) {
		this.items = items;
	}
	public BigDecimal getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(BigDecimal totalPrice) {
		this.totalPrice = totalPrice;
	}
	public Integer getTotalItems() {
		return totalItems;
	}
	public void setTotalItems(Integer totalItems) {
		this.totalItems = totalItems;
	}
	@Override
	public String toString() {
		return "CartDto [id=" + id + ", items=" + items + ", totalPrice=" + totalPrice + ", totalItems=" + totalItems
				+ "]";
	}
    
}