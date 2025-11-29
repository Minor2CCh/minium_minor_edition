package com.github.Minor2CCh.minium_me.entity;

import com.github.Minor2CCh.minium_me.block.MiniumBlock;
import com.github.Minor2CCh.minium_me.component.EnergyComponent;
import com.github.Minor2CCh.minium_me.enchantment.MiniumEnchantments;
import com.github.Minor2CCh.minium_me.particle.EnergyGunParticleType;
import com.github.Minor2CCh.minium_me.particle.MiniumParticles;
import com.github.Minor2CCh.minium_me.sound.MiniumSoundsEvent;
import com.github.Minor2CCh.minium_me.statuseffect.MiniumStatusEffects;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalEntityTypeTags;
import net.minecraft.block.*;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.tag.EntityTypeTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.property.Properties;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.GameRules;
import net.minecraft.world.LightType;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

import java.util.Objects;
import java.util.Random;


public class EnergyBulletEntity extends ProjectileEntity {
    private int existTime = 0;
    private EnergyComponent.EnergyType energyType = EnergyComponent.EnergyType.ENERGY_EMPTY;
    private int gunBoost = 0;
    private static final TrackedData<Integer> ENERGY_INDEX = DataTracker.registerData(EnergyBulletEntity.class, TrackedDataHandlerRegistry.INTEGER);
    private static final TrackedData<Integer> GUN_BOOST = DataTracker.registerData(EnergyBulletEntity.class, TrackedDataHandlerRegistry.INTEGER);
    private static final TrackedData<Integer> EXIST_TIME = DataTracker.registerData(EnergyBulletEntity.class, TrackedDataHandlerRegistry.INTEGER);
    public EnergyBulletEntity(EntityType<? extends EnergyBulletEntity> entityType, World world) {
        super(entityType, world);
        this.noClip = true;

    }


