package com.github.Minor2CCh.minium_me.item;

import com.github.Minor2CCh.minium_me.entity.EntityRaycastUtil;
import com.github.Minor2CCh.minium_me.mixin.MaceItemInvoker;
import com.github.Minor2CCh.minium_me.util.HasCustomTooltip;
import net.minecraft.block.BlockState;
import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.component.type.AttributeModifiersComponent;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.MaceItem;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.network.packet.s2c.play.EntityVelocityUpdateS2CPacket;
import net.minecraft.particle.BlockStateParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.Registries;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.*;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.explosion.AdvancedExplosionBehavior;
import net.minecraft.world.explosion.ExplosionBehavior;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;


public class IrisQuartzMaceItem extends MaceItem implements HasCustomTooltip {
    public IrisQuartzMaceItem(Settings settings) {
        super(settings);
    }
    private static final ExplosionBehavior EXPLOSION_BEHAVIOR = new AdvancedExplosionBehavior(
            true, false, Optional.of(1.22F*3), Registries.BLOCK.getEntryList(BlockTags.BLOCKS_WIND_CHARGE_EXPLOSIONS).map(Function.identity()));
    public static AttributeModifiersComponent createAttributeModifiers() {
        return AttributeModifiersComponent.builder()
                .add(
                        EntityAttributes.GENERIC_ATTACK_DAMAGE,
                        new EntityAttributeModifier(BASE_ATTACK_DAMAGE_MODIFIER_ID, 7.0, EntityAttributeModifier.Operation.ADD_VALUE),
                        AttributeModifierSlot.MAINHAND
                )
                .add(
                        EntityAttributes.GENERIC_ATTACK_SPEED,
                        new EntityAttributeModifier(BASE_ATTACK_SPEED_MODIFIER_ID, -2.4F, EntityAttributeModifier.Operation.ADD_VALUE),
                        AttributeModifierSlot.MAINHAND
                )
                .add(
                        EntityAttributes.PLAYER_BLOCK_INTERACTION_RANGE,
                        new EntityAttributeModifier(Identifier.ofVanilla("player.block_interaction_range"), 1.0, EntityAttributeModifier.Operation.ADD_VALUE),
                        AttributeModifierSlot.MAINHAND
                )
                .add(
                        EntityAttributes.PLAYER_ENTITY_INTERACTION_RANGE,
                        new EntityAttributeModifier(Identifier.ofVanilla("player.entity_interaction_range"), 1.0, EntityAttributeModifier.Operation.ADD_VALUE),
                        AttributeModifierSlot.MAINHAND
                )
                .build();
    }

