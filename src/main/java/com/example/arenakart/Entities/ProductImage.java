package com.example.arenakart.Entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "product_images")
public class ProductImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    private String url;
    private boolean isPrimary = false;
	public ProductImage() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ProductImage(Long id, Product product, String url, boolean isPrimary) {
		super();
		this.id = id;
		this.product = product;
		this.url = url;
		this.isPrimary = isPrimary;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public boolean isPrimary() {
		return isPrimary;
	}
	public void setPrimary(boolean isPrimary) {
		this.isPrimary = isPrimary;
	}
	@Override
	public String toString() {
		return "ProductImage [id=" + id + ", product=" + product + ", url=" + url + ", isPrimary=" + isPrimary + "]";
	}
    
}