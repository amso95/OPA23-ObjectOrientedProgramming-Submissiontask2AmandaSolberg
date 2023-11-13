package org.example;

import java.util.concurrent.atomic.AtomicInteger;

public class Crop extends Entity{
    private String cropType;
    private int quantity;
    private static AtomicInteger idCounter = new AtomicInteger(1);

    public Crop(String name, String cropType, int quantity) {
        super(idCounter.getAndIncrement(), name);
        this.cropType = cropType;
        this.quantity = quantity;
    }
    public Crop(int id, String name, String cropType, int quantity) {
        super(id, name);
        idCounter.set(id + 1);
        this.cropType = cropType;
        this.quantity = quantity;
    }

    public Crop() {
        super(-1, "");
    }

    public void addCrop(int nrOfCrops){
        quantity += nrOfCrops;
    }
    public boolean takeCrop(int nrOfCrops){
        if(nrOfCrops<=quantity){
            quantity -= nrOfCrops;
            return true;
        }
        return false;
    }

    public String getCropType() {
        return cropType;
    }

    @Override
    public String getDescription(){
        return "Id: " + getId() + ", Name: " + name + ", Crop Type: " + cropType + ", Quantity: " + quantity;
    }

    public String getCSV(){
        return getId() + "," + name + "," + cropType + "," + quantity;
    }
}
