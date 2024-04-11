package com.food_app_api.Viviepi.controllers;

import com.food_app_api.Viviepi.dto.VoucherDTO;
import com.food_app_api.Viviepi.entities.Voucher;
import com.food_app_api.Viviepi.payload.response.ResponseObject;
import com.food_app_api.Viviepi.services.VoucherService;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/voucher/api")
public class VoucherController {

    @Autowired
    private VoucherService voucherService;
    @GetMapping("/get/voucher/by/code")
    public ResponseEntity<?> getVoucherByCode ( @RequestParam(name = "codeVoucher", required = false) String codeVoucher){
        return ResponseEntity.status(HttpStatus.OK).body(
                voucherService.isExpire(codeVoucher));

    }
    @GetMapping("/get/all")
    public ResponseEntity<ResponseObject> getAllVoucher (){
        return ResponseEntity.status(HttpStatus.OK).body(
                voucherService.getAll());

    }
    @GetMapping("/get/all/code")
    public ResponseEntity<ResponseObject> getAllCodeVoucher (){
        ResponseObject responseObject  = voucherService.getAll();
        return new ResponseEntity<>(responseObject, HttpStatus.OK);


    }
    @GetMapping("/get/value")
    public ResponseEntity<ResponseObject> getValueByCode(@RequestBody VoucherDTO voucherDTO)
    {
        float value = voucherService.getValueByCode(voucherDTO.getCode());
        ResponseObject responseObject = new ResponseObject(HttpStatus.OK.value(), "Value ", value);

        return new ResponseEntity<>(responseObject,HttpStatus.OK);
    }
    @PostMapping("/insert")
    public ResponseEntity<VoucherDTO> insertVoucher(@RequestParam(value = "file",required = false) MultipartFile file , @ModelAttribute VoucherDTO voucherDTO) throws IOException {
        VoucherDTO insertedVoucher = voucherService.insert(voucherDTO,file);
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
