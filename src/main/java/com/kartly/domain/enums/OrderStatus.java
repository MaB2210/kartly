package com.kartly.domain.enums;

public sealed interface OrderStatus
    permits OrderStatus.Created, OrderStatus.Confirmed, OrderStatus.Shipped, OrderStatus.Cancelled{
    record Created() implements OrderStatus{}
    record Confirmed() implements OrderStatus{}
    record Shipped() implements OrderStatus{}
    record Cancelled(String reason) implements OrderStatus{}
}
