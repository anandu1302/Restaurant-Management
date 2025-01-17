package com.nextgen.restaurantmanagement.ModelClass;

public class OrderedFoodModelClass {

    private String foodName;
    private String restName;
    private String restPlace;
    private String restImage;

    public OrderedFoodModelClass(String foodName,String rest_name,String rest_place, String rest_image) {
        this.foodName = foodName;
        this.restName = rest_name;
        this.restPlace = rest_place;
        this.restImage = rest_image;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public String getRestName() {
        return restName;
    }

    public void setRestName(String restName) {
        this.restName = restName;
    }

    public String getRestPlace() {
        return restPlace;
    }

    public void setRestPlace(String restPlace) {
        this.restPlace = restPlace;
    }

    public String getFoodImage() {
        return restImage;
    }

    public void setFoodImage(String rest_image) {
        this.restImage = rest_image;
    }
}
