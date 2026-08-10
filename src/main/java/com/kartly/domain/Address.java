package com.kartly.domain;

public record Address(String street, String city, String postalCode, String country){
    public Address{
        if(street == null || street.isBlank()){
            throw new IllegalArgumentException("street must not be null");
        }
        if(city == null || city.isBlank()){
            throw new IllegalArgumentException("city must not be null");
        }
        if(postalCode == null || postalCode.isBlank()){
            throw new IllegalArgumentException("postalcode must no be null");
        }
        if(country == null ||country.isBlank()){
            throw new IllegalArgumentException("country must not be null");
        }
    }

    public String formatted(){
        return "%s, %s, %s, %s ".formatted(street,city,postalCode,country);
    }
}
