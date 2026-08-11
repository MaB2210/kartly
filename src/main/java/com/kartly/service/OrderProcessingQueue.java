package com.kartly.service;

import com.kartly.domain.Order;

import java.util.ArrayDeque;
import java.util.Deque;

public class OrderProcessingQueue {
    private final Deque<Order> queue = new ArrayDeque<>();

    public void enqueue(Order order){
        queue.addLast(order);
    }
    public Order processNext(){
        return queue.pollFirst();
    }
    public void pushUrgent(Order order){
        queue.addFirst(order);
    }
    public int size(){
        return queue.size();
    }
}
