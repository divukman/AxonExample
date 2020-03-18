package com.dimitar.axon.tutorial.axon.model;

import com.dimitar.axon.tutorial.axon.commands.ConfirmOrderCommand;
import com.dimitar.axon.tutorial.axon.commands.PlaceOrderCommand;
import com.dimitar.axon.tutorial.axon.commands.ShipOrderCommand;
import com.dimitar.axon.tutorial.axon.events.OrderConfirmedEvent;
import com.dimitar.axon.tutorial.axon.events.OrderPlacedEvent;
import com.dimitar.axon.tutorial.axon.events.OrderShippedEvent;
import com.dimitar.axon.tutorial.axon.exception.UnconfirmedOrderException;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

@Aggregate
public class OrderAggregate {

    @AggregateIdentifier
    private String orderId;
    private boolean orderConfirmed;

    @CommandHandler
    public OrderAggregate(PlaceOrderCommand command) {
        apply(new OrderPlacedEvent(command.getOrderId(), command.getProduct()));
    }

    @EventSourcingHandler
    public void on(OrderPlacedEvent event) {
        this.orderId = event.getOrderId();
        orderConfirmed = false;
    }

    protected OrderAggregate() { }

    @CommandHandler
    public void handle(ConfirmOrderCommand command) {
        apply(new OrderConfirmedEvent(orderId));
    }

    @CommandHandler
    public void handle(ShipOrderCommand command) {
        if (!orderConfirmed) {
            throw new UnconfirmedOrderException();
        }
        apply(new OrderShippedEvent(orderId));
    }

    @EventSourcingHandler
    public void on(OrderConfirmedEvent event) {
        orderConfirmed = true;
    }
}