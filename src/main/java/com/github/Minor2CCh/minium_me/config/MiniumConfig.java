package com.github.Minor2CCh.minium_me.config;

import net.minecraft.util.math.MathHelper;
import org.jetbrains.annotations.Nullable;

public class MiniumConfig {
    private static final int DEFAULT_HEALTH_BOOST = 40;
    private static final int DEFAULT_HEALTH_BOOST_VALUE = 2;
    private static final int DEFAULT_DURABILITY_IRIS_QUARTZ_ELYTRA = 2048;
    private static final int DEFAULT_DURABILITY_WIND_EXPLODER = 64;
    private static final int DEFAULT_DURABILITY_ADVANCED_WIND_EXPLODER = 256;
    private static final int DEFAULT_DURABILITY_MINIUM_SHIELD = 504;
    private static final int DEFAULT_DURABILITY_IRIS_QUARTZ_MACE = 7716;
    private static final float DEFAULT_IRIS_QUARTZ_MAX_DAMAGE_AMOUNT = 50.0F;
    private static final float DEFAULT_DAMAGE_MUL_IRIS_QUARTZ = 0.5F;
    private Integer healthBoostLimit = DEFAULT_HEALTH_BOOST;
    private Integer healthBoostValue = DEFAULT_HEALTH_BOOST_VALUE;
    private ToolMaterialConfig materialMinium = defaultMiniumToolMaterial();
    private ToolMaterialConfig materialConcentratedMinium = defaultConcentratedMiniumToolMaterial();
    private ToolMaterialConfig materialIrisQuartz = defaultIrisQuartzToolMaterial();
    private Integer durabilityIrisQuartzElytra = DEFAULT_DURABILITY_IRIS_QUARTZ_ELYTRA;
    private Integer durabilityWindExploder = DEFAULT_DURABILITY_WIND_EXPLODER;
    private Integer durabilityAdvancedWindExploder = DEFAULT_DURABILITY_ADVANCED_WIND_EXPLODER;
    private Integer durabilityMiniumShield = DEFAULT_DURABILITY_MINIUM_SHIELD;
    private Integer durabilityIrisQuartzMace = DEFAULT_DURABILITY_IRIS_QUARTZ_MACE;
    private Float roundIrisQuartzMaxDamageAmount = DEFAULT_IRIS_QUARTZ_MAX_DAMAGE_AMOUNT;
    private Float damageMulIrisQuartz = DEFAULT_DAMAGE_MUL_IRIS_QUARTZ;
    private ArmorMaterialConfig armorMaterialMinium = defaultMiniumArmorMaterial();
    private ArmorMaterialConfig armorMaterialConcentratedMinium = defaultConcentratedMiniumArmorMaterial();
    private ArmorMaterialConfig armorMaterialIrisQuartz = defaultIrisQuartzArmorMaterial();

