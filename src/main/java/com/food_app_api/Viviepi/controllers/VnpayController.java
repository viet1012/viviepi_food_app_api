package com.food_app_api.Viviepi.controllers;

import com.food_app_api.Viviepi.config.VnpayConfig;
import com.food_app_api.Viviepi.dto.BillDTO;
import com.food_app_api.Viviepi.entities.Bill;
import com.food_app_api.Viviepi.repositories.IBillRepository;
import com.food_app_api.Viviepi.services.BillService;
import com.paypal.base.rest.PayPalRESTException;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@RestController
@RequestMapping("/paymentx/VnpayController")
public class VnpayController {
    @Autowired
    private IBillRepository billRepository;
    @Autowired
    private BillService billService;


    @GetMapping("payment-callback")
    public ModelAndView paymentCallback(@RequestParam Map<String, String> queryParams) throws ChangeSetPersister.NotFoundException {
        String vnp_ResponseCode = queryParams.get("vnp_ResponseCode");
        String billId = queryParams.get("billId");

        if (billId != null && !billId.isEmpty()) {
            if ("00".equals(vnp_ResponseCode)) {
                // Giao dịch thành công
                // Thực hiện các xử lý cần thiết, ví dụ: cập nhật CSDL
                Optional<Bill> billOptional = billRepository.findById(Long.parseLong(billId));
                if (billOptional.isPresent()) {
                    Bill bill = billOptional.get();

                    // Cập nhật trạng thái mới cho hóa đơn
                    // bill.setStatus("Đã thanh toán");
                    // Thực hiện tính toán lại tổng giá trị hóa đơn sau khi trừ tiền
                    String total = queryParams.get("vnp_Amount");
                    long totallong = Long.parseLong(total) / 100;
                    bill.setTotalPrice(bill.getTotalPrice() - (float) totallong);

                    billRepository.save(bill);
                } else {
                    String redirectUrl = "myappscheme://fail";
                    return new ModelAndView(new RedirectView(redirectUrl));
                }
                // Trả về thông báo thành công và billId
                String redirectUrl = "myappscheme://success";
                return new ModelAndView(new RedirectView(redirectUrl));
            } else {
                // Trả về thông báo thất bại
                String redirectUrl = "myappscheme://fail";
                return new ModelAndView(new RedirectView(redirectUrl));
            }
        } else {
            // Trả về lỗi nếu không tìm thấy billId
            String redirectUrl = "myappscheme://invalid";
            return new ModelAndView(new RedirectView(redirectUrl));
        }
    }

@PostMapping("pay")
public ResponseEntity<String> getPay(@RequestBody BillDTO billDTO,
                                     @RequestParam(name = "codeVoucher", required = false) String codeVoucher,
                                     @RequestParam(name = "Authorization") String token) throws UnsupportedEncodingException {
    try {
        String vnp_Version = "2.1.0";
        String vnp_Command = "pay";
        String orderType = "other";
        long amount = (long) (billDTO.getTotalPrice() * 100);
        String bankCode = "NCB";

        String vnp_TxnRef = VnpayConfig.getRandomNumber(8);
        String vnp_IpAddr = "127.0.0.1";

        String vnp_TmnCode = VnpayConfig.vnp_TmnCode;

        BillDTO savedBill = billService.createBill(billDTO,codeVoucher,token);
        Map<String, String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", vnp_Version);
        vnp_Params.put("vnp_Command", vnp_Command);
        vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
        vnp_Params.put("vnp_Amount", String.valueOf(amount));
        vnp_Params.put("vnp_CurrCode", "VND");

        vnp_Params.put("vnp_BankCode", bankCode);
        vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
        vnp_Params.put("vnp_OrderInfo", "Thanh toan don hang:" + vnp_TxnRef);
        vnp_Params.put("vnp_OrderType", orderType);

        vnp_Params.put("vnp_Locale", "vn");
        vnp_Params.put("vnp_ReturnUrl", VnpayConfig.vnp_ReturnUrl + "?billId=" + String.valueOf(savedBill.getId()));
        vnp_Params.put("vnp_IpAddr", vnp_IpAddr);

        ZoneId zoneId = ZoneId.of("Asia/Ho_Chi_Minh");


// Tạo đối tượng LocalDateTime từ thời gian hiện tại và đổi múi giờ
        LocalDateTime now = LocalDateTime.now();
        ZonedDateTime zonedDateTime = now.atZone(ZoneId.systemDefault()).withZoneSameInstant(zoneId);

// Thêm 15 phút
        ZonedDateTime expireDateTime = zonedDateTime.plusMinutes(15);
        ZonedDateTime seAsiaTime = zonedDateTime.plusHours(1);

// Định dạng thời gian theo định dạng yyyMMddHHmmss
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        String vnp_CreateDate = now.format(formatter);
        String vnp_ExpireDate = expireDateTime.format(formatter);


// Đặt các giá trị vào vnp_Params
        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);
        vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);

        List fieldNames = new ArrayList(vnp_Params.keySet());
        Collections.sort(fieldNames);
        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();
        Iterator itr = fieldNames.iterator();
        while (itr.hasNext()) {
            String fieldName = (String) itr.next();
            String fieldValue = (String) vnp_Params.get(fieldName);
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                //Build hash data
                hashData.append(fieldName);
                hashData.append('=');
                hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                //Build query
                query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()));
                query.append('=');
                query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                if (itr.hasNext()) {
                    query.append('&');
                    hashData.append('&');
                }
            }
        }
        String queryUrl = query.toString();
        String vnp_SecureHash = VnpayConfig.hmacSHA512(VnpayConfig.secretKey, hashData.toString());
        queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
        String paymentUrl = VnpayConfig.vnp_PayUrl + "?" + queryUrl;
        return ResponseEntity.ok(paymentUrl);
    } catch (UnsupportedEncodingException e) {
        // Handle UnsupportedEncodingException if necessary
        e.printStackTrace();
        return ResponseEntity.status(500).body("Error occurred while encoding: " + e.getMessage());
    }
    }


}