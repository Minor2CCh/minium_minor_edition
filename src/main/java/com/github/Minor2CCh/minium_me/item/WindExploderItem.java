package com.github.Minor2CCh.minium_me.item;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.WindChargeEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.Registries;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.explosion.AdvancedExplosionBehavior;
import net.minecraft.world.explosion.ExplosionBehavior;

import java.util.Optional;
import java.util.function.Function;

public class WindExploderItem extends Item implements HasCustomTooltip{
    private static final ExplosionBehavior EXPLOSION_BEHAVIOR = new AdvancedExplosionBehavior(
            true, false, Optional.of(1.22F), Registries.BLOCK.getEntryList(BlockTags.BLOCKS_WIND_CHARGE_EXPLOSIONS).map(Function.identity()));
    public WindExploderItem(Settings settings) {
        super(settings);
    }
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);

        if(!world.isClient()){
            WindChargeEntity windChargeEntity = new WindChargeEntity(world, user.getX(), user.getY(), user.getZ(), new Vec3d(0, -0.125, 0));
            Vec3d vec3d = user.getPos();
            Vec3d userVec = user.getVelocity();
            windChargeEntity.setVelocity(user, user.getPitch(), user.getYaw(), 0.0F, 0.75F, 1.0F);
            windChargeEntity.refreshPositionAndAngles(vec3d.x, vec3d.y, vec3d.z, user.getYaw(), user.getPitch());
            Vec3d vel3d = windChargeEntity.getVelocity();
            Vec3d windPos = user.isFallFlying()
            ? new Vec3d(user.getX()-vel3d.x+userVec.x, user.getY()+0.125-vel3d.y*1.25+userVec.y*1.25, user.getZ()-vel3d.z+userVec.z)
            : new Vec3d(user.getX()+userVec.x, user.getY(), user.getZ()+userVec.z);
            int loopMax = user.isFallFlying() ? 2 : 1;
            for(int i=0;i<loopMax;i++){
            world.createExplosion(
                    windChargeEntity,
                    null,
                    EXPLOSION_BEHAVIOR,
                    windPos.getX(),
                    windPos.getY(),
                    windPos.getZ(),
                    1.4F,
                    false,
                    World.ExplosionSourceType.TRIGGER,
                    ParticleTypes.GUST_EMITTER_SMALL,
                    ParticleTypes.GUST_EMITTER_LARGE,
                    SoundEvents.ENTITY_WIND_CHARGE_WIND_BURST
            );
            }
        }
        itemStack.damage(1, user, LivingEntity.getSlotForHand(hand));
        if(itemStack.isOf(MiniumItem.WIND_EXPLODER)){
            user.getItemCooldownManager().set(this, 20);
        }else
        if(itemStack.isOf(MiniumItem.ADVANCED_WIND_EXPLODER)){
            user.getItemCooldownManager().set(this, 5);
        }

        return TypedActionResult.success(itemStack, world.isClient());//itemStackの部分を変えるとアイテムがそれに化ける
    }
}
