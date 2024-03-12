package com.food_app_api.Viviepi.controllers;

import com.food_app_api.Viviepi.dto.VoucherDTO;
import com.food_app_api.Viviepi.entities.Voucher;
import com.food_app_api.Viviepi.services.VoucherService;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/voucher/api")
public class VoucherController {

    @Autowired
    private VoucherService voucherService;

    @GetMapping("/get/all")
    public ResponseEntity<?> getAllVoucher (){
        return ResponseEntity.status(HttpStatus.OK).body(
                voucherService.test());

    }
    @GetMapping("/get/all/code")
    public ResponseEntity<?> getAllCodeVoucher (){
        return ResponseEntity.status(HttpStatus.OK).body(
                voucherService.getAllCode());

    }
    @GetMapping("/get/value")
    public ResponseEntity<?> getValueByCode(@RequestBody VoucherDTO voucherDTO)
    {
        float value = voucherService.getValueByCode(voucherDTO.getCode());
        return ResponseEntity.status(HttpStatus.OK).body(
                value);
    }
    @PostMapping("/insert")
    public ResponseEntity<VoucherDTO> insertVoucher(@RequestBody VoucherDTO voucherDTO) {
        VoucherDTO insertedVoucher = voucherService.insert(voucherDTO);
        return new ResponseEntity<>(insertedVoucher, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete-all")
    public ResponseEntity<String> deleteAllVouchers() {
        voucherService.deleteVoucher();
        return new ResponseEntity<>("All vouchers deleted successfully!", HttpStatus.OK);
    }

    @DeleteMapping("/delete-by-id/{code}")
    public ResponseEntity<String> deleteVoucherById(@PathVariable String code) {
//        VoucherDTO voucherDTO = new VoucherDTO();
//        voucherDTO.setId(id);

        try {
            voucherService.deleteVoucherByCode(code);
            return new ResponseEntity<>("Voucher deleted successfully!", HttpStatus.OK);
        } catch (ObjectNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

}
