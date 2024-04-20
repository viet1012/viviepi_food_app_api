package com.food_app_api.Viviepi.paypal;

import com.paypal.api.payments.*;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
public class PaypalService {

	@Autowired
	private APIContext apiContext;

	public Payment createPayment(
			Double totalPriceVND,
			String currency,
			String method,
			String intent,
			String description,
			String cancelUrl,
			String successUrl) throws PayPalRESTException {

		// Tỷ giá hối đoái từ VND sang USD
		double exchangeRate = 23000.0; // Tỷ giá 1 USD = 23000 VND

		// Chuyển đổi tổng số tiền từ VND sang USD
		Double totalPriceUSD = totalPriceVND / exchangeRate;

		Amount amount = new Amount();
		amount.setCurrency(currency);
		// Làm tròn tổng số tiền USD tới 2 chữ số thập phân
		amount.setTotal(String.format(Locale.US, "%.2f", totalPriceUSD));

		Transaction transaction = new Transaction();
		transaction.setDescription(description);
		transaction.setAmount(amount);

		List<Transaction> transactions = new ArrayList<>();
		transactions.add(transaction);

		Payer payer = new Payer();
		payer.setPaymentMethod(method.toString());

		Payment payment = new Payment();
		payment.setIntent(intent);
		payment.setPayer(payer);
		payment.setTransactions(transactions);

		RedirectUrls redirectUrls = new RedirectUrls();
		redirectUrls.setCancelUrl(cancelUrl);
		redirectUrls.setReturnUrl(successUrl);
		payment.setRedirectUrls(redirectUrls);

		return payment.create(apiContext);
	}

	public Payment executePayment(String paymentId, String payerId) throws PayPalRESTException {
		Payment payment = new Payment();
		payment.setId(paymentId);
		PaymentExecution paymentExecute = new PaymentExecution();
		paymentExecute.setPayerId(payerId);
		return payment.execute(apiContext, paymentExecute);
	}
}

