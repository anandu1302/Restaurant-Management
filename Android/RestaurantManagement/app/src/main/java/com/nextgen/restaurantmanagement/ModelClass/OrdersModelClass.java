package com.nextgen.restaurantmanagement.ModelClass;

import java.util.List;

public class OrdersModelClass {

    String id;
    String orderId;
    String price;
    String date;
    String status;
    String itemCount;
    private List<OrderedFoodModelClass> foodList;

    public OrdersModelClass(String id,String orderId,String price,String date,String status,String itemCount,List<OrderedFoodModelClass> foodList) {
        this.id = id;
        this.orderId = orderId;
        this.price = price;
        this.date = date;
        this.status = status;
        this.itemCount = itemCount;
        this.foodList = foodList;

    }

    public String getId(){
        return id;
    }

    public String getOrderId(){
        return orderId;
    }

    public String getPrice(){
        return price;
    }

    public String getDate(){
        return date;
    }

    public String getStatus(){
        return status;
    }

    public String getItemCount(){
        return itemCount;
    }

    public List<OrderedFoodModelClass> getFoodList() {
        return foodList;
    }

    public void setFoodList(List<OrderedFoodModelClass> foodList) {
        this.foodList = foodList;
    }


}
