package com.github.Minor2CCh.minium_me.component;

import com.github.Minor2CCh.minium_me.registry.MiniumDamageTypes;
import com.github.Minor2CCh.minium_me.item.MiniumItemTag;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.StringIdentifiable;
import net.minecraft.util.function.ValueLists;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.awt.*;
import java.time.LocalTime;
import java.util.function.IntFunction;
import java.util.function.ToIntFunction;

@SuppressWarnings("unused")
public record EnergyComponent(int remain, EnergyType energyType){
    public enum EnergyType implements StringIdentifiable {
        ENERGY_EMPTY(0, "energy_empty", 0xFFFFFF, DamageTypes.OUT_OF_WORLD, 0.0F, null, null, 0),
        ENERGY_COAL(1, "energy_coal", 0x555555, MiniumDamageTypes.ENERGY_FIRE, 0.5F, MiniumItemTag.ENERGY_COAL, MiniumItemTag.ENERGY_COAL_STORAGE_BLOCKS, 10),
        ENERGY_IRON(2, "energy_iron", 0xBFC9C8, MiniumDamageTypes.ENERGY_DEFAULT, 1.0F, MiniumItemTag.ENERGY_IRON, MiniumItemTag.ENERGY_IRON_STORAGE_BLOCKS, 10),
        ENERGY_COPPER(3, "energy_copper", 0xB4684D, MiniumDamageTypes.ENERGY_DEFAULT, 1.0F, MiniumItemTag.ENERGY_COPPER, MiniumItemTag.ENERGY_COPPER_STORAGE_BLOCKS, 10),
        ENERGY_GOLD(4, "energy_gold", 0xECD93F, MiniumDamageTypes.ENERGY_DEFAULT, 1.0F, MiniumItemTag.ENERGY_GOLD, MiniumItemTag.ENERGY_GOLD_STORAGE_BLOCKS, 10),
        ENERGY_LAPIS(5, "energy_lapis", 0x1C4D9C, MiniumDamageTypes.ENERGY_DEFAULT, 0.8F, MiniumItemTag.ENERGY_LAPIS, MiniumItemTag.ENERGY_LAPIS_STORAGE_BLOCKS, 10),
        ENERGY_REDSTONE(6, "energy_redstone", 0x971607, MiniumDamageTypes.ENERGY_DEFAULT, 0.8F, MiniumItemTag.ENERGY_REDSTONE, MiniumItemTag.ENERGY_REDSTONE_STORAGE_BLOCKS, 10),
        ENERGY_DIAMOND(7, "energy_diamond", 0x6EFCF2, MiniumDamageTypes.ENERGY_DEFAULT, 1.75F, MiniumItemTag.ENERGY_DIAMOND, MiniumItemTag.ENERGY_DIAMOND_STORAGE_BLOCKS, 10),
        ENERGY_EMERALD(8, "energy_emerald", 0x0EC754, MiniumDamageTypes.ENERGY_NOT_PROJECTILE, 1.5F, MiniumItemTag.ENERGY_EMERALD, MiniumItemTag.ENERGY_EMERALD_STORAGE_BLOCKS, 10),
        ENERGY_GLOWSTONE(9, "energy_glowstone", 0xFBDA74, MiniumDamageTypes.ENERGY_DEFAULT, 1.0F, MiniumItemTag.ENERGY_GLOWSTONE, null, 5),
        ENERGY_QUARTZ(10, "energy_quartz", 0xF6EADF, MiniumDamageTypes.ENERGY_NOT_PROJECTILE, 1.0F, MiniumItemTag.ENERGY_QUARTZ, null, 5),
        ENERGY_NETHERITE(11, "energy_netherite", 0x443A3B, MiniumDamageTypes.ENERGY_NOT_PROJECTILE, 2.5F, MiniumItemTag.ENERGY_NETHERITE, MiniumItemTag.ENERGY_NETHERITE_STORAGE_BLOCKS, 10),
        ENERGY_AMETHYST(12, "energy_amethyst", 0x9A5CC5, MiniumDamageTypes.ENERGY_AMETHYST, 0.6F, MiniumItemTag.ENERGY_AMETHYST, null, 5),
        //MiniumMOD ores
        ENERGY_MINIUM(13, "energy_minium", 0x2BD8B3, MiniumDamageTypes.ENERGY_NOT_PROJECTILE, 1.5F, MiniumItemTag.ENERGY_MINIUM, MiniumItemTag.ENERGY_MINIUM_STORAGE_BLOCKS, 10),
        ENERGY_C_MINIUM(14, "energy_c_minium", 0x009866, MiniumDamageTypes.ENERGY_NOT_PROJECTILE, 2.5F, MiniumItemTag.ENERGY_C_MINIUM, MiniumItemTag.ENERGY_C_MINIUM_STORAGE_BLOCKS, 10),
        ENERGY_OSMIUM(15, "energy_osmium", 0xDDEFFD, MiniumDamageTypes.ENERGY_DEFAULT, 1.5F, MiniumItemTag.ENERGY_OSMIUM, MiniumItemTag.ENERGY_OSMIUM_STORAGE_BLOCKS, 10),
        ENERGY_IRIS_QUARTZ(16, "energy_iris_quartz", 0xFFFFFF, MiniumDamageTypes.ENERGY_IRIS_QUARTZ, 4.0F, MiniumItemTag.ENERGY_IRIS_QUARTZ, MiniumItemTag.ENERGY_IRIS_QUARTZ_STORAGE_BLOCKS, 10),
        //OtherMOD ores
        ENERGY_REFINED_IRON(17, "energy_refined_iron", 0xD8DEFF, MiniumDamageTypes.ENERGY_DEFAULT, 1.5F, MiniumItemTag.ENERGY_REFINED_IRON, MiniumItemTag.ENERGY_REFINED_IRON_STORAGE_BLOCKS, 10),
        ENERGY_SOURCE_GEM(18, "energy_source_gem", 0xCC66FF, MiniumDamageTypes.ENERGY_DEFAULT, 1.2F, MiniumItemTag.ENERGY_SOURCE_GEM, MiniumItemTag.ENERGY_SOURCE_GEM_STORAGE_BLOCKS, 5),
        ENERGY_ALUMINIUM(19, "energy_aluminium", 0xE7EAEA, MiniumDamageTypes.ENERGY_DEFAULT, 1.0F, MiniumItemTag.ENERGY_ALUMINIUM, MiniumItemTag.ENERGY_ALUMINIUM_STORAGE_BLOCKS, 10),//アルミニウム
        ENERGY_LEAD(20, "energy_lead", 0x8CA7A3, MiniumDamageTypes.ENERGY_DEFAULT, 1.2F, MiniumItemTag.ENERGY_LEAD, MiniumItemTag.ENERGY_LEAD_STORAGE_BLOCKS, 10),//鉛
        ENERGY_NICKEL(21, "energy_nickel", 0xB0A075, MiniumDamageTypes.ENERGY_DEFAULT, 1.2F, MiniumItemTag.ENERGY_NICKEL, MiniumItemTag.ENERGY_NICKEL_STORAGE_BLOCKS, 10),//ニッケル
        ENERGY_SILVER(22, "energy_silver", 0x9FADB4, MiniumDamageTypes.ENERGY_DEFAULT, 1.2F, MiniumItemTag.ENERGY_SILVER, MiniumItemTag.ENERGY_SILVER_STORAGE_BLOCKS, 10),//銀
        ENERGY_TIN(23, "energy_tin", 0xFBEFE3, MiniumDamageTypes.ENERGY_DEFAULT, 1.2F, MiniumItemTag.ENERGY_TIN, MiniumItemTag.ENERGY_TIN_STORAGE_BLOCKS, 10),//錫
        ENERGY_URANIUM(24, "energy_uranium", 0xB1FAB3, MiniumDamageTypes.ENERGY_DEFAULT, 1.0F, MiniumItemTag.ENERGY_URANIUM, MiniumItemTag.ENERGY_URANIUM_STORAGE_BLOCKS, 10),//ウラン
        ENERGY_ZINC(25, "energy_zinc", 0xF0F1EF, MiniumDamageTypes.ENERGY_DEFAULT, 0.4F, MiniumItemTag.ENERGY_ZINC, MiniumItemTag.ENERGY_ZINC_STORAGE_BLOCKS, 10),//亜鉛
        ENERGY_BRONZE(26, "energy_bronze", 0xC68754, MiniumDamageTypes.ENERGY_NOT_PROJECTILE, 1.75F, MiniumItemTag.ENERGY_BRONZE, MiniumItemTag.ENERGY_BRONZE_STORAGE_BLOCKS, 10),//青銅
        ENERGY_STEEL(27, "energy_steel", 0xA2A2A2, MiniumDamageTypes.ENERGY_FIRE, 1.0F, MiniumItemTag.ENERGY_STEEL, MiniumItemTag.ENERGY_STEEL_STORAGE_BLOCKS, 10),//鋼鉄
        ENERGY_CERTUS_QUARTZ(28, "energy_certus_quartz", 0xACE9FF, MiniumDamageTypes.ENERGY_NOT_PROJECTILE, 1.0F, MiniumItemTag.ENERGY_CERTUS_QUARTZ, MiniumItemTag.ENERGY_CERTUS_QUARTZ_STORAGE_BLOCKS, 5),//ケルタスクォーツ
        ENERGY_FLUIX(29, "energy_fluix", 0xFF80D7, MiniumDamageTypes.ENERGY_NOT_PROJECTILE, 1.5F, MiniumItemTag.ENERGY_FLUIX, null, 5),//フルーシュ
        ENERGY_FLUORITE(30, "energy_fluorite", 0xEEFDF6, MiniumDamageTypes.ENERGY_NOT_PROJECTILE, 1.0F, MiniumItemTag.ENERGY_FLUORITE, MiniumItemTag.ENERGY_FLUORITE_STORAGE_BLOCKS, 10),//蛍石
        ENERGY_REFINED_GLOWSTONE(31, "energy_refined_glowstone", 0xFFF09D, MiniumDamageTypes.ENERGY_NOT_PROJECTILE, 1.5F, MiniumItemTag.ENERGY_REFINED_GLOWSTONE, MiniumItemTag.ENERGY_REFINED_GLOWSTONE_STORAGE_BLOCKS, 10),//精製グロウストーン
        ENERGY_REFINED_OBSIDIAN(32, "energy_refined_obsidian", 0x8469AC, MiniumDamageTypes.ENERGY_DEFAULT, 3.0F, MiniumItemTag.ENERGY_REFINED_OBSIDIAN, MiniumItemTag.ENERGY_REFINED_OBSIDIAN_STORAGE_BLOCKS, 10),//精製黒曜石
        ENERGY_SALT(33, "energy_salt", 0xDBDCD8, MiniumDamageTypes.ENERGY_DEFAULT, 0.6F, MiniumItemTag.ENERGY_SALT, null, 5),//塩
        ENERGY_RUBY(34, "energy_ruby", 0xB23744, MiniumDamageTypes.ENERGY_NOT_PROJECTILE, 1.0F, MiniumItemTag.ENERGY_RUBY, MiniumItemTag.ENERGY_RUBY_STORAGE_BLOCKS, 10),//ルビー
        ENERGY_SAPPHIRE(35, "energy_sapphire", 0x6E9DEF, MiniumDamageTypes.ENERGY_NOT_PROJECTILE, 1.0F, MiniumItemTag.ENERGY_SAPPHIRE, MiniumItemTag.ENERGY_SAPPHIRE_STORAGE_BLOCKS, 10),//サファイア
        ENERGY_ELECTRUM(36, "energy_electrum", 0xCFB56F, MiniumDamageTypes.ENERGY_DEFAULT, 1.0F, MiniumItemTag.ENERGY_ELECTRUM, MiniumItemTag.ENERGY_ELECTRUM_STORAGE_BLOCKS, 10),//エレクトラム
        ENERGY_PERIDOT(37, "energy_peridot", 0xACD570, MiniumDamageTypes.ENERGY_NOT_PROJECTILE, 1.5F, MiniumItemTag.ENERGY_PERIDOT, MiniumItemTag.ENERGY_PERIDOT_STORAGE_BLOCKS, 10),//ペリドット
        ENERGY_BRASS(38, "energy_brass", 0xDEBA67, MiniumDamageTypes.ENERGY_DEFAULT, 1.5F, MiniumItemTag.ENERGY_BRASS, MiniumItemTag.ENERGY_BRASS_STORAGE_BLOCKS, 10),//真鍮
        ENERGY_ADVANCED_ALLOY(39, "energy_advanced_alloy", 0xDEA384, MiniumDamageTypes.ENERGY_DEFAULT, 1.0F, MiniumItemTag.ENERGY_ADVANCED_ALLOY, MiniumItemTag.ENERGY_ADVANCED_ALLOY_STORAGE_BLOCKS, 10),//上級合金
        ENERGY_INVAR(40, "energy_invar", 0xA5ADA7, MiniumDamageTypes.ENERGY_DEFAULT, 1.5F, MiniumItemTag.ENERGY_INVAR, MiniumItemTag.ENERGY_INVAR_STORAGE_BLOCKS, 10),//不変鋼
        ENERGY_TITANIUM(41, "energy_titanium", 0xE0E0E6, MiniumDamageTypes.ENERGY_TITANIUM, 1.0F, MiniumItemTag.ENERGY_TITANIUM, MiniumItemTag.ENERGY_TITANIUM_STORAGE_BLOCKS, 10),//チタン
        ENERGY_CHROME(42, "energy_chrome", 0xF1EFEF, MiniumDamageTypes.ENERGY_DEFAULT, 1.0F, MiniumItemTag.ENERGY_CHROMIUM, MiniumItemTag.ENERGY_CHROMIUM_STORAGE_BLOCKS, 10),//クロム
        ENERGY_IRIDIUM(43, "energy_iridium", 0x91A09C, MiniumDamageTypes.ENERGY_NOT_PROJECTILE, 2.0F, MiniumItemTag.ENERGY_IRIDIUM, MiniumItemTag.ENERGY_IRIDIUM_STORAGE_BLOCKS, 10),//イリジウム
        ENERGY_PLATINUM(44, "energy_platinum", 0xACBDC9, MiniumDamageTypes.ENERGY_NOT_PROJECTILE, 2.5F, MiniumItemTag.ENERGY_PLATINUM, MiniumItemTag.ENERGY_PLATINUM_STORAGE_BLOCKS, 10);//プラチナ

