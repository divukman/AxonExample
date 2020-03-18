package com.dimitar.axon.tutorial.axon.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class OrderedProduct {

    private final String orderId;
    private final String product;
    private OrderStatus orderStatus;

    public OrderedProduct(String orderId, String product) {
        this.orderId = orderId;
        this.product = product;
        orderStatus = OrderStatus.PLACED;
    }

    public void setOrderConfirmed() {
        this.orderStatus = OrderStatus.CONFIRMED;
    }

    public void setOrderShipped() {
        this.orderStatus = OrderStatus.SHIPPED;
    }
}
