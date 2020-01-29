package com.mccivilizations.civilizations.dbmodels;

import io.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.UUID;

@Entity
public class PlayerModel extends Model {
    @Id
    @GeneratedValue
    private long id;

    private final String name;
    private final UUID uuid;

    public PlayerModel(String name, UUID uuid) {
        this.name = name;
        this.uuid = uuid;
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getName() {
        return name;
    }

    public long getId() {
        return id;
    }
}
