package com.cuauh.springbootecommerce.service;

import com.cuauh.springbootecommerce.dao.CustomerRepository;
import com.cuauh.springbootecommerce.dto.Purchase;
import com.cuauh.springbootecommerce.dto.PurchaseResponse;
import com.cuauh.springbootecommerce.entity.Customer;
import com.cuauh.springbootecommerce.entity.Order;
import com.cuauh.springbootecommerce.entity.OrderItem;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.UUID;

@Service
public class CheckoutServiceImpl implements CheckoutService{

    private CustomerRepository customerRepository;

    public CheckoutServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }
    @Override
    @Transactional
    public PurchaseResponse placeOrder(Purchase purchase) {
        //retrieve order info from DTO
        Order order = purchase.getOrder();

        //generate tracking number
        String orderTrackingNumber = generateOrderTrackingNumber();
        order.setOrderTrackingNumber(orderTrackingNumber);

        //populate the order with orderItems
        Set<OrderItem> orderItems = purchase.getOrderItems();
        orderItems.forEach(item -> order.add(item));

        //populate order with billing address and shiping address
        order.setBillingAddress(purchase.getBillingAddress());
        order.setShippingAddress(purchase.getShppingAddress());

        //populate the customer with order
        Customer customer = purchase.getCustomer();
        customer.add(order);

        //save to database
        customerRepository.save(customer);

        //return a response
        return new PurchaseResponse(orderTrackingNumber);
    }

    private String generateOrderTrackingNumber() {
        //generate a random UUID number
        return UUID.randomUUID().toString();
    }
}
