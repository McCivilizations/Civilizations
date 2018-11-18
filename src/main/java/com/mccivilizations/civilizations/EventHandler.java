package com.mccivilizations.civilizations;

import com.mccivilizations.civilizations.database.operation.CreateOperation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;

@EventBusSubscriber(modid = Civilizations.MODID)
public class EventHandler {
    @SubscribeEvent
    public static void createPerson(PlayerEvent.PlayerLoggedInEvent playerLoggedInEvent) {
        final EntityPlayer player = playerLoggedInEvent.player;
        Civilizations.proxy.getDatabase().submitOperation(new CreateOperation(
                "INSERT INTO PERSON(NAME, UUID) VALUES(?, ?) ON CONFLICT DO NOTHING", preparedStatement -> {
                    preparedStatement.setString(1, player.getName());
                    preparedStatement.setString(2, player.getUniqueID().toString());
        }));
    }
}
