package com.github.Minor2CCh.minium_me.event;

import com.github.Minor2CCh.minium_me.block.MiniumBlock;
import com.github.Minor2CCh.minium_me.component.ArmorReinforcedComponent;
import com.github.Minor2CCh.minium_me.component.MiniumModComponent;
import com.github.Minor2CCh.minium_me.component.ToolReinforcedComponent;
import com.github.Minor2CCh.minium_me.util.EntityFunctions;
import com.github.Minor2CCh.minium_me.registry.MiniumTrackDatas;
import com.github.Minor2CCh.minium_me.statuseffect.MiniumStatusEffects;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.minecraft.block.AbstractSignBlock;
import net.minecraft.block.BlockState;
import net.minecraft.component.type.AttributeModifiersComponent;
import net.minecraft.entity.EntityStatuses;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.projectile.WindChargeEntity;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.Items;
import net.minecraft.item.ShieldItem;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.Registries;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.DamageTypeTags;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemActionResult;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.explosion.AdvancedExplosionBehavior;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;


public class ReinforceEvent {
    private static final int INVINCIBLE_TIME = 60;
    private static final int AP_INVINCIBLE_TIME = 40;
    public static void initialize() {
        // ダメージ無効化
        ServerLivingEntityEvents.ALLOW_DAMAGE.register((entity, source, amount) -> {
            if (source == null){
                return true;
            }
            if(source.isOf(DamageTypes.FLY_INTO_WALL) || source.isIn(DamageTypeTags.IS_FALL)){
                if (entity.getMainHandStack().getOrDefault(MiniumModComponent.TOOL_REINFORCED, ToolReinforcedComponent.EMPTY).equals(ToolReinforcedComponent.WIND_SLINGER)){
                    if(entity.hasStatusEffect(StatusEffects.WIND_CHARGED)){
                        return false;
                    }
                }
                if (EntityFunctions.numReinforcedComponent(entity, ArmorReinforcedComponent.WIND_AMULET) > 0){
                    return false;
                }

            }
            if (EntityFunctions.numReinforcedComponent(entity, ArmorReinforcedComponent.ALL_PROTECTION) == 4){
                long prevHitTime = entity.getDataTracker().get(MiniumTrackDatas.getPrevDamageTimeTracker());
                if(entity.getWorld().getTime() - prevHitTime >= 0 && entity.getWorld().getTime() - prevHitTime < AP_INVINCIBLE_TIME){
                    return false;
                }else{
                    entity.getDataTracker().set(MiniumTrackDatas.getPrevDamageTimeTracker(), entity.getWorld().getTime());
                }
            }
            if (source.isIn(DamageTypeTags.IS_FIRE)){
                if (EntityFunctions.numReinforcedComponent(entity, ArmorReinforcedComponent.FIRE_IMMUNE) >= 3){
                    return false;
                }
            }
            if (source.isIn(DamageTypeTags.IS_FREEZING)){
                if (EntityFunctions.numReinforcedComponent(entity, ArmorReinforcedComponent.FROZEN_IMMUNE) >= 2){
                    return false;
                }
            }
            if(source.isIn(DamageTypeTags.BURN_FROM_STEPPING)){
                if (EntityFunctions.numReinforcedComponent(entity, ArmorReinforcedComponent.FROZEN_IMMUNE) >= 2){
                    return false;
                }

            }
            if(source.isIn(DamageTypeTags.IS_PROJECTILE)){
                if (EntityFunctions.numReinforcedComponent(entity, ArmorReinforcedComponent.WIND_AMULET) >= 2){
                    return false;
                }
            }
            if (source.isIn(DamageTypeTags.WITCH_RESISTANT_TO)){
                if (EntityFunctions.numReinforcedComponent(entity, ArmorReinforcedComponent.MAGIC_IMMUNE) >= 3){
                    if (EntityFunctions.numReinforcedComponent(entity, ArmorReinforcedComponent.MAGIC_IMMUNE) >= 4 && amount > 0){
                        entity.heal(Math.min(entity.getMaxHealth() / 2, amount));
                    }
                    return false;
                }
            }
            return true;
        });
        // ダメージ後
        ServerLivingEntityEvents.AFTER_DAMAGE.register((LivingEntity entity, DamageSource source, float amount, float damageTaken, boolean blocked) -> {

            if (source.getAttacker() instanceof LivingEntity attackerEntity){
                if (EntityFunctions.numReinforcedComponent(entity, ArmorReinforcedComponent.FROZEN_IMMUNE) >= 3){
                    if(attackerEntity.canFreeze()){
                        int addFreezeTime = EntityFunctions.numReinforcedComponent(entity, ArmorReinforcedComponent.FROZEN_IMMUNE) == 3 ? 120 : 240;
                        attackerEntity.setFrozenTicks(Math.min(attackerEntity.getFrozenTicks()+addFreezeTime, attackerEntity.getMinFreezeDamageTicks() * 4));
                        if (EntityFunctions.numReinforcedComponent(entity, ArmorReinforcedComponent.FROZEN_IMMUNE) == 4){
                            DamageSource damageSource = attackerEntity.getDamageSources().create(DamageTypes.FREEZE, entity);
                            attackerEntity.damage(damageSource, 5.0F);
                        }
                    }
                }
                if (EntityFunctions.numReinforcedComponent(entity, ArmorReinforcedComponent.WATER_ADAPTION) >= 4){
                    int maxAir = attackerEntity.getMaxAir();
                    int air = attackerEntity.getAir();
                    if (maxAir > 0){
                        attackerEntity.setAir(air > maxAir / 2 ? air / 2 : air - maxAir / 5);
                    }
                    if (!attackerEntity.isTouchingWaterOrRain()){
                        attackerEntity.addStatusEffect(new StatusEffectInstance(MiniumStatusEffects.PICKLING_SALT, 200, 0));
                    }
                }
                lifeSteal(entity, source, amount);

            }
        });
        // とどめを刺すとAFTER_DAMAGEを通らないのでこっちでもやる必要あり
        // ただしALLOW_DEATH経由で蘇生すると両方読み込む可能性あり
        ServerLivingEntityEvents.ALLOW_DEATH.register((LivingEntity entity, DamageSource source, float damageAmount) -> {
            lifeSteal(entity, source, damageAmount);
            return true;
        });
        AttackEntityCallback.EVENT.register((player, world, hand, entity, hitResult) -> {
            ItemStack weaponStack = player.getWeaponStack();
            if(world.getRandom().nextInt(10) == 0){
                if(weaponStack.getOrDefault(MiniumModComponent.TOOL_REINFORCED, ToolReinforcedComponent.EMPTY).equals(ToolReinforcedComponent.WIND_SLINGER) || EntityFunctions.numReinforcedComponent(player, ArmorReinforcedComponent.WIND_AMULET) == 4){
                    addWindChargedTime(player, 1200, 200);
                }
            }
            return ActionResult.PASS;
        });
        UseItemCallback.EVENT.register((player, world, hand) -> {
            ItemStack handStack = player.getStackInHand(hand);
            if(handStack.isEmpty() || player.isSpectator()){
                return TypedActionResult.pass(handStack);
            }
            if(handStack.getOrDefault(MiniumModComponent.TOOL_REINFORCED, ToolReinforcedComponent.EMPTY).equals(ToolReinforcedComponent.WIND_SLINGER)){
                if(!player.getItemCooldownManager().isCoolingDown(handStack.getItem())){
                    if(player.isFallFlying()){
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
                                        new AdvancedExplosionBehavior(
                                                true, false, Optional.of(1.22F), Registries.BLOCK.getEntryList(BlockTags.BLOCKS_WIND_CHARGE_EXPLOSIONS).map(Function.identity())),
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
                        player.getItemCooldownManager().set(handStack.getItem(), 100);
                        handStack.damage(1, player, LivingEntity.getSlotForHand(hand));
                        player.setStackInHand(hand, handStack);
                        return TypedActionResult.success(handStack, world.isClient());

                    }else if(player.hasStatusEffect(StatusEffects.WIND_CHARGED) &&
                            (hand.equals(Hand.OFF_HAND) || !(player.getOffHandStack().getItem() instanceof ShieldItem) || player.isSneaking())){
                        if (!world.isClient()) {
                            WindChargeEntity windChargeEntity = new WindChargeEntity(player, world, player.getPos().getX(), player.getEyePos().getY(), player.getPos().getZ());
                            windChargeEntity.setVelocity(player, player.getPitch(), player.getYaw(), 0.0F, 1.5F, 1.0F);
                            world.spawnEntity(windChargeEntity);
                        }

                        world.playSound(
                                null,
                                player.getX(),
                                player.getY(),
                                player.getZ(),
                                SoundEvents.ENTITY_WIND_CHARGE_THROW,
                                SoundCategory.NEUTRAL,
                                0.5F,
                                0.4F / (world.getRandom().nextFloat() * 0.4F + 0.8F)
                        );
                        player.getItemCooldownManager().set(handStack.getItem(), 10);
                        //player.incrementStat(Stats.USED.getOrCreateStat(Items.WIND_CHARGE));
                        handStack.damage(1, player, LivingEntity.getSlotForHand(hand));
                        player.setStackInHand(hand, handStack);
                        return TypedActionResult.success(handStack, world.isClient());
                    }
                }
            }

            return TypedActionResult.pass(handStack);
        });
        UseBlockCallback.EVENT.register((player, world, hand, hitResult) -> {
            ItemStack handStack = player.getStackInHand(hand);
            if(!player.isSneaking() && handStack.getOrDefault(MiniumModComponent.TOOL_REINFORCED, ToolReinforcedComponent.EMPTY).equals(ToolReinforcedComponent.GENERATE_LIGHT)){
                BlockPos blockPos = hitResult.getBlockPos().offset(hitResult.getSide());
                if((world.isAir(blockPos) || world.isWater(blockPos) || world.getBlockState(blockPos).isReplaceable())) {
                    world.setBlockState(blockPos, MiniumBlock.GLOWSTONE_ENERGY_BLOCK.getDefaultState().with(Properties.WATERLOGGED, world.getFluidState(blockPos).getFluid() == Fluids.WATER));
                    handStack.damage(5, player, LivingEntity.getSlotForHand(hand));
                    BlockSoundGroup blockSoundGroup = world.getBlockState(blockPos).getSoundGroup();
                    world.playSound(
                            player,
                            blockPos,
                            world.getBlockState(blockPos).getSoundGroup().getPlaceSound(),
                            SoundCategory.BLOCKS,
                            (blockSoundGroup.getVolume() + 1.0F) / 2.0F,
                            blockSoundGroup.getPitch() * 0.8F
                    );
                    return ActionResult.success(world.isClient());
                }
            }

            if(handStack.getOrDefault(MiniumModComponent.TOOL_REINFORCED, ToolReinforcedComponent.EMPTY).equals(ToolReinforcedComponent.HONEY_PAINTER)){
                if(!player.isSneaking() && (hand == Hand.OFF_HAND || !(player.getOffHandStack().getItem() instanceof ShieldItem))){
                    BlockState blockState = world.getBlockState(hitResult.getBlockPos());
                    ItemStack honeycombStack = new ItemStack(Items.HONEYCOMB, 1);
                    player.setStackInHand(hand, honeycombStack);
                    if(!blockState.hasBlockEntity() || blockState.getBlock() instanceof AbstractSignBlock){
                        ItemActionResult itemActionResult = blockState.onUseWithItem(player.getStackInHand(hand), world, player, hand, hitResult);
                        if(itemActionResult.isAccepted()){
                            int useCount = honeycombStack.getItem() != Items.HONEYCOMB ? 1 : 1 - honeycombStack.getCount();
                            handStack.damage(useCount * 10, player, LivingEntity.getSlotForHand(hand));
                            player.setStackInHand(hand, handStack);
                            return itemActionResult.toActionResult();
                        }
                    }
                    ItemUsageContext context = new ItemUsageContext(world, player, hand, honeycombStack, hitResult);
                    ActionResult result = Items.HONEYCOMB.useOnBlock(context);
                    int useCount = honeycombStack.getItem() != Items.HONEYCOMB ? 1 : 1 - honeycombStack.getCount();
                    handStack.damage(useCount * 10, player, LivingEntity.getSlotForHand(hand));
                    player.setStackInHand(hand, handStack);
                    return result;

                }



            }
            return ActionResult.PASS;

        });
        // コンポーネント反映
        // トーテム効果
        ServerLivingEntityEvents.ALLOW_DEATH.register((entity, source, amount) -> {
            if (source.isIn(DamageTypeTags.BYPASSES_INVULNERABILITY)) {
                return true;
            }
            for(EquipmentSlot slot : EntityFunctions.EQUIPMENT_SLOT_LIST){
                ItemStack stack = entity.getEquippedStack(slot);
                ArmorReinforcedComponent component = stack.get(MiniumModComponent.ARMOR_REINFORCED);
                if (Objects.equals(component, ArmorReinforcedComponent.UNDYING)){
                    AttributeModifiersComponent attr = component.attributeFunction.apply(slot);
                    removeAttributes(entity, attr);
                    stack.remove(MiniumModComponent.ARMOR_REINFORCED);

                    if (!entity.isAlive()){
                        entity.setHealth(1.0F);
                    }
                    if (entity.timeUntilRegen < INVINCIBLE_TIME){
                        entity.timeUntilRegen = INVINCIBLE_TIME;
                    }
                    entity.addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, INVINCIBLE_TIME, 3));
                    entity.addStatusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, INVINCIBLE_TIME, 4));
                    entity.addStatusEffect(new StatusEffectInstance(StatusEffects.FIRE_RESISTANCE, INVINCIBLE_TIME * 4, 0));
                    entity.getWorld().sendEntityStatus(entity, EntityStatuses.USE_TOTEM_OF_UNDYING);
                    return false;
                }
            }
            return true;
        });
        // コンポーネント反映
        ServerEntityEvents.EQUIPMENT_CHANGE.register((livingEntity, slot, oldStack, newStack) -> {
            if(slot.isArmorSlot()){
                // --- 1. oldStack の補強効果を REMOVE ---
                ArmorReinforcedComponent oldComp = oldStack.get(MiniumModComponent.ARMOR_REINFORCED);
                if (oldComp != null) {
                    AttributeModifiersComponent attr = oldComp.attributeFunction.apply(slot);
                    removeAttributes(livingEntity, attr);
                }

                // --- 2. newStack の補強効果を ADD ---
                ArmorReinforcedComponent newComp = newStack.get(MiniumModComponent.ARMOR_REINFORCED);
                if (newComp != null) {
                    AttributeModifiersComponent attr = newComp.attributeFunction.apply(slot);
                    addAttributes(livingEntity, attr);
                }
            }else if(slot == EquipmentSlot.MAINHAND){
                // --- 1. oldStack の補強効果を REMOVE ---
                ToolReinforcedComponent oldComp = oldStack.get(MiniumModComponent.TOOL_REINFORCED);
                if (oldComp != null) {
                    AttributeModifiersComponent attr = oldComp.attributeFunction.apply(slot);
                    removeAttributes(livingEntity, attr);
                }

                // --- 2. newStack の補強効果を ADD ---
                ToolReinforcedComponent newComp = newStack.get(MiniumModComponent.TOOL_REINFORCED);
                if (newComp != null) {
                    AttributeModifiersComponent attr = newComp.attributeFunction.apply(slot);
                    addAttributes(livingEntity, attr);
                }

            }
        });
    }
    private static void addAttributes(LivingEntity entity, AttributeModifiersComponent component) {
        for (var entry : component.modifiers()) {
            EntityAttributeInstance instance = entity.getAttributeInstance(entry.attribute());
            if (instance != null) {
                if(!instance.hasModifier(entry.modifier().id())){// クラッシュ対策
                    instance.addTemporaryModifier(entry.modifier());
                }
            }
        }
    }
    private static void removeAttributes(LivingEntity entity, AttributeModifiersComponent component) {
        for (var entry : component.modifiers()) {
            EntityAttributeInstance instance = entity.getAttributeInstance(entry.attribute());
            if (instance != null) {
                instance.removeModifier(entry.modifier().id());
            }
        }
    }
    private static void addWindChargedTime(LivingEntity entity, @SuppressWarnings("all")int maxTime, @SuppressWarnings("all")int addTime) {
        if(!entity.hasStatusEffect(StatusEffects.WIND_CHARGED)){
            entity.addStatusEffect(new StatusEffectInstance(StatusEffects.WIND_CHARGED, addTime, 0));
        }else{
            StatusEffectInstance effect = entity.getStatusEffect(StatusEffects.WIND_CHARGED);
            if(effect != null){
                entity.addStatusEffect(new StatusEffectInstance(StatusEffects.WIND_CHARGED, Math.min(effect.getDuration()+addTime, maxTime), 0));
            }
        }
    }
    private static void lifeSteal(LivingEntity entity, DamageSource source, float amount){
        ItemStack attackStack = source.getWeaponStack();
        if(amount <= 0 || attackStack == null || attackStack.isEmpty() || !attackStack.getOrDefault(MiniumModComponent.TOOL_REINFORCED, ToolReinforcedComponent.EMPTY).equals(ToolReinforcedComponent.LIFE_STEAL)){
            return;
        }
        if(source.getAttacker() instanceof LivingEntity attackerEntity){
            attackerEntity.heal(Math.min(amount / 2, entity.getMaxHealth() / 2));
        }

    }
}
