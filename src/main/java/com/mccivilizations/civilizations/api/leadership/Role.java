package com.mccivilizations.civilizations.api.leadership;

import java.util.List;

public class Role {
    private final String name;
    private final List<Permission> permissions;

    public Role(String name, List<Permission> permissions) {
        this.name = name;
        this.permissions = permissions;
    }

    public String getName() {
        return name;
    }

    public List<Permission> getPermissions() {
        return permissions;
    }
}
