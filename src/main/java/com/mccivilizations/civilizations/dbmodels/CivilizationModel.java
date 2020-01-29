package com.mccivilizations.civilizations.dbmodels;

import com.mccivilizations.civilizations.api.civilization.Civilization;
import io.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class CivilizationModel extends Model {
    @Id
    private long id;
    private String name;

    public CivilizationModel(String name) {
        this.name = name;
    }

    public CivilizationModel(Civilization civilization) {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Civilization fromEntity() {
        return new Civilization(id, name);
    }
}
