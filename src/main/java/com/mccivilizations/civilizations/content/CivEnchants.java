package com.mccivilizations.civilizations.content;

import com.mccivilizations.civilizations.Civilizations;
import com.mccivilizations.civilizations.enchantment.LeadershipEnchantment;
import net.minecraft.enchantment.Enchantment;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class CivEnchants {
    private static final DeferredRegister<Enchantment> ENCHANTMENTS = new DeferredRegister<>(
            ForgeRegistries.ENCHANTMENTS, Civilizations.MODID);

    public static final RegistryObject<LeadershipEnchantment> LEADERSHIP = ENCHANTMENTS.register("leadership",
            LeadershipEnchantment::new);

    public static void register(IEventBus eventBus) {
        ENCHANTMENTS.register(eventBus);
    }
}