        final int index;
        final String type;
        final int color;
        final RegistryKey<DamageType> damageType;
        final float damageMul;
        final TagKey<Item> material;
        @Nullable
        final TagKey<Item> materialSB;
        final int remainSB;
        public static final Codec<EnergyType> CODEC =
                StringIdentifiable.createCodec(EnergyType::values);

        public static final IntFunction<EnergyType> ID_TO_VALUE = ValueLists.createIdToValueFunction((ToIntFunction<EnergyType>) value -> value.index, values(), ValueLists.OutOfBoundsHandling.ZERO);
        public static final PacketCodec<ByteBuf, EnergyType> PACKET_CODEC = PacketCodecs.indexed(ID_TO_VALUE, value -> value.index);

        EnergyType(final int index, final String type, final int color, final RegistryKey<DamageType> damageType, final float damageMul, final TagKey<Item> material, final @Nullable TagKey<Item> materialSB, final int remainSB){
            this.index = index;
            this.type = type;
            this.color = color;
            this.damageType = damageType;
            this.damageMul = damageMul;
            this.material = material;
            this.materialSB = materialSB;
            this.remainSB = remainSB;
        }
        @Override
        public String asString() {
            return this.type;
        }
        public int getIndex(){
            return this.index;
        }
        public boolean isEmpty(){
            return this.equals(EnergyType.ENERGY_EMPTY);
        }
        public int getColor(){
            if(this.equals(EnergyComponent.EnergyType.ENERGY_IRIS_QUARTZ)){
                // 虹水晶のみグラデーション
                return EnergyComponent.colorFromClock(0.45);
            }
            return this.color;
        }
        public int getColor(long worldTime){
            if(this.equals(EnergyComponent.EnergyType.ENERGY_IRIS_QUARTZ)){
                return EnergyComponent.colorFromWorldTime(0.45, worldTime);
            }
            return this.color;
        }
        public int stringToInteger(){
            return this.ordinal();
        }
        public String indexToInteger(){
            return this.asString();
        }
        public RegistryKey<DamageType> getDamageType(){
            return this.damageType;
        }
        public String getEnergyKey(){
            return "item.minium_me.energy.type."+this.asString().substring(7);
        }
        public float getDamageMul(){
            return this.damageMul;
        }
        public TagKey<Item> getMaterial(){
            return this.material;
        }
        @Nullable
        public TagKey<Item> getMaterialSB(){
            return this.materialSB;
        }
        public int getRemainSB(){
            return this.remainSB;
        }
    }
    public static final Codec<EnergyComponent> CODEC = RecordCodecBuilder.create(builder -> builder.group(
            Codec.INT.fieldOf("energy_remain").forGetter(EnergyComponent::remain),
            EnergyType.CODEC.optionalFieldOf("energy_type", EnergyType.ENERGY_EMPTY).forGetter(EnergyComponent::energyType)

    ).apply(builder, EnergyComponent::new));

