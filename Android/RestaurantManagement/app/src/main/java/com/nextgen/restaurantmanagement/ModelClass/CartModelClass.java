package com.nextgen.restaurantmanagement.ModelClass;

public class CartModelClass {

    String id;
    String itemName;
    String price;
    String image;
    String quantity;

    public CartModelClass(String id,String itemName,String price,String image,String quantity) {
        this.id = id;
        this.itemName = itemName;
        this.price = price;
        this.image = image;
        this.quantity = quantity;
    }

    public String getId(){
        return id;
    }

    public String getItemName(){
        return itemName;
    }

    public String getPrice(){
        return price;
    }

    public String getImage(){
        return image;
    }

    public String getQuantity(){
        return quantity;
    }
}
