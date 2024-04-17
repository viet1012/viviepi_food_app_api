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
import com.food_app_api.Viviepi.repositories.IFoodRepository;
import org.apache.catalina.mapper.Mapper;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    @Autowired
    private IFoodRepository foodRepository;

    @Override
    public List<BillDetailDTO> getBillDetailsByBillId(Long billId) {
        List<BillDetail> billDetails = billDetailRepository.findByBillId(billId);
        return billDetailMapper.toBillDetailDTOList(billDetails);
    }

    @Override
    public List<BillDetailDTO> getAllBillDetail() {
        List<BillDetail> billDetails = billDetailRepository.findAll();
        return billDetailMapper.toBillDetailDTOList(billDetails);
    }

    @Override
    public List<BillDetailDTO> createBillDetail(BillDetailDTO newBillDetail, Long billId) {
        Bill bill = billService.getBillById(billId);
        if (bill == null) {
            throw new IllegalArgumentException("Không tìm thấy hóa đơn phù hợp.");
        }

        double totalPriceIncrease = 0.0;
        List<BillDetail> billDetails = new ArrayList<>();

        for (FoodDTO foodItem : newBillDetail.getFoodDTOS()) {
            FoodDTO foodDTO = foodService.getFoodById(foodItem.getId());
            if (foodDTO == null) {
                throw new IllegalArgumentException("Không tìm thấy sản phẩm có id = " + foodItem.getId());
            }
            double unitPrice = foodDTO.getPrice();
//            int quantity = newBillDetail.getQuantity();
//            totalPriceIncrease += unitPrice * quantity;
            Food food = foodMapper.toFood(foodDTO);
            if(food.getQuantity() - foodItem.getQuantity() >= 0)
            {
                food.setQuantity(food.getQuantity() - foodItem.getQuantity());
                foodRepository.save(food);

            }else
            {
                throw new IllegalArgumentException("Không đủ số lượng sản phẩm có id = " + foodItem.getId());
            }

            BillDetail billDetail = new BillDetail();
            billDetail.setBill(bill);
            billDetail.setFood(food);
            billDetail.setPrice(unitPrice);
            billDetail.setQuantity(foodItem.getQuantity());
            billDetails.add(billDetail);
        }

//        // Cập nhật tổng giá trị của hóa đơn
//        double newTotalPrice = bill.getTotalPrice() + totalPriceIncrease;
        //bill.setTotalPrice(newBillDetail.getTotal_price());
        billService.updateBill(billId, billMapper.toBillDTO(bill));

        // Lưu danh sách chi tiết hóa đơn vào cơ sở dữ liệu
        List<BillDetail> savedBillDetails = billDetailRepository.saveAll(billDetails);

        // Chuyển đổi danh sách các chi tiết hóa đơn đã lưu thành DTO và trả về
        return billDetailMapper.toBillDetailDTOList(savedBillDetails);
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

    @Override
    public void deleteAll() {
        billDetailRepository.deleteAll();
    }
}
