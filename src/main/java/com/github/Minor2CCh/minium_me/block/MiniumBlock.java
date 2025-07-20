package com.github.Minor2CCh.minium_me.block;

import com.github.Minor2CCh.minium_me.Minium_me;
import com.github.Minor2CCh.minium_me.statuseffect.MiniumStatusEffects;
import net.minecraft.block.*;
import net.minecraft.block.enums.NoteBlockInstrument;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.intprovider.ConstantIntProvider;
import net.minecraft.util.math.intprovider.UniformIntProvider;

import java.util.function.Function;


public class MiniumBlock{

    public static final Block MINIUM_ORE = register(new ExperienceDroppingBlock(
            ConstantIntProvider.create(0),Block.Settings.create().strength(3f, 3.0f).mapColor(MapColor.STONE_GRAY).requiresTool()), "minium_ore", new Item.Settings());
    public static final Block DEEPSLATE_MINIUM_ORE = register(new ExperienceDroppingBlock(
            ConstantIntProvider.create(0),Block.Settings.create().strength(4.5f, 3.0f).mapColor(MapColor.DEEPSLATE_GRAY).requiresTool().sounds(BlockSoundGroup.DEEPSLATE)), "deepslate_minium_ore", new Item.Settings());
    public static final Block MINIUM_BLOCK = register(new Block(Block.Settings.create().strength(5f, 20.0f).mapColor(MapColor.CYAN).requiresTool().sounds(BlockSoundGroup.METAL).instrument(NoteBlockInstrument.IRON_XYLOPHONE)), "minium_block", new Item.Settings());
    public static final Block CONCENTRATED_MINIUM_BLOCK = register(new Block(Block.Settings.create().strength(7.5f, 600.0f).mapColor(MapColor.DIAMOND_BLUE).requiresTool().sounds(BlockSoundGroup.NETHERITE).instrument(NoteBlockInstrument.IRON_XYLOPHONE)), "concentrated_minium_block", new Item.Settings());
    public static final Block NETHER_MINIUM_ORE = register(new ExperienceDroppingBlock(
            ConstantIntProvider.create(0),Block.Settings.create().strength(3f, 3.0f).mapColor(MapColor.DARK_CRIMSON).requiresTool().sounds(BlockSoundGroup.NETHER_ORE)), "nether_minium_ore", new Item.Settings());
    public static final Block END_MINIUM_ORE = register(new ExperienceDroppingBlock(
            ConstantIntProvider.create(0),Block.Settings.create().strength(3f, 3.0f).mapColor(MapColor.PALE_YELLOW).requiresTool()), "end_minium_ore", new Item.Settings());
    public static final Block RAW_MINIUM_BLOCK = register(new Block(Block.Settings.create().strength(5f, 3.0f).mapColor(MapColor.CYAN).requiresTool()), "raw_minium_block", new Item.Settings());
    public static final Block MINIUM_GRATE = register(
            new GrateBlock(Block.Settings.create()
                    .strength(5f, 20.0f)
                    .sounds(BlockSoundGroup.COPPER_GRATE)
                    .mapColor(MapColor.CYAN).nonOpaque().requiresTool().allowsSpawning(Blocks::never)
                    .solidBlock(Blocks::never)
                    .suffocates(Blocks::never)
                    .blockVision(Blocks::never)),
            "minium_grate",
            new Item.Settings());
    public static final Block MINIUM_BULB = register(
            new MiniumBulbBlock(
                    Block.Settings.create()
                            .mapColor(MapColor.CYAN)
                            .strength(5.0F, 20.0F)
                            .sounds(BlockSoundGroup.COPPER_BULB)
                            .requiresTool()
                            .solidBlock(Blocks::never)
                            .luminance(Blocks.createLightLevelFromLitBlockState(15))
            ),"minium_bulb",new Item.Settings()
    );
    public static final Block IRIS_QUARTZ_ORE = register(new ExperienceDroppingBlock(UniformIntProvider.create(20, 25),Block.Settings.create().strength(5f, 3.0f).mapColor(MapColor.STONE_GRAY).requiresTool()), "iris_quartz_ore", new Item.Settings().fireproof());
    public static final Block DEEPSLATE_IRIS_QUARTZ_ORE = register(new ExperienceDroppingBlock(UniformIntProvider.create(20, 25),Block.Settings.create().strength(7.5f, 3.0f).mapColor(MapColor.DEEPSLATE_GRAY).requiresTool().sounds(BlockSoundGroup.DEEPSLATE)), "deepslate_iris_quartz_ore", new Item.Settings().fireproof());
    public static final Block IRIS_QUARTZ_BLOCK = register(new Block(Block.Settings.create().strength(15f, 1200.0f).mapColor(MapColor.WHITE).requiresTool().sounds(BlockSoundGroup.METAL).instrument(NoteBlockInstrument.BELL)), "iris_quartz_block", new Item.Settings().fireproof());
    public static final Block REDSTONE_ENERGY_BLOCK = register(new RedStoneEnergyBlock(Block.Settings.create()
            .replaceable()
            .mapColor(createMapColorFromWaterloggedBlockState(MapColor.CLEAR))
            //.solidBlock(Blocks::never)
            .strength(0F, 3600000.8F)
            //.suffocates(Blocks::never)
            .dropsNothing()
            .nonOpaque()
            .noCollision()
            //.blockVision(Blocks::never)
            ), "redstone_energy_block");
    public static final Block GLOWSTONE_ENERGY_BLOCK = register(new GlowStoneEnergyBlock(Block.Settings.create()
            .mapColor(createMapColorFromWaterloggedBlockState(MapColor.CLEAR))
            .strength(0F, 3600000.8F)
            .dropsNothing()
            .nonOpaque()
            .noCollision()
            .luminance(state -> 15)
            ), "glowstone_energy_block");
    public static final Block STONE_ALCHEMY_BREAK_STONE = register(new Block(Block.Settings.create()
            .mapColor(MapColor.STONE_GRAY)
            .strength(0F, 0F)
            .nonOpaque()
            .noCollision()
            .sounds(BlockSoundGroup.STONE)
    ), "stone_alchemy_break_stone");
    public static final Block STONE_ALCHEMY_BREAK_NETHERRACK = register(new Block(Block.Settings.create()
            .mapColor(MapColor.DARK_RED)
            .strength(0F, 0F)
            .nonOpaque()
            .noCollision()
            .sounds(BlockSoundGroup.NETHERRACK)
    ), "stone_alchemy_break_netherrack");
    public static final Block STONE_ALCHEMY_BREAK_END_STONE = register(new Block(Block.Settings.create()
            .mapColor(MapColor.PALE_YELLOW)
            .strength(0F, 0F)
            .nonOpaque()
            .noCollision()
            .sounds(BlockSoundGroup.STONE)
    ), "stone_alchemy_break_end_stone");
    public static final Block WIND_CHARGE_BLOCK = register(new WindChargeBlock(Block.Settings.create().strength(1.0f, 1.5f).mapColor(MapColor.LIGHT_BLUE_GRAY).sounds(BlockSoundGroup.WOOL).instrument(NoteBlockInstrument.GUITAR)), "wind_charge_block", new Item.Settings());
    public static Block EASY_GRINDER = register(new GrindingBlock(Block.Settings.create().strength(2.0f, 6.0f).mapColor(MapColor.CYAN).sounds(BlockSoundGroup.COPPER_BULB)
            .suffocates(Blocks::never)
            .nonOpaque()
            .blockVision(Blocks::never)), "easy_grinder", new Item.Settings());
    public static Block ADVANCED_GRINDER = register(new AdvancedGrindingBlock(Block.Settings.create().strength(2.0f, 6.0f).mapColor(MapColor.DIAMOND_BLUE).sounds(BlockSoundGroup.COPPER_BULB)
            .suffocates(Blocks::never)
            .nonOpaque()
            .blockVision(Blocks::never)), "advanced_grinder", new Item.Settings());
    public static final Block EASY_CONVEYOR = register(new ConveyorBlock(Block.Settings.create().strength(2.0f, 6.0f).mapColor(MapColor.CYAN).sounds(BlockSoundGroup.COPPER_BULB).allowsSpawning(Blocks::never)), "easy_conveyor", new Item.Settings());

