package com.mccivilizations.database.event;

import com.google.common.collect.Lists;
import com.mccivilizations.database.support.IDBSupport;
import net.minecraftforge.eventbus.api.Event;

import java.util.List;

public class GatherDatabaseSupportEvent extends Event {
    private List<IDBSupport> databaseSupport;

    public GatherDatabaseSupportEvent() {
        this.databaseSupport = Lists.newArrayList();
    }

    public void addDatabaseSupport(IDBSupport dbSpecific) {
        this.databaseSupport.add(dbSpecific);
    }

    public List<IDBSupport> getDatabaseSupport() {
        return databaseSupport;
    }
}
