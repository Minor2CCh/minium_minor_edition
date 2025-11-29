package com.github.Minor2CCh.minium_me.block;

import com.github.Minor2CCh.minium_me.registry.MiniumDamageTypes;
import net.fabricmc.fabric.api.entity.FakePlayer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class AdvancedGrindingBlock extends GrindingBlock{
    public AdvancedGrindingBlock(Settings settings) {
        super(settings);
        GRIND_DAMAGE = 15.0F;
    }
    @Override
    protected void damageEntity(World world, LivingEntity livingEntity, BlockPos pos) {
        FakePlayer fakePlayer = FakePlayer.get((ServerWorld) world);
        fakePlayer.setPos(pos.getX()+0.5, pos.getY(), pos.getZ()+0.5);
        DamageSource damageSource = fakePlayer.getDamageSources().create(MiniumDamageTypes.MINIUM_GRINDING, fakePlayer);
        livingEntity.damage(damageSource, GRIND_DAMAGE);
    }
}
