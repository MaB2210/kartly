package com.kartly.domain;

import java.util.HashMap;
import java.util.Map;

public class Cart {
    private final Map<Product, Integer> items = new HashMap<>();

    public void addItem(Product product, int quantity){
        if(quantity <= 0){
            throw new IllegalArgumentException("product must be positive");
        }
        items.merge(product,quantity,Integer::sum);
    }

    public void removeItem(Product product){
        items.remove(product);
    }

    public int quantityOf(Product product){
        return items.getOrDefault(product,0);
    }

    public Map<Product,Integer> items(){
        return Map.copyOf(items);
    }

    public boolean isEmpty(){
        return items.isEmpty();
    }

    public java.util.Set<String> distinctCategories(){
        java.util.Set<String> categories = new java.util.HashSet<>();
        for(Product product: items.keySet()){
            categories.add(product.category());
        }
        return categories;
    }

}
