package com.food_app_api.Viviepi.controllers;

import com.food_app_api.Viviepi.dto.AddressDTO;
import com.food_app_api.Viviepi.entities.Address;
import com.food_app_api.Viviepi.payload.response.ResponseObject;
import com.food_app_api.Viviepi.services.IAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/address/api")
public class AddressController {

    private final IAddressService addressService;

    @Autowired
    public AddressController(IAddressService addressService) {
        this.addressService = addressService;
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<ResponseObject> getAddressesByUserId(@PathVariable("userId") Long userId) {
        List<AddressDTO> addressDTOs = addressService.getAddressesByUserId(userId);
        return ResponseEntity.ok().body(new ResponseObject(HttpStatus.OK.value(), "Success", addressDTOs));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseObject> getAddressById(@PathVariable("id") Long id) {
        AddressDTO addressDTO = addressService.getAddressById(id);
        if (addressDTO != null) {
            return ResponseEntity.ok().body(new ResponseObject(HttpStatus.OK.value(), "Success", addressDTO));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject(HttpStatus.NOT_FOUND.value(), "Address not found", null));
        }
    }

    @GetMapping("/get/all")
    public ResponseEntity<ResponseObject> getAllAddress() {
        List<AddressDTO> addressDTOs = addressService.getAllAddresses();
        return ResponseEntity.ok().body(new ResponseObject(HttpStatus.OK.value(), "Success", addressDTOs));
    }

    @PostMapping("/insert")
    public ResponseEntity<ResponseObject> addAddresses(@RequestBody List<Address> addresses) {
        List<AddressDTO> addedAddresses = addressService.addAddresses(addresses);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseObject(HttpStatus.CREATED.value(), "Addresses added successfully", addedAddresses));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ResponseObject> updateAddress(@PathVariable("id") Long id, @RequestBody AddressDTO updatedAddressDTO) {
        AddressDTO updatedAddress = addressService.updateAddress(id, updatedAddressDTO);
        if (updatedAddress != null) {
            return ResponseEntity.ok().body(new ResponseObject(HttpStatus.OK.value(), "Address updated successfully", updatedAddress));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject(HttpStatus.NOT_FOUND.value(), "Address not found", null));
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ResponseObject> deleteAddress(@PathVariable("id") Long id) {
        addressService.deleteAddress(id);
        return ResponseEntity.noContent().build();
    }
}
