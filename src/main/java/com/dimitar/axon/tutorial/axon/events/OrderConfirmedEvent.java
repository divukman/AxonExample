package com.dimitar.axon.tutorial.axon.events;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@RequiredArgsConstructor
@Getter
@Setter
@ToString
public class OrderConfirmedEvent {
    private final String orderId;
}
