package com.dimitar.axon.tutorial.axon.commands;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@RequiredArgsConstructor
@Getter
@Setter
@ToString
public class PlaceOrderCommand {
    @TargetAggregateIdentifier
    private final String orderId;
    private final String product;

}
