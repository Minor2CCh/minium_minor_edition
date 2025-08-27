package com.github.Minor2CCh.minium_me.entity;

import com.github.Minor2CCh.minium_me.block.MiniumBlock;
import com.github.Minor2CCh.minium_me.component.MiniumModComponent;
import com.github.Minor2CCh.minium_me.enchantment.MiniumEnchantments;
import com.github.Minor2CCh.minium_me.particle.MiniumParticle;
import com.github.Minor2CCh.minium_me.sound.MiniumSoundsEvent;
import com.github.Minor2CCh.minium_me.statuseffect.MiniumStatusEffects;
import com.google.common.base.MoreObjects;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalEntityTypeTags;
import net.minecraft.block.*;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.EntityTypeTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.property.Properties;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

import java.util.HashMap;
import java.util.Objects;
import java.util.Random;
import java.util.UUID;


public class EnergyBulletEntity extends ProjectileEntity {
    private int existTime = 0;
    private String EnergyType = MiniumModComponent.ENERGY_EMPTY;
    public static int color = 0xFFFFFF;
    public static int particleColor = 0xFFFFFF;
    //public int renderColor = 0xFFFFFF;
    public ItemStack attackStack;
    private static final HashMap<UUID, Integer> clientRenderColor = new HashMap<>();
    public EnergyBulletEntity(EntityType<? extends EnergyBulletEntity> entityType, World world) {
        super(entityType, world);
        this.noClip = true;

    }


