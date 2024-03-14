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
            final String data = jwtUtil.parserToken(token);
            ResponseToken responseToken = gson.fromJson(data, ResponseToken.class);
            Optional<User> optionalUser = userRepository.findByEmail(responseToken.getUsername());
            User user = optionalUser.get();
            billDTO.setUser(user);
        }else {
            System.out.println("Token is not valid!");
        }
        Optional<Voucher> optionalVoucher = voucherRepository.findByCode(codeVoucher);

        if (optionalVoucher.isPresent() && voucherService.isExpire(codeVoucher) ) {
            Voucher voucher = optionalVoucher.get();
            billDTO.setVoucher(voucher);
            // Apply voucher logic here (e.g., update total price of bill)
            // ship = 20.000
            double curTotalPrice = billDTO.getTotalPrice() - voucher.getValue() + 20000;
            if(curTotalPrice < 0)
            {
                billDTO.setTotalPrice(0);

            }else {
                billDTO.setTotalPrice(curTotalPrice);
            }
        }
        else {
            billDTO.setTotalPrice(billDTO.getTotalPrice());
        }

        Bill bill = billMapper.toBill(billDTO);
        return billMapper.toBillDTO(billRepository.save(bill));
    }

    @Override
    public List<BillDTO> getAllBills() {
        List<Bill> bills = billRepository.findAll();
        return billMapper.toBillDTOList(bills);
    }

    @Override
    public BillDTO getBillById(long id) {
        Optional<Bill> billOptional = billRepository.findById(id);
        return billOptional.map(billMapper::toBillDTO).orElse(null);
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
}
