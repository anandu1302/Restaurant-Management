package com.nextgen.restaurantmanagement.ModelClass;

public class FoodModelClass {

    String id;
    String name;
    String price;
    String category;
    String description;
    String image;

    public FoodModelClass(String id,String name,String price,String category,String description,String image) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.category = category;
        this.description = description;
        this.image = image;

    }

    public String getId(){
        return id;
    }

    public String getName(){
        return name;
    }

    public String getPrice(){
        return price;
    }

    public String getCategory(){
        return category;
    }

    public String getDescription(){
        return description;
    }

    public String getImage(){
        return image;
    }
}
