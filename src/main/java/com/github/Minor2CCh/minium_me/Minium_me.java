package com.github.Minor2CCh.minium_me;

import com.github.Minor2CCh.minium_me.block.MiniumBlock;
import com.github.Minor2CCh.minium_me.component.MiniumModComponent;
import com.github.Minor2CCh.minium_me.damage_type.MiniumDamageType;
import com.github.Minor2CCh.minium_me.enchantment.MiniumEnchantments;
import com.github.Minor2CCh.minium_me.entity.MiniumEntityType;
import com.github.Minor2CCh.minium_me.event.*;
import com.github.Minor2CCh.minium_me.handler.DoubleClickHandler;
import com.github.Minor2CCh.minium_me.item.*;
import com.github.Minor2CCh.minium_me.particle.MiniumParticle;
import com.github.Minor2CCh.minium_me.platform.AccessoryPlatform;
import com.github.Minor2CCh.minium_me.platform.DisableAccessoryPlatform;
import com.github.Minor2CCh.minium_me.platform.EnableAccessoryPlatform;
import com.github.Minor2CCh.minium_me.sound.MiniumSoundsEvent;
import com.github.Minor2CCh.minium_me.statuseffect.MiniumStatusEffects;
import com.github.Minor2CCh.minium_me.worldgen.MiniumOres;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;

public class Minium_me implements ModInitializer {

    public static final String MOD_ID = "minium_me";
    public static final AccessoryPlatform ACCESSORY_PLATFORM = FabricLoader.getInstance().isModLoaded("trinkets") ? new EnableAccessoryPlatform() : new DisableAccessoryPlatform();

    @Override
    public void onInitialize() {
        MiniumItem.initialize();
        if(FabricLoader.getInstance().isModLoaded("farmersdelight")){
            FDItems.initialize();
        }
        MiniumBlock.initialize();
        MiniumOres.initialize();
        MiniumItemGroup.initialize();
        AddLootTable.initialize();
        MiniumModComponent.initialize();
        MiniumEntityType.initialize();
        MiniumParticle.initialize();
        MiniumDamageType.initialize();
        MiniumStatusEffects.initialize();
        MiniumSoundsEvent.initialize();
        MiniumPotions.initialize();
        MiniumEnchantments.initialize();
        DoubleClickHandler.initialize();
        PicklingSaltReduceEvent.initialize();
        PendantProtectionEvent.initialize();
        IrisQuartzElytraBoostEvent.initialize();
        LivingEntityTickEvent.initialize();
        MaceAdvancementEvent.initialize();
    }
}
