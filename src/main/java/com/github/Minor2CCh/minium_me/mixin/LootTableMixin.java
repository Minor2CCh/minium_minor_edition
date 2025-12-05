package com.github.Minor2CCh.minium_me.mixin;

import com.github.Minor2CCh.minium_me.component.MiniumModComponent;
import com.github.Minor2CCh.minium_me.component.ToolReinforcedComponent;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.recipe.AbstractCookingRecipe;
import net.minecraft.recipe.RecipeEntry;
import net.minecraft.recipe.RecipeManager;
import net.minecraft.recipe.RecipeType;
import net.minecraft.recipe.input.SingleStackRecipeInput;
import net.minecraft.server.world.ServerWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LootTable.class)
public class LootTableMixin {
    @Inject(method = "generateLoot(Lnet/minecraft/loot/context/LootContext;)Lit/unimi/dsi/fastutil/objects/ObjectArrayList;",
            at = @At("RETURN"), cancellable = true)
    private void onGenerateLoot(LootContext context, CallbackInfoReturnable<ObjectArrayList<ItemStack>> cir) {

        // 採掘に使ったツール
        ItemStack tool = context.get(LootContextParameters.TOOL);
        if(canSmeltTool(tool)){
            ObjectArrayList<ItemStack> drops = cir.getReturnValue();
            ObjectArrayList<ItemStack> newDrops = new ObjectArrayList<>();

            for (ItemStack stack : drops) {
                if (canSmeltStack(context.getWorld(), stack)) {
                    newDrops.add(smeltStack(context.getWorld(), stack));
                } else {
                    newDrops.add(stack);
                }
            }
            System.out.println(newDrops);
            cir.setReturnValue(newDrops);
        }
    }
    @Unique
    private static boolean canSmeltTool(ItemStack stack){
        if(stack == null || stack.isEmpty()){
            return false;
        }
        return stack.getOrDefault(MiniumModComponent.TOOL_REINFORCED, ToolReinforcedComponent.EMPTY) == ToolReinforcedComponent.AUTO_SMELT;
    }
    @Unique
    private static boolean canSmeltStack(ServerWorld world, ItemStack stack){
        if(stack.isEmpty()){
            return false;
        }
        RecipeEntry<?> recipeEntry;
        final RecipeManager.MatchGetter<SingleStackRecipeInput, ? extends AbstractCookingRecipe> matchGetter = RecipeManager.createCachedMatchGetter(RecipeType.SMELTING);
        recipeEntry = matchGetter.getFirstMatch(new SingleStackRecipeInput(stack), world).orElse(null);
        return recipeEntry != null;
    }
    @Unique
    private static ItemStack smeltStack(ServerWorld world, ItemStack stack){
        RecipeEntry<?> recipeEntry;
        final RecipeManager.MatchGetter<SingleStackRecipeInput, ? extends AbstractCookingRecipe> matchGetter = RecipeManager.createCachedMatchGetter(RecipeType.SMELTING);
        recipeEntry = matchGetter.getFirstMatch(new SingleStackRecipeInput(stack), world).orElse(null);
        if(recipeEntry == null){
            return stack;
        }
        ItemStack copyStack = recipeEntry.value().getResult(world.getRegistryManager()).copy();
        copyStack.setCount(stack.getCount());
        return copyStack;
    }
}
