package com.food_app_api.Viviepi.services;

import com.food_app_api.Viviepi.dto.BillDTO;
import com.food_app_api.Viviepi.dto.BillDetailDTO;
import com.food_app_api.Viviepi.dto.FoodDTO;
import com.food_app_api.Viviepi.entities.Bill;
import com.food_app_api.Viviepi.entities.BillDetail;
import com.food_app_api.Viviepi.entities.Food;
import com.food_app_api.Viviepi.mapper.BillDetailMapper;
import com.food_app_api.Viviepi.mapper.BillMapper;
import com.food_app_api.Viviepi.mapper.FoodMapper;
import com.food_app_api.Viviepi.repositories.IBillDetailRepository;
import org.apache.catalina.mapper.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BillDetailService implements  IBillDetailService{

    @Autowired
    private BillService billService;
    @Autowired
    private IBillDetailRepository billDetailRepository;
    @Autowired
    private FoodService foodService;
    @Autowired
    private BillDetailMapper billDetailMapper;
    @Autowired
    private BillMapper billMapper;
    @Autowired
    private FoodMapper foodMapper;
    @Override
    public List<BillDetailDTO> getAllBillDetail() {
        List<BillDetail> billDetails = billDetailRepository.findAll();
        return billDetailMapper.toBillDetailDTOList(billDetails);
    }

    @Override
    public BillDetailDTO createBillDetail(BillDetailDTO newBillDetail, Long billId, Long foodId) {
        FoodDTO foodDTO = foodService.getFoodById( foodId);
        Bill bill = billService.getBillById(billId);
        BillDetail billDetail = new BillDetail();
        if(foodDTO == null){
            throw new IllegalArgumentException("Không tìm thấy sản phẩm!");
        }
        if (bill == null) {
            // Nếu không có sản phẩm hoặc bill ID, hoặc chúng không hợp lệ, bạn có thể xử lý lỗi ở đây
            throw new IllegalArgumentException("Không tìm thấy sản phẩm hoặc id của hóa đơn phù hợp.");
        }else
        {
            double unitPrice = foodDTO.getPrice();
            int quantity = newBillDetail.getQuantity();
            double newTotalPrice = bill.getTotalPrice() + (unitPrice * quantity);
            bill.setTotalPrice(newTotalPrice);
            billService.updateBill(billId,billMapper.toBillDTO(bill));

            Food food = foodMapper.toFood(foodDTO);
            billDetail.setBill(bill);
            billDetail.setFood(food);

            billDetail.setPrice(unitPrice);
            billDetail.setQuantity( newBillDetail.getQuantity());

            BillDetail billDetail1 = billDetailRepository.save(billDetail);

            return  billDetailMapper.toDTO(billDetail1);

        }
    }

    @Override
    public BillDetail updateBillDetail(Long billDetailId, BillDetail updatedBillDetail) {
        Optional<BillDetail> existingBillDetailOptional = billDetailRepository.findById(billDetailId);

        if (existingBillDetailOptional.isPresent()) {
            BillDetail existingBillDetail = existingBillDetailOptional.get();
            existingBillDetail.setQuantity(updatedBillDetail.getQuantity());

            return billDetailRepository.save(existingBillDetail);
        } else {
            throw new IllegalArgumentException("Không tìm thấy chi tiết hóa đơn với ID: " + billDetailId);
        }
    }
}
