package org.example;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Farm {
    private ArrayList<Crop> cropList = new ArrayList<>();
    private ArrayList<Animal> animalList = new ArrayList<>();
    public void mainMenu(){
        CropManager cm = new CropManager();
        Scanner scanner = new Scanner(System.in);
        AnimalManager am = new AnimalManager();
        cm.cropList = cropList;
        am.animalList = animalList;
        String input;
        boolean stayInLoop = true;
        while(stayInLoop) {
            System.out.println("Farm Menu");
            System.out.println("What do you want to do?");
            System.out.println("1.Go to crop menu.");
            System.out.println("2.Go to animal menu.");
            System.out.println("9.Save and Quit");
            input = scanner.nextLine();
            switch (input){
                case "1":
                    cm.cropMenu();  //Go to crop menu
                    break;
                case "2":
                    cropList = cm.getCropList();    //Update list
                    am.animalMenu(cropList);    //Go to animal menu
                    break;
                case"9":
                    save(); //Save to files
                    System.out.println("Good bye!");
                    stayInLoop = false;
                    break;
                default:
                    System.out.println("Invalid input");
                    break;
            }
        }
    }

    public Farm(){
        File cropFile = new File("crops.txt");
        File animalFile = new File("animals.txt");
        if(cropFile.exists()){  //If file exists read it and add objects to crop list
            try {
                FileReader fr = new FileReader(cropFile);
                BufferedReader br = new BufferedReader(fr);
                String line = br.readLine();
                String[] tempArr;
                String delimiter = ",";
                int id = -1;
                String name;
                String cropType;
                int quantity = -1;
                while(line != null){
                    tempArr = line.split(delimiter);
                    name = tempArr[1];
                    cropType = tempArr[2];
                    try{
                        id = Integer.parseInt(tempArr[0]);
                        quantity = Integer.parseInt(tempArr[3]);
                    }
                    catch (Exception e){
                        System.out.println("Invalid input from file. Must be a number.");
                    }
                    if(id != -1 && quantity != -1) {
                        Crop crop = new Crop(id, name, cropType, quantity);
                        cropList.add(crop);
                    }
                    line = br.readLine();
                }
                br.close(); //Done with reading from the file, let's close it
            }
            catch (IOException e){
                System.out.println("Error, something went wrong.");
            }
        }
        else{   //File do not exist create some data to se in the program and save to a file
            System.out.println(cropFile.getName() + " do not exists. Some crops will now be added.");
            cropList.add(new Crop("Carrot","Vegetable",25));
            cropList.add(new Crop("Hay","Hey",25));
            cropList.add(new Crop("Grass","Grass",25));
            cropList.add(new Crop("Sunflower seed","Seed",25));
            cropList.add(new Crop("Sesame seed","Seed",25));
            cropList.add(new Crop("Cucumber","Vegetable",25));
            cropList.add(new Crop("Corn","Vegetable",25));
            cropList.add(new Crop("Apple","Fruit",25));
            cropList.add(new Crop("Pear","Fruit",25));
            cropList.add(new Crop("Grape","Fruit",25));
        }
        if(animalFile.exists()){    //If file exists read it and add objects to animal list
            try {
                FileReader fr = new FileReader(animalFile);
                BufferedReader br = new BufferedReader(fr);
                String line = br.readLine();
                String[] tempArr;
                String delimiter = ",";
                String delimiterList = "/";
                int id = -1;
                String name;
                String species;
                ArrayList<String> acceptableCropTypes = new ArrayList<>();
                while(line != null){
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
                        animalList.add(animal);
                    }
                    line = br.readLine();
                }
                br.close(); //Done with reading from the file, let's close it
            }
            catch (IOException e){
                System.out.println("Error, something went wrong.");
            }
        }
        else{   //File do not exist create some data to se in the program and save to a file
            System.out.println(animalFile.getName() + " do not exists. Some animals will now be added.");
            ArrayList<String> str = new ArrayList<>();
            str.add("Seed");
            str.add("Vegetable");
            animalList.add(new Animal("Peter","Rooster",str));
            animalList.add(new Animal("Anna","Hen",str));
            animalList.add(new Animal("Monica","Chicken",str));
            str.clear();
            str.add("Hay");
            str.add("Grass");
            str.add("Vegetable");
            animalList.add(new Animal("Grappo","Horse",str));
            animalList.add(new Animal("Grynet","Horse",str));
            animalList.add(new Animal("Carl","Horse",str));
            animalList.add(new Animal("Ross","Cow",str));
            animalList.add(new Animal("Chandler","Sheep",str));
            animalList.add(new Animal("Joe","Goat",str));
            animalList.add(new Animal("Lisa","Llama",str));
        }
    }

    private void save(){    //Write to files to save the information in animal and crop list
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
        file = new File("crops.txt");  //Name of the file
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
}
