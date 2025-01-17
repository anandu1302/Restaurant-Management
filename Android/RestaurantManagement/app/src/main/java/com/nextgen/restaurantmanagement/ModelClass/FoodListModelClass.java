package com.nextgen.restaurantmanagement.ModelClass;

public class FoodListModelClass {

    String id;
    String name;
    String quantity;
    String price;

    public FoodListModelClass(String id,String name,String quantity,String price) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.price = price;

    }

    public String getId(){
        return id;
    }

    public String getName(){
        return name;
    }

    public String getQuantity(){
        return quantity;
    }

    public String getPrice(){
        return price;
    }

}
