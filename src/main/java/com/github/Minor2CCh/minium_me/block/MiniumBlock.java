package com.github.Minor2CCh.minium_me.block;

import com.github.Minor2CCh.minium_me.Minium_me;
import net.minecraft.block.*;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.intprovider.UniformIntProvider;

import static net.minecraft.block.Blocks.createLightLevelFromLitBlockState;

public class MiniumBlock{

    public static final Block MINIUM_ORE = register(new Block(Block.Settings.create().strength(3f, 3.0f).mapColor(MapColor.CYAN).requiresTool()), "minium_ore", true, false);
    public static final Block DEEPSLATE_MINIUM_ORE = register(new Block(Block.Settings.create().strength(4.5f, 3.0f).mapColor(MapColor.CYAN).requiresTool().sounds(BlockSoundGroup.DEEPSLATE)), "deepslate_minium_ore", true, false);
    public static final Block MINIUM_BLOCK = register(new Block(Block.Settings.create().strength(5f, 20.0f).mapColor(MapColor.CYAN).requiresTool().sounds(BlockSoundGroup.METAL)), "minium_block", true, false);
    public static final Block CONCENTRATED_MINIUM_BLOCK = register(new Block(Block.Settings.create().strength(7.5f, 600.0f).mapColor(MapColor.CYAN).requiresTool().sounds(BlockSoundGroup.NETHERITE)), "concentrated_minium_block", true, false);
    public static final Block NETHER_MINIUM_ORE = register(new Block(Block.Settings.create().strength(3f, 3.0f).mapColor(MapColor.DARK_CRIMSON).requiresTool().sounds(BlockSoundGroup.NETHER_ORE)), "nether_minium_ore", true, false);
    public static final Block END_MINIUM_ORE = register(new Block(Block.Settings.create().strength(3f, 3.0f).mapColor(MapColor.PALE_YELLOW).requiresTool()), "end_minium_ore", true, false);
    public static final Block RAW_MINIUM_BLOCK = register(new Block(Block.Settings.create().strength(5f, 3.0f).mapColor(MapColor.CYAN).requiresTool()), "raw_minium_block", true, false);
    public static final Block MINIUM_GRATE = register(
            new GrateBlock(AbstractBlock.Settings.create()
                    .strength(5f, 20.0f)
                    .sounds(BlockSoundGroup.COPPER_GRATE)
                    .mapColor(MapColor.CYAN).nonOpaque().requiresTool().allowsSpawning(Blocks::never)
                    .solidBlock(Blocks::never)
                    .suffocates(Blocks::never)
                    .blockVision(Blocks::never)),
            "minium_grate",
            true, false);
    public static final Block MINIUM_BULB = register(
            new MiniumBulbBlock(
                    AbstractBlock.Settings.create()
                            .mapColor(MapColor.CYAN)
                            .strength(5.0F, 20.0F)
                            .sounds(BlockSoundGroup.COPPER_BULB)
                            .requiresTool()
                            .solidBlock(Blocks::never)
                            .luminance(createLightLevelFromLitBlockState(15))
            ),"minium_bulb",true,false
    );
    public static final Block IRIS_QUARTZ_ORE = register(new ExperienceDroppingBlock(UniformIntProvider.create(20, 25),Block.Settings.create().strength(5f, 3.0f).mapColor(MapColor.CYAN).requiresTool()), "iris_quartz_ore", true, true);
    public static final Block DEEPSLATE_IRIS_QUARTZ_ORE = register(new ExperienceDroppingBlock(UniformIntProvider.create(20, 25),Block.Settings.create().strength(7.5f, 3.0f).mapColor(MapColor.CYAN).requiresTool().sounds(BlockSoundGroup.DEEPSLATE)), "deepslate_iris_quartz_ore", true, true);
    public static final Block IRIS_QUARTZ_BLOCK = register(new Block(Block.Settings.create().strength(15f, 1200.0f).mapColor(MapColor.WHITE).requiresTool().sounds(BlockSoundGroup.METAL)), "iris_quartz_block", true, true);
    //from mekanism
    public static final Block OSMIUM_BLOCK_FROM_MEKANISM = register(new Block(Block.Settings.create().strength(5f, 6.0f).mapColor(MapColor.LIGHT_BLUE_GRAY).requiresTool()), "osmium_block", true, false);
    public static final Block RAW_OSMIUM_BLOCK_FROM_MEKANISM = register(new Block(Block.Settings.create().strength(5f, 6.0f).mapColor(MapColor.LIGHT_BLUE_GRAY).requiresTool()), "raw_osmium_block", true, false);
    public static final Block OSMIUM_ORE_FROM_MEKANISM = register(new Block(Block.Settings.create().strength(3f, 6.0f).mapColor(MapColor.CYAN).requiresTool()), "osmium_ore", true, false);
    public static final Block DEEPSLATE_OSMIUM_ORE_FROM_MEKANISM = register(new Block(Block.Settings.create().strength(4.5f, 6.0f).mapColor(MapColor.CYAN).requiresTool().sounds(BlockSoundGroup.DEEPSLATE)), "deepslate_osmium_ore", true, false);


    public static Block register(Block block, String name, boolean shouldRegisterItem, boolean FireProof) {
        Identifier id = Identifier.of(Minium_me.MOD_ID, name);
        if (shouldRegisterItem) {
            Item.Settings ItemProperty = new Item.Settings();
            if(FireProof){
                ItemProperty.fireproof();
            }
            BlockItem blockItem = new BlockItem(block, ItemProperty);
            Registry.register(Registries.ITEM, id, blockItem);
        }
        return Registry.register(Registries.BLOCK, id, block);

    }
    public static void initialize() {
    }
}
