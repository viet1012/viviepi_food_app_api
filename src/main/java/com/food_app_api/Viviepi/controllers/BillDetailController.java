package com.food_app_api.Viviepi.controllers;

import com.food_app_api.Viviepi.dto.BillDTO;
import com.food_app_api.Viviepi.dto.BillDetailDTO;
import com.food_app_api.Viviepi.entities.BillDetail;
import com.food_app_api.Viviepi.payload.response.ResponseObject;
import com.food_app_api.Viviepi.services.BillDetailService;
import com.food_app_api.Viviepi.services.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bill-detail/api")
public class BillDetailController {

    @Autowired
    private BillDetailService billDetailService;
    @Autowired
    private BillService billService;
    @GetMapping("/get/all")
    @Transactional(readOnly = true)
    public ResponseEntity<ResponseObject> getAllBillDetail(){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(200,
                            "get all bill details completed !",
                            billDetailService.getAllBillDetail()
                    )
            );
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new ResponseObject(500,
                            e.getMessage(), ""

                    )
            );
        }

    }
    @GetMapping("/count/details/{billId}")
    public ResponseEntity<ResponseObject> countBillDetailsByBillId(@PathVariable Long billId) {
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(200,
                        "Total bill details  by bill id: " + billId +" completed !",
                        billDetailService.countTotalBillDetailsByBillId(billId)
                )
        );
    }
    @GetMapping("/get/details/{billId}")
    public ResponseEntity<ResponseObject> getBillDetailsByBillId(@PathVariable Long billId) {
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(200,
                        "get all bill details  by bill id: " + billId +" completed !",
                        billDetailService.getBillDetailsByBillId(billId)
                )
        );
    }

    @PostMapping("/insert")
    public  ResponseEntity<ResponseObject>  createBillDetail(@RequestBody BillDetailDTO newBillDetail,
                                          @RequestParam Long billId) {
        try {
            List<BillDetailDTO> createdBillDetail =  billDetailService.createBillDetail(newBillDetail, billId);
            return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseObject(
                    201,"insert bill detail completed", createdBillDetail
            ));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteAll(@PathVariable Long id) {
        billService.deleteBill(id);
        return new ResponseEntity<>("bill detail: "+ id +" deleted successfully!", HttpStatus.OK);
    }
    @DeleteMapping("/delete-all")
    public ResponseEntity<String> deleteAll() {
        billDetailService.deleteAll();
        return new ResponseEntity<>("All bill-detail deleted successfully!", HttpStatus.OK);
    }
}
