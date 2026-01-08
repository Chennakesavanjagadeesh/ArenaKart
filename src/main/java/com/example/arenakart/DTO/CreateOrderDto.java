package com.example.arenakart.DTO;

import com.example.arenakart.Entities.PaymentMethod;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

public class CreateOrderDto {
    @NotNull(message = "Shipping address is required")
    private Long shippingAddressId;

    @NotNull(message = "Payment method is required")
    private PaymentMethod paymentMethod;

	public CreateOrderDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CreateOrderDto(@NotNull(message = "Shipping address is required") Long shippingAddressId,
			@NotNull(message = "Payment method is required") PaymentMethod paymentMethod) {
		super();
		this.shippingAddressId = shippingAddressId;
		this.paymentMethod = paymentMethod;
	}

	public Long getShippingAddressId() {
		return shippingAddressId;
	}

	public void setShippingAddressId(Long shippingAddressId) {
		this.shippingAddressId = shippingAddressId;
	}

	public PaymentMethod getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(PaymentMethod paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	@Override
	public String toString() {
		return "CreateOrderDto [shippingAddressId=" + shippingAddressId + ", paymentMethod=" + paymentMethod + "]";
	}

	
    
}
