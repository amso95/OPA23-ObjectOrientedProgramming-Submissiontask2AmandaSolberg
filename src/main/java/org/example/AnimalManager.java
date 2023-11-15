package org.example;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Scanner;

public class AnimalManager {
    public ArrayList<Animal> animalList;
    Scanner scanner= new Scanner(System.in);
    public void animalMenu(ArrayList<Crop> cropList){
        boolean stayInLoop = true;
        while(stayInLoop) {
            System.out.println("Animal Menu");
            System.out.println("What do you want to do?");
            System.out.println("1.Add animal.");
            System.out.println("2.Remove animal.");
            System.out.println("3.List all animal.");
            System.out.println("4.Feed animals.");
            System.out.println("9.Go back.");
            String input = scanner.nextLine();

            switch (input){
                case "1":   //Add
                    addAnimal();
                    break;
                case "2":   //Remove
                    removeAnimal();
                    break;
                case "3":   //List all
                    viewAnimals();
                    break;
                case "4":   //Feed animal
                    feedAnimal(cropList);
                    break;
                case "9":   //Go back
                    stayInLoop = false;
                    break;
                default:
                    System.out.println("Invalid input! Try again");
                    break;
            }
        }
    }
    private void addAnimal(){
        boolean stayInLoop = true;
        String name = getVariable("Press 0 to use auto increment and add a new animal to the list.");
        int tempId = -1;
        try {
                tempId = Integer.parseInt(name);    //Try to make input to a number
        }
        catch (Exception e){
            System.out.println("Invalid input! Must be a number.");
            return;
        }
        if(tempId != 0){    //Check if input is a given id that already exists in the list
            for(Animal animal:animalList){
                if(tempId == animal.getId()){
                    System.out.println("This id is already in use and exists in the list.");
                    System.out.println("Try again.");
                    return;
                }
            }
            System.out.println("Invalid input. Try again!");
        }
        else if(tempId == 0) {  //Auto increment is allowed to add a new animal
            name = getVariable("What is the name of the animal you want to add.");  //Get the name of the animal
            String species = getVariable("What type of species is " + name + ".");  //Get the species of the animal
            ArrayList<String> acceptableCropTypes = new ArrayList<>();
            while (stayInLoop) {
                String cropToEat = getVariable("What type of food/crop does " + name + "eat?"); //Get acceptable crop types
                acceptableCropTypes.add(cropToEat);
                System.out.println(cropToEat + " is added to " + name + " food list.\nIf you don't want to add more food/crops to the list write 0 and press Enter.\nOtherwise press Enter to continue adding to the list.");
                if (scanner.nextLine().equals("0")) {
                    stayInLoop = false;
                }
            }
            Animal animalToAdd = new Animal(name, species, acceptableCropTypes);    //Create new animal
            animalList.add(animalToAdd);
        }
        else{
            System.out.println("Invalid input. Try again!");
        }
    }
    private void removeAnimal(){
        System.out.println("Write the id of what animal you want to remove.");
        viewAnimals();
        try{
            int input = Integer.parseInt(scanner.nextLine());   //Get id from the user
            for(int i = 0; i < animalList.size(); i++){
                if(input == animalList.get(i).getId()){ //See if the given id from user is a match with one in the list
                    Animal removeAnimal = animalList.get(i);
                    animalList.remove(removeAnimal);    //Remove animal
                    System.out.println(removeAnimal.getDescription() + ". Is now removed.");
                    return;
                }
            }
            System.out.println("Animal with id " + input + " is not found.");   //Animal with given id is not found
        }
        catch (Exception e){
            System.out.println("Invalid input! Must be a number.");
        }
    }
    private void viewAnimals(){
        if(animalList.isEmpty()){ //List of animals is empty
            System.out.println("There is no animals to list.");
            return;
        }
        for(Animal animal: animalList){ //List all animals
            System.out.println(animal.getDescription());
        }
    }
    private void feedAnimal(ArrayList<Crop> cropList){  //Choose an animal to feed a crop
        Animal animalToFeed = getAnimalById(); //Variable holder for the picked animal to feed
        if(animalToFeed.getId() != -1) {
            Crop cropToGive = animalToFeed.pickCrop(cropList);  //Variable holder for the picked crop
            if (cropToGive.getId() > 0) {
                animalToFeed.feed(cropToGive);  //Feed crop
            }
        }
        else{
            System.out.println("No valid pick of animal where made. Try again.");
        }
    }
    public Animal getAnimalById(){  //Find the animal of a specific id in animal list
        System.out.println("Write the id of the animal you want to feed.");
        viewAnimals();
        try {
            int id = Integer.parseInt(scanner.nextLine());
            for (Animal animal : animalList) {
                if (animal.getId() == id) {
                    return animal;
                }
            }
        }
        catch (Exception e){
            System.out.println("Invalid input. Must be a number!");
        }
        return new Animal();
    }
    private String getVariable(String variableMessage){
        System.out.println(variableMessage);
        return scanner.nextLine();
    }
}
