package com.dimitar.axon.tutorial.axon.service;

import com.dimitar.axon.tutorial.axon.events.OrderPlacedEvent;
import com.dimitar.axon.tutorial.axon.model.OrderedProduct;
import com.dimitar.axon.tutorial.axon.query.FindAllOrderedProductsQuery;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OrderedProductsEventHandler {

    private final Map<String, OrderedProduct> orderedProducts = new HashMap<>();

    @EventHandler
    public void on(OrderPlacedEvent event) {
        String orderId = event.getOrderId();
        orderedProducts.put(orderId, new OrderedProduct(orderId, event.getProduct()));
    }

    @QueryHandler
    public List<OrderedProduct> handle(FindAllOrderedProductsQuery query) {
        return new ArrayList<>(orderedProducts.values());
    }
}