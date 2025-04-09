package com.github.Minor2CCh.minium_me.item;

import com.github.Minor2CCh.minium_me.Minium_me;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public class MiniumItemTag {
    //Default:Dusts
    public static final TagKey<Item> ENERGY_REDSTONE_STORAGE_BLOCKS = conventionalOf("storage_blocks/redstone");//レッドストーン
    public static final TagKey<Item> ENERGY_REDSTONE = conventionalOf("dusts/redstone");
    public static final TagKey<Item> ENERGY_GLOWSTONE = conventionalOf("dusts/glowstone");
    //Default:Gems
    public static final TagKey<Item> ENERGY_LAPIS_STORAGE_BLOCKS = conventionalOf("storage_blocks/lapis");//ラピスラズリ
    public static final TagKey<Item> ENERGY_LAPIS = conventionalOf("gems/lapis");
    public static final TagKey<Item> ENERGY_EMERALD_STORAGE_BLOCKS = conventionalOf("storage_blocks/emerald");//エメラルド
    public static final TagKey<Item> ENERGY_EMERALD = conventionalOf("gems/emerald");
    public static final TagKey<Item> ENERGY_AMETHYST = conventionalOf("gems/amethyst");//アメジスト
    public static final TagKey<Item> ENERGY_DIAMOND_STORAGE_BLOCKS = conventionalOf("storage_blocks/diamond");//ダイヤモンド
    public static final TagKey<Item> ENERGY_DIAMOND = conventionalOf("gems/diamond");
    public static final TagKey<Item> ENERGY_QUARTZ = conventionalOf("gems/quartz");//クォーツ
    public static final TagKey<Item> ENERGY_FLUORITE_STORAGE_BLOCKS = conventionalOf("storage_blocks/fluorite");//蛍石(MOD)
    public static final TagKey<Item> ENERGY_FLUORITE = conventionalOf("gems/fluorite");
    public static final TagKey<Item> ENERGY_CERTUS_QUARTZ_STORAGE_BLOCKS = conventionalOf("storage_blocks/certus_quartz");//ケルタスクォーツ(MOD)
    public static final TagKey<Item> ENERGY_CERTUS_QUARTZ = conventionalOf("gems/certus_quartz");
    public static final TagKey<Item> ENERGY_FLUIX = conventionalOf("gems/fluix");//フルーシュ(MOD)
    public static final TagKey<Item> ENERGY_SOURCE_GEM_STORAGE_BLOCKS = conventionalOf("storage_blocks/source");//ソースジェム(MOD)
    public static final TagKey<Item> ENERGY_SOURCE_GEM = conventionalOf("gems/source");
    //Default:Ingots
    public static final TagKey<Item> ENERGY_MINIUM_STORAGE_BLOCKS = conventionalOf("storage_blocks/minium");//マイニウム(MOD)
    public static final TagKey<Item> ENERGY_MINIUM = conventionalOf("ingots/minium");
    public static final TagKey<Item> ENERGY_C_MINIUM_STORAGE_BLOCKS = conventionalOf("storage_blocks/concentrated_minium");//濃縮マイニウム(MOD)
    public static final TagKey<Item> ENERGY_C_MINIUM = conventionalOf("ingots/concentrated_minium");
    public static final TagKey<Item> ENERGY_IRON_STORAGE_BLOCKS = conventionalOf("storage_blocks/iron");//鉄
    public static final TagKey<Item> ENERGY_IRON = conventionalOf("ingots/iron");
    public static final TagKey<Item> ENERGY_GOLD_STORAGE_BLOCKS = conventionalOf("storage_blocks/gold");//金
    public static final TagKey<Item> ENERGY_GOLD = conventionalOf("ingots/gold");
    public static final TagKey<Item> ENERGY_COPPER_STORAGE_BLOCKS = modOf("energy/storage_blocks/copper");//銅
    public static final TagKey<Item> ENERGY_COPPER = conventionalOf("ingots/copper");
    public static final TagKey<Item> ENERGY_OSMIUM_STORAGE_BLOCKS = conventionalOf("storage_blocks/osmium");//オスミウム(MOD)
    public static final TagKey<Item> ENERGY_OSMIUM = conventionalOf("ingots/osmium");
    public static final TagKey<Item> ENERGY_IRIS_QUARTZ_STORAGE_BLOCKS = conventionalOf("storage_blocks/iris_quartz");//虹水晶(MOD)
    public static final TagKey<Item> ENERGY_IRIS_QUARTZ = conventionalOf("ingots/iris_quartz");
    public static final TagKey<Item> ENERGY_NETHERITE_STORAGE_BLOCKS = conventionalOf("storage_blocks/netherite");//ネザライト
    public static final TagKey<Item> ENERGY_NETHERITE = conventionalOf("ingots/netherite");
    public static final TagKey<Item> ENERGY_REFINED_IRON_STORAGE_BLOCKS = conventionalOf("storage_blocks/refined_iron");//製錬鉄(MOD)
    public static final TagKey<Item> ENERGY_REFINED_IRON = conventionalOf("ingots/refined_iron");
    public static final TagKey<Item> ENERGY_ALUMINIUM_STORAGE_BLOCKS = modOf("energy/storage_blocks/aluminium");//アルミニウム(MOD)
    public static final TagKey<Item> ENERGY_ALUMINIUM = modOf("energy/ingots/aluminium");
    public static final TagKey<Item> ENERGY_LEAD_STORAGE_BLOCKS = conventionalOf("storage_blocks/lead");//鉛(MOD)
    public static final TagKey<Item> ENERGY_LEAD = conventionalOf("ingots/lead");
    public static final TagKey<Item> ENERGY_NICKEL_STORAGE_BLOCKS = conventionalOf("storage_blocks/nickel");//ニッケル(MOD)
    public static final TagKey<Item> ENERGY_NICKEL = conventionalOf("ingots/nickel");
    public static final TagKey<Item> ENERGY_SILVER_STORAGE_BLOCKS = conventionalOf("storage_blocks/silver");//銀(MOD)
    public static final TagKey<Item> ENERGY_SILVER = conventionalOf("ingots/silver");
    public static final TagKey<Item> ENERGY_TIN_STORAGE_BLOCKS = conventionalOf("storage_blocks/tin");//錫(MOD)
    public static final TagKey<Item> ENERGY_TIN = conventionalOf("ingots/tin");
    public static final TagKey<Item> ENERGY_URANIUM_STORAGE_BLOCKS = conventionalOf("storage_blocks/uranium");//ウラン(MOD)
    public static final TagKey<Item> ENERGY_URANIUM = conventionalOf("ingots/uranium");
    public static final TagKey<Item> ENERGY_ZINC_STORAGE_BLOCKS = conventionalOf("storage_blocks/zinc");//亜鉛(MOD)
    public static final TagKey<Item> ENERGY_ZINC = conventionalOf("ingots/zinc");
    public static final TagKey<Item> ENERGY_BRONZE_STORAGE_BLOCKS = conventionalOf("storage_blocks/bronze");//青銅(MOD)
    public static final TagKey<Item> ENERGY_BRONZE = conventionalOf("ingots/bronze");
    public static final TagKey<Item> ENERGY_STEEL_STORAGE_BLOCKS = conventionalOf("storage_blocks/steel");//鋼鉄(MOD)
    public static final TagKey<Item> ENERGY_STEEL = conventionalOf("ingots/steel");



    //Another source
    public static final TagKey<Item> ENERGY_COAL_STORAGE_BLOCKS = modOf("energy/storage_blocks/coal");//石炭or木炭
    public static final TagKey<Item> ENERGY_COAL = conventionalOf("coal");

    private MiniumItemTag() {
    }

    private static TagKey<Item> modOf(String id) {
        return TagKey.of(RegistryKeys.ITEM, Identifier.of(Minium_me.MOD_ID, id));
    }
    private static TagKey<Item> conventionalOf(String id) {
        return TagKey.of(RegistryKeys.ITEM, Identifier.of("c", id));
    }
    private static TagKey<Item> vanillaOf(String id) {
        return TagKey.of(RegistryKeys.ITEM, Identifier.ofVanilla(id));
    }
    private static TagKey<Item> otherModOf(String Modid, String id) {
        return TagKey.of(RegistryKeys.ITEM, Identifier.of(Modid, id));
    }
}
