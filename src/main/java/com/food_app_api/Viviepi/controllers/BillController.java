package com.food_app_api.Viviepi.controllers;

import com.food_app_api.Viviepi.dto.BillDTO;
import com.food_app_api.Viviepi.payload.response.ResponseObject;
import com.food_app_api.Viviepi.services.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bill/api")
public class BillController {

    @Autowired
    private BillService billService;
    @GetMapping("/get/all")
    @Transactional(readOnly = true)
    public ResponseEntity<ResponseObject>getAllBill(){
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(200,
                        "get all bills completed !",
                        billService.getAllBills()
                )
        );
    }

    @GetMapping("/bills")
    public Page<BillDTO> getAllBills(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return billService.getAllBills(PageRequest.of(page, size));
    }

    @GetMapping("/get/all/by/user-id/{userId}")
    @Transactional(readOnly = true)
    public ResponseEntity<ResponseObject>getAllBillByUserId(@PathVariable long userId){
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(200,
                        "get all bills completed !",
                        billService.getBillByUserId(userId)
                )
        );
    }
    @PostMapping("/insert")
    public ResponseEntity<ResponseObject> createBill(@RequestBody BillDTO billDTO,
                                              @RequestParam(name = "codeVoucher", required = false) String codeVoucher,
                                              @RequestParam(name = "Authorization") String token) {
        try {
            BillDTO createdBill = billService.createBill(billDTO, codeVoucher, token);
            return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseObject(
                    201,"insert bill completed", createdBill
            ));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
