package com.mccivilizations.civilizations.api.resource;

public class Resource {
    private final int id;
    private final String name;
    private final String translationKey;

    public Resource(int id, String name, String translationKey) {
        this.id = id;
        this.name = name;
        this.translationKey = translationKey;
    }
}
