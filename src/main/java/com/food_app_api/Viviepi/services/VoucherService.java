package com.food_app_api.Viviepi.services;

import com.food_app_api.Viviepi.dto.CategoryDTO;
import com.food_app_api.Viviepi.dto.VoucherDTO;
import com.food_app_api.Viviepi.entities.Voucher;
import com.food_app_api.Viviepi.exceptions.ObjectEmptyException;
import com.food_app_api.Viviepi.mapper.VoucherMapper;
import com.food_app_api.Viviepi.payload.response.ResponseObject;
import com.food_app_api.Viviepi.repositories.IVoucherRepository;
import com.google.api.client.util.DateTime;
import jakarta.transaction.Transactional;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class VoucherService implements IVoucherService{

    @Autowired
    private IVoucherRepository voucherRepository;
    @Autowired
    VoucherMapper voucherMapper;
    @Override
    public ResponseObject getAll() {
        List<VoucherDTO> voucherDTOS = new ArrayList<>();
        List<Voucher> voucherList = voucherRepository.findAll();
        List<String> codes = voucherRepository.getAllCode();
        if(voucherList.isEmpty())
        {

            throw new ObjectEmptyException(
                    404, "List voucher is empty !");
        }
        for (Voucher voucher : voucherList){
            voucherDTOS.add(voucherMapper.toVoucherDTO(voucher));
        }
        return new ResponseObject(200, "List Voucher", voucherList);

    }


    @Override
    public List<String> getAllCode() {
        List<String> voucherList = voucherRepository.getAllCode();
        if(voucherList.isEmpty())
        {
            throw new ObjectEmptyException(
                    404, "List voucher is empty !");
        }
        return voucherList;
    }

    @Override
    public float getValueByCode(String code) {
        System.out.println("Code: " + code);
        Float value = voucherRepository.getValueByCode(code);

        boolean exist = voucherRepository.existsByCode(code);
        if(!exist)
        {
            System.out.println();
            throw new ObjectEmptyException(
                    404, "Voucher is empty !");
        }
        if (value == null) {
            System.out.println("NULL!!!!");
        }
        return value;
    }

    @Override
    public VoucherDTO insert(VoucherDTO voucherDTO) {
        Voucher voucher = voucherMapper.toVoucher(voucherDTO);
        return voucherMapper.toVoucherDTO(voucherRepository.save(voucher));
    }

    @Override
    public void deleteVoucher() {
        voucherRepository.deleteAll();
        System.out.println("Delete voucher is completed !");
    }

    @Override
    public void deleteVoucherByCode(String code) {
        boolean exist = voucherRepository.existsByCode(code);
        if(!exist)
        {
            System.out.println("Cannot delete code: "+ code);
            throw new ObjectNotFoundException(
                    404, "Cannot delete id: "+ code
            );
        }
        voucherRepository.deleteByCode(code);
        System.out.println("Delete voucher is completed !");
    }


    @Override
    public Boolean existDiscount(VoucherDTO discountTypeDTO) {
        return voucherRepository.existsByCode(discountTypeDTO.getCode());
    }

    @Override
    public Boolean isExpire(String code) {
        LocalDateTime now = LocalDateTime.now();
        // Chuyển đổi thời điểm kết thúc voucher từ DateTime sang LocalDateTime
        LocalDateTime endDate = getEndDateByCode(code);

        // So sánh thời điểm hiện tại với thời điểm kết thúc voucher
        return now.isAfter(endDate);
    }
    public LocalDateTime getEndDateByCode(String code) {
        Optional<LocalDateTime> endDateOptional = voucherRepository.findEndDateByCode(code);
        return endDateOptional.orElse(null); // Hoặc xử lý theo ý bạn khi không tìm thấy
    }
}