    public EnergyBulletEntity(World world, LivingEntity owner, ItemStack itemStack) {
        this(MiniumEntityType.ENERGY_BULLET, world);
        this.setOwner(owner);
        Vec3d vec3d = owner.getBoundingBox().getCenter();
        this.refreshPositionAndAngles(vec3d.x, vec3d.y, vec3d.z, this.getYaw(), this.getPitch());
        EnergyComponent EComp = EnergyComponent.getEnergyComponent(itemStack);
        energyType = EComp.energyType();
        int energyIndex = energyType.getIndex();
        gunBoost = MiniumEnchantments.getEnchantmentLevel(this, itemStack, MiniumEnchantments.ENERGY_BOOST);
        this.getDataTracker().set(ENERGY_INDEX, energyIndex);
        this.getDataTracker().set(GUN_BOOST, gunBoost);

    }
    public EnergyBulletEntity(World world, ItemStack itemStack, Vec3d pos) {
        this(MiniumEntityType.ENERGY_BULLET, world);
        this.refreshPositionAndAngles(pos.x, pos.y, pos.z, this.getYaw(), this.getPitch());
        EnergyComponent EComp = EnergyComponent.getEnergyComponent(itemStack);
        energyType = EComp.energyType();
        int energyIndex = energyType.getIndex();
        gunBoost = MiniumEnchantments.getEnchantmentLevel(this, itemStack, MiniumEnchantments.ENERGY_BOOST);
        this.getDataTracker().set(ENERGY_INDEX, energyIndex);
        this.getDataTracker().set(GUN_BOOST, gunBoost);

    }
    public EnergyComponent.EnergyType getEnergyType(){
        return EnergyComponent.safeGetEnergyByIndex(this.getDataTracker().get(ENERGY_INDEX));
    }
    public int getEnergyColor(){
        return this.getEnergyType().getColor();
    }
    public int getGunBoostLevel(){
        return this.getDataTracker().get(GUN_BOOST);
    }
    public int getExistTime(){
        return this.getDataTracker().get(EXIST_TIME);
    }
    public void addExistTime(){
        this.getDataTracker().set(EXIST_TIME, ++existTime);

    }
    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putInt("EnergyIndex", energyType.getIndex());
        nbt.putInt("GunBoost", this.gunBoost);
        nbt.putInt("ExistTime", this.existTime);
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        this.energyType = EnergyComponent.safeGetEnergyByIndex(nbt.getInt("EnergyIndex"));
        this.gunBoost = (nbt.getInt("GunBoost"));
        this.existTime = nbt.getInt("ExistTime");
        this.getDataTracker().set(ENERGY_INDEX, energyType.getIndex());
        this.getDataTracker().set(GUN_BOOST, gunBoost);
        this.getDataTracker().set(EXIST_TIME, existTime);
    }
    @Override
    protected void initDataTracker(DataTracker.Builder builder) {
        builder.add(ENERGY_INDEX, 0);
        builder.add(GUN_BOOST, 0);
        builder.add(EXIST_TIME, 0);
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
        LivingEntity ownerEntity = entity2 instanceof LivingEntity ? (LivingEntity)entity2 : null;
        final float baseDamage = 10.0F;
        float resultDamage;
        DamageSource damageSource = this.getDamageSources().create(energyType.getDamageType(), this, ownerEntity);

        resultDamage = baseDamage * energyType.getDamageMul();
        if(energyType.equals(EnergyComponent.EnergyType.ENERGY_GOLD)){
            if(entity.getType().isIn(EntityTypeTags.SENSITIVE_TO_SMITE)){
                resultDamage *= 1.5F;
            }
        }else if(energyType.equals(EnergyComponent.EnergyType.ENERGY_DIAMOND)){
            if(entity.getType().isIn(ConventionalEntityTypeTags.BOSSES)){
                resultDamage *= 1.75F;
            }
        }else if(energyType.equals(EnergyComponent.EnergyType.ENERGY_NICKEL) || energyType.equals(EnergyComponent.EnergyType.ENERGY_INVAR)){
            if(entity.getType().isFireImmune()){
                resultDamage *= 1.75F;
            }
        }else if(energyType.equals(EnergyComponent.EnergyType.ENERGY_SILVER)){
            if(!entity.getType().isIn(EntityTypeTags.SENSITIVE_TO_SMITE)){
                resultDamage *= 1.75F;
            }
        }else if(energyType.equals(EnergyComponent.EnergyType.ENERGY_TIN)){
            if(entity.isTouchingWaterOrRain()){
                resultDamage *= 1.75F;
            }
        }else if(energyType.equals(EnergyComponent.EnergyType.ENERGY_URANIUM)){
            if(entity.getType().isIn(EntityTypeTags.ILLAGER)
                    || entity.getType().equals(EntityType.VILLAGER)
                    || entity.getType().equals(EntityType.WANDERING_TRADER)
                    || entity.getType().equals(EntityType.WITCH)
                    || entity.getType().equals(EntityType.PLAYER)
                    || (entity instanceof VillagerEntity)){
                resultDamage *= 5.0F;
            }
        }else if(energyType.equals(EnergyComponent.EnergyType.ENERGY_STEEL)){
            resultDamage = baseDamage * (entity.isTouchingWaterOrRain() ? 0.4F : (entity.isOnFire() ? 2.0F : 1.2F));

        }else if(energyType.equals(EnergyComponent.EnergyType.ENERGY_REFINED_GLOWSTONE)) {
            if (entity.getType().isIn(EntityTypeTags.SENSITIVE_TO_SMITE)) {
                resultDamage *= 2.0F;
            }
        }else if(energyType.equals(EnergyComponent.EnergyType.ENERGY_RUBY)) {
            if (entity instanceof LivingEntity livingEntity1 && !livingEntity1.canFreeze()) {
                resultDamage *= 2.0F;
            }
        }else if(energyType.equals(EnergyComponent.EnergyType.ENERGY_SAPPHIRE)) {
            if (entity instanceof LivingEntity livingEntity1 && livingEntity1.canBreatheInWater() && !livingEntity1.getType().isIn(EntityTypeTags.UNDEAD)) {
                resultDamage *= 2.0F;
            }
        }else if(energyType.equals(EnergyComponent.EnergyType.ENERGY_PERIDOT)) {
            if (entity instanceof LivingEntity livingEntity1) {
                resultDamage *= 1.0F+getSunlightLevel(livingEntity1.getBlockPos()) / 15.0F;
            }
        }else if(energyType.equals(EnergyComponent.EnergyType.ENERGY_ADVANCED_ALLOY)) {
            resultDamage *= 0.5F + this.getWorld().getRandom().nextFloat() * 2.5F;
        }else if(energyType.equals(EnergyComponent.EnergyType.ENERGY_IRIDIUM)) {
            if (entity instanceof LivingEntity livingEntity1 && livingEntity1.getHealth() / (livingEntity1.getMaxHealth() <= 0 ? 1 : livingEntity1.getMaxHealth()) >= 0.5) {
                resultDamage *= 2.0F;
            }
        }
        if(energyType.equals(EnergyComponent.EnergyType.ENERGY_REDSTONE)) {
            entity.timeUntilRegen -= Math.min(entity.timeUntilRegen, 6);
        }else if(energyType.equals(EnergyComponent.EnergyType.ENERGY_ALUMINIUM)) {
            entity.timeUntilRegen -= Math.min(entity.timeUntilRegen, 10);
        }



        boolean bl = false;
        int lv = getGunBoostLevel();
        if(lv > 0)
            resultDamage *= (1 + 0.25F * lv);
        if(ownerEntity == null || !(entity instanceof TameableEntity tameableEntity) || !tameableEntity.isOwner(ownerEntity)){
            if(Objects.equals(energyType, EnergyComponent.EnergyType.ENERGY_STEEL)){
                entity.setFireTicks(120);
            }
            bl = entity.damage(damageSource, resultDamage);
        }else{
            if(Objects.equals(energyType, EnergyComponent.EnergyType.ENERGY_SOURCE_GEM)){
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
            if (ownerEntity != null && ownerEntity.isAlive()) {
                if(Objects.equals(energyType, EnergyComponent.EnergyType.ENERGY_SOURCE_GEM)) {
                    Random rand = new Random();
                    switch (rand.nextInt(6)) {
                        case 0:
                            ownerEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, 200, 0), entity2);
                            break;
                        case 1:
                            ownerEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 100, 1), entity2);
                            break;
                        case 2:
                            ownerEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.HASTE, 200, 1), entity2);
                            break;
                        case 3:
                            ownerEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, 200, 1), entity2);
                            break;
                        case 4:
                            ownerEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.FIRE_RESISTANCE, 400, 0), entity2);
                            break;
                        case 5:
                            ownerEntity.setHealth(ownerEntity.getHealth() + 3);
                            break;
                    }
                }
            }
            if (entity instanceof LivingEntity livingEntity2) {

                //金、発光効果
                if(Objects.equals(energyType, EnergyComponent.EnergyType.ENERGY_GOLD) || Objects.equals(energyType, EnergyComponent.EnergyType.ENERGY_GLOWSTONE) || Objects.equals(energyType, EnergyComponent.EnergyType.ENERGY_REFINED_GLOWSTONE)){
                    livingEntity2.addStatusEffect(new StatusEffectInstance(StatusEffects.GLOWING, Objects.equals(energyType, EnergyComponent.EnergyType.ENERGY_REFINED_GLOWSTONE) ? 2500 : 500), entity2);
                //ラピスラズリ、ランダムエフェクト
                }else if(Objects.equals(energyType, EnergyComponent.EnergyType.ENERGY_LAPIS)){
                    Random rand = new Random();
                    switch(rand.nextInt(3)){
                        case 0:
                            livingEntity2.addStatusEffect(new StatusEffectInstance(StatusEffects.POISON, 160, 1), entity2);
                            break;
                        case 1:
                            livingEntity2.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 320, 2), entity2);
                            break;
                        case 2:
                            livingEntity2.addStatusEffect(new StatusEffectInstance(StatusEffects.WEAKNESS, 320, 1), entity2);
                            break;
                    }

                }else if(Objects.equals(energyType, EnergyComponent.EnergyType.ENERGY_NETHERITE)){
                    livingEntity2.addStatusEffect(new StatusEffectInstance(StatusEffects.WITHER, 80, 3), entity2);
                }else if(Objects.equals(energyType, EnergyComponent.EnergyType.ENERGY_OSMIUM)){
                    livingEntity2.addStatusEffect(new StatusEffectInstance(StatusEffects.POISON, 80, 1), entity2);
                }else if(Objects.equals(energyType, EnergyComponent.EnergyType.ENERGY_LEAD)){
                    livingEntity2.addStatusEffect(new StatusEffectInstance(StatusEffects.POISON, 40, 3), entity2);
                }else if(Objects.equals(energyType, EnergyComponent.EnergyType.ENERGY_ZINC)){
                    Random rand = new Random();
                    if(ownerEntity != null && livingEntity2.getHealth() <= 0 && rand.nextFloat() < 0.1F) {
                        ownerEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.SATURATION, 9, 0), entity2);
                    }
                }else if(Objects.equals(energyType, EnergyComponent.EnergyType.ENERGY_SALT)){
                    livingEntity2.addStatusEffect(new StatusEffectInstance(MiniumStatusEffects.PICKLING_SALT, 400, 0), entity2);
                }else if(Objects.equals(energyType, EnergyComponent.EnergyType.ENERGY_ELECTRUM)){
                    if(entity.getType().isIn(EntityTypeTags.SENSITIVE_TO_SMITE)){
                        livingEntity2.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 600, 5), entity2);
                    }else{
                        livingEntity2.addStatusEffect(new StatusEffectInstance(StatusEffects.GLOWING, 1200), entity2);

                    }
                }else if(Objects.equals(energyType, EnergyComponent.EnergyType.ENERGY_INVAR)){
                    if(entity.getType().isFireImmune()){
                        livingEntity2.addStatusEffect(new StatusEffectInstance(StatusEffects.WITHER, 120, 0), entity2);
                    }
                }else if(Objects.equals(energyType, EnergyComponent.EnergyType.ENERGY_CHROME)) {
                    if(ownerEntity != null && livingEntity2.getHealth() <= 0 && ownerEntity instanceof PlayerEntity player) {
                        int maxHealth = MathHelper.ceil(livingEntity2.getMaxHealth());
                        player.getHungerManager().add(maxHealth / 10, MathHelper.clamp(maxHealth / 20.0F, 0.5F, 2.0F));
                    }
                }
            }
        }
    }
    private int getSunlightLevel(BlockPos pos){
        int i = this.getWorld().getLightLevel(LightType.SKY, pos) - this.getWorld().getAmbientDarkness();
        float f = this.getWorld().getSkyAngleRadians(1.0F);if (i > 0) {
            float g = f < (float) Math.PI ? 0.0F : (float) (Math.PI * 2);
            f += (g - f) * 0.2F;
            i = Math.round(i * MathHelper.cos(f));
        }

        return MathHelper.clamp(i, 0, 15);

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
                if((Objects.equals(energyType, EnergyComponent.EnergyType.ENERGY_REDSTONE) || Objects.equals(energyType, EnergyComponent.EnergyType.ENERGY_STEEL)) && blockState.getBlock() instanceof TntBlock){
                    TntBlock.primeTnt(this.getWorld(), blockPos2);
                    this.getWorld().removeBlock(blockPos2, false);
                }else if (Objects.equals(energyType, EnergyComponent.EnergyType.ENERGY_STEEL) && (CampfireBlock.canBeLit(blockState) || CandleBlock.canBeLit(blockState) || CandleCakeBlock.canBeLit(blockState))) {
                    //this.playUseSound(this.getWorld(), blockPos);
                    this.getWorld().setBlockState(blockPos2, blockState.with(Properties.LIT, Boolean.TRUE));
                    this.getWorld().emitGameEvent(this, GameEvent.BLOCK_CHANGE, blockPos2);
                }else if (Objects.equals(energyType, EnergyComponent.EnergyType.ENERGY_STEEL) && this.getWorld().isAir(blockPos)) {
                    this.getWorld().setBlockState(blockPos, AbstractFireBlock.getState(this.getWorld(), blockPos));
                }else if (Objects.equals(energyType, EnergyComponent.EnergyType.ENERGY_REDSTONE) && (this.getWorld().isAir(blockPos) || this.getWorld().isWater(blockPos) || this.getWorld().getBlockState(blockPos).isReplaceable())) {
                    this.getWorld().setBlockState(blockPos, MiniumBlock.REDSTONE_ENERGY_BLOCK.getDefaultState().with(Properties.WATERLOGGED, this.getWorld().getFluidState(blockPos).getFluid() == Fluids.WATER));
                }else if ((Objects.equals(energyType, EnergyComponent.EnergyType.ENERGY_GLOWSTONE) || Objects.equals(energyType, EnergyComponent.EnergyType.ENERGY_REFINED_GLOWSTONE)) && (this.getWorld().isAir(blockPos) || this.getWorld().isWater(blockPos) || this.getWorld().getBlockState(blockPos).isReplaceable())) {
                    this.getWorld().setBlockState(blockPos, MiniumBlock.GLOWSTONE_ENERGY_BLOCK.getDefaultState().with(Properties.WATERLOGGED, this.getWorld().getFluidState(blockPos).getFluid() == Fluids.WATER));
                }
            }
        }

        hitParticles();
    }
    protected void hitParticles() {
        Vec3d vec3d = this.getVelocity();
        this.setPosition(this.getX() + vec3d.x / 2, this.getY() + vec3d.y / 2, this.getZ() + vec3d.z / 2);
        if(Objects.equals(this.energyType, EnergyComponent.EnergyType.ENERGY_IRIS_QUARTZ)){
            ((ServerWorld) this.getWorld()).spawnParticles(MiniumParticles.ENERGY_HIT_PARTICLE2, this.getX(), this.getY(), this.getZ(), 1, 0.2, 0.2, 0.2, 0.0);
        }else {
            ((ServerWorld) this.getWorld()).spawnParticles(EnergyGunParticleType.create(MiniumParticles.ENERGY_HIT_PARTICLE, this.getEnergyColor()), this.getX(), this.getY(), this.getZ(), 1, 0.2, 0.2, 0.2, 0.0);
        }
        this.playSound(MiniumSoundsEvent.HIT_ENERGY_BULLET_EVENT, 1.0F, 0.6F);

    }
    private void destroy() {
        this.discard();
        this.getWorld().emitGameEvent(GameEvent.ENTITY_DAMAGE, this.getPos(), GameEvent.Emitter.of(this));
        //System.out.println("Destroy EnergyBullet.");
    }
    @Override
    public void tick() {
        super.tick();
        Vec3d vec3d = this.getVelocity();
        float velMul = 1.0F;
        if(Objects.equals(energyType, EnergyComponent.EnergyType.ENERGY_QUARTZ) || Objects.equals(energyType, EnergyComponent.EnergyType.ENERGY_CERTUS_QUARTZ) || Objects.equals(energyType, EnergyComponent.EnergyType.ENERGY_FLUIX)){
            velMul *= 1.5F;
        }
        vec3d = vec3d.multiply(velMul);
        //this.getWorld().addParticle(ParticleTypes.END_ROD, this.getX(), this.getY() + 0.15, this.getZ(), 0.0, 0.0, 0.0);
        if(getExistTime() > 80){
            this.destroy();
        }
        for(int i = 0;i < (getExistTime() == 0 ? 2 : 5) ;i++){
            this.setPosition(this.getX() + vec3d.x / 4, this.getY() + vec3d.y / 4, this.getZ() + vec3d.z / 4);
            if(!this.getWorld().isClient){
                HitResult hitResult = ProjectileUtil.getCollision(this, this::canHit);
                if (hitResult.getType() != HitResult.Type.MISS) {
                    this.hitOrDeflect(hitResult);
                    break;
                }
            }
        }
        if (this.getWorld().isClient) {
            if(getExistTime() > 1){
                this.getWorld().addParticle(ParticleTypes.END_ROD, this.getX() - vec3d.x, this.getY() - vec3d.y + 0.15, this.getZ() - vec3d.z, 0.0, 0.0, 0.0);
            }
        }


        addExistTime();
        this.checkBlockCollision();
    }
    @Override//ダメージを受けたときの処理
    public boolean damage(DamageSource source, float amount) {
        return true;
    }

    @Override
    public boolean shouldRender(double distance) {
        if(this.getOwner() == null){    //所有者がいないのは無条件で描画
            return distance < 16000.0f;
        }
        return (distance < 16000.0f && distance > 5F || getExistTime() > 2);
    }

}
