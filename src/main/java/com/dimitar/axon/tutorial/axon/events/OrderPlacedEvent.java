package com.dimitar.axon.tutorial.axon.events;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@RequiredArgsConstructor
@Getter
@Setter
@ToString
public class OrderPlacedEvent {
    private final String orderId;
    private final String product;
}
