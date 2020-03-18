package com.dimitar.axon.tutorial.axon.web.controller;

import com.dimitar.axon.tutorial.axon.commands.ConfirmOrderCommand;
import com.dimitar.axon.tutorial.axon.commands.PlaceOrderCommand;
import com.dimitar.axon.tutorial.axon.commands.ShipOrderCommand;
import com.dimitar.axon.tutorial.axon.model.OrderedProduct;
import com.dimitar.axon.tutorial.axon.query.FindAllOrderedProductsQuery;
import lombok.RequiredArgsConstructor;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class OrderRestEndpoint {
    private final CommandGateway commandGateway;
    private final QueryGateway queryGateway;

    @PostMapping("/ship-order")
    public void shipOrder() {
        String orderId = UUID.randomUUID().toString();
        commandGateway.send(new PlaceOrderCommand(orderId, "Deluxe Chair"));
        commandGateway.send(new ConfirmOrderCommand(orderId));
        commandGateway.send(new ShipOrderCommand(orderId));
    }

    @GetMapping("/all-orders")
    public List<OrderedProduct> findAllOrderedProducts() {
        return queryGateway.query(new FindAllOrderedProductsQuery(),
                ResponseTypes.multipleInstancesOf(OrderedProduct.class)).join();
    }


}
