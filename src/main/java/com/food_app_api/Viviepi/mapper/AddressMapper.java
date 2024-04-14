package com.food_app_api.Viviepi.mapper;

import com.food_app_api.Viviepi.dto.AddressDTO;
import com.food_app_api.Viviepi.entities.Address;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class AddressMapper {

    public AddressDTO toAddressDTO(Address address) {
        return AddressDTO.builder()
                .id(address.getId())
                .userId(address.getUserId())
                .ward(address.getWard())
                .district(address.getDistrict())
                .houseNumber(address.getHouseNumber())
                .note(address.getNote())
                .active(address.isActive())
                .build();
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
