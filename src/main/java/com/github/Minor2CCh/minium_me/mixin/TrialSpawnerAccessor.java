package com.github.Minor2CCh.minium_me.mixin;

import net.minecraft.block.spawner.TrialSpawnerData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(TrialSpawnerData.class)
public interface TrialSpawnerAccessor {
    @Accessor("cooldownEnd")
    long cooldownEnd();
}
