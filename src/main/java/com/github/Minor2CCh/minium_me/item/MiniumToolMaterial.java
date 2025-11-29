package com.github.Minor2CCh.minium_me.item;
import com.github.Minor2CCh.minium_me.block.MiniumBlock;
import com.github.Minor2CCh.minium_me.block.MiniumBlockTag;
import com.github.Minor2CCh.minium_me.config.MiniumConfigLoader;
import com.google.common.base.Suppliers;

import java.util.List;
import java.util.function.Supplier;
import net.minecraft.block.Block;
import net.minecraft.component.type.ToolComponent;
import net.minecraft.item.ToolMaterial;
import net.minecraft.recipe.Ingredient;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.TagKey;

/*
    WOOD(BlockTags.INCORRECT_FOR_WOODEN_TOOL, 59, 2.0f, 0.0f, 15, () -> Ingredient.fromTag(ItemTags.PLANKS)),
    STONE(BlockTags.INCORRECT_FOR_STONE_TOOL, 131, 4.0f, 1.0f, 5, () -> Ingredient.fromTag(ItemTags.STONE_TOOL_MATERIALS)),
    IRON(BlockTags.INCORRECT_FOR_IRON_TOOL, 250, 6.0f, 2.0f, 14, () -> Ingredient.ofItems(Items.IRON_INGOT)),
    DIAMOND(BlockTags.INCORRECT_FOR_DIAMOND_TOOL, 1561, 8.0f, 3.0f, 10, () -> Ingredient.ofItems(Items.DIAMOND)),
    GOLD(BlockTags.INCORRECT_FOR_GOLD_TOOL, 32, 12.0f, 0.0f, 22, () -> Ingredient.ofItems(Items.GOLD_INGOT)),
    NETHERITE(BlockTags.INCORRECT_FOR_NETHERITE_TOOL, 2031, 9.0f, 4.0f, 15, () -> Ingredient.ofItems(Items.NETHERITE_INGOT));
*/
public enum MiniumToolMaterial implements ToolMaterial{
    MINIUM_TOOL(BlockTags.INCORRECT_FOR_IRON_TOOL, MiniumConfigLoader.getConfig().getMaterialMinium().getDurability(), MiniumConfigLoader.getConfig().getMaterialMinium().getMiningSpeed(), MiniumConfigLoader.getConfig().getMaterialMinium().getAttackDamage(), MiniumConfigLoader.getConfig().getMaterialMinium().getEnchantability(), () -> Ingredient.fromTag(MiniumItemTag.MINIUM_INGOT)),
    C_MINIUM_TOOL(BlockTags.INCORRECT_FOR_DIAMOND_TOOL, MiniumConfigLoader.getConfig().getMaterialConcentratedMinium().getDurability(), MiniumConfigLoader.getConfig().getMaterialConcentratedMinium().getMiningSpeed(), MiniumConfigLoader.getConfig().getMaterialConcentratedMinium().getAttackDamage(), MiniumConfigLoader.getConfig().getMaterialConcentratedMinium().getEnchantability(), () -> Ingredient.fromTag(MiniumItemTag.C_MINIUM_INGOT)),
    IRIS_QUARTZ_TOOL(MiniumBlockTag.INCORRECT_FOR_IRIS_QUARTZ_TOOL, MiniumConfigLoader.getConfig().getMaterialIrisQuartz().getDurability(), MiniumConfigLoader.getConfig().getMaterialIrisQuartz().getMiningSpeed(), MiniumConfigLoader.getConfig().getMaterialIrisQuartz().getAttackDamage(), MiniumConfigLoader.getConfig().getMaterialIrisQuartz().getEnchantability(), () -> Ingredient.fromTag(MiniumItemTag.IRIS_QUARTZ_INGOT));

    private final TagKey<Block> inverseTag;
    private final int itemDurability;
    private final float miningSpeed;
    private final float attackDamage;
    private final int enchantability;
    private final Supplier<Ingredient> repairIngredient;

    MiniumToolMaterial(
            final TagKey<Block> inverseTag,
            final int itemDurability,
            final float miningSpeed,
            final float attackDamage,
            final int enchantability,
            final Supplier<Ingredient> repairIngredient
    ) {
        this.inverseTag = inverseTag;
        this.itemDurability = itemDurability;
        this.miningSpeed = miningSpeed;
        this.attackDamage = attackDamage;
        this.enchantability = enchantability;
        this.repairIngredient = Suppliers.memoize(repairIngredient::get);
    }

    @Override
    public int getDurability() {
        return this.itemDurability;
    }

    @Override
    public float getMiningSpeedMultiplier() {
        return this.miningSpeed;
    }

    @Override
    public float getAttackDamage() {
        return this.attackDamage;
    }

    @Override
    public TagKey<Block> getInverseTag() {
        return this.inverseTag;
    }

    @Override
    public int getEnchantability() {
        return this.enchantability;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return this.repairIngredient.get();
    }

    @Override
    public ToolComponent createComponent(TagKey<Block> tag) {
        if(this.equals(MiniumToolMaterial.C_MINIUM_TOOL) && (tag.equals(BlockTags.PICKAXE_MINEABLE) || (tag.equals(MiniumBlockTag.MULTITOOL_MINEABLE)))){
            return new ToolComponent(
                    List.of(ToolComponent.Rule.ofAlwaysDropping(CONCENTRATED_MINIUM_WHITELIST, this.getMiningSpeedMultiplier()), ToolComponent.Rule.ofNeverDropping(this.getInverseTag()), ToolComponent.Rule.ofAlwaysDropping(tag, this.getMiningSpeedMultiplier())), 1.0F, 1
            );

        }
        return ToolMaterial.super.createComponent(tag);
    }
    private static final List<Block> CONCENTRATED_MINIUM_WHITELIST = List.of(MiniumBlock.IRIS_QUARTZ_BLOCK, MiniumBlock.IRIS_QUARTZ_ORE, MiniumBlock.DEEPSLATE_IRIS_QUARTZ_ORE, MiniumBlock.IRIS_QUARTZ_CORE, MiniumBlock.IRIS_QUARTZ_GRINDER);

}
