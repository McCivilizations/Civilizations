package com.mccivilizations.civilizations.enchantment;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.inventory.EquipmentSlotType;

public class LeadershipEnchantment extends Enchantment {
    public LeadershipEnchantment() {
        super(Rarity.VERY_RARE, EnchantmentType.ALL, EquipmentSlotType.values());
    }
}