    public void fillDefaults() {
        if(healthBoostLimit == null){
            healthBoostLimit = DEFAULT_HEALTH_BOOST;
        }
        if(healthBoostLimit < 0){
            healthBoostLimit = 0;
        }
        if(healthBoostValue == null){
            healthBoostValue = DEFAULT_HEALTH_BOOST_VALUE;
        }
        if(healthBoostValue < 0){
            healthBoostValue = 0;
        }
        if(materialMinium == null){
            materialMinium = defaultMiniumToolMaterial();
        }else{
            materialMinium.nullToDefault(defaultMiniumToolMaterial());
        }
        if(materialConcentratedMinium == null){
            materialConcentratedMinium = defaultConcentratedMiniumToolMaterial();
        }else{
            materialConcentratedMinium.nullToDefault(defaultConcentratedMiniumToolMaterial());
        }
        if(materialIrisQuartz == null){
            materialIrisQuartz = defaultIrisQuartzToolMaterial();
        }else{
            materialIrisQuartz.nullToDefault(defaultIrisQuartzToolMaterial());
        }
        durabilityIrisQuartzElytra = fixDurability(durabilityIrisQuartzElytra, DEFAULT_DURABILITY_IRIS_QUARTZ_ELYTRA);
        durabilityWindExploder = fixDurability(durabilityWindExploder, DEFAULT_DURABILITY_WIND_EXPLODER);
        durabilityAdvancedWindExploder = fixDurability(durabilityAdvancedWindExploder, DEFAULT_DURABILITY_ADVANCED_WIND_EXPLODER);
        durabilityMiniumShield = fixDurability(durabilityMiniumShield, DEFAULT_DURABILITY_MINIUM_SHIELD);
        durabilityIrisQuartzMace = fixDurability(durabilityIrisQuartzMace, DEFAULT_DURABILITY_IRIS_QUARTZ_MACE);
        if(roundIrisQuartzMaxDamageAmount == null){
            roundIrisQuartzMaxDamageAmount = DEFAULT_IRIS_QUARTZ_MAX_DAMAGE_AMOUNT;
        }
        if(damageMulIrisQuartz == null){
            damageMulIrisQuartz = DEFAULT_DAMAGE_MUL_IRIS_QUARTZ;
        }
        roundIrisQuartzMaxDamageAmount = Math.max(roundIrisQuartzMaxDamageAmount, 0.0F);
        damageMulIrisQuartz = MathHelper.clamp(damageMulIrisQuartz, 0.0F, 1.0F);
        if(armorMaterialMinium == null){
            armorMaterialMinium = defaultMiniumArmorMaterial();
        }else{
            armorMaterialMinium.nullToDefault(defaultMiniumArmorMaterial());
        }
        if(armorMaterialConcentratedMinium == null){
            armorMaterialConcentratedMinium = defaultConcentratedMiniumArmorMaterial();
        }else{
            armorMaterialConcentratedMinium.nullToDefault(defaultConcentratedMiniumArmorMaterial());
        }
        if(armorMaterialIrisQuartz == null){
            armorMaterialIrisQuartz = defaultIrisQuartzArmorMaterial();
        }else{
            armorMaterialIrisQuartz.nullToDefault(defaultIrisQuartzArmorMaterial());
        }

    }
    public Integer fixDurability(@Nullable Integer baseValue, Integer fixValue){
        if(baseValue == null){
            return fixValue;
        }else if(baseValue <= 0){
            return 1;
        }
        return baseValue;
    }
    public Integer getHealthBoostLimit(){
        return this.healthBoostLimit;
    }
    public Integer getHealthBoostValue(){
        return this.healthBoostValue;
    }
    public ToolMaterialConfig getMaterialMinium(){
        return this.materialMinium;
    }
    public ToolMaterialConfig getMaterialConcentratedMinium(){
        return this.materialConcentratedMinium;
    }
    public ToolMaterialConfig getMaterialIrisQuartz(){
        return this.materialIrisQuartz;
    }
    public Integer getDurabilityIrisQuartzElytra(){
        return this.durabilityIrisQuartzElytra;
    }
    public Integer getDurabilityWindExploder(){
        return this.durabilityWindExploder;
    }
    public Integer getDurabilityAdvancedWindExploder(){
        return this.durabilityAdvancedWindExploder;
    }
    public Integer getDurabilityMiniumShield(){
        return this.durabilityMiniumShield;
    }
    public Integer getDurabilityIrisQuartzMace(){
        return this.durabilityIrisQuartzMace;
    }
    public Float getRoundIrisQuartzMaxDamageAmount(){
        return this.roundIrisQuartzMaxDamageAmount;
    }
    public Float getDamageMulIrisQuartz(){
        return this.damageMulIrisQuartz;
    }
    public ArmorMaterialConfig getArmorMaterialMinium(){
        return this.armorMaterialMinium;
    }
    public ArmorMaterialConfig getArmorMaterialConcentratedMinium(){
        return this.armorMaterialConcentratedMinium;
    }
    public ArmorMaterialConfig getArmorMaterialIrisQuartz(){
        return this.armorMaterialIrisQuartz;
    }
    public static class ToolMaterialConfig{
        private Integer durability;
        private Float miningSpeed;
        private Float attackDamage;
        private Integer enchantability;
        private ToolMaterialConfig(final Integer durability, final Float miningSpeed, final Float attackDamage, final Integer enchantability) {
            this.durability = durability;
            this.miningSpeed = miningSpeed;
            this.attackDamage = attackDamage;
            this.enchantability = enchantability;
        }
        public Integer getDurability(){
            return this.durability;
        }
        public Float getMiningSpeed(){
            return this.miningSpeed;
        }
        public Float getAttackDamage(){
            return this.attackDamage;
        }
        public Integer getEnchantability(){
            return this.enchantability;
        }
        private void nullToDefault(ToolMaterialConfig defaultToolMaterial){
            if(durability == null){
                durability = defaultToolMaterial.durability;
            }
            if(miningSpeed == null){
                miningSpeed = defaultToolMaterial.miningSpeed;
            }
            if(attackDamage == null){
                attackDamage = defaultToolMaterial.attackDamage;
            }
            if(enchantability == null){
                enchantability = defaultToolMaterial.enchantability;
            }
            durability = MathHelper.clamp(durability, 1, 2147483647);
            miningSpeed = Math.max(miningSpeed, 0);
            enchantability = MathHelper.clamp(enchantability, 1, 2147483647);
        }
    }
    private static ToolMaterialConfig defaultMiniumToolMaterial(){
        return new ToolMaterialConfig(375, 10.0f, 2.5f, 15);
    }
    private static ToolMaterialConfig defaultConcentratedMiniumToolMaterial(){
        return new ToolMaterialConfig(1949, 11.0f, 3.5f, 15);
    }
    private static ToolMaterialConfig defaultIrisQuartzToolMaterial(){
        return new ToolMaterialConfig(7716, 13.0f, 6f, 25);
    }
    public static class ArmorMaterialConfig{
        private Integer baseDurability;
        private Integer helmetValue;
        private Integer chestplateValue;
        private Integer leggingsValue;
        private Integer bootsValue;
        private Integer enchantability;
        private Float toughness;
        private Float knockbackResistance;
        private ArmorMaterialConfig(final Integer baseDurability, final Integer helmetValue, final Integer chestplateValue, final Integer leggingsValue, final Integer bootsValue, final Integer enchantability, final Float toughness, final Float knockbackResistance) {
            this.baseDurability = baseDurability;
            this.helmetValue = helmetValue;
            this.chestplateValue = chestplateValue;
            this.leggingsValue = leggingsValue;
            this.bootsValue = bootsValue;
            this.enchantability = enchantability;
            this.toughness = toughness;
            this.knockbackResistance = knockbackResistance;
        }
        private void nullToDefault(ArmorMaterialConfig defaultArmorMaterial){
            if(baseDurability == null){
                baseDurability = defaultArmorMaterial.baseDurability;
            }
            if(helmetValue == null){
                helmetValue = defaultArmorMaterial.helmetValue;
            }
            if(chestplateValue == null){
                chestplateValue = defaultArmorMaterial.chestplateValue;
            }
            if(leggingsValue == null){
                leggingsValue = defaultArmorMaterial.leggingsValue;
            }
            if(bootsValue == null){
                bootsValue = defaultArmorMaterial.bootsValue;
            }
            if(enchantability == null){
                enchantability = defaultArmorMaterial.enchantability;
            }
            if(toughness == null){
                toughness = defaultArmorMaterial.toughness;
            }
            if(knockbackResistance == null){
                knockbackResistance = defaultArmorMaterial.knockbackResistance;
            }
            baseDurability = MathHelper.clamp(baseDurability, 1, 2147483647);
            helmetValue = MathHelper.clamp(helmetValue, 1, 2147483647);
            chestplateValue = MathHelper.clamp(chestplateValue, 1, 2147483647);
            leggingsValue = MathHelper.clamp(leggingsValue, 1, 2147483647);
            bootsValue = MathHelper.clamp(bootsValue, 1, 2147483647);
            enchantability = MathHelper.clamp(enchantability, 1, 2147483647);
            toughness = MathHelper.clamp(toughness, 0, 2147483647);
            knockbackResistance = MathHelper.clamp(knockbackResistance, 0, 2147483647);

        }
        public Integer getBaseDurability(){
            return this.baseDurability;
        }
        public Integer getHelmetValue(){
            return this.helmetValue;
        }
        public Integer getChestplateValue(){
            return this.chestplateValue;
        }
        public Integer getLeggingsValue(){
            return this.leggingsValue;
        }
        public Integer getBootsValue(){
            return this.bootsValue;
        }
        public Float getToughness(){
            return this.toughness;
        }
        public Float getKnockbackResistance(){
            return this.knockbackResistance;
        }
        public Integer getEnchantability(){
            return this.enchantability;
        }
    }
    private static ArmorMaterialConfig defaultMiniumArmorMaterial(){
        return new ArmorMaterialConfig(15, 2,7,5,2,15,1.0F,0.0F);
    }
    private static ArmorMaterialConfig defaultConcentratedMiniumArmorMaterial(){
        return new ArmorMaterialConfig(45, 3,8,6,3,15,2.0F,0.05F);
    }
    private static ArmorMaterialConfig defaultIrisQuartzArmorMaterial(){
        return new ArmorMaterialConfig(100, 4,9,7,4,25,5.0F,0.1F);
    }
}
