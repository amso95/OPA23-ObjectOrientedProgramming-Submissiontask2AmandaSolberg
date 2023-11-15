package org.example;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class CropManager {
    public ArrayList<Crop> cropList;
    Scanner scanner= new Scanner(System.in);
    public void cropMenu(){
        boolean stayInLoop = true;
        //cropList = getCrops();  //Update crop lst
        while(stayInLoop) {
            System.out.println("Crop Menu");
            System.out.println("What do you want to do?");
            System.out.println("1.Add crop.");
            System.out.println("2.Remove crop.");
            System.out.println("3.List all crops.");
            System.out.println("9.Go back.");
            String input = scanner.nextLine();
            switch (input){
                case "1":   //Add
                    addCrop();
                    break;
                case "2":   //Remove
                    removeCrop();
                    break;
                case "3":   //List all
                    viewCrops();
                    break;
                case "9":   //Go back
                    stayInLoop = false;
                    break;
                default:
                    System.out.println("Invalid input! Try again.");
                    break;
            }
        }
    }
    private void addCrop(){
        viewCrops();
        boolean cropIdFound = false;
        String name = getVariable("What is the id of the crop you want to add to, press 0 to use auto increment and add a new crop.");
        int tempId = -1;
        try {
            for(Crop crop: cropList) {
                if(Integer.parseInt(name) == crop.getId() || Integer.parseInt(name) == 0) {
                    tempId = Integer.parseInt(name);  //Check if the picked id is an id in the list
                    cropIdFound = true;
                    break;
                }
            }
            if(!cropIdFound){
                System.out.println("Not a valid option try again.");
                return;
            }
        }
        catch (Exception e){
            System.out.println("Invalid input! Must be a number.");
            return;
        }
        int quantity = 0;
        for(Crop crop:cropList){
            if(tempId == crop.getId()){ //If crop with given id already exists, add to this crops quantity
                System.out.println("This crop with id " + crop.getId() + " exists in the list.");
                System.out.println(crop.getDescription() + " is the information of the crop.");
                try {
                    quantity =Integer.parseInt(getVariable("How much do you want to add to the crops quantity?"));
                    if(quantity <= 0){  //Quantity must be a positive number
                        System.out.println("Invalid input. Quantity have to be a number bigger then 0.");
                        return;
                    }
                }
                catch (Exception e){
                    System.out.println("Invalid input! Must be a number.");
                }
                crop.addCrop(quantity);
                return;
            }
        }
        name = getVariable("What is the name of the crop you want to add?");    //New crop will be added get the name of the crop
        String cropType = getVariable("What type of crop is " + name + "?");    //Get crop type of the crop
        try {
            quantity =Integer.parseInt(getVariable("What is the quantity of the " + name +"?"));    //Get quantity of he crop
        }
        catch (Exception e){
            System.out.println("Invalid input! Must be a number.");
        }
        if(quantity <= 0){
            System.out.println("Invalid input. Quantity have to be a number bigger then 0.");
            return;
        }
        Crop addCrop;   //Variable holder for crop to add.
        if(tempId == 0){    //If-statement to determine which constructor to use.
            addCrop = new Crop(name, cropType, quantity);   //Add crop to list
        }
        else {
            addCrop = new Crop(tempId, name, cropType, quantity);   //Add crop to list
        }
        cropList.add(addCrop);
    }
    private void removeCrop(){
        System.out.println("Write the id of what crop you want to remove.");
        viewCrops();
        try{
            int input = Integer.parseInt(scanner.nextLine());
            for(int i = 0; i < cropList.size(); i++){   //Look for the crop in the list with the id the user write
                if(input == cropList.get(i).getId()){
                    Crop removeCrop = cropList.get(i);
                    cropList.remove(removeCrop);    //Remove crop
                    System.out.println(removeCrop.getDescription() + ". Is now removed.");
                    return;
                }
            }
            System.out.println("Crop with id " + input + " is not found."); //Crops not found
        }
        catch (Exception e){
            System.out.println("Invalid input! Must be a number.");
        }
    }
    private void viewCrops(){
        if(cropList.isEmpty()){ //List of crops is empty
            System.out.println("There is no crops to list.");
            return;
        }
        for(Crop crop: cropList){   //List crops
            System.out.println(crop.getDescription());
        }
    }
    private String getVariable(String variableMessage){
        System.out.println(variableMessage);
        return scanner.nextLine();
    }
    public ArrayList<Crop> getCropList(){
        return cropList;
    }
}
