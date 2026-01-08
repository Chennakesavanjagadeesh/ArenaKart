package com.example.arenakart.Services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.arenakart.DTO.AddressDto;
import com.example.arenakart.Entities.Address;
import com.example.arenakart.Entities.User;
import com.example.arenakart.Exceptions.ResourceNotFoundException;
import com.example.arenakart.Repositories.AddressRepository;

import java.util.List;

@Service
public class AddressService {

    private final AddressRepository addressRepository;
    private final UserService userService;
    

    public AddressService(AddressRepository addressRepository, UserService userService) {
		super();
		this.addressRepository = addressRepository;
		this.userService = userService;
	}

	public List<Address> getUserAddresses(Long userId) {
        return addressRepository.findByUserId(userId);
    }

    public Address getAddressById(Long id) {
        return addressRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Address not found"));
    }

    @Transactional
    public Address createAddress(Long userId, AddressDto dto) {
        User user = userService.getUserById(userId);

        // If this is set as default, unset other default addresses
        if (dto.isDefault()) {
            addressRepository.findByUserIdAndIsDefaultTrue(userId)
                    .ifPresent(addr -> {
                        addr.setDefault(false);
                        addressRepository.save(addr);
                    });
        }

        Address address = new Address();
        address.setUser(user);
        address.setStreet(dto.getStreet());
        address.setCity(dto.getCity());
        address.setState(dto.getState());
        address.setZipCode(dto.getZipCode());
        address.setCountry(dto.getCountry());
        address.setDefault(dto.isDefault());

        return addressRepository.save(address);
    }

    @Transactional
    public Address updateAddress(Long id, AddressDto dto) {
        Address address = getAddressById(id);

        if (dto.isDefault() && !address.isDefault()) {
            addressRepository.findByUserIdAndIsDefaultTrue(address.getUser().getId())
                    .ifPresent(addr -> {
                        addr.setDefault(false);
                        addressRepository.save(addr);
                    });
        }

        address.setStreet(dto.getStreet());
        address.setCity(dto.getCity());
        address.setState(dto.getState());
        address.setZipCode(dto.getZipCode());
        address.setCountry(dto.getCountry());
        address.setDefault(dto.isDefault());

        return addressRepository.save(address);
    }

    @Transactional
    public void deleteAddress(Long id) {
        Address address = getAddressById(id);
        addressRepository.delete(address);
    }
}
