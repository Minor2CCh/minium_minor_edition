package com.github.Minor2CCh.minium_me.entity;

import com.github.Minor2CCh.minium_me.component.MiniumModComponent;
import com.github.Minor2CCh.minium_me.damage_type.MiniumDamageType;
import com.github.Minor2CCh.minium_me.particle.MiniumParticle;
import com.github.Minor2CCh.minium_me.sound.MiniumSoundsEvent;
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

import java.util.Objects;

public class EnergyBulletEntity extends ProjectileEntity {
    private int existTime = 0;
    public int colorID = -1;
    private String EnergyType = MiniumModComponent.ENERGY_EMPTY;
    public static int color = 0xFFFFFF;
    public static int particleColor = 0xFFFFFF;
    public int renderColor = 0xFFFFFF;
    public EnergyBulletEntity(EntityType<? extends EnergyBulletEntity> entityType, World world) {
        super(entityType, world);
        //this.noClip = true;

    }


    public EnergyBulletEntity(World world, LivingEntity owner, String energyType) {
        this(MiniumEntityType.ENERGY_BULLET, world);
        this.setOwner(owner);
        Vec3d vec3d = owner.getBoundingBox().getCenter();
        this.refreshPositionAndAngles(vec3d.x, vec3d.y, vec3d.z, this.getYaw(), this.getPitch());
        Vec3d vel3d = this.getVelocity();
        this.setPosition(this.getX() + vel3d.x * 2, owner.getEyeY() - 0.1F + vel3d.y * 2, this.getZ() + vel3d.z * 2);
        EnergyType = energyType;

        switch(EnergyType){
            case MiniumModComponent.ENERGY_COAL:
                color = 0x555555;
                break;
            case MiniumModComponent.ENERGY_IRON:
                color = 0xBFC9C8;
                break;
            case MiniumModComponent.ENERGY_COPPER:
                color = 0xB4684D;
                break;
            case MiniumModComponent.ENERGY_GOLD:
                color = 0xECD93F;
                break;
            case MiniumModComponent.ENERGY_LAPIS:
                color = 0x1C4D9C;
                break;
            case MiniumModComponent.ENERGY_REDSTONE:
                color = 0x971607;
                break;
            case MiniumModComponent.ENERGY_DIAMOND:
                color = 0x6EFCF2;
                break;
            case MiniumModComponent.ENERGY_EMERALD:
                color = 0x0EC754;
                break;
            case MiniumModComponent.ENERGY_QUARTZ:
                color = 0xF6EADF;
                break;
            case MiniumModComponent.ENERGY_GLOWSTONE:
                color = 0xFBDA74;
                break;
            case MiniumModComponent.ENERGY_NETHERITE:
                color = 0x443A3B;
                break;
            case MiniumModComponent.ENERGY_AMETHYST:
                color = 0x9A5CC5;
                break;
            case MiniumModComponent.ENERGY_MINIUM:
                color = 0x2BD8B3;
                break;
            case MiniumModComponent.ENERGY_C_MINIUM:
                color = 0x009866;
                break;
            case MiniumModComponent.ENERGY_OSMIUM:
                color = 0xDDEFFD;
                break;
            case MiniumModComponent.ENERGY_IRIS_QUARTZ:
                color = 0xFFFFFF;
                break;
            case MiniumModComponent.ENERGY_REFINED_IRON:
                color = 0xD8DEFF;
                break;
            case MiniumModComponent.ENERGY_SOURCE_GEM:
                color = 0xCC66FF;
                break;
            case MiniumModComponent.ENERGY_ALUMINIUM:
                color = 0xE7EAEA;
                break;
            case MiniumModComponent.ENERGY_LEAD:
                color = 0x8CA7A3;
                break;
            case MiniumModComponent.ENERGY_NICKEL:
                color = 0xB0A075;
                break;
            case MiniumModComponent.ENERGY_SILVER:
                color = 0x9FADB4;
                break;
            case MiniumModComponent.ENERGY_TIN:
                color = 0xFBEFE3;
                break;
            case MiniumModComponent.ENERGY_URANIUM:
                color = 0xB1FAB3;
                break;
            case MiniumModComponent.ENERGY_ZINC:
                color = 0xF0F1EF;
                break;
            case MiniumModComponent.ENERGY_BRONZE:
                color = 0xC68754;
                break;
            case MiniumModComponent.ENERGY_STEEL:
                color = 0xA2A2A2;
                break;
            default:
                color = 0xFFFFFF;
            }

    }
    /*
    public EnergyBulletEntity(World world, LivingEntity owner, ItemStack stack) {
        super(EntityType.ARROW, owner, world, stack);
    }*/

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
        //DamageSource damageSource = this.getDamageSources().mobProjectile(this, livingEntity);
        //DamageSource damageSource = this.getDamageSources().mobAttack(livingEntity);
        DamageSource damageSource = this.getDamageSources().outOfWorld();
        final float baseDamage = 10.0F;
        float resultDamage = 0.0F;
        //Coal
        if(Objects.equals(EnergyType, MiniumModComponent.ENERGY_COAL)){
            damageSource = new DamageSource(
                    this.getWorld().getRegistryManager()
                            .get(RegistryKeys.DAMAGE_TYPE)
                            .entryOf(MiniumDamageType.ENERGY_FIRE)
                    ,livingEntity);
            resultDamage = baseDamage * 0.5F;
        }
        //Iron
        else if(Objects.equals(EnergyType, MiniumModComponent.ENERGY_IRON)){
            damageSource = new DamageSource(
                    this.getWorld().getRegistryManager()
                    .get(RegistryKeys.DAMAGE_TYPE)
                    .entryOf(MiniumDamageType.ENERGY_DEFAULT)
                    ,livingEntity);
            resultDamage = baseDamage;
        }else if(Objects.equals(EnergyType, MiniumModComponent.ENERGY_COPPER)){
            damageSource = new DamageSource(
                    this.getWorld().getRegistryManager()
                    .get(RegistryKeys.DAMAGE_TYPE)
                    .entryOf(MiniumDamageType.ENERGY_DEFAULT)
                    ,livingEntity);
            resultDamage = baseDamage;
        }else if(Objects.equals(EnergyType, MiniumModComponent.ENERGY_GOLD)){
            damageSource = new DamageSource(
                    this.getWorld().getRegistryManager()
                    .get(RegistryKeys.DAMAGE_TYPE)
                    .entryOf(MiniumDamageType.ENERGY_DEFAULT)
                    ,livingEntity);
            resultDamage = baseDamage;
            if(entity.getType().isIn(EntityTypeTags.SENSITIVE_TO_SMITE)){
                resultDamage *= 1.5F;
            }
        }else if(Objects.equals(EnergyType, MiniumModComponent.ENERGY_LAPIS)){
            damageSource = new DamageSource(
                    this.getWorld().getRegistryManager()
                    .get(RegistryKeys.DAMAGE_TYPE)
                    .entryOf(MiniumDamageType.ENERGY_DEFAULT)
                    ,livingEntity);
            resultDamage = baseDamage * 0.8F;
        }else if(Objects.equals(EnergyType, MiniumModComponent.ENERGY_REDSTONE)){
            damageSource = new DamageSource(
                    this.getWorld().getRegistryManager()
                    .get(RegistryKeys.DAMAGE_TYPE)
                    .entryOf(MiniumDamageType.ENERGY_DEFAULT)
                    ,livingEntity);
            resultDamage = baseDamage * 0.8F;
        }else if(Objects.equals(EnergyType, MiniumModComponent.ENERGY_DIAMOND)){
            damageSource = new DamageSource(
                    this.getWorld().getRegistryManager()
                            .get(RegistryKeys.DAMAGE_TYPE)
                            .entryOf(MiniumDamageType.ENERGY_DEFAULT)
                    ,livingEntity);
            resultDamage = baseDamage * 1.75F;
            if(entity.getType().isIn(ConventionalEntityTypeTags.BOSSES)){
                resultDamage *= 1.5F;
            }
        }else if(Objects.equals(EnergyType, MiniumModComponent.ENERGY_EMERALD)){
            damageSource = new DamageSource(
                    this.getWorld().getRegistryManager()
                            .get(RegistryKeys.DAMAGE_TYPE)
                            .entryOf(MiniumDamageType.ENERGY_NOT_PROJECTILE)
                    ,livingEntity);
            resultDamage = baseDamage * 1.5F;
        }else if(Objects.equals(EnergyType, MiniumModComponent.ENERGY_QUARTZ)){
            damageSource = new DamageSource(
                    this.getWorld().getRegistryManager()
                            .get(RegistryKeys.DAMAGE_TYPE)
                            .entryOf(MiniumDamageType.ENERGY_NOT_PROJECTILE)
                    ,livingEntity);
            resultDamage = baseDamage;
        }else if(Objects.equals(EnergyType, MiniumModComponent.ENERGY_GLOWSTONE)){
            damageSource = new DamageSource(
                    this.getWorld().getRegistryManager()
                            .get(RegistryKeys.DAMAGE_TYPE)
                            .entryOf(MiniumDamageType.ENERGY_DEFAULT)
                    ,livingEntity);
            resultDamage = baseDamage;
        }else if(Objects.equals(EnergyType, MiniumModComponent.ENERGY_NETHERITE)){
            damageSource = new DamageSource(
                    this.getWorld().getRegistryManager()
                            .get(RegistryKeys.DAMAGE_TYPE)
                            .entryOf(MiniumDamageType.ENERGY_NOT_PROJECTILE)
                    ,livingEntity);
            resultDamage = baseDamage * 2.5F;
        }else if(Objects.equals(EnergyType, MiniumModComponent.ENERGY_AMETHYST)){
            damageSource = new DamageSource(
                    this.getWorld().getRegistryManager()
                            .get(RegistryKeys.DAMAGE_TYPE)
                            .entryOf(MiniumDamageType.ENERGY_AMETHYST)
                    ,livingEntity);
            resultDamage = baseDamage * 0.6F;
        }else if(Objects.equals(EnergyType, MiniumModComponent.ENERGY_MINIUM)) {
            damageSource = new DamageSource(
                    this.getWorld().getRegistryManager()
                            .get(RegistryKeys.DAMAGE_TYPE)
                            .entryOf(MiniumDamageType.ENERGY_NOT_PROJECTILE)
                    , livingEntity);
            resultDamage = baseDamage * 1.5F;
        }else if(Objects.equals(EnergyType, MiniumModComponent.ENERGY_C_MINIUM)) {
            damageSource = new DamageSource(
                    this.getWorld().getRegistryManager()
                            .get(RegistryKeys.DAMAGE_TYPE)
                            .entryOf(MiniumDamageType.ENERGY_NOT_PROJECTILE)
                    , livingEntity);
            resultDamage = baseDamage * 2.5F;
        }else if(Objects.equals(EnergyType, MiniumModComponent.ENERGY_OSMIUM)) {
            damageSource = new DamageSource(
                    this.getWorld().getRegistryManager()
                            .get(RegistryKeys.DAMAGE_TYPE)
                            .entryOf(MiniumDamageType.ENERGY_DEFAULT)
                    , livingEntity);
            resultDamage = baseDamage * 1.5F;
        }else if(Objects.equals(EnergyType, MiniumModComponent.ENERGY_IRIS_QUARTZ)) {
            damageSource = new DamageSource(
                    this.getWorld().getRegistryManager()
                            .get(RegistryKeys.DAMAGE_TYPE)
                            .entryOf(MiniumDamageType.ENERGY_IRIS_QUARTZ)
                    , livingEntity);
            resultDamage = baseDamage * 4.0F;
        }else if(Objects.equals(EnergyType, MiniumModComponent.ENERGY_REFINED_IRON)) {
            damageSource = new DamageSource(
                    this.getWorld().getRegistryManager()
                            .get(RegistryKeys.DAMAGE_TYPE)
                            .entryOf(MiniumDamageType.ENERGY_DEFAULT)
                    , livingEntity);
            resultDamage = baseDamage * 1.5F;
        }else if(Objects.equals(EnergyType, MiniumModComponent.ENERGY_SOURCE_GEM)) {
            damageSource = new DamageSource(
                    this.getWorld().getRegistryManager()
                            .get(RegistryKeys.DAMAGE_TYPE)
                            .entryOf(MiniumDamageType.ENERGY_DEFAULT)
                    , livingEntity);
            resultDamage = baseDamage * 1.2F;
        }else if(Objects.equals(EnergyType, MiniumModComponent.ENERGY_ALUMINIUM)) {
            damageSource = new DamageSource(
                    this.getWorld().getRegistryManager()
                            .get(RegistryKeys.DAMAGE_TYPE)
                            .entryOf(MiniumDamageType.ENERGY_DEFAULT)
                    , livingEntity);
            resultDamage = baseDamage;
        }else if(Objects.equals(EnergyType, MiniumModComponent.ENERGY_LEAD)) {
            damageSource = new DamageSource(
                    this.getWorld().getRegistryManager()
                            .get(RegistryKeys.DAMAGE_TYPE)
                            .entryOf(MiniumDamageType.ENERGY_DEFAULT)
                    , livingEntity);
            resultDamage = baseDamage * 1.2F;
        }else if(Objects.equals(EnergyType, MiniumModComponent.ENERGY_NICKEL)) {
            damageSource = new DamageSource(
                    this.getWorld().getRegistryManager()
                            .get(RegistryKeys.DAMAGE_TYPE)
                            .entryOf(MiniumDamageType.ENERGY_DEFAULT)
                    , livingEntity);
            resultDamage = baseDamage * 1.2F;
            if(entity.getType().isFireImmune()){
                resultDamage *= 1.5F;
            }
        }else if(Objects.equals(EnergyType, MiniumModComponent.ENERGY_SILVER)) {
            damageSource = new DamageSource(
                    this.getWorld().getRegistryManager()
                            .get(RegistryKeys.DAMAGE_TYPE)
                            .entryOf(MiniumDamageType.ENERGY_DEFAULT)
                    , livingEntity);
            resultDamage = baseDamage * 1.2F;
            if(!entity.getType().isIn(EntityTypeTags.SENSITIVE_TO_SMITE)){
                resultDamage *= 1.5F;
            }
        }else if(Objects.equals(EnergyType, MiniumModComponent.ENERGY_TIN)) {
            damageSource = new DamageSource(
                    this.getWorld().getRegistryManager()
                            .get(RegistryKeys.DAMAGE_TYPE)
                            .entryOf(MiniumDamageType.ENERGY_DEFAULT)
                    , livingEntity);
            resultDamage = baseDamage * 1.2F;
            if(entity.isTouchingWaterOrRain()){
                resultDamage *= 1.5F;
            }

        }else if(Objects.equals(EnergyType, MiniumModComponent.ENERGY_URANIUM)) {
            damageSource = new DamageSource(
                    this.getWorld().getRegistryManager()
                            .get(RegistryKeys.DAMAGE_TYPE)
                            .entryOf(MiniumDamageType.ENERGY_DEFAULT)
                    , livingEntity);
            resultDamage = baseDamage;
            if(entity.getType().isIn(EntityTypeTags.ILLAGER)
                    || entity.getType().equals(EntityType.VILLAGER)
                    || entity.getType().equals(EntityType.WANDERING_TRADER)
                    || entity.getType().equals(EntityType.WITCH)
                    || entity.getType().equals(EntityType.PLAYER)){
                resultDamage *= 5.0F;
            }
        }else if(Objects.equals(EnergyType, MiniumModComponent.ENERGY_ZINC)) {
            damageSource = new DamageSource(
                    this.getWorld().getRegistryManager()
                            .get(RegistryKeys.DAMAGE_TYPE)
                            .entryOf(MiniumDamageType.ENERGY_DEFAULT)
                    , livingEntity);
            resultDamage = baseDamage * 0.4F;
        }else if(Objects.equals(EnergyType, MiniumModComponent.ENERGY_BRONZE)) {
            damageSource = new DamageSource(
                    this.getWorld().getRegistryManager()
                            .get(RegistryKeys.DAMAGE_TYPE)
                            .entryOf(MiniumDamageType.ENERGY_NOT_PROJECTILE)
                    , livingEntity);
            resultDamage = baseDamage * 1.75F;
        }else if(Objects.equals(EnergyType, MiniumModComponent.ENERGY_STEEL)) {
            damageSource = new DamageSource(
                    this.getWorld().getRegistryManager()
                            .get(RegistryKeys.DAMAGE_TYPE)
                            .entryOf(MiniumDamageType.ENERGY_FIRE)
                    , livingEntity);
            resultDamage = baseDamage * (entity.isTouchingWaterOrRain() ? 0.4F : (entity.isOnFire() ? 2.0F : 1.2F));
        }
        boolean bl = false;
        if(!(entity instanceof TameableEntity tameableEntity) || !tameableEntity.isOwner(livingEntity)){
            if(Objects.equals(EnergyType, MiniumModComponent.ENERGY_STEEL)){
                entity.setFireTicks(120);
            }
            bl = entity.damage(damageSource, resultDamage);
        }else{
            if(Objects.equals(EnergyType, MiniumModComponent.ENERGY_SOURCE_GEM)){
                if(tameableEntity.isAlive()){
                    tameableEntity.heal(12.0F);
                }
            }
        }
        //Objects.requireNonNull(livingEntity).setHealth(livingEntity.getHealth()+1);
        /*
        if(Objects.requireNonNull(livingEntity).isAlive()){
            Objects.requireNonNull(livingEntity).setHealth(livingEntity.getHealth()+20);
        }*/
        hitParticles();
        if (bl) {
            if (this.getWorld() instanceof ServerWorld serverWorld) {
                EnchantmentHelper.onTargetDamaged(serverWorld, entity, damageSource);

            }
            if(Objects.equals(EnergyType, MiniumModComponent.ENERGY_SOURCE_GEM)) {
                if (livingEntity.isAlive()) {
                    double rand = Math.random();
                    int selectEffect = (int) (rand * 6);
                    switch (selectEffect) {
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
                if(Objects.equals(EnergyType, MiniumModComponent.ENERGY_GOLD) || Objects.equals(EnergyType, MiniumModComponent.ENERGY_GLOWSTONE)){
                    livingEntity2.addStatusEffect(new StatusEffectInstance(StatusEffects.GLOWING, 500), MoreObjects.firstNonNull(Objects.requireNonNull(entity2), this));
                //ラピスラズリ、ランダムエフェクト
                }else if(Objects.equals(EnergyType, MiniumModComponent.ENERGY_LAPIS)){
                    double rand = Math.random();
                    int selectEffect = (int) (rand*3);
                    switch(selectEffect){
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
                    livingEntity2.addStatusEffect(new StatusEffectInstance(StatusEffects.POISON, 20, 3), MoreObjects.firstNonNull(Objects.requireNonNull(entity2), this));
                }else if(Objects.equals(EnergyType, MiniumModComponent.ENERGY_ZINC)){
                    if(livingEntity2.getHealth() <= 0 && Math.random() < 0.1F) {
                        livingEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.SATURATION, 9, 0), MoreObjects.firstNonNull(Objects.requireNonNull(entity2), this));
                    }
                }
            }

        }
    }

