package org.example;

public class Entity {
    private int id;
    protected String name;

    public Entity(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getDescription(){
        return "Id: " + id + ", Name: " + name;
    }
}
