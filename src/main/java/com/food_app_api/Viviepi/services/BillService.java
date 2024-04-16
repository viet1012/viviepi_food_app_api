package com.food_app_api.Viviepi.services;

import com.food_app_api.Viviepi.dto.BillDTO;
import com.food_app_api.Viviepi.entities.Bill;
import com.food_app_api.Viviepi.entities.User;
import com.food_app_api.Viviepi.entities.Voucher;
import com.food_app_api.Viviepi.jwt.JwtUtil;
import com.food_app_api.Viviepi.jwt.ResponseToken;
import com.food_app_api.Viviepi.mapper.BillMapper;
import com.food_app_api.Viviepi.repositories.IBillRepository;
import com.food_app_api.Viviepi.repositories.IUserRepository;
import com.food_app_api.Viviepi.repositories.IVoucherRepository;
import com.food_app_api.Viviepi.services.IBillService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BillService implements IBillService {

    @Autowired
    private IBillRepository billRepository;

    @Autowired
    private IVoucherRepository voucherRepository;

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private BillMapper billMapper;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private Gson gson;

    @Autowired
    private VoucherService voucherService;

    @Override
    public BillDTO createBill(BillDTO billDTO, String codeVoucher, String token) {

        if (jwtUtil.validationToke(token)) {
            System.out.println("token: "  + token);
            final String data = jwtUtil.parserToken(token);
            ResponseToken responseToken = gson.fromJson(data, ResponseToken.class);
            Optional<User> optionalUser = userRepository.findByEmail(responseToken.getUsername());
            User user = optionalUser.get();
            billDTO.setUser(user);
        }else {
           throw new RuntimeException("Token is not valid!");
        }
        if(codeVoucher != null)
        {
            Voucher voucher = voucherService.findByCode(codeVoucher);

            if (voucher != null && voucherService.isExpire(codeVoucher) ) {
                billDTO.setVoucher(voucher);
                // Apply voucher logic here (e.g., update total price of bill)
                // ship = 20.000
//            double curTotalPrice = billDTO.getTotalPrice() - voucher.getValue() + 20000;
//            if(curTotalPrice < 0)
//            {
//                billDTO.setTotalPrice(0);
//
//            }else {
//                billDTO.setTotalPrice(curTotalPrice);
//            }
            }
            else {
                throw  new RuntimeException("Voucher is not valid!");
//            billDTO.setTotalPrice(billDTO.getTotalPrice());
            }
        }

        billDTO.setTotalPrice(billDTO.getTotalPrice());
        Bill bill = billMapper.toBill(billDTO);
        return billMapper.toBillDTO(billRepository.save(bill));
    }

    @Override
    public List<BillDTO> getAllBills() {
        List<Bill> bills = billRepository.findAll();
        return billMapper.toBillDTOList(bills);
    }

    @Override
    public Page<BillDTO> getAllBills(Pageable pageable) {
        Page<Bill> billPage = billRepository.findAll(pageable);
        return billPage.map(billMapper::toBillDTO); // Sử dụng map để chuyển đổi từ Page<Bill> sang Page<BillDTO>
    }

    @Override
    public BillDTO getBillDTOById(long id) {
        Optional<Bill> billOptional = billRepository.findById(id);
        return billOptional.map(billMapper::toBillDTO).orElse(null);
    }
    @Override
    public Bill getBillById(long id) {
        Optional<Bill> billOptional = billRepository.findById(id);
        Bill bill = new Bill();
        if(billOptional.isPresent())
        {
            bill  = billOptional.get();
        }
        else{
            bill = null;
        }
        return bill;
    }

    @Override
    public List<BillDTO> getBillByUserId(long userID) {
        List<Bill> bills = billRepository.findByUserId(userID);
        return billMapper.toBillDTOList(bills);

    }

    @Override
    public BillDTO updateBill(long id, BillDTO billDTO) {
        Optional<Bill> billOptional = billRepository.findById(id);
        if (billOptional.isPresent()) {
            Bill bill = billOptional.get();
            // Update bill fields
            bill.setName(billDTO.getName());
            // Update other fields as needed
            return billMapper.toBillDTO(billRepository.save(bill));
        }
        return null; // Or throw an exception indicating the bill was not found
    }

    @Override
    public void deleteBill(long id) {
        billRepository.deleteById(id);
    }

    @Override
    public void deleteAll() {
        billRepository.deleteAll();
    }


}