    public EnergyBulletEntity(World world, LivingEntity owner, ItemStack itemStack) {
        this(MiniumEntityType.ENERGY_BULLET, world);
        this.setOwner(owner);
        Vec3d vec3d = owner.getBoundingBox().getCenter();
        this.refreshPositionAndAngles(vec3d.x, vec3d.y, vec3d.z, this.getYaw(), this.getPitch());
        setYaw(owner.getYaw());
        setPitch(owner.getPitch());
        Vec3d vel3d = this.getVelocity();
        this.setPosition(this.getX() + vel3d.x * 2, owner.getEyeY() - 0.1F + vel3d.y * 2, this.getZ() + vel3d.z * 2);
        MiniumModComponent.EnergyComponent EComp = itemStack.get(MiniumModComponent.REMAIN_ENERGY);
        EnergyType = EComp != null ? EComp.type() : MiniumModComponent.ENERGY_EMPTY;
        attackStack = itemStack;
        color = MiniumModComponent.getEnergyColor(EnergyType);
            //renderColor = color;
        //System.out.println(this.getUuidAsString());
        clientRenderColor.put(uuid, color);

    }
    /*
    public EnergyBulletEntity(World world, LivingEntity owner, ItemStack stack) {
        super(EntityType.ARROW, owner, world, stack);
    }*/
    public static int getRenderColor(UUID uuid){
        return clientRenderColor.getOrDefault(uuid, 0xFFFFFF);
    }
    public static void removeRenderColor(UUID uuid){
        clientRenderColor.remove(uuid);
    }
    @Override
    protected void onCollision(HitResult hitResult) {
        super.onCollision(hitResult);
        this.destroy();
    }
    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        super.onEntityHit(entityHitResult);
        Entity entity = entityHitResult.getEntity();
        Entity entity2 = this.getOwner();
        LivingEntity livingEntity = entity2 instanceof LivingEntity ? (LivingEntity)entity2 : null;
        Objects.requireNonNull(livingEntity);
        DamageSource damageSource;
        final float baseDamage = 10.0F;
        float resultDamage;
        damageSource = new DamageSource(
                this.getWorld().getRegistryManager()
                        .get(RegistryKeys.DAMAGE_TYPE)
                        .entryOf(MiniumModComponent.getEnergyDamageType(EnergyType))
                ,livingEntity);
        resultDamage = baseDamage * MiniumModComponent.getEnergyMulDamage(EnergyType);
        if(EnergyType.equals(MiniumModComponent.ENERGY_GOLD)){
            if(entity.getType().isIn(EntityTypeTags.SENSITIVE_TO_SMITE)){
                resultDamage *= 1.5F;
            }
        }else if(EnergyType.equals(MiniumModComponent.ENERGY_DIAMOND)){
            if(entity.getType().isIn(ConventionalEntityTypeTags.BOSSES)){
                resultDamage *= 1.75F;
            }
        }else if(EnergyType.equals(MiniumModComponent.ENERGY_NICKEL)){
            if(entity.getType().isFireImmune()){
                resultDamage *= 1.75F;
            }
        }else if(EnergyType.equals(MiniumModComponent.ENERGY_SILVER)){
            if(!entity.getType().isIn(EntityTypeTags.SENSITIVE_TO_SMITE)){
                resultDamage *= 1.75F;
            }
        }else if(EnergyType.equals(MiniumModComponent.ENERGY_TIN)){
            if(entity.isTouchingWaterOrRain()){
                resultDamage *= 1.75F;
            }
        }else if(EnergyType.equals(MiniumModComponent.ENERGY_URANIUM)){
            if(entity.getType().isIn(EntityTypeTags.ILLAGER)
                    || entity.getType().equals(EntityType.VILLAGER)
                    || entity.getType().equals(EntityType.WANDERING_TRADER)
                    || entity.getType().equals(EntityType.WITCH)
                    || entity.getType().equals(EntityType.PLAYER)){
                resultDamage *= 5.0F;
            }
        }else if(EnergyType.equals(MiniumModComponent.ENERGY_STEEL)){
            resultDamage = baseDamage * (entity.isTouchingWaterOrRain() ? 0.4F : (entity.isOnFire() ? 2.0F : 1.2F));

        }else if(EnergyType.equals(MiniumModComponent.ENERGY_REFINED_GLOWSTONE)) {
            if (entity.getType().isIn(EntityTypeTags.SENSITIVE_TO_SMITE)) {
                resultDamage *= 2.0F;
            }
        }else if(EnergyType.equals(MiniumModComponent.ENERGY_RUBY)) {
            if (entity instanceof LivingEntity livingEntity1 && !livingEntity1.canFreeze()) {
                resultDamage *= 2.0F;
            }
        }else if(EnergyType.equals(MiniumModComponent.ENERGY_SAPPHIRE)) {
            if (entity instanceof LivingEntity livingEntity1 && livingEntity1.canBreatheInWater() && !livingEntity1.getType().isIn(EntityTypeTags.UNDEAD)) {
                resultDamage *= 2.0F;
            }
        }
        if(EnergyType.equals(MiniumModComponent.ENERGY_REDSTONE)) {
            entity.timeUntilRegen -= Math.min(entity.timeUntilRegen, 6);
        }else if(EnergyType.equals(MiniumModComponent.ENERGY_ALUMINIUM)) {
            entity.timeUntilRegen -= Math.min(entity.timeUntilRegen, 10);
        }



