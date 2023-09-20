package com.cuauh.springbootecommerce.service;

import com.cuauh.springbootecommerce.dto.Purchase;
import com.cuauh.springbootecommerce.dto.PurchaseResponse;

public interface CheckoutService {

    PurchaseResponse placeOrder(Purchase purchase);
}
