package com.food_app_api.Viviepi.controllers;

import com.food_app_api.Viviepi.dto.BillDTO;
import com.food_app_api.Viviepi.dto.BillDetailDTO;
import com.food_app_api.Viviepi.payload.response.ResponseObject;
import com.food_app_api.Viviepi.services.BillDetailService;
import com.food_app_api.Viviepi.services.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bill-detail/api")
public class BillDetailController {

    @Autowired
    private BillDetailService billDetailService;
    @Autowired
    private BillService billService;
    @GetMapping("/get/all")
    @Transactional(readOnly = true)
    public ResponseEntity<ResponseObject> getAllUser(){
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(200,
                        "get all bill details completed !",
                        billDetailService.getAllBillDetail()
                )
        );
    }
    @PostMapping("/insert")
    public  ResponseEntity<ResponseObject>  createBillDetail(@RequestBody BillDetailDTO newBillDetail,
                                          @RequestParam Long billId,
                                          @RequestParam Long foodId ) {
        try {
            BillDetailDTO createdBillDetail =  billDetailService.createBillDetail(newBillDetail, billId, foodId );
            return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseObject(
                    201,"insert bill detail completed", createdBillDetail
            ));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
