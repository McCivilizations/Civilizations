package com.mccivilizations.civilizations.api.civilization;

public class Civilization {
    private long id;
    private String name;

    public Civilization(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
