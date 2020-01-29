package com.mccivilizations.civilizations.api.resource;

public class Resource {
    public final int id;
    public final String name;
    public final String translationKey;

    public Resource(int id, String name, String translationKey) {
        this.id = id;
        this.name = name;
        this.translationKey = translationKey;
    }
}