    @Override
    public int getMaxUseTime(ItemStack stack, LivingEntity user) {
        return 72000;
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user){
        this.useFinished(stack, world, user, 0);
        return stack;
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.SPEAR;
    }
    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);
        user.setCurrentHand(hand);
        return TypedActionResult.consume(stack);
    }
    @Override
    public void onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {
        this.useFinished(stack, world, user, remainingUseTicks);
    }
    private void useFinished(ItemStack stack, World world, LivingEntity user, int remainingUseTicks){
        int usingTime = this.getMaxUseTime(stack, user)-remainingUseTicks;
        if(usingTime >= 15){
            if(user instanceof PlayerEntity player){
                double blockReach = player.getAttributeValue(EntityAttributes.PLAYER_BLOCK_INTERACTION_RANGE);
                double entityReach = player.getAttributeValue(EntityAttributes.PLAYER_ENTITY_INTERACTION_RANGE);
                HitResult result = EntityRaycastUtil.raycastIncludingEntities(player, blockReach, entityReach, 0);
                if (result.getType() == HitResult.Type.BLOCK) {
                    if (result instanceof BlockHitResult blockHitResult) {
                        Vec3d pos = blockHitResult.getPos();
                        BlockState state = player.getWorld().getBlockState(blockHitResult.getBlockPos());
                        if (!world.isClient()){
                            this.windExplosion(world, player, stack, pos, state);
                        } else {
                            if(player.getMainHandStack() == stack){
                                stack.damage(1, player, EquipmentSlot.MAINHAND);
                                player.swingHand(Hand.MAIN_HAND);
                            }else if(player.getOffHandStack() == stack){
                                stack.damage(1, player, EquipmentSlot.OFFHAND);
                                player.swingHand(Hand.OFF_HAND);
                            }
                        }
                    }

                }else if(result.getType() == HitResult.Type.ENTITY){
                    if(result instanceof EntityHitResult entityHitResult){
                        if(!world.isClient()){
                            double attack = player.getAttributeValue(EntityAttributes.GENERIC_ATTACK_DAMAGE);
                            DamageSource damageSource = player.getDamageSources().create(DamageTypes.PLAYER_ATTACK, player);

                            float bonusDamage = getBonusAttackDamage(entityHitResult.getEntity(), (float) attack, damageSource);
                            if(bonusDamage > 0){
                                ServerPlayerEntity serverPlayer = (ServerPlayerEntity) player;
                                serverPlayer.setIgnoreFallDamageFromCurrentExplosion(true);
                                serverPlayer.setVelocity(serverPlayer.getVelocity().withAxis(Direction.Axis.Y, 0.01F));
                                serverPlayer.networkHandler.sendPacket(new EntityVelocityUpdateS2CPacket(serverPlayer));
                            }
                            entityHitResult.getEntity().damage(damageSource, (float) (attack+bonusDamage));
                            Vec3d knockBackVel = new Vec3d(-Math.sin(player.getYaw() * (Math.PI / 180F)), 1, Math.cos(player.getYaw() * (Math.PI / 180F)));

                            entityHitResult.getEntity().addVelocity(knockBackVel.multiply(5, 1, 5));
                            Vec3d pos = entityHitResult.getPos();

                            BlockPos blockPos = new BlockPos((int)entityHitResult.getEntity().getPos().x, (int)entityHitResult.getEntity().getPos().y-1, (int)entityHitResult.getEntity().getPos().z);
                            BlockState state = player.getWorld().getBlockState(blockPos);
                            this.windExplosion(world, player, stack, pos, state);
                        } else {
                            if(player.getMainHandStack() == stack){
                                stack.damage(1, player, EquipmentSlot.MAINHAND);
                                player.swingHand(Hand.MAIN_HAND);
                            }else if(player.getOffHandStack() == stack){
                                stack.damage(1, player, EquipmentSlot.OFFHAND);
                                player.swingHand(Hand.OFF_HAND);
                            }
                        }
                    }
                }

            }
        }
        //System.out.println("最大使用時間："+usingTime);

    }
    private void windExplosion(World world, PlayerEntity player, ItemStack stack, Vec3d pos, BlockState sparkState){
        world.createExplosion(
                player,
                null,
                EXPLOSION_BEHAVIOR,
                pos.getX(),
                pos.getY(),
                pos.getZ(),
                4.0F,
                false,
                World.ExplosionSourceType.TRIGGER,
                ParticleTypes.GUST_EMITTER_SMALL,
                ParticleTypes.GUST_EMITTER_LARGE,
                SoundEvents.ENTITY_WIND_CHARGE_WIND_BURST
        );
        ServerWorld serverWorld = (ServerWorld) world;
        serverWorld.spawnParticles(
                new BlockStateParticleEffect(ParticleTypes.BLOCK, sparkState),
                pos.getX(),
                pos.getY(),
                pos.getZ(),
                600,    // パーティクル数
                3.0, 0.5, 3.0, // 拡散
                0.15   // 速度
        );

        world.playSound(null, pos.getX(), pos.getY(), pos.getZ(), SoundEvents.ITEM_MACE_SMASH_GROUND_HEAVY, player.getSoundCategory(), 1.0F, 1.0F);
        if(player.getMainHandStack() == stack){
            stack.damage(1, player, EquipmentSlot.MAINHAND);
            player.swingHand(Hand.MAIN_HAND, true);
        }else if(player.getOffHandStack() == stack){
            stack.damage(1, player, EquipmentSlot.OFFHAND);
            player.swingHand(Hand.OFF_HAND, true);
        }
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (attacker instanceof ServerPlayerEntity serverPlayerEntity && shouldDealAdditionalDamage(serverPlayerEntity)) {
            ServerWorld serverWorld = (ServerWorld)attacker.getWorld();
            if (serverPlayerEntity.shouldIgnoreFallDamageFromCurrentExplosion() && serverPlayerEntity.currentExplosionImpactPos != null) {
                if (serverPlayerEntity.currentExplosionImpactPos.y > serverPlayerEntity.getPos().y) {
                    serverPlayerEntity.currentExplosionImpactPos = serverPlayerEntity.getPos();
                }
            } else {
                serverPlayerEntity.currentExplosionImpactPos = serverPlayerEntity.getPos();
            }

            serverPlayerEntity.setIgnoreFallDamageFromCurrentExplosion(true);
            serverPlayerEntity.setVelocity(serverPlayerEntity.getVelocity().withAxis(Direction.Axis.Y, 0.01F));
            serverPlayerEntity.networkHandler.sendPacket(new EntityVelocityUpdateS2CPacket(serverPlayerEntity));
            if (target.isOnGround()) {
                serverPlayerEntity.setSpawnExtraParticlesOnFall(true);
                SoundEvent soundEvent = getFallDistanceCalc(serverPlayerEntity) > 5.0F ? SoundEvents.ITEM_MACE_SMASH_GROUND_HEAVY : SoundEvents.ITEM_MACE_SMASH_GROUND;
                serverWorld.playSound(
                        null, serverPlayerEntity.getX(), serverPlayerEntity.getY(), serverPlayerEntity.getZ(), soundEvent, serverPlayerEntity.getSoundCategory(), 1.0F, 1.0F
                );
            } else {
                serverWorld.playSound(
                        null,
                        serverPlayerEntity.getX(),
                        serverPlayerEntity.getY(),
                        serverPlayerEntity.getZ(),
                        SoundEvents.ITEM_MACE_SMASH_AIR,
                        serverPlayerEntity.getSoundCategory(),
                        1.0F,
                        1.0F
                );
            }

            MaceItemInvoker.invokeKnockbackNearbyEntities(serverWorld, serverPlayerEntity, target);
        }

        return true;
    }
    @Override
    public void postDamageEntity(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        stack.damage(1, attacker, EquipmentSlot.MAINHAND);
        if (shouldDealAdditionalDamage(attacker)) {
            attacker.onLanding();
        }
    }
    public static boolean shouldDealAdditionalDamage(LivingEntity attacker) {
        return getFallDistanceCalc(attacker) > 1.5F;
    }
    @Override
    public float getBonusAttackDamage(Entity target, float baseAttackDamage, DamageSource damageSource) {
        if (damageSource.getSource() instanceof LivingEntity livingEntity) {
            if (!shouldDealAdditionalDamage(livingEntity)) {
                return 0.0F;
            } else {
                float f = 3.0F;
                float g = 8.0F;
                float h = getFallDistanceCalc(livingEntity);
                float i;
                if (h <= f) {
                    i = 4.0F * h;
                } else if (h <= g) {
                    i = 12.0F + 2.0F * (h - 3.0F);
                } else {
                    i = 22.0F + h - 8.0F;
                }

                return livingEntity.getWorld() instanceof ServerWorld serverWorld
                        ? i + EnchantmentHelper.getSmashDamagePerFallenBlock(serverWorld, livingEntity.getWeaponStack(), target, damageSource, 0.0F) * h
                        : i;
            }
        } else {
            return 0.0F;
        }
    }
    private static float getFallDistanceCalc(LivingEntity livingEntity){
        float flyVelocity = (float)livingEntity.getVelocity().length() * 5;
        if(livingEntity.isFallFlying() && flyVelocity > livingEntity.fallDistance){
            return (float)livingEntity.getVelocity().length();
        }
        return livingEntity.fallDistance;
    }
    @Override
    public boolean canRepair(ItemStack stack, ItemStack ingredient) {
        return ingredient.isIn(MiniumItemTag.IRIS_QUARTZ_INGOT);
    }
    @Override
    public void customTooltip(ItemStack itemStack, Item.TooltipContext context, List<Text> tooltip, TooltipType type, boolean hasShiftDown) {
        if (hasShiftDown) {
            for(int i=0;i<2;i++){
                tooltip.add(Text.translatable(MiniumItem.IRIS_QUARTZ_MACE.getTranslationKey()+".desc."+i).formatted(Formatting.WHITE));
            }
        } else {
            tooltip.add(Text.translatable("item.minium_me.hide_tooltip.desc"));
        }

    }
}