    @Override
    protected void onBlockHit(BlockHitResult blockHitResult) {
        super.onBlockHit(blockHitResult);
        if(Objects.equals(EnergyType, MiniumModComponent.ENERGY_STEEL)){
            if (!this.getWorld().isClient) {
                Entity entity = this.getOwner();
                if (!(entity instanceof MobEntity) || this.getWorld().getGameRules().getBoolean(GameRules.DO_MOB_GRIEFING)) {
                    BlockPos blockPos = blockHitResult.getBlockPos().offset(blockHitResult.getSide());
                    BlockPos blockPos2 = blockHitResult.getBlockPos();
                    BlockState blockState = this.getWorld().getBlockState(blockPos2);
                    if (CampfireBlock.canBeLit(blockState) || CandleBlock.canBeLit(blockState) || CandleCakeBlock.canBeLit(blockState)) {
                        //this.playUseSound(this.getWorld(), blockPos);
                        this.getWorld().setBlockState(blockPos2, blockState.with(Properties.LIT, Boolean.TRUE));
                        this.getWorld().emitGameEvent(this, GameEvent.BLOCK_CHANGE, blockPos2);
                    }else if (this.getWorld().isAir(blockPos)) {
                        this.getWorld().setBlockState(blockPos, AbstractFireBlock.getState(this.getWorld(), blockPos));
                    }
                }
            }
        }
        hitParticles();
    }
    protected void hitParticles() {
        particleColor = color;
        if(Objects.equals(this.EnergyType, MiniumModComponent.ENERGY_IRIS_QUARTZ)){
            ((ServerWorld) this.getWorld()).spawnParticles(MiniumParticle.ENERGY_HIT_PARTICLE2, this.getX(), this.getY(), this.getZ(), 1, 0.2, 0.2, 0.2, 0.0);
        }else {
            ((ServerWorld) this.getWorld()).spawnParticles(MiniumParticle.ENERGY_HIT_PARTICLE, this.getX(), this.getY(), this.getZ(), 1, 0.2, 0.2, 0.2, 0.0);
        }
        this.playSound(MiniumSoundsEvent.HIT_ENERGY_BULLET_EVENT, 1.0F, 0.6F);
    }
    @Override
    protected void initDataTracker(DataTracker.Builder builder) {
    }/*
    @Override
    public boolean canHit() {
        return false;
    }*/
    private void destroy() {
        this.discard();
        this.getWorld().emitGameEvent(GameEvent.ENTITY_DAMAGE, this.getPos(), GameEvent.Emitter.of(this));
        //System.out.println("Destroy EnergyBullet.");
    }
    @Override
    public void tick() {
        super.tick();
        Vec3d vec3d = this.getVelocity();
        float velMul = 1.5F;
        if(Objects.equals(EnergyType, MiniumModComponent.ENERGY_QUARTZ)){
            velMul *= 1.5F;
        }
        vec3d = vec3d.multiply(velMul);
        if (!this.getWorld().isClient) {
            //this.getWorld().addParticle(ParticleTypes.END_ROD, this.getX(), this.getY() + 0.15, this.getZ(), 0.0, 0.0, 0.0);
            if(existTime > 400){
                destroy();
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
