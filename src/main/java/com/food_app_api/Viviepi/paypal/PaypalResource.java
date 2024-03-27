package com.food_app_api.Viviepi.paypal;

import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

@RestController
@RequestMapping("/api/Paypal")
public class PaypalResource {

    @Autowired
    private PaypalService paypalService;

    @GetMapping("/cancel")
    public ResponseEntity<String> cancelPay() {
        return ResponseEntity.ok("Payment canceled.");
    }

    @PostMapping("/create-payment")
    public ResponseEntity<String> createPayment() {
        try {
            String cancelUrl = "https://viviepi-food-app-api.onrender.com/api/Paypal/cancel";
            String successUrl = "https://viviepi-food-app-api.onrender.com/api/Paypal/success";
            Payment payment = paypalService.createPayment(
                    5.0,
                    "USD",
                    "paypal",
                    "sale",
                    "ThanhToan",
                    cancelUrl,
                    successUrl
            );
            for (Links links : payment.getLinks()) {
                if ("approval_url".equals(links.getRel())) {
                    return ResponseEntity.ok(links.getHref());
                }
            }
            return ResponseEntity.status(500).body("Approval URL not found");
        } catch (PayPalRESTException e) {
            // Handle PayPal REST Exception
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error occurred while creating payment: " + e.getMessage());
        }
    }

    @GetMapping("/success")
    public ModelAndView paymentSuccess(@RequestParam("paymentId") String paymentId, @RequestParam("PayerID") String payerId) {
        try {
            Payment payment = paypalService.executePayment(paymentId, payerId);
            if ("approved".equals(payment.getState())) {
                String redirectUrl = "myappscheme://success";
                return new ModelAndView(new RedirectView(redirectUrl));
            }
        } catch (PayPalRESTException e) {
            throw new RuntimeException(e);
        }
        // Trả về một ModelAndView hoặc chuỗi khác nếu cần
        return new ModelAndView("payment-failed"); // Ví dụ trả về một trang "payment-failed"
    }
}
