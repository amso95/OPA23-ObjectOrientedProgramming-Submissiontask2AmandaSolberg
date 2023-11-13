package org.example;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class AnimalManager {
    private ArrayList<Animal> animalList = getAnimals();
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
            System.out.println("9.Save and Go back.");
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
                    save(); //To keep the list of animals updated
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
                saveCropList(cropList); //Update list of crops
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
    public ArrayList<Animal> getAnimals(){  //Fill in the animal list with data from file: animals.txt
        File file = new File("animals.txt");  //Name of the file
        ArrayList<Animal> listOfAnimals = new ArrayList<>();
        try {
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String line = br.readLine();
            String[] tempArr;
            String delimiter = ",";
            String delimiterList = "/";
            int id = -1;
            String name;
            String species;
            ArrayList<String> acceptableCropTypes = new ArrayList<>();
            while(line != null){    //Get line from the file and make it into animal objects
                tempArr = line.split(delimiter);
                name = tempArr[1];
                species = tempArr[2];
                try{
                    id = Integer.parseInt(tempArr[0]);
                }
                catch (Exception e){
                    System.out.println("Invalid input from file. Must be a number.");
                }
                acceptableCropTypes = new ArrayList<String>(Arrays.asList(tempArr[3].split(delimiterList)));
                if(id != -1 ) {
                    Animal animal = new Animal(id, name, species, acceptableCropTypes);
                    listOfAnimals.add(animal);  //Add animal object to animal list
                }
                line = br.readLine();
            }
            br.close(); //Done with reading from the file, let's close it
        }
        catch (IOException e){
            System.out.println("Error, something went wrong.");
        }
        return listOfAnimals;
    }

    private void saveCropList(ArrayList<Crop> cropList){    //Save the changes made in crop list by writing them to the file
        File file = new File("crops.txt");  //Name of the file
        try {
            FileWriter fw = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw);
            for(int i = 0; i < cropList.size(); i++){
                bw.write(cropList.get(i).getCSV()); //Write crops in file in CSV format
                if(i - 1 < cropList.size()){    //So the file don't end with new line
                    bw.newLine();
                }
            }
            bw.close(); //Done with writing to the file, let's close it
        }
        catch (IOException e){
            System.out.println("Error, something went wrong.");
        }
    }

    private void save(){    //Save changes made in animal list by writing them to the file
        File file = new File("animals.txt");  //Name of the file
        try {
            FileWriter fw = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw);
            for(int i = 0; i < animalList.size(); i++){
                bw.write(animalList.get(i).getCSV()); //Write crops in file in CSV format
                if(i - 1 < animalList.size()){    //So the file don't end with new line
                    bw.newLine();
                }
            }
            bw.close(); //Done with writing to the file, let's close it
        }
        catch (IOException e){
            System.out.println("Error, something went wrong.");
        }
    }
    private String getVariable(String variableMessage){
        System.out.println(variableMessage);
        return scanner.nextLine();
    }

    public ArrayList<Animal> getAnimalList(){
        return animalList;
    }
}
