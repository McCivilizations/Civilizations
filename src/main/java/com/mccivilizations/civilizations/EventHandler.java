package com.mccivilizations.civilizations;

import com.mccivilizations.civilizations.api.CivilizationsAPI;
import com.mccivilizations.civilizations.api.player.Player;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;

@EventBusSubscriber(modid = Civilizations.MODID)
public class EventHandler {
    @SubscribeEvent
    public static void handleLogin(PlayerEvent.PlayerLoggedInEvent playerLoggedInEvent) {
        CivilizationsAPI.getInstance()
                .getRepositoryHolder()
                .getPlayerRepository()
                .create(new Player(playerLoggedInEvent.player));
    }
}
