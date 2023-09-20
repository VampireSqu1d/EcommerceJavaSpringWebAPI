package com.cuauh.springbootecommerce.dto;

import com.cuauh.springbootecommerce.entity.Address;
import com.cuauh.springbootecommerce.entity.Customer;
import com.cuauh.springbootecommerce.entity.Order;
import com.cuauh.springbootecommerce.entity.OrderItem;
import lombok.Data;

import java.util.Set;

@Data
public class Purchase {

    private Customer customer;
    private Address shppingAddress;
    private Address billingAddress;
    private Order order;
    private Set<OrderItem> orderItems;

}
