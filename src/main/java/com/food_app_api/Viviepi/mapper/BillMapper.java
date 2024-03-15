package com.food_app_api.Viviepi.mapper;

import com.food_app_api.Viviepi.dto.BillDTO;
import com.food_app_api.Viviepi.entities.Bill;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class BillMapper {

    public BillDTO toBillDTO(Bill bill) {
        BillDTO billDTO = new BillDTO();
        billDTO.setId(bill.getId());
        billDTO.setUsername(bill.getUser().getEmail());
        billDTO.setDate(bill.getDate());
        billDTO.setName(bill.getName());
        billDTO.setPhone(bill.getPhone());
        billDTO.setDeliveryAddress(bill.getDeliveryAddress());
        billDTO.setDescription(bill.getDescription());
        billDTO.setStatus(bill.getStatus());
        billDTO.setNameVoucher(bill.getVoucher()!= null ? bill.getVoucher().getCode() : null);
        billDTO.setTotalPrice(bill.getTotalPrice());
        return billDTO;
    }

    public List<BillDTO> toBillDTOList(List<Bill> billList) {
        return billList.stream().map(this::toBillDTO).collect(Collectors.toList());
    }

    public Bill toBill(BillDTO billDTO) {
        Bill bill = new Bill();
        bill.setId(billDTO.getId());
        bill.setUser(billDTO.getUser());
        bill.setDate(billDTO.getDate());
        bill.setName(billDTO.getName());
        bill.setPhone(billDTO.getPhone());
        bill.setDeliveryAddress(billDTO.getDeliveryAddress());
        bill.setDescription(billDTO.getDescription());
        bill.setStatus(billDTO.getStatus());
        bill.setVoucher(billDTO.getVoucher()!= null ? billDTO.getVoucher() : null);
        bill.setTotalPrice(billDTO.getTotalPrice());
        return bill;
    }

    public List<Bill> toBillList(List<BillDTO> billDTOList) {
        return billDTOList.stream().map(this::toBill).collect(Collectors.toList());
    }
}