    public static final PacketCodec<RegistryByteBuf, EnergyComponent> PACKET_CODEC =
            PacketCodec.tuple(
                    PacketCodecs.INTEGER, EnergyComponent::remain,
                    EnergyType.PACKET_CODEC, EnergyComponent::energyType,
                    EnergyComponent::new
            );
    public int getColor(){
        return this.energyType().getColor();
    }
    public int getColor(long worldTime){
        return this.energyType().getColor(worldTime);
    }
    public int getIndex(){
        return this.energyType().getIndex();
    }
    public int stringToInteger(){
        return this.energyType().ordinal();
    }
    public String indexToString(){
        return this.energyType().asString();
    }
    public RegistryKey<DamageType> getDamageType(){
        return this.energyType().getDamageType();
    }
    public String getEnergyKey(){
        return this.energyType().getEnergyKey();
    }
    public float getDamageMul(){
        return this.energyType().getDamageMul();
    }
    public TagKey<Item> getMaterial(){
        return this.energyType().getMaterial();
    }
    public TagKey<Item> getMaterialSB(){
        return this.energyType().getMaterialSB();
    }
    public int getRemainSB(){
        return this.energyType().getRemainSB();
    }
    public boolean isEmpty(){
        return this.energyType().isEmpty();
    }
    public static EnergyComponent getDefaultComponent(){
        return new EnergyComponent(0, EnergyType.ENERGY_EMPTY);
    }
    @NotNull
    public static EnergyComponent getEnergyComponent(ItemStack stack){
        EnergyComponent component = stack.get(MiniumModComponent.REMAIN_ENERGY);
        return component != null ? component : getDefaultComponent();
    }
    public static void setEnergyComponent(ItemStack stack, EnergyComponent component){
        stack.set(MiniumModComponent.REMAIN_ENERGY, component);
    }
    public static EnergyType stringToEnergyType(String type){
        return EnergyType.valueOf(type);
    }
    public static EnergyType safeGetEnergyByIndex(int i) {
        EnergyType[] arr = EnergyType.values();
        return (i >= 0 && i < arr.length) ? arr[i] : EnergyType.ENERGY_EMPTY;
    }

    public static EnergyType safeGetEnergyById(String id) {
        for (EnergyType t : EnergyType.values()) {
            if (t.asString().equals(id)){
                return t;
            }
        }
        return EnergyType.ENERGY_EMPTY;
    }
    private static int hsvToRgbInt(double h, double s, @SuppressWarnings("all")double v) {
        float hf = (float)(h / 360.0);
        float sf = (float)s;
        float vf = (float)v;
        return Color.HSBtoRGB(hf, sf/2, vf);
    }

    public static int colorFromClock(double saturation) {
        LocalTime t = LocalTime.now();
        double three_seconds = t.getSecond() % 3+t.getNano()/(1000000000.0);
        double hue = (three_seconds / 3.0) * 360.0;
        return hsvToRgbInt(hue, saturation, 1.0);
    }
    public static int colorFromWorldTime(double saturation, long time) {
        LocalTime t = LocalTime.now();
        double sixtyTimes = time % 60;
        double hue = time * 360.0;
        return hsvToRgbInt(hue, saturation, 1.0);
    }
}
