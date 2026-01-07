package com.example.arenakart.Config;

import com.example.arenakart.Entities.*;
import com.example.arenakart.Repositories.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final PasswordEncoder passwordEncoder;
    

    public DataLoader(UserRepository userRepository, CategoryRepository categoryRepository,
			ProductRepository productRepository, PasswordEncoder passwordEncoder) {
		super();
		this.userRepository = userRepository;
		this.categoryRepository = categoryRepository;
		this.productRepository = productRepository;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
    public void run(String... args) throws Exception {
        if (userRepository.count() == 0) {
            loadSampleData();
        }
    }

    private void loadSampleData() {

        // -------- Create Admin User --------
        User admin = new User();
        admin.setEmail("admin@example.com");
        admin.setPassword(passwordEncoder.encode("admin123"));
        admin.setFirstName("Admin");
        admin.setLastName("User");
        admin.setRole(UserRole.ADMIN);
        admin.setActive(true);

        userRepository.save(admin);

        // -------- Create Categories --------
        Category electronics = new Category();
        electronics.setName("Electronics");
        electronics.setDescription("Electronic devices and gadgets");
        categoryRepository.save(electronics);

        Category clothing = new Category();
        clothing.setName("Clothing");
        clothing.setDescription("Fashion and apparel");
        categoryRepository.save(clothing);

        // -------- Create Products --------
        Product laptop = new Product();
        laptop.setName("Laptop Pro 15");
        laptop.setDescription("High-performance laptop for professionals");
        laptop.setPrice(new BigDecimal("1299.99"));
        laptop.setStockQuantity(50);
        laptop.setSku("LAP-001");
        laptop.setCategory(electronics);
        laptop.setActive(true);
        productRepository.save(laptop);

        Product tshirt = new Product();
        tshirt.setName("Cotton T-Shirt");
        tshirt.setDescription("Comfortable cotton t-shirt");
        tshirt.setPrice(new BigDecimal("29.99"));
        tshirt.setStockQuantity(200);
        tshirt.setSku("TSH-001");
        tshirt.setCategory(clothing);
        tshirt.setActive(true);
        productRepository.save(tshirt);

        System.out.println("Sample data loaded successfully!");
    }
}
