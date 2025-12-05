package com.github.Minor2CCh.minium_me.event;

import com.github.Minor2CCh.minium_me.Minium_me;
import com.github.Minor2CCh.minium_me.item.MiniumItem;
import com.github.Minor2CCh.minium_me.payload.IrisQuartzElytraBoostPayLoad;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.WindChargeEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.Registries;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.explosion.AdvancedExplosionBehavior;
import net.minecraft.world.explosion.ExplosionBehavior;

import java.util.Optional;
import java.util.function.Function;

public class IrisQuartzElytraBoostEvent {
    public static final int BOOST_COOL_TIME = 100;
    private static final float REQUIRE_COOL_PROGRESS = 0.5F;
    private static final ExplosionBehavior EXPLOSION_BEHAVIOR = new AdvancedExplosionBehavior(
            true, false, Optional.of(1.22F), Registries.BLOCK.getEntryList(BlockTags.BLOCKS_WIND_CHARGE_EXPLOSIONS).map(Function.identity()));
    public static void initialize(){
        PayloadTypeRegistry.playC2S().register(IrisQuartzElytraBoostPayLoad.ID, IrisQuartzElytraBoostPayLoad.CODEC);
        ServerPlayNetworking.registerGlobalReceiver(IrisQuartzElytraBoostPayLoad.ID, (payload, context) -> {
            ServerPlayerEntity player = context.player();
            ServerWorld world = player.getServerWorld();
            ItemStack beltStack = getWindExploderItem(player);
            if(beltStack != null){
                if(!world.isClient()){
                    WindChargeEntity windChargeEntity = new WindChargeEntity(world, player.getX(), player.getY(), player.getZ(), new Vec3d(0, -0.125, 0));
                    Vec3d vec3d = player.getPos();
                    Vec3d userVec = player.getVelocity();
                    windChargeEntity.setVelocity(player, player.getPitch(), player.getYaw(), 0.0F, 0.75F, 1.0F);
                    windChargeEntity.refreshPositionAndAngles(vec3d.x, vec3d.y, vec3d.z, player.getYaw(), player.getPitch());
                    Vec3d vel3d = windChargeEntity.getVelocity();
                    Vec3d windPos = player.isFallFlying()
                            ? new Vec3d(player.getX()-vel3d.x+userVec.x, player.getY()+0.125-vel3d.y*1.25+userVec.y*1.25, player.getZ()-vel3d.z+userVec.z)
                            : new Vec3d(player.getX()+userVec.x, player.getY(), player.getZ()+userVec.z);
                    int loopMax = player.isFallFlying() ? 2 : 1;
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
                beltStack.damage(1, player.getServerWorld(), player, ((item) -> {}));
                if(beltStack.isOf(MiniumItem.WIND_EXPLODER)){
                    player.getItemCooldownManager().set(MiniumItem.WIND_EXPLODER, 20);
                }else if(beltStack.isOf(MiniumItem.ADVANCED_WIND_EXPLODER)){
                    player.getItemCooldownManager().set(MiniumItem.ADVANCED_WIND_EXPLODER, 5);
                }
                return;

            }

            ItemStack capeStack = Minium_me.ACCESSORY_PLATFORM.getIrisQuartzElytraStack(player);
            if(capeStack == null){
                return;
            }
            if(player.isFallFlying() && player.getItemCooldownManager().getCooldownProgress(capeStack.getItem(), 0.0f) < REQUIRE_COOL_PROGRESS) {
                WindChargeEntity windChargeEntity = new WindChargeEntity(world, player.getX(), player.getY(), player.getZ(), new Vec3d(0, -0.125, 0));
                Vec3d vec3d = player.getPos();
                Vec3d userVec = player.getVelocity();
                windChargeEntity.setVelocity(player, player.getPitch(), player.getYaw(), 0.0F, 0.75F, 1.0F);
                windChargeEntity.refreshPositionAndAngles(vec3d.x, vec3d.y, vec3d.z, player.getYaw(), player.getPitch());
                Vec3d vel3d = windChargeEntity.getVelocity();
                Vec3d windPos = player.isFallFlying()
                        ? new Vec3d(player.getX()-vel3d.x+userVec.x, player.getY()+0.125-vel3d.y*1.25+userVec.y*1.25, player.getZ()-vel3d.z+userVec.z)
                        : new Vec3d(player.getX()+userVec.x, player.getY(), player.getZ()+userVec.z);
                //System.out.println(player.getItemCooldownManager().getCooldownProgress(capeStack.getItem(), 0.0f));
                for(int i=0;i<2;i++){
                    world.createExplosion(
                            windChargeEntity,
                            null,
                            EXPLOSION_BEHAVIOR,
                            windPos.getX(),
                            windPos.getY(),
                            windPos.getZ(),
                            1.4F * (1-getCoolTimePow(player, capeStack.getItem(), 0.5F)),
                            false,
                            World.ExplosionSourceType.TRIGGER,
                            ParticleTypes.GUST_EMITTER_SMALL,
                            ParticleTypes.GUST_EMITTER_LARGE,
                            SoundEvents.ENTITY_WIND_CHARGE_WIND_BURST
                    );
                }
                player.getItemCooldownManager().set(capeStack.getItem(), BOOST_COOL_TIME);
            }
        });
    }
    private static float getCoolTimePow(PlayerEntity player, Item item, @SuppressWarnings("all")float pow){
        if(!player.getItemCooldownManager().isCoolingDown(item))
            return 0;
        return (float) Math.pow(player.getItemCooldownManager().getCooldownProgress(item, 0.0f), pow);
    }
    public static ItemStack getWindExploderItem(PlayerEntity player){
        if(!Minium_me.ACCESSORY_PLATFORM.enableTrinkets()){
            return null;
        }
        ItemStack weStack = Minium_me.ACCESSORY_PLATFORM.getTrinketsStackBelt(player, MiniumItem.WIND_EXPLODER);
        if(weStack != null && !player.getItemCooldownManager().isCoolingDown(MiniumItem.WIND_EXPLODER)){
            return weStack;
        }
        ItemStack aweStack = Minium_me.ACCESSORY_PLATFORM.getTrinketsStackBelt(player, MiniumItem.ADVANCED_WIND_EXPLODER);
        if(aweStack != null && !player.getItemCooldownManager().isCoolingDown(MiniumItem.ADVANCED_WIND_EXPLODER)){
            return aweStack;
        }
        return null;

    }

}