    public static final Block MINIUM_ARTIFICIAL_FLOWER = register(

            new FlowerBlock(
                    MiniumStatusEffects.POISON_HEAL,
                    8,
                    Block.Settings.create()
                            .mapColor(MapColor.CYAN)
                            .noCollision()
                            .breakInstantly()
                            .sounds(BlockSoundGroup.GRASS)
                            .offset(Block.OffsetType.XZ)
                            .pistonBehavior(PistonBehavior.DESTROY)
            ),"minium_artificial_flower", new Item.Settings());

    public static final Block POTTED_MINIUM_ARTIFICIAL_FLOWER = register(Blocks.createFlowerPotBlock(MINIUM_ARTIFICIAL_FLOWER), "potted_minium_artificial_flower");
    //from mekanism
    public static final Block OSMIUM_BLOCK_FROM_MEKANISM = register(new Block(Block.Settings.create().strength(7.5f, 12.0f).mapColor(MapColor.LIGHT_BLUE_GRAY).requiresTool()), "osmium_block", new Item.Settings());
    public static final Block RAW_OSMIUM_BLOCK_FROM_MEKANISM = register(new Block(Block.Settings.create().strength(7.5f, 12.0f).mapColor(MapColor.LIGHT_BLUE_GRAY).requiresTool()), "raw_osmium_block", new Item.Settings());
    public static final Block OSMIUM_ORE_FROM_MEKANISM = register(new ExperienceDroppingBlock(
            ConstantIntProvider.create(0),Block.Settings.create().strength(3f, 3.0f).mapColor(MapColor.STONE_GRAY).requiresTool()), "osmium_ore", new Item.Settings());
    public static final Block DEEPSLATE_OSMIUM_ORE_FROM_MEKANISM = register(new ExperienceDroppingBlock(
            ConstantIntProvider.create(0),Block.Settings.create().strength(4.5f, 3.0f).mapColor(MapColor.DEEPSLATE_GRAY).requiresTool().sounds(BlockSoundGroup.DEEPSLATE)), "deepslate_osmium_ore", new Item.Settings());


    public static Block register(Block block, String name, Item.Settings itemSettings) {
        Identifier id = Identifier.of(Minium_me.MOD_ID, name);
        BlockItem blockItem = new BlockItem(block, itemSettings);
        Registry.register(Registries.ITEM, id, blockItem);
        return register(block, name);

    }
    public static Block register(Block block, String name) {
        Identifier id = Identifier.of(Minium_me.MOD_ID, name);
        return Registry.register(Registries.BLOCK, id, block);

    }

    private static Function<BlockState, MapColor> createMapColorFromWaterloggedBlockState(MapColor mapColor) {
        return state -> state.get(Properties.WATERLOGGED) ? MapColor.WATER_BLUE : mapColor;
    }
    public static void initialize() {
    }
    public static String getBlockId(Block block){
        String key = block.getTranslationKey();
        int lastDot = key.lastIndexOf(".");
        return key.substring(lastDot + 1);
    }
}
