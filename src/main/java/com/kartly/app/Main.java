package com.kartly.app;

import com.kartly.domain.Address;
import com.kartly.domain.Money;
import com.kartly.domain.enums.OrderStatus;

public class Main {
    public static void main(String[] args) {
        System.out.println("Kartly bootstraped successfully - Java" + System.getProperty("java.version"));
        Money price = Money.of(19.99, "USD");
        Money total = price.multiply(3);
        System.out.println(total);
        Address address = new Address("5949 Yonge Street","North York","M2M 3V8","Canada");
        System.out.println(address.formatted());
        OrderStatus status = new OrderStatus.Cancelled("Customer changed their mind");
        String message = switch(status) {
            case OrderStatus.Created c -> "Order just Created";
            case OrderStatus.Confirmed c -> "Order is Confirmed";
            case OrderStatus.Shipped s -> "Order Shipped";
            case OrderStatus.Cancelled c -> "Order Cancelled: " + c.reason();
        };

        System.out.println(message);
    }
}
