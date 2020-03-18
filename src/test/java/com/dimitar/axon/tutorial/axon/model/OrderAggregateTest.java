package com.dimitar.axon.tutorial.axon.model;

import com.dimitar.axon.tutorial.axon.commands.PlaceOrderCommand;
import com.dimitar.axon.tutorial.axon.commands.ShipOrderCommand;
import com.dimitar.axon.tutorial.axon.events.OrderConfirmedEvent;
import com.dimitar.axon.tutorial.axon.events.OrderPlacedEvent;
import com.dimitar.axon.tutorial.axon.events.OrderShippedEvent;
import com.dimitar.axon.tutorial.axon.exception.UnconfirmedOrderException;
import org.axonframework.test.aggregate.AggregateTestFixture;
import org.axonframework.test.aggregate.FixtureConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;


class OrderAggregateTest {
    private FixtureConfiguration<OrderAggregate> fixture;

    @BeforeEach
    public void setUp() {
        fixture = new AggregateTestFixture<>(OrderAggregate.class);
    }

    @Test
    public void giveNoPriorActivity_whenPlaceOrderCommand_thenShouldPublishOrderPlacedEvent() {
        final String orderId = UUID.randomUUID().toString();
        final String product = "Deluxe Chair";
        fixture.givenNoPriorActivity()
                .when(new PlaceOrderCommand(orderId, product))
                .expectEvents(new OrderPlacedEvent(orderId, product));
    }

    @Test
    public void givenOrderPlacedEvent_whenShipOrderCommand_thenShouldThrowUnconfirmedOrderException() {
        final String orderId = UUID.randomUUID().toString();
        final String product = "Deluxe Chair";
        fixture.given(new OrderPlacedEvent(orderId, product))
                .when(new ShipOrderCommand(orderId))
                .expectException(UnconfirmedOrderException.class);
    }

    @Test
    public void givenOrderPlacedEventAndOrderConfirmedEvent_whenShipOrderCommand_thenShouldPublishOrderShippedEvent() {
        String orderId = UUID.randomUUID().toString();
        String product = "Deluxe Chair";
        fixture.given(new OrderPlacedEvent(orderId, product), new OrderConfirmedEvent(orderId))
                .when(new ShipOrderCommand(orderId))
                .expectEvents(new OrderShippedEvent(orderId));
    }

}