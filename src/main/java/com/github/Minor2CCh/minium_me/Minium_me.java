package com.github.Minor2CCh.minium_me;

import com.github.Minor2CCh.minium_me.block.MiniumBlock;
import com.github.Minor2CCh.minium_me.component.MiniumModComponent;
import com.github.Minor2CCh.minium_me.damage_type.MiniumDamageType;
import com.github.Minor2CCh.minium_me.enchantment.MiniumEnchantments;
import com.github.Minor2CCh.minium_me.entity.MiniumEntityType;
import com.github.Minor2CCh.minium_me.event.*;
import com.github.Minor2CCh.minium_me.item.*;
import com.github.Minor2CCh.minium_me.particle.MiniumParticles;
import com.github.Minor2CCh.minium_me.platform.AccessoryPlatform;
import com.github.Minor2CCh.minium_me.platform.DisableAccessoryPlatform;
import com.github.Minor2CCh.minium_me.platform.EnableAccessoryPlatform;
import com.github.Minor2CCh.minium_me.registry.MiniumBlockEntityTypes;
import com.github.Minor2CCh.minium_me.sound.MiniumSoundsEvent;
import com.github.Minor2CCh.minium_me.statuseffect.MiniumStatusEffects;
import com.github.Minor2CCh.minium_me.worldgen.MiniumOres;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.util.Identifier;

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
        MiniumParticles.initialize();
        MiniumDamageType.initialize();
        MiniumStatusEffects.initialize();
        MiniumSoundsEvent.initialize();
        MiniumPotions.initialize();
        MiniumEnchantments.initialize();
        PicklingSaltReduceEvent.initialize();
        PendantProtectionEvent.initialize();
        IrisQuartzElytraBoostEvent.initialize();
        LivingEntityTickEvent.initialize();
        MaceAdvancementEvent.initialize();
        ProtectionIntoWallDamage.initialize();
        MiniumSpecialRecipes.initialize();
        ShieldDamageEvent.initialize();
        MiniumBlockEntityTypes.initialize();
        EnchantmentAllowEvents.initialize();
    }
    public static Identifier of(String id){
        return Identifier.of(Minium_me.MOD_ID, id);
    }
}
