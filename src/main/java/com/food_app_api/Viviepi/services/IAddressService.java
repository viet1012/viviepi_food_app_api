package com.food_app_api.Viviepi.services;

import com.food_app_api.Viviepi.dto.AddressDTO;
import com.food_app_api.Viviepi.entities.Address;

import java.util.List;

public interface IAddressService {

    // Lấy một địa chỉ dựa trên ID
    AddressDTO getAddressById(Long id);

    // Lấy tất cả các địa chỉ
    List<AddressDTO> getAllAddresses();

    // Lấy tất cả các địa chỉ của một người dùng dựa trên ID người dùng
    List<AddressDTO> getAddressesByUserId(Long userId);

    // Thêm một địa chỉ mới
    List<AddressDTO> addAddresses(List<Address> addresses);

    // Cập nhật thông tin của một địa chỉ
    AddressDTO updateAddress(Long id, AddressDTO updatedAddress);

    // Xóa một địa chỉ dựa trên ID
    void deleteAddress(Long id);
}

