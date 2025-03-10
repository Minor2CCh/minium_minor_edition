package com.github.Minor2CCh.minium_me.item;
import com.google.common.base.Suppliers;
import java.util.function.Supplier;
import net.minecraft.block.Block;
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
    MINIUM_TOOL(BlockTags.INCORRECT_FOR_IRON_TOOL, 375, 10.0f, 2.5f, 15, () -> Ingredient.ofItems(MiniumItem.MINIUM_INGOT)),
    C_MINIUM_TOOL(BlockTags.INCORRECT_FOR_NETHERITE_TOOL, 1949, 11.0f, 3.5f, 15, () -> Ingredient.ofItems(MiniumItem.C_MINIUM_INGOT)),
    IRIS_QUARTZ_TOOL(BlockTags.INCORRECT_FOR_NETHERITE_TOOL, 7716, 13.0f, 6f, 25, () -> Ingredient.ofItems(MiniumItem.IRIS_QUARTZ_INGOT)),
    MINIUM_MULTITOOL(MINIUM_TOOL.inverseTag, MINIUM_TOOL.itemDurability*3, MINIUM_TOOL.miningSpeed, MINIUM_TOOL.attackDamage, MINIUM_TOOL.enchantability, MINIUM_TOOL.repairIngredient),
    C_MINIUM_MULTITOOL(C_MINIUM_TOOL.inverseTag, C_MINIUM_TOOL.itemDurability*3, C_MINIUM_TOOL.miningSpeed, C_MINIUM_TOOL.attackDamage, C_MINIUM_TOOL.enchantability, C_MINIUM_TOOL.repairIngredient),
    IRIS_QUARTZ_MULTITOOL(IRIS_QUARTZ_TOOL.inverseTag, IRIS_QUARTZ_TOOL.itemDurability*3, IRIS_QUARTZ_TOOL.miningSpeed, IRIS_QUARTZ_TOOL.attackDamage, IRIS_QUARTZ_TOOL.enchantability, IRIS_QUARTZ_TOOL.repairIngredient);

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
        return (Ingredient)this.repairIngredient.get();
    }
}
