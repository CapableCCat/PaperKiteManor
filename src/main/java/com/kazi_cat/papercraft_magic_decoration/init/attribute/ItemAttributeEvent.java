package com.kazi_cat.papercraft_magic_decoration.init.attribute;

import com.kazi_cat.papercraft_magic_decoration.PaperKiteManor;
import com.kazi_cat.papercraft_magic_decoration.init.ModItems;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraftforge.event.ItemAttributeModifierEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.UUID;

@Mod.EventBusSubscriber(modid = PaperKiteManor.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ItemAttributeEvent {
    private static final UUID ATTACK_DAMAGE_UUID = UUID.fromString("e6378796-89ce-4f76-a9bc-e12f4ac2ab43");
    private static final UUID ATTACK_SPEED_UUID = UUID.fromString("4d292468-b122-478d-88df-2b9ce37f5a3a");

    @SubscribeEvent
    static void onItemAttributeModifier(ItemAttributeModifierEvent event) {
        if (event.getItemStack().is(ModItems.SAUSAGE_MACE_WEAPON.get()) && !event.getSlotType().isArmor() && event.getSlotType().getIndex() == EquipmentSlot.MAINHAND.getIndex()) {
            event.addModifier(
                    Attributes.ATTACK_DAMAGE,
                    new AttributeModifier(ATTACK_DAMAGE_UUID,
                            "papercraft_magic_decoration:attack_damage_modifier",
                            7.0,
                            AttributeModifier.Operation.ADDITION)
            );

            event.addModifier(
                    Attributes.ATTACK_SPEED,
                    new AttributeModifier(ATTACK_SPEED_UUID,
                            "papercraft_magic_decoration:weapon_speed_modifier",
                            -2.8,
                            AttributeModifier.Operation.ADDITION)
            );
        }
    }
}
