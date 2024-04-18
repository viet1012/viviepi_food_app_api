package com.food_app_api.Viviepi.mapper;

import com.food_app_api.Viviepi.dto.AddressDTO;
import com.food_app_api.Viviepi.entities.Address;
import com.food_app_api.Viviepi.entities.User;
import com.food_app_api.Viviepi.repositories.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class AddressMapper {

    @Autowired
    private IUserRepository userRepository;

//    public AddressDTO toAddressDTO(Address address) {
//        return AddressDTO.builder()
//                .id(address.getId())
//                .userId(address.getUserId())
//                .ward(address.getWard())
//                .district(address.getDistrict())
//                .houseNumber(address.getHouseNumber())
//                .note(address.getNote())
//                .active(address.isActive())
//                .build();
//    }
    public AddressDTO toAddressDTO(Address address) {
        Optional<User> optionalUser = userRepository.findById(address.getUserId());
        if(optionalUser.isPresent())
        {
            User user = optionalUser.get();;
            return AddressDTO.builder()
                    .id(address.getId())
                    .fullname(user.getFullname())
                    .phoneNumber(user.getPhoneNumber() )
                    .phoneNumber(user.getPhoneNumber())
                    .ward(address.getWard())
                    .district(address.getDistrict())
                    .houseNumber(address.getHouseNumber())
                    .note(address.getNote())
                    .active(address.isActive())
                    .build();
        }
        return null;
    }

    public List<AddressDTO> toAddressDTOList(List<Address> addressList) {
        return addressList.stream().map(this::toAddressDTO).collect(Collectors.toList());
    }

    public Address toAddress(AddressDTO addressDTO) {
        Address address = new Address();
        address.setId(addressDTO.getId());
        address.setUserId(addressDTO.getUserId());
        address.setWard(addressDTO.getWard());
        address.setDistrict(addressDTO.getDistrict());
        address.setHouseNumber(addressDTO.getHouseNumber());
        address.setNote(addressDTO.getNote());
        address.setActive(addressDTO.isActive());
        return address;
    }

    public Address toAddress(AddressDTO addressDTO, Address address) {
        address.setUserId(addressDTO.getUserId() != null ? addressDTO.getUserId() : address.getUserId());
        address.setWard(addressDTO.getWard() != null ? addressDTO.getWard() : address.getWard());
        address.setDistrict(addressDTO.getDistrict() != null ? addressDTO.getDistrict() : address.getDistrict());
        address.setHouseNumber(addressDTO.getHouseNumber() != null ? addressDTO.getHouseNumber() : address.getHouseNumber());
        address.setNote(addressDTO.getNote() != null ? addressDTO.getNote() : address.getNote());
        address.setActive(addressDTO.isActive());
        return address;
    }

    public List<Address> toAddressList(List<AddressDTO> addressDTOList) {
        return addressDTOList.stream().map(this::toAddress).collect(Collectors.toList());
    }
}
