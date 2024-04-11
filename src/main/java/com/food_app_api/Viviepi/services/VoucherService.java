package com.food_app_api.Viviepi.services;

import com.food_app_api.Viviepi.dto.CategoryDTO;
import com.food_app_api.Viviepi.dto.VoucherDTO;
import com.food_app_api.Viviepi.entities.Voucher;
import com.food_app_api.Viviepi.exceptions.ObjectEmptyException;
import com.food_app_api.Viviepi.mapper.VoucherMapper;
import com.food_app_api.Viviepi.payload.response.ResponseObject;
import com.food_app_api.Viviepi.repositories.IVoucherRepository;
import com.food_app_api.Viviepi.util.CloudinaryUtil;
import com.google.api.client.util.DateTime;
import jakarta.transaction.Transactional;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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
    @Autowired
    CloudinaryUtil cloudinaryUtil;

    @Override
    public ResponseObject getAll() {
        List<Voucher> voucherList = voucherRepository.findAll();
        if(voucherList.isEmpty())
        {
            throw new ObjectEmptyException(
                    404, "List voucher is empty !");
        }

        return new ResponseObject(200, "List Voucher", voucherMapper.toVoucherDTOList(voucherList));

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
    public int getValueByCode(String code) {
        System.out.println("Code: " + code);
        int value = voucherRepository.getValueByCode(code);

        boolean exist = voucherRepository.existsByCode(code);
        if(!exist)
        {
            System.out.println();
            throw new ObjectEmptyException(
                    404, "Voucher is empty !");
        }

        return value;
    }

    @Override
    public VoucherDTO insert(VoucherDTO voucherDTO, MultipartFile file) throws IOException {
        String fileName = "";
        if( file != null )
        {
            fileName = cloudinaryUtil.uploadImageToFolder(file, "voucher");

        }
        Voucher voucher = voucherMapper.toVoucher(voucherDTO);
        voucher.setBannerUrl(fileName);
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

        LocalDateTime endDate = getEndDateByCode(code);
        System.out.println("now : " + now + ", " + "end_date: " + endDate);
        // So sánh thời điểm hiện tại với thời điểm kết thúc voucher
        return endDate.isAfter(now);
    }

    @Override
    public Voucher findByCode(String code) {
        Optional<Voucher> optionalVoucher = voucherRepository.findByCode(code);
        return optionalVoucher.get();
    }

    public LocalDateTime getEndDateByCode(String code) {
        Optional<LocalDateTime> endDateOptional = voucherRepository.findEndDateByCode(code);
        return endDateOptional.orElse(null); // Hoặc xử lý theo ý bạn khi không tìm thấy
    }
}
