package com.kartly.app;

import com.kartly.domain.Address;
import com.kartly.domain.Money;
import com.kartly.domain.Order;
import com.kartly.domain.OrderLine;
import com.kartly.domain.enums.OrderStatus;
import com.kartly.exception.InvalidOrderStateException;
import com.kartly.service.DiscountPolicy;
import com.kartly.service.FlatAmountDiscount;
import com.kartly.service.NoDiscount;
import com.kartly.service.PercentageDiscount;

import java.math.BigDecimal;

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
            case OrderStatus.Cancelled(String reason)  -> "Order Cancelled: " + reason;
        };

        System.out.println(message);

        OrderLine orderLine = new OrderLine("WirelessMouse",Money.of(29.99,"USD"), 2);
        System.out.println(orderLine.lineTotal());

        Order order = new Order("Cust-001",new Address("5949 Yonge St.","North York","M2M 3V8","Canada"));
        order.addLine(new OrderLine("Wireless Mouse",Money.of(29.99,"USD"),2));
        order.addLine(new OrderLine("USB_C Cable",Money.of(9.99,"USD"),1));

        System.out.println("Total: "+order.calculateTotal());
        System.out.println("Status: "+ order.status());

        order.confirm();
        order.ship();

        System.out.println("status after shipping: "+order.status());

        try{
            order.cancel("Changed my mind");
        }catch (InvalidOrderStateException e){
            System.out.println("Caught expected error: "+ e.getMessage());
        }

        DiscountPolicy noDiscount = new NoDiscount();
        Money result = noDiscount.apply(Money.of(100.00,"USD"));
        System.out.println(result);

        Money orderTotal = Money.of(100,"USD");

        DiscountPolicy[] policies = {
            new NoDiscount(),
            new PercentageDiscount(BigDecimal.valueOf(10)),
            new FlatAmountDiscount(Money.of(15,"USD"))
        };
        for(DiscountPolicy policy: policies){
            System.out.println(policy.getClass().getSimpleName() + " => "+ policy.apply(orderTotal));
        }
    }
}
