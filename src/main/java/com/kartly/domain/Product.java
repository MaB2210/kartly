package com.kartly.domain;

public record Product(String id, String name,Money price, String category) {
    public Product{
        if(id == null || id.isBlank()){
            throw new IllegalArgumentException("id must not be blank.");
        }
        if(name == null || name.isBlank()){
            throw new IllegalArgumentException("name must not be blank.");
        }
        if(price == null){
            throw new IllegalArgumentException("price must not be null");
        }
        if(category == null ||category.isBlank()){
            throw new IllegalArgumentException("category must not be null");
        }
    }
}