        boolean bl = false;
        int lv = MiniumEnchantments.getEnchantmentLevel(this, attackStack, MiniumEnchantments.ENERGY_BOOST);
        if(lv > 0)
            resultDamage *= (1 + 0.25F * lv);
        //resultDamage = EnchantmentHelper.getDamage((ServerWorld) this.getWorld(), attackStack, entity, damageSource, resultDamage);
        if(!(entity instanceof TameableEntity tameableEntity) || !tameableEntity.isOwner(livingEntity)){
            if(Objects.equals(EnergyType, MiniumModComponent.ENERGY_STEEL)){
                entity.setFireTicks(120);
            }
            bl = entity.damage(damageSource, resultDamage);
        }else{
            if(Objects.equals(EnergyType, MiniumModComponent.ENERGY_SOURCE_GEM)){
                if(tameableEntity.isAlive()){
                    float heal = 12.0F * (lv > 0 ? (1 + 0.25F * lv) : 1);
                    tameableEntity.heal(heal);
                }
            }

        }
        hitParticles();
        if (bl) {
            if (this.getWorld() instanceof ServerWorld serverWorld) {
                EnchantmentHelper.onTargetDamaged(serverWorld, entity, damageSource);

            }
            if(Objects.equals(EnergyType, MiniumModComponent.ENERGY_SOURCE_GEM)) {
                if (livingEntity.isAlive()) {
                    Random rand = new Random();
                    switch (rand.nextInt(6)) {
                        case 0:
                            livingEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, 200, 0), MoreObjects.firstNonNull(Objects.requireNonNull(entity2), this));
                            break;
                        case 1:
                            livingEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 100, 1), MoreObjects.firstNonNull(Objects.requireNonNull(entity2), this));
                            break;
                        case 2:
                            livingEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.HASTE, 200, 1), MoreObjects.firstNonNull(Objects.requireNonNull(entity2), this));
                            break;
                        case 3:
                            livingEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, 200, 1), MoreObjects.firstNonNull(Objects.requireNonNull(entity2), this));
                            break;
                        case 4:
                            livingEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.FIRE_RESISTANCE, 400, 0), MoreObjects.firstNonNull(Objects.requireNonNull(entity2), this));
                            break;
                        case 5:
                            livingEntity.setHealth(livingEntity.getHealth() + 3);
                            break;
                    }
                }
            }
            if (entity instanceof LivingEntity livingEntity2) {
                //livingEntity2.addStatusEffect(new StatusEffectInstance(StatusEffects.POISON, 40), MoreObjects.firstNonNull(Objects.requireNonNull(entity2), this));

                //金、発光効果
                if(Objects.equals(EnergyType, MiniumModComponent.ENERGY_GOLD) || Objects.equals(EnergyType, MiniumModComponent.ENERGY_GLOWSTONE) || Objects.equals(EnergyType, MiniumModComponent.ENERGY_REFINED_GLOWSTONE)){
                    livingEntity2.addStatusEffect(new StatusEffectInstance(StatusEffects.GLOWING, Objects.equals(EnergyType, MiniumModComponent.ENERGY_REFINED_GLOWSTONE) ? 2500 : 500), MoreObjects.firstNonNull(Objects.requireNonNull(entity2), this));
                //ラピスラズリ、ランダムエフェクト
                }else if(Objects.equals(EnergyType, MiniumModComponent.ENERGY_LAPIS)){
                    Random rand = new Random();
                    switch(rand.nextInt(3)){
                        case 0:
                            livingEntity2.addStatusEffect(new StatusEffectInstance(StatusEffects.POISON, 160, 1), MoreObjects.firstNonNull(Objects.requireNonNull(entity2), this));
                            break;
                        case 1:
                            livingEntity2.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 320, 2), MoreObjects.firstNonNull(Objects.requireNonNull(entity2), this));
                            break;
                        case 2:
                            livingEntity2.addStatusEffect(new StatusEffectInstance(StatusEffects.WEAKNESS, 320, 1), MoreObjects.firstNonNull(Objects.requireNonNull(entity2), this));
                            break;
                    }

                }else if(Objects.equals(EnergyType, MiniumModComponent.ENERGY_NETHERITE)){
                    livingEntity2.addStatusEffect(new StatusEffectInstance(StatusEffects.WITHER, 80, 3), MoreObjects.firstNonNull(Objects.requireNonNull(entity2), this));
                }else if(Objects.equals(EnergyType, MiniumModComponent.ENERGY_OSMIUM)){
                    livingEntity2.addStatusEffect(new StatusEffectInstance(StatusEffects.POISON, 80, 1), MoreObjects.firstNonNull(Objects.requireNonNull(entity2), this));
                }else if(Objects.equals(EnergyType, MiniumModComponent.ENERGY_LEAD)){
                    livingEntity2.addStatusEffect(new StatusEffectInstance(StatusEffects.POISON, 40, 3), MoreObjects.firstNonNull(Objects.requireNonNull(entity2), this));
                }else if(Objects.equals(EnergyType, MiniumModComponent.ENERGY_ZINC)){
                    Random rand = new Random();
                    if(livingEntity2.getHealth() <= 0 && rand.nextFloat() < 0.1F) {
                        livingEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.SATURATION, 9, 0), MoreObjects.firstNonNull(Objects.requireNonNull(entity2), this));
                    }
                }else if(Objects.equals(EnergyType, MiniumModComponent.ENERGY_SALT)){
                    livingEntity2.addStatusEffect(new StatusEffectInstance(MiniumStatusEffects.PICKLING_SALT, 400, 0), MoreObjects.firstNonNull(Objects.requireNonNull(entity2), this));
                }else if(Objects.equals(EnergyType, MiniumModComponent.ENERGY_ELECTRUM)){
                    if(entity.getType().isIn(EntityTypeTags.SENSITIVE_TO_SMITE)){
                        livingEntity2.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 600, 5), MoreObjects.firstNonNull(Objects.requireNonNull(entity2), this));
                    }else{
                        livingEntity2.addStatusEffect(new StatusEffectInstance(StatusEffects.GLOWING, 1200), MoreObjects.firstNonNull(Objects.requireNonNull(entity2), this));

                    }
                }
            }

        }
    }

    @Override
    protected void onBlockHit(BlockHitResult blockHitResult) {
        super.onBlockHit(blockHitResult);
        if (!this.getWorld().isClient) {
            Entity entity = this.getOwner();
            if (!(entity instanceof MobEntity) || this.getWorld().getGameRules().getBoolean(GameRules.DO_MOB_GRIEFING)) {
                BlockPos blockPos = blockHitResult.getBlockPos().offset(blockHitResult.getSide());
                BlockPos blockPos2 = blockHitResult.getBlockPos();
                BlockState blockState = this.getWorld().getBlockState(blockPos2);
                if((Objects.equals(EnergyType, MiniumModComponent.ENERGY_REDSTONE) || Objects.equals(EnergyType, MiniumModComponent.ENERGY_STEEL)) && blockState.getBlock() instanceof TntBlock){
                    TntBlock.primeTnt(this.getWorld(), blockPos2);
                    this.getWorld().removeBlock(blockPos2, false);
                }else if (Objects.equals(EnergyType, MiniumModComponent.ENERGY_STEEL) && (CampfireBlock.canBeLit(blockState) || CandleBlock.canBeLit(blockState) || CandleCakeBlock.canBeLit(blockState))) {
                    //this.playUseSound(this.getWorld(), blockPos);
                    this.getWorld().setBlockState(blockPos2, blockState.with(Properties.LIT, Boolean.TRUE));
                    this.getWorld().emitGameEvent(this, GameEvent.BLOCK_CHANGE, blockPos2);
                }else if (Objects.equals(EnergyType, MiniumModComponent.ENERGY_STEEL) && this.getWorld().isAir(blockPos)) {
                    this.getWorld().setBlockState(blockPos, AbstractFireBlock.getState(this.getWorld(), blockPos));
                }else if (Objects.equals(EnergyType, MiniumModComponent.ENERGY_REDSTONE) && (this.getWorld().isAir(blockPos) || this.getWorld().isWater(blockPos) || this.getWorld().getBlockState(blockPos).isReplaceable())) {
                    this.getWorld().setBlockState(blockPos, MiniumBlock.REDSTONE_ENERGY_BLOCK.getDefaultState().with(Properties.WATERLOGGED, this.getWorld().getFluidState(blockPos).getFluid() == Fluids.WATER));
                }else if ((Objects.equals(EnergyType, MiniumModComponent.ENERGY_GLOWSTONE) || Objects.equals(EnergyType, MiniumModComponent.ENERGY_REFINED_GLOWSTONE)) && (this.getWorld().isAir(blockPos) || this.getWorld().isWater(blockPos) || this.getWorld().getBlockState(blockPos).isReplaceable())) {
                    this.getWorld().setBlockState(blockPos, MiniumBlock.GLOWSTONE_ENERGY_BLOCK.getDefaultState().with(Properties.WATERLOGGED, this.getWorld().getFluidState(blockPos).getFluid() == Fluids.WATER));
                }
            }
        }

        hitParticles();
    }
    protected void hitParticles() {
        particleColor = getRenderColor(this.getUuid());
        if(Objects.equals(this.EnergyType, MiniumModComponent.ENERGY_IRIS_QUARTZ)){
            ((ServerWorld) this.getWorld()).spawnParticles(MiniumParticle.ENERGY_HIT_PARTICLE2, this.getX(), this.getY(), this.getZ(), 1, 0.2, 0.2, 0.2, 0.0);
        }else {
            ((ServerWorld) this.getWorld()).spawnParticles(MiniumParticle.ENERGY_HIT_PARTICLE, this.getX(), this.getY(), this.getZ(), 1, 0.2, 0.2, 0.2, 0.0);
        }
        this.playSound(MiniumSoundsEvent.HIT_ENERGY_BULLET_EVENT, 1.0F, 0.6F);

    }
    @Override
    protected void initDataTracker(DataTracker.Builder builder) {
    }
    private void destroy() {
        removeRenderColor(this.getUuid());
        this.discard();
        this.getWorld().emitGameEvent(GameEvent.ENTITY_DAMAGE, this.getPos(), GameEvent.Emitter.of(this));
        //System.out.println("Destroy EnergyBullet.");
    }
    @Override
    public void tick() {
        super.tick();
        Vec3d vec3d = this.getVelocity();
        float velMul = 1.5F;
        if(Objects.equals(EnergyType, MiniumModComponent.ENERGY_QUARTZ) || Objects.equals(EnergyType, MiniumModComponent.ENERGY_CERTUS_QUARTZ) || Objects.equals(EnergyType, MiniumModComponent.ENERGY_FLUIX)){
            velMul *= 1.5F;
        }
        vec3d = vec3d.multiply(velMul);
        if (!this.getWorld().isClient) {
            //this.getWorld().addParticle(ParticleTypes.END_ROD, this.getX(), this.getY() + 0.15, this.getZ(), 0.0, 0.0, 0.0);
            if(existTime > 400){
                this.destroy();
            }
            if(existTime == 0){
                HitResult hitResult = ProjectileUtil.getCollision(this, this::canHit);
                if (hitResult.getType() != HitResult.Type.MISS) {
                    this.hitOrDeflect(hitResult);
                }

            }else{
                for(int i = 0;i < 4;i++){
                    this.setPosition(this.getX() + vec3d.x / 4, this.getY() + vec3d.y / 4, this.getZ() + vec3d.z / 4);
                    HitResult hitResult = ProjectileUtil.getCollision(this, this::canHit);
                    if (hitResult.getType() != HitResult.Type.MISS) {
                        this.hitOrDeflect(hitResult);
                        break;
                    }
                }
            }
            existTime++;
        }
        if (this.getWorld().isClient) {
            this.setPosition(this.getX() + vec3d.x * (existTime > 0 ? 1 : 0), this.getY() + vec3d.y * (existTime > 0 ? 1 : 0), this.getZ() + vec3d.z * (existTime > 1 ? 1 : 0));
            if(existTime > 2){
                this.getWorld().addParticle(ParticleTypes.END_ROD, this.getX() - vec3d.x, this.getY() - vec3d.y + 0.15, this.getZ() - vec3d.z, 0.0, 0.0, 0.0);
            }
        }
        this.checkBlockCollision();
        //destroy();
    }
    @Override//ダメージを受けたときの処理
    public boolean damage(DamageSource source, float amount) {
        /*
        if (!this.getWorld().isClient) {
            this.playSound(SoundEvents.ENTITY_SHULKER_BULLET_HURT, 1.0F, 1.0F);
            ((ServerWorld)this.getWorld()).spawnParticles(ParticleTypes.CRIT, this.getX(), this.getY(), this.getZ(), 15, 0.2, 0.2, 0.2, 0.0);
            this.destroy();
        }*/


        return true;
    }

    @Override
    public boolean shouldRender(double distance) {
        existTime++;
        return (distance < 16000.0f && existTime > 3);
    }

}
