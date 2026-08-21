package com.kartly.app;

import com.kartly.domain.*;
import com.kartly.domain.enums.OrderStatus;
import com.kartly.exception.InvalidOrderStateException;
import com.kartly.service.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

        Product mouse = new Product("p1","Wireless mouse",Money.of(19.99,"USD"),"Electronics");
        Product wire = new Product("p2","USB-C cable",Money.of(9.99,"USD"),"Electronics");

        Cart cart = new Cart();
        cart.addItem(mouse,2);
        cart.addItem(wire,1);
        cart.addItem(mouse,1);

        System.out.println("Mouse Quantity: "+ cart.quantityOf(mouse));
        System.out.println("Full cart: "+cart.items());

        System.out.println("Distinct Categories: "+cart.distinctCategories());

        OrderProcessingQueue queue = new OrderProcessingQueue();
        queue.enqueue(order);
        Order urgentOrder = new Order("cust-002",new Address("5949 Yonge St.","North York","M2M 3V8","Canada"));
        queue.pushUrgent(urgentOrder);
        System.out.println("Queue Size: "+queue.size());
        System.out.println("First to process: "+queue.processNext().customerId());
        System.out.println("Next: "+queue.processNext().customerId());

        List<Order> orders = new ArrayList<>();
        orders.add(order);
        orders.add(urgentOrder);
        orders.sort(null);
        System.out.println("Sorted By Date: ");
        orders.forEach(o -> System.out.println(o.id() + " - " + o.createdAt()));

        Comparator<Order> byTotalDescending = (o1, o2) -> o2.calculateTotal().amount().compareTo(o1.calculateTotal().amount());
        orders.sort(byTotalDescending);
        System.out.println("Sorted By Total (highest first): ");
        orders.forEach(o -> System.out.println(o.id() + " - " + o.calculateTotal()));

        Map<String,List<Order>> ordersByStatus = orders.stream()
                .collect((Collectors.groupingBy(o ->o.status().getClass().getSimpleName())));
        System.out.println("Orders grouped by status: ");
        ordersByStatus.forEach((statusName,ordersInStatus) ->
                System.out.println(statusName + ": " + ordersInStatus.size() + " order(s)"));

        List<Order> nonEmptyOrders = orders.stream()
                .filter(o -> o.calculateTotal().amount().compareTo(BigDecimal.ZERO) > 0 )
                .collect(Collectors.toList());
        System.out.println("Non-empty orders: "+ nonEmptyOrders.size());

        Money grandTotal = orders.stream()
                .map(Order::calculateTotal)
                .reduce(Money.of(0.0,"USD"), Money::add);
        System.out.println("Grand Total across all orders: "+grandTotal);
    }
}
