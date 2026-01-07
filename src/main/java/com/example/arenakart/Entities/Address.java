package com.example.arenakart.Entities;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "addresses")
@Data       // generates getters, setters, equals, hashCode, toString
@NoArgsConstructor
@AllArgsConstructor
@Builder    // generates builder()
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String street;
    private String city;
    private String state;
    private String zipCode;
    private String country;

    private boolean isDefault;
    // Note: field name matches getter/setter

	public Address() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Address(Long id, User user, String street, String city, String state, String zipCode, String country,
			boolean isDefault) {
		super();
		this.id = id;
		this.user = user;
		this.street = street;
		this.city = city;
		this.state = state;
		this.zipCode = zipCode;
		this.country = country;
		this.isDefault = isDefault;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getZipCode() {
		return zipCode;
	}
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public boolean isDefault() {
		return isDefault;
	}
	public void setDefault(boolean isDefault) {
		this.isDefault = isDefault;
	}
	@Override
	public String toString() {
		return "Address [id=" + id + ", user=" + user + ", street=" + street + ", city=" + city + ", state=" + state
				+ ", zipCode=" + zipCode + ", country=" + country + ", isDefault=" + isDefault + "]";
	}
    
}
