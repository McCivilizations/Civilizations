package com.mccivilizations.civilizations;

import com.mccivilizations.civilizations.databasehandling.DBPlayer;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;

@EventBusSubscriber(modid = Civilizations.MODID)
public class EventHandler {
    @SubscribeEvent
    public static void createPerson(PlayerEvent.PlayerLoggedInEvent playerLoggedInEvent) {
        DBPlayer.create(playerLoggedInEvent.player);
    }
}
