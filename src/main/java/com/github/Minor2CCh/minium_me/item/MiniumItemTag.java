package com.github.Minor2CCh.minium_me.item;

import com.github.Minor2CCh.minium_me.Minium_me;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
@SuppressWarnings("unused")
public class MiniumItemTag {

    public static final TagKey<Item> SPEARS = modOf("spears");
    public static final TagKey<Item> ENERGY_GUNS = modOf("energy_guns");
    public static final TagKey<Item> IRIS_QUARTZ_BONUS_WEAPON = modOf("iris_quartz_bonus_weapon");
    public static final TagKey<Item> MINIUM_ARMORS = modOf("minium_armors");
    public static final TagKey<Item> CONCENTRATED_MINIUM_ARMORS = modOf("concentrated_minium_armors");
    public static final TagKey<Item> IRIS_QUARTZ_ARMORS = modOf("iris_quartz_armors");
    public static final TagKey<Item> IRIS_QUARTZ_INGOT = conventionalOf("ingots/iris_quartz");
    public static final TagKey<Item> MINIUM_INGOT = conventionalOf("ingots/minium");
    public static final TagKey<Item> C_MINIUM_INGOT = conventionalOf("ingots/concentrated_minium");
    public static final TagKey<Item> WALKABLE_POWDER_SNOW = modOf("walkable_powder_snow");
    //Default:Dusts
    public static final TagKey<Item> ENERGY_REDSTONE_STORAGE_BLOCKS = modOf("energy/storage_blocks/redstone");//レッドストーン
    public static final TagKey<Item> ENERGY_REDSTONE = modOf("energy/dusts/redstone");
    public static final TagKey<Item> ENERGY_GLOWSTONE = modOf("energy/dusts/glowstone");
    public static final TagKey<Item> ENERGY_SALT = modOf("energy/dusts/salt");
    //Default:Gems
    public static final TagKey<Item> ENERGY_LAPIS_STORAGE_BLOCKS = modOf("energy/storage_blocks/lapis");//ラピスラズリ
    public static final TagKey<Item> ENERGY_LAPIS = modOf("energy/gems/lapis");
    public static final TagKey<Item> ENERGY_EMERALD_STORAGE_BLOCKS = modOf("energy/storage_blocks/emerald");//エメラルド
    public static final TagKey<Item> ENERGY_EMERALD = modOf("energy/gems/emerald");
    public static final TagKey<Item> ENERGY_AMETHYST = modOf("energy/gems/amethyst");//アメジスト
    public static final TagKey<Item> ENERGY_DIAMOND_STORAGE_BLOCKS = modOf("energy/storage_blocks/diamond");//ダイヤモンド
    public static final TagKey<Item> ENERGY_DIAMOND = modOf("energy/gems/diamond");
    public static final TagKey<Item> ENERGY_QUARTZ = modOf("energy/gems/quartz");//クォーツ
    public static final TagKey<Item> ENERGY_FLUORITE_STORAGE_BLOCKS = modOf("energy/storage_blocks/fluorite");//蛍石(MOD)
    public static final TagKey<Item> ENERGY_FLUORITE = modOf("energy/gems/fluorite");
    public static final TagKey<Item> ENERGY_CERTUS_QUARTZ_STORAGE_BLOCKS = modOf("energy/storage_blocks/certus_quartz");//ケルタスクォーツ(MOD)
    public static final TagKey<Item> ENERGY_CERTUS_QUARTZ = modOf("energy/gems/certus_quartz");
    public static final TagKey<Item> ENERGY_FLUIX = modOf("energy/gems/fluix");//フルーシュ(MOD)
    public static final TagKey<Item> ENERGY_SOURCE_GEM_STORAGE_BLOCKS = modOf("energy/storage_blocks/source");//ソースジェム(MOD)
    public static final TagKey<Item> ENERGY_SOURCE_GEM = modOf("energy/gems/source");
    public static final TagKey<Item> ENERGY_RUBY_STORAGE_BLOCKS = modOf("energy/storage_blocks/ruby");//ルビー(MOD)
    public static final TagKey<Item> ENERGY_RUBY = modOf("energy/gems/ruby");
    public static final TagKey<Item> ENERGY_SAPPHIRE_STORAGE_BLOCKS = modOf("energy/storage_blocks/sapphire");//サファイア(MOD)
    public static final TagKey<Item> ENERGY_SAPPHIRE = modOf("energy/gems/sapphire");
    //Default:Ingots
    public static final TagKey<Item> ENERGY_MINIUM_STORAGE_BLOCKS = modOf("energy/storage_blocks/minium");//マイニウム(MOD)
    public static final TagKey<Item> ENERGY_MINIUM = modOf("energy/ingots/minium");
    public static final TagKey<Item> ENERGY_C_MINIUM_STORAGE_BLOCKS = modOf("energy/storage_blocks/concentrated_minium");//濃縮マイニウム(MOD)
    public static final TagKey<Item> ENERGY_C_MINIUM = modOf("energy/ingots/concentrated_minium");
    public static final TagKey<Item> ENERGY_IRON_STORAGE_BLOCKS = modOf("energy/storage_blocks/iron");//鉄
    public static final TagKey<Item> ENERGY_IRON = modOf("energy/ingots/iron");
    public static final TagKey<Item> ENERGY_GOLD_STORAGE_BLOCKS = modOf("energy/storage_blocks/gold");//金
    public static final TagKey<Item> ENERGY_GOLD = modOf("energy/ingots/gold");
    public static final TagKey<Item> ENERGY_COPPER_STORAGE_BLOCKS = modOf("energy/storage_blocks/copper");//銅
    public static final TagKey<Item> ENERGY_COPPER = modOf("energy/ingots/copper");
    public static final TagKey<Item> ENERGY_OSMIUM_STORAGE_BLOCKS = modOf("energy/storage_blocks/osmium");//オスミウム(MOD)
    public static final TagKey<Item> ENERGY_OSMIUM = modOf("energy/ingots/osmium");
    public static final TagKey<Item> ENERGY_IRIS_QUARTZ_STORAGE_BLOCKS = modOf("energy/storage_blocks/iris_quartz");//虹水晶(MOD)
    public static final TagKey<Item> ENERGY_IRIS_QUARTZ = modOf("energy/ingots/iris_quartz");
    public static final TagKey<Item> ENERGY_NETHERITE_STORAGE_BLOCKS = modOf("energy/storage_blocks/netherite");//ネザライト
    public static final TagKey<Item> ENERGY_NETHERITE = modOf("energy/ingots/netherite");
    public static final TagKey<Item> ENERGY_REFINED_IRON_STORAGE_BLOCKS = modOf("energy/storage_blocks/refined_iron");//製錬鉄(MOD)
    public static final TagKey<Item> ENERGY_REFINED_IRON = modOf("energy/ingots/refined_iron");
    public static final TagKey<Item> ENERGY_ALUMINIUM_STORAGE_BLOCKS = modOf("energy/storage_blocks/aluminium");//アルミニウム(MOD)
    public static final TagKey<Item> ENERGY_ALUMINIUM = modOf("energy/ingots/aluminium");
    public static final TagKey<Item> ENERGY_LEAD_STORAGE_BLOCKS = modOf("energy/storage_blocks/lead");//鉛(MOD)
    public static final TagKey<Item> ENERGY_LEAD = modOf("energy/ingots/lead");
    public static final TagKey<Item> ENERGY_NICKEL_STORAGE_BLOCKS = modOf("energy/storage_blocks/nickel");//ニッケル(MOD)
    public static final TagKey<Item> ENERGY_NICKEL = modOf("energy/ingots/nickel");
    public static final TagKey<Item> ENERGY_SILVER_STORAGE_BLOCKS = modOf("energy/storage_blocks/silver");//銀(MOD)
    public static final TagKey<Item> ENERGY_SILVER = modOf("energy/ingots/silver");
    public static final TagKey<Item> ENERGY_TIN_STORAGE_BLOCKS = modOf("energy/storage_blocks/tin");//錫(MOD)
    public static final TagKey<Item> ENERGY_TIN = modOf("energy/ingots/tin");
    public static final TagKey<Item> ENERGY_URANIUM_STORAGE_BLOCKS = modOf("energy/storage_blocks/uranium");//ウラン(MOD)
    public static final TagKey<Item> ENERGY_URANIUM = modOf("energy/ingots/uranium");
    public static final TagKey<Item> ENERGY_ZINC_STORAGE_BLOCKS = modOf("energy/storage_blocks/zinc");//亜鉛(MOD)
    public static final TagKey<Item> ENERGY_ZINC = modOf("energy/ingots/zinc");
    public static final TagKey<Item> ENERGY_BRONZE_STORAGE_BLOCKS = modOf("energy/storage_blocks/bronze");//青銅(MOD)
    public static final TagKey<Item> ENERGY_BRONZE = modOf("energy/ingots/bronze");
    public static final TagKey<Item> ENERGY_STEEL_STORAGE_BLOCKS = modOf("energy/storage_blocks/steel");//鋼鉄(MOD)
    public static final TagKey<Item> ENERGY_STEEL = modOf("energy/ingots/steel");
    public static final TagKey<Item> ENERGY_REFINED_GLOWSTONE_STORAGE_BLOCKS = modOf("energy/storage_blocks/refined_glowstone");//精製グロウストーン(MOD)
    public static final TagKey<Item> ENERGY_REFINED_GLOWSTONE = modOf("energy/ingots/refined_glowstone");
    public static final TagKey<Item> ENERGY_REFINED_OBSIDIAN_STORAGE_BLOCKS = modOf("energy/storage_blocks/refined_obsidian");//精製黒曜石(MOD)
    public static final TagKey<Item> ENERGY_REFINED_OBSIDIAN = modOf("energy/ingots/refined_obsidian");
    public static final TagKey<Item> ENERGY_ELECTRUM_STORAGE_BLOCKS = modOf("energy/storage_blocks/electrum");//エレクトラム(MOD)
    public static final TagKey<Item> ENERGY_ELECTRUM = modOf("energy/ingots/electrum");



    //Another source
    public static final TagKey<Item> ENERGY_COAL_STORAGE_BLOCKS = modOf("energy/storage_blocks/coal");//石炭or木炭
    public static final TagKey<Item> ENERGY_COAL = modOf("energy/others/coal");

    private MiniumItemTag() {
    }

    private static TagKey<Item> modOf(String id) {
        return TagKey.of(RegistryKeys.ITEM, Minium_me.of(id));
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
