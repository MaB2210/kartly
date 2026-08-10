package com.kartly.domain;

import com.kartly.domain.enums.OrderStatus;
import com.kartly.exception.InvalidOrderStateException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class Order {
    private final String id;
    private final String customerId;
    private final Address shippingAddress;
    private final List<OrderLine> lines;
    private final LocalDateTime createdAt;
    private OrderStatus status;

    public Order(String customerId, Address shippingAddress){
        if(customerId == null || customerId.isBlank()){
            throw new IllegalArgumentException("customerId must not be blank.");
        }
        if(shippingAddress == null){
            throw new IllegalArgumentException("shippingAddress must not be null");
        }
        this.id = UUID.randomUUID().toString();
        this.customerId = customerId;
        this.shippingAddress = shippingAddress;
        this.lines = new ArrayList<>();
        this.createdAt = LocalDateTime.now(java.time.ZoneOffset.UTC);
        this.status = new OrderStatus.Created();
    }

    public void ensureModifiable(){
        if(!(status instanceof OrderStatus.Created)){
            throw new InvalidOrderStateException("order cannot be modified once it is "+ status.getClass().getSimpleName());
        }
    }

    public void addLine(OrderLine line){
        ensureModifiable();
        lines.add(line);
    }

    public Money calculateTotal(){
        Money total = Money.of(0.0,"USD");
        for(OrderLine line : lines){
            total = total.add(line.lineTotal());
        }
        return total;
    }

    public void confirm(){
        ensureModifiable();
        this.status = new OrderStatus.Confirmed();
    }

    public void ship(){
        if(!(status instanceof OrderStatus.Confirmed)){
            throw new InvalidOrderStateException("Order must be Confirmed before it can be shipped.");
        }
        this.status = new OrderStatus.Shipped();
    }

    public void cancel(String reason){
        if(status instanceof OrderStatus.Shipped){
            throw new InvalidOrderStateException("Cannot Cancel an order that has already shipped.");
        }
        this.status = new OrderStatus.Cancelled(reason);
    }

    public String id() {return id;}
    public String customerId() {return customerId;}
    public Address shippingAddress() {return shippingAddress;}
    public List<OrderLine> lines(){return Collections.unmodifiableList(lines);}
    public OrderStatus status(){return status;}
    public LocalDateTime createdAt(){return createdAt;}
}
