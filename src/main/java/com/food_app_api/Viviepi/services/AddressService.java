package com.food_app_api.Viviepi.services;


import com.food_app_api.Viviepi.dto.AddressDTO;
import com.food_app_api.Viviepi.entities.Address;
import com.food_app_api.Viviepi.mapper.AddressMapper;
import com.food_app_api.Viviepi.repositories.IAddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AddressService implements IAddressService {

    @Autowired
    private  IAddressRepository addressRepository;

    @Autowired
    private  AddressMapper addressMapper;



    @Override
    public AddressDTO getAddressById(Long id) {
        Optional<Address> optionalAddress = addressRepository.findById(id);
        return optionalAddress.map(addressMapper::toAddressDTO).orElse(null);
    }

    @Override
    public List<AddressDTO> getAllAddresses() {
        List<Address> addressList = addressRepository.findAll();
        return addressMapper.toAddressDTOList(addressList);
    }

    @Override
    public List<AddressDTO> getAddressesByUserId(Long userId) {
        List<Address> addressList = addressRepository.findByUserId(userId);
        return addressMapper.toAddressDTOList(addressList);
    }

    @Override
    public List<AddressDTO> addAddresses(List<Address> addresses) {
        List<Address> savedAddresses = addressRepository.saveAll(addresses);
        return addressMapper.toAddressDTOList(savedAddresses);
    }

    @Override
    public AddressDTO updateAddress(Long id, AddressDTO updatedAddress) {
        Optional<Address> optionalAddress = addressRepository.findById(id);
        if (optionalAddress.isPresent()) {
            Address existingAddress = optionalAddress.get();
            addressMapper.toAddress(updatedAddress, existingAddress);
            Address updated = addressRepository.save(existingAddress);
            return addressMapper.toAddressDTO(updated);
        }
        return null; // or throw NotFoundException
    }

    @Override
    public void deleteAddress(Long id) {
        addressRepository.deleteById(id);
    }
}
