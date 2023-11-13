package org.example;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

public class Animal extends Entity{
    private String species;
    private ArrayList<String> acceptableCropTypes;

    private static AtomicInteger idCounter = new AtomicInteger(1);

    public Animal(String name, String species, ArrayList<String> acceptableCropTypes){
        super(idCounter.getAndIncrement(),name);
        this.species = species;
        this.acceptableCropTypes = acceptableCropTypes;
    }
    public Animal(int id, String name, String species, ArrayList<String> acceptableCropTypes){
        super(id,name);
        idCounter.set(id + 1);
        this.species = species;
        this.acceptableCropTypes = acceptableCropTypes;
    }

    public Animal() {
        super(-1, "");
    }

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    @Override
    public String getDescription(){
        String temp = getAcceptableCropTypesCSV(", ");
        return "Id: "+ getId() + ", Name: " + name + ", Species: " + species + ", Acceptable crop types: " + temp;
    }
    private String getAcceptableCropTypesCSV(String delimiter){
        String temp = "";
        for(int i = 0; i < acceptableCropTypes.size(); i++){
            temp += acceptableCropTypes.get(i);
            if(i < acceptableCropTypes.size() - 1){
                temp += delimiter;
            }
        }
        return temp;
    }
    public Crop pickCrop(ArrayList<Crop> cropList){
        Crop crop = new Crop();
        int id = -1;
        boolean cropFound = false;
        Scanner scanner= new Scanner(System.in);
        for(int i = 0; i < cropList.size();i++){  //List the crops that the animal can eat
            for(int j = 0; j < acceptableCropTypes.size(); j++){
                if(cropList.get(i).getCropType().equals(acceptableCropTypes.get(j))){
                    System.out.println(cropList.get(i).getDescription());
                    cropFound = true;
                }
            }
        }
        if(cropFound){
            System.out.println("Write the id of the crop you want to feed the animal from the list above.");
            try {
                id = Integer.parseInt(scanner.nextLine());
                for(String str: acceptableCropTypes){
                    if(cropList.get(id - 1).getCropType().equals(str)){
                        return cropList.get(id - 1);
                        /*if(cropList.get(id - 1).takeCrop(1)){
                            System.out.println(name + " ate 1 crop.");
                            return cropList;
                        }
                        else {
                            System.out.println("There is not enough crops to feed the animal.");
                            return cropList;
                        }*/
                    }
                }
            }
            catch (Exception e){
                System.out.println("Invalid input. Must be a number!");
            }
        }
        else{
            System.out.println("Didn't found any crop that the animal wanted to eat.");
        }
        return crop;
    }
    public void feed(Crop crop){ //The crop that the user choose is feed to the animal so crops quantity decreases.
        if(crop.getId() == -1){    //Check in variable holder got filled in
            System.out.println("There is no crop to feed the selected animal with.");
        }
        else if(!crop.takeCrop(1)){   //Check if feeding the animal worked
            System.out.println("This is the information about the crop: " + crop.getDescription() + ". There is not enough to feed the animal.");
            System.out.println("Feeding the animal didn't work. Add more of the crop and try again.");
        }
        else{
            System.out.println(name + " ate 1 crop.");
        }
    }
    public String getCSV(){
        String str = getAcceptableCropTypesCSV("/");
        return getId() + "," + name + "," + species + "," + str;
    }
}
