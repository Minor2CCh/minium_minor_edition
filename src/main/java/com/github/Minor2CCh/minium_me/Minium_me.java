package com.github.Minor2CCh.minium_me;

import com.github.Minor2CCh.minium_me.block.MiniumBlock;
import com.github.Minor2CCh.minium_me.component.MiniumModComponent;
import com.github.Minor2CCh.minium_me.damage_type.MiniumDamageType;
import com.github.Minor2CCh.minium_me.entity.MiniumEntityType;
import com.github.Minor2CCh.minium_me.item.*;
import com.github.Minor2CCh.minium_me.particle.MiniumParticle;
import com.github.Minor2CCh.minium_me.statuseffect.MiniumStatusEffects;
import com.github.Minor2CCh.minium_me.worldgen.MiniumOres;
import net.fabricmc.api.ModInitializer;
//import net.minecraft.item.*;

public class Minium_me implements ModInitializer {

    public static final String MOD_ID = "minium_me";

    @Override
    public void onInitialize() {
        MiniumItem.initialize();
        MiniumBlock.initialize();
        MiniumOres.initialize();
        MiniumItemGroup.initialize();
        AddLootTable.initialize();
        MiniumModComponent.initialize();
        MiniumEntityType.initialize();
        MiniumParticle.initialize();
        MiniumDamageType.initialize();
        MiniumStatusEffects.initialize();
    }
}
